package hello.itemservice.domain.item;

import hello.itemservice.web.item.form.ItemSaveForm;
import hello.itemservice.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/view/items")
@RequiredArgsConstructor
public class ItemController {

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
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm itemSaveForm, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        log.info("POST:view/items, itemSaveForm={}", itemSaveForm);

        if (itemSaveForm.getPrice() != null && itemSaveForm.getQuantity() != null) {
            int resultPrice = itemSaveForm.getPrice() * itemSaveForm.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            } }


        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "view/addForm";
        }

        Item item = new Item();
        item.setItemName(itemSaveForm.getItemName());
        item.setPrice(itemSaveForm.getPrice());
        item.setQuantity(itemSaveForm.getQuantity());

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
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm itemUpdateForm
            , BindingResult bindingResult) {
        log.info("POST:/basic/items/{itemId}/edit, itemId={}, itemUpdateForm={}", itemId, itemUpdateForm);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "view/editForm";
        }

        Item item = new Item();
        item.setItemName(itemUpdateForm.getItemName());
        item.setPrice(itemUpdateForm.getPrice());
        item.setQuantity(itemUpdateForm.getQuantity());

        itemRepository.update(itemId, item);
        return "redirect:/view/items/{itemId}";
    }

    /*2
        테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
