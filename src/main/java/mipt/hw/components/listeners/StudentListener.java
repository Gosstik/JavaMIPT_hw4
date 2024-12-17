package mipt.hw.components.listeners;

import mipt.hw.domain.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentListener {
    void onEvent(Student student);
}
