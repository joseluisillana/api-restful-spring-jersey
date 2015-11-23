package com.bbva.operationalreportingapi.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ClaimsAnsCompliance;
import com.bbva.operationalreportingapi.rest.beans.collections.ProcessDefinition;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 *          Java generics type of report
 * @implements {@link ComplianceProcessDefinitionService}
 */
@Component
public class ComplianceProcessDefinitionServiceImpl implements
    ComplianceProcessDefinitionService {

  @Autowired
  private MongoOperations mongoOps;

  private static final Logger log = LoggerFactory
      .getLogger(ComplianceProcessDefinitionServiceImpl.class);

  static final Map<String, Object> PROCESSES_DEFINITION_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_MOCK_CHILD;
  static final Map<String, Object> PROCESSES_GRAPH_MOCK_CHILD_NODE;
  static final Map<String, Object> TASKS_MOCK_CHILD;
  static final Map<String, Object> TASKS_MOCK_CHILD_NODE;
  static final Map<String, Object> PHASES_MOCK;
  static final Map<String, Object> PHASES_MOCK_CHILD;
  static final Map<String, Object> PHASES_MOCK_CHILD_NODE;
  static final Map<String, Object> TASKS_MOCK;
  static final Map<String, Object> POSITION_DETAIL_MOCK;
  static final Map<String, Object> POSITIONS_MOCK;
  static final Map<String, Object> TIME_INTERVAL_MOCK;
  static final Map<String, Object> TIME_INTERVAL_MOCK_DETAIL;
  static final Map<String, Object> POSITION_DETAIL_COORDS_MOCK;
  static final Map<String, Object> GROUP_PHASE_DETAIL_MOCK;
  static final Map<String, Object> GROUPS_MOCK;
  static final Map<String, Object> GROUP_PHASE_DETAIL_TASKS_MOCK;
  static final Map<String, Object> GROUP_PHASE_DETAIL_TASK_DETAIL_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_TASKS_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE;
  static final Map<String, Object> PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_MOCK_CHILD_NODE;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_POSITION_DETAIL_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE;
  static final Map<String, Object> PROCESSES_GRAPH_PHASES_GROUP_MOCK;

  static {
    PROCESSES_DEFINITION_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_TASKS_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_MOCK_CHILD = new HashMap<String, Object>();
    PROCESSES_GRAPH_MOCK_CHILD_NODE = new HashMap<String, Object>();
    TASKS_MOCK_CHILD = new HashMap<String, Object>();
    TASKS_MOCK_CHILD_NODE = new HashMap<String, Object>();
    PHASES_MOCK = new HashMap<String, Object>();
    PHASES_MOCK_CHILD = new HashMap<String, Object>();
    PHASES_MOCK_CHILD_NODE = new HashMap<String, Object>();
    TASKS_MOCK = new HashMap<String, Object>();
    POSITION_DETAIL_MOCK = new HashMap<String, Object>();
    POSITIONS_MOCK = new HashMap<String, Object>();
    TIME_INTERVAL_MOCK = new HashMap<String, Object>();
    TIME_INTERVAL_MOCK_DETAIL = new HashMap<String, Object>();
    POSITION_DETAIL_COORDS_MOCK = new HashMap<String, Object>();
    GROUP_PHASE_DETAIL_MOCK = new HashMap<String, Object>();
    GROUPS_MOCK = new HashMap<String, Object>();
    GROUP_PHASE_DETAIL_TASKS_MOCK = new HashMap<String, Object>();
    GROUP_PHASE_DETAIL_TASK_DETAIL_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE = new HashMap<String, Object>();
    PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_MOCK_CHILD_NODE = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_POSITION_DETAIL_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE = new HashMap<String, Object>();
    PROCESSES_GRAPH_PHASES_GROUP_MOCK = new HashMap<String, Object>();
  }

  /**
   * Method that create example data about {@link ProcessDefinition} .
   * 
   * @return {@link ProcessDefinition}
   * @throws ApiException
   *           the kind of exception.
   */
  @Override
  public ProcessDefinition createAndPersistsMockDataComplianceProcessDefinitionExampleData()
      throws ApiException {

    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createAndPersistsMockDataComplianceProcessDefinitionExampleData START");
    List<ProcessDefinition> listRegs = new ArrayList<ProcessDefinition>();
    ProcessDefinition complianceProcessDefinition = null;

    try {

      complianceProcessDefinition = new ProcessDefinition();
      complianceProcessDefinition.setId("CLAIMS");
      complianceProcessDefinition.setName("Reclamaciones");

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      complianceProcessDefinition.getTimeIntervalLevels().put("D0",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      complianceProcessDefinition.getTimeIntervalLevels().put("D1",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      complianceProcessDefinition.getTimeIntervalLevels().put("D2",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      complianceProcessDefinition.getTimeIntervalLevels().put("D3to5",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      complianceProcessDefinition.getTimeIntervalLevels().put("D6to10",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      complianceProcessDefinition.getTimeIntervalLevels().put("D10to20",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      complianceProcessDefinition.getTimeIntervalLevels().put("D20",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      /**
       * Poner el grafo del front
       * **/

      // PROCESSES GRAPH TASKS

      // TASK AM

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_ALTA_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 1);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from", new String[] {});
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to",
          new String[] { ApplicationConstants.TASK_REGISTRO_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_ALTA_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK REG

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_REGISTRO_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 18);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_ALTA_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {
          ApplicationConstants.TASK_GESTION_RESOL_ID,
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_REGISTRO_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK GEST

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_GESTION_RESOL_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 35);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_REGISTRO_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(
          ApplicationConstants.TASK_GESTION_RESOL_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK SOL

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 18);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 6);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from", new String[] {
          ApplicationConstants.TASK_GESTION_RESOL_ID,
          ApplicationConstants.TASK_REGISTRO_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {});

      PROCESSES_GRAPH_TASKS_MOCK.put(
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK RECT

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_RECTIFICAR_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 35);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 6);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_RESOLVER_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to",
          new String[] { ApplicationConstants.TASK_CONT_MAN_VERIF_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_RECTIFICAR_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK VER

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 52);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from", new String[] {
          ApplicationConstants.TASK_GESTION_RESOL_ID,
          ApplicationConstants.TASK_RECTIFICAR_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to",
          new String[] { ApplicationConstants.TASK_CONT_MAN_GRAB_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK GRAB

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CONT_MAN_GRAB_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 67);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_CONT_MAN_VERIF_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {
          ApplicationConstants.TASK_CONT_MAN_VAL_ID,
          ApplicationConstants.TASK_RESOLVER_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(
          ApplicationConstants.TASK_CONT_MAN_GRAB_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK VAL

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CONT_MAN_VAL_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 82);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_CONT_MAN_GRAB_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {
          ApplicationConstants.TASK_CORREGIR_ID,
          ApplicationConstants.TASK_REAL_COMM_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_CONT_MAN_VAL_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK RES

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_RESOLVER_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 67);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 6);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_CONT_MAN_GRAB_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to",
          new String[] { ApplicationConstants.TASK_RECTIFICAR_ID });

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_RESOLVER_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK COR

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CORREGIR_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 82);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 6);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_CONT_MAN_VAL_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {});

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_CORREGIR_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      // TASK CMN

      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_REAL_COMM_ID);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("x", 99);
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.put("y", 18);
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("from",
          new String[] { ApplicationConstants.TASK_CONT_MAN_VAL_ID });
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.put("to", new String[] {});

      PROCESSES_GRAPH_TASKS_MOCK.put(ApplicationConstants.TASK_REAL_COMM_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_TASKS_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_TASKS_POSITION_DETAIL_MOCK.clear();

      complianceProcessDefinition.getProcessGraph().put("tasks",
          new HashMap<String, Object>(PROCESSES_GRAPH_TASKS_MOCK));

      // PROCESSES GRAPH PHASES

      // Phase 1

      // Group 1

//      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("x", 0);
//      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("y", 17);
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("position",
//          new HashMap<String, Object>(
//              PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK));
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("width", 15.5);
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("height", 9);
//
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK.put(
//          ApplicationConstants.PHASE_ONE_GROUP_ONE_ID,
//          new HashMap<String, Object>(
//              PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE));
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.clear();

      // Group 1 (OLD Group 2)

      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("x", 50.5);
      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("y", 5);
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("width", 46);
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("height", 21);

      PROCESSES_GRAPH_PHASES_GROUP_MOCK.put(
          ApplicationConstants.PHASE_ONE_GROUP_ONE_ID	,
          new HashMap<String, Object>(
              PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.clear();

      PROCESSES_GRAPH_PHASES_MOCK.put(ApplicationConstants.PHASE_ONE_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_PHASES_GROUP_MOCK));
      PROCESSES_GRAPH_PHASES_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_PHASES_POSITION_DETAIL_MOCK.clear();
      PROCESSES_GRAPH_PHASES_GROUP_MOCK.clear();

      // Phase 2

      // Group 1

      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("x", 16.5);
      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("y", 17);
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("position",
          new HashMap<String, Object>(
              PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK));
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("width", 33);
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("height", 9);

      PROCESSES_GRAPH_PHASES_GROUP_MOCK.put(
          ApplicationConstants.PHASE_TWO_GROUP_ONE_ID,
          new HashMap<String, Object>(
              PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE));
      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.clear();

//      // Group 2
//
//      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("x", 33.5);
//      PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK.put("y", 5);
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("position",
//          new HashMap<String, Object>(
//              PROCESSES_GRAPH_PHASES_GROUP_POSITION_DETAIL_MOCK));
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("width", 16);
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.put("height", 9);
//
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK.put(
//          ApplicationConstants.PHASE_TWO_GROUP_TWO_ID,
//          new HashMap<String, Object>(
//              PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE));
//      PROCESSES_GRAPH_PHASES_GROUP_MOCK_CHILD_NODE.clear();

      PROCESSES_GRAPH_PHASES_MOCK.put(ApplicationConstants.PHASE_TWO_ID,
          new HashMap<String, Object>(PROCESSES_GRAPH_PHASES_GROUP_MOCK));
      PROCESSES_GRAPH_PHASES_MOCK_CHILD_NODE.clear();
      PROCESSES_GRAPH_PHASES_POSITION_DETAIL_MOCK.clear();

      complianceProcessDefinition.getProcessGraph().put("phases",
          new HashMap<String, Object>(PROCESSES_GRAPH_PHASES_MOCK));

      /**
       * INFORMACION DE LAS TAREAS
       * **/
      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_ALTA_ID);
      TASKS_MOCK_CHILD_NODE.put("name", ApplicationConstants.TASK_ALTA_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_ALTA_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_REGISTRO_ID);
      TASKS_MOCK_CHILD_NODE
          .put("name", ApplicationConstants.TASK_REGISTRO_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 3600);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", true);
      TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_ONE_ID);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_REGISTRO_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_GESTION_RESOL_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_GESTION_RESOL_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_GESTION_RESOL_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_APOR_INFO_DOCUM_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);
      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_APOR_INFO_DOCUM_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_RECTIFICAR_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_RECTIFICAR_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 72000);
      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_RECTIFICAR_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();
      /**
       * TO-DO : NO LAS CONTEMPLA EMILIO DE MOMENTO
       * **/
      // TASKS_MOCK_CHILD_NODE.put("id",
      // ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID);
      // TASKS_MOCK_CHILD_NODE.put("name",
      // ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 360000);
      // TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
      // ApplicationConstants.HUMAN_ANS_TIME_UNIT_SECONDS);
      // TASKS_MOCK_CHILD_NODE.put("isAns", true);
      // TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_ONE_ID);
      //
      // complianceProcessDefinition.getTasks().put(
      // ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID,
      // new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      // TASKS_MOCK_CHILD_NODE.clear();
      //
      // TASKS_MOCK_CHILD_NODE.put("id",
      // ApplicationConstants.TASK_APORT_DICTA_ID);
      // TASKS_MOCK_CHILD_NODE.put("name",
      // ApplicationConstants.TASK_APORT_DICTA_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 360000);
      // TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
      // ApplicationConstants.HUMAN_ANS_TIME_UNIT_SECONDS);
      // TASKS_MOCK_CHILD_NODE.put("isAns", true);
      // TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_ONE_ID);
      //
      // complianceProcessDefinition.getTasks().put(
      // ApplicationConstants.TASK_APORT_DICTA_ID,
      // new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      // TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_CONT_MAN_VERIF_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 3600);
      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", true);
      TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_TWO_ID);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id",
          ApplicationConstants.TASK_CONT_MAN_GRAB_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_CONT_MAN_GRAB_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 3600);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", true);
      TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_TWO_ID);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_CONT_MAN_GRAB_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE
          .put("id", ApplicationConstants.TASK_CONT_MAN_VAL_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_CONT_MAN_VAL_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 3600);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", true);
      TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_TWO_ID);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_CONT_MAN_VAL_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_RESOLVER_ID);
      TASKS_MOCK_CHILD_NODE
          .put("name", ApplicationConstants.TASK_RESOLVER_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_RESOLVER_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_CORREGIR_ID);
      TASKS_MOCK_CHILD_NODE
          .put("name", ApplicationConstants.TASK_CORREGIR_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", null);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_CORREGIR_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      TASKS_MOCK_CHILD_NODE.put("id", ApplicationConstants.TASK_REAL_COMM_ID);
      TASKS_MOCK_CHILD_NODE.put("name",
          ApplicationConstants.TASK_REAL_COMM_NAME);
      // TASKS_MOCK_CHILD_NODE.put("ans", 0);

      // Time intervals for task
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      TASKS_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      TASKS_MOCK_CHILD_NODE.put("humanAnsTimeUnit",
          ApplicationConstants.HUMAN_ANS_TIME_UNIT_MINUTES);
      TASKS_MOCK_CHILD_NODE.put("isAns", false);
      TASKS_MOCK_CHILD_NODE.put("phase", ApplicationConstants.PHASE_ONE_ID);

      complianceProcessDefinition.getTasks().put(
          ApplicationConstants.TASK_REAL_COMM_ID,
          new HashMap<String, Object>(TASKS_MOCK_CHILD_NODE));
      TASKS_MOCK_CHILD_NODE.clear();

      /**
       * Las fases
       * **/

      // FASE 1

      PHASES_MOCK_CHILD_NODE.put("id", ApplicationConstants.PHASE_ONE_ID);
      PHASES_MOCK_CHILD_NODE.put("name", ApplicationConstants.PHASE_ONE_NAME);

      // INTERVALOS DE CUMPLIMIENTO DE ANS
      // Time intervals for phase
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      PHASES_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      // Parametros de la posición de la fase
      /*
       * POSITION_DETAIL_COORDS_MOCK.put("x", 16);
       * POSITION_DETAIL_COORDS_MOCK.put("y", 11);
       * 
       * POSITION_DETAIL_MOCK.put("position", new HashMap<String, Object>(
       * POSITION_DETAIL_COORDS_MOCK));
       * 
       * POSITION_DETAIL_MOCK.put("height", 9);
       * POSITION_DETAIL_MOCK.put("width", 17);
       */

      // Parametros del nombre e id del grupo 1 de fase 1
      GROUP_PHASE_DETAIL_MOCK.put("id",
          ApplicationConstants.PHASE_ONE_GROUP_ONE_ID);
      GROUP_PHASE_DETAIL_MOCK.put("name",
          ApplicationConstants.PHASE_ONE_GROUP_ONE_NAME);

      GROUP_PHASE_DETAIL_MOCK.put("tasks",
          new String[] { ApplicationConstants.TASK_ALTA_ID });

      GROUPS_MOCK.put(ApplicationConstants.PHASE_ONE_GROUP_ONE_ID,
          new HashMap<String, Object>(GROUP_PHASE_DETAIL_MOCK));
      GROUP_PHASE_DETAIL_MOCK.clear();

      // Parametros del nombre e id del grupo 2 de fase 2
      GROUP_PHASE_DETAIL_MOCK.put("id",
          ApplicationConstants.PHASE_ONE_GROUP_TWO_ID);
      GROUP_PHASE_DETAIL_MOCK.put("name",
          ApplicationConstants.PHASE_ONE_GROUP_TWO_NAME);

      GROUP_PHASE_DETAIL_MOCK.put("tasks", new String[] {
          ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
          ApplicationConstants.TASK_CONT_MAN_VAL_ID,
          ApplicationConstants.TASK_CONT_MAN_GRAB_ID,
          ApplicationConstants.TASK_RESOLVER_ID,
          ApplicationConstants.TASK_CORREGIR_ID });

      GROUPS_MOCK.put(ApplicationConstants.PHASE_ONE_GROUP_TWO_ID,
          new HashMap<String, Object>(GROUP_PHASE_DETAIL_MOCK));
      GROUP_PHASE_DETAIL_MOCK.clear();

      PHASES_MOCK_CHILD_NODE.put("groupTasks", new HashMap<String, Object>(
          GROUPS_MOCK));

      complianceProcessDefinition.getPhases().put("p1",
          new HashMap<String, Object>(PHASES_MOCK_CHILD_NODE));
      PHASES_MOCK_CHILD_NODE.clear();
      POSITION_DETAIL_MOCK.clear();
      POSITIONS_MOCK.clear();
      PHASES_MOCK_CHILD.clear();
      GROUPS_MOCK.clear();
      GROUP_PHASE_DETAIL_MOCK.clear();

      // FASE 2

      PHASES_MOCK_CHILD_NODE.put("id", ApplicationConstants.PHASE_TWO_ID);
      PHASES_MOCK_CHILD_NODE.put("name", ApplicationConstants.PHASE_TWO_NAME);

      // INTERVALOS DE CUMPLIMIENTO DE ANS
      // Time intervals for phase
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 1);
      TIME_INTERVAL_MOCK.put("D0", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", true);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 2);
      TIME_INTERVAL_MOCK.put("D1", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 3);
      TIME_INTERVAL_MOCK.put("D2", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 4);
      TIME_INTERVAL_MOCK.put("D3to5", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 5);
      TIME_INTERVAL_MOCK.put("D6to10", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 6);
      TIME_INTERVAL_MOCK.put("D10to20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      // Time intervals for process
      TIME_INTERVAL_MOCK_DETAIL.put("ansOk", false);
      TIME_INTERVAL_MOCK_DETAIL.put("order", 7);
      TIME_INTERVAL_MOCK.put("D20", new HashMap<String, Object>(
          TIME_INTERVAL_MOCK_DETAIL));
      TIME_INTERVAL_MOCK_DETAIL.clear();

      PHASES_MOCK_CHILD_NODE.put("timeIntervalLevels",
          new HashMap<String, Object>(TIME_INTERVAL_MOCK));
      TIME_INTERVAL_MOCK.clear();

      // Parametros de la posición de la fase

      /*
       * POSITION_DETAIL_COORDS_MOCK.put("x", 50);
       * POSITION_DETAIL_COORDS_MOCK.put("y", 11);
       * 
       * POSITION_DETAIL_MOCK.put("position", new HashMap<String, Object>(
       * POSITION_DETAIL_COORDS_MOCK));
       * 
       * POSITION_DETAIL_MOCK.put("height", 9);
       * POSITION_DETAIL_MOCK.put("width", 47);
       * 
       * POSITIONS_MOCK.put("position0", new HashMap<String, Object>(
       * POSITION_DETAIL_MOCK));
       */

      // Parametros del nombre e id del grupo 1 de fase 1
      GROUP_PHASE_DETAIL_MOCK.put("id",
          ApplicationConstants.PHASE_TWO_GROUP_ONE_ID);
      GROUP_PHASE_DETAIL_MOCK.put("name",
          ApplicationConstants.PHASE_TWO_GROUP_ONE_NAME);

      GROUP_PHASE_DETAIL_MOCK.put("tasks", new String[] {
          ApplicationConstants.TASK_REGISTRO_ID,
          ApplicationConstants.TASK_GESTION_RESOL_ID });

      GROUPS_MOCK.put(ApplicationConstants.PHASE_TWO_GROUP_ONE_ID,
          new HashMap<String, Object>(GROUP_PHASE_DETAIL_MOCK));
      GROUP_PHASE_DETAIL_MOCK.clear();

      // Parametros del nombre e id del grupo 2 de fase 2
      GROUP_PHASE_DETAIL_MOCK.put("id",
          ApplicationConstants.PHASE_TWO_GROUP_TWO_ID);
      GROUP_PHASE_DETAIL_MOCK.put("name",
          ApplicationConstants.PHASE_TWO_GROUP_TWO_NAME);

      GROUP_PHASE_DETAIL_MOCK.put("tasks",
          new String[] { ApplicationConstants.TASK_RECTIFICAR_ID });

      GROUPS_MOCK.put(ApplicationConstants.PHASE_TWO_GROUP_TWO_ID,
          new HashMap<String, Object>(GROUP_PHASE_DETAIL_MOCK));
      GROUP_PHASE_DETAIL_MOCK.clear();

      PHASES_MOCK_CHILD_NODE.put("groupTasks", new HashMap<String, Object>(
          GROUPS_MOCK));

      complianceProcessDefinition.getPhases().put("p2",
          new HashMap<String, Object>(PHASES_MOCK_CHILD_NODE));
      PHASES_MOCK_CHILD_NODE.clear();
      POSITION_DETAIL_MOCK.clear();
      POSITIONS_MOCK.clear();
      PHASES_MOCK_CHILD.clear();

      mongoOps.insert(complianceProcessDefinition);

      listRegs = mongoOps.findAll(ProcessDefinition.class);

    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: createAndPersistsMockDataComplianceProcessDefinitionExampleData.");
      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: createAndPersistsMockDataComplianceProcessDefinitionExampleData END");
    return complianceProcessDefinition;
  }

  /**
   * Method that returns all documents in DB about {@link ClaimsAnsCompliance}.
   * 
   * @return {@link ProcessDefinition} (List)
   * @throws ApiException
   *           the exception.
   */
  @Override
  public List<ProcessDefinition> getComplianceProcessDefinitionList()
      throws ApiException {
    log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
        + " METHOD: getComplianceProcessDefinitionList START");

    try {
      log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
          + " METHOD: getComplianceProcessDefinitionList END");
      return mongoOps.findAll(ProcessDefinition.class);
    } catch (Exception e) {
      log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
          + " METHOD: getComplianceProcessDefinitionList.");

      throw new ApiException(
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
          ApplicationConstants.GENERAL_ERROR_API_MESSAGE
              + Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), null, e);
    }
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
