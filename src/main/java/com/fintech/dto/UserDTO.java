package com.fintech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fintech.model.Account;
import com.fintech.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String userName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String birthDate;

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    private String emailAddress;

}
