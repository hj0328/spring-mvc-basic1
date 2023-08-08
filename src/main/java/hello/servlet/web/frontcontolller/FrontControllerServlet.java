package hello.servlet.web.frontcontolller;

import hello.servlet.web.frontcontolller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontolller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontolller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public FrontControllerServlet() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FrontControllerServlet.service");
        String requestURI = request.getRequestURI();

        Controller controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView myView = controller.process(request, response);
        myView.render(request, response);
    }
}
