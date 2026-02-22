package com.doharu.randomeats.repository;

import com.doharu.randomeats.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
