package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.io.IOException;
import java.util.List;

/** @noinspection UnresolvedClassReferenceRepair*/
@Service
public class ChucknorrisJokeService {

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;

    public String getChucknorrisJokeId(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=" + keyword)
                .method("GET", null)
                .addHeader("authority", "api.chucknorris.io")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_ga=GA1.2.1933386174.1727263884; _gid=GA1.2.1968446145.1727263884; _gat=1; _ga_JRZZDJK0D7=GS1.2.1727263884.1.0.1727263884.0.0.0")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();

        AllData res = om.readValue(response.body().string(), AllData.class);
       ArrayList<String> valueJoke = new ArrayList<>();
       for ( joke joke : res.getResult())
      valueJoke.add(joke.getvalue());
        String result = String.join("\n", valueJoke);
        return result;
        }

    static class AllData {

        List<joke>  result;

        public List<joke> getResult() {
            return result;
        }
    }

    static class joke{

        public String getvalue() {
            return value;
        }
        String value;


    }




}