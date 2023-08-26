package hello.itemservice.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 로그인이 되어있으면 doFilter, 그렇지 않으면 홈으로 리다이렉트

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증체크 필터 실행 {}", requestURI);

            if (!isLoginCheckPath(requestURI)) {
                HttpSession session = httpRequest.getSession();
                if (session == null) {
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     *  화이트리스트의 경우 인증 체크 x
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
