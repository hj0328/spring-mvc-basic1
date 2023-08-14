package hello.servlet.web.frontcontolller.v2.controller;

import hello.servlet.web.frontcontolller.ModelView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class MemberFormControllerV2 implements ControllerV2{
    @Override
    public ModelView process(Map<String, String> paramMap) throws ServletException, IOException {

        return new ModelView("new-form");
    }
}
