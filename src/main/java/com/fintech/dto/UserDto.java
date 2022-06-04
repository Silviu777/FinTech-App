package com.fintech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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
