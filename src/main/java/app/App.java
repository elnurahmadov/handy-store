package app;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class App {
    @Value("dezhtscuq")
    private String cloudName;

    @Value("411598337988761")
    private String apiKey;

    @Value("xIHEMP3cYl0R2V6XkbqZcDDSh0g")
    private String apiSecret;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        return new Cloudinary(config);
    }
}
