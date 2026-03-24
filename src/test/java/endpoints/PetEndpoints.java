package endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Pet;	

//CRUD operation methods for endpoints
public class PetEndpoints {

	public static Response addPet(Pet payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.addPet_url);
		
		return response;
	}
	
	public static Response getPetByStatus(String status) {
		Response response = given()
			.queryParam("status", status)
		.when()
			.get(Routes.getPetByStatus_url);
		
		return response;
	}
	
	public static Response getPetById(int id) {
		Response response = given()
			.pathParam("petid", id)
		.when()
			.get(Routes.getPetById_url);
		
		return response;
	}
	
	public static Response updatePet(Pet payload, int id) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.put(Routes.updatePet_url);
		
		return response;
	}
	
	public static Response deletePet(int id) {
		Response response = given()
			.pathParam("petid", id)
		.when()
			.delete(Routes.deletePet_url);
		
		return response;
	}

}
