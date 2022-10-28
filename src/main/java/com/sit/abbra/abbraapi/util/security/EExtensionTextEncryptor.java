package com.sit.abbra.abbraapi.util.security;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EExtensionTextEncryptor implements TextEncryptor {

    private final BytesEncryptor encryptor;

    public EExtensionTextEncryptor(BytesEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encrypt(String text) {
        return new String(Hex.encode(encryptor.encrypt(Utf8.encode(text))));
    }

    public String decrypt(String encryptedText) {
        return Utf8.decode(encryptor.decrypt(Hex.decode(encryptedText)));
    }
}
