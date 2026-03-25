package endpoints;

//This will contain routes for all the endpoints
public class Routes {
	
	public static String base_url = "https://petstore.swagger.io/v2";
	
	//User Endpoints
	public static String create_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String update_url = base_url + "/user/{username}";
	public static String delete_url = base_url + "/user/{username}";
	public static String login_url = base_url + "/user/login";
	public static String logout_url = base_url + "/user/logout";
	
	//Pet Endpoints
	public static String addPet_url = base_url + "/pet";
	public static String addPetImage_url = base_url + "/pet/{petid}/uploadImage";
	public static String getPetByStatus_url = base_url + "/pet/findByStatus";
	public static String getPetById_url = base_url + "/pet/{petid}";
	public static String updatePet_url = base_url + "/pet";
	public static String deletePet_url = base_url + "/pet/{petid}";
	
	//Store Endpoints
	public static String getInventory_url = base_url + "/store/inventory";
	public static String createOrder_url = base_url + "/store/order";
	public static String getOrder_url = base_url + "/store/order/{orderid}";
	public static String deleteOrder_url = base_url + "/store/order/{orderid}";
	
}
