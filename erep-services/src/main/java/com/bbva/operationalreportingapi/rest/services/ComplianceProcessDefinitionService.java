package com.bbva.operationalreportingapi.rest.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ProcessDefinition;
import com.bbva.operationalreportingapi.rest.beans.collections.DailyProcessAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Service class to manage the Ans Compliance Finished Instances By Operation
 * report components.
 * 
 * @author BBVA-ReportingOperacional
 */
public interface ComplianceProcessDefinitionService {

  /**
   * Method that create example data about {@link DailyProcessAnsCompliance} .
   * 
   * @return {@link DailyProcessAnsCompliance}
   * @throws ApiException
   *           the kind of exception.
   */
  ProcessDefinition createAndPersistsMockDataComplianceProcessDefinitionExampleData()
      throws ApiException;

  /**
   * Method that returns all documents in DB about
   * {@link DailyProcessAnsCompliance}.
   * 
   * @return {@link DailyProcessAnsCompliance} (List)
   * @throws ApiException
   *           the exception.
   */
  List<ProcessDefinition> getComplianceProcessDefinitionList()
      throws ApiException;

  /**
   * SETTER for 'Operations by Process' service layer, inyected by Spring
   * Context.
   * 
   * @param reportService
   *          the hierarchy services.
   */
  void setMongoOps(MongoOperations mongoOps);

}
