package stepdefination;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.github.javafaker.Faker;

import helper.base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojofiles.GooglepagestepsREQpojo;
import pojofiles.Locationchildpojo;
import pojofiles.updateREQpojo;
import resource.Endpoints;
import groovy.json.JsonBuilder;

public class googlepagesteps1 {

	RequestSpecification req;

	Response res;
	static String PlaceID;

	static String fname;
	static String fphone;
	static String faddress;
	static String typ1;
	static String flanguage;
	static String typ2;
	Faker f = new Faker();
	GooglepagestepsREQpojo rq = new GooglepagestepsREQpojo();
	Locationchildpojo l = new Locationchildpojo();

	@Given("user prepare a request with payload")
	public void user_prepare_a_request_with_payload() throws IOException {

		Faker f = new Faker();
		double fLat = f.number().randomNumber();
		double fLng = f.number().randomNumber();
		int facu = f.number().randomDigit();
		fname = f.name().fullName();
		fphone = f.phoneNumber().phoneNumber();
		faddress = f.address().fullAddress();
		typ1 = f.funnyName().name();
		typ2 = f.funnyName().name();
		flanguage = f.programmingLanguage().name();

		l.setLat(fLat);
		l.setLng(fLng);
		rq.setLocation(l);
		rq.setAccuracy(facu);
		rq.setName(fname);
		rq.setPhone_number(fphone);
		rq.setAddress(faddress);
		List<String> typeslist = new ArrayList<String>();
		typeslist.add(typ1);
		typeslist.add(typ2);
		rq.setTypes(typeslist);
		rq.setWebsite("http://google.com");
		rq.setLanguage(flanguage);

		req = given().log().all().spec(base.setup()).body(rq);
	}

	@Then("User send {string} request")
	public void user_send_request(String method) {
		if (method.equalsIgnoreCase("POST")) {
			res = req.post(Endpoints.addplace);
		} else if (method.equalsIgnoreCase("GET")) {
			res = req.get(Endpoints.getplace);
		} else if (method.equalsIgnoreCase("PUT")) {
			res = req.put(Endpoints.updateplace);
		} else if (method.equalsIgnoreCase("Delete")) {
			res = req.delete(Endpoints.deleteplace);
		}

	}

	@Then("User get status reponse code {string}")
	public void user_get_status_reponse_code(String statuscode) {
		int actualcode = res.getStatusCode();
		Assert.assertEquals(String.valueOf(actualcode), statuscode);

	}

	@Then("user validate {string} is {string}")
	public void user_validate_is(String string1, String string2) {

		String resp = res.asString();
		JsonPath js = new JsonPath(resp);

		String actualstatus = js.getString(string1);
		Assert.assertEquals(actualstatus, string2);
		PlaceID = js.getString("place_id");

	}

	@Given("user prepare a get request with payload")
	public void user_prepare_a_get_request_with_payload() throws IOException {

		req = given().log().all().spec(base.setup()).queryParam("place_id", PlaceID);

	}

	@Then("user validate all the response")
	public void user_validate_all_the_response() {

		String res1 = res.asString();

		JsonPath js = new JsonPath(res1);

		String actualname = js.getString("name");

		Assert.assertEquals(actualname, fname);
		String actualphonenumber = js.getString("phone_number");
		Assert.assertEquals(actualphonenumber, fphone);
		String actualaddress = js.getString("address");
		Assert.assertEquals(actualaddress, faddress);

		String actualwebsite = js.getString("website");
		Assert.assertEquals(actualwebsite, "http://google.com");
		String actuallanguage = js.getString("language");
		Assert.assertEquals(actuallanguage, flanguage);

	}

	@Given("user prepare a put request with payload")
	public void user_prepare_a_put_request_with_payload() throws IOException {

		updateREQpojo up = new updateREQpojo();
		up.setPlace_id(PlaceID);
		String faddress2 = f.address().fullAddress();
		up.setAddress(faddress2);
		up.setKey("qaclick123");
		req = given().log().all().spec(base.setup()).queryParam("place_id", PlaceID).body(up);

	}

	@Then("user validate response {string} is {string}")
	public void user_validate_response_is(String string1, String string2) {

		String resp = res.asString();

		JsonPath js2 = new JsonPath(resp);

		String actualmsg = js2.getString(string1);
		Assert.assertEquals(actualmsg, string2);

	}

	@Given("user prepare a put delete with payload")
	public void user_prepare_a_put_delete_with_payload() throws IOException {
		req = given().log().all().spec(base.setup())
				.body("{\r\n" + "\r\n" + "    \"place_id\":\"" + PlaceID + "\"\r\n" + "}");
	}

}
