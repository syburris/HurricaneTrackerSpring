package com.YoungMoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by stevenburris on 10/21/16.
 */
@Controller
public class HurricaneTrackerController {
    @Autowired
    HurricaneRepo hurricanes;


    //home page,
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, Hurricane.Category category, String search) {
        List<Hurricane> hList;
        if (category != null) {
            hList = hurricanes.findByCategory(category);
        }
        else if(search != null) {
            hList = hurricanes.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(search,search);
        }
        else {
            hList = (List<Hurricane>) hurricanes.findAll();
        }
        model.addAttribute("hurricanes", hList);
        return "home";
    }

    @RequestMapping(path = "/create-hurricane", method = RequestMethod.POST)
    public String addHurricane(String hName, String hLocation, Hurricane.Category hCat, String hImage) {
        Hurricane h = new Hurricane(hName, hLocation, hCat, hImage);
        hurricanes.save(h);
        return "redirect:/";
    }

//    @RequestMapping(path = /category)

//    log in route
//    @RequestMapping(path = "/login", method = RequestMethod.POST)
//    public String login() {
//
//    }
}
