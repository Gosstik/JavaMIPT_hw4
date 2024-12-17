package mipt.hw;

import mipt.hw.service.dao.StudentDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "mipt.hw")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class ApplicationConfig {

    public static final String ALL_LOGS_DIRECTORY_NAME = "logs";
}
