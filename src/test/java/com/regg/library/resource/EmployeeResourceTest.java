package com.regg.library.resource;

import com.regg.library.model.Employee;
import com.regg.library.model.JsonUtil;
import com.regg.library.service.implementation.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Test
    public void getEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setName("Norberto");
        employee.setPhone(3418787464L);
        employee.setAddress("Guzman");
        employee.setSalary(5000.25);

        Employee employee2 = new Employee();
        employee2.setName("Norberto2");
        employee2.setPhone(3418787460L);
        employee2.setAddress("Guzman2");
        employee2.setSalary(5000.25);

        List<Employee> allEmployees = Arrays.asList(employee, employee2);

        given(employeeService.list()).willReturn(allEmployees);
        mockMvc.perform(
                        get("/employee/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.employees", hasSize(2)))
                .andExpect(jsonPath("$.data.employees[0].name").value(employee.getName()))
                .andExpect(jsonPath("$.data.employees[1].name").value(employee2.getName()));
    }

    @Test
    public void saveEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Norberto");
        employee.setPhone(3418787464L);
        employee.setAddress("Guzman");
        employee.setSalary(5000.25);

        given(employeeService.create(employee)).willReturn(employee);
        mockMvc.perform(
                post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(employee))
        ).andExpect(status().isOk());
    }

    @Test
    public void getEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Norberto");
        employee.setPhone(3418787464L);
        employee.setAddress("Guzman");
        employee.setSalary(5000.25);

        given(employeeService.get(1L)).willReturn(employee);
        mockMvc.perform(
                        get("/employee/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.employee.name").value(employee.getName()));
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Norberto");
        employee.setPhone(3418787464L);
        employee.setAddress("Guzman");
        employee.setSalary(5000.25);

        given(employeeService.update(employee, 1L)).willReturn(employee);
        mockMvc.perform(
                        put("/employee/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(employee))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Norberto");
        employee.setPhone(3418787464L);
        employee.setAddress("Guzman");
        employee.setSalary(5000.25);

        given(employeeService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/employee/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(employee))
                )
                .andExpect(status().isOk());
    }
}