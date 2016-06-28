package com.peterfich.buildit;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.peterfich.buildit.domain.HtmlResource;
import com.peterfich.buildit.domain.ImageResource;
import com.peterfich.buildit.domain.Resource;
import com.peterfich.buildit.download.DownLoader;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlResourceTest {

    @Test
    public void shouldFindLinkResource() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"http://wiprodigital.com/\" title=\"Digital Transformation &#8211; Wipro Digital\" rel=\"home\">";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(1);
        assertThat(resources.get(0)).isInstanceOf(HtmlResource.class);
        assertThat(resources.get(0).getPath()).isEqualTo("http://wiprodigital.com/");
    }

    @Test
    public void shouldFindImageResource() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<img src=\"http://wiprodigital.com/wp-content/uploads/2015/09/WD_logo_150X27.png\">";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(1);
        assertThat(resources.get(0)).isInstanceOf(ImageResource.class);
        assertThat(resources.get(0).getPath()).isEqualTo("http://wiprodigital.com/wp-content/uploads/2015/09/WD_logo_150X27.png");
    }

    @Test
    public void shouldFindBothResource() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"http://wiprodigital.com/\"><img src=\"/wp-content/uploads/2015/09/WD_logo_150X27.png\"></a>";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(2);
        assertThat(resources.get(0)).isInstanceOf(HtmlResource.class);
        assertThat(resources.get(0).getPath()).isEqualTo("http://wiprodigital.com/");
        assertThat(resources.get(1)).isInstanceOf(ImageResource.class);
        assertThat(resources.get(1).getPath()).isEqualTo("http://wiprodigital.com//wp-content/uploads/2015/09/WD_logo_150X27.png");
    }

    @Test
    public void shouldRemoveAnchor() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"http://wiprodigital.com#anchor/\" >Click here</a>";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(1);
        assertThat(resources.get(0)).isInstanceOf(HtmlResource.class);
        assertThat(resources.get(0).getPath()).isEqualTo("http://wiprodigital.com");
    }

    @Test
    public void shouldExpandHref() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"newPage.html\" >Click here</a>";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(1);
        assertThat(resources.get(0)).isInstanceOf(HtmlResource.class);
        assertThat(resources.get(0).getPath()).isEqualTo("http://wiprodigital.com/newPage.html");
    }

    @Test
    public void shouldSkipLinksOffHost() throws UnirestException {
        DownLoader downLoader = new DownLoader() {
            @Override
            public String getContentAsString(String path) {
                return "<a href=\"http://some-other-side.com\" >Click here</a>";
            }
        };

        HtmlResource htmlResource = new HtmlResource("http://wiprodigital.com/");
        List<Resource> resources = htmlResource.crawl(downLoader);

        assertThat(resources).hasSize(0);
    }
}
