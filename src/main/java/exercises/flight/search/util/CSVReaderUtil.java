package exercises.flight.search.util;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class CSVReaderUtil {

    public static Reader getFileReader(String pathName, String fileName) throws IOException {
        try {
            Path path = Paths.get(pathName, fileName);
            return Files.newBufferedReader(path, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            throw ex;
        }
    }
}
