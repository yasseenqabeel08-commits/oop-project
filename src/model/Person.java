package model;
import java.time.LocalDate;
import model.enums.*;

public abstract class Person {
protected String username;
protected String password;
protected String name;
protected LocalDate dateOfBirth;
protected Gender gender;

public Person(){};
public Person(String username,String password,String name, LocalDate dateOfBirth,Gender gender){
    this.username = username;
    this.password = password;
    this.name=name;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
}

public abstract void validatePassword(String password);


    public String getName() {
        return name;
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
}
