package external.token;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@RestController
public class Controller {

    @RequestMapping("/token")
    public Token token() {
        return generateToken(System.currentTimeMillis());
    }

    private Token generateToken(long generationStart) {
        Instant now = Instant.now();
        return Token.builder()
                .token(UUID.randomUUID().toString())
                .validFrom(now)
                .validTo(now.plus(5, ChronoUnit.MINUTES))
                .generatedIn(System.currentTimeMillis() - generationStart).build();
    }


    @RequestMapping("/delayedToken")
    public Token delayedToken(@RequestParam(required = false) Long delayMillis) {
        long generationStart = System.currentTimeMillis();
        long delay;
        if (delayMillis == null) {
            delay = new Random().nextInt(1000) + 1000;
        } else {
            delay = delayMillis;
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return generateToken(generationStart);
    }
}
