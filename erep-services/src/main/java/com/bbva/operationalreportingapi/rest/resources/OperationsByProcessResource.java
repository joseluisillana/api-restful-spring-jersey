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

import com.bbva.operationalreportingapi.rest.beans.collections.DailyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.MonthlyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.WeeklyRegistration;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.errorhandling.InformationMessage;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.services.OperationsByProcessService;

/**
 * END-POINT class for the reports web service resource.
 * 
 * @author BBVA-ReportingOperacional
 */
@Component
@Path("/")
public class OperationsByProcessResource {

  @Autowired
  private OperationsByProcessService operationsByProcessService;

  /**
   * ########################################################## TYPE:
   * Registrations in order, operations by process aggregated by day, month or
   * weeks. ##########################################################
   * **/

  /**
   * Method that returns to client, one example of the data for 'Registrations
   * in order aggregated by day' report.
   * 
   * @verb POST
   * @produces JSON
   */
  @POST
  @Path("reports/operationsByProcess/generateDayMockData")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createNonPersistedDataAggregatedByDay() throws IOException,
      ApiException {

    DailyRegistration inOrderByDayDataExample = null;
    try {
      inOrderByDayDataExample = operationsByProcessService
          .getRegistrationsInOrdersAggrByDayMock();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(inOrderByDayDataExample).build();
  }

  /**
   * Method that returns to client, one example of the data for 'Registrations
   * in order aggregated by month report.
   * 
   * @verb POST
   * @produces JSON
   */
  @POST
  @Path("reports/operationsByProcess/generateMonthMockData")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createNonPersistedDataAggregatedByMonth() throws IOException,
      ApiException {

    MonthlyRegistration inOrderByMonthDataExample = null;
    try {
      inOrderByMonthDataExample = operationsByProcessService
          .getRegistrationsInOrdersAggrByMonthMock();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(inOrderByMonthDataExample).build();
  }

  /**
   * Method that returns to client, one example of the data for 'Registrations
   * in order aggregated by Week report.
   * 
   * @verb POST
   * @produces JSON
   */
  @POST
  @Path("reports/operationsByProcess/generateWeekMockData")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createNonPersistedDataAggregatedByWeek() throws IOException,
      ApiException {

    WeeklyRegistration inOrderByWeekDataExample = null;
    try {
      inOrderByWeekDataExample = operationsByProcessService
          .getRegistrationsInOrdersAggrByWeekMock();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(inOrderByWeekDataExample).build();
  }

  /**
   * Method that returns to client, one example of the data for 'Registrations
   * in order aggregated by day' report.
   * 
   * @verb POST
   * @produces JSON
   */
  @POST
  @Path("reports/operationsByProcess/generateData")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response createAndPersistsMockDataOperationByProcessDayMonthWeek()
      throws IOException, ApiException {

    try {
      operationsByProcessService.createRegistrationsInOrdersAggrByWeek();
      operationsByProcessService.createRegistrationsInOrdersAggrByMonth();
      operationsByProcessService.createRegistrationsInOrdersAggrByDay();

    } catch (Exception e) {
      throw e;
    }

    InformationMessage messageEntity = new InformationMessage(
        Response.Status.OK.getStatusCode(),
        Response.Status.CREATED.getStatusCode(),
        ApplicationConstants.GENERAL_ERROR_API_MESSAGE
            + Response.Status.CREATED.getReasonPhrase() + ".");
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(messageEntity).build();
  }

  /**
   * Method (Resources) to query the number of the processes actually in order,
   * it aggregates by day without filters neither order fields specified.
   * 
   * @return Response of resource
   * @throws IOException
   *           a kind of exception
   * @throws ApiException
   *           a kind of exception
   */
  @GET
  @Path("reports/operationsByProcess/" + ApplicationConstants.AGGR_BY_DAY)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggrByDayWithoutFiltersPathParam()
      throws IOException, ApiException {
    List<DailyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByDayList();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(listResults).build();
  }

  /**
   * Method (Resources) to query the number of the processes actually in order,
   * it aggregates by month without filters neither order fields specified.
   * 
   * @return Response of resource
   * @throws IOException
   *           a kind of exception
   * @throws ApiException
   *           a kind of exception
   */
  @GET
  @Path("reports/operationsByProcess/" + ApplicationConstants.AGGR_BY_MONTH)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggrByMonthWithoutFiltersPathParam()
      throws IOException, ApiException {
    List<MonthlyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByMonthList();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(listResults).build();
  }

  /**
   * Method (Resources) to query the number of the processes actually in order,
   * it aggregates by week without filters neither order fields specified.
   * 
   * @return Response of resource
   * @throws IOException
   *           a kind of exception
   * @throws ApiException
   *           a kind of exception
   */
  @GET
  @Path("reports/operationsByProcess/" + ApplicationConstants.AGGR_BY_WEEK)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggrByWeekWithoutFiltersPathParam()
      throws IOException, ApiException {
    List<WeeklyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByWeekList();
    } catch (Exception e) {
      throw e;
    }
    return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(listResults).build();
  }

  /**
   * Method (Resources) that shows an information message to client, asks
   * him/her for parameters.
   * 
   * @return {@link Response}
   * @throws IOException
   *           Input/Output exception.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedWhitFilter()
      throws IOException, ApiException {
    InformationMessage messageEntity = new InformationMessage(
        Response.Status.BAD_REQUEST.getStatusCode(),
        Response.Status.BAD_REQUEST.getStatusCode(),
        ApplicationConstants.GENERAL_ERROR_API_MESSAGE
            + ApplicationConstants.INCOMPLETE_REQUEST_API_MESSAGE);
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(messageEntity).build();
  }

  /**
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by day whit filters and order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter interval to filter results parameter.
   * @param to
   *          the ending interval to filter results parameter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_DAY + "/{from}/{to}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByDayWithFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("from") String from,
      @PathParam("to") String to) throws ApiException {

    List<DailyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByDayListWithFilter(family, subfamily,
              process, from, to);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by month whit filters and order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter interval to filter results parameter.
   * @param to
   *          the ending interval to filter results parameter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_MONTH + "/{from}/{to}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByMonthWithFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("from") String from,
      @PathParam("to") String to) throws ApiException {

    List<MonthlyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByMonthListWithFilter(family, subfamily,
              process, from, to);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by week whit filters and order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter interval to filter results parameter.
   * @param to
   *          the ending interval to filter results parameter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_WEEK + "/{from}/{to}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByWeekWithFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("from") String from,
      @PathParam("to") String to) throws ApiException {

    List<WeeklyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByWeekListWithFilter(family, subfamily,
              process, from, to);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by day for one specific date, it uses filters and orders field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param date
   *          the date to filter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_DAY + "/{date}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByDayForCertainDate(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("date") String date)
      throws ApiException {

    List<DailyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByDayListWithFilter(family, subfamily,
              process, date, null);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by month for one specific date, it uses filters and orders
   * field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param date
   *          the date to filter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_MONTH + "/{date}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByMonthForCertainDate(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("date") String date)
      throws ApiException {

    List<MonthlyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByMonthListWithFilter(family, subfamily,
              process, date, null);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by month for one specific date, it uses filters and orders
   * field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param date
   *          the date to filter.
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_WEEK + "/{date}")
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByWeekForCertainDate(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process, @PathParam("date") String date)
      throws ApiException {

    List<WeeklyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByWeekListWithFilter(family, subfamily,
              process, date, null);
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
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by day whit .
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * 
   * @return {@link Response}
   * @throws IOException
   *           Input/Output exception.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_DAY)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByDayWithoutFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process) throws IOException, ApiException {

    List<DailyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByDayListWithFilter(family, subfamily,
              process, null, null);
      if (listResults != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listResults).build();
      } else {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).build();
      }

    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by month whit filters and order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * 
   * @return {@link Response}
   * @throws IOException
   *           Input/Output exception.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_MONTH)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByMonthWithoutFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process) throws IOException, ApiException {

    List<MonthlyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByMonthListWithFilter(family, subfamily,
              process, null, null);
      if (listResults != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listResults).build();
      } else {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).build();
      }

    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * Method (Resources) to query the number of the process actually in order, it
   * aggregates by week whit filters and order field.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * 
   * @return {@link Response}
   * @throws IOException
   *           Input/Output exception.
   * @throws ApiException
   *           The kind of exception.
   */
  @GET
  @Path("reports/operationsByProcess/{family}/{subfamily}/{process}/"
      + ApplicationConstants.AGGR_BY_WEEK)
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getRegistrationsInOrderAggregatedByWeekWithoutFilter(
      @PathParam("family") String family,
      @PathParam("subfamily") String subfamily,
      @PathParam("process") String process) throws IOException, ApiException {

    List<WeeklyRegistration> listResults = null;

    try {

      listResults = operationsByProcessService
          .getRegistrationsInOrdersAggrByWeekListWithFilter(family, subfamily,
              process, null, null);
      if (listResults != null) {
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listResults).build();
      } else {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .type(MediaType.APPLICATION_JSON).build();
      }

    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * SETTER for 'Operations by Process' service layer, inyected by Spring
   * Context.
   * 
   * @param operationsByProcessService
   *          the hierarchy services.
   */
  public void setOperationsByProcessService(
      OperationsByProcessService operationsByProcessService) {
    this.operationsByProcessService = operationsByProcessService;
  }
}
