package external.projectdocs;

import external.projectdocs.repo.ProjectDocEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ProjectdocsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectdocsApplication.class, args);
    }

}

