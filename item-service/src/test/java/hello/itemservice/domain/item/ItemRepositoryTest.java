package hello.itemservice.domain.item;

import hello.itemservice.domain.item.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findAll() {
        // when
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 20);

        // given
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // then
        List<Item> result = itemRepository.findAll();
        assertThat(result).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        // given
        Item itemA = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();

        // when
        Item itemB = new Item("itemB", 20000, 20);
        itemRepository.update(itemId, itemB);

        Item updateItem = itemRepository.findById(itemId);

        // then
        assertThat(updateItem.getItemName()).isEqualTo(itemB.getItemName());
        assertThat(updateItem.getPrice()).isEqualTo(itemB.getPrice());
        assertThat(updateItem.getQuantity()).isEqualTo(itemB.getQuantity());
    }
}