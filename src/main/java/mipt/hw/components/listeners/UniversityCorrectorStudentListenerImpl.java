package mipt.hw.components.listeners;

import mipt.hw.components.correctors.UniversityCorrector;
import mipt.hw.domain.Student;
import mipt.hw.logannotation.LogTransformation;
import org.springframework.stereotype.Component;

@Component
public class UniversityCorrectorStudentListenerImpl implements UniversityCorrectorStudentListener {

    @Override
    @LogTransformation(logFilename = "LogTransformation_test")
    public void onEvent(Student student) {
        String university = UniversityCorrector.getUniversityName(student.getUniversityName());
        if (university == null) {
            return;
        }
        student.setUniversityName(university);
    }
}
