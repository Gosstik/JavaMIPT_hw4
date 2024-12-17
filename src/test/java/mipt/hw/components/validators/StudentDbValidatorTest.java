package mipt.hw.components.validators;

import mipt.hw.ApplicationConfig;
import mipt.hw.components.StudentInserter;
import mipt.hw.components.readers.CsvDataReader;
import mipt.hw.components.readers.DataReader;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class StudentDbValidatorTest {

    @Test
    void validate() {
        String dataDirectory = "validate";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);
    }

    @Test
    void logInvalid() {
        String dataDirectory = "logInvalid";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);
    }
}