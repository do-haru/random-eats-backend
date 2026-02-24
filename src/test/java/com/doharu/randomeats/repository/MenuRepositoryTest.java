package com.doharu.randomeats.repository;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 저장 후 조회")
    void saveAndFindMenu() {
        //given
        Menu menu = new Menu(
                "비빔밥",
                Category.KOREAN,
                "https://example.com/bibimbap.jpg"
        );

        //when
        menuRepository.save(menu);
        List<Menu> result = menuRepository.findAll();

        //then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo(menu.getName());
        assertThat(result.getFirst().getCategory()).isEqualTo(menu.getCategory());
        assertThat(result.getFirst().getImageUrl()).isEqualTo(menu.getImageUrl());
    }

    @Test
    @DisplayName("카테고리로 메뉴 조회")
    void findByCategoryIn() {
        // given
        Menu korean = new Menu("비빔밥", Category.KOREAN, "url1");
        Menu japanese = new Menu("초밥", Category.JAPANESE, "url2");
        menuRepository.saveAll(List.of(korean, japanese));

        // when
        List<Menu> result = menuRepository.findByCategoryIn(List.of(Category.KOREAN));

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(Category.KOREAN);
    }
}