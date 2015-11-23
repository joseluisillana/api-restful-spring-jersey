package com.bbva.operationalreportingapi.rest.services;

import java.util.List;

import com.bbva.operationalreportingapi.rest.beans.hierarchy.OperationsConfigurations;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 */
public interface HierarchyService {

  /**
   * Method that build a mock with data about hierarchy.
   * 
   * @return {@link InformeEjemplo}
   * @throws ApiException
   *           Exception.
   */
  OperationsConfigurations getHierarchyMock() throws ApiException;

  /**
   * Method that sets up example data of hierarchy.
   * 
   * @return {@link OperationsConfigurations}
   * @throws ApiException
   *           the kind of exception.
   */
  OperationsConfigurations createHierarchyMock() throws ApiException;

  /**
   * Method that returns the list of hierarchy from DB.
   * 
   * @return {@link OperationsConfigurations} (List).
   * @throws ApiException
   *           the kind of exception.
   */
  List<OperationsConfigurations> getHierarchyList() throws ApiException;

  /**
   * Method that returns the mocked old list of hierarchy from DB.
   * 
   * {@link OperationsConfigurations} (List).
   * 
   * @throws ApiException
   *           the kind of exception.
   */
  List<OperationsConfigurations> getHierarchyMockList() throws ApiException;
}
