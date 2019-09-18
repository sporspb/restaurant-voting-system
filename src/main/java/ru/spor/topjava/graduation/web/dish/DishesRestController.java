package ru.spor.topjava.graduation.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spor.topjava.graduation.model.Dish;
import ru.spor.topjava.graduation.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.spor.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNew;

public class DishesRestController {

    private static final String REST_URL = "/dishes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService service;

    public DishesRestController(DishService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        log.info("get all dishes");
        return service.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable Integer id) {
        log.info("get dish with id={}", id);
        return service.get(id);
    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Dish> createWithNewUri(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = service.create(dish);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void update(@Valid @RequestBody Dish dish, @PathVariable Integer id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        service.update(dish);
    }
}
