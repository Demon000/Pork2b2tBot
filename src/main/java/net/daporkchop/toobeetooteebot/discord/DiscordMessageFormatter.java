package net.daporkchop.toobeetooteebot.discord;

import net.daporkchop.lib.logging.LogLevel;
import net.daporkchop.lib.logging.format.MessageFormatter;
import net.daporkchop.lib.logging.format.component.TextComponent;
import net.daporkchop.lib.logging.format.component.TextComponentHolder;
import net.daporkchop.lib.logging.format.component.TextComponentString;

import java.util.*;
import java.util.List;

public class DiscordMessageFormatter implements MessageFormatter {
    protected static final TextComponent START = new TextComponentString("[");
    protected static final TextComponent BETWEEN = new TextComponentString(" ");
    protected static final TextComponent END = new TextComponentString("]");

    protected static final Map<LogLevel, TextComponent> LEVEL_COMPONENTS = new EnumMap<>(LogLevel.class);

    static {
        for (LogLevel level : LogLevel.values())    {
            LEVEL_COMPONENTS.put(level, new TextComponentString(level.getColor(), null, level.getStyle(), level.name()));
        }
    }

    private void addTextComponents(List<TextComponent> components, TextComponent message) {
        if (message.getText() != null) {
            components.add(new TextComponentString(message.getColor(), null, message.getStyle(), message.getText()));
        }

        if (!message.getChildren().isEmpty()) {
            for (TextComponent child : message.getChildren()) {
                addTextComponents(components, child);
            }
        }
    }

    @Override
    public TextComponent format(Date date, String channelName, LogLevel level, TextComponent message) {
        List<TextComponent> components = new ArrayList<>();

        if (channelName == null) {
            components.add(new TextComponentString(""));
        } else {
            components.add(new TextComponentString(channelName));
        }

        components.add(LEVEL_COMPONENTS.get(level));

        addTextComponents(components, message);

        return new TextComponentHolder(components);
    }
}
