package in.arya;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by dev on 07/04/18.
 */

@EnableAutoConfiguration
@EnableMongoRepositories
@SpringBootApplication
@ComponentScan(basePackages = { "in.arya" })
public class AuditMain {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
