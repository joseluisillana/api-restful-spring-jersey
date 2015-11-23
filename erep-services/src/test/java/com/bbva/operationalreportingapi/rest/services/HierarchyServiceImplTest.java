package com.bbva.operationalreportingapi.rest.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.bbva.operationalreportingapi.rest.beans.hierarchy.OperationsConfigurations;
import com.bbva.operationalreportingapi.rest.beans.hierarchy.Process;
import com.bbva.operationalreportingapi.rest.beans.hierarchy.Subfamily;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;

public class HierarchyServiceImplTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Method to test the request for hierarchy list.
   */
  @Test
  public final void testGetHierarchyListWithData() {
    HierarchyService servicioMockeado = Mockito
        .mock(HierarchyServiceImpl.class);

    List<OperationsConfigurations> familiesMock = new ArrayList<OperationsConfigurations>();

    for (Long idFamily = 0L; idFamily < 1L; idFamily++) {
      OperationsConfigurations family = new OperationsConfigurations(
          "Family number " + idFamily);

      for (Long idSubFamily = 0L; idSubFamily < 5L; idSubFamily++) {
        Subfamily subfamily = new Subfamily("Sub-Family number " + idFamily
            + "_" + idSubFamily);
        for (Long idProcess = 0L; idProcess < 4L; idProcess++) {
          Process process = new Process();
          process.setProcess("Process number " + idFamily + "_" + idSubFamily
              + "_" + idProcess);
          subfamily.getProcesses().add(process);
        }
        family.getSubfamilies().add(subfamily);
      }

      familiesMock.add(family);
    }

    List<OperationsConfigurations> families = null;

    try {
      Mockito.when(servicioMockeado.getHierarchyMockList()).thenReturn(
          familiesMock);
    } catch (ApiException e) {
      fail(e.getMessage());
    }

    try {
      families = servicioMockeado.getHierarchyMockList();
    } catch (ApiException e) {
      fail(e.getMessage());
    }

    Assert.assertTrue(families.size() > 0);
  }

  /**
   * Method to test the request for hierarchy list when applications has no
   * data.
   */
  @Test
  public final void testGetHierarchyListWithNullData() {
    HierarchyService servicioMockeado = Mockito
        .mock(HierarchyServiceImpl.class);

    List<OperationsConfigurations> families = null;

    try {
      Mockito.when(servicioMockeado.getHierarchyMockList()).thenReturn(null);

      families = servicioMockeado.getHierarchyMockList();

    } catch (ApiException e) {
      fail(e.getMessage());
    }
    Assert.assertNull(families);
  }
}
