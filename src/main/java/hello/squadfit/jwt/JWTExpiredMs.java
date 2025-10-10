package hello.squadfit.jwt;

public interface JWTExpiredMs {

    long refreshExpiredMs = 10 * 60 * 1000L; // 12 * 60 * 1000L = 12분
    long accessExpiredMs = 30 * 1000L; // 30초

}
