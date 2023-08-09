package hello.servlet.web.frontcontolller;

import hello.servlet.web.frontcontolller.v1.controller.*;
import hello.servlet.web.frontcontolller.v2.controller.ControllerHandlerAdapterV2;
import hello.servlet.web.frontcontolller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontolller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontolller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, Object> controllerMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServlet() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());

        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerHandlerAdapterV1());
        handlerAdapters.add(new ControllerHandlerAdapterV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FrontControllerServlet.service");
        String requestURI = request.getRequestURI();

        // 컨트롤러보다 넒은 의미의 핸들러로 네이밍
        // 핸들러가 지원만 한다면 컨트롤러 뿐만 아니라 어떤 것이라도 URL에 매핑해서 사용할 수 있기 때문이다.
        Object handler = getHandler(requestURI);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // adapter를 통해서 핸들러(컨트롤러) 호출
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(request, response, handler);

        MyView myView = viewResolver(modelView.getViewName());
        myView.render(modelView.getModel(), request, response);
    }

    private Object getHandler(String requestURI) {
        return controllerMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)) {
                return adapter;
            }
        }

        throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        MyView myView = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return myView;
    }
}
