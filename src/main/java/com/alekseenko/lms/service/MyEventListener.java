package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyEventListener {

    private final UserService userService;

    @Autowired
    public MyEventListener(UserService userService) {
        this.userService = userService;
    }

    /*
    Пока отсутствует метод создания пользователей в БД, при запуске приложения
    создаю двух тестовых пользователей, если таблица пользователей пустая,
    для проверки функционала задания недели.
    Будет удалено в дальнейшем
    * */

    @EventListener
    public void addDefaultUsers(ContextRefreshedEvent e) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            User user1 = new User("Test1");
            User user2 = new User("Test2");
            userService.saveUser(user1);
            userService.saveUser(user2);
        }
    }
}
