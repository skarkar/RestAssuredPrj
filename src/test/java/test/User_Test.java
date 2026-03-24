package test;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.UserEndpoints;
import io.restassured.response.Response;
import payloads.User;

public class User_Test {
	
	Faker f;
	User payload;
	
	@BeforeTest
	public void setPayload() {
		f = new Faker();
		payload = new User();
		
		payload.setId(f.idNumber().hashCode());
		payload.setUsername(f.name().username());
		payload.setFirstname(f.name().firstName());
		payload.setLastname(f.name().lastName());
		payload.setEmail(f.internet().safeEmailAddress());
		payload.setPassword(f.internet().password(true));
		payload.setPhone(f.phoneNumber().cellPhone());
		
		
	}
	
	@Test(priority = 1)
	public void createUser_Test() {
		Response response = UserEndpoints.createUser(payload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void getUser_Test() {
		Response response = UserEndpoints.getUser(payload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 3)
	public void updateUser_Test() {
		payload.setFirstname(f.name().firstName());
		payload.setLastname(f.name().lastName());
		
		Response response = UserEndpoints.updateUser(this.payload, payload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//verify data updated
		response = UserEndpoints.getUser(payload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 4)
	public void deleteUser_Test() {
		Response response = UserEndpoints.deleteUser(payload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
				
	}
	
	
}
