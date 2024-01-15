package com.utcn.employeeApplication.department;

import com.utcn.employeeApplication.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }

    @GetMapping("/{id}")
    public Department getEmployee(@PathVariable int id) {
        return departmentService.getDepartmentById(id);

    }

    @PutMapping("/{departmentID}")
    public Department update(@PathVariable int departmentID, @RequestBody Department department) {
        return departmentService.updateDepartment(departmentID, department);
    }

    @DeleteMapping("/{departmentID}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int departmentID) {
        boolean deleted = departmentService.deleteDepartment(departmentID);

        if (deleted) {
            return new ResponseEntity<>("Department with ID " + departmentID + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Department with ID " + departmentID + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{departmentID}/getManager")
    public Optional<Employee> getManager(@PathVariable int departmentID) {
        return departmentService.getManager(departmentID);
    }
}
