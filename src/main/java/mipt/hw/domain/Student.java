package mipt.hw.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonAutoDetect // For logging.
public class Student {
    public static Student createFromMap(Map<String, String> map) {
        int id = idCounter;
        ++idCounter;
        String lastName = map.get("Фамилия");
        String firstName = map.get("Имя");
        String patronymic = map.get("Отчество");
        String phoneNumber = map.get("Номер телефона");
        String email = map.get("Email");
        String universityName = map.get("Учебное заведение");
        return new Student(id, lastName, firstName, patronymic, phoneNumber, email, universityName);
    }

    private static int idCounter = 1;

    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private String universityName;
}
