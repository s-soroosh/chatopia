package ninja.soroosh.chatopia.core.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ninja.soroosh.chatopia.core")
public class ExmpleProgram {
    public static void main(String[] args) {
        SpringApplication.run(ExmpleProgram.class, args);
    }
}
