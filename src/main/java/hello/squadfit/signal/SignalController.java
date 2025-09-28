package hello.squadfit.signal;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class SignalController {

    // 환경변수 (기본값 포함)
    private final String AUTH_SECRET =
            System.getenv().getOrDefault("TURN_AUTH_SECRET", "devsecret");
    private final String TURN_HOST =
            System.getenv().getOrDefault("TURN_HOST", "localhost");
    private final String AI_OFFER_URL =
            System.getenv().getOrDefault("AI_OFFER_URL", "http://localhost:7000/offer");

    // /candidate URL은 /offer 기준으로 대체 (원하면 환경변수로 분리)
    private final String AI_CANDIDATE_URL = AI_OFFER_URL.replace("/offer", "/candidate");

    @Autowired
    private RestTemplate http; // 커넥션 풀 적용된 RestTemplate

    /** TURN/STUN 크레덴셜 발급 (time-limited username, HMAC-SHA1) */
    @GetMapping("/turn/credentials")
    public Map<String, Object> creds(@RequestParam(name = "ttl", defaultValue = "3600") long ttl) {
        long expiry = (System.currentTimeMillis()/1000L) + ttl; // seconds
        String username = String.valueOf(expiry);
        String credential = hmacSha1Base64(AUTH_SECRET, username);

        Map<String,Object> stun = Map.of("urls", List.of("stun:stun.l.google.com:19302"));

        Map<String,Object> turn = new HashMap<>();
        turn.put("urls", List.of(
                "turn:" + TURN_HOST + ":3478?transport=udp",
                "turn:" + TURN_HOST + ":3478?transport=tcp"
        ));
        turn.put("username", username);
        turn.put("credential", credential);

        return Map.of(
                "username", username,
                "credential", credential,
                "ttl", ttl,
                "iceServers", List.of(stun, turn)
        );
    }

    /** Offer 프록시 (Flutter → Spring → aiortc) */
    @PostMapping("/signal/offer")
    public ResponseEntity<?> offer(@RequestBody Map<String,Object> body) {
        long t0 = System.currentTimeMillis();
        try {
            ResponseEntity<Map> resp = http.postForEntity(AI_OFFER_URL, body, Map.class);
            long took = System.currentTimeMillis() - t0;
            System.out.println("[SIGNAL] /offer roundtrip " + took + " ms");
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());

        } catch (HttpStatusCodeException e) {
            String raw = e.getResponseBodyAsString();
            return ResponseEntity.status(e.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(raw);

        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "error", "AI server unreachable",
                            "detail", e.getMessage(),
                            "aiOfferUrl", AI_OFFER_URL
                    ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Signal proxy failed",
                            "detail", e.getMessage()
                    ));
        }
    }

    /** Candidate 프록시 (Flutter → Spring → aiortc) */
    @PostMapping("/signal/candidate")
    public ResponseEntity<?> candidate(@RequestBody Map<String,Object> body) {
        long t0 = System.currentTimeMillis();
        try {
            ResponseEntity<Map> resp = http.postForEntity(AI_CANDIDATE_URL, body, Map.class);
            long took = System.currentTimeMillis() - t0;
            System.out.println("[SIGNAL] /candidate roundtrip " + took + " ms");
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());

        } catch (HttpStatusCodeException e) {
            String raw = e.getResponseBodyAsString();
            return ResponseEntity.status(e.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(raw);

        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "error", "AI server unreachable",
                            "detail", e.getMessage(),
                            "aiCandidateUrl", AI_CANDIDATE_URL
                    ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Candidate proxy failed",
                            "detail", e.getMessage()
                    ));
        }
    }

    private static String hmacSha1Base64(String key, String msg) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA1"));
            byte[] raw = mac.doFinal(msg.getBytes());
            return Base64.getEncoder().encodeToString(raw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
