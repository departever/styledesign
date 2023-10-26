package com.example.controller;

import com.example.entity.ContactPerson;
import com.example.entity.User;
import com.example.listener.HttpSessionListener;
import com.example.service.ContactPersonService;
import com.example.service.UserService;
import com.example.util.CookieUtil;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactPersonService contactPersonService;

    @PostMapping("/toLogin")
    public String login(
            @RequestParam("number") String number,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response){
        if(ObjectUtils.isEmpty(number)||ObjectUtils.isEmpty(password)){
            redirectAttributes.addFlashAttribute("msg","账号或密码不能为空！");
            return "redirect:login";
        }
        Integer flag = userService.checkLogin(number,password);
        if(flag == 1){
            User user = userService.getUserByNumber(number);
            session.setAttribute("number", user.getNumber());
            session.setAttribute("name",user.getName());
            session.setAttribute("password",user.getPassword());
            List<Cookie> cookies = CookieUtil.loginUser(number, password);
            cookies.forEach(cookie -> {
                response.addCookie(cookie);
            });
            return "redirect:index";
        }else if(flag == 0){
            redirectAttributes.addFlashAttribute("msg","密码错误！登陆失败!");
            return "redirect:login";
        }else {
            redirectAttributes.addFlashAttribute("msg","该账号不存在！登陆失败!");
            return "redirect:login";
        }
    }

    @GetMapping("/handleLogin")
    public String handleLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg","没有权限，请先登陆");
        return "redirect:login";
    }

    @GetMapping("/online")
    @ResponseBody
    public Result online() {
        return Result.ok(HttpSessionListener.getOnline());
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session,
                           RedirectAttributes redirectAttributes){
        if (session != null) {
            session.removeAttribute("number");
            session.removeAttribute("name");
            session.removeAttribute("password");
            session.invalidate();
        }
        redirectAttributes.addFlashAttribute("msg", "退出登录成功！");
        return "redirect:login";
    }

    @GetMapping("/view/list")
    public String listContactPersons(Model model){
        List<ContactPerson> contactPersonList = this.contactPersonService.listContactPersons();
        model.addAttribute("contactPersonList",contactPersonList);
        return "/view/list";
    }

    @GetMapping("/view/edit/{id}")
    public String updateById(@PathVariable Integer id,
                             Model model){
        ContactPerson contactPerson = this.contactPersonService.getContactPersonById(id);
        model.addAttribute("contactperson",contactPerson);
        return "/view/edit";
    }

    @GetMapping("/view/toDelete/{id}")
    @ResponseBody
    public Result deleteById(@PathVariable Integer id){
        this.contactPersonService.deleteById(id);
        return Result.ok();
    }

    @PostMapping("/view/toEdit")
    @ResponseBody
    public Result updateContactPerson(ContactPerson contactPerson){
        this.contactPersonService.updateContactPerson(contactPerson);
        return Result.ok();
    }

    @PostMapping("/view/toAdd")
    @ResponseBody
    public Result addContactPerson(ContactPerson contactPerson){
        this.contactPersonService.addContactPerson(contactPerson);
        return Result.ok();
    }
}
