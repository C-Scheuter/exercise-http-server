package nl.han.dea.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HtmlPageReader {
    public String readFile(String filename) {
        var fullFileName = "pages/".concat(filename);
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            var file = new File(classLoader.getResource(fullFileName).getFile()).toPath();
            return new String(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String setLength(String filename) {

        String fileToString = readFile(filename);
        return Integer.toString(fileToString.getBytes(StandardCharsets.UTF_8).length);

    }
}