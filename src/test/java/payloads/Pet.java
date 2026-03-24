package payloads;

import java.util.List;
import java.util.Map;

public class Pet {
    int id;
    Map<String, Object> category;
    String name;
    List<String> photoUrls;
    Map<String, Object>[] tags;
    String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public List<String> getPhotoUrls() {
    	return photoUrls; 
	}
    public void setPhotoUrls(List<String> photoUrls) {
    	this.photoUrls = photoUrls; 
	}
	public Map<String, Object>[] getTags() {
		return tags;
	}
	public void setTags(Map<String, Object>[] tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getCategory() {
		return category;
	}
	public void setCategory(Map<String, Object> category) {
		this.category = category;
	}

}
