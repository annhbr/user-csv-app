package com.example.usercsvapp.service;

import com.example.usercsvapp.helper.HelperCSV;
import com.example.usercsvapp.model.UserDetails;
import com.example.usercsvapp.model.IUserWithAge;
import com.example.usercsvapp.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ServiceCSV {

    @Autowired
    UserRepository repository;
    @Autowired
    HelperCSV helperCSV;

    public void save(MultipartFile file){
        try{
            List<UserDetails> usersList = helperCSV.transform(file.getInputStream());
            repository.saveAll(usersList);
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof ConstraintViolationException){
                Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
                ConstraintViolation<?> violation = constraintViolations.stream().findFirst().get();
                message = violation.getMessage();
                log.info("### Issue with input data. Error: {}. Invalid value: {}", message, violation.getInvalidValue());
            }
            if (e instanceof DateTimeParseException){
                message = "Please provide correct date of birth. Acceptable format is yyyy.mm.dd";
                log.info("### Issue with parsing date. Error message: " + e.getMessage());
            }
            if (e instanceof DateTimeParseException && ((DateTimeParseException) e).getParsedString().isEmpty()){
                message = "Data is missing. Please check input file.";
                log.info("### Issue with parsing date. Error message: " + e.getMessage());
            }
            if (e instanceof DataIntegrityViolationException){
                message = "Please provide unique phone number";
                log.info("### Issue with data integrity. Error message: " + e.getMessage());

            }
            throw new RuntimeException("Unable to save data in repository. Error: " + message);
        }
    }

    public List<UserDetails> getAllUsersById(List<Integer> ids){
        return repository.findAllById(ids);
    }

    public List<UserDetails> getUsersByLastName(String lastName){
       return repository.findByLastNameIgnoreCase(lastName);
    }

    public long countAllUsers() {
        return repository.count();
    }

    public void removeUsersByIdList(List<Integer> ids){
        try {
            repository.deleteAllById(ids);
        } catch (Exception e){
            throw new RuntimeException("Unable to delete users. Error: " + e.getMessage());
        }
    }

    public List<IUserWithAge> getUsersOrderedByAge(){
        return repository.findAllOrderByAgeDesc();
    }

    public Page<UserDetails> findAllUsers(Pageable pageable){
        return repository.findAll(pageable);
    }
}
