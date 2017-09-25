package external.token;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class Token {
    private String token;
    private Instant validFrom;
    private Instant validTo;
    private Long generatedIn;
}
