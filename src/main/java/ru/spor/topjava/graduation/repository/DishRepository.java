package ru.spor.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spor.topjava.graduation.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Override
    Optional<Dish> findById(Integer id);

    List<Dish> getByRestaurant_Id_OrderByDateDesc(Integer restaurant_id);

    List<Dish> getByRestaurant_IdAndDateBetweenOrderByDateDesc(Integer restaurantId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM Dish m JOIN FETCH m.restaurant WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    Dish getWithRestaurant(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Dish getByIdAndRestaurantId(@Param("id") int id, @Param("restaurantId") int restaurantId);
}