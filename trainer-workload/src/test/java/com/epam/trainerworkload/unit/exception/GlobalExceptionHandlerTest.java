package com.epam.trainerworkload.unit.exception;


import com.epam.trainerworkload.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleValidationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("object", "field1", "message1"));
        errors.add(new FieldError("object", "field2", "message2"));
        when(bindingResult.getAllErrors()).thenReturn(errors);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Object> response = handler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, ((Map<?, ?>) response.getBody()).size());
        assertEquals("message1", ((Map<?, ?>) response.getBody()).get("field1"));
        assertEquals("message2", ((Map<?, ?>) response.getBody()).get("field2"));
    }
}
