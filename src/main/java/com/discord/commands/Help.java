package com.discord.commands;

import com.discord.embeds.Embeds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("help")){
            event.replyEmbeds(Embeds.helpEmbed(event.getJDA().getSelfUser())).setEphemeral(true).queue();
        }
    }
}
