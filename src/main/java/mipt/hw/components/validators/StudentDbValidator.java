package mipt.hw.components.validators;

import mipt.hw.domain.Student;

public interface StudentDbValidator {
    void setStudent(Student student);
    void validate() throws IllegalStateException;
    void logInvalid(String comment);
}
