package hello.servlet.web.frontcontolller.v2.controller;

import hello.servlet.web.frontcontolller.ModelView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public interface ControllerV2 {
    ModelView process(Map<String, String> paramMap) throws ServletException, IOException;
}
