package external.projectdocs.repo;

import external.projectdocs.NoProjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class ProjectDocRepository {
    @Autowired
    private Documents docs;

    public Stream<ProjectDocEntity> findForProject(String project, Predicate<ProjectDocEntity> filter) {
        return docsOfProject(project).filter(filter);
    }

    public Optional<ProjectDocEntity> findOneForProject(String project, String docId, Predicate<ProjectDocEntity> filter) {
        return docsOfProject(project).filter(filter).filter(d -> d.getId().equals(docId))
                .findAny();
    }

    private Stream<ProjectDocEntity> docsOfProject(String project) {
        return docs.getProjects().entrySet().stream()
                .filter(p -> p.getKey().equals(project)).findAny().orElseThrow(NoProjectException::new)
                .getValue().stream();
    }
}
