package com.peterfich.buildit.domain;

import com.peterfich.buildit.download.DownLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlResource extends Resource {

    public HtmlResource(String path) {
        super(path);
    }

    @Override
    public List<Resource> crawl(DownLoader downLoader) {
        List<Resource> resources = new ArrayList<>();
        String body = downLoader.getContentAsString(getPath());

        addLinkResources(resources, body);
        addImageResources(resources, body);

        return resources;
    }

    private void addLinkResources(List<Resource> resources, String body) {
        Pattern pattern = Pattern.compile(".*<a.* href=\"(\\S*)\".*>.*");
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            String href = matcher.group(1);
            int firstHash = href.indexOf('#');
            if (firstHash != -1) {
                href = href.substring(0, firstHash);
            }
            if (href.startsWith("http")) {
                if (isHostMatching(getPath(), href)) {
                    resources.add(new HtmlResource(href));
                }
            } else{
                resources.add(new HtmlResource(getPath() + href));
            }
        }
    }

    private void addImageResources(List<Resource> resources, String body) {
        Pattern pattern = Pattern.compile(".*<img.* src=\"(\\S*)\".*>.*");
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            String src = matcher.group(1);
            if (src.startsWith("http")) {
                if (isHostMatching(getPath(), src)) {
                    resources.add(new ImageResource(src));
                }
            } else{
                resources.add(new ImageResource(getPath() + src));
            }
        }
    }

    private boolean isHostMatching(String path, String href) {
        return getSchemaAndHost(path).equals(getSchemaAndHost(href));
    }

    private String getSchemaAndHost(String path) {
        int index = path.indexOf('/', 7);
        if (index == -1)  {
            return path;
        }
        return path.substring(0, index);
    }
}
