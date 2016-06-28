package com.peterfich.buildit;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.peterfich.buildit.domain.Connection;
import com.peterfich.buildit.domain.Resource;
import com.peterfich.buildit.download.DownLoader;
import org.junit.Test;

import javax.xml.xpath.XPathExpressionException;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebCrawlerTest {

    private String fromPath = "http://wiprodigital.com/from-path";
    private String toPath = "http://wiprodigital.com/to-path";

    private DownLoader downLoader = mock(DownLoader.class);

    @Test
    public void shouldAddNoConnectionsToSiteMapIfResourceHasNoConnections() throws XPathExpressionException, UnirestException, URISyntaxException {
        when(downLoader.getContentAsString(fromPath)).thenReturn("");

        WebCrawler webCrawler = new WebCrawler(downLoader);
        List<Connection> connections = webCrawler.crawl(fromPath);

        assertThat(connections).hasSize(0);
    }

    @Test
    public void shouldDownloadThePassedInPath() {
        when(downLoader.getContentAsString(fromPath)).thenReturn("");

        WebCrawler webCrawler = new WebCrawler(downLoader);
        webCrawler.crawl(fromPath);

        verify(downLoader).getContentAsString(fromPath);
    }

    @Test
    public void shouldReturnConnections() {
        String imageUrl = "http://wiprodigital.com/image.jpg";
        when(downLoader.getContentAsString(fromPath)).thenReturn("<img src=\"" + imageUrl + "\" >");

        Resource fromResource = mock(Resource.class);
        when(fromResource.getPath()).thenReturn(fromPath);

        Resource toResource = mock(Resource.class);
        when(toResource.getPath()).thenReturn(toPath);

        when(fromResource.crawl(downLoader)).thenReturn(singletonList(toResource));

        WebCrawler webCrawler = new WebCrawler(downLoader);
        List<Connection> connections = webCrawler.crawl(fromPath);

        verify(downLoader).getContentAsString(fromPath);

        assertThat(connections).hasSize(1);
        assertThat(connections.get(0).getFrom()).isEqualTo(fromPath);
        assertThat(connections.get(0).getTo()).isEqualTo(imageUrl);
    }
}
