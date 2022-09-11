package com.example.Shop.controllers;

import com.example.Shop.models.Position;
import com.example.Shop.repo.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("")
    public String positionMain(Model model) {
        Iterable<Position> positions = positionRepository.findAll();
        model.addAttribute("positions", positions);
        return "position/main";
    }
    @GetMapping("/add")
    public String positionAddGet(Position position, Model model){
        return "position/add";
    }

    @PostMapping("/add")
    public String positionAddPost(@ModelAttribute("position") @Valid Position position,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "position/add";
        }
        positionRepository.save(position);
        return "redirect:/position";
    }




}
