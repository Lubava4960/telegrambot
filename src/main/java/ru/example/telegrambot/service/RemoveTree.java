package ru.example.telegrambot.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.example.telegrambot.dto.CategoryDto;
import ru.example.telegrambot.repository.TreeRepository;

@Component
public class RemoveTree {
    private final TreeRepository treeRepository;

    public RemoveTree(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    public void deleteCategoryByName(String name) throws ChangeSetPersister.NotFoundException {
        CategoryDto categoryDto = (CategoryDto) treeRepository.findByName(name)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        treeRepository.delete(categoryDto);
    }


}
