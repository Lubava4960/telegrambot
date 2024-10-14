package ru.example.telegrambot.controller;


import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.telegrambot.config.TelegramBotConfiguration;
import ru.example.telegrambot.service.TreeService;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class MessageController {

    private final TreeService treeService;
    @Autowired
    private final TelegramBotConfiguration telegramBotConfiguration;


    @Operation(
            summary = "добавление родительской категории ",
            tags= "категории"
    )

    @GetMapping("/{name}/addCategory")

    public void addCategory(@PathVariable String name) {
        treeService.addCategory(name);
    }


    @Operation(
            summary = "добавление дочернего элемента ",
            tags= "категории"
    )
    @GetMapping("/{parentName}/{name}/addElement")

    public void addElement(@PathVariable String parentName, @PathVariable String name) {
        treeService.addElement(parentName, name);
    }


    @Operation(
            summary = "просмотри категорий ",
            tags= "категории"
    )
    @GetMapping("/tree/view")
    public Map<String, Set<Set<String>>> treeView() {
        return treeService.viewTree();
    }


    @Operation(
            summary = "удаление категорий ",
            tags= "категории"
    )
    @DeleteMapping("/{name}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        try {
            treeService.deleteCategoryByName(name);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}





