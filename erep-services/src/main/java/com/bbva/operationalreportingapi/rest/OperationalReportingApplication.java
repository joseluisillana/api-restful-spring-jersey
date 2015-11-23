package com.bbva.operationalreportingapi.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Class that sets up the components of the application.
 * 
 * @exteds {@link ResourceConfig}
 * @author BBVA-ReportingOperacional
 **/
public class OperationalReportingApplication extends ResourceConfig {
  /**
   * Set up the JAX-RS components of API.
   */
  public OperationalReportingApplication() {

    /**
     * It sets up this components.
     * <p>
     * -API Resources: InformeEjemploResource -FILTERS: RequestContextFilter
     * LoggingResponseFilter CORSResponseFilter -EXCEPTION MAPPERS:
     * GenericExceptionMapper AppExceptionMapper
     * CustomReasonPhraseExceptionMapper NotFoundExceptionMapper -ADITIONAL
     * CAPACITIES: JacksonFeature
     * </p>
     * **/
    packages("com.bbva.operationalreportingapi.rest");

    // JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    // provider.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
    // provider.disable(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
    // provider.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
    // true);
    // provider
    // .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
    // provider.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS,
    // false);
    //
    // register(provider);

    // OUTSIDE CAPABILITIES API
    register(EntityFilteringFeature.class);
    register(MultiPartFeature.class);

    // EncodingFilter.enableFor(this, GZipEncoder.class);

    // EXAMPLE OF THREATING WITH PROPERTIES
    // property(EntityFilteringFeature.ENTITY_FILTERING_SCOPE, new
    // Annotation[] {PodcastDetailedView.Factory.get()});
  }
}
