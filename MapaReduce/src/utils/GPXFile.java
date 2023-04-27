package utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GPXFile implements Serializable {
    private final String filename;
    private final byte[] content;

    public GPXFile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        this.filename = path.getFileName().toString();
        this.content = Files.readAllBytes(path);
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentAsString(){
        return new String(this.getContent(), StandardCharsets.UTF_8);
    }
}
