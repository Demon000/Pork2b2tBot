/*
 * Decompiled with CFR 0_132.
 */
package gnu.trove.iterator;

import gnu.trove.iterator.TAdvancingIterator;

public interface TObjectShortIterator<K>
extends TAdvancingIterator {
    public K key();

    public short value();

    public short setValue(short var1);
}
