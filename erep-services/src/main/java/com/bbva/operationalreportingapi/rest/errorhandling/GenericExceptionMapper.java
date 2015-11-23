package com.bbva.operationalreportingapi.rest.errorhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;

import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that maps general exceptions of application to send it as response.
 * 
 * @author BBVA-ReportingOperacional
 * @implements {@link ExceptionMapper}
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

  @JsonIgnore
  private static final Logger log = LoggerFactory
      .getLogger(GenericExceptionMapper.class);

  @JsonIgnore
  private InformationMessage messageEntity;

  @Override
  public Response toResponse(Throwable ex) {

    log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + ApplicationConstants.LOG_ERROR + ex.getMessage());

    if (ex instanceof NotAllowedException) {
      messageEntity = new InformationMessage(
          Response.Status.METHOD_NOT_ALLOWED.getStatusCode(),
          Response.Status.FORBIDDEN.getStatusCode(),
          ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
              + Response.Status.METHOD_NOT_ALLOWED.getReasonPhrase());
      return Response.status(messageEntity.getStatus()).entity(messageEntity)
          .type(MediaType.APPLICATION_JSON).build();
    } else if (ex instanceof ApiException) {

      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR
          + ((ApiException) ex).getDeveloperMessage()
          + ((ApiException) ex).getExceptionNaturalMessage());

      messageEntity = new InformationMessage(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

      return Response.status(messageEntity.getStatus()).entity((messageEntity))
          .type(MediaType.APPLICATION_JSON).build();

    } else {

      ErrorMessage errorMessage = new ErrorMessage();
      setHttpStatus(ex, errorMessage);
      errorMessage.setCode(ApplicationConstants.GENERAL_ERROR_CODE_API);
      errorMessage.setMessage(ex.getMessage());
      StringWriter errorStackTrace = new StringWriter();
      ex.printStackTrace(new PrintWriter(errorStackTrace));
      errorMessage.setDeveloperMessage(errorStackTrace.toString());
      // errorMessage.setLink(AppConstants.URL_TO_BBVA_FAQ);

      messageEntity = new InformationMessage(errorMessage.getStatus(),
          errorMessage.getCode(),
          ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR + ex.getMessage() + errorStackTrace);

      return Response.status(errorMessage.getStatus()).entity(messageEntity)
          .type(MediaType.APPLICATION_JSON).build();
    }
  }

  /**
   * Method that check the type of exception and change the HTTP Status. Also
   * fill log file.
   * 
   * @param ex
   *          The exception captched.
   * @param errorMessage
   *          The error message bean.
   */
  private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {

    StringWriter errorStackTrace = null;

    if (ex instanceof NotAllowedException) {
      errorMessage
          .setStatus(Response.Status.METHOD_NOT_ALLOWED.getStatusCode());
      errorStackTrace = new StringWriter();
      ex.printStackTrace(new PrintWriter(errorStackTrace));
      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR + ex.getMessage() + errorStackTrace);
    }
    if (ex instanceof DataAccessResourceFailureException) {
      errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
          .getStatusCode());
      errorStackTrace = new StringWriter();
      ex.printStackTrace(new PrintWriter(errorStackTrace));
      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR + ex.getMessage() + errorStackTrace);
    } else if (ex instanceof WebApplicationException) {
      // This way to combine both of methods.
      errorMessage.setStatus(((WebApplicationException) ex).getResponse()
          .getStatus());
      errorStackTrace = new StringWriter();
      ex.printStackTrace(new PrintWriter(errorStackTrace));
      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR + ex.getMessage() + errorStackTrace);
    } else {
      // Defaults to internal server error 500
      errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
          .getStatusCode());
      errorStackTrace = new StringWriter();
      ex.printStackTrace(new PrintWriter(errorStackTrace));
      log.error(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + ApplicationConstants.LOG_ERROR + ex.getMessage() + errorStackTrace);
    }
  }
}
