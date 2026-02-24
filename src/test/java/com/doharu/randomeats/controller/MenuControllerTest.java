package com.doharu.randomeats.controller;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import com.doharu.randomeats.dto.MenuResponseDto;
import com.doharu.randomeats.exception.GlobalExceptionHandler;
import com.doharu.randomeats.exception.InvalidCategoryException;
import com.doharu.randomeats.exception.NoMenuFoundException;
import com.doharu.randomeats.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(MenuController.class)
@Import(GlobalExceptionHandler.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("전체 메뉴 조회 성공")
    void getAllMenus() throws Exception{

        List<MenuResponseDto> dtos = List.of(
                new MenuResponseDto("비빔밥", "KOREAN", "url1")
        );

        given(menuService.getAllMenus()).willReturn(dtos);

        mockMvc.perform(get("/api/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("비빔밥"))
                .andExpect(jsonPath("$[0].category").value("KOREAN"))
                .andExpect(jsonPath("$[0].imageUrl").value("url1"));

        verify(menuService).getAllMenus();
    }

    @Test
    @DisplayName("랜점 메뉴 추천 성공")
    void getRandomMenu() throws Exception {
        MenuResponseDto dto = new MenuResponseDto("비빔밥", "KOREAN", "url1");

        given(menuService.getRandomMenuByCategories(List.of(Category.KOREAN))).willReturn(dto);

        mockMvc.perform(post("/api/menus/random")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                List.of(Category.KOREAN))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("비빔밥"))
                .andExpect(jsonPath("$.category").value("KOREAN"))
                .andExpect(jsonPath("$.imageUrl").value("url1"));

        verify(menuService).getRandomMenuByCategories(List.of(Category.KOREAN));
    }

    @Test
    @DisplayName("카테고리 미선택 시 400 반환")
    void invalidCategory() throws Exception {

        given(menuService.getRandomMenuByCategories(List.of()))
                .willThrow(new InvalidCategoryException("카테고리를 하나 이상 선택해야 합니다."));

        mockMvc.perform(post("/api/menus/random")
                        .contentType("application/json")
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("카테고리를 하나 이상 선택해야 합니다."));
    }

    @Test
    @DisplayName("메뉴 없음 시 404 반환")
    void noMenuFound() throws Exception {

        given(menuService.getRandomMenuByCategories(List.of(Category.KOREAN)))
                .willThrow(new NoMenuFoundException("선택한 카테고리에 등록된 메뉴가 없습니다."));

        mockMvc.perform(post("/api/menus/random")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                List.of(Category.KOREAN))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("선택한 카테고리에 등록된 메뉴가 없습니다."));
    }
}
