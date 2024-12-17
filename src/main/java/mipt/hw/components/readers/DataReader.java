package mipt.hw.components.readers;

import java.util.List;
import java.util.Map;

public interface DataReader {
   List<Map<String, String>> readAllLines();
   Map<String, String> readNextLine();
}
