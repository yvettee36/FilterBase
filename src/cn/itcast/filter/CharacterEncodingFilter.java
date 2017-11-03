package cn.itcast.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yvettee on 2017/10/24.
 */
public class CharacterEncodingFilter implements Filter {
    private FilterConfig config;
    private String defaultConfig = "UTF-8";//定义一个缺省的字符集

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String charset = this.config.getInitParameter("charset");
        if (charset == null) {
            charset = defaultConfig;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        response.setContentType("text/html;charset = " + charset);

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
