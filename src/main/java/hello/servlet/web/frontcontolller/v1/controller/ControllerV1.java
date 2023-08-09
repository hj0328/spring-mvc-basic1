package hello.servlet.web.frontcontolller.v1.controller;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public interface ControllerV1 {
    String process(Map<String, String> paramMap, Map<String, Object> model)
            throws ServletException, IOException;
}
