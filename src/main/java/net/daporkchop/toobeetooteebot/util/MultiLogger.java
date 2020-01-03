package net.daporkchop.toobeetooteebot.util;

import net.daporkchop.lib.logging.LogLevel;
import net.daporkchop.lib.logging.Logger;
import net.daporkchop.lib.logging.format.FormatParser;
import net.daporkchop.lib.logging.format.MessageFormatter;
import net.daporkchop.lib.logging.format.MessagePrinter;
import net.daporkchop.lib.logging.format.component.TextComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MultiLogger implements Logger {
    List<Logger> loggers = new ArrayList<>();

    public MultiLogger(Logger ...loggers) {
        this.loggers.addAll(Arrays.asList(loggers));
    }

    @Override
    public MultiLogger log(LogLevel level, String message) {
        for (Logger logger : loggers) {
            logger.log(level, message);
        }
        return this;
    }

    @Override
    public FormatParser getFormatParser() {
        return null;
    }

    @Override
    public Logger setFormatParser(FormatParser parser) {
        return null;
    }

    @Override
    public MessageFormatter getMessageFormatter() {
        return null;
    }

    @Override
    public Logger setMessageFormatter(MessageFormatter formatter) {
        return null;
    }

    @Override
    public MessagePrinter getMessagePrinter() {
        return null;
    }

    @Override
    public Logger setMessagePrinter(MessagePrinter printer) {
        return null;
    }

    @Override
    public TextComponent getAlertHeader() {
        return null;
    }

    @Override
    public Logger setAlertHeader(TextComponent alertHeader) {
        return null;
    }

    @Override
    public TextComponent getAlertPrefix() {
        return null;
    }

    @Override
    public Logger setAlertPrefix(TextComponent alertPrefix) {
        return null;
    }

    @Override
    public TextComponent getAlertFooter() {
        return null;
    }

    @Override
    public Logger setAlertFooter(TextComponent alertFooter) {
        return null;
    }

    @Override
    public Set<LogLevel> getLogLevels() {
        return null;
    }

    @Override
    public Logger setLogLevels(Set<LogLevel> levels) {
        return null;
    }

    @Override
    public MultiLogger channel(String name) {
        List<Logger> channelLoggers = new ArrayList<>();
        for (Logger logger : loggers) {
            channelLoggers.add(logger.channel(name));
        }
        return new MultiLogger(channelLoggers.toArray(new Logger[0]));
    }
}
