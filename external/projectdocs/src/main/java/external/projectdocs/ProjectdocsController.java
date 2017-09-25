package external.projectdocs;

import external.projectdocs.repo.ProjectDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProjectdocsController {

    @Autowired
    private ProjectDocRepository docsRepo;

    @GetMapping("/projects/{project}/docs")
    public List<ProjectDoc> allDocuments(@PathVariable String project) {
        return docsRepo.findForProject(project, d -> d.isPublic())
                .map(d -> new ProjectDoc(d, uri(project, d.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/projects/{project}/docs/{doc}")
    public ProjectDoc document(@PathVariable String project, @PathVariable String doc) {
        return docsRepo.findOneForProject(project, doc, d -> d.isPublic())
                .map(d -> new ProjectDoc(d, uri(project, d.getId())))
                .orElseThrow(NoDocException::new);
    }


    @ExceptionHandler(NoProjectException.class)
    public ResponseEntity<Void> handleNoProjectException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoDocException.class)
    public ResponseEntity<Void> handleNoDocException() {
        return ResponseEntity.notFound().build();
    }

    protected String uri(String project, String docId) {
        return MessageFormat.format("/projects/{0}/docs/{1}", project, docId);
    }


}
