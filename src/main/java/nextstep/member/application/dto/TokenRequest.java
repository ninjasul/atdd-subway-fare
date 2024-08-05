package nextstep.member.application.dto;

public class TokenRequest {
    private String email;
    private String password;
    private String code;

    public TokenRequest() {
    }

    private TokenRequest(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public static TokenRequest ofEmailAndPassword(String email, String password) {
        return new TokenRequest(email, password, null);
    }

    public static TokenRequest ofCode(String code) {
        return new TokenRequest(null, null, code);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }
}