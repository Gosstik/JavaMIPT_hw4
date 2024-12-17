package mipt.hw;

import mipt.hw.components.StudentInserter;
import mipt.hw.components.readers.CsvDataReader;
import mipt.hw.components.readers.DataReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        String dataDirectory = "data";

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentInserter studentInserter = ctx.getBean(StudentInserter.class);
        DataReader dataReader = new CsvDataReader(dataDirectory);
        studentInserter.insertStudents(ctx, dataReader);

        System.out.println("Main finished successfully");
    }
}
