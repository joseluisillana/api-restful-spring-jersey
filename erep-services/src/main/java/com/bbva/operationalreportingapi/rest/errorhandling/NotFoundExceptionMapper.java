package com.bbva.operationalreportingapi.rest.errorhandling;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;

/**
 * Class that maps exceptions sets up when a request is doing for a non existing
 * resources in API.
 * 
 * @author BBVA-ReportingOperacional
 * @implements {@link ExceptionMapper}
 */
@Provider
public class NotFoundExceptionMapper implements
    ExceptionMapper<NotFoundException> {

  /**
   * Method that override the way to send exception to response.
   * */
  @Override
  public Response toResponse(NotFoundException ex) {

    InformationMessage messageEntity = new InformationMessage(
        Response.Status.BAD_REQUEST.getStatusCode(),
        Response.Status.NOT_FOUND.getStatusCode(),
        ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
            + ApplicationConstants.BAD_REQUEST_API_MESSAGE);
    return Response.status(messageEntity.getStatus()).entity(messageEntity)
        .type(MediaType.APPLICATION_JSON).build();
  }
}
