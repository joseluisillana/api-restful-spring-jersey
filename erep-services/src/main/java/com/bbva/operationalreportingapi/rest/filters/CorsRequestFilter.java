package com.bbva.operationalreportingapi.rest.filters;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;

/**
 * Clase para implementar el mecanismo 'Cross Origin Resource Sharing'.
 * 
 * @author BBVA-ReportingOperacional
 * @implements {@link ContainerResponseFilter}
 **/
@Provider
public class CorsRequestFilter implements ContainerRequestFilter {

  private static final Logger log = LoggerFactory
      .getLogger(CorsRequestFilter.class);

  /*
   * private static final String ACAOHEADER = "Access-Control-Allow-Origin";
   * private static final String ACRHHEADER = "Access-Control-Request-Headers";
   * private static final String ACAHHEADER = "Access-Control-Allow-Headers";
   * private static final String ACAMHEADER = "Access-Control-Allow-Methods";
   * private static final String ACACHEADER =
   * "Access-Control-Allow-Credentials"; *
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + "Executing REST request filter");

    requestContext.getHeaders().putSingle("Access-Control-Allow-Origin",
        "*");

    requestContext.getHeaders().putSingle("Access-Control-Allow-Methods",
        "GET, POST, PUT, DELETE");

    List<String> reqHead = requestContext.getHeaders().get(
        "Access-Control-Request-Headers");

    if (null != reqHead) {
      requestContext.getHeaders().add("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE");
      requestContext.getHeaders().add("Access-Control-Allow-Origin",
          "*");
      requestContext.getHeaders().add("Access-Control-Allow-Headers",
          "origin, content-type, accept, authorization");
      requestContext.getHeaders().add("Access-Control-Allow-Credentials",
          "true");
      requestContext.getHeaders().add("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      requestContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

    requestContext.getRequest().evaluatePreconditions();
    // When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
    if (requestContext.getRequest().getMethod().equals("OPTIONS")) {

      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: CorsRequestFilter : HTTP Method (OPTIONS) - Detected!");
      // Just send a OK signal back to the browser
      requestContext.abortWith(Response.status(Response.Status.OK).build());
    }

  }
}
