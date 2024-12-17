package mipt.hw.components.validators;

import mipt.hw.ApplicationConfig;
import mipt.hw.CustomLogger;

import java.io.File;

public class InvalidStudentsLoggerConfig {

    public static CustomLogger getLogger() {
        String loggerName = "invalidStudentsLogger";
        String logsDirectoryName = ApplicationConfig.ALL_LOGS_DIRECTORY_NAME;
        String logFilename = logsDirectoryName + "/invalidStudents.txt";

        CustomLogger logger = new CustomLogger(loggerName, logsDirectoryName);
        logger.addFileHandler(new File(logFilename));

        return logger;
    }
}
