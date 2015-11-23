package com.bbva.operationalreportingapi.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.DailyProcessAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.errorhandling.InformationMessage;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.services.AnsComplianceFinishedInstService;

/**
 * END-POINT class for the ANS compliance for finished instances grouped by
 * operations report web service resource.
 * 
 * @author BBVA-ReportingOperacional
 */
@Component
@Path("/")
public class AnsComplianceResource {

  @Autowired
  private AnsComplianceFinishedInstService ansComplianceFinishedInstService;

  /**
   * END POINT URL FOR ANS COMPLIANCE BY OPERATION GENERATE DATA.
   */
  private static final String URL_ANS_COMPL_BY_OPER_GEN_DATA = "reports/"
      + "ansCompliance/generateData";
  /**
   * END POINT URL FOR ANS COMPLIANCE BY OPERATION LIST.
   */
  private static final String URL_ANS_COMPL_BY_OPER_LIST = "reports/ansComplianceByOperation/list";
  /**
   * END POINT URL FOR ANS COMPLIANCE BY PROCESS.
   */
  private static final String URL_ANS_COMPL_BY_PROCESS_FOR_A_DAY = "reports/"
      + "ansComplianceByProcess/list/{family}/{subfamily}/{process}/{from}";

  /**
   * END POINT URL FOR ANS COMPLIANCE BY PROCESS FOR A DAY.
   */
  private static final String URL_ANS_COMPL_BY_PROCESS = "reports/"
      + "ansComplianceByProcess/list/{family}/{subfamily}/{process}/{from}/{to}";
  /**
   * END POINT URL FOR ANS COMPLIANCE BY OPERATION.
   */
  private static final String URL_ANS_COMPL_BY_OPER = ""
      + "reports/ansComplianceByOperation/list/{family}/"
      + "{subfamily}/{process}/{operationtype}/{from}/{to}";

  /**
   * END POINT URL FOR ANS COMPLIANCE BY OPERATION FOR A DAY.
   */
  private static final String URL_ANS_COMPL_BY_OPER_FOR_A_DAY = ""
      + "reports/ansComplianceByOperation/list/{family}/"
      + "{subfamily}/{process}/{operationtype}/{from}";

  /**
   * #################### ###################################### TYPE: ANS
   * compliance for finished instances grouped by operations types.
   * ##########################################################
   * **/

  /**
   * Method that create example data 'ANS compliance for finished instances
   * grouped by operations types.' report.
   * 
   * @verb POST
   * @produces JSON
   */
  @POST
  @Path(URL_ANS_COMPL_BY_OPER_GEN_DATA)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createAndPersistsMockDataDailyProcessAnsComplianceExampleData()
      throws IOException, ApiException {

    try {
      ansComplianceFinishedInstService
          .createDailyProcessAnsComplianceExampleData();
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
   * Method (Resources) to query the 'ANS compliance for finished instances
   * grouped by operations types.' without filters neither order fields
   * specified.
   * 
   * @return Response of resource
   * @throws IOException
   *           a kind of exception
   * @throws ApiException
   *           a kind of exception
   */
  @GET
  @Path(URL_ANS_COMPL_BY_OPER_LIST)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getDailyProcessAnsComplianceExampleDataWithoutFiltersPathParam()
      throws IOException, ApiException {
    List<DailyProcessAnsCompliance> listResults = null;

    try {

      listResults = ansComplianceFinishedInstService
          .getDailyProcessAnsComplianceList();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(listResults).build();
  }

  /**
   * Method (Resources) to query the 'ANS compliance for finished instances
   * grouped by operations types.', it aggregates by operations with filters and
   * order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter interval to search
   * @param to
   *          the ending interval to search
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path(URL_ANS_COMPL_BY_PROCESS)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getDailyProcessAnsComplianceListForProcessWithFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("from") Long from,
      @PathParam("to") Long to) throws ApiException {

    List<DailyProcessAnsCompliance> listResults = null;

    try {

      listResults = ansComplianceFinishedInstService
          .getDailyProcessAnsComplianceListForProcessWithFilter(family,
              subfamily, process, from, to);
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
   * Method (Resources) to query the 'ANS compliance for finished instances
   * grouped by operations types.', it aggregates by operations with filters and
   * order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param date
   *          the starter interval to search
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path(URL_ANS_COMPL_BY_PROCESS_FOR_A_DAY)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getDailyProcessAnsComplianceListForProcessWithFilterForADate(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("from") Long date)
      throws ApiException {

    List<DailyProcessAnsCompliance> listResults = null;

    try {

      listResults = ansComplianceFinishedInstService
          .getDailyProcessAnsComplianceListForProcessWithFilter(family,
              subfamily, process, date, null);
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
   * Method (Resources) to query the 'ANS compliance for finished instances
   * grouped by operations types.', it aggregates by operations with filters and
   * order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param operationtype
   *          the operationtype to filter results parameter.
   * @param from
   *          the starter interval to search
   * @param to
   *          the ending interval to search
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path(URL_ANS_COMPL_BY_OPER)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getDailyProcessAnsComplianceForOperation(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process,
      @PathParam("operationtype") String operationtype,
      @PathParam("from") Long from, @PathParam("to") Long to)
      throws ApiException {

    List<DailyProcessAnsCompliance> listResults = null;

    try {

      listResults = ansComplianceFinishedInstService
          .getDailyProcessAnsComplianceListForOperationWithFilters(family,
              subfamily, process, operationtype, from, to);
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
   * Method (Resources) to query the 'ANS compliance for finished instances
   * grouped by operations types.', it aggregates by operations with filters and
   * order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param operationtype
   *          the operationtype to filter results parameter.
   * @param from
   *          the date to search
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path(URL_ANS_COMPL_BY_OPER_FOR_A_DAY)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getDailyProcessAnsComplianceForOperationForADate(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process,
      @PathParam("operationtype") String operationtype,
      @PathParam("from") Long from) throws ApiException {

    List<DailyProcessAnsCompliance> listResults = null;

    try {

      listResults = ansComplianceFinishedInstService
          .getDailyProcessAnsComplianceListForOperationWithFilters(family,
              subfamily, process, operationtype, from, null);
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
   * SETTER for 'ANS compliance for finished instances grouped by operations'
   * service layer, inyected by Spring Context.
   * 
   * @param operationsByProcessService
   *          the hierarchy services.
   */
  public void setAnsComplianceFinishedInstByOpService(
      AnsComplianceFinishedInstService ansComplianceFinishedInstByOpService) {
    this.ansComplianceFinishedInstService = ansComplianceFinishedInstByOpService;
  }
}
