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

import com.bbva.operationalreportingapi.rest.beans.hierarchy.OperationsConfigurations;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.errorhandling.InformationMessage;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.services.HierarchyService;

/**
 * END-POINT class for the hierarchy resource.
 * 
 * @author BBVA-ReportingOperacional
 */
@Component
@Path("/hierarchy")
public class HierarchyResource {

  @Autowired
  private HierarchyService hierarchyService;

  /**
   * Method that generates example of data about hierarchy of processes.
   * 
   * @return Response with information of {@link OperationsConfigurations} in
   *         JSON format.
   * @throws IOException
   *           kind of exception.
   * @throws ApiException
   *           kind of exception.
   */
  @POST
  @Path("/generateExampleData")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createHierarchy() throws IOException, ApiException {

    OperationsConfigurations operationsConfigurationsAux = null;
    try {
      operationsConfigurationsAux = hierarchyService.createHierarchyMock();
      if (operationsConfigurationsAux != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON)
            .entity(operationsConfigurationsAux).build();
      } else {
        InformationMessage messageEntity = new InformationMessage(
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            ApplicationConstants.GENERAL_ERROR_API_MESSAGE);
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).entity(messageEntity).build();
      }
    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * Method that return to client all data about hierarchy of processes.
   * 
   * @return Response with information of {@link OperationsConfigurations} in
   *         JSON format.
   * @throws IOException
   *           kind of exception.
   * @throws ApiException
   *           kind of exception.
   */
  @GET
  @Path("/list")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getHierarchyList() throws IOException, ApiException {
    List<OperationsConfigurations> listResults = null;
    try {
      listResults = hierarchyService.getHierarchyList();

      if (listResults != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listResults).build();
      } else {
        InformationMessage messageEntity = new InformationMessage(
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            ApplicationConstants.GENERAL_ERROR_API_MESSAGE);
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).entity(messageEntity).build();
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Method that return to client all data about hierarchy of processes.
   * 
   * @return Response with information of {@link OperationsConfigurations} in
   *         JSON format.
   * @throws IOException
   *           kind of exception.
   * @throws ApiException
   *           kind of exception.
   */
  @GET
  @Path("/listMock")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getHierarchyMockList() throws IOException, ApiException {
    List<OperationsConfigurations> listResults = null;
    try {
      listResults = hierarchyService.getHierarchyMockList();
      if (listResults != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listResults).build();
      } else {
        InformationMessage messageEntity = new InformationMessage(
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            ApplicationConstants.GENERAL_ERROR_API_MESSAGE);
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).entity(messageEntity).build();
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * SETTER for Hierarchy Services, Inyected by Spring Context.
   * 
   * @param hierarchyService
   *          the hierarchy services.
   */
  public void setHierarchyService(HierarchyService hierarchyService) {
    this.hierarchyService = hierarchyService;
  }
}
