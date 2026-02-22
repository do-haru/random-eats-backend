package com.doharu.randomeats.service;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
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
    void getAllMenu() {
        // given
        List<Menu> menus = List.of(
                new Menu("비빔밥", Category.KOREAN, "url1"),
                new Menu("파스타", Category.WESTERN, "url2")
        );
        given(menuRepository.findAll()).willReturn(menus);

        // when
        List<Menu> result = menuService.getAllMenu();

        // then
        assertThat(result).hasSize(2);
        verify(menuRepository).findAll();
    }

    @Test
    @DisplayName("랜덤 메뉴 추천")
    void getRandomMenu() {
        // given
        List<Menu> menus = List.of(
                new Menu("비빔밥", Category.KOREAN, "url1")
        );
        given(menuRepository.findAll()).willReturn(menus);

        // when
        Menu result = menuService.getRandomMenu();

        //then
        assertThat(result.getName()).isEqualTo("비빔밥");
    }

    @Test
    @DisplayName("메뉴가 없으면 예외 발생")
    void getRandomMenuThrowsException() {
        // given
        given(menuRepository.findAll()).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> menuService.getRandomMenu())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("등록된 메뉴가 없습니다.");
    }
}