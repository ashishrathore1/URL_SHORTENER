package com.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="apientity")
public class ApiEntity {
	
	@Id
	@Column(name = "apikey")
	private String apiKey;
	
	@Column(name = "qaemailid", nullable=false)
	private String qaEmailID;
	
	@Column(name = "team", nullable=false)
	private String teamName;
	
	@Column(name = "project", nullable=false)
	private String projectName;
	
	@Column(name = "keyenabled", nullable=false)
	private boolean keyEnabled;

	public ApiEntity(String apiKey, String qaEmailID, String teamName,
			String projectName, boolean keyEnabled) {
		this.apiKey = apiKey;
		this.qaEmailID = qaEmailID;
		this.teamName = teamName;
		this.projectName = projectName;
		this.keyEnabled = keyEnabled;
	}
	public ApiEntity() {
	}
	
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getQaEmailID() {
		return qaEmailID;
	}
	public void setQaEmailID(String qaEmailID) {
		this.qaEmailID = qaEmailID;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public boolean isKeyEnabled() {
		return keyEnabled;
	}
	public void setKeyEnabled(boolean keyEnabled) {
		this.keyEnabled = keyEnabled;
	}
	@Override
	public String toString() {
		return "ApiEntity [apiKey=" + apiKey + ", qaEmailID=" + qaEmailID
				+ ", teamName=" + teamName + ", projectName=" + projectName
				+ ", keyEnabled=" + keyEnabled + "]";
	}
}
