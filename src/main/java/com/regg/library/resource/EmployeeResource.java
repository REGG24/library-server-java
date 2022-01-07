package com.regg.library.resource;

import com.regg.library.model.Employee;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.EmployeeServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {

    private final EmployeeServiceImpl employeeService;

    public EmployeeResource(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getEmployees(){
        Map<String, Collection<Employee>> myMap = new HashMap<>();
        myMap.put("employees", employeeService.list());
        return response(myMap,"Employees retrieved", OK);
    }

    @GetMapping("/list/{name}")
    public ResponseEntity<Response> getEmployeesByName(@PathVariable("name") String name){
        Map<String, Collection<Employee>> myMap = new HashMap<>();
        myMap.put("employees", employeeService.listByName(name));
        return response(myMap,"Employees retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveEmployee(@RequestBody @Valid Employee employee){
        Map<String, Employee> myMap = new HashMap<>();
        myMap.put("employee", employeeService.create(employee));
        return response(myMap,"Employee successfully created!", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getEmployee(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Employee> myMap = new HashMap<>();
        try{
            myMap.put("employee", employeeService.get(id));
            return response(myMap,"Employee retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("employee", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Employee> myMap = new HashMap<>();
        try{
            myMap.put("employee", employeeService.update(employee,id));
            return response(myMap,"Employee successfully updated!", OK);
        }catch (RecordNotFoundException e){
            myMap.put("employee", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", employeeService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Employee successfully deleted!", OK);
    }

    public ResponseEntity<Response> response(Map<?,?> myMap, String message, HttpStatus status){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(myMap)
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build()
        );
    }
    
}
