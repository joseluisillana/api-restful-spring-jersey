package com.bbva.operationalreportingapi.rest.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.DailyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.MonthlyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.WeeklyRegistration;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 *          Java generics type of report
 */
public interface OperationsByProcessService {

  /**
   * Method that build a mock with data about {@link DailyRegistration}.
   * 
   * @return {@link DailyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  DailyRegistration getRegistrationsInOrdersAggrByDayMock() throws ApiException;

  /**
   * Method that build a mock with data about {@link MonthlyRegistration}.
   * 
   * @return {@link MonthlyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  MonthlyRegistration getRegistrationsInOrdersAggrByMonthMock()
      throws ApiException;

  /**
   * Method that build a mock with data about {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  WeeklyRegistration getRegistrationsInOrdersAggrByWeekMock()
      throws ApiException;

  /**
   * Method that create example data about {@link DailyRegistration} .
   * 
   * @return {@link DailyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  DailyRegistration createRegistrationsInOrdersAggrByDay() throws ApiException;

  /**
   * Method that create example data about {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  WeeklyRegistration createRegistrationsInOrdersAggrByWeek()
      throws ApiException;

  /**
   * Method that create example data about {@link MonthlyRegistration} .
   * 
   * @return {@link MonthlyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  MonthlyRegistration createRegistrationsInOrdersAggrByMonth()
      throws ApiException;

  /**
   * Method that returns all documents in DB about {@link DailyRegistration}.
   * 
   * @return {@link DailyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  List<DailyRegistration> getRegistrationsInOrdersAggrByDayList()
      throws ApiException;

  /**
   * Method that returns all documents in DB about months
   * {@link MonthlyRegistration}.
   * 
   * @return {@link MonthlyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  List<MonthlyRegistration> getRegistrationsInOrdersAggrByMonthList()
      throws ApiException;

  /**
   * Method that returns all documents in DB about week
   * {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  List<WeeklyRegistration> getRegistrationsInOrdersAggrByWeekList()
      throws ApiException;

  /**
   * Method that returns all documents in DB about {@link DailyRegistration}
   * that matches within filter.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter point of interval filter.
   * @param to
   *          the end point of interval filter.
   * @return {@link DailyRegistration}. (List)
   * @throws ApiException
   *           the kind of exception.
   */
  List<DailyRegistration> getRegistrationsInOrdersAggrByDayListWithFilter(
      String family, String subfamily, String process, String from, String to)
      throws ApiException;

  /**
   * Method that returns all documents in DB about {@link MonthlyRegistration}
   * that matches within filter.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter point of interval filter.
   * @param to
   *          the end point of interval filter.
   * @return {@link MonthlyRegistration}. (List)
   * @throws ApiException
   *           the kind of exception.
   */
  List<MonthlyRegistration> getRegistrationsInOrdersAggrByMonthListWithFilter(
      String family, String subfamily, String process, String from, String to)
      throws ApiException;

  /**
   * Method that returns all documents in DB about {@link WeeklyRegistration}
   * that matches within filter.
   * 
   * @param family
   *          the family parameter.
   * @param subfamily
   *          the subfamily parameter.
   * @param process
   *          the process parameter.
   * @param from
   *          the starter point of interval filter.
   * @param to
   *          the end point of interval filter.
   * @return {@link WeeklyRegistration}. (List)
   * @throws ApiException
   *           the kind of exception.
   */
  List<WeeklyRegistration> getRegistrationsInOrdersAggrByWeekListWithFilter(
      String family, String subfamily, String process, String from, String to)
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
