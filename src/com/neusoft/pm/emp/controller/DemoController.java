package com.neusoft.pm.emp.controller;

import com.neusoft.pm.common.enums.EnglishLevelEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 14:30
 */
public class DemoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //将外语水平列表传到jsp
        request.setAttribute("englishLevelList", EnglishLevelEnum.getEnumList());
        request.getRequestDispatcher("****.jsp").forward(request, response);
    }
}
