package com.utcn.employeeApplication.department;

import com.utcn.employeeApplication.employee.Employee;
import com.utcn.employeeApplication.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(int departmentID) {
        return departmentRepository.findById(departmentID).orElse(null);
    }
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department updateDepartment(int departmentID, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(departmentID);

        if (existingDepartment != null) {
            existingDepartment.setDescription(updatedDepartment.getDescription());
            return departmentRepository.save(existingDepartment);
        } else {
            return null;
        }
    }
    public boolean deleteDepartment(int departmentID) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentID);

        if (optionalDepartment.isPresent()) {
            departmentRepository.delete(optionalDepartment.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Employee> getManager(int departmentID) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentID);

        if (departmentOptional.isPresent()) {
            Integer managerId = departmentOptional.get().getManagerID();
            if(managerId!=null){ return employeeRepository.findById(managerId);}

            else {return Optional.empty();}
        } else {
            return Optional.empty();
        }
    }

}
