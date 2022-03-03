package com.mycompany.rest_api_v2;

import tables.BloodTestDatabaseOperations;
import mainClasses.BloodTest;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PUT;
import org.json.JSONObject;

@Path("generic")
public class healthAPI {

    @Context
    private UriInfo context;

    public healthAPI() {
    }

    public static boolean checkIfNoMeasure(BloodTest bloodtest) {
        return bloodtest.getVitamin_d3_level().equals("")
                && bloodtest.getBlood_sugar_level().equals("")
                && bloodtest.getVitamin_b12_level().equals("")
                && bloodtest.getCholesterol_level().equals("")
                && bloodtest.getIron_level().equals("");
    }

    public static boolean checkForOutOfRangeValues(BloodTest bloodtest) {
        return bloodtest.getVitamin_d3_level().equals("Invalid")
                || bloodtest.getBlood_sugar_level().equals("Invalid")
                || bloodtest.getVitamin_b12_level().equals("Invalid")
                || bloodtest.getCholesterol_level().equals("Invalid")
                || bloodtest.getIron_level().equals("Invalid");
    }

    @POST
    @Path("/newBloodTest")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addBloodTest(String bloodTest) {
        Gson gson = new Gson();
        BloodTest bloodtest = gson.fromJson(bloodTest, BloodTest.class);
        bloodtest.setValues();


        if (bloodtest.getMedical_center().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Bad client request!\"}").build();
        } else if (bloodtest.getTest_date().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Bad client request\"}").build();
        } else if (bloodtest.getAmka().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Bad client request\"}").build();
        } else if (checkIfNoMeasure(bloodtest)) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"No measure given.\"}").build();
        } else if (checkForOutOfRangeValues(bloodtest)) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Out of range test value found\"}").build();
        }
        try {
            (new BloodTestDatabaseOperations()).createNewBloodTest(bloodtest);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Response.status(Response.Status.OK).type("application/json").entity("{\"success\":\"The input blood test has been registered.\"}").build();
    }

    public static boolean checkForInvalidDates(String d1, String d2) {

        try {
            SimpleDateFormat formatDates = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatDates.parse(d1);
            Date date2 = formatDates.parse(d2);
            if (date2.before(date1)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
    }

    @GET
    @Path("/BloodTests/{amka}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBloodTests(@PathParam("amka") String amka,
            @QueryParam("fromDate") String fromDate,
            @QueryParam("toDate") String toDate) {

        if ((new BloodTestDatabaseOperations()).checkAmka(amka) == false) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid input amka.\"}").build();
        }
        String q = "";
        try {
            if (toDate == null && fromDate != null) {
                q = "SELECT * FROM bloodtest WHERE amka='" + amka + "' AND test_date >= '" + fromDate + "'";
            } else if (toDate != null && fromDate == null) {
                q = "SELECT * FROM bloodtest WHERE amka='" + amka + "' AND test_date <= '" + toDate + "'";
            } else if (fromDate != null && toDate != null) {
                if (checkForInvalidDates(fromDate, toDate)) {
                    return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid dates given.\"}").build();
                }
                q = "SELECT * FROM bloodtest WHERE amka='" + amka + "' AND test_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
            }
            ArrayList<BloodTest> list = (new BloodTestDatabaseOperations()).databaseToBloodTestList(amka, q);

            if (list == null) {
                return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"No tests found.\"}").build();
            } else if (list.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"No tests found.\"}").build();
            }

            String result = "";
            int i = 0;
            for (result += "["; i < list.size(); ++i) {
                result = result + new Gson().toJson(list.get(i)) + ",";
            }
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1) + "]";
            }
            return Response.status(Response.Status.OK).type("application/json").entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid input dates.\"}").build();
        }
    }

    @GET
    @Path("/BloodTestMeasure/{amka}/{Measure}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBloodTestMeasure(@PathParam("amka") String amka,
            @PathParam("Measure") String measure) {

        String[] arr = {"blood_sugar", "cholesterol", "iron", "vitamin_d3", "vitamin_b12"};
        if (!(new BloodTestDatabaseOperations()).checkAmka(amka)) {
            return Response.status(Response.Status.EXPECTATION_FAILED).type("application/json").entity("{\"error\":\"Invalid AMKA.\"}").build();
        }

        String query = "SELECT * FROM `bloodtest` WHERE amka = '" + amka + "'";
        try {
            ArrayList<BloodTest> list = (new BloodTestDatabaseOperations()).databaseToBloodTestList(amka, query);
            String result = "[";
            String s = "_level";
            for (int i = 0; i < list.size(); ++i) {
                JSONObject json = new JSONObject(new Gson().toJson(list.get(i)));
                json.remove("bloodtest_id");
                json.remove("amka");
                if (!measure.equals("blood_sugar")) {
                    json.remove("blood_sugar");
                    json.remove("blood_sugar" + s);
                }
                if (!measure.equals("cholesterol")) {
                    json.remove("cholesterol");
                    json.remove("cholesterol" + s);
                }
                if (!measure.equals("iron")) {
                    json.remove("iron");
                    json.remove("iron" + s);
                }
                if (!measure.equals("vitamin_d3")) {
                    json.remove("vitamin_d3");
                    json.remove("vitamin_d3" + s);
                }
                if (!measure.equals("vitamin_b12")) {
                    json.remove("vitamin_b12");
                    json.remove("vitamin_b12" + s);
                }
                result += json.toString() + ",";
            }
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1) + "]";
            }
            return Response.status(Response.Status.OK).type("application/json").entity(result).build();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @PUT
    @Path("/bloodTest/{bloodTestID}/{measure}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBloodTest(@PathParam("bloodTestID") String btID, @PathParam("measure") String measure, @PathParam("value") double val) {
        if (!(measure == "blood_sugar"
                || measure == "cholesterol"
                || measure == "iron"
                || measure == "vitamin_d3"
                || measure == "vitamin_b12")) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid measure.\"}").build();
        } else if (BloodTestDatabaseOperations.checkForIvalidTestID(btID)) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid ID.\"}").build();
        } else if (val < 0) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid (negative) value.\"}").build();
        }

        try {
            String valToStr = String.valueOf(val);
            BloodTestDatabaseOperations.updateTest(measure, valToStr, btID);
            return Response.status(Response.Status.OK).type("application/json").entity("Update completed").build();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @DELETE
    @Path("/bloodTestDeletion/{bloodTestID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBloodTestID(@PathParam("bloodTestID") String btID) {
        if (BloodTestDatabaseOperations.checkForIvalidTestID(btID)) {
            return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Invalid ID.\"}").build();
        }
        try {
            BloodTestDatabaseOperations.deleteTest(btID);
            return Response.status(Response.Status.OK).type("application/json").entity("Deletion successful").build();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
