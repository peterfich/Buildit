package com.peterfich.buildit;

public class WebCrawler {


    public static void main(String[] args) {
        new WebCrawler().crawl(args[0]);
    }

    private void crawl(String arg) {
        System.out.println("exec is working, crawling: " + arg);
    }
}
