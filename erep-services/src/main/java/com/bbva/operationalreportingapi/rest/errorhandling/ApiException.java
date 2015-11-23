package com.bbva.operationalreportingapi.rest.errorhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper class to manager the application exceptions.
 * 
 * @author BBVA-ReportingOperacional
 */
public class ApiException extends Exception {

  private static final long serialVersionUID = -8999932578270387947L;

  /**
   * HTTP error status code, so that the developer can “see” it without having
   * to analyze the response’s header.
   **/
  private Integer status;

  /**
   * Internal code specific to the API.
   **/
  int code;

  /** Helper link to attach information about error. */
  String link;

  /** Detail of the error, it must contains useful information for developers. */
  String developerMessage;

  /** Detail of the specific error from the trace of exception. */
  String exceptionNaturalMessage;

  /**
   * Constructor of {@link ApiException}.
   * 
   * @param status
   *          the status param.
   * @param code
   *          the code param.
   * @param message
   *          the message of the exception.
   * @param developerMessage
   *          the specific error for developers.
   * @param link
   *          the helper link to attach.
   */
  public ApiException(int status, int code, String message,
      String developerMessage, String link) {
    super(message);
    this.status = status;
    this.code = code;
    this.developerMessage = developerMessage;
    this.link = link;
    this.exceptionNaturalMessage = developerMessage;
  }

  /**
   * Constructor of {@link ApiException}.
   * 
   * @param status
   *          the status param.
   * @param code
   *          the code param.
   * @param message
   *          the message of the exception.
   * @param developerMessage
   *          the specific error for developers.
   * @param exception
   *          the specific exception.
   * @param link
   *          the helper link to attach.
   */
  public ApiException(int status, int code, String message,
      String developerMessage, String link, Exception exception) {
    super(message);
    this.status = status;
    this.code = code;
    this.developerMessage = developerMessage;
    this.link = link;
    StringWriter naturalMessage = new StringWriter();
    exception.printStackTrace(new PrintWriter(naturalMessage));
    this.exceptionNaturalMessage = naturalMessage.toString();
  }

  public String getExceptionNaturalMessage() {
    return exceptionNaturalMessage;
  }

  public void setExceptionNaturalMessage(String exceptionNaturalMessage) {
    this.exceptionNaturalMessage = exceptionNaturalMessage;
  }

  /**
   * Default constructor of {@link ApiException}.
   */
  public ApiException() {
  }

  /**
   * GETTER for status.
   * 
   * @return int
   */
  public int getStatus() {
    return status;
  }

  /**
   * SETTER for status.
   * 
   * @param status
   *          the status param.
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * GETTER for code.
   * 
   * @return int
   */
  public int getCode() {
    return code;
  }

  /**
   * SETTER for code.
   * 
   * @param code
   *          the code param.
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * GETTER for developer message.
   * 
   * @return String.
   */
  public String getDeveloperMessage() {
    return developerMessage;
  }

  /**
   * SETTER for developer message.
   * 
   * @param developerMessage
   *          the developer message param.
   */
  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }

  /**
   * GETTER for link.
   * 
   * @return String.
   */
  public String getLink() {
    return link;
  }

  /**
   * SETTER for link.
   * 
   * @param link
   *          the link parameter.
   */
  public void setLink(String link) {
    this.link = link;
  }

}
