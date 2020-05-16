package com.yapp.ios2.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User is not exist")
public class UserNotFoundException extends RuntimeException {
}
