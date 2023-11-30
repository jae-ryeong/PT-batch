package com.springbatch.project.springbatch.controller.pass;

import com.springbatch.project.springbatch.service.pass.Pass;
import com.springbatch.project.springbatch.service.pass.PassService;
import com.springbatch.project.springbatch.service.user.User;
import com.springbatch.project.springbatch.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/passes")
public class PassViewController {
    private final UserService userService;
    private final PassService passService;

    @GetMapping
    public ModelAndView getPasses(@RequestParam("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();

        List<Pass> passes = passService.getPasses(userId);
        User user = userService.getUser(userId);
        modelAndView.addObject("passes", passes);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pass/index");

        return modelAndView;
    }
}
