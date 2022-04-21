package com.fintech.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String birthDate;
    private final String emailAddress;
    private final String phoneNumber;
    private final String address;
    private final String city;
    private final String country;
}
