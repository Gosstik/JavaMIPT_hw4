package mipt.hw.components.readers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvDataReader implements DataReader {

    public CsvDataReader(String dirName) {
        this.dirName = dirName;
        validateDirectory();
        File dir = new File(dirName);

        files = dir.listFiles(file -> Files.isRegularFile(file.toPath()));
        if (files == null) {
            throw new RuntimeException("Unable to read files from directory.");
        }

        curFile = 0;

        lineReader = new CsvReader(files[curFile].getAbsolutePath());
    }

    @Override
    public List<Map<String, String>> readAllLines() {
        List<Map<String, String>> res = new ArrayList<>();

        File dir = new File(dirName);
        File[] files = dir.listFiles(file -> Files.isRegularFile(file.toPath()));
        if (files == null) {
            throw new RuntimeException("Unable to read files from directory.");
        }

        for (File file: files) {
            CsvReader reader = new CsvReader(file.getAbsolutePath());
            res.addAll(reader.readAllLines());
        }

        return res;
    }

    @Override
    public Map<String, String> readNextLine() {
        Map<String, String> line = lineReader.readNextLine();
        while (line == null) {
            ++curFile;
            if (files.length == curFile) {
                return null;
            }
            lineReader = new CsvReader(files[curFile].getAbsolutePath());
            line = lineReader.readNextLine();
        }
        return line;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////                       Internals.                           ////////
    ////////////////////////////////////////////////////////////////////////////

    private void validateDirectory() {
        Path path = Paths.get(dirName);
        if (!Files.exists(path)) {
            throw new RuntimeException("Directory '%s' does not exists.".formatted(dirName));
        }

        if (!Files.isDirectory(path)) {
            throw new RuntimeException("'%s' is not a directory.".formatted(dirName));
        }
    }

    private final String dirName;
    private final File[] files;
    private int curFile;
    private CsvReader lineReader;
}
