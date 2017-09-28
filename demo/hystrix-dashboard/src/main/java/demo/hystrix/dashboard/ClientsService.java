package demo.hystrix.dashboard;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@DefaultProperties(commandProperties = {
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")})
public class ClientsService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @HystrixCommand(commandKey = "GetToken", fallbackMethod = "tokenFallback")
    public String getToken(String user) {
        log.info("Retrieving token for {}", user);
        RestTemplate restTemplate = restTemplateBuilder.build();
        Token token = restTemplate.getForObject("http://localhost:8101/token?user={user}", Token.class, user);
        return token.getToken();
    }

    @HystrixCommand(commandKey = "GetMyDocuments", fallbackMethod = "myDocumentsFallback")
    public MyDocuments getMyDocuments(List<String> projects, String token) {
        List<Project> projs = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();
        for (String project : projects) {
            log.info("Retrieving documents for project {}", project);
            List<Map<String, String>> documents = restTemplate.getForObject(
                    "http://localhost:8103/sec/projects/{project}/docs?token={token}", List.class, project, token);
            log.info("Got documents for project {}: {}", project, documents);
            projs.add(new Project(project, documents.stream().map(
                    d -> new Document(
                            d.get("name"),
                            d.get("author")))
                    .collect(Collectors.toList())));
        }

        return new MyDocuments(projs);
    }

    public String tokenFallback(String user) {
        return "000";
    }

    public MyDocuments myDocumentsFallback(List<String> projects, String token) {
        return new MyDocuments(projects.stream()
                .map(p -> new Project(p, Collections.emptyList())).collect(Collectors.toList()));
    }
}

@Value
class Token {
    private String token;
}
