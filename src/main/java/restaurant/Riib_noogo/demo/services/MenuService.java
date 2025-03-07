package restaurant.Riib_noogo.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.Riib_noogo.demo.models.Menu;
import restaurant.Riib_noogo.demo.repositories.MenuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Optional<Menu> updateMenu(Long id, Menu menuDetails) {
        return menuRepository.findById(id).map(menu -> {
            menu.setName(menuDetails.getName());
            menu.setDescription(menuDetails.getDescription());
            menu.setPrice(menuDetails.getPrice());
            return menuRepository.save(menu);
        });
    }

    public boolean deleteMenu(Long id) {
        return menuRepository.findById(id).map(menu -> {
            menuRepository.delete(menu);
            return true;
        }).orElse(false);
    }
}
