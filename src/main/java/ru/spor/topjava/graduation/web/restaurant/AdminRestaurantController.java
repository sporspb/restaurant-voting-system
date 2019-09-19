package ru.spor.topjava.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.service.DishService;
import ru.spor.topjava.graduation.service.RestaurantService;
import ru.spor.topjava.graduation.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;

import static ru.spor.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(
        value = AdminRestaurantController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    private final DishService dishService;

    public AdminRestaurantController(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    @PostMapping()
    public ResponseEntity<Restaurant> add(@Valid @RequestBody Restaurant restaurant) {
        log.info("add restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant newRestaurant = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}")
                .buildAndExpand(newRestaurant.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @PutMapping(value = "/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Restaurant restaurant) throws NotFoundException {
        log.info("update restaurant {} with id {}", restaurant, restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{restaurantId}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) throws NotFoundException {
        log.info("delete restaurant {}", restaurantId);
        restaurantService.delete(restaurantId);
    }
}
