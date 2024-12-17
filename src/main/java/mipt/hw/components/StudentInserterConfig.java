package mipt.hw.components;

import lombok.AllArgsConstructor;
import mipt.hw.components.listeners.NameCorrectorStudentListener;
import mipt.hw.components.listeners.NameCorrectorStudentListenerImpl;
import mipt.hw.components.listeners.StudentListener;
import mipt.hw.components.listeners.UniversityCorrectorStudentListener;
import mipt.hw.components.listeners.UniversityCorrectorStudentListenerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class StudentInserterConfig {

    @Bean("studentInserter")
    public StudentInserter getStudentInserter() {
        List<StudentListener> listeners = new ArrayList<>() {
            {
                add(nameCorrector);
                add(universityCorrector);
            }
        };

        return new StudentInserter(listeners);
    }

    private final NameCorrectorStudentListener nameCorrector;
    private final UniversityCorrectorStudentListener universityCorrector;
}
