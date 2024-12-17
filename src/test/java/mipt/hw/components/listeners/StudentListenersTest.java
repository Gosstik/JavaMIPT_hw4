package mipt.hw.components.listeners;

import mipt.hw.ApplicationConfig;
import mipt.hw.components.StudentInserter;
import mipt.hw.components.readers.CsvDataReader;
import mipt.hw.components.readers.DataReader;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class StudentListenersTest {
    @Test
    public void unitTest() {
        String dataDirectory = "StudentListenersTest";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);
    }
}