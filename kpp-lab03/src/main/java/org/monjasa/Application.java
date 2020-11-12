package org.monjasa;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) throws URISyntaxException {

        URI uri = Application.class.getResource("/test.txt").toURI();
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(Path.of(uri))) {
            MappedByteBuffer byteBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
            for (int i = 0; i < fileChannel.size(); i++) {
                System.out.print((char) byteBuffer.get());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
