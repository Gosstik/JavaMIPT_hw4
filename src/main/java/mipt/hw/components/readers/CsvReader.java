package mipt.hw.components.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public CsvReader(String filepath) {
        this.filepath = filepath;
        lineBr = getBufferedReader();

        String columnNames = getNextLine(lineBr);
        this.columnNames = columnNames.split(DELIMITER);
    }

    public List<Map<String, String>> readAllLines() {
        List<Map<String, String>> res = new ArrayList<>();
        BufferedReader br = getBufferedReader();

        String line;
        while (true) {
            line = getNextLine(br);
            if (line == null) {
                break;
            }
            String[] values = line.split(DELIMITER);

            res.add(mapWithColumns(values));
        }

        return res;
    }

    /**
     * @return The next line from the file without trailing newline, or null if there is no more input.
     */
    public Map<String, String> readNextLine() {
        String line = getNextLine(lineBr);
        if (line == null) {
            return null;
        }
        String[] values = line.split(DELIMITER);

        return mapWithColumns(values);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////                       Internals.                           ////////
    ////////////////////////////////////////////////////////////////////////////

    private BufferedReader getBufferedReader() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return br;
    }

    private String getNextLine(BufferedReader br) {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    private Map<String, String> mapWithColumns(String[] values) {
        Map<String, String> res = new HashMap<>();
        for (int i = 0; i < values.length; ++i) {
            res.put(columnNames[i], values[i]);
        }
        return res;
    }

    private final String DELIMITER = ",";

    private final String filepath;
    private final BufferedReader lineBr;
    private final String[] columnNames;
}
