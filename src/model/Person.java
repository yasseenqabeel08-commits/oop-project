package model;
import amy.*;
import java.time.LocalDate;
import java.time.Period;

import model.enums.*;

public abstract class Person {
protected String username;
protected String password;
protected String name;
protected LocalDate dateOfBirth;
protected Gender gender;

public Person(){};
public Person(String username,String password,String name, LocalDate dateOfBirth,Gender gender){
    validateUsername(username);
    validatePassword(password);
    validateDateOfBirth(dateOfBirth);
    if (name == null || name.isBlank())
        throw new IllegalArgumentException("Full name cannot be empty.");
    if (gender == null)
        throw new IllegalArgumentException("Gender cannot be null.");
    this.username = username;
    this.password = password;
    this.name=name;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
}
    public static void validateUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty.");
        if (username.length() < 3 || username.length() > 20)
            throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
        if (!username.matches("^[a-zA-Z0-9_]+$"))
            throw new IllegalArgumentException("Username can only contain letters, digits, and underscores.");
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        if (!password.matches(".*[A-Z].*"))
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        if (!password.matches(".*\\d.*"))
            throw new IllegalArgumentException("Password must contain at least one digit.");
    }

    public static void validateDateOfBirth(LocalDate dob) {
        if (dob == null)
            throw new IllegalArgumentException("Date of birth cannot be null.");
        if (!dob.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Date of birth must be in the past.");
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18)
            throw new IllegalArgumentException("You must be at least 18 years old to register.");
    }
    public abstract boolean login(String username, String password);

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername(){
return username;
}
public  String getPassword(){return password;};
public  LocalDate getDateOfBirth(){return dateOfBirth;}
    public Gender getGender(){
    return gender;
    }


public void setUsername(String username){this.username=username;}
    public void setPassword(String password){this.password=password;}

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setGender(Gender gender){
    this.gender=gender;}

    @Override
    public String toString() {
        return String.format("%-20s | %-12s | DOB: %s | Age: %d | %s",
                username, name, dateOfBirth, getAge(), gender);
    }
}
}
