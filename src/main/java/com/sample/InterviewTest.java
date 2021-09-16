package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class InterviewTest {
	/*
	 * Process data from below URL. Display employee names with highest and lowest salary.
	 * - http://dummy.restapiexample.com/api/v1/employees
	 * - Use any language/script for processing.
	 * - Optimize your code to process large number of records.
	 * - Define validation and test boundary conditions.
	 * - Feel free to use internet for solution.
	 */
	
	static Log log = LogFactory.getLog(InterviewTest.class);
	
	public static void main(String[] args) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> getForEntity = rest.getForEntity("http://dummy.restapiexample.com/api/v1/employees",
				String.class);
		if (getForEntity.getStatusCode() != HttpStatus.OK) {
			log.error("error");
		} else {
			String body = getForEntity.getBody();
			JSONObject json = new JSONObject(body);
			JSONArray dataArray = (JSONArray) json.get("data");
			int max = ((JSONObject)dataArray.get(0)).getInt("employee_salary");
			int min = max;
			List<String> minSalaryEmployees = new ArrayList<>();
			List<String> maxSalaryEmployees = new ArrayList<>();
			for(int i=0;i<dataArray.length();i++) {
				JSONObject empObj = (JSONObject) dataArray.get(i);
				if(empObj.getInt("employee_salary") >= max) {
					maxSalaryEmployees.add(empObj.getString("employee_name"));
					if(empObj.getInt("employee_salary") != max) {
						max = empObj.getInt("employee_salary");
					}
				}
				if(empObj.getInt("employee_salary") <= min) {
					minSalaryEmployees.add(empObj.getString("employee_name"));
					if(empObj.getInt("employee_salary") != min) {
						min = empObj.getInt("employee_salary");
					}
				}
			}
			log.info("Employees with max salary: ");
			for(String name: maxSalaryEmployees) {
				log.info(name);
			}
			log.info("Employees with min salary: ");
			for(String name: minSalaryEmployees) {
				log.info(name);
			}
		}
	}

}
