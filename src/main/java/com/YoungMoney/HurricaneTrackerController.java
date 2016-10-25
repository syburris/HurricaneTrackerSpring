package com.YoungMoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by stevenburris on 10/21/16.
 */
@Controller
public class HurricaneTrackerController {
    @Autowired
    HurricaneRepo hurricanes;

    @Autowired
    UserRestRepo users;


//    how to run methods as the controller is constructed shown below
    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException {
        User defaultUser = new User("Steven", PasswordStorage.createHash("Young"));
        if (users.findFirstByName(defaultUser.name) == null) {
        users.save(defaultUser);
        }
    }


    //home page,
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, Hurricane.Category category, String search, HttpSession session) {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByName(name);

        List<Hurricane> hList;
        if (category != null) {
            hList = hurricanes.findByCategory(category);
        }
        else if(search != null) {
            hList = hurricanes.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(search,search);
        }
        else {
            hList = hurricanes.findByOrderByDateDesc();
        }

        for (Hurricane h : hList) {
            h.isMe = h.user.name.equals(name);
        }

        model.addAttribute("hurricanes", hList);
        model.addAttribute("user",user);
        model.addAttribute("now", LocalDate.now());
        return "home";
    }

    @RequestMapping(path = "/create-hurricane", method = RequestMethod.POST)
    public String addHurricane(String hName, String hLocation, Hurricane.Category hCat,String date, String hImage, HttpSession session) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByName(name);
        if (user == null) {
            throw new Exception("Not logged in.");
        }
        Hurricane h = new Hurricane(hName, hLocation, hCat, hImage, LocalDate.parse(date), user);
        hurricanes.save(h);
        return "redirect:/";
    }

//    log in route
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session) throws Exception {
    User user = users.findFirstByName(username);
    if (user == null) {
        user = new User(username,PasswordStorage.createHash(password));
        users.save(user);
    }
    else if(!PasswordStorage.verifyPassword(password, user.password)) {
        throw new Exception("Wrong password!");
    }
    session.setAttribute("username",username);
    return "redirect:/";

    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-hurricane", method = RequestMethod.POST)
    public String delete(HttpSession session, int id) throws Exception {
        if (!validateUser(session,id)) {
            throw new Exception("Uh uh uh!");
        }
        hurricanes.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-hurricane", method = RequestMethod.GET)
    public String editPage(Model model, HttpSession session, int id) throws Exception{
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByName(name);
        Hurricane h = hurricanes.findOne(id);
        if (user == null) {
            throw new Exception("Not logged in!");
        }
        model.addAttribute("user", user);
        model.addAttribute("hurricane", h);
        return "edit-hurricane";
    }

    @RequestMapping(path = "/edit-hurricane", method = RequestMethod.POST)
    public String edit(HttpSession session, String hName, String hLocation, Hurricane.Category hCat, String hImage, Integer id) throws Exception{
        if (!validateUser(session,id)) {
            throw new Exception("Uh uh uh!");
        }
        Hurricane h = hurricanes.findOne(id);
        h.setCategory(hCat);
        h.setImage(hImage);
        h.setLocation(hLocation);
        h.setName(hName);
        hurricanes.save(h);
        return "redirect:/";
    }


    public boolean validateUser(HttpSession session, int id) {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByName(name);
        Hurricane h = hurricanes.findOne(id);
        return user != null && h !=null && user.name.equals(h.user.name);
    }

}
