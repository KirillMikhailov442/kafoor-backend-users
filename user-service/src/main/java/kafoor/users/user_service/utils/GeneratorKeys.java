package kafoor.users.user_service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class GeneratorKeys {

    @Value("${generator.secret}")
    private String secretKey;
    final private String salt = "kafoor";

    public String encode(String text){
        TextEncryptor encryptor = Encryptors.text(secretKey, salt);
        return encryptor.encrypt(text);
    }

    public String decode(String text){
        TextEncryptor encryptor = Encryptors.text(secretKey, salt);
        return encryptor.decrypt(text);
    }
}
