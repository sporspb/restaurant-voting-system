package ru.spor.topjava.graduation.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spor.topjava.graduation.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    List<Vote> findAll(Sort sort);

    List<Vote> findAllByUserId(int userId);

    List<Vote> findByUserIdAndDateBetween(int userId, @NotNull LocalDate dateFrom, @NotNull LocalDate dateTo);

    List<Vote> findByRestaurantId(int restaurantId);

    List<Vote> findByRestaurantIdAndDateBetween(int restaurantId, LocalDate dateFrom, LocalDate dateTo);
}
