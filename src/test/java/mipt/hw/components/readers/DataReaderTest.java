package mipt.hw.components.readers;

import mipt.hw.ApplicationConfig;
import mipt.hw.components.StudentInserter;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class DataReaderTest {

    @Test
    void readAllLines() {
        String dataDirectory = "readAllLines";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);
    }

    @Test
    void readNextLine() {
        String dataDirectory = "readNextLine";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);
    }
}