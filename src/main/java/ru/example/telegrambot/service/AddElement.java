package ru.example.telegrambot.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.example.telegrambot.repository.TreeRepository;

import java.util.HashSet;
import java.util.regex.Pattern;

import static ru.example.telegrambot.service.TreeService.ERROR_DONT_FIND;

@Component
public class AddElement {
    private final TreeRepository treeRepository;
    final TreeService treeService;

    public AddElement(TreeRepository treeRepository, TreeService treeService) {
        this.treeRepository = treeRepository;
        this.treeService = treeService;
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




}
