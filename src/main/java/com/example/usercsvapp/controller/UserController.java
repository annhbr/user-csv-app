package com.example.usercsvapp.controller;

import com.example.usercsvapp.helper.HelperCSV;
import com.example.usercsvapp.model.UserDetails;
import com.example.usercsvapp.model.IUserWithAge;
import com.example.usercsvapp.service.ServiceCSV;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    ServiceCSV service;
    @Autowired
    HelperCSV helper;

    @PostMapping("users/upload")
    public ResponseEntity uploadData(@Valid @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file.");
        }
        if (helper.isCSVType(file)) {
            try {
                service.save(file);
                return ResponseEntity.status(HttpStatus.OK).body("Successfully added.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only CSV files allowed.");
    }

    @GetMapping("users/count")
    public ResponseEntity countAllUsers() {
        try {
            long count = service.countAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body("Users in database: " + count);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please try again later.");
        }
    }

    @GetMapping("/users/name/{lastName}")
    public ResponseEntity getUsersByLastName(@PathVariable String lastName) {
        try {
            List<UserDetails> usersByLastName = service.getUsersByLastName(lastName);
            if (usersByLastName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return new ResponseEntity<>(usersByLastName, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{ids}")
    public ResponseEntity getUsersByIdList(@PathVariable List<Integer> ids) {
        List<UserDetails> allUsersById = service.getAllUsersById(ids);
        if (allUsersById.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        }
        return new ResponseEntity<>(allUsersById, HttpStatus.OK);
    }


    @DeleteMapping("/users/{ids}")
    public ResponseEntity deleteUsersById(@PathVariable List<Integer> ids) {
        try {
            service.removeUsersByIdList(ids);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/users/sorted")
    public ResponseEntity<List<IUserWithAge>> getUserByAge() {
        try {
            List<IUserWithAge> usersOrderedByAge = service.getUsersOrderedByAge();
            return new ResponseEntity<>(usersOrderedByAge, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        try{
            Pageable paging = PageRequest.of(page, size);
            Page<UserDetails> allUsers = service.findAllUsers(paging);
            List<UserDetails> allUserList = allUsers.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("users", allUserList);
            response.put("currentPage", allUsers.getNumber());
            response.put("totalItems", allUsers.getTotalElements());
            response.put("totalPages", allUsers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
