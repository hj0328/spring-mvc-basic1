package hello.servlet.web.frontcontolller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface Controller {
    String process(Map<String, String> paramMap, Map<String, Object> model)
            throws ServletException, IOException;
}
