package com.fintech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Balance cannot be negative! Please add an appropriate amount.")
public class NegativeBalance extends RuntimeException {

}
