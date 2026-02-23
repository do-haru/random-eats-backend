package com.doharu.randomeats.service;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import com.doharu.randomeats.exception.InvalidCategoryException;
import com.doharu.randomeats.exception.NoMenuFoundException;
import com.doharu.randomeats.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("전체 메뉴 조회")
    void getAllMenus() {
        // given
        List<Menu> menus = List.of(
                new Menu("비빔밥", Category.KOREAN, "url1"),
                new Menu("파스타", Category.WESTERN, "url2")
        );
        given(menuRepository.findAll()).willReturn(menus);

        // when
        List<Menu> result = menuService.getAllMenus();

        // then
        assertThat(result).hasSize(2);
        verify(menuRepository).findAll();
    }

    @Test
    @DisplayName("선택된 카테고리들 중 랜덤 메뉴 추천")
    void getRandomMenuByCategories() {
        // given
        List<Category> categories = List.of(Category.KOREAN, Category.JAPANESE);
        List<Menu> menus = List.of(
                new Menu("비빔밥", Category.KOREAN, "url1")
        );

        given(menuRepository.findByCategoryIn(categories))
                .willReturn(menus);

        // when
        Menu result = menuService.getRandomMenuByCategories(categories);

        //then
        assertThat(result.getName()).isEqualTo("비빔밥");
        verify(menuRepository).findByCategoryIn(categories);
    }

    @Test
    @DisplayName("카테고리를 선택하지 않으면 예외 발생")
    void noCategoryThrowsException() {
        // when & then
        assertThatThrownBy(() ->
                menuService.getRandomMenuByCategories(List.of()))
                .isInstanceOf(InvalidCategoryException.class)
                .hasMessage("카테고리를 하나 이상 선택해야 합니다.");
    }

    @Test
    @DisplayName("선택된 카테고리에 메뉴가 없으면 예외 발생")
    void getRandomMenuByCategoriesThrowsException() {
        // given
        List<Category> categories = List.of(Category.KOREAN);

        given(menuRepository.findByCategoryIn(categories))
                .willReturn(List.of());

        // when & then
        assertThatThrownBy(() ->
                menuService.getRandomMenuByCategories(categories))
                .isInstanceOf(NoMenuFoundException.class)
                .hasMessage("선택한 카테고리에 등록된 메뉴가 없습니다.");
    }
}