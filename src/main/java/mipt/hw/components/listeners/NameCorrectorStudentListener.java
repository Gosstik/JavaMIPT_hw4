package mipt.hw.components.listeners;


import mipt.hw.domain.Student;

public interface NameCorrectorStudentListener extends StudentListener {
    void onEvent(Student student);
}
