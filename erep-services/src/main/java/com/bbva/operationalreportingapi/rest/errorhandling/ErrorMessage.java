package com.bbva.operationalreportingapi.rest.errorhandling;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * POJO Class that saves the error messages information.
 * 
 * @author BBVA-ReportingOperacional
 */
@XmlRootElement
public class ErrorMessage {

  @JsonIgnore
  private static Logger log = Logger.getLogger(ErrorMessage.class.getName());

  /** Save the status code given by server. **/
  @XmlElement(name = "status")
  private int status;

  /** Specific error code. **/
  @XmlElement(name = "code")
  private int code;

  /** Error message. **/
  @XmlElement(name = "message")
  private String message;

  /** Link with information useful for client. */
  @XmlElement(name = "link")
  private String link;

  /** Extra information useful for developers. */
  @XmlElement(name = "developerMessage")
  private String developerMessage;

  /**
   * GETTER for status code.
   * 
   * @return int
   */
  public int getStatus() {
    return status;
  }

  /**
   * SETTER for status code.
   * 
   * @param status
   *          the status code parameter.
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * GETTER for the error code.
   * 
   * @return int.
   */
  public int getCode() {
    return code;
  }

  /**
   * SETTER for de error code.
   * 
   * @param code
   *          the error code parameter.
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * GETTER for the message.
   * 
   * @return String.
   */
  public String getMessage() {
    return message;
  }

  /**
   * SETTER for the message.
   * 
   * @param message
   *          the message parameter.
   */
  public void setMessage(String message) {
    this.message = message;
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
   * SETTER for the developer message.
   * 
   * @param developerMessage
   *          the developer message.
   */
  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }

  /**
   * GETTER for the link.
   * 
   * @return String.
   */
  public String getLink() {
    return link;
  }

  /**
   * SETTER for the link.
   * 
   * @param link
   *          the link parameter.
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Constructor for {@link ErrorMessage}.
   * <p>
   * If know the error, use this constructor to take care about it, and show in
   * the trace.
   * </p>
   * 
   * @param exceptionCaptched
   *          the exception, {@link ApiException} type of.
   */
  public ErrorMessage(ApiException exceptionCaptched) {
    try {
      BeanUtils.copyProperties(this, exceptionCaptched);
    } catch (IllegalAccessException e) {
      log.log(Level.SEVERE, e.getMessage(), e);
    } catch (InvocationTargetException e) {
      log.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  /**
   * Constructor for {@link ErrorMessage}.
   * <p>
   * If is an unknown error, use this constructor to take care about it, and
   * show the {@link NotFoundException} message on the trace.
   * </p>
   * 
   * @param exception
   *          the exception parameter.
   */
  public ErrorMessage(NotFoundException exception) {
    this.status = Response.Status.NOT_FOUND.getStatusCode();
    this.message = exception.getMessage();
    this.link = "https://jersey.java.net/apidocs/2.8/jersey/javax/ws/rs/NotFoundException.html";
  }

  /**
   * Default constructor for {@link ErrorMessage}.
   */
  public ErrorMessage() {
  }
}
