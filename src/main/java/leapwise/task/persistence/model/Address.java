package leapwise.task.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.annotations.Expose;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;
	
	@Expose
    private String city;
	@Expose
    private int zipCode;
	@Expose
    private String street;
	@Expose
    private int houseNumber;
    
	public Address(String city, int zipCode, String street, int houseNumber) {
		super();
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
		this.houseNumber = houseNumber;
	}

	public Address() {
		super();
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	@Override
	public String toString() {
		return "Address [id=" + addressId + ", city=" + city + ", zipCode=" + zipCode + ", street=" + street + ", houseNumber="
				+ houseNumber + "]";
	}
    
}