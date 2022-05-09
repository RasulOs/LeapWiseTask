package leapwise.task.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.gson.annotations.Expose;



@Entity
public class RootNode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rootId;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@Expose
    private Customer customer;

	public RootNode() {
		super();
	}

	public RootNode(Customer customer) {
		super();
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Root [id=" + rootId + ", customer=" + customer + "]";
	}
}
