package cn.itcast.filter;

import cn.itcast.domain.User;
import cn.itcast.service.BusinessService;
import sun.misc.BASE64Encoder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by yvettee on 2017/10/24.
 */
public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {//登录继续执行下去
            filterChain.doFilter(request, response);
            return;
        }

        //没有登录，执行自动登录逻辑

        //1.获得用户cookie
        Cookie autoLoginCookie = null;
        Cookie cookies[] = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("autoLogin")) {
                autoLoginCookie = cookies[i];
            }
        }

        if (autoLoginCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //用户带了自动登录cookie，先检查cookie的有效期
        String values[] = autoLoginCookie.getValue().split("\\:");
        if (values.length != 3) {
            filterChain.doFilter(request, response);
            return;
        }

        long expiresTime = Long.parseLong(values[1]);
        if (System.currentTimeMillis() > expiresTime) {
            filterChain.doFilter(request, response);
            return;
        }

        //再检查cookie的有效性,代表cookie时间有效
        String userName = values[0];
        String client_md5 = values[2];

        BusinessService service = new BusinessService();
        user = service.findUser(userName);

        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String server_md5 = md5(user.getUserName(), user.getPassWord(), expiresTime);
        if (server_md5.equals(client_md5)) {
            filterChain.doFilter(request, response);
            return;
        }

        request.getSession().setAttribute("user", user);//执行登录
        filterChain.doFilter(request, response);
    }

    private String md5(String userName, String passWord, long expiresTime) {
        try {
            String value = passWord + ":" + expiresTime + ":" + userName;
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(value.getBytes());
            BASE64Encoder encode = new BASE64Encoder();
            return encode.encode(md5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }
}
