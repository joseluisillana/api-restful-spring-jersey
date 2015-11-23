/**
 * 
 */
package com.bbva.operationalreportingapi.rest.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Tools.
 * 
 * @author BBVA-ReportingOperacional
 */
public class Utilities {

  private static final Logger log = LoggerFactory.getLogger(Utilities.class);

  private static final String ALGORITHM_NAME_SHA256 = "HmacSHA256";

  public static String createHashKeyEncoded(String secretKey, String message)
      throws ApiException {

    String result = null;
    try {
      Mac sha256_HMAC = Mac.getInstance(ALGORITHM_NAME_SHA256);
      SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"),
          ALGORITHM_NAME_SHA256);
      sha256_HMAC.init(secret_key);

      result = Base64.encodeBase64String(sha256_HMAC.doFinal(message
          .getBytes("UTF-8")));

      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: createHashKeyEncoded." + " from " + message + " gets "
          + result);

    } catch (NoSuchAlgorithmException | InvalidKeyException
        | UnsupportedEncodingException ex) {
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, ex.getMessage(), null,
          ex);
    }

    return result;
  }

  /**
   * Method to get a string from an input stream
   * 
   * @param is
   * @return
   * @throws ApiException
   */
  public static String getStringFromInputStream(InputStream is)
      throws ApiException {

    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {

      br = new BufferedReader(new InputStreamReader(is));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

    } catch (IOException e) {
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          throw new ApiException(
              Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
              Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
              ApplicationConstants.GENERAL_ERROR_API_MESSAGE
                  + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(),
              null, e);
        }
      }
    }

    return sb.toString();

  }

}
