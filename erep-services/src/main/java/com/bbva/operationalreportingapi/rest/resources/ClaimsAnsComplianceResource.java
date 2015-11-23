package com.bbva.operationalreportingapi.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bbva.operationalreportingapi.rest.beans.collections.ClaimsAnsCompliance;
import com.bbva.operationalreportingapi.rest.errorhandling.ApiException;
import com.bbva.operationalreportingapi.rest.errorhandling.InformationMessage;
import com.bbva.operationalreportingapi.rest.helpers.ApplicationConstants;
import com.bbva.operationalreportingapi.rest.services.AnsComplianceClaimsService;

/**
 * END-POINT class for the ANS compliance for finished instances grouped by
 * operations report web service resource.
 * 
 * @author BBVA-ReportingOperacional
 */
@Component
@Path("/")
public class ClaimsAnsComplianceResource {

	@Autowired
	private AnsComplianceClaimsService ansComplianceClaimsService;

	/**
	 * END POINT URL TO GENERATE EXAMPLE DATA FOR ANS COMPLIANCE FOR CLAIMS.
	 */
	private static final String URL_ANS_COMPL_CLAIMS_GEN_DATA = "reports/"
			+ "ansCompliance/claims/generateData/{initialYear}/{initialMonth}/{initialDay}/{endYear}/{endMonth}/{endDay}";
	/**
	 * END POINT URL FOR LISTING ANS COMPLIANCE FOR CLAIMS.
	 */
	private static final String URL_ANS_COMPL_CLAIMS_LIST = "reports/"
			+ "ansCompliance/claims/list";
	/**
	 * END POINT URL FOR ANS COMPLIANCE FOR CLAIMS IN A MONTH.
	 */
	private static final String URL_ANS_COMPL_CLAIMS_FOR_A_MONTH = "reports/"
			+ "ansCompliance/claims/list/{year}/{month}";

	/**
	 * Method that create example data 'Claims ANS compliance' report.
	 * 
	 * @verb POST
	 * @produces {@link MediaType.APPLICATION_JSON}
	 */
	@POST
	@Path(URL_ANS_COMPL_CLAIMS_GEN_DATA)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createAndPersistsMockDataClaimsAnsComplianceExampleData(
			@PathParam("initialYear") Long initialYear,
			@PathParam("initialMonth") Long initialMonth,
			@PathParam("initialDay") Long initialDay,
			@PathParam("endYear") Long endYear,
			@PathParam("endMonth") Long endMonth,
			@PathParam("endDay") Long endDay) throws IOException, ApiException {

		try {
			ansComplianceClaimsService
					.createAndPersistsMockDataClaimsAnsComplianceExampleData(
							initialYear, initialMonth, initialDay, endYear,
							endMonth, endDay);
		} catch (Exception e) {
			throw e;
		}
		InformationMessage messageEntity = new InformationMessage(
				Response.Status.OK.getStatusCode(),
				Response.Status.CREATED.getStatusCode(),
				ApplicationConstants.GENERAL_ERROR_API_MESSAGE
						+ Response.Status.CREATED.getReasonPhrase());
		return Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON).entity(messageEntity).build();
	}

	/**
	 * Method (Resources) to query the 'Claims ANS compliance' without filters
	 * neither order fields specified.
	 * 
	 * @return Response of resource
	 * @throws IOException
	 *             a kind of exception
	 * @throws ApiException
	 *             a kind of exception
	 */
	@GET
	@Path(URL_ANS_COMPL_CLAIMS_LIST)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClaimAnsComplianceListWithoutFiltersPathParam()
			throws IOException, ApiException {
		List<ClaimsAnsCompliance> listResults = null;

		try {

			listResults = ansComplianceClaimsService
					.getClaimAnsComplianceList();
		} catch (Exception e) {
			throw e;
		}
		return Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON).entity(listResults).build();
	}

	/**
	 * Method (Resources) to query the 'Claims ANS compliance' without filters
	 * neither order fields specified.
	 * 
	 * @return Response of resource
	 * @throws IOException
	 *             a kind of exception
	 * @throws ApiException
	 *             a kind of exception
	 */
	@GET
	@Path(URL_ANS_COMPL_CLAIMS_FOR_A_MONTH)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClaimAnsComplianceListWithFiltersPathParam(
			@PathParam("year") Long year, @PathParam("month") Long month)
			throws IOException, ApiException {
		List<ClaimsAnsCompliance> listResults = null;

		try {

			listResults = ansComplianceClaimsService
					.getClaimAnsComplianceListWithFilter(year, month);
		} catch (Exception e) {
			throw e;
		}
		return Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON).entity(listResults).build();
	}

	/**
	 * SETTER for 'ANS compliance for finished instances grouped by operations'
	 * service layer, inyected by Spring Context.
	 * 
	 * @param operationsByProcessService
	 *            the hierarchy services.
	 */
	public void setAnsComplianceClaimsService(
			AnsComplianceClaimsService ansComplianceClaimsService) {
		this.ansComplianceClaimsService = ansComplianceClaimsService;
	}
}
