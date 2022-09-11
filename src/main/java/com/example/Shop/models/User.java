package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user ", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 3, message = "Минимальная длина - 3 символа")
    @Size(max = 20, message = "Максимальная длина - 20 символов")
    @Pattern(regexp = "^.*[a-zA-Z].*$", message = "Логин должен содержать хотя бы одну букву")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Логин должен состоять только из букв и цифр")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 3, message = "Минимальная длина - 3 символа")
    @Size(max = 20, message = "Максимальная длина - 20 символов")
    @Pattern(regexp = "^.*[A-Z].*$", message = "Пароль должен содержать хотя бы одну прописную букву")
    @Pattern(regexp = "^.*[a-z].*$", message = "Пароль должен содержать хотя бы одну строчную букву")
    @Pattern(regexp = "^.*\\d.*$", message = "Пароль должен содержать хотя бы одну цифру")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль должен состоять только из букв и цифр")
    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
