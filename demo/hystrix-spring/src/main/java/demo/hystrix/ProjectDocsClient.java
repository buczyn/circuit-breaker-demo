package demo.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ProjectDocsClient {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="3"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="10000")
    })
    public List<ProjectDoc> getProjectDocs(String project) {
        log.info("entering getProjectsDocs");
        RestTemplate restTemplate = new RestTemplate();
        List<ProjectDoc> response = restTemplate.getForObject(
                "http://localhost:8103/projects/{project}/docs", List.class, project);
        log.info("Got project {} docs: {}", project, response);
        return response;
    }
}
