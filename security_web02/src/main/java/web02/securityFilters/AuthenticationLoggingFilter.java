package web02.securityFilters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dkw
 */
@Component
@Slf4j
public class AuthenticationLoggingFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestId = httpRequest.getHeader("Request-id");
        // 在请求头中获取到requestId后将requestId打印在控制台中
        log.info("Successfully authenticated request with id:{}" , requestId);
        filterChain.doFilter(request,response);
    }
}
