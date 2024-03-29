package hello.servlet.web.frontcontolller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MemberListControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);

        return "members";
    }
}
