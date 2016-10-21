package com.YoungMoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by stevenburris on 10/21/16.
 */
@Controller
public class HurricaneTrackerController {
    @Autowired
    HurricaneRepo hurricanes;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        Iterable<Hurricane> hlist = hurricanes.findAll();
        model.addAttribute("hurricanes", hlist);
        return "home";
    }

    @RequestMapping(path = "/create-hurricane", method = RequestMethod.POST)
    public String addHurricane(String hName, String hLocation, Hurricane.Category hCat, String hImage) {
        Hurricane h = new Hurricane(hName, hLocation, hCat, hImage);
        hurricanes.save(h);
        return "redirect:/";
    }



//    log in route
//    @RequestMapping(path = "/login", method = RequestMethod.POST)
//    public String login() {
//
//    }
}
