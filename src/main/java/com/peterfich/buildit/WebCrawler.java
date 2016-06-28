package com.peterfich.buildit;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.peterfich.buildit.domain.Connection;
import com.peterfich.buildit.domain.HtmlResource;
import com.peterfich.buildit.domain.Resource;
import com.peterfich.buildit.download.DownLoader;
import com.peterfich.buildit.download.UniRestDownLoader;
import com.peterfich.buildit.format.ConsoleFormatter;

import javax.xml.xpath.XPathExpressionException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {

    private final DownLoader downLoader;

    // TODO: input validation
    public static void main(String[] args) throws XPathExpressionException, UnirestException, URISyntaxException {
        String startPath = args[0];
        List<Connection> connections = new WebCrawler(new UniRestDownLoader()).crawl(startPath);

        new ConsoleFormatter().format(connections, startPath);
    }

    public WebCrawler(DownLoader downLoader) {
        this.downLoader = downLoader;
    }

    public List<Connection> crawl(String startPath) {
        List<Resource> queue = new ArrayList<>();
        Set<Resource> knownResources = new HashSet<>();

        HtmlResource startingPoint = new HtmlResource(startPath);
        queue.add(startingPoint);
        knownResources.add(startingPoint);

        List<Connection> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Resource resource = queue.remove(0);
            System.out.println("Getting " + resource.getPath());
            System.out.println("Queue size:" + queue.size());

            List<Resource> connectedResources = resource.crawl(downLoader);

            for (Resource connectedResource : connectedResources) {
                if (knownResources.add(connectedResource)) {
                    queue.add(connectedResource);
                }
                result.add(new Connection(resource.getPath(), connectedResource.getPath()));
            }
        }

        return result;
    }
}
