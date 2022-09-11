package com.example.Shop.models;

import com.example.Shop.validators.DateDifferent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Фамилия должна состоять только из букв")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Имя должно состоять только из букв")
    @Column(name = "name", nullable = true)
    private String name;

    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Отчество должно состоять только из букв")
    private String patronymic;

    @NotNull(message = "Это обязательное поле")
    @Past(message = "Дата рождения не может быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@DateDifferent(message = "Сотруднику должно быть не менее 18 лет", minYears = 18)
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id_position")
    private Position position;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    public Employee() {
    }

    public Employee(String surname, String name, String patronymic, Date dateOfBirth, Position position, User user) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthToString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        return sdfDate.format(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
