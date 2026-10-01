package co.edu.itp.ciecyt.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class TruncatingPasswordEncoderTest {

    private PasswordEncoder bcrypt;
    private PasswordEncoder truncating;

    @BeforeEach
    public void setup() {
        bcrypt = new BCryptPasswordEncoder();
        truncating = new TruncatingPasswordEncoder(bcrypt);
    }

    @Test
    public void encodeShouldAcceptPasswordLongerThan72Bytes() {
        String rawPassword = "x".repeat(100);

        String encodedPassword = truncating.encode(rawPassword);

        assertThat(truncating.matches(rawPassword, encodedPassword)).isTrue();
        assertThat(bcrypt.matches(rawPassword, encodedPassword)).isTrue();
    }

    @Test
    public void encodeShouldTruncateOnACharacterBoundary() {
        String rawPassword = "ñ".repeat(50);
        String truncatedPassword = "ñ".repeat(36);
        String encodedPassword = bcrypt.encode(truncatedPassword);

        assertThat(rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8).length).isGreaterThan(72);
        assertThat(truncatedPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8).length).isEqualTo(72);
        assertThat(truncating.matches(rawPassword, encodedPassword)).isTrue();
    }

    @Test
    public void encodeShouldNotSplitAMultiByteCharacter() {
        String rawPassword = "a".repeat(71) + "ñ";
        String truncatedPassword = "a".repeat(71);
        String encodedPassword = bcrypt.encode(truncatedPassword);

        assertThat(truncating.matches(rawPassword, encodedPassword)).isTrue();
        assertThat(truncating.matches(rawPassword, bcrypt.encode("a".repeat(72)))).isFalse();
    }

    @Test
    public void matchesShouldAcceptPasswordShorterThanTheLimit() {
        String rawPassword = "a-very-short-password";

        assertThat(truncating.matches(rawPassword, truncating.encode(rawPassword))).isTrue();
        assertThat(truncating.matches(rawPassword, truncating.encode("another-password"))).isFalse();
    }
}
