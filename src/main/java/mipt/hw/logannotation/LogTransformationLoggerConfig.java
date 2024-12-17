package mipt.hw.logannotation;

import mipt.hw.ApplicationConfig;
import mipt.hw.CustomLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogTransformationLoggerConfig implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, @Nullable String beanName) throws BeansException {
//        System.out.println("inside postProcessAfterInitialization !!!!!!!!!!!!!!!!!!!!");
//        System.out.println("beanName: " + beanName);
        for (Method method: bean.getClass().getDeclaredMethods()) {
            LogTransformation annotation = method.getAnnotation(LogTransformation.class);
            if (annotation != null) {
                String logFilename = annotation.logFilename();
//                System.out.println("---------- found annotation: " + logFilename);
                loggers.computeIfAbsent(logFilename, LogTransformationLoggerConfig::createLogger);
            }
        }

        return bean;
    }

    public static CustomLogger getLogger(String logFilename) {
        return loggers.get(logFilename);
    }

    private static CustomLogger createLogger(String logFilename) {
        String loggerName = "logTransformationLogger_" + logFilename;
        String logsDirectoryName = ApplicationConfig.ALL_LOGS_DIRECTORY_NAME;
        logFilename = logsDirectoryName + "/" + logFilename;

        CustomLogger logger = new CustomLogger(loggerName, logsDirectoryName);
        logger.addFileHandler(new File(logFilename));

        return logger;
    }

    private static final Map<String, CustomLogger> loggers = new HashMap<>();
}
