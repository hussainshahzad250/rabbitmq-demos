package com.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.dto.Employee;
import com.rabbitmq.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping(value = "/employee")
	public String getData(@RequestBody Employee employee) {
		return employeeService.constructObjectToXML(employee);
	}
	@PostMapping(value = "/publishEmployee")
	public String publishEmployee(@RequestBody Employee employee) {
		return employeeService.publishEmployee(employee);
	}

}
