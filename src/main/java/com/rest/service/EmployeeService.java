package com.rest.service;

import com.rest.entities.Employee;
import com.rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmailService emailService;

	public String addEmployee(Employee employee) {
		employee.setId(java.util.UUID.randomUUID().toString());
		employee.setProfileImage("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		employeeRepository.save(employee);
		return employee.getId();
	}

	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public void deleteEmployee(String id) {
		employeeRepository.deleteById(id);
	}

	public void updateEmployee(String id, Employee updatedEmployee) {
		employeeRepository.findById(id).ifPresent(existingEmployee -> {
			updatedEmployee.setId(id);
			employeeRepository.save(updatedEmployee);
		});
	}

	public Employee getNthLevelManager(String employeeId, int n) {
		Employee employee = employeeRepository.findById(employeeId).orElse(null);
		return (employee != null) ? findNthLevelManager(employee, n) : null;
	}

	private Employee findNthLevelManager(Employee employee, int n) {
		if (n <= 0) {
			return employee;
		}

		String reportsTo = employee.getReportsTo();
		if (reportsTo != null) {
			Employee manager = employeeRepository.findById(reportsTo).orElse(null);
			return (manager != null) ? findNthLevelManager(manager, n - 1) : null;
		}

		return null;
	}

	public Page<Employee> getPagedAndSortedEmployees(int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return employeeRepository.findAll(pageable);
	}

	public void sendNewEmployeeEmail(Employee newEmployee) {
		emailService.sendNewEmployeeEmail(newEmployee);
	}
}
