package br.unitins.tp1.notebooks.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    // Um salt aleatório para aumentar a segurança da senha
    private String salt = "#suaSequenciaSegura123";
    // Número de iterações para aumentar a segurança do hash
    private Integer iterationCount = 405;
    // Comprimento do hash em bits
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha) {
        try {
            // PBKDF2 com HmacSHA512 para gerar o hash com salt
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(
                            new PBEKeySpec(senha.toCharArray(), salt.getBytes(), iterationCount, keyLength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar o hash da senha", e);
        }
    }

    // Método de teste para verificar o hash
    public static void main(String[] args) {
        HashService hash = new HashServiceImpl();
        System.out.println(hash.getHashSenha("123456"));
        System.out.println(hash.getHashSenha("123456"));
        System.out.println(hash.getHashSenha("senha123"));
        System.out.println(hash.getHashSenha("outraSenha"));
    }
}
