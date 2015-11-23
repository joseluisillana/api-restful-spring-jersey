package com.bbva.operationalreportingapi.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ProcessDefinition;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.errorhandling.InformationMessage;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.services.ComplianceProcessDefinitionService;

/**
 * END-POINT class for the Processes Definition web service resource.
 * 
 * @author BBVA-ReportingOperacional
 */
@Component
@Path("/")
public class ProcessDefinitionResource {

  @Autowired
  private ComplianceProcessDefinitionService complianceProcessDefinitionService;

  /**
   * END POINT URL TO GENERATE EXAMPLE DATA FOR PROCESSES DEFINITIONS.
   */
  private static final String URL_PROCESS_DEFINITION_GEN_DATA = "processes/generateData";

  /**
   * END POINT URL FOR LISTING PROCESSES DEFINITIONS.
   */
  private static final String URL_PROCESS_DEFINITION_LIST = "processes/list";

  /**
   * Method that create example data 'Processes definitions' report.
   * 
   * @verb POST
   * @produces {@link MediaType.APPLICATION_JSON}
   */
  @POST
  @Path(URL_PROCESS_DEFINITION_GEN_DATA)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createAndPersistsMockDataComplianceProcessDefinitionExampleData()
      throws IOException, ApiException {

    try {
      complianceProcessDefinitionService
          .createAndPersistsMockDataComplianceProcessDefinitionExampleData();
    } catch (Exception e) {
      throw e;
    }
    InformationMessage messageEntity = new InformationMessage(
        Response.Status.OK.getStatusCode(),
        Response.Status.CREATED.getStatusCode(),
        ApplicationConstants.GENERAL_ERROR_API_MESSAGE
            + Response.Status.CREATED.getReasonPhrase());
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(messageEntity).build();
  }

  /**
   * Method (Resources) to query the 'Processes Definitions' without filters
   * neither order fields specified.
   * 
   * @return Response of resource
   * @throws IOException
   *           a kind of exception
   * @throws ApiException
   *           a kind of exception
   */
  @GET
  @Path(URL_PROCESS_DEFINITION_LIST)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getComplianceProcessDefinitionListWithoutFiltersPathParam()
      throws IOException, ApiException {
    List<ProcessDefinition> listResults = null;

    try {

      listResults = complianceProcessDefinitionService
          .getComplianceProcessDefinitionList();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(listResults).build();
  }

  /**
   * SETTER for 'Processes Definition' service layer, inyected by Spring
   * Context.
   * 
   * @param ComplianceProcessDefinitionService
   *          .
   */
  public void setComplianceProcessDefinitionService(
      ComplianceProcessDefinitionService complianceProcessDefinitionService) {
    this.complianceProcessDefinitionService = complianceProcessDefinitionService;
  }
}
