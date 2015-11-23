package com.bbva.operationalreportingapi.rest.services;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.DailyProcessAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Service class to manage the Ans Compliance Finished Instances By Operation
 * report components.
 * 
 * @author BBVA-ReportingOperacional
 */
public interface AnsComplianceFinishedInstService {

  /**
   * Method that create example data about {@link DailyProcessAnsCompliance} .
   * 
   * @return {@link DailyProcessAnsCompliance}
   * @throws ApiException
   *           the kind of exception.
   */
  DailyProcessAnsCompliance createDailyProcessAnsComplianceExampleData()
      throws ApiException;

  /**
   * Method that returns all documents in DB about
   * {@link DailyProcessAnsCompliance}.
   * 
   * @return {@link DailyProcessAnsCompliance} (List)
   * @throws ApiException
   *           the exception.
   */
  List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceList()
      throws ApiException;

  /**
   * Method that returns the 'ANS compliance for finished instances grouped by
   * operations types.' list aggregated by operations with filters and order
   * field.
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

  List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceListForProcessWithFilter(
      String family, String subfamily, String process, Long from, Long to)
      throws ApiException;

  /**
   * Method that returns the 'ANS compliance for finished instances grouped by
   * operations types.' list for an operation type (family, subfamily, process)
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param operationtype
   *          the operation type to filter results parameter.
   * @param from
   *          the starter interval to search
   * @param to
   *          the ending interval to search
   * @return {@link Response}.
   * @throws ApiException
   *           The kind of exception.
   */
  List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceListForOperationWithFilters(
      String family, String subfamily, String process, String operationtype,
      Long from, Long to) throws ApiException;

  /**
   * SETTER for 'Operations by Process' service layer, inyected by Spring
   * Context.
   * 
   * @param reportService
   *          the hierarchy services.
   */
  void setMongoOps(MongoOperations mongoOps);

}
