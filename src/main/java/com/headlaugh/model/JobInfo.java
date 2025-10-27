package com.headlaugh.model;

import java.math.BigDecimal;

public class JobInfo {
	
	private long id;
	private String jobType;
	private String jobDesc;
	private String jobRequirement;
	private BigDecimal salary;
	private String memo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getJobRequirement() {
		return jobRequirement;
	}
	public void setJobRequirement(String jobRequirement) {
		this.jobRequirement = jobRequirement;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public JobInfo(long id, String jobType, String jobDesc, String jobRequirement, BigDecimal salary, String memo) {
		super();
		this.id = id;
		this.jobType = jobType;
		this.jobDesc = jobDesc;
		this.jobRequirement = jobRequirement;
		this.salary = salary;
		this.memo = memo;
	}

	public JobInfo(String jobType, String jobDesc, String jobRequirement, BigDecimal salary, String memo) {
		super();
		this.jobType = jobType;
		this.jobDesc = jobDesc;
		this.jobRequirement = jobRequirement;
		this.salary = salary;
		this.memo = memo;
	}

	public JobInfo() {
		super();
	}


	@Override
	public String toString() {
		return this.id+"#"+this.jobType+"#"+this.jobDesc+"#"+this.jobRequirement+"#"+this.salary+"#"+(this.memo.equals("")?" ":this.memo);
	}
}
