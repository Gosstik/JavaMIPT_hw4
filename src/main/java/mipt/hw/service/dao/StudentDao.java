package mipt.hw.service.dao;

import lombok.AllArgsConstructor;
import mipt.hw.domain.Student;
import mipt.hw.domain.University;
import mipt.hw.service.db.Database;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Collection;

@Component
@AllArgsConstructor
public class StudentDao {

    public void insertStudents(Collection<Student> students) throws SQLException {
        source.preparedStatement("""
            insert into students(id, fio, email, phone_number, university_id)
            values (?, ?, ?, ?, ?)""", insertStudent -> {
            for (Student student : students) {
                insertStudent.setInt(1, student.getId());

                String fio = String.join(" ",
                        student.getLastName(),
                        student.getFirstName(),
                        student.getPatronymic());
                insertStudent.setString(2, fio);

                insertStudent.setString(3, student.getEmail());

                insertStudent.setString(4, student.getPhoneNumber());

                University university = University.getFromNameToUniversity().get(student.getUniversityName());
                insertStudent.setInt(5, university.getId());

                insertStudent.execute();
            }
        });
    }

    private final Database source;
}
