package com.bbva.operationalreportingapi.rest.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ClaimsAnsCompliance;
import com.bbva.operationalreportingapi.rest.beans.collections.DailyProcessAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Service class to manage the Ans Compliance Finished Instances By Operation
 * report components.
 * 
 * @author BBVA-ReportingOperacional
 */
public interface AnsComplianceClaimsService {

  /**
   * Method that create example data about {@link DailyProcessAnsCompliance} .
 * @param endDay 
 * @param endMonth 
 * @param endYear 
 * @param initialDay 
 * @param initialMonth 
 * @param initialYear 
   * 
   * @return {@link DailyProcessAnsCompliance}
   * @throws ApiException
   *           the kind of exception.
   */
  ClaimsAnsCompliance createAndPersistsMockDataClaimsAnsComplianceExampleData(Long initialYear, Long initialMonth, Long initialDay, Long endYear, Long endMonth, Long endDay)
      throws ApiException;

  /**
   * Method that returns all documents in DB about
   * {@link DailyProcessAnsCompliance}.
   * 
   * @return {@link ClaimsAnsCompliance} (List)
   * @throws ApiException
   *           the exception.
   */
  List<ClaimsAnsCompliance> getClaimAnsComplianceList() throws ApiException;

  /**
   * Method that returns the 'ANS compliance for finished instances grouped by
   * operations types.' list aggregated by operations with filters and order
   * field.
   * 
   * @param year
   * @param month
   * @return
   * @throws ApiException
   */
  List<ClaimsAnsCompliance> getClaimAnsComplianceListWithFilter(Long year,
      Long month) throws ApiException;

  /**
   * SETTER for 'Operations by Process' service layer, inyected by Spring
   * Context.
   * 
   * @param reportService
   *          the hierarchy services.
   */
  void setMongoOps(MongoOperations mongoOps);

}
