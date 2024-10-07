package com.handson.chatbot.controller;


import com.handson.chatbot.service.AmazonService;
import com.handson.chatbot.service.ChucknorrisJokeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    AmazonService amazonService;
    @Autowired
    ChucknorrisJokeService cnService;

    @RequestMapping(value = "/amazon", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(amazonService.searchProducts(keyword), HttpStatus.OK);
    }

        @RequestMapping(value = "/ChucknorrisJoke", method = RequestMethod.GET)
        public ResponseEntity<?> getChucknorrisJoke (@RequestParam String keyword) throws IOException {
            return new ResponseEntity<>(cnService.getChucknorrisJokeId(keyword), HttpStatus.OK);
        }

    @RequestMapping(value = "", method = { RequestMethod.POST})
    public ResponseEntity<?> getBotResponse(@RequestBody BotQuery query) throws IOException {
        HashMap<String, String> params = query.getQueryResult().getParameters();
        String res;
        if (params.containsKey("word")) {
            res = cnService.getChucknorrisJokeId(params.get("word"));
        } else if (params.containsKey("product"))
            res = amazonService.searchProducts(params.get("product"));
            else         res ="Not Found";


        return new ResponseEntity<>(BotResponse.of(res), HttpStatus.OK);
    }


    static class BotQuery {
        QueryResult queryResult;

        public QueryResult getQueryResult() {
            return queryResult;
        }
    }

    static class QueryResult {
        HashMap<String, String> parameters;

        public HashMap<String, String> getParameters() {
            return parameters;
        }
    }

    static class BotResponse {
        String fulfillmentText;
        String source = "BOT";

        public  String getFulfillmentText() {
            return fulfillmentText;
        }

        public String getSource() {
            return source;
        }

        public static BotResponse of(String fulfillmentText) {
            BotResponse res = new BotResponse();
            res.fulfillmentText = fulfillmentText;
            return res;
        }
    }
}


