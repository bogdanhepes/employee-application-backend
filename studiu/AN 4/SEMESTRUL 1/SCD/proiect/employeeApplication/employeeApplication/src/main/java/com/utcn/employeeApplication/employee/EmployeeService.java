package com.utcn.employeeApplication.employee;

import com.google.common.hash.Hashing;
import com.utcn.employeeApplication.department.Department;
import com.utcn.employeeApplication.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    public boolean createEmployee ( CreateEmployeeRequestDTO registerUserRequest) {
        String name = registerUserRequest.getName();
        String username = registerUserRequest.getUsername();
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null
                || password.isEmpty()) {
            return false;
        }

        Optional<Employee> foundUser = employeeRepository.findEmployeeByUsernameOrEmail(username, email);
        if (foundUser.isPresent()) {
            return false;
        }

        Employee user = new Employee();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(hashString(password));
        user.setName(name);

        employeeRepository.save(user);
        return true;
    }


    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee != null) {
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setUsername(updatedEmployee.getUsername());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }
    public boolean deleteEmployee(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    //queries

    public List<Employee> getAllEmployeesByDepartment(int departmentID) {
        return employeeRepository.findByDepartment(departmentID);
    }

    public boolean assignEmployeeToDepartment(int id, int departmentID) {
        Employee existingEmployee = getEmployeeById(id);
        Department existingDepartment = departmentService.getDepartmentById(departmentID);
        Optional<Employee> departmentManager = departmentService.getManager(departmentID);

        if (existingEmployee != null && departmentID != 0 && id != 0) {
            existingEmployee.setDepartmentID(departmentID);

            if (!departmentManager.isEmpty()) {
                existingEmployee.setManagerID(departmentManager.get().getId());
            } else  {
                existingEmployee.setManagerID(id);
                existingDepartment.setManagerID(id);
            }
            // Save the updated employee
            employeeRepository.save(existingEmployee);
            return true;
        } else {
            return false;
        }
    }
    private String hashString(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

}
