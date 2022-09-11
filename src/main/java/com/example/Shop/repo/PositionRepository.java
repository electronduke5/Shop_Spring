package com.example.Shop.repo;

import com.example.Shop.models.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionRepository extends CrudRepository<Position, Long> {

    List<Position>findUserByPositionNameContains(String positionName);
    List<Position>findUserByPositionName(String positionName); //Точный поиск
}
