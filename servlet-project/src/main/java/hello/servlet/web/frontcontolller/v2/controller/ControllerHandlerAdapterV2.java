package hello.servlet.web.frontcontolller.v2.controller;

import hello.servlet.web.frontcontolller.ModelView;
import hello.servlet.web.frontcontolller.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ControllerHandlerAdapterV2 implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerV2;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {

        ControllerV2 controller = (ControllerV2) handler;
        HashMap<String, String> paramMap = createParamMap(request);
        ModelView modelView = controller.process(paramMap);
        return modelView;
    }

    private HashMap<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(name -> paramMap.put(name, request.getParameter(name)));
        return paramMap;
    }
}
