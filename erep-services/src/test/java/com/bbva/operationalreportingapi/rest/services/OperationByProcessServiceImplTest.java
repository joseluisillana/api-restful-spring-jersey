package com.bbva.operationalreportingapi.rest.services;

import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.bbva.operationalreportingapi.rest.beans.collections.AmountOperationsByType;
import com.bbva.operationalreportingapi.rest.beans.collections.DailyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.MonthlyRegistration;
import com.bbva.operationalreportingapi.rest.beans.collections.WeeklyRegistration;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

/**
 * Class that sets up the components of the application.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 **/
public class OperationByProcessServiceImplTest {

  static OperationsByProcessService reportServiceMocked;
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

  private static final Integer DAILY_NUM_FAMILIES_MOCK = 20;
  private static final Integer DAILY_NUM_SUBFAMILIES_MOCK = 4;
  private static final Integer DAILY_NUM_OPERATIONTYPES_MOCK = 10;
  private static final Integer DAILY_NUM_PROCESS_MOCK = 10;

  private static final Integer MONTHLY_NUM_FAMILIES_MOCK = 10;
  private static final Integer MONTHLY_NUM_SUBFAMILIES_MOCK = 10;
  private static final Integer MONTHLY_NUM_OPERATIONTYPES_MOCK = 10;
  private static final Integer MONTHLY_NUM_PROCESS_MOCK = 10;

  private static final Integer WEEKLY_NUM_FAMILIES_MOCK = 10;
  private static final Integer WEEKLY_NUM_SUBFAMILIES_MOCK = 10;
  private static final Integer WEEKLY_NUM_OPERATIONTYPES_MOCK = 10;
  private static final Integer WEEKLY_NUM_PROCESS_MOCK = 10;

  DailyRegistration registrationInOrderByDayMock;
  MonthlyRegistration registrationInOrderByMonthMock;
  WeeklyRegistration registrationInOrderByWeekMock;
  List<DailyRegistration> listRegsDay;
  List<MonthlyRegistration> listRegsMonth;
  List<WeeklyRegistration> listRegsWeek;

