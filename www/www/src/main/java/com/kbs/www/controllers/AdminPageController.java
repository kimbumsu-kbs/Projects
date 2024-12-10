package com.kbs.www.controllers;

import com.kbs.www.entities.AdminPageEntity;
import com.kbs.www.entities.UserEntity;
import com.kbs.www.services.AdminPageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class AdminPageController {

    private final AdminPageService adminPageService;

    @Autowired
    public AdminPageController(AdminPageService adminPageService) {
        this.adminPageService = adminPageService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/adminIndex");
        return modelAndView;
    }

    @RequestMapping(value = "write/" , method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite() {
//        Boolean result = this.adminPageService.write(adminPage);
        ModelAndView modelAndView = new ModelAndView();
//        JSONObject response = new JSONObject();
//        response.put("result", result);
        modelAndView.setViewName("admin/adminWrite");
        return modelAndView;
    }

    @RequestMapping(value = "user/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser() {
        UserEntity[] users = this.adminPageService.read();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/adminUser");
        return modelAndView;
    }

    @RequestMapping(value = "write/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> postWrite(@RequestParam("title") String title,
                                       @RequestParam("location") String location,
                                       @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       @RequestParam("description") String description,
                                       @RequestParam("coverData") MultipartFile coverFile) {
        // 날짜 문자열 형식 정의 (예시: yyyy-MM-dd HH:mm:ss)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // startDate 와 endDate 문자열을 LocalDateTime 으로 변환
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        // AdminPageEntity 생성 및 속성 설정
        AdminPageEntity adminPage = new AdminPageEntity();
        adminPage.setTitle(title);
        adminPage.setLocation(location);
        adminPage.setStartDate(startDateTime); // LocalDate Time 으로 설정
        adminPage.setEndDate(endDateTime);     // LocalDate Time 으로 설정
        adminPage.setDescription(description);

        Boolean result = this.adminPageService.write(adminPage, coverFile);
        System.out.println(result);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("result", result.toString());
            return ResponseEntity.ok(response);  // 성공 시 JSON 응답 반환
        } else {
            response.put("result", result.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // 실패 시 JSON 응답 반환
        }
    }
}
