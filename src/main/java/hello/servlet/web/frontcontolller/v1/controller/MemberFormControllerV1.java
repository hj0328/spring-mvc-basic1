package hello.servlet.web.frontcontolller.v1.controller;

import hello.servlet.web.frontcontolller.Controller;
import hello.servlet.web.frontcontolller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements Controller {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        return new MyView("/WEB-INF/views/new-form.jsp");

    }
}
