package com.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="applicationsettings")
public class ApplicationSettingsEntity {
	
	@Id
	@Column(name="keyname")
	private String keyName;
	@Column(name="value")
	private String value;
	
	
	public ApplicationSettingsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ApplicationSettingsEntity(String keyName, String value) {
		super();
		this.keyName = keyName;
		this.value = value;
	}


	public String getKeyName() {
		return keyName;
	}


	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "ApplicationSettingsEntity [keyName=" + keyName + ", value="
				+ value + "]";
	}
	
	

}
