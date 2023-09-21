package nl.workingtalent.backend.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
 
    private final Logger log = LoggerFactory.getLogger(CorsFilter.class);
 
    public CorsFilter() {
        log.info("SimpleCORSFilter init");
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
 
//        if (request.getHeader("Origin") != null) {
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        }
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, pragma, cache-control, authorization, x-xsrf-token");
        
        if (!request.getMethod().equals("OPTIONS")) 
        	chain.doFilter(req, res);
    }
 
    @Override
    public void init(FilterConfig filterConfig) {
    }
 
    @Override
    public void destroy() {
    }
}