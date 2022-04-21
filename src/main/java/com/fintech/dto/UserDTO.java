package com.fintech.dto;

import com.fintech.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String birthDate;

    private List<Account> accounts; // display user's accounts - necessary?

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

}
