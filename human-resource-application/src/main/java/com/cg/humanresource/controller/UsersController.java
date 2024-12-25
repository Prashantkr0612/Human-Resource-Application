package com.cg.humanresource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.humanresource.entity.Users;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.service.UsersService;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins="http://localhost:4200")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping(value="/signin",consumes="application/json")
    public ResponseEntity<Boolean> signIn(@RequestBody Users user) {
    	boolean isAuthenticated = usersService.signIn(user.getUsername(),user.getPassword());
    	if(isAuthenticated) {
    		return new ResponseEntity<Boolean>(true,HttpStatus.FOUND);
    	}else {
    		return new ResponseEntity<Boolean>(false,HttpStatus.NOT_FOUND);
    	}
    	
    }

    @PostMapping(value="/signup",consumes="application/json")
    public ResponseEntity<String> signUp(@RequestBody Users user) {
        if(user.getUsername().equals(null) || user.getPassword().equals(null)) {
        	throw new ResourceNotFoundException("Please provide username or password");
        }
        usersService.signUp(user);
        return new ResponseEntity<String>("User added successfully",HttpStatus.CREATED);
    }
}
