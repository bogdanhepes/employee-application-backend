package com.utcn.employeeApplication.employee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEmployeeRequestDTO {

    private String name;
        private String username;
        private String email;
        private String password;

        public CreateEmployeeRequestDTO() {
        }

        public CreateEmployeeRequestDTO(String name, String username, String email, String password) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.password = password;
        }


    }


