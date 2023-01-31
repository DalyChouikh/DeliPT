package com.discord;

import com.discord.commands.Gpt;
import com.discord.commands.Help;
import com.discord.commands.Image;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Arrays;

public class Bot {
    public final static GatewayIntent[] INTENTS = {GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.SCHEDULED_EVENTS,
            GatewayIntent.MESSAGE_CONTENT};

    public static void main(String[] args) {
        JDA bot = JDABuilder.create(Arrays.asList(INTENTS))
                .disableCache(CacheFlag.VOICE_STATE)
                .enableCache(CacheFlag.EMOJI)
                .setToken(System.getenv("DeliPT_TOKEN"))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Gpt())
                .addEventListeners(new Image())
                .addEventListeners(new Help())
                .build();
        bot.getPresence().setActivity(Activity.listening("/help"));
        bot.updateCommands()
                .addCommands(Commands.slash("gpt", "Ask a question")
                                .addOption(OptionType.STRING, "question", "The question you need an answer to", true)
                                .setGuildOnly(true),
                        Commands.slash("image", "Generate an image based on a text")
                                .addOption(OptionType.STRING, "text", "Describe the image content", true)
                                .setGuildOnly(true),
                        Commands.slash("help", "Displays commands").setGuildOnly(true)
                ).queue();
    }

}