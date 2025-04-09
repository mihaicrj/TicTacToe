package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class AiRequest {

    private static AiRequest instance;
    private AiRequest(){}
    public static AiRequest getInstance()
    {
        if(instance==null)
            instance=new AiRequest();
        return instance;
    }
    public String getResponse(String prompt)throws IOException
    {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "AIzaSyBxOuhsAxPVqmkRA_m-vkrail0jdYJBwUs"; // Obține de la Google Cloud Console
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent\n";

        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\""+prompt+"\"}]}]}";

        Request request = new Request.Builder()
                .url(url + "?key=" + apiKey)
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            JsonObject result = JsonParser.parseString(response.body().string()).getAsJsonObject();
            return result.get("candidates").getAsJsonArray().get(0).getAsJsonObject().get("content").getAsJsonObject().get("parts").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
        }
    }
}
