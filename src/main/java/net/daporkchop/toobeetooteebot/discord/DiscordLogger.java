package net.daporkchop.toobeetooteebot.discord;

import lombok.NonNull;
import net.daporkchop.lib.logging.LogLevel;
import net.daporkchop.lib.logging.format.component.TextComponent;
import net.daporkchop.lib.logging.impl.SimpleLogger;
import net.daporkchop.lib.minecraft.text.parser.MinecraftFormatParser;

public class DiscordLogger extends SimpleLogger {

    public DiscordLogger() {
        super(new DiscordMessagePrinter());
        setMessageFormatter(new DiscordMessageFormatter());
        setFormatParser(new MinecraftFormatParser());
    }

    @Override
    protected synchronized void doLog(@NonNull LogLevel level, @NonNull TextComponent component) {
        if (this.logLevels.contains(level))   {
            this.messagePrinter.accept(component);
        }
    }
}
