package com.doharu.randomeats.service;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import com.doharu.randomeats.dto.MenuResponseDto;
import com.doharu.randomeats.exception.InvalidCategoryException;
import com.doharu.randomeats.exception.NoMenuFoundException;
import com.doharu.randomeats.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    // 전체 메뉴 조회
    public List<MenuResponseDto> getAllMenus() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::from)
                .collect(Collectors.toList());
    }

    // 랜덤 메뉴 추천
    public MenuResponseDto getRandomMenuByCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new InvalidCategoryException("카테고리를 하나 이상 선택해야 합니다.");
        }

        List<Menu> menus = menuRepository.findByCategoryIn(categories);

        if (menus.isEmpty()) {
            throw new NoMenuFoundException("선택한 카테고리에 등록된 메뉴가 없습니다.");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(menus.size());
        Menu selectMenu = menus.get(randomIndex);

        return MenuResponseDto.from(selectMenu);
    }
}
