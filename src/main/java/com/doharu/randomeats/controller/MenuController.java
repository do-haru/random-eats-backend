package com.doharu.randomeats.controller;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.dto.MenuResponseDto;
import com.doharu.randomeats.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menu API", description = "메뉴 조회 및 추천 API")
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 전체 메뉴 조회
    @Operation(
            summary = "전체 메뉴 조회",
            description = "등록된 모든 메뉴를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public List<MenuResponseDto> getAllMenus() {
        return menuService.getAllMenus();
    }

    // 선택된 카테고리 기반 랜덤 추천
    @Operation(
            summary = "랜덤 메뉴 추천",
            description = "선택한 카테고리 중에서 랜덤으로 메뉴 1개를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추천 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리를 선택하지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "선택한 카테고리에 메뉴가 없는 경우")
    })
    @PostMapping("/random")
    public MenuResponseDto getRandomMenu(@RequestBody List<Category> categories) {
        return menuService.getRandomMenuByCategories(categories);
    }
}
