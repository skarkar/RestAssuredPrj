package endpoints;

//This will contain routes for all the endpoints
public class Routes {
	
	public static String base_url = "https://petstore.swagger.io/v2";
	
	//User Endpoints
	public static String create_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String update_url = base_url + "/user/{username}";
	public static String delete_url = base_url + "/user/{username}";
}
