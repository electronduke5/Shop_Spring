package com.example.Shop.repo;

import com.example.Shop.models.Employee;
import com.example.Shop.models.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findEmployeeBySurnameContains(String surname);
    List<Employee>findEmployeeBySurname(String surname); //Точный поиск
}
