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

/**
 * Clase utilitaria encargada de cifrar y descifrar números de teléfono
 * utilizando el algoritmo AES.
 * 
 * Su propósito es proteger la información sensible de los clientes al momento
 * de almacenarla y recuperarla dentro del sistema.
 * 
 * La clave secreta utilizada debe mantenerse constante, ya que cambiarla
 * impediría descifrar los teléfonos previamente almacenados.
 * 
 * Esta clase no puede ser instanciada.
 * 
 * @author Regina, Mariana e Isaac
 */
public class CifradorTelefono {

    /**
     * Nombre del algoritmo de cifrado utilizado.
     */
    private static final String ALGORITMO = "AES";

    /**
     * Transformación de cifrado utilizada.
     */
    private static final String TRANSFORMACION = "AES/ECB/PKCS5Padding";

    /**
     * Clave secreta fija utilizada para generar la clave de cifrado.
     * 
     * Debe permanecer igual durante toda la vida del proyecto para poder
     * descifrar correctamente los datos ya almacenados.
     */
    private static final String CLAVE_SECRETA = "LePusse_Telefonos_2026";

    /**
     * Constructor privado para evitar la creación de instancias de esta clase.
     */
    private CifradorTelefono() {
    }

    /**
     * Genera la clave secreta en formato {@code SecretKeySpec} a partir
     * de la frase definida en la constante {@code CLAVE_SECRETA}.
     * 
     * Para ello, aplica el algoritmo SHA-256 sobre la clave base y toma
     * los primeros 128 bits para el cifrado AES.
     * 
     * @return clave secreta lista para ser utilizada en el cifrado o descifrado.
     * @throws Exception si ocurre un error al generar la clave.
     */
    private static SecretKeySpec obtenerClave() throws Exception {
        byte[] claveBytes = CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        claveBytes = sha.digest(claveBytes);
        claveBytes = Arrays.copyOf(claveBytes, 16); // 128 bits
        return new SecretKeySpec(claveBytes, ALGORITMO);
    }

    /**
     * Cifra un texto plano utilizando AES y devuelve el resultado en formato Base64.
     * 
     * Si el texto recibido es {@code null} o está vacío, se devuelve sin cambios.
     * 
     * @param textoPlano texto original que se desea cifrar.
     * @return texto cifrado en formato Base64.
     * @throws RuntimeException si ocurre un error durante el proceso de cifrado.
     */
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

    /**
     * Descifra un texto previamente cifrado con AES y codificado en Base64.
     * 
     * Si el texto recibido es {@code null} o está vacío, se devuelve sin cambios.
     * 
     * @param textoEncriptado texto cifrado en formato Base64.
     * @return texto original descifrado.
     * @throws RuntimeException si ocurre un error durante el proceso de descifrado.
     */
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