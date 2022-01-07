package com.regg.library.service;

import com.regg.library.model.Employee;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface EmployeeService {
    Employee create(Employee employee);
    Collection<Employee> list();
    Collection<Employee> listByName(String name);
    Employee get(Long id) throws RecordNotFoundException;
    Employee update(Employee employee, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
