package org.monjasa.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharSequenceLoader {

    public static String loadResourceAsClammedString(URL resource) throws IOException, URISyntaxException {
        try (Stream<String> stringStream = Files.lines(Path.of(resource.toURI()))) {
            return stringStream.collect(Collectors.joining());
        }
    }
}
