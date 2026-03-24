package test;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.UserEndpoints;
import io.restassured.response.Response;
import payloads.User;
import utilities.DataProviders;

public class User_DataDriven {
	
	Faker f;
	User payload;
	
	@Test(priority = 1, dataProvider = "UserData", dataProviderClass = DataProviders.class)
	public void createUser_Test(String userid, String username, String fname, String lname, String email, String password, String phone) {
		
		payload = new User();
		
		payload.setId(Integer.parseInt(userid));
		payload.setUsername(username);
		payload.setFirstname(fname);
		payload.setLastname(lname);
		payload.setEmail(email);
		payload.setPassword(password);
		payload.setPhone(phone);
		
		Response response = UserEndpoints.createUser(payload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2, dataProvider = "Username", dataProviderClass = DataProviders.class)
	public void getUser_Test(String Username) {
		Response response = UserEndpoints.getUser(Username);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 4, dataProvider = "Username", dataProviderClass = DataProviders.class)
	public void deleteUser_Test(String Username) {
		Response response = UserEndpoints.deleteUser(Username);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
				
	}
	
}
