package mipt.hw.logannotation;

import mipt.hw.CustomLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class LogTransformationAspect {
    @Around("@annotation(LogTransformation)")
    public static Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get file where to log.
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] argsClasses = Arrays.stream(joinPoint.getArgs()).map(Object::getClass).toArray(Class[]::new);
        Method m = joinPoint.getTarget().getClass().getMethod(methodName, argsClasses);
        String logFilename = m.getAnnotation(LogTransformation.class).logFilename();

        // Call method.
        String beginTime = DTF.format(LocalDateTime.now());
        long start = System.nanoTime();

        Object retVal = joinPoint.proceed();

        long finish = System.nanoTime();
        String endTime = DTF.format(LocalDateTime.now());

        // Get duration.
        Duration nanoElapsedTime = Duration.ofNanos(finish - start);
        String elapsedTime = TimeUnit.MICROSECONDS.convert(nanoElapsedTime) + " mcs";

        // Get class name, input and output data.
        String className = joinPoint.getTarget().getClass().getName();
        String inputData = Arrays.toString(joinPoint.getArgs());

        String outputData;
        if (m.getReturnType() == void.class) {
            outputData = "void";
        } else if (retVal == null) {
            outputData = "null";
        } else {
            outputData = retVal.toString();
        }

        // Make log.
        String logMessage = """
                
                Class name: %s.
                Operation start: %s.
                Operation finished: %s.
                Elapsed time: %s.
                Input data: %s.
                Output data: %s
                """.formatted(className, beginTime, endTime, elapsedTime, inputData, outputData);
        CustomLogger logger = LogTransformationLoggerConfig.getLogger(logFilename);
        logger.info(logMessage);

        return retVal;
    }

    // Example: 2023-12-24 15:14:55
    static private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
}
