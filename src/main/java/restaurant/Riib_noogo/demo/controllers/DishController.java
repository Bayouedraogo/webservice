package restaurant.Riib_noogo.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.Riib_noogo.demo.dto.DishDTO;
import restaurant.Riib_noogo.demo.models.Dish;
import restaurant.Riib_noogo.demo.services.DishService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public List<DishDTO> getAllDishes() {
        return dishService.getAllDishes().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        return dishService.getDishById(id)
                .map(dish -> ResponseEntity.ok(mapToDTO(dish)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DishDTO createDish(@Valid @RequestBody DishDTO dishDTO) {
        Dish dish = mapToEntity(dishDTO);
        return mapToDTO(dishService.saveDish(dish));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @Valid @RequestBody DishDTO dishDTO) {
        return dishService.updateDish(id, mapToEntity(dishDTO))
                .map(dish -> ResponseEntity.ok(mapToDTO(dish)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        return dishService.deleteDish(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private DishDTO mapToDTO(Dish dish) {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName(dish.getName());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setCategory(dish.getCategory());
        dishDTO.setAllergens(dish.getAllergens());
        dishDTO.setStatus(dish.getStatus());
        return dishDTO;
    }

    private Dish mapToEntity(DishDTO dishDTO) {
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setPrice(dishDTO.getPrice());
        dish.setDescription(dishDTO.getDescription());
        dish.setCategory(dishDTO.getCategory());
        dish.setAllergens(dishDTO.getAllergens());
        dish.setStatus(dishDTO.getStatus());
        return dish;
    }
}
