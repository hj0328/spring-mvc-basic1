package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    /*
        로그인 전, 후로 다르게 홈 화면을 보여준다.
     */
    @RequestMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        // 로그인
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            // 세션 만료
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
