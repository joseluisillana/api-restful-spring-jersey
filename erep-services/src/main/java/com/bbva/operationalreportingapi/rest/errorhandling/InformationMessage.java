package com.bbva.operationalreportingapi.rest.errorhandling;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO class to send messages in response.
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
public class InformationMessage {

  @XmlElement(name = "status")
  int status;

  @XmlElement(name = "code")
  int code;

  @XmlElement(name = "message")
  String message;

  /**
   * Constructor for {@link InformationMessage}.
   * 
   * @param statusCode
   *          the status code parameter.
   * @param code
   *          the code parameter.
   * @param messageText
   *          the message of the exception parameter.
   */
  public InformationMessage(int statusCode, int code, String messageText) {
    this.status = statusCode;
    this.message = messageText;
    this.code = code;

  }

  /**
   * Default constructor for {@link InformationMessage}.
   */
  public InformationMessage() {
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
