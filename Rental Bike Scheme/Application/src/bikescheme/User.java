package bikescheme;

import java.util.Date;

public class User {
	private String name;
	private String keyId;
	private String email;
	private String bankCard;
	private int usage=0;
	private Date hireDate;
	private Date returnDate;
	private int charges;
	private String bikeId;
	private static int totalUsage = 0;
	private String hStation;
	private String rStation;
	
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setKey(String keyId)
	{
		this.keyId = keyId;
	}
	
	public void setBike(String bikeId)
	{
		this.bikeId = bikeId;
	}
	
	public String getBike()
	{
		return bikeId;
	}
	
	public void setUsage(int usage)
	{
		this.usage = usage;
	}
	
	public static void setTotalUsage(int usage)
	{
		totalUsage = usage;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getKey()
	{
		return keyId;
	}
	
	public int getUsage()
	{
		return usage;
	}
	
	public static int getTotalUsage()
	{
		return totalUsage;
	}
	
	public void setCard(String card){
		this.bankCard = card;
	}
	
	public String getCard(){
		return bankCard;
	}
	
	public void calcCharges()
	{
		if(getUsage() <= 30){
			charges =  1;
		}
		else{
			if(getUsage() % 30 != 0){
				charges = 1 + ((int)(getUsage()/30))*2;
			}
			else{
				charges = 1 + ((int)(getUsage()/30) - 1)*2;
			}
		}
	}
	
	public int getCharges()
	{
		return charges;
	}
	
	public void setHireDate(Date hDate)
	{
		hireDate = hDate;
	}
	
	public Date getHireDate()
	{
		return hireDate;
	}
	
	public void setReturnDate(Date rDate)
	{
		returnDate = rDate;
	}
	
	public Date getReturnDate()
	{
		return returnDate;
	}
	
	public int calcUsage(){
	   usage += Clock.minutesBetween(getHireDate(), getReturnDate());
	   return usage;
	}

	public void setHireStation(String instance)
	{
		hStation = instance;
	}
	
	public void setReturnStation(String instance)
	{
		rStation = instance;
	}
	
	public String getHireStation()
	{
		return hStation;
	}
	
	public String getReturnStation()
	{
		return rStation;
	}
	
}
