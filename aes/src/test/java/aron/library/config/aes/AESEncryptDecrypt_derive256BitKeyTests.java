package aron.library.config.aes;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AESEncryptDecrypt_derive256BitKeyTests {
    @Test
    @DisplayName("derive256bitKey - password and salt is null test")
    public void derive256bitKey_shouldThrowException_whenPasswordAndSaltIsNull_then() {
        assertThrows(AESToolException.class, () -> {
            AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256(null, null, 2500000);
        });
    }

    @Test
    @DisplayName("derive256bitKey - salt is null test")
    public void derive256bitKey_shouldThrowException_whenSaltIsNull() {
        assertThrows(AESToolException.class, () -> {
            AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256("password", null, 250000);
        });
    }

    @Test
    @DisplayName("derive256bitKey - password is null, salt is too short test")
    public void derive256bitKey_shouldThrowException_whenPasswordIsNullSaltIsShort() {
        assertThrows(AESToolException.class, () -> {
            AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256(null, "short", 250000);
        });
    }

    @Test
    @DisplayName("derive256bitKey - password, salt is too long")
    public void derive256bitKey_shouldThrowException_whenSaltIsTooLong() {
        assertThrows(AESToolException.class, () -> {
            AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256("password", "abcdef0123456789-", 250000);
        });
    }

    @Test
    @DisplayName("derive256bitKey - password is empty string")
    public void derive256bitKey_shouldThrowException_whenPasswordIsEmptyString() {
        assertThrows(AESToolException.class, () -> {
            AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256("", "abcdef0123456789", 250000);
        });
    }

    @Test
    @DisplayName("derive256BitKey - Java,GO key derivation comparison test")
    public void derive256bitKey_testAgainstGo() throws AESToolException, DecoderException {
        byte[] expected = Hex.decodeHex("1D9E9E7E2BD8B7840124A79F4D486ECD81BD53E2511DA83BEE3F3642A5C7A0AD".toCharArray());
        SecretKeySpec key = AESEncryptDecrypt.derive256BitAESKeyWithHmacSHA256("password", "abcdef0123456789", 250000);

        final byte[] ba = key.getEncoded();
        for (byte b : ba) {
            System.out.printf("%02X ", b);
        }
        System.out.println();

        for (byte b : expected) {
            System.out.printf("%02X ", b);
        }
        System.out.println();

        assertArrayEquals(expected, ba);
    }
}
