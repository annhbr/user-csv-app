package com.example.usercsvapp.repository;

import com.example.usercsvapp.model.UserDetails;
import com.example.usercsvapp.model.IUserWithAge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    List<UserDetails> findByLastNameIgnoreCase(String lastName);

    @Query(value = "SELECT first_name as firstName, last_name as lastName, phone_number as phoneNumber, datediff(YY,birth_date,sysdate) as age FROM user_details ORDER BY age DESC", nativeQuery = true)
    List<IUserWithAge> findAllOrderByAgeDesc();

    Page<UserDetails> findAll(Pageable pageable);

}
