package com.example.usercsvapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_details", uniqueConstraints = @UniqueConstraint(columnNames = "phone_number", name = "phoneNumberConstraint"))
public class UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "You have to provide first name")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "You have to provide last name")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^(?:\\d{9})?$", message = "Provide valid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "You have to provide birth date")
    @Column(name = "birth_date")
    @Past(message = "Date of birth have to be in the past")
    private LocalDate birthDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public UserDetails(String firstName, String lastName, String phoneNumber, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = LocalDate.parse(birthDate, FORMATTER);
    }
}
