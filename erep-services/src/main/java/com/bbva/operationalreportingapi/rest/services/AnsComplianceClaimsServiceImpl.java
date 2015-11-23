package com.bbva.operationalreportingapi.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ClaimsAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.helpers.Utilities;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 *            Java generics type of report
 * @implements {@link AnsComplianceClaimsService}
 */
@Component
public class AnsComplianceClaimsServiceImpl implements
		AnsComplianceClaimsService {

	@Autowired
	private MongoOperations mongoOps;

	private static final Logger log = LoggerFactory
			.getLogger(AnsComplianceClaimsServiceImpl.class);

	static final Map<String, Object> CLAIMS_PROCESSES_MOCK;
	static final Map<String, Object> ANS_OK_PROCESSES_MOCK;
	static final Map<String, Object> ANS_KO_PROCESSES_MOCK;
	static final Map<String, Object> ANS_OK_TASKS_DETAILS_PROCESSES_MOCK;
	static final Map<String, Object> ANS_KO_TASKS_PROCESSES_MOCK;
	static final Map<String, Object> ANS_TASK_DETAILS_PROCESSES_MOCK;
	static final Map<String, Object> ANS_PHASES_DETAILS_PROCESSES_MOCK;

	static final Map<String, Object> PROCESSES_ACUMS_MOCK;
	static final Map<String, Object> PROCESSES_DAILY_MOCK;

	static final Map<String, Object> TASKS_ACUMS_MOCK;
	static final Map<String, Object> TASKS_DAILY_MOCK;

	static final Map<String, Object> PHASES_ACUMS_MOCK;
	static final Map<String, Object> PHASES_DAILY_MOCK;

	static final LinkedHashMap<String, double[]> ansMockValues;

	static final Map<String, Double> ansAcumsTasksValues;

	static Double avgTimeTask = null;
	static Double acumTasksDataAcumsD0 = null;
	static Double acumTasksDataAcumsD1 = null;
	static Double acumTasksDataAcumsD2 = null;
	static Double acumTasksDataAcumsD3to5 = null;
	static Double acumTasksDataAcumsD6to10 = null;
	static Double acumTasksDataAcumsD10to20 = null;
	static Double acumTasksDataAcumsD20 = null;

	static Double dailyTasksDataAcumsD0 = null;
	static Double dailyTasksDataAcumsD1 = null;
	static Double dailyTasksDataAcumsD2 = null;
	static Double dailyTasksDataAcumsD3to5 = null;
	static Double dailyTasksDataAcumsD6to10 = null;
	static Double dailyTasksDataAcumsD10to20 = null;
	static Double dailyTasksDataAcumsD20 = null;

	static Double acumPhasesDataAcumsD0 = null;
	static Double acumPhasesDataAcumsD1 = null;
	static Double acumPhasesDataAcumsD2 = null;
	static Double acumPhasesDataAcumsD3to5 = null;
	static Double acumPhasesDataAcumsD6to10 = null;
	static Double acumPhasesDataAcumsD10to20 = null;
	static Double acumPhasesDataAcumsD20 = null;

	static Double dailyPhasesDataAcumsD0 = null;
	static Double dailyPhasesDataAcumsD1 = null;
	static Double dailyPhasesDataAcumsD2 = null;
	static Double dailyPhasesDataAcumsD3to5 = null;
	static Double dailyPhasesDataAcumsD6to10 = null;
	static Double dailyPhasesDataAcumsD10to20 = null;
	static Double dailyPhasesDataAcumsD20 = null;

	static Double acumProcessDataNewInstances = null;
	static Double acumProcessDataOpenInstances = null;
	static Double acumProcessDataClosedInstances = null;
	static Double acumProcessDataAcumsD0 = null;
	static Double acumProcessDataAcumsD1 = null;
	static Double acumProcessDataAcumsD2 = null;
	static Double acumProcessDataAcumsD3to5 = null;
	static Double acumProcessDataAcumsD6to10 = null;
	static Double acumProcessDataAcumsD10to20 = null;
	static Double acumProcessDataAcumsD20 = null;
	static Double acumProcessAvgTimeProcess = null;

	static Double dailyProcessDataNewInstances = null;
	static Double dailyProcessDataOpenInstances = null;
	static Double dailyProcessDataClosedInstances = null;
	static Double dailyProcessDataAcumsD0 = null;
	static Double dailyProcessDataAcumsD1 = null;
	static Double dailyProcessDataAcumsD2 = null;
	static Double dailyProcessDataAcumsD3to5 = null;
	static Double dailyProcessDataAcumsD6to10 = null;
	static Double dailyProcessDataAcumsD10to20 = null;
	static Double dailyProcessDataAcumsD20 = null;

	static {
		CLAIMS_PROCESSES_MOCK = new HashMap<String, Object>();
		ANS_OK_PROCESSES_MOCK = new HashMap<String, Object>();
		ANS_KO_PROCESSES_MOCK = new HashMap<String, Object>();
		ANS_OK_TASKS_DETAILS_PROCESSES_MOCK = new HashMap<String, Object>();
		ANS_TASK_DETAILS_PROCESSES_MOCK = new HashMap<String, Object>();
		ANS_KO_TASKS_PROCESSES_MOCK = new HashMap<String, Object>();
		CLAIMS_PROCESSES_MOCK.put("RECLAMACIONES", "HOLA");

		PROCESSES_ACUMS_MOCK = new HashMap<String, Object>();
		PROCESSES_DAILY_MOCK = new HashMap<String, Object>();
		TASKS_ACUMS_MOCK = new HashMap<String, Object>();
		TASKS_DAILY_MOCK = new HashMap<String, Object>();
		PHASES_ACUMS_MOCK = new HashMap<String, Object>();
		PHASES_DAILY_MOCK = new HashMap<String, Object>();
		ANS_PHASES_DETAILS_PROCESSES_MOCK = new HashMap<String, Object>();

		acumTasksDataAcumsD0 = new Double(0);
		acumTasksDataAcumsD1 = new Double(0);
		acumTasksDataAcumsD2 = new Double(0);
		acumTasksDataAcumsD3to5 = new Double(0);
		acumTasksDataAcumsD6to10 = new Double(0);
		acumTasksDataAcumsD10to20 = new Double(0);
		acumTasksDataAcumsD20 = new Double(0);

		dailyTasksDataAcumsD0 = new Double(0);
		dailyTasksDataAcumsD1 = new Double(0);
		dailyTasksDataAcumsD2 = new Double(0);
		dailyTasksDataAcumsD3to5 = new Double(0);
		dailyTasksDataAcumsD6to10 = new Double(0);
		dailyTasksDataAcumsD10to20 = new Double(0);
		dailyTasksDataAcumsD20 = new Double(0);

		acumPhasesDataAcumsD0 = new Double(0);
		acumPhasesDataAcumsD1 = new Double(0);
		acumPhasesDataAcumsD2 = new Double(0);
		acumPhasesDataAcumsD3to5 = new Double(0);
		acumPhasesDataAcumsD6to10 = new Double(0);
		acumPhasesDataAcumsD10to20 = new Double(0);
		acumPhasesDataAcumsD20 = new Double(0);

		dailyPhasesDataAcumsD0 = new Double(0);
		dailyPhasesDataAcumsD1 = new Double(0);
		dailyPhasesDataAcumsD2 = new Double(0);
		dailyPhasesDataAcumsD3to5 = new Double(0);
		dailyPhasesDataAcumsD6to10 = new Double(0);
		dailyPhasesDataAcumsD10to20 = new Double(0);
		dailyPhasesDataAcumsD20 = new Double(0);

		acumProcessDataNewInstances = new Double(0);
		acumProcessDataOpenInstances = new Double(0);
		acumProcessDataClosedInstances = new Double(0);
		acumProcessDataAcumsD0 = new Double(0);
		acumProcessDataAcumsD1 = new Double(0);
		acumProcessDataAcumsD2 = new Double(0);
		acumProcessDataAcumsD3to5 = new Double(0);
		acumProcessDataAcumsD6to10 = new Double(0);
		acumProcessDataAcumsD10to20 = new Double(0);
		acumProcessDataAcumsD20 = new Double(0);
		acumProcessAvgTimeProcess = new Double(0);

		dailyProcessDataNewInstances = new Double(0);
		dailyProcessDataOpenInstances = new Double(0);
		dailyProcessDataClosedInstances = new Double(0);
		dailyProcessDataAcumsD0 = new Double(0);
		dailyProcessDataAcumsD1 = new Double(0);
		dailyProcessDataAcumsD2 = new Double(0);
		dailyProcessDataAcumsD3to5 = new Double(0);
		dailyProcessDataAcumsD6to10 = new Double(0);
		dailyProcessDataAcumsD10to20 = new Double(0);
		dailyProcessDataAcumsD20 = new Double(0);

		ansMockValues = new LinkedHashMap<String, double[]>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("ans", new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
						1, 1, 1, 1, 1, 1, 1, 1, 1 });
				put("processData.acums.avgTimeProcess", new double[] { 10, 10,
						30, 100, 90, 75, 200, 150, 50, 80, 95, 300, 25, 45, 1,
						80, 56, 42, 60, 10, 250 });
				put("processData.acums.newInstances", new double[] { 10, 25,
						11, 5, 29, 22, 14, 18, 9, 5, 0, 22, 8, 15, 17, 22, 30,
						2, 8, 20, 21 });
				put("processData.acums.openInstances",
						new double[] { 0, 0, 0, 0, 0, 0, 7, 6, 0, 0, 0, 17, 0,
								0, 0, 0, 0, 0, 0, 0, 17 });
				put("processData.acums.closedInstances", new double[] { 10, 25,
						11, 5, 29, 22, 7, 12, 9, 5, 5, 5, 8, 15, 17, 22, 30, 2,
						8, 20, 4 });
				put("processData.acums.D0", new double[] { 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D1", new double[] { 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D2", new double[] { 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D3to5", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D10to20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.daily.avgTimeProcess", new double[] { 0, 10,
						30, 100, 90, 75, 200, 150, 50, 80, 95, 300, 25, 45,
						100, 80, 56, 42, 60, 0, 250 });
				put("processData.daily.newInstances", new double[] { 10, 25,
						11, 5, 29, 22, 14, 18, 9, 5, 0, 22, 8, 15, 17, 22, 30,
						2, 8, 20, 21 });
				put("processData.daily.openInstances",
						new double[] { 0, 0, 0, 0, 0, 0, 7, 6, 0, 0, 0, 17, 0,
								0, 0, 0, 0, 0, 0, 0, 17 });
				put("processData.daily.closedInstances", new double[] { 10, 25,
						11, 5, 29, 22, 7, 12, 9, 5, 5, 5, 8, 15, 17, 22, 30, 2,
						8, 20, 4 });
				put("processData.daily.D0", new double[] { 10, 24, 11, 5, 20,
						22, 1, 5, 8, 5, 5, 1, 4, 11, 10, 10, 30, 2, 7, 10, 0 });
				put("processData.daily.D1", new double[] { 0, 1, 0, 0, 9, 0, 2,
						3, 1, 0, 0, 2, 4, 4, 7, 12, 0, 0, 1, 10, 1 });
				put("processData.daily.D2", new double[] { 0, 0, 0, 0, 0, 0, 2,
						2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("processData.daily.D3to5", new double[] { 0, 0, 0, 0, 0, 0,
						1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("processData.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("processData.daily.D10to20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("processData.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.RES.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.RES.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.RES.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.RES.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.RES.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.RES.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.RES.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RES.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.avgTimeTask", new double[] {
						0, 0, 0, 0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0,
						0, 0, 0, 69.2 });
				put("tasksData.NEG_REC_DESIST.acums.D0",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D1",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D2",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D3to5", new double[] { 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D6to10", new double[] { 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D10to20", new double[] { 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 });
				put("tasksData.NEG_REC_DESIST.acums.D20",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.daily.avgTimeTask", new double[] {
						0, 0, 0, 0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0,
						0, 0, 0, 69.2 });
				put("tasksData.NEG_REC_DESIST.daily.D0",
						new double[] { 0, 0, 0, 0, 0, 0, 1, 5, 0, 0, 0, 1, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.daily.D1",
						new double[] { 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 0, 1, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.NEG_REC_DESIST.daily.D2",
						new double[] { 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 0,
								0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.NEG_REC_DESIST.daily.D3to5", new double[] { 0,
						0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
						0, 1 });
				put("tasksData.NEG_REC_DESIST.daily.D6to10", new double[] { 0,
						0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 1 });
				put("tasksData.NEG_REC_DESIST.daily.D10to20", new double[] { 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 });
				put("tasksData.NEG_REC_DESIST.daily.D20",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.avgTimeTask", new double[] { 0, 0, 0,
						0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0, 0, 0,
						69.2 });
				put("tasksData.COR.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.daily.avgTimeTask", new double[] { 0, 0, 0,
						0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0, 0, 0,
						69.2 });
				put("tasksData.COR.daily.D0", new double[] { 0, 0, 0, 0, 0, 0,
						1, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.daily.D1", new double[] { 0, 0, 0, 0, 0, 0,
						2, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.COR.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.COR.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.COR.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.COR.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.CMN.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.CMN.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.CMN.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.CMN.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.CMN.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.CMN.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.CMN.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.CMN.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.SOL.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.SOL.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.SOL.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.SOL.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.SOL.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.SOL.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.SOL.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.SOL.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.avgTimeTask", new double[] { 1.6,
						0.6, 4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5,
						5.4, 0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.GEST.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.acums.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.daily.avgTimeTask", new double[] { 1.6,
						0.6, 4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5,
						5.4, 0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.GEST.daily.D0",
						new double[] { 10, 25, 10, 5, 21, 20, 1, 5, 5, 3, 1, 1,
								7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.GEST.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.GEST.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.GEST.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.GEST.daily.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.GEST.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GEST.daily.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.VAL.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.VAL.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.VAL.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.VAL.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.VAL.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.VAL.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.VAL.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VAL.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.avgTimeTask", new double[] { 1.6,
						0.6, 4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5,
						5.4, 0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.GRAB.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.acums.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.daily.avgTimeTask", new double[] { 1.6,
						0.6, 4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5,
						5.4, 0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.GRAB.daily.D0",
						new double[] { 10, 25, 10, 5, 21, 20, 1, 5, 5, 3, 1, 1,
								7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.GRAB.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.GRAB.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.GRAB.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.GRAB.daily.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.GRAB.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.GRAB.daily.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.avgTimeTask", new double[] { 0,
						0, 0, 0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0,
						0, 0, 69.2 });
				put("tasksData.APORT_DICT.acums.D0", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D1", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D2", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D3to5", new double[] { 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D6to10",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D10to20",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.acums.D20", new double[] { 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.daily.avgTimeTask", new double[] { 0,
						0, 0, 0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0,
						0, 0, 69.2 });
				put("tasksData.APORT_DICT.daily.D0", new double[] { 0, 0, 0, 0,
						0, 0, 1, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.daily.D1", new double[] { 0, 0, 0, 0,
						0, 0, 2, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.daily.D2", new double[] { 0, 0, 0, 0,
						0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.APORT_DICT.daily.D3to5", new double[] { 0, 0, 0,
						0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.APORT_DICT.daily.D6to10",
						new double[] { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.APORT_DICT.daily.D10to20",
						new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.APORT_DICT.daily.D20", new double[] { 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.REG.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.REG.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.REG.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.REG.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.REG.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.REG.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.REG.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.REG.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.VER.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.VER.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.VER.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.VER.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.VER.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.VER.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.VER.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.VER.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.avgTimeTask", new double[] { 0, 0, 0,
						0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0, 0, 0,
						69.2 });
				put("tasksData.RECT.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.acums.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.daily.avgTimeTask", new double[] { 0, 0, 0,
						0, 0, 0, 31, 13.8, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0, 0, 0,
						69.2 });
				put("tasksData.RECT.daily.D0", new double[] { 0, 0, 0, 0, 0, 0,
						1, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.daily.D1", new double[] { 0, 0, 0, 0, 0, 0,
						2, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.RECT.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.RECT.daily.D6to10", new double[] { 0, 0, 0, 0,
						0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.RECT.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.RECT.daily.D20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.AM.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D10to20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.daily.avgTimeTask", new double[] { 1.6, 0.6,
						4, 32, 4.9, 5.4, 31, 13.8, 9.5, 25.6, 30.4, 96, 5, 5.4,
						0.09, 5.81, 44.7, 33.5, 12, 0.8, 69.2 });
				put("tasksData.AM.daily.D0", new double[] { 10, 25, 10, 5, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("tasksData.AM.daily.D1", new double[] { 0, 0, 1, 0, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("tasksData.AM.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("tasksData.AM.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.AM.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("tasksData.AM.daily.D10to20", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("tasksData.AM.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.avgTimePhase", new double[] { 1, 5, 2,
						12, 80, 60, 120, 110, 35, 58, 89, 200, 17, 35, 95, 48,
						46, 23, 56, 45, 180 });
				put("phasesData.p1.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.daily.avgTimePhase", new double[] { 1, 5, 2,
						12, 80, 60, 120, 110, 35, 58, 89, 200, 17, 35, 95, 48,
						46, 23, 56, 45, 180 });
				put("phasesData.p1.daily.D0", new double[] { 10, 25, 10, 4, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("phasesData.p1.daily.D1", new double[] { 0, 0, 1, 1, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("phasesData.p1.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("phasesData.p1.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("phasesData.p1.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("phasesData.p1.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p1.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.avgTimePhase", new double[] { 1, 5, 2,
						12, 80, 60, 120, 110, 35, 58, 89, 200, 17, 35, 95, 48,
						46, 23, 56, 45, 180 });
				put("phasesData.p2.acums.D0", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D1", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D2", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.acums.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.daily.avgTimePhase", new double[] { 1, 5, 2,
						12, 80, 60, 120, 110, 35, 58, 89, 200, 17, 35, 95, 48,
						46, 23, 56, 45, 180 });
				put("phasesData.p2.daily.D0", new double[] { 10, 25, 10, 4, 21,
						20, 1, 5, 5, 3, 1, 1, 7, 10, 1, 18, 14, 2, 8, 19, 0 });
				put("phasesData.p2.daily.D1", new double[] { 0, 0, 1, 1, 8, 2,
						2, 3, 4, 2, 4, 1, 1, 5, 16, 4, 16, 0, 0, 1, 0 });
				put("phasesData.p2.daily.D2", new double[] { 0, 0, 0, 0, 0, 0,
						2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
				put("phasesData.p2.daily.D3to5", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("phasesData.p2.daily.D6to10", new double[] { 0, 0, 0, 0, 0,
						0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
				put("phasesData.p2.daily.D10to20", new double[] { 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				put("phasesData.p2.daily.D20", new double[] { 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
			}
		};

		ansAcumsTasksValues = new HashMap<String, Double>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("tasksData.RES.acums.avgTimeTask", new Double(0));
				put("tasksData.RES.acums.D0", new Double(0));
				put("tasksData.RES.acums.D1", new Double(0));
				put("tasksData.RES.acums.D2", new Double(0));
				put("tasksData.RES.acums.D3to5", new Double(0));
				put("tasksData.RES.acums.D6to10", new Double(0));
				put("tasksData.RES.acums.D10to20", new Double(0));
				put("tasksData.RES.acums.D20", new Double(0));

				put("tasksData.NEG_REC_DESIST.acums.avgTimeTask", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D0", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D1", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D2", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D3to5", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D6to10", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D10to20", new Double(0));
				put("tasksData.NEG_REC_DESIST.acums.D20", new Double(0));

				put("tasksData.COR.acums.avgTimeTask", new Double(0));
				put("tasksData.COR.acums.D0", new Double(0));
				put("tasksData.COR.acums.D1", new Double(0));
				put("tasksData.COR.acums.D2", new Double(0));
				put("tasksData.COR.acums.D3to5", new Double(0));
				put("tasksData.COR.acums.D6to10", new Double(0));
				put("tasksData.COR.acums.D10to20", new Double(0));
				put("tasksData.COR.acums.D20", new Double(0));

				put("tasksData.CMN.acums.avgTimeTask", new Double(0));
				put("tasksData.CMN.acums.D0", new Double(0));
				put("tasksData.CMN.acums.D1", new Double(0));
				put("tasksData.CMN.acums.D2", new Double(0));
				put("tasksData.CMN.acums.D3to5", new Double(0));
				put("tasksData.CMN.acums.D6to10", new Double(0));
				put("tasksData.CMN.acums.D10to20", new Double(0));
				put("tasksData.CMN.acums.D20", new Double(0));

				put("tasksData.SOL.acums.avgTimeTask", new Double(0));
				put("tasksData.SOL.acums.D0", new Double(0));
				put("tasksData.SOL.acums.D1", new Double(0));
				put("tasksData.SOL.acums.D2", new Double(0));
				put("tasksData.SOL.acums.D3to5", new Double(0));
				put("tasksData.SOL.acums.D6to10", new Double(0));
				put("tasksData.SOL.acums.D10to20", new Double(0));
				put("tasksData.SOL.acums.D20", new Double(0));

				put("tasksData.GEST.acums.avgTimeTask", new Double(0));
				put("tasksData.GEST.acums.D0", new Double(0));
				put("tasksData.GEST.acums.D1", new Double(0));
				put("tasksData.GEST.acums.D2", new Double(0));
				put("tasksData.GEST.acums.D3to5", new Double(0));
				put("tasksData.GEST.acums.D6to10", new Double(0));
				put("tasksData.GEST.acums.D10to20", new Double(0));
				put("tasksData.GEST.acums.D20", new Double(0));

				put("tasksData.VAL.acums.avgTimeTask", new Double(0));
				put("tasksData.VAL.acums.D0", new Double(0));
				put("tasksData.VAL.acums.D1", new Double(0));
				put("tasksData.VAL.acums.D2", new Double(0));
				put("tasksData.VAL.acums.D3to5", new Double(0));
				put("tasksData.VAL.acums.D6to10", new Double(0));
				put("tasksData.VAL.acums.D10to20", new Double(0));
				put("tasksData.VAL.acums.D20", new Double(0));

				put("tasksData.GRAB.acums.avgTimeTask", new Double(0));
				put("tasksData.GRAB.acums.D0", new Double(0));
				put("tasksData.GRAB.acums.D1", new Double(0));
				put("tasksData.GRAB.acums.D2", new Double(0));
				put("tasksData.GRAB.acums.D3to5", new Double(0));
				put("tasksData.GRAB.acums.D6to10", new Double(0));
				put("tasksData.GRAB.acums.D10to20", new Double(0));
				put("tasksData.GRAB.acums.D20", new Double(0));

				put("tasksData.APORT_DICT.acums.avgTimeTask", new Double(0));
				put("tasksData.APORT_DICT.acums.D0", new Double(0));
				put("tasksData.APORT_DICT.acums.D1", new Double(0));
				put("tasksData.APORT_DICT.acums.D2", new Double(0));
				put("tasksData.APORT_DICT.acums.D3to5", new Double(0));
				put("tasksData.APORT_DICT.acums.D6to10", new Double(0));
				put("tasksData.APORT_DICT.acums.D10to20", new Double(0));
				put("tasksData.APORT_DICT.acums.D20", new Double(0));

				put("tasksData.REG.acums.avgTimeTask", new Double(0));
				put("tasksData.REG.acums.D0", new Double(0));
				put("tasksData.REG.acums.D1", new Double(0));
				put("tasksData.REG.acums.D2", new Double(0));
				put("tasksData.REG.acums.D3to5", new Double(0));
				put("tasksData.REG.acums.D6to10", new Double(0));
				put("tasksData.REG.acums.D10to20", new Double(0));
				put("tasksData.REG.acums.D20", new Double(0));

				put("tasksData.VER.acums.avgTimeTask", new Double(0));
				put("tasksData.VER.acums.D0", new Double(0));
				put("tasksData.VER.acums.D1", new Double(0));
				put("tasksData.VER.acums.D2", new Double(0));
				put("tasksData.VER.acums.D3to5", new Double(0));
				put("tasksData.VER.acums.D6to10", new Double(0));
				put("tasksData.VER.acums.D10to20", new Double(0));
				put("tasksData.VER.acums.D20", new Double(0));

				put("tasksData.RECT.acums.avgTimeTask", new Double(0));
				put("tasksData.RECT.acums.D0", new Double(0));
				put("tasksData.RECT.acums.D1", new Double(0));
				put("tasksData.RECT.acums.D2", new Double(0));
				put("tasksData.RECT.acums.D3to5", new Double(0));
				put("tasksData.RECT.acums.D6to10", new Double(0));
				put("tasksData.RECT.acums.D10to20", new Double(0));
				put("tasksData.RECT.acums.D20", new Double(0));

				put("tasksData.AM.acums.avgTimeTask", new Double(0));
				put("tasksData.AM.acums.D0", new Double(0));
				put("tasksData.AM.acums.D1", new Double(0));
				put("tasksData.AM.acums.D2", new Double(0));
				put("tasksData.AM.acums.D3to5", new Double(0));
				put("tasksData.AM.acums.D6to10", new Double(0));
				put("tasksData.AM.acums.D10to20", new Double(0));
				put("tasksData.AM.acums.D20", new Double(0));

				put("tasksData.RES.daily.D0", new Double(0));
				put("tasksData.RES.daily.D1", new Double(0));
				put("tasksData.RES.daily.D2", new Double(0));
				put("tasksData.RES.daily.D3to5", new Double(0));
				put("tasksData.RES.daily.D6to10", new Double(0));
				put("tasksData.RES.daily.D10to20", new Double(0));
				put("tasksData.RES.daily.D20", new Double(0));

				put("tasksData.NEG_REC_DESIST.daily.D0", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D1", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D2", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D3to5", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D6to10", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D10to20", new Double(0));
				put("tasksData.NEG_REC_DESIST.daily.D20", new Double(0));

				put("tasksData.COR.daily.D0", new Double(0));
				put("tasksData.COR.daily.D1", new Double(0));
				put("tasksData.COR.daily.D2", new Double(0));
				put("tasksData.COR.daily.D3to5", new Double(0));
				put("tasksData.COR.daily.D6to10", new Double(0));
				put("tasksData.COR.daily.D10to20", new Double(0));
				put("tasksData.COR.daily.D20", new Double(0));

				put("tasksData.CMN.daily.D0", new Double(0));
				put("tasksData.CMN.daily.D1", new Double(0));
				put("tasksData.CMN.daily.D2", new Double(0));
				put("tasksData.CMN.daily.D3to5", new Double(0));
				put("tasksData.CMN.daily.D6to10", new Double(0));
				put("tasksData.CMN.daily.D10to20", new Double(0));
				put("tasksData.CMN.daily.D20", new Double(0));

				put("tasksData.SOL.daily.D0", new Double(0));
				put("tasksData.SOL.daily.D1", new Double(0));
				put("tasksData.SOL.daily.D2", new Double(0));
				put("tasksData.SOL.daily.D3to5", new Double(0));
				put("tasksData.SOL.daily.D6to10", new Double(0));
				put("tasksData.SOL.daily.D10to20", new Double(0));
				put("tasksData.SOL.daily.D20", new Double(0));

				put("tasksData.GEST.daily.D0", new Double(0));
				put("tasksData.GEST.daily.D1", new Double(0));
				put("tasksData.GEST.daily.D2", new Double(0));
				put("tasksData.GEST.daily.D3to5", new Double(0));
				put("tasksData.GEST.daily.D6to10", new Double(0));
				put("tasksData.GEST.daily.D10to20", new Double(0));
				put("tasksData.GEST.daily.D20", new Double(0));

				put("tasksData.VAL.daily.D0", new Double(0));
				put("tasksData.VAL.daily.D1", new Double(0));
				put("tasksData.VAL.daily.D2", new Double(0));
				put("tasksData.VAL.daily.D3to5", new Double(0));
				put("tasksData.VAL.daily.D6to10", new Double(0));
				put("tasksData.VAL.daily.D10to20", new Double(0));
				put("tasksData.VAL.daily.D20", new Double(0));

				put("tasksData.GRAB.daily.D0", new Double(0));
				put("tasksData.GRAB.daily.D1", new Double(0));
				put("tasksData.GRAB.daily.D2", new Double(0));
				put("tasksData.GRAB.daily.D3to5", new Double(0));
				put("tasksData.GRAB.daily.D6to10", new Double(0));
				put("tasksData.GRAB.daily.D10to20", new Double(0));
				put("tasksData.GRAB.daily.D20", new Double(0));

				put("tasksData.APORT_DICT.daily.D0", new Double(0));
				put("tasksData.APORT_DICT.daily.D1", new Double(0));
				put("tasksData.APORT_DICT.daily.D2", new Double(0));
				put("tasksData.APORT_DICT.daily.D3to5", new Double(0));
				put("tasksData.APORT_DICT.daily.D6to10", new Double(0));
				put("tasksData.APORT_DICT.daily.D10to20", new Double(0));
				put("tasksData.APORT_DICT.daily.D20", new Double(0));

				put("tasksData.REG.daily.D0", new Double(0));
				put("tasksData.REG.daily.D1", new Double(0));
				put("tasksData.REG.daily.D2", new Double(0));
				put("tasksData.REG.daily.D3to5", new Double(0));
				put("tasksData.REG.daily.D6to10", new Double(0));
				put("tasksData.REG.daily.D10to20", new Double(0));
				put("tasksData.REG.daily.D20", new Double(0));

				put("tasksData.VER.daily.D0", new Double(0));
				put("tasksData.VER.daily.D1", new Double(0));
				put("tasksData.VER.daily.D2", new Double(0));
				put("tasksData.VER.daily.D3to5", new Double(0));
				put("tasksData.VER.daily.D6to10", new Double(0));
				put("tasksData.VER.daily.D10to20", new Double(0));
				put("tasksData.VER.daily.D20", new Double(0));

				put("tasksData.RECT.daily.D0", new Double(0));
				put("tasksData.RECT.daily.D1", new Double(0));
				put("tasksData.RECT.daily.D2", new Double(0));
				put("tasksData.RECT.daily.D3to5", new Double(0));
				put("tasksData.RECT.daily.D6to10", new Double(0));
				put("tasksData.RECT.daily.D10to20", new Double(0));
				put("tasksData.RECT.daily.D20", new Double(0));

				put("tasksData.AM.daily.D0", new Double(0));
				put("tasksData.AM.daily.D1", new Double(0));
				put("tasksData.AM.daily.D2", new Double(0));
				put("tasksData.AM.daily.D3to5", new Double(0));
				put("tasksData.AM.daily.D6to10", new Double(0));
				put("tasksData.AM.daily.D10to20", new Double(0));
				put("tasksData.AM.daily.D20", new Double(0));

				put("phasesData.p1.acums.avgTimePhase", new Double(0));
				put("phasesData.p1.acums.D0", new Double(0));
				put("phasesData.p1.acums.D1", new Double(0));
				put("phasesData.p1.acums.D2", new Double(0));
				put("phasesData.p1.acums.D3to5", new Double(0));
				put("phasesData.p1.acums.D6to10", new Double(0));
				put("phasesData.p1.acums.D10to20", new Double(0));
				put("phasesData.p1.acums.D20", new Double(0));

				put("phasesData.p2.acums.avgTimePhase", new Double(0));
				put("phasesData.p2.acums.D0", new Double(0));
				put("phasesData.p2.acums.D1", new Double(0));
				put("phasesData.p2.acums.D2", new Double(0));
				put("phasesData.p2.acums.D3to5", new Double(0));
				put("phasesData.p2.acums.D6to10", new Double(0));
				put("phasesData.p2.acums.D10to20", new Double(0));
				put("phasesData.p2.acums.D20", new Double(0));
			}
		};

	}

	/**
	 * Method that create example data about {@link ClaimsAnsCompliance} .
	 * 
	 * @return {@link ClaimsAnsCompliance}
	 * @throws ApiException
	 *             the kind of exception.
	 */
	@Override
	public ClaimsAnsCompliance createAndPersistsMockDataClaimsAnsComplianceExampleData(
			Long initialYear, Long initialMonth, Long initialDay, Long endYear,
			Long endMonth, Long endDay) throws ApiException {

		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: createAndPersistsMockDataClaimsAnsComplianceExampleData START");

		List<ClaimsAnsCompliance> listRegs = new ArrayList<ClaimsAnsCompliance>();
		ClaimsAnsCompliance claimsAnsCompliance = null;

		// DateFormat dateFormater = new SimpleDateFormat(DAILY_DATE_FORMAT);

		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, initialYear.intValue());
		startCal.set(Calendar.MONTH,
				(initialMonth.intValue() > 0) ? initialMonth.intValue() - 1
						: initialMonth.intValue());
		startCal.set(Calendar.DAY_OF_MONTH, initialDay.intValue());

		Calendar endCal = Calendar.getInstance();
		/*
		 * endCal.set(Calendar.YEAR, 2016); endCal.set(Calendar.MONTH,
		 * Calendar.JANUARY); endCal.set(Calendar.DAY_OF_MONTH, 1);
		 */
		endCal.set(Calendar.YEAR, endYear.intValue());
		endCal.set(
				Calendar.MONTH,
				(endMonth.intValue() > 0) ? endMonth.intValue() - 1 : endMonth
						.intValue());
		endCal.set(Calendar.DAY_OF_MONTH, endDay.intValue());

		Date startDate = startCal.getTime();
		Date endDate = endCal.getTime();

		boolean isFirstIteration = true;
		try {
			while (startDate.before(endDate)) {

				DateFormat dateFormaterForId = new SimpleDateFormat("yyyyMMdd");

				claimsAnsCompliance = new ClaimsAnsCompliance();

				String auxId = Utilities.createHashKeyEncoded(
						ApplicationConstants.SECRET_KEY, "Claim_"
								+ dateFormaterForId.format(startDate));
				claimsAnsCompliance.setId(auxId);

				claimsAnsCompliance.setName("Reclamaciones");
				claimsAnsCompliance.setDate(startDate.getTime());

				Random rn = new Random();
				// int range = 100 - 10 + 1;
				int range = 20;

				int index = rn.nextInt(range);

				claimsAnsCompliance.setAns(Double.valueOf(
						ansMockValues.get("ans")[index]).intValue()
						* ApplicationConstants.DAY_SECONDS);

				// PROCESS DATA

				/*
				 * CALCULATE ACUMS FOR PROCESSES
				 */

				calculateAcumsForProcess(startCal, index, isFirstIteration);

				// Daily counters

				calculateDailyForProcess(startCal, index, isFirstIteration);

				PROCESSES_ACUMS_MOCK.put("avgTimeProcess",
						acumProcessAvgTimeProcess
								* ApplicationConstants.DAY_SECONDS / 100);

				PROCESSES_ACUMS_MOCK.put("newInstances",
						acumProcessDataNewInstances);
				PROCESSES_ACUMS_MOCK.put("openInstances",
						acumProcessDataOpenInstances);
				PROCESSES_ACUMS_MOCK.put("closedInstances",
						acumProcessDataClosedInstances);
				PROCESSES_ACUMS_MOCK.put("D0", acumProcessDataAcumsD0);
				PROCESSES_ACUMS_MOCK.put("D1", acumProcessDataAcumsD1);
				PROCESSES_ACUMS_MOCK.put("D2", acumProcessDataAcumsD2);
				PROCESSES_ACUMS_MOCK.put("D3to5", acumProcessDataAcumsD3to5);
				PROCESSES_ACUMS_MOCK.put("D6to10", acumProcessDataAcumsD6to10);
				PROCESSES_ACUMS_MOCK
						.put("D10to20", acumProcessDataAcumsD10to20);
				PROCESSES_ACUMS_MOCK.put("D20", acumProcessDataAcumsD20);

				claimsAnsCompliance.getProcessData().put("acums",
						new HashMap<String, Object>(PROCESSES_ACUMS_MOCK));
				PROCESSES_ACUMS_MOCK.clear();

				PROCESSES_DAILY_MOCK
						.put("avgTimeProcess",
								ansMockValues
										.get("processData.daily.avgTimeProcess")[index]
										* ApplicationConstants.DAY_SECONDS
										/ 100);
				PROCESSES_DAILY_MOCK.put("newInstances",
						dailyProcessDataNewInstances);
				PROCESSES_DAILY_MOCK.put("openInstances",
						dailyProcessDataOpenInstances);
				PROCESSES_DAILY_MOCK.put("closedInstances",
						dailyProcessDataClosedInstances);
				PROCESSES_DAILY_MOCK.put("D0", dailyProcessDataAcumsD0);
				PROCESSES_DAILY_MOCK.put("D1", dailyProcessDataAcumsD1);
				PROCESSES_DAILY_MOCK.put("D2", dailyProcessDataAcumsD2);
				PROCESSES_DAILY_MOCK.put("D3to5", dailyProcessDataAcumsD3to5);
				PROCESSES_DAILY_MOCK.put("D6to10", dailyProcessDataAcumsD6to10);
				PROCESSES_DAILY_MOCK.put("D10to20",
						dailyProcessDataAcumsD10to20);
				PROCESSES_DAILY_MOCK.put("D20", dailyProcessDataAcumsD20);
				claimsAnsCompliance.getProcessData().put("daily",
						new HashMap<String, Object>(PROCESSES_DAILY_MOCK));
				PROCESSES_DAILY_MOCK.clear();

				// TASKS DATA

				/*
				 * COMMON MOCK DETAILS FOR A TASK ACUMS
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_ALTA_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				/*
				 * ACUMS
				 */
				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_ALTA_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				/*
				 * COMMON MOCK DETAILS FOR A TASK REG
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_REGISTRO_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_REGISTRO_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK GEST
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_GESTION_RESOL_ID, startCal,
						index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(
						ApplicationConstants.TASK_GESTION_RESOL_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK SOL
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_APOR_INFO_DOCUM_ID, startCal,
						index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(
						ApplicationConstants.TASK_APOR_INFO_DOCUM_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK RECT
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_RECTIFICAR_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_RECTIFICAR_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK NEG_REC_DESIST
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(
						ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK APORT_DICT
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_APORT_DICTA_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_APORT_DICTA_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK VER
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_CONT_MAN_VERIF_ID, startCal,
						index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(
						ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK GRAB
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_CONT_MAN_GRAB_ID, startCal,
						index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(
						ApplicationConstants.TASK_CONT_MAN_GRAB_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK VAL
				 */

				calculateAcumsForTask(
						ApplicationConstants.TASK_CONT_MAN_VAL_ID, startCal,
						index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_CONT_MAN_VAL_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK RES
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_RESOLVER_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_RESOLVER_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK COR
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_CORREGIR_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_CORREGIR_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				/*
				 * COMMON MOCK DETAILS FOR A TASK CNM
				 */

				calculateAcumsForTask(ApplicationConstants.TASK_REAL_COMM_ID,
						startCal, index, isFirstIteration);

				ANS_TASK_DETAILS_PROCESSES_MOCK.put("avgTimeTask", avgTimeTask);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D0", acumTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D1", acumTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D2", acumTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						acumTasksDataAcumsD20);

				TASKS_ACUMS_MOCK.put(ApplicationConstants.TASK_REAL_COMM_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));

				claimsAnsCompliance.getTasksData().put("acums",
						new HashMap<String, Object>(TASKS_ACUMS_MOCK));
				TASKS_ACUMS_MOCK.clear();

				/*
				 * DAILY
				 */

				// TASK DAILY ACUMS FOR TASK AM
				calculateDailyForTask(ApplicationConstants.TASK_ALTA_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_ALTA_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_ALTA_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK REG
				calculateDailyForTask(ApplicationConstants.TASK_REGISTRO_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_REGISTRO_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_REGISTRO_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK GEST
				calculateDailyForTask(
						ApplicationConstants.TASK_GESTION_RESOL_ID, startCal,
						index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_GESTION_RESOL_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(
						ApplicationConstants.TASK_GESTION_RESOL_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK SOL
				calculateDailyForTask(
						ApplicationConstants.TASK_APOR_INFO_DOCUM_ID, startCal,
						index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_APOR_INFO_DOCUM_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(
						ApplicationConstants.TASK_APOR_INFO_DOCUM_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK RECT
				calculateDailyForTask(ApplicationConstants.TASK_RECTIFICAR_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_RECTIFICAR_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_RECTIFICAR_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK NEG_REC_DESIST
				calculateDailyForTask(
						ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("avgTimeTask",
								ansMockValues
										.get("tasksData"
												+ "."
												+ ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID
												+ "." + "daily.avgTimeTask")[index]
										* ApplicationConstants.DAY_SECONDS
										/ 100);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(
						ApplicationConstants.TASK_REA_NEGOC_REC_DESIST_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK APORT_DICT
				calculateDailyForTask(ApplicationConstants.TASK_APORT_DICTA_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_APORT_DICTA_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_APORT_DICTA_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK VER
				calculateDailyForTask(
						ApplicationConstants.TASK_CONT_MAN_VERIF_ID, startCal,
						index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_CONT_MAN_VERIF_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(
						ApplicationConstants.TASK_CONT_MAN_VERIF_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK GRAB
				calculateDailyForTask(
						ApplicationConstants.TASK_CONT_MAN_GRAB_ID, startCal,
						index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_CONT_MAN_GRAB_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(
						ApplicationConstants.TASK_CONT_MAN_GRAB_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK VAL
				calculateDailyForTask(
						ApplicationConstants.TASK_CONT_MAN_VAL_ID, startCal,
						index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_CONT_MAN_VAL_ID
								+ "." + "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_CONT_MAN_VAL_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK RES
				calculateDailyForTask(ApplicationConstants.TASK_RESOLVER_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_RESOLVER_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_RESOLVER_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK COR
				calculateDailyForTask(ApplicationConstants.TASK_CORREGIR_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_CORREGIR_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_CORREGIR_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				// TASK DAILY ACUMS FOR TASK COR
				calculateDailyForTask(ApplicationConstants.TASK_REAL_COMM_ID,
						startCal, index);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put(
						"avgTimeTask",
						ansMockValues.get("tasksData" + "."
								+ ApplicationConstants.TASK_REAL_COMM_ID + "."
								+ "daily.avgTimeTask")[index]);

				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D0", dailyTasksDataAcumsD0);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D1", dailyTasksDataAcumsD1);
				ANS_TASK_DETAILS_PROCESSES_MOCK
						.put("D2", dailyTasksDataAcumsD2);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyTasksDataAcumsD3to5);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyTasksDataAcumsD6to10);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyTasksDataAcumsD10to20);
				ANS_TASK_DETAILS_PROCESSES_MOCK.put("D20",
						dailyTasksDataAcumsD20);

				TASKS_DAILY_MOCK.put(ApplicationConstants.TASK_REAL_COMM_ID,
						new HashMap<String, Object>(
								ANS_TASK_DETAILS_PROCESSES_MOCK));
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				claimsAnsCompliance.getTasksData().put("daily",
						new HashMap<String, Object>(TASKS_DAILY_MOCK));
				TASKS_DAILY_MOCK.clear();

				// PHASES DATA

				/*
				 * COMMON MOCK DETAILS FOR A PHASE ONE
				 */

				calculateAcumsForPhase(ApplicationConstants.PHASE_ONE_ID,
						startCal, index, isFirstIteration);

				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D0",
						acumPhasesDataAcumsD0);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D1",
						acumPhasesDataAcumsD1);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D2",
						acumPhasesDataAcumsD2);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumPhasesDataAcumsD3to5);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumPhasesDataAcumsD6to10);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumPhasesDataAcumsD10to20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D20",
						acumPhasesDataAcumsD20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put(
						"avgTimePhase",
						ansMockValues.get("phasesData."
								+ ApplicationConstants.PHASE_ONE_ID
								+ ".acums.avgTimePhase")[index]
								* ApplicationConstants.DAY_SECONDS / 100);

				/*
				 * ACUMS
				 */
				PHASES_ACUMS_MOCK.put(ApplicationConstants.PHASE_ONE_ID,
						new HashMap<String, Object>(
								ANS_PHASES_DETAILS_PROCESSES_MOCK));
				ANS_PHASES_DETAILS_PROCESSES_MOCK.clear();

				/*
				 * COMMON MOCK DETAILS FOR A PHASE TWO
				 */

				calculateAcumsForPhase(ApplicationConstants.PHASE_TWO_ID,
						startCal, index, isFirstIteration);

				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D0",
						acumPhasesDataAcumsD0);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D1",
						acumPhasesDataAcumsD1);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D2",
						acumPhasesDataAcumsD2);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumPhasesDataAcumsD3to5);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumPhasesDataAcumsD6to10);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumPhasesDataAcumsD10to20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D20",
						acumPhasesDataAcumsD20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put(
						"avgTimePhase",
						ansMockValues.get("phasesData."
								+ ApplicationConstants.PHASE_TWO_ID
								+ ".acums.avgTimePhase")[index]
								* ApplicationConstants.DAY_SECONDS / 100);

				PHASES_ACUMS_MOCK.put(ApplicationConstants.PHASE_TWO_ID,
						new HashMap<String, Object>(
								ANS_PHASES_DETAILS_PROCESSES_MOCK));
				ANS_PHASES_DETAILS_PROCESSES_MOCK.clear();

				claimsAnsCompliance.getPhasesData().put("acums",
						new HashMap<String, Object>(PHASES_ACUMS_MOCK));
				PHASES_ACUMS_MOCK.clear();

				/*
				 * DAILY FOR PHASE 1
				 */
				calculateDailyForPhase(ApplicationConstants.PHASE_ONE_ID,
						startCal, index);

				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D0",
						acumPhasesDataAcumsD0);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D1",
						acumPhasesDataAcumsD1);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D2",
						acumPhasesDataAcumsD2);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D3to5",
						acumPhasesDataAcumsD3to5);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D6to10",
						acumPhasesDataAcumsD6to10);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D10to20",
						acumPhasesDataAcumsD10to20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D20",
						acumPhasesDataAcumsD20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put(
						"avgTimePhase",
						ansMockValues.get("phasesData."
								+ ApplicationConstants.PHASE_ONE_ID
								+ ".daily.avgTimePhase")[index]
								* ApplicationConstants.DAY_SECONDS / 100);

				calculateDailyForPhase(ApplicationConstants.PHASE_TWO_ID,
						startCal, index);

				PHASES_DAILY_MOCK.put(ApplicationConstants.PHASE_ONE_ID,
						new HashMap<String, Object>(
								ANS_PHASES_DETAILS_PROCESSES_MOCK));
				ANS_PHASES_DETAILS_PROCESSES_MOCK.clear();

				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D0",
						dailyPhasesDataAcumsD0);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D1",
						dailyPhasesDataAcumsD1);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D2",
						dailyPhasesDataAcumsD2);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D3to5",
						dailyPhasesDataAcumsD3to5);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D6to10",
						dailyPhasesDataAcumsD6to10);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D10to20",
						dailyPhasesDataAcumsD10to20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put("D20",
						dailyPhasesDataAcumsD20);
				ANS_PHASES_DETAILS_PROCESSES_MOCK.put(
						"avgTimePhase",
						ansMockValues.get("phasesData."
								+ ApplicationConstants.PHASE_TWO_ID
								+ ".daily.avgTimePhase")[index]
								* ApplicationConstants.DAY_SECONDS / 100);

				PHASES_DAILY_MOCK.put(ApplicationConstants.PHASE_TWO_ID,
						new HashMap<String, Object>(
								ANS_PHASES_DETAILS_PROCESSES_MOCK));
				claimsAnsCompliance.getPhasesData().put("daily",
						new HashMap<String, Object>(PHASES_DAILY_MOCK));
				PHASES_DAILY_MOCK.clear();

				listRegs.add(claimsAnsCompliance);
				log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
						+ " METHOD: createAndPersistsMockDataClaimsAnsComplianceExampleData. Insertion of "
						+ claimsAnsCompliance.toString());
				mongoOps.insert(listRegs, ClaimsAnsCompliance.class);
				listRegs.clear();
				ANS_OK_TASKS_DETAILS_PROCESSES_MOCK.clear();
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();
				TASKS_DAILY_MOCK.clear();
				TASKS_ACUMS_MOCK.clear();
				ANS_TASK_DETAILS_PROCESSES_MOCK.clear();

				startCal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = startCal.getTime();

				// Set to false the first iteration flag.
				isFirstIteration = false;
			}
			claimsAnsCompliance = null;

			claimsAnsCompliance = mongoOps.findAll(ClaimsAnsCompliance.class)
					.get(0);
		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: createAndPersistsMockDataClaimsAnsComplianceExampleData.");
			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: createAndPersistsMockDataClaimsAnsComplianceExampleData END");
		return claimsAnsCompliance;
	}

	/**
	 * Method that calculates de acums for process at a certain day.
	 * 
	 * @param startCal
	 *            .
	 * @param index
	 *            .
	 * @param isFirstIteration
	 *            .
	 */
	private void calculateAcumsForProcess(Calendar startCal, int index,
			boolean isFirstIteration) {
		acumProcessDataNewInstances = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.acums.newInstances")[index]
				: acumProcessDataNewInstances
						+ ansMockValues.get("processData.acums.newInstances")[index];

		acumProcessDataOpenInstances = isFirstIteration ? (5 + (ansMockValues
				.get("processData.acums.newInstances")[index] - ansMockValues
				.get("processData.acums.closedInstances")[index]))
				: (acumProcessDataOpenInstances + (ansMockValues
						.get("processData.acums.newInstances")[index] - ansMockValues
						.get("processData.acums.closedInstances")[index]));

		acumProcessDataClosedInstances = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.acums.closedInstances")[index]
				: acumProcessDataClosedInstances
						+ ansMockValues
								.get("processData.acums.closedInstances")[index];

		acumProcessDataAcumsD0 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D0")[index] : acumProcessDataAcumsD0
				+ ansMockValues.get("processData.daily.D0")[index];

		acumProcessDataAcumsD1 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D1")[index] : acumProcessDataAcumsD1
				+ ansMockValues.get("processData.daily.D1")[index];

		acumProcessDataAcumsD2 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D2")[index] : acumProcessDataAcumsD2
				+ ansMockValues.get("processData.daily.D2")[index];

		acumProcessDataAcumsD3to5 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D3to5")[index]
				: acumProcessDataAcumsD3to5
						+ ansMockValues.get("processData.daily.D3to5")[index];

		acumProcessDataAcumsD6to10 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D6to10")[index]
				: acumProcessDataAcumsD6to10
						+ ansMockValues.get("processData.daily.D6to10")[index];

		acumProcessDataAcumsD10to20 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D10to20")[index]
				: acumProcessDataAcumsD10to20
						+ ansMockValues.get("processData.daily.D10to20")[index];

		acumProcessDataAcumsD20 = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.daily.D20")[index] : acumProcessDataAcumsD20
				+ ansMockValues.get("processData.daily.D20")[index];

		Double totalAcumsProcessData =  acumProcessDataAcumsD0 +  acumProcessDataAcumsD1
				+ acumProcessDataAcumsD2 + acumProcessDataAcumsD3to5
				+ acumProcessDataAcumsD6to10 + acumProcessDataAcumsD10to20
				+ acumProcessDataAcumsD20;

		acumProcessAvgTimeProcess = (startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
				.get("processData.acums.avgTimeProcess")[index]
				* totalAcumsProcessData : acumProcessAvgTimeProcess
				+ ansMockValues.get("processData.acums.avgTimeProcess")[index]
				* totalAcumsProcessData;

	}

	/**
	 * Method that calculates de acums for Process daily
	 * 
	 * @param startCal
	 * @param index
	 * @param isFirstIteration
	 */
	private void calculateDailyForProcess(Calendar startCal, int index,
			boolean isFirstIteration) {
		dailyProcessDataNewInstances = ansMockValues
				.get("processData.daily.newInstances")[index];

		dailyProcessDataOpenInstances = isFirstIteration ? (5 + ansMockValues
				.get("processData.daily.openInstances")[index]) : ansMockValues
				.get("processData.daily.openInstances")[index];

		dailyProcessDataClosedInstances = ansMockValues
				.get("processData.daily.closedInstances")[index];

		dailyProcessDataAcumsD0 = ansMockValues.get("processData.daily.D0")[index];

		dailyProcessDataAcumsD1 = ansMockValues.get("processData.daily.D1")[index];

		dailyProcessDataAcumsD2 = ansMockValues.get("processData.daily.D2")[index];

		dailyProcessDataAcumsD3to5 = ansMockValues
				.get("processData.daily.D3to5")[index];

		dailyProcessDataAcumsD6to10 = ansMockValues
				.get("processData.daily.D6to10")[index];

		dailyProcessDataAcumsD10to20 = ansMockValues
				.get("processData.daily.D10to20")[index];

		dailyProcessDataAcumsD20 = ansMockValues.get("processData.daily.D20")[index];
	}

	/**
	 * Method that calculates de acums for task levels
	 * 
	 * @param keyForTask
	 * @param startCal
	 * @param index
	 * @param isFirstIteration
	 */
	private void calculateAcumsForTask(String keyForTask, Calendar startCal,
			int index, boolean isFirstIteration) {

		ansAcumsTasksValues
				.put("tasksData" + "." + keyForTask + "." + "acums.D0",
						(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
								.get("tasksData" + "." + keyForTask + "."
										+ "daily.D0")[index]
								: ansAcumsTasksValues.get("tasksData" + "."
										+ keyForTask + "." + "acums.D0")
										+ ansMockValues
												.get("tasksData" + "."
														+ keyForTask + "."
														+ "daily.D0")[index]);
		acumTasksDataAcumsD0 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D0");

		ansAcumsTasksValues
				.put("tasksData" + "." + keyForTask + "." + "acums.D1",
						(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
								.get("tasksData" + "." + keyForTask + "."
										+ "daily.D1")[index]
								: ansAcumsTasksValues.get("tasksData" + "."
										+ keyForTask + "." + "acums.D1")
										+ ansMockValues
												.get("tasksData" + "."
														+ keyForTask + "."
														+ "daily.D1")[index]);
		acumTasksDataAcumsD1 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D1");

		ansAcumsTasksValues
				.put("tasksData" + "." + keyForTask + "." + "acums.D2",
						(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
								.get("tasksData" + "." + keyForTask + "."
										+ "daily.D2")[index]
								: ansAcumsTasksValues.get("tasksData" + "."
										+ keyForTask + "." + "acums.D2")
										+ ansMockValues
												.get("tasksData" + "."
														+ keyForTask + "."
														+ "daily.D2")[index]);
		acumTasksDataAcumsD2 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D2");

		ansAcumsTasksValues.put(
				"tasksData" + "." + keyForTask + "." + "acums.D3to5",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("tasksData" + "." + keyForTask + "."
								+ "daily.D3to5")[index] : ansAcumsTasksValues
						.get("tasksData" + "." + keyForTask + "."
								+ "acums.D3to5")
						+ ansMockValues.get("tasksData" + "." + keyForTask
								+ "." + "daily.D3to5")[index]);
		acumTasksDataAcumsD3to5 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D3to5");

		ansAcumsTasksValues.put(
				"tasksData" + "." + keyForTask + "." + "acums.D6to10",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("tasksData" + "." + keyForTask + "."
								+ "daily.D6to10")[index] : ansAcumsTasksValues
						.get("tasksData" + "." + keyForTask + "."
								+ "acums.D6to10")
						+ ansMockValues.get("tasksData" + "." + keyForTask
								+ "." + "daily.D6to10")[index]);
		acumTasksDataAcumsD6to10 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D6to10");

		ansAcumsTasksValues.put(
				"tasksData" + "." + keyForTask + "." + "acums.D10to20",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("tasksData" + "." + keyForTask + "."
								+ "daily.D10to20")[index] : ansAcumsTasksValues
						.get("tasksData" + "." + keyForTask + "."
								+ "acums.D10to20")
						+ ansMockValues.get("tasksData" + "." + keyForTask
								+ "." + "daily.D10to20")[index]);
		acumTasksDataAcumsD10to20 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D10to20");

		ansAcumsTasksValues
				.put("tasksData" + "." + keyForTask + "." + "acums.D20",
						(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
								.get("tasksData" + "." + keyForTask + "."
										+ "daily.D20")[index]
								: ansAcumsTasksValues.get("tasksData" + "."
										+ keyForTask + "." + "acums.D20")
										+ ansMockValues.get("tasksData" + "."
												+ keyForTask + "."
												+ "daily.D20")[index]);
		acumTasksDataAcumsD20 = ansAcumsTasksValues.get("tasksData" + "."
				+ keyForTask + "." + "acums.D20");

		Double totalForAns = acumTasksDataAcumsD0 + acumTasksDataAcumsD1
				+ acumTasksDataAcumsD2 + acumTasksDataAcumsD3to5
				+ acumTasksDataAcumsD6to10 + acumTasksDataAcumsD10to20
				+ acumTasksDataAcumsD20;

		Map<Double, Double> auxMapValues = new HashMap<Double, Double>();
		auxMapValues.put(
				ansAcumsTasksValues.get("tasksData" + "." + keyForTask + "."
						+ "avgTimeTask"), totalForAns);

		ansAcumsTasksValues.put(
				"tasksData" + "." + keyForTask + "." + "avgTimeTask",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("tasksData" + "." + keyForTask
								+ ".acums.avgTimeTask")[index]
						* totalForAns : ansAcumsTasksValues.get("tasksData"
						+ "." + keyForTask + "." + "avgTimeTask")
						+ ansMockValues.get("tasksData" + "." + keyForTask
								+ ".acums.avgTimeTask")[index] * totalForAns);
		avgTimeTask = ansAcumsTasksValues.get("tasksData" + "." + keyForTask
				+ "." + "avgTimeTask");
	}

	/**
	 * Method to calculate acums for phase.
	 * 
	 * @param keyForPhase
	 *            .
	 * @param startCal
	 *            .
	 * @param index
	 *            .
	 * @param isFirstIteration
	 *            .
	 */
	private void calculateAcumsForPhase(String keyForPhase, Calendar startCal,
			int index, boolean isFirstIteration) {
		
		if(isFirstIteration || (startCal.get(Calendar.DAY_OF_MONTH) == 1)){
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D0", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D1", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D2", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D3to5", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D6to10", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D10to20", new Double(0));
			ansAcumsTasksValues.put("phasesData" + "."
					+ keyForPhase + "." + "acums.D20", new Double(0));
			
		}

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D0",
				((startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D0")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D0")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D0")[index]));

		acumPhasesDataAcumsD0 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D0");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D1",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D1")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D1")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D1")[index]);

		acumPhasesDataAcumsD1 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D1");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D2",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D2")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D2")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D2")[index]);

		acumPhasesDataAcumsD2 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D2");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D3to5",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D3to5")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D3to5")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D3to5")[index]);
		acumPhasesDataAcumsD3to5 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D3to5");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D6to10",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D6to10")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D6to10")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D6to10")[index]);

		acumPhasesDataAcumsD6to10 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D6to10");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D10to20",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D10to20")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D10to20")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D10to20")[index]);

		acumPhasesDataAcumsD10to20 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D10to20");

		ansAcumsTasksValues.put(
				"phasesData" + "." + keyForPhase + "." + "acums.D20",
				(startCal.get(Calendar.DAY_OF_MONTH) == 1) ? ansMockValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "daily.D20")[index] : ansAcumsTasksValues
						.get("phasesData" + "." + keyForPhase + "."
								+ "acums.D20")
						+ ansMockValues.get("phasesData" + "." + keyForPhase
								+ "." + "daily.D20")[index]);

		acumPhasesDataAcumsD20 = ansAcumsTasksValues.get("phasesData" + "."
				+ keyForPhase + "." + "acums.D20");

	}

	/**
	 * Method Calculate Daily For Task.
	 * 
	 * @param keyForTask
	 *            .
	 * @param startCal
	 *            .
	 * @param index
	 *            .
	 */
	private static void calculateDailyForTask(String keyForTask,
			Calendar startCal, int index) {
		// Daily counters
		dailyTasksDataAcumsD0 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D0")[index];

		dailyTasksDataAcumsD1 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D1")[index];

		dailyTasksDataAcumsD2 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D2")[index];

		dailyTasksDataAcumsD3to5 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D3to5")[index];

		dailyTasksDataAcumsD6to10 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D6to10")[index];

		dailyTasksDataAcumsD10to20 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D10to20")[index];

		dailyTasksDataAcumsD20 = ansMockValues.get("tasksData" + "."
				+ keyForTask + "." + "daily.D20")[index];
	}

	/**
	 * Method Calculate Daily Instances For Phase
	 * 
	 * @param keyForPhase
	 * @param startCal
	 * @param index
	 */
	private void calculateDailyForPhase(String keyForPhase, Calendar startCal,
			int index) {

		dailyPhasesDataAcumsD0 = dailyPhasesDataAcumsD0
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D0")[index];
		dailyPhasesDataAcumsD1 = dailyPhasesDataAcumsD1
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D1")[index];

		dailyPhasesDataAcumsD2 = dailyPhasesDataAcumsD2
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D2")[index];

		dailyPhasesDataAcumsD3to5 = dailyPhasesDataAcumsD3to5
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D3to5")[index];

		dailyPhasesDataAcumsD6to10 = dailyPhasesDataAcumsD6to10
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D6to10")[index];

		dailyPhasesDataAcumsD10to20 = dailyPhasesDataAcumsD10to20
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D10to20")[index];

		dailyPhasesDataAcumsD20 = dailyPhasesDataAcumsD20
				+ ansMockValues.get("phasesData" + "." + keyForPhase + "."
						+ "daily.D20")[index];
	}

	/**
	 * Method that returns all documents in DB about {@link ClaimsAnsCompliance}
	 * 
	 * @return {@link ClaimsAnsCompliance} (List)
	 * @throws ApiException
	 *             the exception.
	 */
	@Override
	public List<ClaimsAnsCompliance> getClaimAnsComplianceList()
			throws ApiException {
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getClaimsAnsComplianceList START");

		try {
			log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
					+ " METHOD: getClaimsAnsComplianceList END");
			return getClaimAnsComplianceListWithFilter(null, null);
		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: getRegistrationsInOrdersAggrByDayList.");

			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
	}

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
	@Override
	public List<ClaimsAnsCompliance> getClaimAnsComplianceListWithFilter(
			Long year, Long month) throws ApiException {
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getClaimAnsComplianceListWithFilter START");
		List<ClaimsAnsCompliance> results;
		Long from = new Long(0);
		Long to = new Long(0);
		try {
			Query queryClaimsAnsComplianceListForProcessWithFilter = new Query();

			if (year != null && month != null) {
				Calendar startDate = Calendar.getInstance();
				Calendar endDate = Calendar.getInstance();
				DateFormat dateFormaterMonth = new SimpleDateFormat("MM");
				DateFormat dateFormaterYear = new SimpleDateFormat("yyyy");

				startDate.set(Calendar.YEAR, year.intValue());

				startDate.set(Calendar.MONTH,
						Long.valueOf((month > 0) ? (month - 1) : month)
								.intValue());
				startDate.set(Calendar.DAY_OF_MONTH, 10);

				startDate.set(Integer.valueOf(dateFormaterYear.format(startDate
						.getTime())), Integer.valueOf(dateFormaterMonth
						.format(startDate.getTime())) - 1, startDate
						.getMinimum(Calendar.DAY_OF_MONTH), startDate
						.getMinimum(Calendar.HOUR_OF_DAY), startDate
						.getMinimum(Calendar.MINUTE), startDate
						.getMinimum(Calendar.SECOND));
				startDate.set(Calendar.MILLISECOND, startDate
						.getMinimum(Calendar.MILLISECOND));
				from = startDate.getTimeInMillis();

				endDate.set(Integer.valueOf(dateFormaterYear.format(startDate
						.getTime())), Integer.valueOf(dateFormaterMonth
						.format(startDate.getTime())) - 1, startDate
						.getActualMaximum(Calendar.DAY_OF_MONTH), startDate
						.getActualMaximum(Calendar.HOUR_OF_DAY), startDate
						.getActualMaximum(Calendar.MINUTE), startDate
						.getActualMaximum(Calendar.SECOND));

				to = endDate.getTimeInMillis();

				queryClaimsAnsComplianceListForProcessWithFilter
						.addCriteria(Criteria
								.where(ApplicationConstants.FIELD_DATE)
								.gte(from)
								.andOperator(
										Criteria.where(
												ApplicationConstants.FIELD_DATE)
												.lt(to)));
			}

			queryClaimsAnsComplianceListForProcessWithFilter.with(new Sort(
					Direction.ASC, "date"));

			results = mongoOps.find(
					queryClaimsAnsComplianceListForProcessWithFilter,
					ClaimsAnsCompliance.class);

		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: getClaimsAnsComplianceListForProcessWithFilter.");

			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getClaimAnsComplianceListWithFilter END");
		return results;
	}

	/**
	 * SETTER for MongoOperations template.
	 * 
	 * @param mongoOps
	 *            the mongo template.
	 */
	@Override
	public void setMongoOps(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
}
