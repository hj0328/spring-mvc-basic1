package hello.servlet.web.frontcontolller.v1.controller;

import hello.servlet.web.frontcontolller.Controller;
import hello.servlet.web.frontcontolller.ModelView;
import hello.servlet.web.frontcontolller.MyView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class MemberFormControllerV1 implements Controller {
    @Override
    public ModelView process(Map<String, String> paramMap)
            throws ServletException, IOException {

        return new ModelView("new-form");
    }
}
