package ru.spor.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "actual", "dishes"}, name = "menus_unique_restaurant_actual_dishes_idx")})
@BatchSize(size = 200)
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @NotNull
    @Column(name = "actual", nullable = false, columnDefinition = "date default now()")
    private LocalDate actual = LocalDate.now();

    @NotBlank
    @Size(min = 1, max = 8000)
    @SafeHtml
    @Column(name = "dishes", nullable = false)
    private String dishes;

    @Column(name = "price", nullable = false)
    private Integer price;


    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getActual(), m.getDishes(), m.getPrice());
    }

    public Menu(Integer id, String dishes, Integer price) {
        this(id, LocalDate.now(), dishes, price);
    }

    public Menu(Integer id, LocalDate actual, String dishes, Integer price) {
        super(id);
        this.actual = actual;
        this.dishes = dishes;
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getActual() {
        return actual;
    }

    public void setActual(LocalDate actual) {
        this.actual = actual;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", actual=" + actual +
                ", dishes='" + dishes + '\'' +
                ", price=" + price +
                '}';
    }
}
