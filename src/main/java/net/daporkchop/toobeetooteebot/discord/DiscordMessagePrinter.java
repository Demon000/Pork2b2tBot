package net.daporkchop.toobeetooteebot.discord;

import net.daporkchop.lib.logging.format.MessagePrinter;
import net.daporkchop.lib.logging.format.component.TextComponent;
import net.daporkchop.toobeetooteebot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;
import java.util.List;

public class DiscordMessagePrinter implements MessagePrinter, Constants {
    @Override
    public void accept(TextComponent component) {
        EmbedBuilder builder = new EmbedBuilder();

        this.doBuild(builder, component);

        DISCORD_BOT.sendEmbed(builder.build());
    }

    private void doBuild(EmbedBuilder builder, TextComponent component) {
        Color color = component.getColor();
        if (color != null) {
            builder.setColor(color);
        }

        List<TextComponent> prefix = component.getChildren().subList(0, 2);
        String channelName = prefix.get(0).toRawString();
        String levelName = prefix.get(1).toRawString();
        if (!channelName.equals("Chat") && !levelName.equals("INFO")) {
            String title = channelName + " " + levelName;
            builder.setFooter(title);
        }

        if (component.getChildren().size() <= 2) {
            return;
        }

        TextComponent firstContent = component.getChildren().get(2);
        if (firstContent.getColor() != null) {
            builder.setColor(firstContent.getColor());
        }

        List<TextComponent> content = component.getChildren().subList(2, component.getChildren().size());
        StringBuilder sb = new StringBuilder();
        for (TextComponent text : content) {
            if (text.getText() != null) {
                sb.append(text.getText());
            }
        }

        builder.setDescription(sb.toString());
    }
}
