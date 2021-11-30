package top.newpointer.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync//开启异步
public class FarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmApplication.class, args);
    }

}
