package com.doharu.randomeats.controller;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import com.doharu.randomeats.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 전체 메뉴 조회
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    // 선택된 카테고리 기반 랜덤 추천
    @PostMapping("/random")
    public Menu getRandomMenu(@RequestBody List<Category> categories) {
        return menuService.getRandomMenuByCategories(categories);
    }
}
