package org.monjasa.lab03.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

@Slf4j
class ResourceLoaderTest {

    @Test
    void shouldLoadResourceAsString() throws IOException, URISyntaxException {

        URL resource = getClass().getClassLoader().getResource("test.txt");
        URI resourceUri = Objects.requireNonNull(resource).toURI();

        String result = ResourceLoader.loadResource(resourceUri);

        assertThat(result).isNotNull()
                .isEqualTo(String.join("", linesOf(resource)));

        log.info(result);
    }
}