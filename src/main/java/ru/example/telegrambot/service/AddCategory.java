package ru.example.telegrambot.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.example.telegrambot.dto.CategoryDto;
import ru.example.telegrambot.repository.TreeRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddCategory {
    private final TreeRepository treeRepository;
    private final Pattern pattern = Pattern.compile("(([А-я\\d\\s.,!?:]+))");
    final TreeService treeService;

    public AddCategory(TreeRepository treeRepository, TreeService treeService) {
        this.treeRepository = treeRepository;
        this.treeService = treeService;
    }
    public void addCategory(String name) {

        var categoryDto = new CategoryDto();
        categoryDto.setName(name);
        treeRepository.save(categoryDto);

    }

//    public String getCommand() {
//      return "/addCategory";
//    }



    }
















