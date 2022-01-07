package com.regg.library.service.implementation;

import com.regg.library.model.Employee;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.repo.EmployeeRepo;
import com.regg.library.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Collection<Employee> list() {
        return employeeRepo.findAll();
    }

    @Override
    public Collection<Employee> listByName(String name) {
        return employeeRepo.listByName(name);
    }

    @Override
    public Employee get(Long id) throws RecordNotFoundException {
        employeeRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Employee"));
        return employeeRepo.findById(id).get();
    }

    @Override
    public Employee update(Employee employee, Long id) throws RecordNotFoundException {
        employeeRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Employee"));
        Employee employeeToUpdate = employeeRepo.getOne(id);
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setPhone(employee.getPhone());
        employeeToUpdate.setAddress(employee.getAddress());
        employeeToUpdate.setSalary(employee.getSalary());
        return employeeRepo.save(employeeToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        employeeRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Employee"));
        employeeRepo.deleteById(id);
        return true;
    }
}
