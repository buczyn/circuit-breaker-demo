package external.projectdocs.repo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProjectDocEntity {
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String author;
    private boolean isPublic;
}
