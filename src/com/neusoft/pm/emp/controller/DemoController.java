package com.neusoft.pm.emp.controller;

import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;
import com.neusoft.pm.common.enums.EnglishLevelEnum;
import com.neusoft.pm.emp.service.EmpService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 14:30
 */
@Bean
@WebServlet(urlPatterns = "demo")
public class DemoController extends HttpServlet {

    @Autowired
    private EmpService empService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //将外语水平列表传到jsp
        request.setAttribute("englishLevelList", EnglishLevelEnum.getEnumList());
        request.getRequestDispatcher("****.jsp").forward(request, response);
    }
}
