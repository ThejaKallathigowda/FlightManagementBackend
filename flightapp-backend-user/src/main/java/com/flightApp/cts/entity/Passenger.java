package com.flightApp.cts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 *  change the schema attribute according to your schema
 */

@Entity
@Table(name = "passengers")
public class Passenger {
	
	@Id
	@GeneratedValue
	private Integer passengerId;
	
	private String name;
	private Integer age;
	private String gender;
	
	

	public Passenger() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Passenger(Integer passengerId, String name, Integer age, String gender) {
		super();
		this.passengerId = passengerId;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public Integer getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


}
