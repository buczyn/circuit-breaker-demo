package external.projectdocs;

import external.projectdocs.repo.ProjectDocEntity;
import external.projectdocs.repo.ProjectDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sec")
public class SecProjectdocsController {

    @Autowired
    private ProjectDocRepository docsRepo;

    @GetMapping("/projects/{project}/docs")
    public List<ProjectDoc> allDocuments(@PathVariable String project, @RequestParam String token) {
        return docsRepo.findForProject(project, token2Fileter(token))
                .map(d -> new ProjectDoc(d, uri(project, d.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/projects/{project}/docs/{doc}")
    public ProjectDoc document(@PathVariable String project, @PathVariable String doc, @RequestParam String token) {
        return docsRepo.findOneForProject(project, doc, token2Fileter(token))
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

    private Predicate<ProjectDocEntity> token2Fileter(String token) {
        if(token.equals("000")) {
            return this::onlyPublicDocs;
        }
        return this::allDocs;
    }

    private boolean allDocs(ProjectDocEntity entity) {
        return true;
    }

    private boolean onlyPublicDocs(ProjectDocEntity entity) {
        return entity.isPublic();
    }

    protected String uri(String project, String docId) {
        return MessageFormat.format("/projects/{0}/docs/{1}", project, docId);
    }
}
