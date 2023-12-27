package com.rest.controller;

import com.rest.entities.Employee;
import com.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		String newEmployeeId = employeeService.addEmployee(employee);
		// Send email to level 1 manager
		employeeService.sendNewEmployeeEmail(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(newEmployeeId);
	}


	@GetMapping
	public ResponseEntity<Iterable<Employee>> getAllEmployees() {
		Iterable<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
		employeeService.updateEmployee(id, updatedEmployee);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/manager/{n}")
	public ResponseEntity<Employee> getNthLevelManager(@PathVariable String id, @PathVariable int n) {
		Employee manager = employeeService.getNthLevelManager(id, n);
		return (manager != null) ? ResponseEntity.ok(manager) : ResponseEntity.notFound().build();
	}

	@GetMapping("/paged")
	public ResponseEntity<Page<Employee>> getPagedAndSortedEmployees(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "employeeName") String sortBy) {
		Page<Employee> employees = employeeService.getPagedAndSortedEmployees(page, size, sortBy);
		return ResponseEntity.ok(employees);
	}
}
