package com.application.spring;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.application.spring.controller.EmpRestURIConstants;
import com.application.spring.model.Employee;

public class TestTheApplication {

	//public static final String SERVER_URI = "http://localhost:9090/SpringRestExample";
	public static final String SERVER_URI = "http://localhost:8080/spring";
	
	public static void main(String args[]){
		
		testGetSampleEmployee();
		System.out.println("*****");
		testCreateEmployee();
		System.out.println("*****");
		testGetEmployee();
		System.out.println("*****");
		testGetAllEmployee();
	}

	private static void testGetAllEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.GET_ALL_EMP_URL, List.class);
		System.out.println(emps.size());
		for(LinkedHashMap map : emps){
			System.out.println("ID="+map.get("id")+"CompanyID="+map.get("companyId")+",Name="+map.get("name")+",Address="+map.get("address")/*+",CreatedDate="+map.get("createdDate")*/);;
		}
	}

	private static void testCreateEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = new Employee();
		emp.setId(1);emp.setName("John Smith");
		Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP_URL, emp, Employee.class);
		printEmpData(response);
	}

	private static void testGetEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
		printEmpData(emp);
	}

	private static void testGetSampleEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.SAMPLE_EMP_URL, Employee.class);
		printEmpData(emp);
	}
	
	public static void printEmpData(Employee emp){
		System.out.println("ID="+emp.getId()+"CompanyID="+emp.getCompanyId()+",Name="+emp.getName()+",Address="+emp.getAddress()/*+",CreatedDate="+emp.getCreatedDate()*/);
	}
}
