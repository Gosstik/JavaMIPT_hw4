package mipt.hw.service.dao;

//create table universities (
//        id INT not null primary key,
//        name VARCHAR not null,
//        primary key (id)
//        );
//
//create table students (
//        id INT not null,
//        fio VARCHAR not null,
//        email VARCHAR not null,
//        phone_number VARCHAR not null,
//        university_id INT not null,
//        primary key (id),
//        foreign key (university_id) references universities(id)
//        );

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mipt.hw.domain.Student;
import mipt.hw.domain.University;
import mipt.hw.service.db.Database;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class UniversityDao {

    @PostConstruct
    void init() {
        try {
//            insertUniversities(List.of(new University(100, "TestUniverName")));
            insertUniversities(University.getFromNameToUniversity().values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUniversities(Collection<University> universities) throws SQLException {
        source.preparedStatement("""
            insert into universities(id, name)
            values (?, ?)""", insertUniversity -> {
            for (University university : universities) {
                insertUniversity.setInt(1, university.getId());
                insertUniversity.setString(2, university.getName());
                insertUniversity.execute();
            }
        });
    }

    private final Database source;
}
