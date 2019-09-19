package ru.spor.topjava.graduation.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spor.topjava.graduation.model.Menu;
import ru.spor.topjava.graduation.service.MenuService;
import ru.spor.topjava.graduation.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.spor.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {

    static final String REST_URL = "/rest/menu";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService menuService;

    @Autowired

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Menu> getAll() {
        log.info("get all menus");
        return menuService.getAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        log.info("get menu with id={}", id);
        return menuService.get(id);
    }

    @GetMapping(params = "date", produces = APPLICATION_JSON_VALUE)
    public List<Menu> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menus by date {}", date);
        return menuService.getAllByDate(date);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu {}", id);
        menuService.delete(id);
    }

    @PostMapping
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        Menu created = menuService.createFromTo(menuTo);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable Integer id) {
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menuTo, id);
        menuService.updateFromTo(menuTo);
    }
}
