package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer", unique = true, nullable = false)
    private Long id;

    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Фамилия должна состоять только из букв")
    private String surname;

    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Имя должно состоять только из букв")
    private String name;

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^\\+7\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", message = "Неправильный формат номера телефона")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    public Customer() {
    }

    public Customer(String surname, String name, String phoneNumber, User user) {
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
