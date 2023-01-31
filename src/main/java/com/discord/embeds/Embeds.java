package com.discord.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class Embeds {
    public static MessageEmbed questionEmbed(String question, String answer, User user){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(question + "?")
                .setDescription("**Answer**"+ answer)
                .setThumbnail("https://media.giphy.com/media/WRQBXSCnEFJIuxktnw/giphy.gif")
                .setColor(5766022)
                .setFooter("Question requested by : " + user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl());
        return embed.build();
    }
    public static MessageEmbed imageEmbed(String text, String url, User user){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(text)
                .setImage(url)
                .setColor(5766022)
                .setFooter("Image requested by : " + user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl());
        return embed.build();
    }
    public static MessageEmbed helpEmbed(User jda){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("DeliPT",null, jda.getEffectiveAvatarUrl())
                .setTitle("Commands:")
                .addField("</help:1064908407095705631>", "`/help`: Displays this message", false)
                .addField("</gpt:1064908407095705630>", "`/gpt [question]`: Answer your question", false)
                .addField("</image:1064998945459294348>", "`/image [text]`: Generates an image based on the given text", false)
                .setThumbnail("https://media.giphy.com/media/l46Cbqvg6gxGvh2PS/giphy.gif")
                .setColor(5766022)
                .setFooter("Developped by Daly#3068 ❤️", "https://images-ext-2.discordapp.net/external/fSxextfq1fTuMD7Vgb2VY4hwE6BtfikiTXK0rTqRWt8/https/cdn.discordapp.com/avatars/392041081983860746/316401c64397974a28995adbe5ee5ed8.png");
        return embed.build();
    }
}
