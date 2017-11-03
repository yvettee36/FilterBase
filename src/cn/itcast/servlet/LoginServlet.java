package cn.itcast.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.BusinessService;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by yvettee on 2017/10/24.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        BusinessService service = new BusinessService();
        User user = service.login(userName, passWord);
        if (user == null) {
            request.setAttribute("messgae", "用户名或密码错误！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }

        //用户存在，存一个用户登录标记在session里
        request.getSession().setAttribute("user", user);

        //得到cookie的失效时间
        int expiresTime = Integer.parseInt(request.getParameter("time"));

        //给客户机发送自动登录的cookie
        Cookie cookie = makeCookie(user, expiresTime);

        response.addCookie(cookie);
        response.sendRedirect("/index.jsp");
    }

    //给客户机发送自动登录的cookie的值为：username:md5(password)
    //同时给cookie的值里面带一个失效时间(expiresTime)，，即cookie的值为：username:expirestime:md5(password)
    public Cookie makeCookie(User user, int expiresTime) {
        long currentTime = System.currentTimeMillis();
        String cookieValue = user.getUserName() + ":" + (currentTime + expiresTime * 1000) + ":" + md5(user.getUserName(), user.getPassWord(), (currentTime + expiresTime * 1000));
        Cookie cookie = new Cookie("autoLogin", cookieValue);
        cookie.setMaxAge(expiresTime);
        cookie.setPath("/");
        return cookie;
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

}
