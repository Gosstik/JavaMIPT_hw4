package mipt.hw.components.correctors;

import mipt.hw.domain.Student;

public class StudentFirstNameCorrector implements StudentCorrector {

    @Override
    public Object getCorrected(Student student) {
        return NameCorrector.correctName(student.getFirstName());
    }
}
