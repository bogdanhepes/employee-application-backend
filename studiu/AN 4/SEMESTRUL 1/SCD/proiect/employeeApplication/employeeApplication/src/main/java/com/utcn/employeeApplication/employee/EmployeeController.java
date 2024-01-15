package com.utcn.employeeApplication.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Boolean> createEmployee(@RequestBody CreateEmployeeRequestDTO user) {
        boolean isSaved = employeeService.createEmployee(user);
        if (isSaved) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(false);
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployeeById(id);

    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        boolean deleted = employeeService.deleteEmployee(id);

        if (deleted) {
            return new ResponseEntity<>("Employee with ID " + id + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getEmployeeByUsername(@PathVariable String username) {
        Optional<Employee> employeeOptional = employeeService.getEmployeeByUsername(username);

        return employeeOptional.map(employee ->
                        ResponseEntity.ok(employee))
                .orElse(ResponseEntity.notFound().build());}

    // queries

    @GetMapping("/getAllEmployeesByDepartment/{departmentID}")
    public List<Employee> getAllEmployeesByDepartment(@PathVariable int departmentID) {
        return employeeService.getAllEmployeesByDepartment(departmentID);
    }

    @PutMapping("/{id}/assignToDepartment")
    public ResponseEntity<String> assignEmployeeToDepartment(@PathVariable int id, @RequestBody AssignEmployeeToDepartmentDTO request) {
        Integer departmentID = request.getDepartmentID();
        boolean success = employeeService.assignEmployeeToDepartment(id, departmentID);

        if (success) {
            return new ResponseEntity<>("Employee assigned to the department successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found for the given ID", HttpStatus.NOT_FOUND);
        }
    }

}
