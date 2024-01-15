package com.utcn.employeeApplication.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.departmentID = :departmentID")
    List<Employee> findByDepartment(@Param("departmentID") int departmentID);
    Optional<Employee> findEmployeeByUsernameOrEmail(String username, String email);
    Optional<Employee> findEmployeeByUsernameAndPassword(String username, String password);

    Optional<Employee> findByUsername(String username);

}
