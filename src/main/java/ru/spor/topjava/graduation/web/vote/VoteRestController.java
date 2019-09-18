package ru.spor.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spor.topjava.graduation.model.Vote;
import ru.spor.topjava.graduation.service.VoteService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class VoteRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    @Autowired
    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/votes", produces = APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        log.info("get all votes");
        return service.getAll(Sort.by(Sort.Direction.ASC, "date"));
    }
}
