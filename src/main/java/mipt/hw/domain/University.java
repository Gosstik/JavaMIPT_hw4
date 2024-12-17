package mipt.hw.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonAutoDetect // For logging.
public class University {
    public static University createFromMap(Map<String, String> map) {
        int id = idCounter;
        ++idCounter;
        String name = map.get("Имя");
        return new University(id, name);
    }

    private static int idCounter = 1;
    @Getter
    private static final Map<String, String> universities = new HashMap<>() {
        {
            put("имэс", "ИМЭС");
            put("мти", "МТИ");
            put("миту", "МИТУ");
            put("мгту", "МГТУ");
            put("мфти", "МФТИ");
            put("ранхигс", "РАНХиГС");
            put("вшэ", "ВШЭ");
            put("мисис", "МИСИС");
        }
    };
    @Getter
    private static final Map<String, University> fromNameToUniversity = new HashMap<>() {
        {
            for (String universityName: universities.values()) {
                put(universityName, new University(idCounter, universityName));
                ++idCounter;
            }
        }
    };

    private int id;
    private String name;
}
