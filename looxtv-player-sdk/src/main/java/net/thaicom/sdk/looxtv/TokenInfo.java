package net.thaicom.sdk.looxtv;

public class TokenInfo {
    private String token;
    private int validFrom;
    private int validTo;

    private int validDuration;

    public TokenInfo(String token, int validFrom, int validTo, int validDuration) {
        this.token = token;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.validDuration = validDuration;
    }

    public String getToken() {
        return token;
    }

    public int getValidFrom() {
        return validFrom;
    }

    public int getValidTo() {
        return validTo;
    }

    public int getValidDuration() {
        return validDuration;
    }


}
