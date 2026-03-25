package test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.PetEndpoints;
import endpoints.StoreEndpoints;
import io.restassured.response.Response;
import payloads.Store;

public class Store_Test {
	
	Faker f;
	Store payload;
	
	@BeforeTest
	public void setPayload(ITestContext context) {
		f = new Faker();
		payload = new Store();
		
		payload.setId(f.number().randomDigitNotZero());
		
		if(context.getSuite().getAttribute("petId") == null)
			payload.setPetId(f.idNumber().hashCode());
		else
			payload.setPetId(Integer.parseInt(context.getSuite().getAttribute("petId").toString()));
		payload.setQuantity(f.number().randomDigitNotZero());
		payload.setShipDate(Instant.now().toString());
		payload.setStatus("placed");
	}
	
	@Test(priority = 1)
	public void getInventory_Test() {
		Response response = StoreEndpoints.getInventory();
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		
		Assert.assertTrue(response.jsonPath().getInt("available") > 0);
	}
	
	@Test(priority = 2)
	public void createOrder_Test() {
		Response response = StoreEndpoints.createOrder(payload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		Assert.assertEquals(response.jsonPath().getInt("id"), payload.getId());
		
	}
	
	@Test(priority = 3)
	public void getOrder_Test() {
		Response response = StoreEndpoints.getOrder(payload.getId());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		
		Assert.assertEquals(response.jsonPath().getInt("id"), payload.getId());
	}
	
	@Test(priority = 4)
	public void deleteOrder_Test() {
		Response response = StoreEndpoints.deleteOrder(payload.getId());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		Assert.assertTrue(response.then().extract().jsonPath().get("message").toString().equals(String.valueOf(payload.getId())));
	}
	
}
