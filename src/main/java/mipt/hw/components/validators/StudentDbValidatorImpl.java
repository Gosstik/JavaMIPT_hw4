package mipt.hw.components.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import mipt.hw.CustomLogger;
import mipt.hw.components.correctors.NameCorrector;
import mipt.hw.components.correctors.UniversityCorrector;
import mipt.hw.domain.Student;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class StudentDbValidatorImpl implements StudentDbValidator {

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
//    @LogTransformation
    public void validate() throws IllegalStateException {
        String correctName = NameCorrector.correctName(student.getLastName());
        if (!student.getLastName().equals(correctName)) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid last name.");
        }

        correctName = NameCorrector.correctName(student.getFirstName());
        if (!student.getFirstName().equals(correctName)) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid first name.");
        }

        correctName = NameCorrector.correctName(student.getPatronymic());
        if (!student.getPatronymic().equals(correctName)) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid patronymic.");
        }

        if (!PHONE_PATTERN.matcher(student.getPhoneNumber()).matches()) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid phone number.");
        }

        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid email.");
        }

        correctName = UniversityCorrector.getUniversityName(student.getUniversityName());
        if (correctName == null || !correctName.equals(student.getUniversityName())) {
            throw new IllegalStateException("StudentDbValidatorImpl: invalid university name.");
        }
    }

    @Override
//    @LogTransformation
    public void logInvalid(String comment) {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(writer, student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String studentData = writer.toString();

        String logMessage = """
                Error: %s
                Student Data: %s""".formatted(comment, studentData);

        INVALID_STUDENTS_LOGGER.info(logMessage);
    }

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+7[0-9]{10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+$");
    private static final CustomLogger INVALID_STUDENTS_LOGGER = InvalidStudentsLoggerConfig.getLogger();

    private Student student;
}
