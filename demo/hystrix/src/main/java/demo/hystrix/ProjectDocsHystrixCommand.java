package demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class ProjectDocsHystrixCommand extends HystrixCommand<List<ProjectDoc>> {

    private String project;

    public ProjectDocsHystrixCommand(String project) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProjectDocs"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerRequestVolumeThreshold(5)
                                .withCircuitBreakerSleepWindowInMilliseconds(10000)));
        this.project = project;
    }

    @Override
    protected List<ProjectDoc> run() {
        RestTemplate restTemplate = new RestTemplate();
        List<ProjectDoc> response = restTemplate.getForObject("http://localhost:8103/projects/{project}/docs", List.class, project);
        log.info("Got project {} docs: {}", project, response);
        return response;
    }
}
