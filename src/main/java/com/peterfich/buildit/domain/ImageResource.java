package com.peterfich.buildit.domain;

import com.peterfich.buildit.download.DownLoader;

import java.util.List;

import static java.util.Collections.emptyList;

public class ImageResource extends Resource {

    public ImageResource(String path) {
        super(path);
    }

    @Override
    public List<Resource> crawl(DownLoader downLoader) {
        return emptyList();
    }
}
