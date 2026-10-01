package co.edu.itp.ciecyt.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

public class TruncatingPasswordEncoder implements PasswordEncoder {

    private static final int MAX_PASSWORD_BYTES = 72;

    private final PasswordEncoder delegate;

    public TruncatingPasswordEncoder(PasswordEncoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(truncate(rawPassword));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return delegate.matches(truncate(rawPassword), encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return delegate.upgradeEncoding(encodedPassword);
    }

    private static String truncate(CharSequence rawPassword) {
        String value = rawPassword.toString();
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length <= MAX_PASSWORD_BYTES) {
            return value;
        }
        int end = MAX_PASSWORD_BYTES;
        while (end > 0 && (bytes[end] & 0xC0) == 0x80) {
            end--;
        }
        return new String(bytes, 0, end, StandardCharsets.UTF_8);
    }
}
