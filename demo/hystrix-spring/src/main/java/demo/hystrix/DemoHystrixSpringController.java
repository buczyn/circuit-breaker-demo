package demo.hystrix;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class DemoHystrixSpringController {

    @Autowired
    private ProjectDocsClient projectDocsClient;

    @GetMapping("/documents/count/{project}")
    public int documentsCount(@PathVariable String project) {
        log.info("--------");
        List<ProjectDoc> projectDocs = projectDocsClient.getProjectDocs(project);
        return projectDocs.size();
    }
}

@Value
class ProjectDoc {
    private String name;
    private String author;
}
