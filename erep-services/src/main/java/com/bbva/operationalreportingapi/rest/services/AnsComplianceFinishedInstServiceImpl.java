package com.bbva.operationalreportingapi.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.bbva.operationalreportingapi.rest.beans.collections.DailyProcessAnsCompliance;
import com.bbva.operationalreportingapi.rest.beans.collections.OperationByProcess;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Service class to manage the hierarchy components.
 * 
 * @author BBVA-ReportingOperacional
 * @param <T>
 *            Java generics type of report
 * @implements {@link InformeEjemploService}
 */
@Component
public class AnsComplianceFinishedInstServiceImpl implements
		AnsComplianceFinishedInstService {

	@Autowired
	private MongoOperations mongoOps;

	private static final Logger log = LoggerFactory
			.getLogger(AnsComplianceFinishedInstServiceImpl.class);

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

		PROCESSES_MOCK.put(FLUJO_6_ESTADOS_TD_P, new HashSet<String>(
				OPERTYPES_MOCK));

		// Operations type for process FLUJO 4 ESTADOS TdP
		OPERTYPES_MOCK.clear();
		OPERTYPES_MOCK.add(ALTCED);

		PROCESSES_MOCK.put(FLUJO_4_ESTADOS_TD_P, new HashSet<String>(
				OPERTYPES_MOCK));

		// Operations type for process FLUJO 8 ESTADOS TdP
		OPERTYPES_MOCK.clear();
		OPERTYPES_MOCK.add("PU4");

		PROCESSES_MOCK.put(FLUJO_8_ESTADOS_TD_P, new HashSet<String>(
				OPERTYPES_MOCK));

		SUBFAMILIES_MOCK.put(PRUEBAS_SUITE_BPM,
				new HashMap<String, Set<String>>(PROCESSES_MOCK));

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

		FAMILIES_MOCK
				.put(FORMULARIO_PRUEBAS,
						new HashMap<String, Map<String, Set<String>>>(
								SUBFAMILIES_MOCK));

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

		PROCESSES_MOCK.put(FLUJO_4_ESTADOS_TD_P, new HashSet<String>(
				OPERTYPES_MOCK));

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

		FAMILIES_MOCK
				.put(FORMULARIO_TALLER_DE_PROCESOS,
						new HashMap<String, Map<String, Set<String>>>(
								SUBFAMILIES_MOCK));
	}

	/**
	 * Method that create example data about {@link DailyProcessAnsCompliance} .
	 * 
	 * @return {@link DailyProcessAnsCompliance}
	 * @throws ApiException
	 *             the kind of exception.
	 */
	@Override
	public DailyProcessAnsCompliance createDailyProcessAnsComplianceExampleData()
			throws ApiException {

		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: createRegistrationsInOrdersAggrByDay START");
		List<DailyProcessAnsCompliance> listRegs = new ArrayList<DailyProcessAnsCompliance>();
		DailyProcessAnsCompliance dailyProcessAnsCompliance = null;

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
				dailyProcessAnsCompliance = new DailyProcessAnsCompliance();
				String formattedDate = dateFormater.format(startDate);
				Iterator<Entry<String, Map<String, Map<String, Set<String>>>>> iteratorFamilies;
				iteratorFamilies = FAMILIES_MOCK.entrySet().iterator();

				while (iteratorFamilies.hasNext()) {
					Map.Entry<String, Map<String, Map<String, Set<String>>>> familyItem = iteratorFamilies
							.next();
					// set the family name mock
					dailyProcessAnsCompliance.setFamily(familyItem.getKey());

					Iterator<Entry<String, Map<String, Set<String>>>> iteratorSubFamilies = familyItem
							.getValue().entrySet().iterator();

					while (iteratorSubFamilies.hasNext()) {
						Map.Entry<String, Map<String, Set<String>>> subFamilyItem = iteratorSubFamilies
								.next();
						// set the subfamily name mock
						dailyProcessAnsCompliance.setSubfamily(subFamilyItem
								.getKey());

						Iterator<Entry<String, Set<String>>> iteratorProcesses = subFamilyItem
								.getValue().entrySet().iterator();

						while (iteratorProcesses.hasNext()) {
							Map.Entry<String, Set<String>> processItem = iteratorProcesses
									.next();

							// set the process name mock
							dailyProcessAnsCompliance.setProcess(processItem
									.getKey());

							Set<OperationByProcess> operationsSet = new HashSet<OperationByProcess>();
							for (String operationType : processItem.getValue()) {

								Random rn = new Random();
								int range = 100 - 10 + 1;
								int rangeHours = 10;

								int ansokNum = rn.nextInt(range) + 10;
								int anskoNum = rn.nextInt(range) + 10;
								int answarningNum = rn.nextInt(range) + 10;
								Long ansavgtimeNum = (long) ((rn.nextFloat() * (rn
										.nextInt(rangeHours))) * 3600000);

								Long ansNum = (long) ((ansavgtimeNum >= 3) ? (ansavgtimeNum - (rn
										.nextFloat() * 360000))
										: (ansavgtimeNum + (rn.nextFloat() * 360000)));

								int instancecountNum = anskoNum + ansokNum
										+ answarningNum;

								// Generate de Operations By Process data
								OperationByProcess operationByProcess = new OperationByProcess();
								operationByProcess.setAns(Long.parseLong(String
										.valueOf(ansNum)));
								operationByProcess.setAnsavgtime(Long
										.parseLong(String
												.valueOf(ansavgtimeNum)));
								operationByProcess.setAnsko(anskoNum);
								operationByProcess.setAnsok(ansokNum);
								operationByProcess.setAnswarning(answarningNum);
								operationByProcess
										.setInstancecount(instancecountNum);
								operationByProcess
										.setOperationtype(operationType);
								operationsSet.add(operationByProcess);

							}
							dailyProcessAnsCompliance
									.setOperations(operationsSet);

							// TODO poner la fecha bien
							// :dailyProcessAnsCompliance.setDate(formattedDate);
							dailyProcessAnsCompliance.setDate(startDate
									.getTime());
							listRegs.add((DailyProcessAnsCompliance) BeanUtils
									.cloneBean(dailyProcessAnsCompliance));
						}
					}

					mongoOps.insert(listRegs, DailyProcessAnsCompliance.class);
					listRegs.clear();
				}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = startCal.getTime();
			}

			dailyProcessAnsCompliance = null;
			String filterSearch = "F";

			BasicQuery query = new BasicQuery("{\"process\": {$regex : '"
					+ filterSearch + "'} }");
			query.limit(1);

			dailyProcessAnsCompliance = mongoOps.find(query,
					DailyProcessAnsCompliance.class).get(0);
		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: createDailyProcessAnsComplianceExampleData.");
			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: createDailyProcessAnsComplianceExampleData END");
		return dailyProcessAnsCompliance;
	}

	/**
	 * Method that returns all documents in DB about
	 * {@link DailyProcessAnsCompliance}.
	 * 
	 * @return {@link DailyProcessAnsCompliance} (List)
	 * @throws ApiException
	 *             the exception.
	 */
	@Override
	public List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceList()
			throws ApiException {
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getDailyProcessAnsComplianceList START");

		try {
			log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
					+ " METHOD: getDailyProcessAnsComplianceList END");
			return mongoOps.findAll(DailyProcessAnsCompliance.class);
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
	 * @param family
	 *            the family parameter.
	 * @param subfamily
	 *            the subfamily parameter.
	 * @param process
	 *            the process parameter.
	 * @param date
	 *            the date to filter results parameter.
	 * @return {@link Response}.
	 * @throws ApiException
	 *             The kind of exception.
	 */
	@Override
	public List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceListForProcessWithFilter(
			String family, String subfamily, String process, Long from, Long to)
			throws ApiException {
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getDailyProcessAnsComplianceListForProcessWithFilter START");
		List<DailyProcessAnsCompliance> results;

		try {
			Query queryDailyProcessAnsComplianceListForProcessWithFilter = new Query();
			queryDailyProcessAnsComplianceListForProcessWithFilter
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_FAMILY)
									.is(family))
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_SUBFAMILY)
									.is(subfamily))
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_PROCESS)
									.is(process));

			if (from != null && to != null && !from.equals(to)) {
				queryDailyProcessAnsComplianceListForProcessWithFilter
						.addCriteria(Criteria
								.where(ApplicationConstants.FIELD_DATE)
								.gte(from
										+ ApplicationConstants.OFFSET_MILISECONDS)
								.andOperator(
										Criteria.where(
												ApplicationConstants.FIELD_DATE)
												.lte(to
														+ ApplicationConstants.OFFSET_MILISECONDS)));
			} else if (from != null) {
				queryDailyProcessAnsComplianceListForProcessWithFilter
						.addCriteria(Criteria
								.where(ApplicationConstants.FIELD_DATE)
								.gte(from
										+ ApplicationConstants.OFFSET_MILISECONDS)
								.andOperator(
										Criteria.where(
												ApplicationConstants.FIELD_DATE)
												.lt(to
														+ ApplicationConstants.DAY_MILISECONDS)));
			}

			// Sorting rules
			queryDailyProcessAnsComplianceListForProcessWithFilter
					.with(new Sort(Sort.Direction.ASC,
							ApplicationConstants.FIELD_DATE));

			results = mongoOps.find(
					queryDailyProcessAnsComplianceListForProcessWithFilter,
					DailyProcessAnsCompliance.class);

		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: getDailyProcessAnsComplianceListForProcessWithFilter.");

			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getDailyProcessAnsComplianceListForProcessWithFilter END");
		return results;
	}

	/**
	 * Method that returns the 'ANS compliance for finished instances grouped by
	 * operations types.' list for an operation type (family, subfamily,
	 * process)
	 * 
	 * @param family
	 *            the family parameter.
	 * @param subfamily
	 *            the subfamily parameter.
	 * @param process
	 *            the process parameter.
	 * @param operationtype
	 *            the operation type to filter results parameter.
	 * @return {@link Response}.
	 * @throws ApiException
	 *             The kind of exception.
	 */
	@Override
	public List<DailyProcessAnsCompliance> getDailyProcessAnsComplianceListForOperationWithFilters(
			String family, String subfamily, String process,
			String operationtype, Long from, Long to) throws ApiException {
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getDailyProcessAnsComplianceForOperation START");
		List<DailyProcessAnsCompliance> results = new ArrayList<DailyProcessAnsCompliance>();
		// TODO Clean all this
		try {
			Query queryDailyProcessAnsComplianceForOperation = new Query();
			queryDailyProcessAnsComplianceForOperation
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_FAMILY)
									.is(family))
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_SUBFAMILY)
									.is(subfamily))
					.addCriteria(
							Criteria.where(ApplicationConstants.FIELD_PROCESS)
									.is(process))
					.addCriteria(
							Criteria.where(
									ApplicationConstants.FIELD_OPERATIONS)
									.elemMatch(
											new Criteria(
													ApplicationConstants.FIELD_OPERATIONTYPENODE)
													.is(operationtype)));

			if (from != null && to != null && !from.equals(to)) {
				queryDailyProcessAnsComplianceForOperation
						.addCriteria(Criteria
								.where(ApplicationConstants.FIELD_DATE)
								.gte(from
										+ +ApplicationConstants.OFFSET_MILISECONDS)
								.andOperator(
										Criteria.where(
												ApplicationConstants.FIELD_DATE)
												.lte(to
														+ ApplicationConstants.OFFSET_MILISECONDS)));
			} else if (from != null) {
				queryDailyProcessAnsComplianceForOperation.addCriteria(Criteria
						.where(ApplicationConstants.FIELD_DATE).is(from));
			}

			// Sorting rules
			queryDailyProcessAnsComplianceForOperation.with(new Sort(
					Sort.Direction.ASC, ApplicationConstants.FIELD_DATE));

			/** I+D **/
			/*
			 * Criteria principalFieldsCriteria = Criteria
			 * .where(ApplicationConstants.FIELD_FAMILY).is(family)
			 * .and(ApplicationConstants.FIELD_SUBFAMILY).is(subfamily)
			 * .and(ApplicationConstants.FIELD_PROCESS).is(process); if (from !=
			 * null && to != null && !from.equals(to)) { principalFieldsCriteria
			 * .and(ApplicationConstants.FIELD_DATE) .gte(from) .andOperator(
			 * Criteria.where(ApplicationConstants.FIELD_DATE).lte(to)); } else
			 * if (from != null) {
			 * principalFieldsCriteria.and(ApplicationConstants
			 * .FIELD_DATE).is(from); }
			 * 
			 * Criteria secondaryFieldsCriteria = Criteria.where(
			 * ApplicationConstants.FIELD_OPERATIONTYPE).is(operationtype);
			 * 
			 * AggregationOperation matchPrincipalFields = Aggregation
			 * .match(principalFieldsCriteria);
			 * 
			 * AggregationOperation matchUnwindOps = Aggregation
			 * .unwind(ApplicationConstants.FIELD_OPERATIONS);
			 * 
			 * AggregationOperation matchSecondaryFields = Aggregation
			 * .match(secondaryFieldsCriteria);
			 * 
			 * List<AggregationOperation> listExample = new
			 * ArrayList<AggregationOperation>();
			 * listExample.add(matchPrincipalFields);
			 * listExample.add(matchUnwindOps);
			 * listExample.add(matchSecondaryFields);
			 * 
			 * Aggregation aggregation =
			 * Aggregation.newAggregation(listExample);
			 * AggregationResults<DailyProcessAnsCompliance> resultsa = mongoOps
			 * .aggregate(aggregation, DailyProcessAnsCompliance.class,
			 * DailyProcessAnsCompliance.class); //### FUNCIONA PERO MAPEA MAL
			 */

			DBCollection coll = mongoOps
					.getCollection("C_EREP_DAILY_PROCESS_ANS_COMPLIANCE");
			// create our pipeline operations, first with the $match
			final Map<String, String[]> dataGroupInOps = new HashMap<String, String[]>();
			final Map<String, Object> dataFilter = new HashMap<String, Object>();
			dataFilter.put(ApplicationConstants.FIELD_FAMILY, family);
			dataFilter.put(ApplicationConstants.FIELD_SUBFAMILY, subfamily);
			dataFilter.put(ApplicationConstants.FIELD_PROCESS, process);

			String[] listOpsToLookFor = new String[1];
			listOpsToLookFor[0] = operationtype;
			dataGroupInOps.put("$in", listOpsToLookFor);

			dataFilter.put(ApplicationConstants.FIELD_OPERATIONTYPE,
					dataGroupInOps);

			final Map<String, Object> dataGroup = new HashMap<String, Object>();
			final Map<String, Object> dataGroupId = new HashMap<String, Object>();
			final Map<String, Object> dataGroupOps = new HashMap<String, Object>();
			final Map<String, Object> dataOrder = new HashMap<String, Object>();

			final Map<String, Object> dateFilter = new HashMap<String, Object>();
			if (from != null && to != null && !from.equals(to)) {
				dateFilter.put("$gte", from
						+ ApplicationConstants.OFFSET_MILISECONDS);
				dateFilter.put("$lte", to
						+ ApplicationConstants.OFFSET_MILISECONDS);
				dataFilter.put(ApplicationConstants.FIELD_DATE, dateFilter);
			} else if (from != null && to != null) {
				dateFilter.put("$gte", from
						+ ApplicationConstants.OFFSET_MILISECONDS);
				dateFilter.put("$lt",
						(to + ApplicationConstants.DAY_MILISECONDS));
				dataFilter.put(ApplicationConstants.FIELD_DATE, dateFilter);
			} else {
				log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
						+ " METHOD: getDailyProcessAnsComplianceForOperation.");

				throw new ApiException(
						Response.Status.BAD_REQUEST.getStatusCode(),
						Response.Status.BAD_REQUEST.getStatusCode(),
						ApplicationConstants.GENERAL_ERROR_API_MESSAGE
								+ ApplicationConstants.BAD_REQUEST_API_MESSAGE,
						"Incorrect params", null);
			}

			dataGroupId.put(ApplicationConstants.FIELD_ID_MONGO, "$"
					+ ApplicationConstants.FIELD_ID_MONGO);
			dataGroupId.put(ApplicationConstants.FIELD_FAMILY, "$"
					+ ApplicationConstants.FIELD_FAMILY);
			dataGroupId.put(ApplicationConstants.FIELD_SUBFAMILY, "$"
					+ ApplicationConstants.FIELD_SUBFAMILY);
			dataGroupId.put(ApplicationConstants.FIELD_PROCESS, "$"
					+ ApplicationConstants.FIELD_PROCESS);
			dataGroupId.put(ApplicationConstants.FIELD_DATE, "$"
					+ ApplicationConstants.FIELD_DATE);

			dataGroupOps.put("$push", "$"
					+ ApplicationConstants.FIELD_OPERATIONS);

			dataGroup.put(ApplicationConstants.FIELD_ID_MONGO, dataGroupId);

			dataGroup.put(ApplicationConstants.FIELD_OPERATIONS, dataGroupOps);

			dataOrder.put(ApplicationConstants.FIELD_ID_MONGO + "."
					+ ApplicationConstants.FIELD_DATE,
					ApplicationConstants.ORDER_ASC);

			// Now the $group operation
			DBObject unWindFields = new BasicDBObject("$unwind", "$operations");
			// run aggregation

			DBObject matchFields = new BasicDBObject("$match",
					new BasicDBObject(dataFilter));

			DBObject groupFields = new BasicDBObject("$group",
					new BasicDBObject(dataGroup));

			DBObject orderFields = new BasicDBObject("$sort",
					new BasicDBObject(dataOrder));
			// { $sort : { family:1, subfamily:1} }

			List<DBObject> pipeline = Arrays.asList(unWindFields, matchFields,
					groupFields, orderFields);

			AggregationOutput output = coll.aggregate(pipeline);

			for (DBObject resultsBson : output.results()) {

				DailyProcessAnsCompliance auxBeanCollector = new DailyProcessAnsCompliance();

				BasicDBObject fieldsDbObject = (BasicDBObject) resultsBson
						.get(ApplicationConstants.FIELD_ID_MONGO);

				auxBeanCollector.setId(fieldsDbObject.get(
						ApplicationConstants.FIELD_ID_MONGO).toString());
				auxBeanCollector.setFamily(fieldsDbObject.get(
						ApplicationConstants.FIELD_FAMILY).toString());
				auxBeanCollector.setSubfamily(fieldsDbObject.get(
						ApplicationConstants.FIELD_SUBFAMILY).toString());
				auxBeanCollector.setProcess(fieldsDbObject.get(
						ApplicationConstants.FIELD_PROCESS).toString());
				auxBeanCollector.setDate(Long.parseLong(fieldsDbObject.get(
						ApplicationConstants.FIELD_DATE).toString()));

				BasicDBList operationsAuxList = (BasicDBList) resultsBson
						.get(ApplicationConstants.FIELD_OPERATIONS);

				for (int indexOperationType = 0; indexOperationType < operationsAuxList
						.size(); indexOperationType++) {
					BasicDBObject fieldsOperationType = (BasicDBObject) operationsAuxList
							.get(indexOperationType);

					OperationByProcess operationAux = new OperationByProcess();

					operationAux.setAns(Long.parseLong(fieldsOperationType.get(
							ApplicationConstants.FIELD_ANSNODE).toString()));
					operationAux.setAnsko(Integer.parseInt(fieldsOperationType
							.get(ApplicationConstants.FIELD_ANSKONODE)
							.toString()));
					operationAux.setAnsok(Integer.parseInt(fieldsOperationType
							.get(ApplicationConstants.FIELD_ANSOKNODE)
							.toString()));
					operationAux.setAnswarning(Integer
							.parseInt(fieldsOperationType.get(
									ApplicationConstants.FIELD_ANSWARNINGNODE)
									.toString()));
					operationAux
							.setInstancecount(Integer
									.parseInt(fieldsOperationType
											.get(ApplicationConstants.FIELD_INSTANCECOUNTNODE)
											.toString()));
					operationAux.setAnsavgtime(Long
							.parseLong(fieldsOperationType.get(
									ApplicationConstants.FIELD_ANSAVGTIMENODE)
									.toString()));
					operationAux.setOperationtype(fieldsOperationType.get(
							ApplicationConstants.FIELD_OPERATIONTYPENODE)
							.toString());

					auxBeanCollector.getOperations().add(operationAux);
				}
				results.add(auxBeanCollector);
			}

			/*
			 * results =
			 * mongoOps.find(queryDailyProcessAnsComplianceForOperation,
			 * DailyProcessAnsCompliance.class);
			 */

		} catch (Exception e) {
			log.error(ApplicationConstants.GENERAL_ERROR_API_MESSAGE
					+ " METHOD: getDailyProcessAnsComplianceForOperation.");

			throw new ApiException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					ApplicationConstants.GENERAL_ERROR_API_MESSAGE
							+ Response.Status.INTERNAL_SERVER_ERROR,
					e.getMessage(), null, e);
		}
		log.info(ApplicationConstants.GENERAL_HEADER_LOG_MESSAGE
				+ " METHOD: getDailyProcessAnsComplianceForOperation END");
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
