package demo.hystrix;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class HystrixDemoController {

    @GetMapping("/documents/count/{project}")
    public int documentsCount(@PathVariable String project) {
        RestTemplate restTemplate = new RestTemplate();
        List<ProjectDoc> projectDocs = restTemplate.getForObject(
                "http://localhost:8103/projects/{project}/docs", List.class, project);
        log.info("Got project {} docs: {}", project, projectDocs);
        return projectDocs.size();
    }
}

@Value
class ProjectDoc {
    private String name;
    private String author;
}
