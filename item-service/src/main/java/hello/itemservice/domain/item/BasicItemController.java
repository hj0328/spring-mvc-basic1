package hello.itemservice.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/view/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        log.info("GET:/items");
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "view/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        log.info("GET:/items/{itemId}, itemId={}", itemId);
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "view/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        log.info("GET:/items/add");
        model.addAttribute("item", new Item());
        return "view/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        log.info("POST:view/items, item={}", item);

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "사용 이름은 필수입니다."));
        }

        if ((item.getPrice() == null) || (item.getPrice() < 1000) || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값=" + resultPrice));

            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "view/addForm";
        }

        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/view/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        log.info("GET:/basic/items/{itemId}/edit, itemId={}", itemId);
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "view/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        log.info("POST:/basic/items/{itemId}/edit, itemId={}, item={}", itemId, item);
        itemRepository.update(itemId, item);
        return "redirect:/view/items/{itemId}";
    }

    /*
        테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
