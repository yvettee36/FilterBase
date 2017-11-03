package cn.itcast.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yvettee on 2017/10/31.
 */
public class HtmlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        filterChain.doFilter(new MyRequest(request), response);  //request.getParameter("resume");  //<script>
    }

    class MyRequest extends HttpServletRequestWrapper {
        private HttpServletRequest request;

        public MyRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        /* 覆盖需要增强的getParameter方法
         * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
         */
        @Override
        public String getParameter(String name) {

            String value = this.request.getParameter(name);
            if (value == null) {
                return null;
            }
            //调用filter转义value中的html标签
            return filter(value);
        }

        public String filter(String message) {

            if (message == null)
                return (null);

            char content[] = new char[message.length()];
            message.getChars(0, message.length(), content, 0);
            StringBuffer result = new StringBuffer(content.length + 50);
            for (int i = 0; i < content.length; i++) {
                switch (content[i]) {
                    case '<':
                        result.append("&lt;");
                        break;
                    case '>':
                        result.append("&gt;");
                        break;
                    case '&':
                        result.append("&amp;");
                        break;
                    case '"':
                        result.append("&quot;");
                        break;
                    default:
                        result.append(content[i]);
                }
            }
            return (result.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
