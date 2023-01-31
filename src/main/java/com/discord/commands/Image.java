package com.discord.commands;

import com.discord.embeds.Embeds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Image extends ListenerAdapter {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/images/generations";

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("image")) {
            event.deferReply(false).queue();
            OkHttpClient httpClient = new OkHttpClient();
            String prompt = event.getOption("text").getAsString();
            JSONObject json = new JSONObject();
            json.put("prompt", prompt);
            json.put("n", 1);
            json.put("size", "512x512");
            RequestBody requestBody = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", String.format("Bearer %s", API_KEY))
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                String responseString = response.body().string();
                JSONObject responseJson = new JSONObject(responseString);
                if (responseJson.has("error")) {
                    event.getHook().sendMessage("**" + responseJson.getJSONObject("error").getString("message") + "**").queue();
                } else {
                    String url = responseJson.getJSONArray("data")
                            .getJSONObject(0).getString("url");
                    event.getHook().sendMessageEmbeds(Embeds.imageEmbed(prompt, url, event.getUser())).queue();
                }

            } catch (IOException | JSONException e) {
                event.getHook().sendMessage("**Error occured**").queue();
                e.printStackTrace();
            }
        }
    }

}
