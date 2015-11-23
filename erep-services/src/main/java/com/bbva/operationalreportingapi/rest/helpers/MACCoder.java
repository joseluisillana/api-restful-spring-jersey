/**
 * 
 */
package com.bbva.operationalreportingapi.rest.helpers;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * @author joseluisillana
 *
 */
class MACCoder {
  /**
   * HmacMD5
   */
  public static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {
    // HmacMD5
    KeyGenerator generator = KeyGenerator.getInstance("HmacMD5");
    //
    SecretKey secretKey = generator.generateKey();
    //
    byte[] key = secretKey.getEncoded();
    return key;
  }

  /**
   * HmacMd5 ???
   */
  public static String encodeHmacMD5(byte[] data, byte[] key) throws Exception {
    //
    SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
    // Mac
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    // mac
    mac.init(secretKey);
    //
    byte[] digest = mac.doFinal(data);
    return new HexBinaryAdapter().marshal(digest);//
  }

  /**
   * HmacSHA1
   */
  public static byte[] initHmacSHAKey() throws NoSuchAlgorithmException {
    // HmacMD5
    KeyGenerator generator = KeyGenerator.getInstance("HmacSHA1");
    //
    SecretKey secretKey = generator.generateKey();
    //
    byte[] key = secretKey.getEncoded();
    return key;
  }

  /**
   * HmacSHA1 ???
   */
  public static String encodeHmacSHA(byte[] data, byte[] key) throws Exception {
    //
    SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
    // Mac
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    // mac
    mac.init(secretKey);
    //
    byte[] digest = mac.doFinal(data);
    return new HexBinaryAdapter().marshal(digest);//
  }

  /**
   * HmacSHA256
   */
  public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {
    // HmacMD5
    KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
    //
    SecretKey secretKey = generator.generateKey();
    //
    byte[] key = secretKey.getEncoded();
    return key;
  }

  /**
   * HmacSHA1 ???
   */
  public static String encodeHmacSHA256(byte[] data, byte[] key)
      throws Exception {
    //
    SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
    // Mac
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    // mac
    mac.init(secretKey);
    //
    byte[] digest = mac.doFinal(data);
    return new HexBinaryAdapter().marshal(digest);//
  }

  /**
   * HmacSHA256
   */
  public static byte[] initHmacSHA384Key() throws NoSuchAlgorithmException {
    // HmacMD5
    KeyGenerator generator = KeyGenerator.getInstance("HmacSHA384");
    //
    SecretKey secretKey = generator.generateKey();
    //
    byte[] key = secretKey.getEncoded();
    return key;
  }

  /**
   * HmacSHA1 ???
   */
  public static String encodeHmacSHA384(byte[] data, byte[] key)
      throws Exception {
    //
    SecretKey secretKey = new SecretKeySpec(key, "HmacSHA384");
    // Mac
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    // mac
    mac.init(secretKey);
    //
    byte[] digest = mac.doFinal(data);
    return new HexBinaryAdapter().marshal(digest);//
  }

  /**
   * HmacSHA256
   */
  public static byte[] initHmacSHA512Key() throws NoSuchAlgorithmException {
    // HmacMD5
    KeyGenerator generator = KeyGenerator.getInstance("HmacSHA512");
    //
    SecretKey secretKey = generator.generateKey();
    //
    byte[] key = secretKey.getEncoded();
    return key;
  }

  /**
   * HmacSHA1 ???
   */
  public static String encodeHmacSHA512(byte[] data, byte[] key)
      throws Exception {
    //
    SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
    // Mac
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    // mac
    mac.init(secretKey);
    //
    byte[] digest = mac.doFinal(data);
    return new HexBinaryAdapter().marshal(digest);//
  }
}
