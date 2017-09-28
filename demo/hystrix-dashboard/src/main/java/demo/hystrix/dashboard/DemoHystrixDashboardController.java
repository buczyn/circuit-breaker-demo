package demo.hystrix.dashboard;

import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class DemoHystrixDashboardController {

    @Autowired
    private ClientsService service;

    @GetMapping("/{user}/mydocuments")
    public MyDocuments myDocuments(@PathVariable String user, @RequestParam("project") List<String> projects) {
        log.info("Documents of {} for projects {}", user, projects);

        String token = service.getToken(user);
        MyDocuments myDocuments = service.getMyDocuments(projects, token);
        log.info("My documents: {}", myDocuments);
        return myDocuments;
    }
}

@Value
class MyDocuments {
    private List<Project> projects;
}

@Value
class Project {
    private String name;
    private List<Document> documents;
}

@Value
class Document {
    private String name;
    private String author;
}

