package com.cs790.app.controller.advice;


import com.cs790.app.exceptions.TasksNotFoundException;
import com.cs790.app.exceptions.UserAlreadyExistsException;
import com.cs790.app.exceptions.UserNotFoundException;
import com.cs790.app.model.dto.CustomExceptionDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomExceptionDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        System.out.println("Not even coming here");
        CustomExceptionDto customExceptionDto =  new CustomExceptionDto();
        customExceptionDto.setCustomMessage(ex.getMessage());
        customExceptionDto.setErrorCode("409");

        return ResponseEntity.status(409).body(customExceptionDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomExceptionDto> handleUserNotFoundException(UserNotFoundException ex){
        System.out.println("Not even coming here");
        CustomExceptionDto customExceptionDto =  new CustomExceptionDto();
        customExceptionDto.setCustomMessage(ex.getMessage());
        customExceptionDto.setErrorCode("409");

        return ResponseEntity.status(409).body(customExceptionDto);
    }

    @ExceptionHandler(TasksNotFoundException.class)
    public ResponseEntity<CustomExceptionDto> handleTasksNotFoundException(TasksNotFoundException ex) {
        System.out.println("Handling TasksNotFoundException");
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setCustomMessage(ex.getMessage());
        customExceptionDto.setErrorCode("404");

        return ResponseEntity.status(404).body(customExceptionDto);
    }
}
