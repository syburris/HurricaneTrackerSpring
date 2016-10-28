package com.YoungMoney.controllers;

import com.YoungMoney.entities.Hurricane;
import com.YoungMoney.entities.Like;
import com.YoungMoney.entities.User;
import com.YoungMoney.services.HurricaneRepo;
import com.YoungMoney.services.LikeRepository;
import com.YoungMoney.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevenburris on 10/28/16.
 */
@RestController
public class HurricaneTrackerRestController {

    @Autowired
    HurricaneRepo hurricanes;

    @Autowired
    UserRepo users;

    @Autowired
    LikeRepository likes;

    @RequestMapping(path = "/hurricanes.json", method = RequestMethod.GET)
    public Iterable<Hurricane> getHurricanes() {
        return hurricanes.findAll();
    }

    @RequestMapping(path = "/liked-hurricanes.json", method = RequestMethod.GET)
    public List<Hurricane> getLikedHurricanes(HttpSession session) {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByName(name);
        ArrayList<Hurricane> likedHurricanes = new ArrayList<>();
        for (Like like : likes.findByUser(user)) {
            likedHurricanes.add(like.hurricane);
        }
        return likedHurricanes;
    }
}
