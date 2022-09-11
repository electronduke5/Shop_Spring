package com.example.Shop.models;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 3, message = "Минимальная длина - 3 символа")
    @Size(max = 20, message = "Максимальная длина - 20 символов")
    @Pattern(regexp = "^[а-яА-Я0-9]+$", message = "Должность должна состоять только из букв и цифр")
    @Column(name = "position_name", unique = true, nullable = false)
    private String positionName;

    @NotNull(message = "Это обязательное поле")
    @DecimalMin(value = "1.0",message = "Оклад не может быть меньше 1.0")
    @Column(name = "salary", nullable = false)
    private double salary;

    public Position() {
    }

    public Position(String positionName, double salary) {
        this.positionName = positionName;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
