package mipt.hw.components;

import lombok.AllArgsConstructor;
import mipt.hw.components.listeners.StudentListener;
import mipt.hw.components.readers.DataReader;
import mipt.hw.components.validators.StudentDbValidator;
import mipt.hw.domain.Student;
import mipt.hw.service.dao.StudentDao;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class StudentInserter {

    public void insertStudents(ApplicationContext ctx, DataReader dataReader) {
        StudentDao studentDao = ctx.getBean(StudentDao.class);
        StudentDbValidator validator = ctx.getBean(StudentDbValidator.class);

        Map<String, String> studentMap;
        while ((studentMap = dataReader.readNextLine()) != null) {
            Student student = Student.createFromMap(studentMap);

            for (StudentListener listener: listeners) {
                listener.onEvent(student);
            }

            validator.setStudent(student);
            try {
                validator.validate();
                studentDao.insertStudents(List.of(student));
            } catch (SQLException | IllegalStateException e) {
                validator.logInvalid(e.getMessage());
            }
        }
    }

    private final List<StudentListener> listeners;
}
