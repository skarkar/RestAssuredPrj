package test;

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
import io.restassured.response.Response;
import payloads.Pet;

public class Pet_Test {
	
	Faker f;
	Pet payload;
	
	@BeforeTest
	public void setPayload(ITestContext context) {
		f = new Faker();
		payload = new Pet();
		
		payload.setId(f.idNumber().hashCode());
		context.getSuite().setAttribute("petId", payload.getId()); //capturing the Pet ID value to be used by other Endpoints
		
		Map<String, Object> category = new HashMap<String, Object>();
		category.put("id", f.idNumber().hashCode());
		category.put("name", f.dog().breed());
		payload.setCategory(category);
		
		payload.setName(f.dog().name());
		
		//String[] urls = {f.internet().url(), f.internet().url(), f.internet().url()};
		List<String> urls = new ArrayList<String>();
		urls.add(f.internet().url());
		urls.add(f.internet().url());
		urls.add(f.internet().url());
		payload.setPhotoUrls(urls);
		
		@SuppressWarnings("unchecked")
		Map<String, Object>[] tags = new HashMap[2];
		tags[0] = new HashMap<String, Object>();
				tags[0].put("id", f.idNumber().hashCode());
				tags[0].put("name", f.artist().name());
		tags[1] = new HashMap<String, Object>();
			tags[1].put("id", f.idNumber().hashCode());
			tags[1].put("name", f.artist().name());
		payload.setTags(tags);
		
		payload.setStatus("available");
	}
	
	@Test(priority = 1)
	public void addPet_Test() {
		Response response = PetEndpoints.addPet(payload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		Assert.assertEquals(response.jsonPath().getInt("id"), payload.getId());
		
	}
	
	@Test(priority = 2)
	public void getPetByStatus_Test() {
		Response response = PetEndpoints.getPetByStatus(payload.getStatus());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		
		//Verify newly created Pet is present in this response
		boolean status = false;
		JSONArray ja = new JSONArray(response.body().asString());
		
		for(int i = 0; i < ja.length(); i++) {
			if(ja.getJSONObject(i).getInt("id") == payload.getId()) {
				status = true;
				System.out.println("Pet was found by Status");
				System.out.println(ja.getJSONObject(i).toString(4));
				break;
			}
		}
		Assert.assertTrue(status);	
	}
	
	@Test(priority = 3)
	public void getPetById_Test() {
		Response response = PetEndpoints.getPetById(payload.getId());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		
		Assert.assertEquals(response.jsonPath().getInt("id"), payload.getId());
	}
	
	@Test(priority = 4)
	public void updatePet_Test() {
		payload.setName(f.cat().name());
		payload.setStatus("sold");
		
		Response response = PetEndpoints.updatePet(payload, payload.getId());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		Assert.assertTrue(response.jsonPath().get("name").toString().equals(payload.getName()));
		Assert.assertTrue(response.jsonPath().get("status").toString().equals(payload.getStatus()));
	}
	
	@Test(priority = 5)
	public void deletePet_Test() {
		Response response = PetEndpoints.deletePet(payload.getId());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getHeader("access-control-allow-methods"), "GET, POST, DELETE, PUT");
		Assert.assertEquals(response.getHeader("server"), "Jetty(9.2.9.v20150224)");
		Assert.assertTrue(response.then().extract().jsonPath().get("message").toString().equals(String.valueOf(payload.getId())));
	}
	
}
