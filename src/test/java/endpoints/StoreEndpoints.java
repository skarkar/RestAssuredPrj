package endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Store;	

//CRUD operation methods for endpoints
public class StoreEndpoints {

	public static Response getInventory() {
		Response response = given()
		.when()
			.get(Routes.getInventory_url);
		
		return response;
	}
	
	public static Response createOrder(Store payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.createOrder_url);
		
		return response;
	}
	
	public static Response getOrder(int id) {
		Response response = given()
			.pathParam("orderid", id)
		.when()
			.get(Routes.getOrder_url);
		
		return response;
	}
	
	public static Response deleteOrder(int id) {
		Response response = given()
			.pathParam("orderid", id)
		.when()
			.delete(Routes.deleteOrder_url);
		
		return response;
	}

}
