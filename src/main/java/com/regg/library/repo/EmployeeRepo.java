package com.regg.library.repo;

import com.regg.library.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %?1%")
    Collection<Employee> listByName(String name);
}
