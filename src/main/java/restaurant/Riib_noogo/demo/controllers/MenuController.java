package restaurant.Riib_noogo.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.Riib_noogo.demo.dto.MenuDTO;
import restaurant.Riib_noogo.demo.models.Menu;
import restaurant.Riib_noogo.demo.services.MenuService;
import restaurant.Riib_noogo.demo.models.Dish;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<MenuDTO> getAllMenus() {
        return menuService.getAllMenus().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id)
                .map(menu -> ResponseEntity.ok(mapToDTO(menu)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody MenuDTO menuDTO) {
        Menu menu = mapToEntity(menuDTO);
        MenuDTO createdMenu = mapToDTO(menuService.saveMenu(menu));
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuDTO menuDTO) {
        return menuService.updateMenu(id, mapToEntity(menuDTO))
                .map(menu -> ResponseEntity.ok(mapToDTO(menu)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private MenuDTO mapToDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName(menu.getName());
        menuDTO.setDescription(menu.getDescription());
        menuDTO.setPrice(menu.getPrice());
        menuDTO.setDishIds(menu.getDishes().stream()
                .map(Dish::getId)
                .collect(Collectors.toList()));
        return menuDTO;
    }

    private Menu mapToEntity(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu.setPrice(menuDTO.getPrice());
        // Assuming you have a method to set dishes by IDs
        // menu.setDishes(dishService.findAllByIds(menuDTO.getDishIds()));
        return menu;
    }
}
