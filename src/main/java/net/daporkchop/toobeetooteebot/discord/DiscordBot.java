package net.daporkchop.toobeetooteebot.discord;

import net.daporkchop.toobeetooteebot.Bot;
import net.daporkchop.toobeetooteebot.util.Constants;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter implements Constants {
    public static final boolean ENABLED = CONFIG.getBoolean("discord.enabled", false);
    private static final String TOKEN = CONFIG.getString("discord.token");
    private static final String CHANNEL_ID = CONFIG.getString("discord.channelId");

    private MessageChannel CHANNEL;
    private boolean INITIALIZED;

    public DiscordBot() {
        try {
            JDA jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).build();
            jda.addEventListener(this);
            jda.awaitReady();

            CHANNEL = jda.getTextChannelById(CHANNEL_ID);

            DISCORD_LOG.success("Discord bot connected!");
            INITIALIZED = true;
        } catch (LoginException | InterruptedException e) {
            DISCORD_LOG.error(e.getMessage());
            INITIALIZED = false;
        }
    }

    public void sendMessage(String message) {
        if (!ENABLED || !INITIALIZED) {
            return;
        }

        CHANNEL.sendMessage(message).queue();
    }

    public void sendEmbed(MessageEmbed message) {
        if (!ENABLED || !INITIALIZED) {
            return;
        }

        CHANNEL.sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!ENABLED || !INITIALIZED) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        String rawMessage = event.getMessage().getContentRaw();
        if (rawMessage.equals("!connect")) {
            Bot.triggerConnection();
        } else if (rawMessage.equals("!disconnect")) {
            Bot.interruptConnection();
        } else if (rawMessage.startsWith("!chat ")) {
            String message = rawMessage.substring(6);
            Bot.sendMessage(message);
        }

        String message = "[" +
                event.getAuthor().getName() +
                "]" +
                " " +
                event.getMessage().getContentDisplay();
        DISCORD_LOG.info(message);
    }
}
