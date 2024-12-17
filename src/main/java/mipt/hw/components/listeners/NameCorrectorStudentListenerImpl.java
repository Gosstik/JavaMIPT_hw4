package mipt.hw.components.listeners;

import lombok.AllArgsConstructor;
import mipt.hw.components.correctors.NameCorrector;
import mipt.hw.domain.Student;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NameCorrectorStudentListenerImpl implements NameCorrectorStudentListener {

    @Override
    public void onEvent(Student student) {
        String correctName = NameCorrector.correctName(student.getLastName());
        student.setLastName(correctName);

        correctName = NameCorrector.correctName(student.getFirstName());
        student.setFirstName(correctName);

        correctName = NameCorrector.correctName(student.getPatronymic());
        student.setPatronymic(correctName);
    }
}
