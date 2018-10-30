/*
 * Decompiled with CFR 0_132.
 */
package org.yaml.snakeyaml.introspector;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.FieldProperty;
import org.yaml.snakeyaml.introspector.MethodProperty;
import org.yaml.snakeyaml.introspector.MissingProperty;
import org.yaml.snakeyaml.introspector.Property;

public class PropertyUtils {
    private final Map<Class<?>, Map<String, Property>> propertiesCache = new HashMap();
    private final Map<Class<?>, Set<Property>> readableProperties = new HashMap();
    private BeanAccess beanAccess = BeanAccess.DEFAULT;
    private boolean allowReadOnlyProperties = false;
    private boolean skipMissingProperties = false;

    protected Map<String, Property> getPropertiesMap(Class<?> type, BeanAccess bAccess) throws IntrospectionException {
        if (this.propertiesCache.containsKey(type)) {
            return this.propertiesCache.get(type);
        }
        LinkedHashMap<String, Property> properties = new LinkedHashMap<String, Property>();
        boolean inaccessableFieldsExist = false;
        switch (bAccess) {
            case FIELD: {
                for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                    for (Field field : c.getDeclaredFields()) {
                        int modifiers = field.getModifiers();
                        if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || properties.containsKey(field.getName())) continue;
                        properties.put(field.getName(), new FieldProperty(field));
                    }
                }
                break;
            }
            default: {
                for (PropertyDescriptor property : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                    Method readMethod = property.getReadMethod();
                    if (readMethod != null && readMethod.getName().equals("getClass")) continue;
                    properties.put(property.getName(), new MethodProperty(property));
                }
                for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                    for (Field field : c.getDeclaredFields()) {
                        int modifiers = field.getModifiers();
                        if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
                        if (Modifier.isPublic(modifiers)) {
                            properties.put(field.getName(), new FieldProperty(field));
                            continue;
                        }
                        inaccessableFieldsExist = true;
                    }
                }
            }
        }
        if (properties.isEmpty() && inaccessableFieldsExist) {
            throw new YAMLException("No JavaBean properties found in " + type.getName());
        }
        this.propertiesCache.put(type, properties);
        return properties;
    }

    public Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException {
        return this.getProperties(type, this.beanAccess);
    }

    public Set<Property> getProperties(Class<? extends Object> type, BeanAccess bAccess) throws IntrospectionException {
        if (this.readableProperties.containsKey(type)) {
            return this.readableProperties.get(type);
        }
        Set<Property> properties = this.createPropertySet(type, bAccess);
        this.readableProperties.put(type, properties);
        return properties;
    }

    protected Set<Property> createPropertySet(Class<? extends Object> type, BeanAccess bAccess) throws IntrospectionException {
        TreeSet<Property> properties = new TreeSet<Property>();
        Collection<Property> props = this.getPropertiesMap(type, bAccess).values();
        for (Property property : props) {
            if (!property.isReadable() || !this.allowReadOnlyProperties && !property.isWritable()) continue;
            properties.add(property);
        }
        return properties;
    }

    public Property getProperty(Class<? extends Object> type, String name) throws IntrospectionException {
        return this.getProperty(type, name, this.beanAccess);
    }

    public Property getProperty(Class<? extends Object> type, String name, BeanAccess bAccess) throws IntrospectionException {
        Map<String, Property> properties = this.getPropertiesMap(type, bAccess);
        Property property = properties.get(name);
        if (property == null && this.skipMissingProperties) {
            property = new MissingProperty(name);
        }
        if (property == null || !property.isWritable()) {
            throw new YAMLException("Unable to find property '" + name + "' on class: " + type.getName());
        }
        return property;
    }

    public void setBeanAccess(BeanAccess beanAccess) {
        if (this.beanAccess != beanAccess) {
            this.beanAccess = beanAccess;
            this.propertiesCache.clear();
            this.readableProperties.clear();
        }
    }

    public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) {
        if (this.allowReadOnlyProperties != allowReadOnlyProperties) {
            this.allowReadOnlyProperties = allowReadOnlyProperties;
            this.readableProperties.clear();
        }
    }

    public void setSkipMissingProperties(boolean skipMissingProperties) {
        if (this.skipMissingProperties != skipMissingProperties) {
            this.skipMissingProperties = skipMissingProperties;
            this.readableProperties.clear();
        }
    }

}
