package com.peterfich.buildit.domain;

import com.peterfich.buildit.download.DownLoader;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageResourceTest {

    @Test
    public void shouldReturnEmptyListOf() {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"http://wiprodigital.com/\" title=\"Digital Transformation &#8211; Wipro Digital\" rel=\"home\">";
            }
        };

        ImageResource imageResource = new ImageResource("http://wiprodigital.com/image.jpg");
        List<Resource> resources = imageResource.crawl(downLoader);

        assertThat(resources).hasSize(0);
    }
}
