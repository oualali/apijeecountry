package com.example.pulco.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class JWT {

    private static final String SECRET_KEY = "super secret";
    private static final String HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private JSONObject payload = new JSONObject();
    private String signature;
    private String encodedHeader;

    private JWT() {
        encodedHeader = encode(new JSONObject(HEADER));
    }

    public JWT(String email, int id) {
        this();
        payload.put("id", id);
        payload.put("email", email);
        payload.put("exp", LocalDateTime.now().plus(60L, ChronoUnit.MINUTES).toEpochSecond(ZoneOffset.UTC));
        payload.put("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        signature = hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY);
    }

    public JWT(String token) throws NoSuchAlgorithmException {
        this();
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid Token format");
        }
        if (encodedHeader.equals(parts[0])) {
            encodedHeader = parts[0];
        } else {
            throw new NoSuchAlgorithmException("JWT Header is Incorrect: " + parts[0]);
        }

        payload = new JSONObject(decode(parts[1]));
        if (payload.isEmpty()) {
            throw new JSONException("Payload is Empty: ");
        }
        if (!payload.has("exp")) {
            throw new JSONException("Payload doesn't contain expiry " + payload);
        }
        signature = parts[2];
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String encode(JSONObject obj) {
        return encode(obj.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private String hmacSha256(String data, String secret) {
        try {

            // MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);// digest.digest(secret.getBytes(StandardCharsets.UTF_8));

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return encodedHeader + "." + encode(payload) + "." + signature;
    }

    public boolean isValid() {
        return payload.getLong("exp") > (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) // token not expired
                && signature.equals(hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY));// signature matched
    }

    public String getEmail() {
        return payload.getString("email");
    }

    public String getId() {
        return payload.getString("id");
    }

    public LocalDateTime getExpAsLocalDateTime() {
        return LocalDateTime.ofEpochSecond(getExp(), 0, ZoneOffset.UTC);
    }

    public Long getExp() {
        return payload.getLong("exp");
    }
}
