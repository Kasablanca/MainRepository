package com.min.dao.entity;

import java.sql.Clob;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {

	private Integer id;

    private String name;

    private Clob warranty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Clob getWarranty() {
		return warranty;
	}

	public void setWarranty(Clob warranty) {
		this.warranty = warranty;
	}
    
}
