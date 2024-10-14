package ru.example.telegrambot.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.example.telegrambot.dto.CategoryDto;
import ru.example.telegrambot.repository.TreeRepository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.util.FileSystemUtils.deleteRecursively;

@Service
@RequiredArgsConstructor
public class TreeService {
    public static final String ERROR_DONT_FIND = "Не удалось найти родительский элемент";

    private final TreeRepository treeRepository;

    public void addCategory(String name) {
        var categoryDto = new CategoryDto();
        categoryDto.setName(name);

        treeRepository.save(categoryDto);

    }

    public void addElement(String parentName, String name) {
        var category = treeRepository.findByName(parentName)
                .orElseThrow(() -> new RuntimeException(ERROR_DONT_FIND));

        if (category.getChildrens() == null) {
            var childrens = new HashSet<String>();
            category.setChildrens(childrens);

        }

        category.getChildrens().add(name);

        treeRepository.save(category);


    }

    public Map<String, Set<Set<String>>> viewTree() {
        return treeRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(CategoryDto::getName,
                        Collectors.mapping(CategoryDto::getChildrens, Collectors.toSet())));
    }

//    public void deleteCategory(String categoryName) throws NotFoundException {
//        CategoryDto categoryDto= treeRepository.findByName(categoryName)
//                .orElseThrow(() -> new NotFoundException("Category not found: " + categoryName));
//
//
//        treeRepository.delete(categoryDto);
//    }
public void deleteCategoryByName(String name) throws ChangeSetPersister.NotFoundException {
   CategoryDto categoryDto = (CategoryDto) treeRepository.findByName(name)
            .orElseThrow(ChangeSetPersister.NotFoundException::new);

    treeRepository.delete(categoryDto);
}




}















