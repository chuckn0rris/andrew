package com.application.spring.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.spring.model.Company;
import com.application.spring.model.Employee;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	//Map to store employees, ideally we should use database
	Map<Integer, Employee> empData = new HashMap<Integer, Employee>();
	Map<Integer, Company> compData = new HashMap<Integer, Company>();
	
	@RequestMapping(value = EmpRestURIConstants.SAMPLE_EMP_URL, method = RequestMethod.GET)
	public @ResponseBody Employee getSampleEmployee() {
		logger.info("Start getSampleEmployee");
		Employee emp = new Employee();
		emp.setId(9997);
		emp.setCompanyId(1);
		emp.setName("Sample " + emp.getId());
		emp.setAddress("Sample Address " + emp.getId());
		//emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.SAMPLE_EMPS_URL, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getSampleEmployees() {
		logger.info("Start getSampleEmployees");
		List<Employee> emps = new ArrayList<Employee>();
		Employee emp = new Employee();
		emp.setId(9998);
		emp.setCompanyId(1);
		emp.setName("Sample " + emp.getId());
		emp.setAddress("Sample Address " + emp.getId());
		//emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		emp = new Employee();
		emp.setId(9999);
		emp.setCompanyId(1);
		emp.setName("Sample " + emp.getId());
		emp.setAddress("Sample Address " + emp.getId());
		//emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}
	
   	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_COMPS_URL, method = RequestMethod.GET)
	public @ResponseBody List<Company> getAllCompanies() {
		logger.info("Start getAllCompanies");
		List<Company> comps = new ArrayList<Company>();
		Company comp = new Company();
		comp.setId(1);
		comp.setName("Google");
		comp.setAddress("Address 1");
		compData.put(comp.getId(), comp);
		comp = new Company();
		comp.setId(1);
		comp.setName("Apple");
		comp.setAddress("Address 2");
		compData.put(comp.getId(), comp);
		comp = new Company();
		comp.setId(1);
		comp.setName("IBM");
		comp.setAddress("Address 3");
		compData.put(comp.getId(), comp);
		comp = new Company();
		comp.setId(1);
		comp.setName("Stark Enterprises");
		comp.setAddress("Address 1");
		compData.put(comp.getId(), comp);
		
		Set<Integer> compIdKeys = compData.keySet();
		for(Integer i : compIdKeys){
			comps.add(compData.get(i));
		}
		return comps;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_EMP_URL, method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);
		
		return empData.get(empId);
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_COMP_EMP_URL, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getEmployees(@PathVariable("companyId") int companyId) {
		logger.info("Start getEmployee. ID="+companyId);
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			Employee emp = empData.get(i);
			if(companyId == emp.getCompanyId()) {
				emps.add(emp);
			}
		}		
		return emps;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP_URL, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}
	
	/*@RequestMapping(value = EmpRestURIConstants.CREATE_EMP_URL, method = RequestMethod.POST)
	public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
		logger.info("Start createEmployee.");
		emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		return emp;
	}*/
	//BindingResult results
	
	//@RequestMapping(value = EmpRestURIConstants.CREATE_EMP_URL, method = RequestMethod.POST)
	
	@RequestMapping(value = EmpRestURIConstants.CREATE_EMP_URL, method = RequestMethod.POST)
	public @ResponseBody Employee createEmployee(@RequestBody String json) {
		// this is done like this because there was a problem on my particular machine with quotes in JSON when creating objects
		int id = 1;
		Set<Integer> set = empData.keySet();
		if(set != null && set.size() > 0) {
			int idx = Collections.max(empData.keySet());
			id = idx+1;
		}				
		String name = "";
		String address = "";
	    Scanner sc = new Scanner(json);
	    sc.useDelimiter("&");
	    String input = sc.next();
	    Scanner innerSc = new Scanner(input);
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    int companyId = Integer.parseInt(innerSc.next());
	    innerSc = new Scanner(sc.next());
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    name = innerSc.next();
	    innerSc = new Scanner(sc.next());
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    address = innerSc.next();
	    address = address.replace("+", " ");
	    Employee emp = new Employee();
		emp.setId(id);
		emp.setCompanyId(companyId);
		emp.setName(name);
		emp.setAddress(address);
		empData.put(emp.getId(), emp);
	    return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.UPDATE_EMP_URL, method = RequestMethod.POST)
	public @ResponseBody Employee updateEmployee(@RequestBody String json) {
		// this is done like this because there was a problem on my particular machine with quotes in JSON when creating objects
		String name = "";
		String address = "";
	    Scanner sc = new Scanner(json);
	    sc.useDelimiter("&");
	    String input = sc.next();
	    Scanner innerSc = new Scanner(input);
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    int id = Integer.parseInt(innerSc.next());
	    Employee emp = empData.get(id);
	    innerSc = new Scanner(sc.next());
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    name = innerSc.next();
	    innerSc = new Scanner(sc.next());
	    innerSc.useDelimiter("=");
	    innerSc.next();
	    address = innerSc.next();
	    address = address.replace("+", " ");
		emp.setName(name);
		emp.setAddress(address);
		empData.put(emp.getId(), emp);
	    return emp;
	}

	
	//public @ResponseBody List<Employee> createEmployee(@RequestBody Employee e, BindingResult results) {
	/*@ResponseBody
	public void createEmployee(HttpServletRequest request) {
		HttpServletRequest req = request;
		logger.info("Start createEmployee.");
		if(req != null && req.getParameterNames().hasMoreElements()) {
			Enumeration<String> parameterNames = req.getParameterNames();
			
			 
			
			        while (parameterNames.hasMoreElements()) {
			
			 
			
			            String paramName = parameterNames.nextElement();
			
			            System.out.println(paramName);
			
			            System.out.println("\n");
			
			 
			
			            String[] paramValues = req.getParameterValues(paramName);
			
			            for (int i = 0; i < paramValues.length; i++) {
			
			                String paramValue = paramValues[i];
			
			                System.out.println("\t" + paramValue);
			
			                System.out.println("\n");
			
			            }
			
			 
			
			        }

		}
		//emp.setCreatedDate(new Date());
		//empData.put(emp.getId(), emp);
		//return null;
	}*/
	
	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP_URL, method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empData.get(empId);
		empData.remove(empId);
		return emp;
	}
	
}
