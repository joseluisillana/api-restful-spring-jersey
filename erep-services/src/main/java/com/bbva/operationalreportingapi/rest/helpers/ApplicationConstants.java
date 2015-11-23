package com.bbva.operationalreportingapi.rest.helpers;

/**
 * POJO class to store the applications constants values.
 * 
 * @author BBVA-ReportingOperacional
 */
public class ApplicationConstants {

  public static final int GENERAL_ERROR_CODE_API = 5001;
  public static final String GENERAL_ERROR_API_MESSAGE = "BBVA Operational Reporting API message: ";
  public static final String INCOMPLETE_REQUEST_API_MESSAGE = "Incomplete end-point call. "
      + "Please check the descriptor. ";
  public static final String BAD_REQUEST_API_MESSAGE = "HTTP BAD-REQUEST. "
      + "Please check the descriptor. ";
  public static final String GENERAL_HEADER_LOG_MESSAGE = "[[BBVA Operational Reporting]] -- ";
  public static final String LOG_ERROR = "ERROR -- ";

  // Secret key to create a hash key for IDs
  public static final String SECRET_KEY = "MOMBASA";

  // Operations By Process Report type codes
  public static final String AGGR_BY_DAY = "D";
  public static final String AGGR_BY_DAY_PATTERN = "YYYYMMDD";
  public static final String AGGR_BY_MONTH = "M";
  public static final String AGGR_BY_MONTH_PATTERN = "YYYYMM";
  public static final String AGGR_BY_WEEK = "W";
  public static final String AGGR_BY_WEEK_PATTERN = "YYYYWW";

  // MongoDB table names
  public static final String DAILY_REG_COLLECTION = "DailyRegistrationCollection";
  public static final String FIELD_ID = "id";
  public static final String FIELD_ID_MONGO = "_id";
  public static final String FIELD_PROCESS = "process";
  public static final String FIELD_PROCESSES_NODE = "processes";
  public static final String FIELD_FAMILY = "family";
  public static final String FIELD_SUBFAMILY = "subfamily";
  public static final String FIELD_SUBFAMILIES_NODE = "subfamilies";
  public static final String FIELD_DATE = "date";
  public static final String FIELD_TOTAL = "total";
  public static final String FIELD_TOTAL_TIPOPER = "operationtype";
  public static final String FIELD_TOTAL_AMOUNT = "amount";
  public static final String MONTHLY_REG_COLLECTION = "MonthlyRegistration";
  public static final String WEEKLY_REG_COLLECTION = "WeeklyRegistration";
  public static final String FIELD_OPERATIONTYPE = "operations.operationtype";
  public static final String FIELD_OPERATIONS = "operations";
  public static final String FIELD_OPERATIONTYPENODE = "operationtype";
  public static final String FIELD_INSTANCECOUNTNODE = "instancecount";
  public static final String FIELD_ANSOKNODE = "ansok";
  public static final String FIELD_ANSKONODE = "ansko";
  public static final String FIELD_ANSWARNINGNODE = "answarning";
  public static final String FIELD_ANSAVGTIMENODE = "ansavgtime";
  public static final String FIELD_ANSNODE = "ans";

  public static final Long DAY_MILISECONDS = 86400000L;
  public static final int DAY_SECONDS = 86400;
  public static final Long OFFSET_MILISECONDS = 3600000L;
  public static final int ORDER_ASC = 1;
  public static final int ORDER_DESC = 1;

  /**
   * Variables para la conversión de tiempo desde el FRONT
   * **/
  public static final int HUMAN_ANS_TIME_UNIT_SECONDS = 1;
  public static final int HUMAN_ANS_TIME_UNIT_MINUTES = 2;
  public static final int HUMAN_ANS_TIME_UNIT_HOURS = 3;
  public static final int HUMAN_ANS_TIME_UNIT_DAYS = 4;
  public static final int HUMAN_ANS_TIME_UNIT_WEEKS = 5;

