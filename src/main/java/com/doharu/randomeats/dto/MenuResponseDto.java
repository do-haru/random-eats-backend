package com.doharu.randomeats.dto;

import com.doharu.randomeats.domain.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private final String name;
    private final String category;
    private final String imageUrl;

    public MenuResponseDto(String name, String category, String imageUrl) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static MenuResponseDto from(Menu menu) {
        return new MenuResponseDto(
                menu.getName(),
                menu.getCategory().name(),
                menu.getImageUrl()
        );
    }
}
