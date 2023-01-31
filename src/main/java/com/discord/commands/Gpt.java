package com.discord.commands;

import com.discord.embeds.Embeds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Gpt extends ListenerAdapter {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/completions";
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("gpt")) {
            event.deferReply(false).queue();
            OkHttpClient httpClient = new OkHttpClient();
            String prompt = event.getOption("question").getAsString();
            JSONObject json = new JSONObject();
            int tokens = prompt.length() / 4 + prompt.length() % 4;
            System.out.println(tokens);
            json.put("model", "text-davinci-003");
            json.put("prompt", prompt);
            json.put("temperature", 0.5);
            json.put("max_tokens", 4096 - tokens);
            RequestBody requestBody = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", String.format("Bearer %s", API_KEY))
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                String responseString = response.body().string();
                System.out.println(responseString);
                JSONObject responseJson = new JSONObject(responseString);
                String completion = responseJson.getJSONArray("choices")
                        .getJSONObject(0).getString("text");
                event.getHook().sendMessageEmbeds(Embeds.questionEmbed(prompt,completion,event.getUser())).queue();

            } catch (IOException e) {
                event.getHook().sendMessage("** Error occurred **").queue();
                e.printStackTrace();
            }

        }
    }

}
