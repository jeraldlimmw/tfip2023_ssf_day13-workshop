package sg.edu.nus.iss.app.day13workshop.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

// Serializable allows you to write ...
public class Contact implements Serializable {
    // members and validations
    @NotNull(message = "This is a mandatory field")
    @Size(min=3, max=64, message = "Name must be between 3 and 64 characters")
    private String name;

    @NotNull(message = "This is a mandatory field")
    @Email(message="Invalid Email")
    private String email;
    
    @NotNull(message = "This is a mandatory field")
    @Size(min=7, message="Phone number must be at least 7 digits")
    private String phoneNumber;

    private String id;

    @Past(message="Date of Birth must nor be in the future")
    @NotNull(message="Date of Birth cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    
    @Min(value=10, message="Must be above 10 years old")
    @Max(value=100, message="Must be below 100 years old")
    private int age;

    public Contact(){
        this.id = generateId(8);
    }

    public Contact(String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.id = generateId(8);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Contact(String id, String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    private synchronized String generateId(int numOfChar) {
        SecureRandom sr = new SecureRandom(); //generate cryptographically secure rand num
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numOfChar) {
            sb.append(Integer.toHexString(sr.nextInt()));
        }
        return sb.toString().substring(0, numOfChar);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculatedAge = 0;
        if (dateOfBirth != null){
            calculatedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.dateOfBirth = dateOfBirth;
        this.age = calculatedAge;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    
}
