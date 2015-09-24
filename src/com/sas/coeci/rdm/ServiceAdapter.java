package com.sas.coeci.rdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sas.tap.client.SASDSRequest;
import com.sas.tap.client.SASDSRequestFactory;
import com.sas.tap.client.SASDSResponse;

@Path("/rdm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceAdapter {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceResponse invokeRdm(ServiceRequest req) throws Exception {
		ServiceResponse responseContainer = new ServiceResponse();
		responseContainer.setRequestStart(System.currentTimeMillis());
		responseContainer.setStatusCd(200);
		List<ServiceParameter> resultSet = new ArrayList<ServiceParameter>();

		try {
			responseContainer.setIdentifier(req.getConfig().getIdentifier());
			responseContainer.setTimezone(req.getConfig().getTimezone());

			String rdmConnectionURL = "http://" + req.getConfig().getRdmHost() + ":" + req.getConfig().getRdmPort() + "/RTDM/Custom";
			System.out.println("Send Request to host: " + rdmConnectionURL);
			SASDSRequestFactory factory = SASDSRequestFactory.getInstance(rdmConnectionURL, new Properties());
			SASDSRequest request = factory.create(req.getConfig().getEventName(), req.getConfig().getIdentifier(), req.getConfig().getTimezone());

			// fill input variables
			for (ServiceParameter inPar : req.getInParameter()) {

				try {

					if (inPar.getName().isEmpty() || inPar.getType().isEmpty())
						continue;

					if (inPar.getType().equalsIgnoreCase("string")) {
						request.setString(inPar.getName(), inPar.getValue());
					} else if (inPar.getType().equalsIgnoreCase("long") || inPar.getType().equalsIgnoreCase("integer")) {
						request.setLong(inPar.getName(), Long.parseLong(inPar.getValue()));
					} else if (inPar.getType().equalsIgnoreCase("double")) {
						request.setDouble(inPar.getName(), Double.parseDouble(inPar.getValue()));
					} else if (inPar.getType().equalsIgnoreCase("boolean")) {
						request.setBoolean(inPar.getName(), Boolean.parseBoolean(inPar.getValue()));
					} else if (inPar.getType().equalsIgnoreCase("string-list") || inPar.getType().equalsIgnoreCase("stringlist")) {
						request.setStringArray(inPar.getName(), inPar.getStringArray());
					} else if (inPar.getType().equalsIgnoreCase("long-list") || inPar.getType().equalsIgnoreCase("integer-list")
							|| inPar.getType().equalsIgnoreCase("longlist") || inPar.getType().equalsIgnoreCase("integerlist")) {
						request.setLongArray(inPar.getName(), inPar.getLongArray());
					} else if (inPar.getType().equalsIgnoreCase("double-list") || inPar.getType().equalsIgnoreCase("stringlist")) {
						request.setDoubleArray(inPar.getName(), inPar.getDoubleArray());
					} else if (inPar.getType().equalsIgnoreCase("boolean-list") || inPar.getType().equalsIgnoreCase("stringlist")) {
						request.setBooleanArray(inPar.getName(), inPar.getBooleanArray());
					} else {
						throw new IllegalArgumentException("Unsupported datatype " + inPar.getType() + " for input variable " + inPar.getName());
					}
				} catch (NumberFormatException e) {
					responseContainer.getStatusMsg().add("Error while processing input variable: " + inPar.getName() + " Details: " + e.getMessage());
				} catch (IllegalArgumentException e) {
					responseContainer.getStatusMsg().add("Error while processing input variable: " + inPar.getName() + " Details: " + e.getMessage());
				} catch (Exception e) {
					responseContainer.getStatusMsg().add("Error while processing input variable: " + inPar.getName() + " Details: " + e.toString());
				}

			}

			// execute request
			SASDSResponse response = request.execute();

			for (ServiceParameter outPar : req.getOutParameter()) {

				if (outPar.getName() == null || outPar.getType() == null)
					continue;

				if (outPar.getName().isEmpty() || outPar.getType().isEmpty())
					continue;

				String parValue = "";
				try {
					if (outPar.getType().equalsIgnoreCase("string")) {
						parValue = response.getString(outPar.getName());
					} else if (outPar.getType().equalsIgnoreCase("long") || outPar.getType().equalsIgnoreCase("integer")) {
						Long tmpVar = response.getLong(outPar.getName());
						if (tmpVar != null)
							parValue = tmpVar.toString();
						else
							parValue = "";
					} else if (outPar.getType().equalsIgnoreCase("double")) {
						Double tmpVar = response.getDouble(outPar.getName());
						if (tmpVar != null)
							parValue = tmpVar.toString();
						else
							parValue = "";
					} else if (outPar.getType().equalsIgnoreCase("boolean")) {
						Boolean tmpVar = response.getBoolean(outPar.getName());
						if (tmpVar != null)
							parValue = tmpVar.toString();
						else
							parValue = "";

					} else if (outPar.getType().equalsIgnoreCase("string-list") || outPar.getType().equalsIgnoreCase("stringlist")) {
						String[] tmpVar = response.getStringArray(outPar.getName());
						if (tmpVar != null)
							outPar.setValueList(tmpVar);

					} else if (outPar.getType().equalsIgnoreCase("long-list") || outPar.getType().equalsIgnoreCase("integer-list")
							|| outPar.getType().equalsIgnoreCase("longlist") || outPar.getType().equalsIgnoreCase("integerlist")) {
						Long[] tmpVar = response.getLongArray(outPar.getName());
						if (tmpVar != null)
							outPar.setValueList(tmpVar);

					} else if (outPar.getType().equalsIgnoreCase("double-list") || outPar.getType().equalsIgnoreCase("stringlist")) {
						Double[] tmpVar = response.getDoubleArray(outPar.getName());
						if (tmpVar != null)
							outPar.setValueList(tmpVar);
					} else if (outPar.getType().equalsIgnoreCase("boolean-list") || outPar.getType().equalsIgnoreCase("stringlist")) {
						Boolean[] tmpVar = response.getBooleanArray(outPar.getName());
						if (tmpVar != null)
							outPar.setValueList(tmpVar);
					} else {
						throw new IllegalArgumentException("Unsupported datatype for output variable " + outPar.getName());
					}
				} catch (Exception e) {
					responseContainer.getStatusMsg().add("Error while processing output variable: " + outPar.getName() + " Details: " + e.toString());
				}

				outPar.setValue(parValue);
				resultSet.add(outPar);
			}

			responseContainer.setParameters(resultSet);

			responseContainer.setRequestEnd(System.currentTimeMillis());

		} catch (Exception e) {
			responseContainer.setStatusCd(503);
			String exception = e.toString();
			if (e.getCause() != null) {
				exception = e.getCause().toString();
			}
			responseContainer.getStatusMsg().add("Runtime Error " + exception);
			e.printStackTrace();
			responseContainer.setRequestEnd(System.currentTimeMillis());
		}

		return responseContainer;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request")
	public ServiceRequest getRequestDefinition() {
		ServiceRequest req = new ServiceRequest();
		ServiceConfiguration config = new ServiceConfiguration();
		config.setEventName("Event Example");
		config.setIdentifier("SAS_IDENT");
		config.setRdmHost("sasbap.demo.sas.com");
		config.setRdmPort(8080);
		config.setTimezone("GMT");
		req.setConfig(config);

		List<ServiceParameter> inParameter = new ArrayList<ServiceParameter>();
		inParameter.add(new ServiceParameter("CustomerID", "Long", "985999"));
		inParameter.add(new ServiceParameter("CustomerSegment", "String", "GOLD_SEGMENT"));
		inParameter.add(new ServiceParameter("WriteContactHistory", "Boolean", "true"));

		List<String> productList = new ArrayList<String>();
		productList.add("Shampoo");
		productList.add("Nivea");
		productList.add("AXE");
		productList.add("Hugo Boss");
		productList.add("Loreal");
		inParameter.add(new ServiceParameter("ProductList", "StringList", productList));

		List<ServiceParameter> outParameter = new ArrayList<ServiceParameter>();
		outParameter.add(new ServiceParameter("TreatmentID", "String"));
		outParameter.add(new ServiceParameter("TreatmentName", "String"));
		outParameter.add(new ServiceParameter("WriteContactHistory", "Boolean"));

		req.setInParameter(inParameter);
		req.setOutParameter(outParameter);
		return req;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/response")
	public ServiceRequest getResponseDefinition() {
		return new ServiceRequest();
	}

}
