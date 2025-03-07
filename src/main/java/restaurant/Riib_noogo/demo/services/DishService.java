package restaurant.Riib_noogo.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.Riib_noogo.demo.models.Dish;
import restaurant.Riib_noogo.demo.repositories.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Optional<Dish> updateDish(Long id, Dish dishDetails) {
        return dishRepository.findById(id).map(dish -> {
            dish.setName(dishDetails.getName());
            dish.setPrice(dishDetails.getPrice());
            dish.setDescription(dishDetails.getDescription());
            dish.setCategory(dishDetails.getCategory());
            dish.setAllergens(dishDetails.getAllergens());
            dish.setStatus(dishDetails.getStatus());
            return dishRepository.save(dish);
        });
    }

    public boolean deleteDish(Long id) {
        return dishRepository.findById(id).map(dish -> {
            dishRepository.delete(dish);
            return true;
        }).orElse(false);
    }
}

