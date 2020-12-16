package org.monjasa.lab03.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ResourceLoader {
    public static String loadResource(URI resourceUri) throws IOException {
        return Files.lines(Path.of(resourceUri))
                .collect(Collectors.joining());
    }
}
