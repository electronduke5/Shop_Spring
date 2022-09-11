package com.example.Shop.controllers;

import com.example.Shop.models.Employee;
import com.example.Shop.models.Position;
import com.example.Shop.models.User;
import com.example.Shop.repo.EmployeeRepository;
import com.example.Shop.repo.PositionRepository;
import com.example.Shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Constraint;
import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String employeeMain(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee/main";
    }
    @GetMapping("/add")
    public String employeeAddGet(Employee employee, Model model){
        Iterable<Position> positions = positionRepository.findAll();
        model.addAttribute("positions", positions);
        return "employee/add";
    }

    @PostMapping("/add")
    public String employeeAddPost(@ModelAttribute("employee") @Valid Employee employee,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "employee/add";
        }
        User user = new User(employee.getUser().getLogin(), employee.getUser().getPassword());
        userRepository.save(user);
        employee.setUser(user);
        employeeRepository.save(employee);
        return "redirect:/employee";
    }
}
