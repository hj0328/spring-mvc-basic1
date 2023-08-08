package hello.servlet.web.frontcontolller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontolller.Controller;
import hello.servlet.web.frontcontolller.ModelView;
import hello.servlet.web.frontcontolller.MyView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class MemberSaveControllerV1 implements Controller {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap)
            throws ServletException, IOException {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model에 데이터 보관
        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);

        return modelView;
    }
}
