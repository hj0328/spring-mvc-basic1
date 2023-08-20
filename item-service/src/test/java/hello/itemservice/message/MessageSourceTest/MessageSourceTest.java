package hello.itemservice.message.MessageSourceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", new Object[]{"String"}, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void helloMessageArgs() {
        String result = ms.getMessage("hello.name", new Object[]{"String"}, null);
        assertThat(result).isEqualTo("안녕 String");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void enLang() {
        String result = ms.getMessage("hello", null, Locale.ENGLISH);
        assertThat(result).isEqualTo("hello");
    }
}
