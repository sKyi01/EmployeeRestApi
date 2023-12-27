package com.rest.service;

import com.rest.entities.Employee;
import com.rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void sendNewEmployeeEmail(Employee newEmployee) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(getLevel1ManagerEmail(newEmployee.getReportsTo()));
        message.setSubject("New Employee Added");
        message.setText("Dear Manager,\n\n" +
                newEmployee.getEmployeeName() + " will now work under you. " +
                "Mobile number is " + newEmployee.getPhoneNumber() +
                " and email is " + newEmployee.getEmail() + ".");
        javaMailSender.send(message);
    }

    private String getLevel1ManagerEmail(String reportsTo) {
        Employee manager = employeeRepository.findById(reportsTo).orElse(null);
        return (manager != null) ? manager.getEmail() : null;
    }
}
