package com.MySystem.util;

import com.MySystem.pojo.UserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转换成request对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取session对象
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        //获取当前的url
        String url = request.getRequestURI() + "";
//        System.out.println("url=" + url);
        List<String> list = puturl();

        //判断user是否为空 vertify(list, url)
        if (vertify(list, url)) {//检验路径是否存在
            if (user == null) {
                //回到登录页面
                request.getRequestDispatcher("/admin/loginPage").forward(servletRequest, servletResponse);
            } else {
                //不拦截，放行
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            //不拦截，放行
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    public boolean vertify(List<String> list, String url) {//验证用户输入的url是否存在
        for (String s : list) {
            if (s.equals(url)) {
                return true;
            }
        }
        return false;
    }

    public List<String> puturl() {
        List<String> list = new ArrayList<>();
        list.add("/index/homePage");
        list.add("/index/account");
        list.add("/index/changeName");
        list.add("/index/changeEmail");

        list.add("/goods/list");
        list.add("/goods/insertPage");
        list.add("/goods/outlist");
        list.add("/goods/outInsertPage");
        list.add("/goods/outUpdatePage");

        list.add("/store/list");
        list.add("/store/insertPage");
        list.add("/store/histogram");

        list.add("/super/list");
        list.add("/super/insertPage");

        list.add("/supply/list");
        list.add("/supply/insertPage");

        list.add("/ware/list");
        list.add("/ware/insertPage");

        list.add("/sale/list");
        list.add("/sale/insertPage");
        list.add("/sale/lineChart");
        list.add("/sale/china");

        list.add("/analyse/analyChart");
//        list.add("/index/welcome");


        return list;
    }
}
