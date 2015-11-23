package com.bbva.operationalreportingapi.rest.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.hierarchy.OperationsConfigurations;
import com.bbva.operationalreportingapi.rest.beans.hierarchy.Process;
import com.bbva.operationalreportingapi.rest.beans.hierarchy.Subfamily;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;

/**
 * Service class to manage the hierarchy components.
 * 
 * 
 * @author BBVA-ReportingOperacional
 * @implements {@link InformeEjemploService}
 */
@Component
public class HierarchyServiceImpl implements HierarchyService {

  @Autowired
  private MongoOperations mongoOps;

  private static final Logger log = LoggerFactory
      .getLogger(HierarchyServiceImpl.class);

  /**
   * Method that build a mock with data about hierarchy.
   * 
   * @return {@link InformeEjemplo}
   * @throws ApiException
   *           Exception.
   */
  @Override
  public OperationsConfigurations getHierarchyMock() throws ApiException {

    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getHierarchyMock START");
    Long idFamily = 0L;
    OperationsConfigurations family = null;
    try {
      family = new OperationsConfigurations("Family " + idFamily);
      for (Long idSubFamily = 0L; idSubFamily < 5L; idSubFamily++) {

        Subfamily subfamily = new Subfamily("SubFamily " + idSubFamily);
        for (Long idProcess = 0L; idProcess < 4L; idProcess++) {
          Process process = new Process();
          process.setProcess("Process " + "" + idProcess);
          subfamily.getProcesses().add(process);
        }
        family.getSubfamilies().add(subfamily);
      }
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getHierarchyMock.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getHierarchyMock END");
    return family;
  }

  /**
   * Method that sets up example data of hierarchy.
   * 
   * @return {@link OperationsConfigurations}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public OperationsConfigurations createHierarchyMock() throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createHierarchyMock START");
    OperationsConfigurations family = null;

    List<OperationsConfigurations> listFamilies = new ArrayList<OperationsConfigurations>();
    try {
      for (Long idFamily = 0L; idFamily < 20L; idFamily++) {
        family = new OperationsConfigurations("Family " + idFamily);
        for (Long idSubFamily = 0L; idSubFamily < 4L; idSubFamily++) {
          Subfamily subfamily = new Subfamily("SubFamily " + idSubFamily);
          for (Long idProcess = 0L; idProcess < 100L; idProcess++) {
            Process process = new Process();
            process.setProcess(("Process " + idProcess));
            subfamily.getProcesses().add(process);
          }
          family.getSubfamilies().add(subfamily);
        }
        listFamilies.add(family);
      }
      mongoOps.insert(listFamilies, OperationsConfigurations.class);

      family = null;

      family = mongoOps.findById(0, OperationsConfigurations.class);

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createHierarchyMock.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createHierarchyMock END");
    return family;
  }

  /**
   * Method that returns the list of hierarchy from DB.
   * 
   * @return {@link OperationsConfigurations} (List).
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public List<OperationsConfigurations> getHierarchyList() throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getHierarchyList START");
    try {
      Query queryGetHierarchyListOrdered = new Query();

      queryGetHierarchyListOrdered.with(new Sort(Sort.Direction.ASC,
          ApplicationConstants.FIELD_FAMILY))
      // TODO - Completar la ordenación.
      // .with(
      // new Sort(Sort.Direction.ASC, ""
      // + ApplicationConstants.FIELD_SUBFAMILIES_NODE + "."
      // + ApplicationConstants.FIELD_SUBFAMILY + ""))
      // .with(
      // new Sort(Sort.Direction.ASC, ""
      // + ApplicationConstants.FIELD_PROCESSES_NODE + "."
      // + ApplicationConstants.FIELD_PROCESS + ""));
      ;
      // Sorting rules

      // queryRegistrationsInOrdersAggrByDayListWithFilter.with();
      /*
       * results =
       * mongoOps.find(queryRegistrationsInOrdersAggrByDayListWithFilter,
       * DailyRegistration.class);
       */
      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: getHierarchyList END");

      return mongoOps.find(queryGetHierarchyListOrdered,
          OperationsConfigurations.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getHierarchyList.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
  }

  /**
   * Method that returns the mocked old list of hierarchy from DB.
   * 
   * {@link OperationsConfigurations} (List).
   * 
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public List<OperationsConfigurations> getHierarchyMockList()
      throws ApiException {
    try {
      return mongoOps.findAll(OperationsConfigurations.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getHierarchyMockList.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
  }
}