  /**
   * Set up method to set up data before running test.
   * 
   * @throws Exception
   *           the exception.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    try {
      reportServiceMocked = Mockito.mock(OperationsByProcessServiceImpl.class);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Set up method to tear down after running test.
   * 
   * @throws Exception
   *           the exception.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * Set up method to prepare data to test.
   * 
   * @throws Exception
   *           the exception.
   */
  @Before
  public void setUp() throws Exception {
    // Build the daily mock
    createRegInOrderByDayMock();

    // Build the monthly mock
    createRegInOrderByMonthMock();

    // Build the weekly mock
    createRegInOrderByWeekMock();

    // Build the full daily mock
    createFullRegInOrderByDayMock();

    // Build the full month mock
    createFullRegInOrderByMonthMock();

    // Build the full weekly mock
    createFullRegInOrderByWeekMock();

    // Set up the mock actions for methods
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByDayMock())
        .thenReturn(registrationInOrderByDayMock);
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByMonthMock())
        .thenReturn(registrationInOrderByMonthMock);
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByWeekMock())
        .thenReturn(registrationInOrderByWeekMock);

    Mockito.when(reportServiceMocked.createRegistrationsInOrdersAggrByDay())
        .thenReturn(registrationInOrderByDayMock);
    Mockito.when(reportServiceMocked.createRegistrationsInOrdersAggrByWeek())
        .thenReturn(registrationInOrderByWeekMock);
    Mockito.when(reportServiceMocked.createRegistrationsInOrdersAggrByMonth())
        .thenReturn(registrationInOrderByMonthMock);

    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByDayList())
        .thenReturn(listRegsDay);
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByMonthList())
        .thenReturn(listRegsMonth);
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByWeekList())
        .thenReturn(listRegsWeek);

    // TODO - VER COMO PASAR LAS PRUEBAS.
    // listResults = new ArrayList<DailyRegistration>();
    // for (DailyRegistration dayReg : listRegsDay) {
    // listResults.add(BeanUtils.cloneBean(dayReg));
    // }
    //
    // Mockito.when(
    // reportServiceMocked
    // .getRegistrationsInOrderAggregatedWhitFilterPathParam(
    // FAMILY_NAME_MOCK, SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK,
    // ApplicationConstants.AGGR_BY_DAY, "20150101", "20150301"))
    // .thenReturn(listResults);

    Mockito.when(
        reportServiceMocked.getRegistrationsInOrdersAggrByDayListWithFilter(
            FAMILY_NAME_MOCK, SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK,
            "20150101", "20150103")).thenReturn(listRegsDay);
    Mockito.when(
        reportServiceMocked.getRegistrationsInOrdersAggrByMonthListWithFilter(
            FAMILY_NAME_MOCK, SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK, "201501",
            "201503")).thenReturn(listRegsMonth);
    Mockito.when(
        reportServiceMocked.getRegistrationsInOrdersAggrByWeekListWithFilter(
            FAMILY_NAME_MOCK, SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK, "201501",
            "201520")).thenReturn(listRegsWeek);
    Mockito.when(reportServiceMocked.getRegistrationsInOrdersAggrByDayList())
        .thenReturn(listRegsDay);

  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByDayMock()
   * returned object is the result expected.
   * 
   * {@link OperationsByProcessServiceImpl}
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByDayMock() {

    DailyRegistration registrationInOrderByDayMockResult = null;
    try {

      registrationInOrderByDayMockResult = reportServiceMocked
          .getRegistrationsInOrdersAggrByDayMock();
      Assert.assertEquals(registrationInOrderByDayMock,
          registrationInOrderByDayMockResult);
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByMonthMock()
   * returned object is the result expected.
   * 
   * {@link OperationsByProcessServiceImpl}
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByMonthMock() {
    MonthlyRegistration registrationInOrderByMonthMockResult = null;
    try {

      registrationInOrderByMonthMockResult = reportServiceMocked
          .getRegistrationsInOrdersAggrByMonthMock();
      Assert.assertEquals(registrationInOrderByMonthMock,
          registrationInOrderByMonthMockResult);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }

  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByMonthMock()
   * returned object is the result expected.
   * 
   * {@link OperationsByProcessServiceImpl}
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByWeekMock() {
    WeeklyRegistration registrationInOrderByWeekMockResult = null;
    try {

      registrationInOrderByWeekMockResult = reportServiceMocked
          .getRegistrationsInOrdersAggrByWeekMock();
      Assert.assertEquals(registrationInOrderByWeekMock,
          registrationInOrderByWeekMockResult);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }

  }

  /**
   * Test method for know if method createRegistrationsInOrdersAggrByDay()
   * return value is not null.
   */
  @Test
  public void testCreateRegistrationsInOrdersAggrByDay() {
    try {
      Assert.assertNotNull(reportServiceMocked
          .createRegistrationsInOrdersAggrByDay());
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method createRegistrationsInOrdersAggrByWeek()
   * return value is not null.
   */
  @Test
  public void testCreateRegistrationsInOrdersAggrByWeek() {
    try {
      Assert.assertNotNull(reportServiceMocked
          .createRegistrationsInOrdersAggrByWeek());
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method createRegistrationsInOrdersAggrByMonth()
   * return value is not null.
   */
  @Test
  public void testCreateRegistrationsInOrdersAggrByMonth() {
    try {
      MonthlyRegistration monthlyRegResult = reportServiceMocked
          .createRegistrationsInOrdersAggrByMonth();
      Assert.assertNotNull(monthlyRegResult);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByDayList()
   * return a number of values correctly.
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByDayList() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByDayList().size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByWeekList()
   * return a number of values correctly.
   * */
  @Test
  public void testGetRegistrationsInOrdersAggrByWeekList() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByWeekList().size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method getRegistrationsInOrdersAggrByMonthList()
   * return a number of values correctly.
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByMonthList() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByMonthList().size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method
   * getRegistrationsInOrdersAggrByDayListWithFilter() return a number of values
   * correctly.
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByDayListWithFilter() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByDayListWithFilter(FAMILY_NAME_MOCK,
              SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK, "20150101", "20150103")
          .size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method
   * getRegistrationsInOrdersAggrByMonthListWithFilter() return a number of
   * values correctly.
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByMonthListWithFilter() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByMonthListWithFilter(FAMILY_NAME_MOCK,
              SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK, "201501", "201503")
          .size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test method for know if method
   * getRegistrationsInOrdersAggrByMonthListWithFilter() return a number of
   * values correctly.
   */
  @Test
  public void testGetRegistrationsInOrdersAggrByWeekListWithFilter() {
    try {
      Assert.assertTrue(reportServiceMocked
          .getRegistrationsInOrdersAggrByWeekListWithFilter(FAMILY_NAME_MOCK,
              SUBFAMILY_NAME_MOCK, PROCESS_NAME_MOCK, "201501", "201520")
          .size() > 0);
    } catch (AssertionError | ApiException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Generates example data for registration in order by week.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private void createRegInOrderByWeekMock() throws ApiException {
    try {
      registrationInOrderByWeekMock = new WeeklyRegistration();
      registrationInOrderByWeekMock.setProcess(PROCESS_NAME_MOCK + 0);
      registrationInOrderByWeekMock.setFamily(FAMILY_NAME_MOCK + 0);
      registrationInOrderByWeekMock.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
      registrationInOrderByWeekMock.setDate(WEEKLY_DATE_MOCK);
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByWeekMock.getTotal().add(amount);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Generates example data for registration in order by month.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private void createRegInOrderByMonthMock() throws ApiException {
    try {
      registrationInOrderByMonthMock = new MonthlyRegistration();
      registrationInOrderByMonthMock.setProcess(PROCESS_NAME_MOCK + 0);
      registrationInOrderByMonthMock.setFamily(FAMILY_NAME_MOCK + 0);
      registrationInOrderByMonthMock.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
      registrationInOrderByMonthMock.setDate(MONTHLY_DATE_MOCK);
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByMonthMock.getTotal().add(amount);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Generates example data for registration in order by day.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private void createRegInOrderByDayMock() throws ApiException {
    try {
      registrationInOrderByDayMock = new DailyRegistration();
      registrationInOrderByDayMock.setProcess(PROCESS_NAME_MOCK + 0);
      registrationInOrderByDayMock.setFamily(FAMILY_NAME_MOCK + 0);
      registrationInOrderByDayMock.setSubfamily(SUBFAMILY_NAME_MOCK + 0);
      registrationInOrderByDayMock.setDate(DAILY_DATE_MOCK);
      for (int idCounter = 0; idCounter <= 20; idCounter++) {
        Random rn = new Random();
        int range = 10000 - 1000 + 1;
        int randomNum = rn.nextInt(range) + 1000;
        AmountOperationsByType amount = new AmountOperationsByType(
            OPERATIONTYPE_NAME_MOCK + idCounter, randomNum);
        registrationInOrderByDayMock.getTotal().add(amount);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Generates example full data for registration in order by day.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private List<DailyRegistration> createFullRegInOrderByDayMock()
      throws ApiException {
    listRegsDay = new ArrayList<DailyRegistration>();
    DailyRegistration registrationInOrderByDay = null;

    DateFormat dateFormater = new SimpleDateFormat(DAILY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.MONTH, Calendar.JANUARY);
    startCal.set(Calendar.DAY_OF_MONTH, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2015);
    endCal.set(Calendar.MONTH, Calendar.FEBRUARY);
    endCal.set(Calendar.DAY_OF_MONTH, 1);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {

        String formattedDate = dateFormater.format(startDate);

        for (int idProcessCounter = 0; idProcessCounter <= DAILY_NUM_PROCESS_MOCK; idProcessCounter++) {
          registrationInOrderByDay = new DailyRegistration();

          registrationInOrderByDay.setProcess(PROCESS_NAME_MOCK
              + idProcessCounter);
          for (int idFamilyCounter = 0; idFamilyCounter <= DAILY_NUM_FAMILIES_MOCK; idFamilyCounter++) {
            registrationInOrderByDay.setFamily(FAMILY_NAME_MOCK
                + idFamilyCounter);
            for (int idSubFamilyCounter = 0; idSubFamilyCounter <= DAILY_NUM_SUBFAMILIES_MOCK; idSubFamilyCounter++) {
              registrationInOrderByDay.setSubfamily(SUBFAMILY_NAME_MOCK
                  + idSubFamilyCounter);
              registrationInOrderByDay.setDate(formattedDate);
              registrationInOrderByDay.setId(registrationInOrderByDay
                  .getProcess()
                  + formattedDate
                  + idFamilyCounter
                  + idSubFamilyCounter);
              for (int idOpeTypeCounter = 0; idOpeTypeCounter <= DAILY_NUM_OPERATIONTYPES_MOCK; idOpeTypeCounter++) {
                Random rn = new Random();
                int range = 10000 - 1000 + 1;
                int randomNum = rn.nextInt(range) + 1000;
                AmountOperationsByType amount = new AmountOperationsByType(
                    OPERATIONTYPE_NAME_MOCK + idOpeTypeCounter, randomNum);
                registrationInOrderByDay.getTotal().add(amount);

              }
              listRegsDay.add((DailyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByDay));
            }
          }
        }

        startCal.add(Calendar.DAY_OF_MONTH, 1);
        startDate = startCal.getTime();
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    return listRegsDay;
  }

  /**
   * Generates example full data for registration in order by Month.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private List<MonthlyRegistration> createFullRegInOrderByMonthMock()
      throws ApiException {
    listRegsMonth = new ArrayList<MonthlyRegistration>();
    MonthlyRegistration registrationInOrderByMonth = null;

    DateFormat dateFormater = new SimpleDateFormat(MONTHLY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.MONTH, Calendar.JANUARY);
    startCal.set(Calendar.MONTH, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2015);
    endCal.set(Calendar.MONTH, Calendar.FEBRUARY);
    endCal.set(Calendar.MONDAY, 2);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {

        String formattedDate = dateFormater.format(startDate);

        for (int idProcessCounter = 0; idProcessCounter <= MONTHLY_NUM_PROCESS_MOCK; idProcessCounter++) {
          registrationInOrderByMonth = new MonthlyRegistration();

          registrationInOrderByMonth.setProcess(PROCESS_NAME_MOCK
              + idProcessCounter);
          for (int idFamilyCounter = 0; idFamilyCounter <= MONTHLY_NUM_FAMILIES_MOCK; idFamilyCounter++) {
            registrationInOrderByMonth.setFamily(FAMILY_NAME_MOCK
                + idFamilyCounter);
            for (int idSubFamilyCounter = 0; idSubFamilyCounter <= MONTHLY_NUM_SUBFAMILIES_MOCK; idSubFamilyCounter++) {
              registrationInOrderByMonth.setSubfamily(SUBFAMILY_NAME_MOCK
                  + idSubFamilyCounter);
              registrationInOrderByMonth.setDate(formattedDate);
              registrationInOrderByMonth.setId(registrationInOrderByMonth
                  .getProcess()
                  + formattedDate
                  + idFamilyCounter
                  + idSubFamilyCounter);
              for (int idOpeTypeCounter = 0; idOpeTypeCounter <= MONTHLY_NUM_OPERATIONTYPES_MOCK; idOpeTypeCounter++) {
                Random rn = new Random();
                int range = 10000 - 1000 + 1;
                int randomNum = rn.nextInt(range) + 1000;
                AmountOperationsByType amount = new AmountOperationsByType(
                    OPERATIONTYPE_NAME_MOCK + idOpeTypeCounter, randomNum);
                registrationInOrderByMonth.getTotal().add(amount);

              }
              listRegsMonth.add((MonthlyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByMonth));
            }

          }
        }

        startCal.add(Calendar.MONTH, 1);
        startDate = startCal.getTime();
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    return listRegsMonth;
  }

  /**
   * Generates example full data for registration in order by Month.
   * 
   * @throws ApiException
   *           the kind of exception throwed.
   */
  private List<WeeklyRegistration> createFullRegInOrderByWeekMock()
      throws ApiException {
    listRegsWeek = new ArrayList<WeeklyRegistration>();
    WeeklyRegistration registrationInOrderByWeek = null;

    DateFormat dateFormater = new SimpleDateFormat(WEEKLY_DATE_FORMAT);

    Calendar startCal = Calendar.getInstance();
    startCal.set(Calendar.YEAR, 2015);
    startCal.set(Calendar.WEEK_OF_YEAR, 1);

    Calendar endCal = Calendar.getInstance();
    endCal.set(Calendar.YEAR, 2015);
    endCal.set(Calendar.MONTH, Calendar.FEBRUARY);

    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    try {
      while (startDate.before(endDate)) {

        String formattedDate = dateFormater.format(startDate);

        for (int idProcessCounter = 0; idProcessCounter <= WEEKLY_NUM_PROCESS_MOCK; idProcessCounter++) {
          registrationInOrderByWeek = new WeeklyRegistration();

          registrationInOrderByWeek.setProcess(PROCESS_NAME_MOCK
              + idProcessCounter);
          for (int idFamilyCounter = 0; idFamilyCounter <= WEEKLY_NUM_FAMILIES_MOCK; idFamilyCounter++) {
            registrationInOrderByWeek.setFamily(FAMILY_NAME_MOCK
                + idFamilyCounter);
            for (int idSubFamilyCounter = 0; idSubFamilyCounter <= WEEKLY_NUM_SUBFAMILIES_MOCK; idSubFamilyCounter++) {
              registrationInOrderByWeek.setSubfamily(SUBFAMILY_NAME_MOCK
                  + idSubFamilyCounter);
              registrationInOrderByWeek.setDate(formattedDate);
              registrationInOrderByWeek.setId(registrationInOrderByWeek
                  .getProcess()
                  + formattedDate
                  + idFamilyCounter
                  + idSubFamilyCounter);
              for (int idOpeTypeCounter = 0; idOpeTypeCounter <= WEEKLY_NUM_OPERATIONTYPES_MOCK; idOpeTypeCounter++) {
                Random rn = new Random();
                int range = 10000 - 1000 + 1;
                int randomNum = rn.nextInt(range) + 1000;
                AmountOperationsByType amount = new AmountOperationsByType(
                    OPERATIONTYPE_NAME_MOCK + idOpeTypeCounter, randomNum);
                registrationInOrderByWeek.getTotal().add(amount);

              }
              listRegsWeek.add((WeeklyRegistration) BeanUtils
                  .cloneBean(registrationInOrderByWeek));
            }
          }
        }

        startCal.add(Calendar.WEEK_OF_YEAR, 1);
        startDate = startCal.getTime();
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    return listRegsWeek;
  }

}
