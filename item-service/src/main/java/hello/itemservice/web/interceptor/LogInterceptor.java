package hello.itemservice.web.interceptor;

import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(SessionConst.LOG_ID, uuid);

        // HandlerMethod: @RequestMapping 사용 핸들러(컨트롤러)
        // ResourceHttpRequestHandler: 정적 리소스 요청을 처리하는 핸들러
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("@RequestMapping handler data={}", handler);
        }
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 호출
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        String logId = (String) request.getAttribute(SessionConst.LOG_ID);
        log.info("RESPONSE [{}][{}]", logId, requestURL);
        if (ex != null) {
            log.error("afterCompletion error!", ex);
        }
    }
}
