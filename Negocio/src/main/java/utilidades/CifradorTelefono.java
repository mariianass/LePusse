/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CifradorTelefono {

    private static final String ALGORITMO = "AES";
    private static final String TRANSFORMACION = "AES/ECB/PKCS5Padding";

    // Cambia esta llave por una frase fija de tu proyecto.
    // Debe quedarse igual siempre o no podrás desencriptar lo ya guardado.
    private static final String CLAVE_SECRETA = "LePusse_Telefonos_2026";

    private CifradorTelefono() {
    }

    private static SecretKeySpec obtenerClave() throws Exception {
        byte[] claveBytes = CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        claveBytes = sha.digest(claveBytes);
        claveBytes = Arrays.copyOf(claveBytes, 16); // 128 bits
        return new SecretKeySpec(claveBytes, ALGORITMO);
    }

    public static String encriptar(String textoPlano) {
        try {
            if (textoPlano == null || textoPlano.trim().isEmpty()) {
                return textoPlano;
            }

            Cipher cipher = Cipher.getInstance(TRANSFORMACION);
            cipher.init(Cipher.ENCRYPT_MODE, obtenerClave());
            byte[] textoEncriptado = cipher.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(textoEncriptado);

        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar el teléfono.", e);
        }
    }

    public static String desencriptar(String textoEncriptado) {
        try {
            if (textoEncriptado == null || textoEncriptado.trim().isEmpty()) {
                return textoEncriptado;
            }

            Cipher cipher = Cipher.getInstance(TRANSFORMACION);
            cipher.init(Cipher.DECRYPT_MODE, obtenerClave());
            byte[] textoDescifrado = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
            return new String(textoDescifrado, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar el teléfono.", e);
        }
    }
}