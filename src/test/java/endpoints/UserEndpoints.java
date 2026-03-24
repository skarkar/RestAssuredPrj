package endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.User;	

//CRUD operation methods for endpoints
public class UserEndpoints {

	public static Response createUser(User payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.create_url);
		
		return response;
	}
	
	public static Response getUser(String username) {
		Response response = given()
			.pathParam("username", username)
		.when()
			.get(Routes.get_url);
		
		return response;
	}
	
	public static Response updateUser(User payload, String username) {
		Response response = given()
			.pathParam("username", username)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.put(Routes.update_url);
		
		return response;
	}
	
	public static Response deleteUser(String username) {
		Response response = given()
			.pathParam("username", username)
		.when()
			.delete(Routes.delete_url);
		
		return response;
	}
	
}
