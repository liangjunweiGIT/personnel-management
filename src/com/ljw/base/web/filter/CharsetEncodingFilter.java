package com.ljw.base.web.filter;

import com.ljw.base.util.PropertiesUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/22 13:57
 */
public class CharsetEncodingFilter implements Filter {
    private static String ENCODING;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 设置字符编码链锁
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // 接收web.xml配置文件中的初始参数
        ENCODING = config.getInitParameter("CharsetEncoding");
    }

    @Override
    public void destroy() {
    }
}
