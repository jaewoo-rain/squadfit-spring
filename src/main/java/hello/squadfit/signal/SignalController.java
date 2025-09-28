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


@RestController
public class SignalController {

    // 환경변수 (기본값 포함)
    private final String AUTH_SECRET =
            System.getenv().getOrDefault("TURN_AUTH_SECRET", "devsecret");
    private final String TURN_HOST =
            System.getenv().getOrDefault("TURN_HOST", "localhost");
    private final String AI_OFFER_URL =
            System.getenv().getOrDefault("AI_OFFER_URL", "http://localhost:7000/offer");

    private final RestTemplate http = new RestTemplate();

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
        try {
            System.out.println("[SIGNAL] Proxy → " + AI_OFFER_URL);
            ResponseEntity<Map> resp = http.postForEntity(AI_OFFER_URL, body, Map.class);
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());

        } catch (HttpStatusCodeException e) {
            // ai 서버가 4xx/5xx 응답 시: 원문 바디/상태 그대로 패스스루
            String raw = e.getResponseBodyAsString();
            return ResponseEntity.status(e.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(raw);

        } catch (ResourceAccessException e) {
            // ai 서버 연결 실패/타임아웃
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "error", "AI server unreachable",
                            "detail", e.getMessage(),
                            "aiOfferUrl", AI_OFFER_URL
                    ));

        } catch (Exception e) {
            // 기타 예외
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Signal proxy failed",
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
