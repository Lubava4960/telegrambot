package ru.example.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.telegrambot.dto.CategoryDto;

import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<CategoryDto, Long> {
    Optional<CategoryDto> findByName(String name);




}