  /**
   * PROCESO Reclamaciones: Nombres de tareas y sus identificadores
   * **/
  // FASE 1 Y GRUPOS
  public static final String PHASE_ONE_ID = "p1";
  public static final String PHASE_ONE_NAME = "Contabilidad";
  public static final String PHASE_ONE_GROUP_ONE_ID = "Oficinas";
  public static final String PHASE_ONE_GROUP_ONE_NAME = "Group Task Name 01 Phase 1";
  public static final String PHASE_ONE_GROUP_TWO_ID = "OP Plus";
  public static final String PHASE_ONE_GROUP_TWO_NAME = "Group Task Name 02 Phase 1";
  public static final String PHASE_ONE_GROUP_THREE_ID = "groupTask03";
  public static final String PHASE_ONE_GROUP_THREE_NAME = "Group Task Name 03 Phase 1";

  // FASE 2 Y GRUPOS
  public static final String PHASE_TWO_ID = "p2";
  public static final String PHASE_TWO_NAME = "Gestión";
  public static final String PHASE_TWO_GROUP_ONE_ID = "LATAM - Chile";
  public static final String PHASE_TWO_GROUP_ONE_NAME = "Group Task Name 01 Phase 2";
  public static final String PHASE_TWO_GROUP_TWO_ID = "LATAM -Perú";
  public static final String PHASE_TWO_GROUP_TWO_NAME = "Group Task Name 02 Phase 2";
  public static final String PHASE_TWO_GROUP_THREE_ID = "";
  public static final String PHASE_TWO_GROUP_THREE_NAME = "Group Task Name 03 Phase 2";

  // TAREA ALTA
  public static final String TASK_ALTA_ID = "AM";
  public static final String TASK_ALTA_NAME = "Alta Mínima";

  // TAREA REGISTRO
  public static final String TASK_REGISTRO_ID = "REG";
  public static final String TASK_REGISTRO_NAME = "Registro";

  // TAREA GESTIÓN RESOLUCIÓN
  public static final String TASK_GESTION_RESOL_ID = "GEST";
  public static final String TASK_GESTION_RESOL_NAME = "Gestión Resolución";

  // TAREA APORTAR INFORMACIÓN DOCUMENTACIÓN
  public static final String TASK_APOR_INFO_DOCUM_ID = "SOL";
  public static final String TASK_APOR_INFO_DOCUM_NAME = "Aportar documentación";

  // TAREA RECTIFICAR
  public static final String TASK_RECTIFICAR_ID = "RECT";
  public static final String TASK_RECTIFICAR_NAME = "Rectificar";

  // TAREA REALIZAR NEGOCIACIÓN Y/O RECOGER DESISTIMIENTO
  public static final String TASK_REA_NEGOC_REC_DESIST_ID = "NEG_REC_DESIST";
  public static final String TASK_REA_NEGOC_REC_DESIST_NAME = "Realizar Negociación y/o Recoger Desistimiento";

  // TAREA APORTAR DICTAMEN
  public static final String TASK_APORT_DICTA_ID = "APORT_DICT";
  public static final String TASK_APORT_DICTA_NAME = "Aportar Dictamen";

  // TAREA CONTABILIDAD MANUAL: VERIFICAR
  public static final String TASK_CONT_MAN_VERIF_ID = "VER";
  public static final String TASK_CONT_MAN_VERIF_NAME = "Verificar";

  // TAREA CONTABILIDAD MANUAL: GRABAR
  public static final String TASK_CONT_MAN_GRAB_ID = "GRAB";
  public static final String TASK_CONT_MAN_GRAB_NAME = "Grabar";

  // TAREA CONTABILIDAD MANUAL: VALIDAR
  public static final String TASK_CONT_MAN_VAL_ID = "VAL";
  public static final String TASK_CONT_MAN_VAL_NAME = "Validar";

  // TAREA RESOLVER
  public static final String TASK_RESOLVER_ID = "RES";
  public static final String TASK_RESOLVER_NAME = "Resolver";

  // TAREA CORREGIR
  public static final String TASK_CORREGIR_ID = "COR";
  public static final String TASK_CORREGIR_NAME = "Corregir";

  // TAREA REALICAR COMUNICACIÓN 'AD HOC'
  public static final String TASK_REAL_COMM_ID = "CMN";
  public static final String TASK_REAL_COMM_NAME = "Comunicación";

}
