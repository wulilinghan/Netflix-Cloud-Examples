package top.b0x0.cloud.netflix.provider1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ManJiis
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class CloudProvider1Application {
    private static final Logger log = LoggerFactory.getLogger(CloudProvider1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(CloudProvider1Application.class, args);
    }

}
