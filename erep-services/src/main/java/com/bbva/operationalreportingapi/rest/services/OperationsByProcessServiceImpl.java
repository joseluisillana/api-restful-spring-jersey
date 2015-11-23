package com.bbva.operationalreportingapi.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.AmountOperationsByType;
import com.bbva.operationalreportingapi.rest.beans.collections.DailyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.MonthlyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.WeeklyRegistration;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 *          Java generics type of report
 * @implements {@link InformeEjemploService}
 */
@Component
public class OperationsByProcessServiceImpl implements
    OperationsByProcessService {

  @Autowired
  private MongoOperations mongoOps;

  private static final Logger log = LoggerFactory
      .getLogger(OperationsByProcessServiceImpl.class);

  private static final String PROCESS_NAME_MOCK = "FLUJO 6 ESTADOS TdP";
  private static final String SUBFAMILY_NAME_MOCK = "PRUEBAS SUITE BPM";
  private static final String FAMILY_NAME_MOCK = "FORMULARIO PRUEBAS";
  private static final String OPERATIONTYPE_NAME_MOCK = "PCAJAS";
  private static final String DAILY_DATE_MOCK = "20150204";
  private static final String MONTHLY_DATE_MOCK = "201502";
  private static final String WEEKLY_DATE_MOCK = "201524";
  private static final String DAILY_DATE_FORMAT = "yyyyMMdd";
  private static final String MONTHLY_DATE_FORMAT = "yyyyMM";
  private static final String WEEKLY_DATE_FORMAT = "yyyyw";

  /** FAMILIES TO CREATE EXAMPLE DATA. */
  private static final String FORMULARIO_PRUEBAS = "FORMULARIO PRUEBAS";
  private static final String FORMULARIO_TALLER_DE_PROCESOS = "FORMULARIO TALLER DE PROCESOS";

  /** SUBFAMILIES TO CREATE EXAMPLE DATA. */
  private static final String REESTRUCTURACION_RDL = "Reestructuracion RDL";
  private static final String PRUEBAS_SUITE_BPM = "PRUEBAS SUITE BPM";
  private static final String TARJETAS = "TARJETAS";
  private static final String COBROS_Y_PAGOS = "COBROS Y PAGOS";

  /** PROCESSES TO CREATE EXAMPLE DATA. */
  private static final String FLUJO_4_ESTADOS_TD_P = "FLUJO 4 ESTADOS TdP";
  private static final String FLUJO_6_ESTADOS_TD_P = "FLUJO 6 ESTADOS TdP";
  private static final String FLUJO_8_ESTADOS_TD_P = "FLUJO 8 ESTADOS TdP";
  private static final String FLUJO_10_ESTADOS_TD_P = "FLUJO 10 ESTADOS TdP";
  private static final String FLUJO_12_ESTADOS_TD_P = "FLUJO 12 ESTADOS TdP";

  /** OPERATIONS TYPES TO CREATE EXAMPLE DATA. */
  private static final String PU2 = "PU2";
  private static final String _043 = "043";
  private static final String PCAJAS = "PCAJAS";
  private static final String ALTCED = "ALTCED";
  private static final String BPR = "BPR";
  private static final String EMITRF = "EMITRF";
  private static final String PETOMF = "PETOMF";
  private static final String PETEFE = "PETEFE";
  private static final String FORCOM = "FORCOM";

  static final Map<String, Map<String, Map<String, Set<String>>>> FAMILIES_MOCK;
  static final Map<String, Map<String, Set<String>>> SUBFAMILIES_MOCK;
  static final Map<String, Set<String>> PROCESSES_MOCK;
  static final Set<String> OPERTYPES_MOCK;

  static {
    FAMILIES_MOCK = new HashMap<String, Map<String, Map<String, Set<String>>>>();
    SUBFAMILIES_MOCK = new HashMap<String, Map<String, Set<String>>>();
    PROCESSES_MOCK = new HashMap<String, Set<String>>();
    OPERTYPES_MOCK = new HashSet<String>();

    // #####################
    // For family FORMULARIO PRUEBAS, subfamily PRUEBAS SUITE BPM,
    // Reestructuracion RDL
    // #####################

    // #####################
    // For subfamily PRUEBAS SUITE BPM
    // #####################
    // Operations type for process FLUJO 6 ESTADOS TdP
    OPERTYPES_MOCK.add(PU2);
    OPERTYPES_MOCK.add(_043);
    OPERTYPES_MOCK.add(PCAJAS);

    PROCESSES_MOCK.put(FLUJO_6_ESTADOS_TD_P,
        new HashSet<String>(OPERTYPES_MOCK));

    // Operations type for process FLUJO 4 ESTADOS TdP
    OPERTYPES_MOCK.clear();
    OPERTYPES_MOCK.add(ALTCED);

    PROCESSES_MOCK.put(FLUJO_4_ESTADOS_TD_P,
        new HashSet<String>(OPERTYPES_MOCK));

    // Operations type for process FLUJO 8 ESTADOS TdP
    OPERTYPES_MOCK.clear();
    OPERTYPES_MOCK.add("PU4");

    PROCESSES_MOCK.put(FLUJO_8_ESTADOS_TD_P,
        new HashSet<String>(OPERTYPES_MOCK));

    SUBFAMILIES_MOCK.put(PRUEBAS_SUITE_BPM, new HashMap<String, Set<String>>(
        PROCESSES_MOCK));

    // #####################
    // For subfamily Reestructuracion RDL
    // #####################
    PROCESSES_MOCK.clear();
    OPERTYPES_MOCK.clear();

    // Operations type for process FLUJO 12 ESTADOS TdP
    OPERTYPES_MOCK.add(BPR);

    PROCESSES_MOCK.put(FLUJO_12_ESTADOS_TD_P, new HashSet<String>(
        OPERTYPES_MOCK));

    SUBFAMILIES_MOCK.put(REESTRUCTURACION_RDL,
        new HashMap<String, Set<String>>(PROCESSES_MOCK));

    FAMILIES_MOCK.put(FORMULARIO_PRUEBAS,
        new HashMap<String, Map<String, Set<String>>>(SUBFAMILIES_MOCK));

    // #####################
    // For family FORMULARIO TALLER DE PROCESOS, subfamily COBROS Y PAGOS,
    // TARJETAS
    // #####################
    SUBFAMILIES_MOCK.clear();

    // #####################
    // For subfamily COBROS Y PAGOS
    // #####################
    OPERTYPES_MOCK.clear();
    PROCESSES_MOCK.clear();
    // Operations type for process FLUJO 10 ESTADOS TdP
    OPERTYPES_MOCK.add(EMITRF);

    PROCESSES_MOCK.put(FLUJO_10_ESTADOS_TD_P, new HashSet<String>(
        OPERTYPES_MOCK));

    // Operations type for process FLUJO 4 ESTADOS TdP
    OPERTYPES_MOCK.clear();
    OPERTYPES_MOCK.add(PETOMF);

    PROCESSES_MOCK.put(FLUJO_4_ESTADOS_TD_P,
        new HashSet<String>(OPERTYPES_MOCK));

    // Operations type for process FLUJO 12 ESTADOS TdP
    OPERTYPES_MOCK.clear();
    OPERTYPES_MOCK.add(PETEFE);

    PROCESSES_MOCK.put(FLUJO_12_ESTADOS_TD_P, new HashSet<String>(
        OPERTYPES_MOCK));

    SUBFAMILIES_MOCK.put(COBROS_Y_PAGOS, new HashMap<String, Set<String>>(
        PROCESSES_MOCK));

    // #####################
    // For subfamily TARJETAS
    // #####################
    OPERTYPES_MOCK.clear();
    PROCESSES_MOCK.clear();
    // Operations type for process FLUJO 10 ESTADOS TdP
    OPERTYPES_MOCK.add(FORCOM);

    PROCESSES_MOCK.put(FLUJO_10_ESTADOS_TD_P, new HashSet<String>(
        OPERTYPES_MOCK));

    SUBFAMILIES_MOCK.put(TARJETAS, new HashMap<String, Set<String>>(
        PROCESSES_MOCK));

    FAMILIES_MOCK.put(FORMULARIO_TALLER_DE_PROCESOS,
        new HashMap<String, Map<String, Set<String>>>(SUBFAMILIES_MOCK));
  }

  /**
   * Method that build a mock with data about {@link DailyRegistration}.
   * 
   * @return {@link DailyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public DailyRegistration getRegistrationsInOrdersAggrByDayMock()
      throws ApiException {

    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByDayMock START");
    DailyRegistration registrationInOrderByDay = new DailyRegistration();
    registrationInOrderByDay.setProcess(PROCESS_NAME_MOCK + 0);
    registrationInOrderByDay.setFamily(FAMILY_NAME_MOCK + 0);
    registrationInOrderByDay.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
    registrationInOrderByDay.setDate(DAILY_DATE_MOCK);
    try {
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByDay.getTotal().add(amount);
      }
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByDayMock.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByDayMock END");
    return registrationInOrderByDay;
  }

  /**
   * Method that build a mock with data about {@link MonthlyRegistration}.
   * 
   * @return {@link MonthlyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public MonthlyRegistration getRegistrationsInOrdersAggrByMonthMock()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByMonthMock START");
    MonthlyRegistration registrationInOrderByMonth = new MonthlyRegistration();
    registrationInOrderByMonth.setProcess(PROCESS_NAME_MOCK + 0);
    registrationInOrderByMonth.setFamily(FAMILY_NAME_MOCK + 0);
    registrationInOrderByMonth.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
    registrationInOrderByMonth.setDate(MONTHLY_DATE_MOCK);
    try {
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByMonth.getTotal().add(amount);
      }
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByMonthMock.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByMonthMock END");
    return registrationInOrderByMonth;
  }

  /**
   * Method that build a mock with data about {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public WeeklyRegistration getRegistrationsInOrdersAggrByWeekMock()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByWeekMock START");
    WeeklyRegistration registrationInOrderByWeek = new WeeklyRegistration();
    registrationInOrderByWeek.setProcess(PROCESS_NAME_MOCK + 0);
    registrationInOrderByWeek.setFamily(FAMILY_NAME_MOCK + 0);
    registrationInOrderByWeek.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
    registrationInOrderByWeek.setDate(WEEKLY_DATE_MOCK);
    try {
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByWeek.getTotal().add(amount);
      }

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByWeekMock.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByWeekMock END");
    return registrationInOrderByWeek;
  }

  /**
   * Method that create example data about {@link DailyRegistration} .
   * 
   * @return {@link DailyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public DailyRegistration createRegistrationsInOrdersAggrByDay()
      throws ApiException {

    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByDay START");
    List<DailyRegistration> listRegs = new ArrayList<DailyRegistration>();
    DailyRegistration registrationInOrderByDay = null;

    DateFormat dateFormater = new SimpleDateFormat(DAILY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.MONTH, Calendar.JANUARY);
    startCal.set(Calendar.DAY_OF_MONTH, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2016);
    endCal.set(Calendar.MONTH, Calendar.JANUARY);
    endCal.set(Calendar.DAY_OF_MONTH, 1);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {
        registrationInOrderByDay = new DailyRegistration();
        String formattedDate = dateFormater.format(startDate);
        Iterator<Entry<String, Map<String, Map<String, Set<String>>>>> iteratorFamilies;
        iteratorFamilies = FAMILIES_MOCK.entrySet().iterator();

        while (iteratorFamilies.hasNext()) {
          Map.Entry<String, Map<String, Map<String, Set<String>>>> familyItem = iteratorFamilies
              .next();
          // set the family name mock
          registrationInOrderByDay.setFamily(familyItem.getKey());

          Iterator<Entry<String, Map<String, Set<String>>>> iteratorSubFamilies = familyItem
              .getValue().entrySet().iterator();

          while (iteratorSubFamilies.hasNext()) {
            Map.Entry<String, Map<String, Set<String>>> subFamilyItem = iteratorSubFamilies
                .next();
            // set the subfamily name mock
            registrationInOrderByDay.setSubfamily(subFamilyItem.getKey());

            Iterator<Entry<String, Set<String>>> iteratorProcesses = subFamilyItem
                .getValue().entrySet().iterator();

            while (iteratorProcesses.hasNext()) {
              Map.Entry<String, Set<String>> processItem = iteratorProcesses
                  .next();

              // set the process name mock
              registrationInOrderByDay.setProcess(processItem.getKey());

              Set<AmountOperationsByType> amountSet = new HashSet<AmountOperationsByType>();
              for (String operationType : processItem.getValue()) {
                Random rn = new Random();
                int range = 100 - 10 + 1;
                int randomNum = rn.nextInt(range) + 10;

                // set the operation type and the amount
                AmountOperationsByType amount = new AmountOperationsByType(
                    operationType, randomNum);
                amountSet.add(amount);

              }
              registrationInOrderByDay.setTotal(amountSet);
              registrationInOrderByDay.setDate(formattedDate);
              listRegs.add((DailyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByDay));
            }
          }

          mongoOps.insert(listRegs, DailyRegistration.class);
          listRegs.clear();
        }
        startCal.add(Calendar.DAY_OF_MONTH, 1);
        startDate = startCal.getTime();
      }

      registrationInOrderByDay = null;
      String filterSearch = "F";

      BasicQuery query = new BasicQuery("{\"process\": {$regex : '"
          + filterSearch + "'} }");
      query.limit(1);

      registrationInOrderByDay = mongoOps.find(query, DailyRegistration.class)
          .get(0);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createRegistrationsInOrdersAggrByDay.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByDay END");
    return registrationInOrderByDay;
  }

  /**
   * Method that create example data about {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public WeeklyRegistration createRegistrationsInOrdersAggrByWeek()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByWeek START");
    List<WeeklyRegistration> listRegs = new ArrayList<WeeklyRegistration>();
    WeeklyRegistration registrationInOrderByWeek = null;
    DateFormat dateFormater = new SimpleDateFormat(WEEKLY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.WEEK_OF_YEAR, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2016);
    endCal.set(Calendar.WEEK_OF_YEAR, 1);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {

        registrationInOrderByWeek = new WeeklyRegistration();
        String formattedDate = dateFormater.format(startDate);
        Iterator<Entry<String, Map<String, Map<String, Set<String>>>>> iteratorFamilies;
        iteratorFamilies = FAMILIES_MOCK.entrySet().iterator();

        while (iteratorFamilies.hasNext()) {
          Map.Entry<String, Map<String, Map<String, Set<String>>>> familyItem = iteratorFamilies
              .next();
          // set the family name mock
          registrationInOrderByWeek.setFamily(familyItem.getKey());
          Iterator<Entry<String, Map<String, Set<String>>>> iteratorSubFamilies = familyItem
              .getValue().entrySet().iterator();

          while (iteratorSubFamilies.hasNext()) {
            Map.Entry<String, Map<String, Set<String>>> subFamilyItem = iteratorSubFamilies
                .next();
            // set the subfamily name mock
            registrationInOrderByWeek.setSubfamily(subFamilyItem.getKey());

            Iterator<Entry<String, Set<String>>> iteratorProcesses = subFamilyItem
                .getValue().entrySet().iterator();

            while (iteratorProcesses.hasNext()) {
              Map.Entry<String, Set<String>> processItem = iteratorProcesses
                  .next();

              // set the process name mock
              registrationInOrderByWeek.setProcess(processItem.getKey());
              Set<AmountOperationsByType> amountSet = new HashSet<AmountOperationsByType>();
              for (String operationType : processItem.getValue()) {
                Random rn = new Random();
                int range = 100 - 10 + 1;
                int randomNum = rn.nextInt(range) + 10;

                // set the operation type and the amount
                AmountOperationsByType amount = new AmountOperationsByType(
                    operationType, randomNum);
                amountSet.add(amount);

              }
              registrationInOrderByWeek.setTotal(amountSet);
              registrationInOrderByWeek.setDate(formattedDate);
              listRegs.add((WeeklyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByWeek));
            }
          }

          mongoOps.insert(listRegs, WeeklyRegistration.class);
          listRegs.clear();
        }

        startCal.add(Calendar.WEEK_OF_YEAR, 1);
        startDate = startCal.getTime();
      }

      registrationInOrderByWeek = null;

      String filterSearch = "F";

      BasicQuery query = new BasicQuery("{\"process\": {$regex : '"
          + filterSearch + "'} }");
      query.limit(1);

      registrationInOrderByWeek = mongoOps
          .find(query, WeeklyRegistration.class).get(0);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createRegistrationsInOrdersAggrByWeek.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByWeek END");
    return registrationInOrderByWeek;

  }

  /**
   * Method that create example data about {@link MonthlyRegistration} .
   * 
   * @return {@link MonthlyRegistration}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public MonthlyRegistration createRegistrationsInOrdersAggrByMonth()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByDay START");
    List<MonthlyRegistration> listRegs = new ArrayList<MonthlyRegistration>();
    MonthlyRegistration registrationInOrderByMonth = null;
    DateFormat dateFormater = new SimpleDateFormat(MONTHLY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.MONTH, Calendar.JANUARY);
    startCal.set(Calendar.MONTH, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2016);
    endCal.set(Calendar.MONTH, Calendar.JANUARY);
    endCal.set(Calendar.MONTH, 1);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {

        registrationInOrderByMonth = new MonthlyRegistration();
        String formattedDate = dateFormater.format(startDate);
        Iterator<Entry<String, Map<String, Map<String, Set<String>>>>> iteratorFamilies;
        iteratorFamilies = FAMILIES_MOCK.entrySet().iterator();

        while (iteratorFamilies.hasNext()) {
          Map.Entry<String, Map<String, Map<String, Set<String>>>> familyItem = iteratorFamilies
              .next();
          // set the family name mock
          registrationInOrderByMonth.setFamily(familyItem.getKey());
          Iterator<Entry<String, Map<String, Set<String>>>> iteratorSubFamilies = familyItem
              .getValue().entrySet().iterator();

          while (iteratorSubFamilies.hasNext()) {
            Map.Entry<String, Map<String, Set<String>>> subFamilyItem = iteratorSubFamilies
                .next();
            // set the subfamily name mock
            registrationInOrderByMonth.setSubfamily(subFamilyItem.getKey());

            Iterator<Entry<String, Set<String>>> iteratorProcesses = subFamilyItem
                .getValue().entrySet().iterator();

            while (iteratorProcesses.hasNext()) {
              Map.Entry<String, Set<String>> processItem = iteratorProcesses
                  .next();

              // set the process name mock
              registrationInOrderByMonth.setProcess(processItem.getKey());
              Set<AmountOperationsByType> amountSet = new HashSet<AmountOperationsByType>();
              for (String operationType : processItem.getValue()) {
                Random rn = new Random();
                int range = 100 - 10 + 1;
                int randomNum = rn.nextInt(range) + 10;

                // set the operation type and the amount
                AmountOperationsByType amount = new AmountOperationsByType(
                    operationType, randomNum);
                amountSet.add(amount);

              }
              registrationInOrderByMonth.setTotal(amountSet);
              registrationInOrderByMonth.setDate(formattedDate);
              listRegs.add((MonthlyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByMonth));
            }
          }

          mongoOps.insert(listRegs, MonthlyRegistration.class);
          listRegs.clear();
        }

        startCal.add(Calendar.MONTH, 1);
        startDate = startCal.getTime();

      }

      String filterSearch = "F";

      BasicQuery query = new BasicQuery("{\"process\": {$regex : '"
          + filterSearch + "'} }");
      query.limit(1);

      registrationInOrderByMonth = mongoOps.find(query,
          MonthlyRegistration.class).get(0);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createRegistrationsInOrdersAggrByMonth.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createRegistrationsInOrdersAggrByDay END");
    return registrationInOrderByMonth;

  }

  /**
   * Method that returns all documents in DB about days
   * {@link DailyRegistration}.
   * 
   * @return {@link DailyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  @Override
  public List<DailyRegistration> getRegistrationsInOrdersAggrByDayList()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByDayList START");

    try {
      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByDayList END");
      return mongoOps.findAll(DailyRegistration.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByDayList.");

      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }

  }

  /**
   * Method that returns all documents in DB about months
   * {@link MonthlyRegistration}.
   * 
   * @return {@link MonthlyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  @Override
  public List<MonthlyRegistration> getRegistrationsInOrdersAggrByMonthList()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByMonthList START");
    try {
      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByMonthList END");
      return mongoOps.findAll(MonthlyRegistration.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createRegistrationsInOrdersAggrByDay.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
  }

  /**
   * Method that returns all documents in DB about week
   * {@link WeeklyRegistration}.
   * 
   * @return {@link WeeklyRegistration} (List)
   * @throws ApiException
   *           the exception.
   */
  @Override
  public List<WeeklyRegistration> getRegistrationsInOrdersAggrByWeekList()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByWeekList START");
    try {
      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByWeekList END");
      return mongoOps.findAll(WeeklyRegistration.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByWeekList.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
  }

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
  @Override
  public List<DailyRegistration> getRegistrationsInOrdersAggrByDayListWithFilter(
      String family, String subfamily, String process, String from, String to)
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByDayListWithFilter START");
    List<DailyRegistration> results;

    try {
      Query queryRegistrationsInOrdersAggrByDayListWithFilter = new Query();
      queryRegistrationsInOrdersAggrByDayListWithFilter
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_FAMILY).is(family))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_SUBFAMILY)
                  .is(subfamily))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_PROCESS).is(process));

      if (from != null && to != null) {
        queryRegistrationsInOrdersAggrByDayListWithFilter.addCriteria(Criteria
            .where(ApplicationConstants.FIELD_DATE)
            .gte(from)
            .andOperator(
                Criteria.where(ApplicationConstants.FIELD_DATE).lte(to)));
      } else if (from != null) {
        queryRegistrationsInOrdersAggrByDayListWithFilter.addCriteria(Criteria
            .where(ApplicationConstants.FIELD_DATE).is(from));
      }

      // Sorting rules
      queryRegistrationsInOrdersAggrByDayListWithFilter.with(new Sort(
          Sort.Direction.ASC, ApplicationConstants.FIELD_DATE));

      results = mongoOps.find(
          queryRegistrationsInOrdersAggrByDayListWithFilter,
          DailyRegistration.class);

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByDayListWithFilter.");

      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByDayListWithFilter END");
    return results;
  }

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
  @Override
  public List<MonthlyRegistration> getRegistrationsInOrdersAggrByMonthListWithFilter(
      String family, String subfamily, String process, String from, String to)
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByMonthListWithFilter START");
    List<MonthlyRegistration> results;

    try {
      Query queryRegistrationsInOrdersAggrByMonthListWithFilter = new Query();
      queryRegistrationsInOrdersAggrByMonthListWithFilter
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_FAMILY).is(family))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_SUBFAMILY)
                  .is(subfamily))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_PROCESS).is(process));
      if (from != null && to != null) {
        queryRegistrationsInOrdersAggrByMonthListWithFilter
            .addCriteria(Criteria
                .where(ApplicationConstants.FIELD_DATE)
                .gte(from)
                .andOperator(
                    Criteria.where(ApplicationConstants.FIELD_DATE).lte(to)));
      } else if (from != null) {
        queryRegistrationsInOrdersAggrByMonthListWithFilter
            .addCriteria(Criteria.where(ApplicationConstants.FIELD_DATE).is(
                from));
      }

      // Sorting rules
      queryRegistrationsInOrdersAggrByMonthListWithFilter.with(new Sort(
          Sort.Direction.ASC, ApplicationConstants.FIELD_DATE));

      results = mongoOps.find(
          queryRegistrationsInOrdersAggrByMonthListWithFilter,
          MonthlyRegistration.class);

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByMonthListWithFilter.");

      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByMonthListWithFilter END");
    return results;
  }

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
  @Override
  public List<WeeklyRegistration> getRegistrationsInOrdersAggrByWeekListWithFilter(
      String family, String subfamily, String process, String from, String to)
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByWeekListWithFilter START");

    List<WeeklyRegistration> results;

    try {
      Query queryRegistrationsInOrdersAggrByWeekListWithFilter = new Query();

      queryRegistrationsInOrdersAggrByWeekListWithFilter
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_FAMILY).is(family))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_SUBFAMILY)
                  .is(subfamily))
          .addCriteria(
              Criteria.where(ApplicationConstants.FIELD_PROCESS).is(process));
      if (from != null && to != null) {
        queryRegistrationsInOrdersAggrByWeekListWithFilter.addCriteria(Criteria
            .where(ApplicationConstants.FIELD_DATE)
            .gte(from)
            .andOperator(
                Criteria.where(ApplicationConstants.FIELD_DATE).lte(to)));
      } else if (from != null) {
        queryRegistrationsInOrdersAggrByWeekListWithFilter.addCriteria(Criteria
            .where(ApplicationConstants.FIELD_DATE).is(from));
      }

      // Sorting rules
      queryRegistrationsInOrdersAggrByWeekListWithFilter.with(new Sort(
          Sort.Direction.ASC, ApplicationConstants.FIELD_DATE));

      results = mongoOps.find(
          queryRegistrationsInOrdersAggrByWeekListWithFilter,
          WeeklyRegistration.class);

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getRegistrationsInOrdersAggrByWeekListWithFilter.");

      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getRegistrationsInOrdersAggrByWeekListWithFilter END");

    return results;
  }

  /**
   * SETTER for MongoOperations template.
   * 
   * @param mongoOps
   *          the mongo template.
   */
  @Override
  public void setMongoOps(MongoOperations mongoOps) {
    this.mongoOps = mongoOps;
  }

}
