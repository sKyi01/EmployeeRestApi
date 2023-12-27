package com.rest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Data
@Document
@Entity(name = "Employee_Info")
public class Employee {
	@Id
	@Column(name = "Unique_Id")
	private String id;

	@Column(name = "Employee_Name")
	private String employeeName;

	@Column(name = "Phone_Number")
	private String phoneNumber;

	@Column(name = "Email")
	private String email;

	@Column(name = "Report_To")
	private String reportsTo;

	@Column(name = "Profile_Image")
	private String profileImage;
}
