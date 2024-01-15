package com.utcn.employeeApplication.user;

import com.utcn.employeeApplication.employee.Employee;
import com.utcn.employeeApplication.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean authenticateUser(String username, String password){
        String hashedPassword = hashString(password);
        Optional<Employee> user = employeeRepository.findEmployeeByUsernameAndPassword(username,hashedPassword);
        if(user.isPresent()){
            return true;
        }
        return false;
    }


    private String hashString(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

}
