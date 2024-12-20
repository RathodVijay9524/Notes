package com.vijay.note.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException extends RuntimeException {

    private Map<String, Object> error;
}

