package web;

import cn.hutool.core.codec.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dkw
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        String basicAuthorization = "user:3f405ded-2d0d-4748-9f60-4b436929e46e";
        System.out.println(Base64.encode(basicAuthorization));
        SpringApplication.run(DemoApplication.class, args);
    }
}
