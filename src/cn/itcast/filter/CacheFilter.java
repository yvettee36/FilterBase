package cn.itcast.filter;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yvettee on 2017/10/24.
 */
public class CacheFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取用户想访问的资源
        String uri = request.getRequestURI();

        //2.获取该资源的访问时间
        int expires = 0;
        if (uri.endsWith(".jpg")) {
            expires = Integer.parseInt(this.config.getInitParameter("jpg"));
        } else if (uri.endsWith(".css")) {
            expires = Integer.parseInt(this.config.getInitParameter("css"));
        } else {
            expires = Integer.parseInt(this.config.getInitParameter("js"));
        }

        response.setDateHeader("Expires", System.currentTimeMillis() + expires * 60 * 1000);
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
