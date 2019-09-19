package ru.spor.topjava.graduation.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spor.topjava.graduation.model.Restaurant;
import ru.spor.topjava.graduation.model.Vote;
import ru.spor.topjava.graduation.repository.RestaurantRepository;
import ru.spor.topjava.graduation.repository.UserRepository;
import ru.spor.topjava.graduation.repository.VoteRepository;
import ru.spor.topjava.graduation.util.exception.VotingTimeIsOutException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.spor.topjava.graduation.model.Vote.DECISION_TIME;
import static ru.spor.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Vote vote(Integer userId, Integer restaurantId, LocalDate date, LocalTime time) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        Vote vote = getForUserAndDate(userId, date);
        if (vote == null) {
            Restaurant restaurant = restaurantRepository.getOne(restaurantId);
            return voteRepository.save(new Vote(null, date, restaurant, userRepository.getOne(userId)));
        } else {
            if (time.isBefore(DECISION_TIME)) {
                if (restaurantId.equals(vote.getRestaurant().getId())) {
                    return vote;
                }
                vote.setRestaurant(restaurantRepository.getOne(restaurantId));
                return voteRepository.save(vote);
            } else throw new VotingTimeIsOutException("Voting time is over");
        }
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.findById(id).orElse(null), id);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
        voteRepository.delete(id);
    }

    public List<Vote> getAllForUser(Integer userId) {
        return voteRepository.findAllByUserId(userId);
    }

    public List<Vote> getAllForRestaurant(Integer restaurantId) {
        return voteRepository.findByRestaurantId(restaurantId);
    }

    public Vote getForUserAndDate(Integer userId, LocalDate date) {
        List<Vote> votes = voteRepository.findByUserIdAndDateBetween(userId, date, date);
        return votes.isEmpty() ? null : votes.get(votes.size() - 1);
    }

    public List<Vote> getForRestaurantAndDate(Integer restaurantId, LocalDate date) {
        return voteRepository.findByRestaurantIdAndDateBetween(restaurantId, date, date);
    }

    public List<Vote> getAll(Sort sort) {
        return voteRepository.findAll(sort);
    }

}
