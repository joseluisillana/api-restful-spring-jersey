package com.bbva.operationalreportingapi.rest.errorhandling;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * Clase que aporta tipos de estados personalizados, para las respuestas HTTP.
 * 
 * @implements 'StatusType'
 * @author BBVA-ReportingOperacional
 */
public abstract class AbstractStatusType implements StatusType {

  private final Family family;
  private final int statusCode;
  private final String reasonPhrase;

  /**
   * Constructor of {@link AbstractStatusType}.
   * 
   * @param family
   *          param
   * @param statusCode
   *          param
   * @param reasonPhrase
   *          param
   **/
  public AbstractStatusType(final Family family, final int statusCode,
      final String reasonPhrase) {
    super();

    this.family = family;
    this.statusCode = statusCode;
    this.reasonPhrase = reasonPhrase;
  }

  /**
   * Constructor of {@link AbstractStatusType}.
   * 
   * @param status
   *          param
   * @param reasonPhrase
   *          param
   */
  protected AbstractStatusType(final Status status, final String reasonPhrase) {
    this(status.getFamily(), status.getStatusCode(), reasonPhrase);
  }

  @Override
  public Family getFamily() {
    return family;
  }

  @Override
  public String getReasonPhrase() {
    return reasonPhrase;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }
}
