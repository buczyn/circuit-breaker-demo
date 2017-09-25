package external.projectdocs;

import external.projectdocs.repo.ProjectDocEntity;
import lombok.Getter;

@Getter
public class ProjectDoc {
    private String name;
    private String author;
    private String uri;

    public ProjectDoc(ProjectDocEntity entity, String uri) {
        this.name = entity.getName();
        this.author = entity.getAuthor();
        this.uri = uri;
    }
}
