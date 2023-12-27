package com.rest.repository;

import com.rest.entities.Employee;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface EmployeeRepository extends CouchbaseRepository<Employee, String> {
}
