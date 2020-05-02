package com.yapp.ios2.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Album is not exist")
public class AlbumNotFoundException extends RuntimeException {
}
