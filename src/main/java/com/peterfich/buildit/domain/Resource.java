package com.peterfich.buildit.domain;

import com.peterfich.buildit.download.DownLoader;

import java.util.List;

public abstract class Resource {

    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public abstract List<Resource> crawl(DownLoader downLoader);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return path != null ? path.equals(resource.path) : resource.path == null;

    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
