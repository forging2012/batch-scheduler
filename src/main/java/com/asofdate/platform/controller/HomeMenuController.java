package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.entity.HomeMenuEntity;
import com.asofdate.platform.service.HomeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/5/15.
 */
@RestController
public class HomeMenuController {
    @Autowired
    public HomeMenuService homeMenuService;

    @RequestMapping(value = "/v1/auth/main/menu")
    @ResponseBody
    public List<HomeMenuEntity> homeMenu(HttpServletRequest request) {
        String TypeId = request.getParameter("TypeId");
        String Id = request.getParameter("Id");

        String username = JwtService.getConnUser(request).getUserId();
        List<HomeMenuEntity> homeMenusModel = homeMenuService.findAuthedMenus(username, TypeId, Id);
        return homeMenusModel;
    }
}
