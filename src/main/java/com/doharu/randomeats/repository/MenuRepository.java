package com.doharu.randomeats.repository;

import com.doharu.randomeats.domain.Category;
import com.doharu.randomeats.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByCategoryIn(List<Category> categories);
}
