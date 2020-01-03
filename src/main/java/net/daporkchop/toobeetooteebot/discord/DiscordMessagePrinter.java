package net.daporkchop.toobeetooteebot.discord;

import net.daporkchop.lib.logging.format.MessagePrinter;
import net.daporkchop.lib.logging.format.component.TextComponent;
import net.daporkchop.toobeetooteebot.util.Constants;

public class DiscordMessagePrinter implements MessagePrinter, Constants {
    @Override
    public void accept(TextComponent component) {
        StringBuilder builder = new StringBuilder();

        builder.append("```\n");
        this.doBuild(builder, component);
        builder.append("\n```");

        DISCORD_BOT.sendMessage(builder.toString());
    }

    protected void doBuild(StringBuilder builder, TextComponent component) {
        String text = component.getText();
        if (text != null && !text.isEmpty()) {
            builder.append(text);
        }

        for (TextComponent child : component.getChildren()) {
            this.doBuild(builder, child);
        }
    }
}
