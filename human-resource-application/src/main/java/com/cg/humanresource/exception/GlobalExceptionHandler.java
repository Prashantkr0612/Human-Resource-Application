package com.cg.humanresource.exception;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<?> sendEmployeeNotFoundStatus(){
		return new ResponseEntity<>("Employee Not Found",HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<?> sendLocationNotFoundStatus(){
		return new ResponseEntity<>("Location Not Found",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<?> sendDepartmentNotFoundStatus(){
		return new ResponseEntity<>("Department Not Found",HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> sendUserNotFoundStatus(){
		return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OpenJobPositionsNotFoundException.class)
	public ResponseEntity<?> sendOpenJobPositionsNotFoundStatus() {
	    return new ResponseEntity<>("Open Job Positions Not Found", HttpStatus.NOT_FOUND);
	}
	 @ExceptionHandler(ValidationNotFoundException.class)
	    public ResponseEntity<Object> handleValidationNotFoundException(ValidationNotFoundException ex) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("timeStamp", LocalDate.now().toString());
	        response.put("message", ex.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException() {
        Map<String,Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", LocalDate.now());
        errorResponse.put("message", "Validation failed");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(CountryNotFoundException.class)
	public ResponseEntity<?> CountryNotFoundStatus() 
	{
		return new ResponseEntity<>("Country not found",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RegionNotFoundException.class)
	public ResponseEntity<?> sendRegionNotFoundStatus() 
	{
		return new ResponseEntity<>("Region not found",HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<?> sendJobNotFoundStatus()
	{
		return new ResponseEntity<>("Job not found",HttpStatus.NOT_FOUND);
	}
	
}
