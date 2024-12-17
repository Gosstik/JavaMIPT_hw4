package mipt.hw;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class CustomLogger {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");
        FORMATTER = new SimpleFormatter();
    }

    public CustomLogger(String loggerName, String logsDirectoryName) {
        LOGGER = Logger.getLogger(loggerName);
        LOGGER.setUseParentHandlers(false);

        LOGS_DIRECTORY_NAME = logsDirectoryName;

        createLogDir(LOGS_DIRECTORY_NAME);
    }

    public String getLogsDirName() {
        return LOGS_DIRECTORY_NAME;
    }

    public void removeHandlers() {
        LOGGER.setUseParentHandlers(false);
    }

    public void addFileHandler(File file) {
        Handler fh = null;
        try {
            fh = new FileHandler(file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fh.setFormatter(FORMATTER);
        LOGGER.addHandler(fh);
    }

    public void addConsoleHandler() {
        SimpleFormatter fmt = new SimpleFormatter();
        StreamHandler sh = new StreamHandler(System.out, fmt);
        LOGGER.addHandler(sh);
    }

    public void info(String string) {
        LOGGER.info(string);
    }

    public void warning(String string) {
        LOGGER.log(Level.WARNING, string);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////                       Internals.                           ////////
    ////////////////////////////////////////////////////////////////////////////

    private static void createLogDir(String dirPathStr) {
        Path dirPath = Paths.get(dirPathStr);
        if (Files.isDirectory(dirPath)) {
            return;
        }

        boolean dirCreated = new File(dirPathStr).mkdirs();
        if (!dirCreated) {
            throw new RuntimeException(
                    "Unable to create directory \"" + dirPathStr + "\"");
        }
    }

    private static final SimpleFormatter FORMATTER;

    private final String LOGS_DIRECTORY_NAME;
    private final Logger LOGGER;
}
