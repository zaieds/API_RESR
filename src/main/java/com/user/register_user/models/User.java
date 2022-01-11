package com.user.register_user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Date;

@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Name must be at least 3 characters")
    private String userName;


    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;


    @NotEmpty
    @Size(max = 50,message = "Only people born in France can create a count ")
    private String country;


    @Pattern(regexp = "(\\+33|0)[0-9]{9}")
    @Column(nullable = true)
    private String phoneNumber;


    @Column(nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    public User() {
    }

    public User(String userName, Date birthday, String country) {
        this.userName = userName;
        this.birthday = birthday;
        this.country = country;
    }
    public User(String userName, Date birthday, String country , String phoneNumber, Gender gender) {
        this.userName = userName;
        this.birthday = birthday;
        this.country = country;
        this.phoneNumber=phoneNumber;
        this.gender=gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName'" + userName + '\'' +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}
