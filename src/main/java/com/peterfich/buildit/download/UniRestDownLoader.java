package com.peterfich.buildit.download;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.peterfich.buildit.WebCrawlerException;

public class UniRestDownLoader implements DownLoader {

    @Override
    public String getContentAsString(String path) {
        try {
            return Unirest.get(path).asString().getBody();
        } catch (UnirestException e) {
            throw new WebCrawlerException(e);
        }
    }
}
