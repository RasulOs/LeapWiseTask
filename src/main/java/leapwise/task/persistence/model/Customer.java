package leapwise.task.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.gson.annotations.Expose;



@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
	@Expose
	private String firstName;
	@Expose
	private String lastName;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@Expose
	private Address address;
	@Expose
	private int salary;
	@Expose
	private String type;
	
	public Customer(String firstName, String lastName, Address address, int salary, String type) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.salary = salary;
		this.type = type;
	}
	
	
	
	public Customer() {
		super();
	}



	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", salary=" + salary + ", type=" + type + "]";
	}
}