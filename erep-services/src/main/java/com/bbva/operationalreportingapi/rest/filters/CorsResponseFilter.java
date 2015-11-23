package com.bbva.operationalreportingapi.rest.filters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.helpers.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase para implementar el mecanismo 'Cross Origin Resource Sharing'.
 * 
 * @author BBVA-ReportingOperacional
 * @implements {@link ContainerResponseFilter}
 **/
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

  private static final Logger log = LoggerFactory
      .getLogger(CorsResponseFilter.class);

  /*
   * private static final String ACAOHEADER = "Access-Control-Allow-Origin";
   * private static final String ACRHHEADER = "Access-Control-Request-Headers";
   * private static final String ACAHHEADER = "Access-Control-Allow-Headers";
   * private static final String ACAMHEADER = "Access-Control-Allow-Methods";
   * private static final String ACACHEADER =
   * "Access-Control-Allow-Credentials"; *
   */
  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + "Executing REST response filter");

    // responseContext.getHeaders().putSingle("Access-Control-Allow-Origin",
    // "*");
    //
    // responseContext.getHeaders().putSingle("Access-Control-Allow-Methods",
    // "GET, POST, PUT, DELETE");

    List<String> reqHead = requestContext.getHeaders().get(
        "Access-Control-Request-Headers");

    if (null != reqHead) {
      responseContext.getHeaders().put("Access-Control-Allow-Headers",
          new ArrayList<Object>(reqHead));
      responseContext.getHeaders().add("Access-Control-Allow-Origin",
          "*");
      responseContext.getHeaders().add("Access-Control-Allow-Headers",
          "origin, content-type, accept, authorization");
      responseContext.getHeaders().add("Access-Control-Allow-Credentials",
          "true");
      responseContext.getHeaders().add("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

    String method = requestContext.getMethod();

    log.debug("[[BBVA Operational Reporting]] Request : " + method
        + " ; for resource: " + requestContext.getUriInfo().getPath());
    Object entity = responseContext.getEntity();

    if (entity != null) {
      if (entity.getClass().getName().equals("java.io.ByteArrayInputStream")) {
        InputStream is = (ByteArrayInputStream) entity;

        String result;

        try {
          result = Utilities.getStringFromInputStream(is);

          responseContext.setEntity(result);

          entity = responseContext.getEntity();

        } catch (ApiException e) {
          requestContext.abortWith(Response.status(
              Response.Status.INTERNAL_SERVER_ERROR).build());
        }
      }

      /*
       * ObjectMapper objectMapper = new ObjectMapper();
       * objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
       * false);
       */
      log.debug("[[BBVA Operational Reporting]] Service response: "
          + new ObjectMapper().writerWithDefaultPrettyPrinter()
              .writeValueAsString(entity));

    }
  }

}
