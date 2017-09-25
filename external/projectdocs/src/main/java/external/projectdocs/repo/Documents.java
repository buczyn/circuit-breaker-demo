package external.projectdocs.repo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "documents")
@Data
@NoArgsConstructor
public class Documents {
    private Map<String, List<ProjectDocEntity>> projects;
}
