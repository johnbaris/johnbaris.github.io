package bikescheme;

public class Bike {
	
	private  String bikeId;
	private String status = "working";
	
	public void setBike(String bikeID){
		this.bikeId = bikeID;
	}
	
	public String getBike(){
		return bikeId;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return status;
	}

}
