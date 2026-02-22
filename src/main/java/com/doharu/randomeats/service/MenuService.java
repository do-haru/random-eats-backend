package com.doharu.randomeats.service;

import com.doharu.randomeats.domain.Menu;
import com.doharu.randomeats.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final Random random = new Random();

    // 전체 메뉴 조회
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    // 랜덤 메뉴 추천
    public Menu getRandomMenu() {
        List<Menu> menus = menuRepository.findAll();

        if (menus.isEmpty()) {
            throw new IllegalStateException("등록된 메뉴가 없습니다.");
        }

        int randomIndex = random.nextInt(menus.size());
        return menus.get(randomIndex);
    }
}
