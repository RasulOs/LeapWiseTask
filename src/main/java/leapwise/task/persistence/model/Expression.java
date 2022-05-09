package leapwise.task.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Expression {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String value;

	public Expression(String nameOfExpression, String valueOfExpression) {
		super();
		this.name = nameOfExpression;
		this.value = valueOfExpression;
	}
	
	public Expression() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Expression [id=" + id + ", nameOfExpression=" + name + ", valueOfExpression="
				+ value + "]";
	}
}