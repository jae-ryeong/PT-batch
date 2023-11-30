package com.springbatch.project.springbatch.controller.admin;

import com.springbatch.project.springbatch.service.packaze.PackageService;
import com.springbatch.project.springbatch.service.pass.BulkPassService;
import com.springbatch.project.springbatch.service.statistics.StatisticsService;
import com.springbatch.project.springbatch.service.user.UserGroupMappingService;
import com.springbatch.project.springbatch.util.LocalDateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin")
public class AdminViewController {
    private final BulkPassService bulkPassService;
    private final PackageService packageService;
    private final UserGroupMappingService userGroupMappingService;
    private final StatisticsService statisticsService;

    @GetMapping
    public ModelAndView home(ModelAndView modelAndView, @RequestParam("to") String toString) {
        LocalDateTime to = LocalDateTimeUtils.parseDate(toString);

        modelAndView.addObject("chartData", statisticsService.makChartData(to));
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

    @GetMapping("/bulk-pass")
    public ModelAndView registerBulkPass(ModelAndView modelAndView) {
        // bulk pass 목록 조회
        modelAndView.addObject("bulkPasses", bulkPassService.getAllBulkPasses());
        // bulk pass를 등록할 때 필요한 package 값을 위해 모든 package 조회
        modelAndView.addObject("packages", packageService.getAllPackages());
        // bulk pass를 등록할 때 필요한 userGroupId 값을 위해 모든 userGroupId를 조회
        modelAndView.addObject("userGroupIds", userGroupMappingService.getAllUserGroupIds());
        // bulk pass request를 제공
        modelAndView.addObject("request", new BulkPassRequest());
        modelAndView.setViewName("admin/bulk-pass");

        return modelAndView;
    }

    @PostMapping("/bulk-pass")
    public String addBulkPass(@ModelAttribute("request") BulkPassRequest request, Model model) {
        bulkPassService.addBulkPass(request);
        return "redirect:/admin/bulk-pass";
    }
}
