package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://mirek0530.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("goodbye_message", "Best regards");
        context.setVariable("preview", "New task info");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String sendDailyTasksInfoMail(String message) {
        List<String> tasks = new ArrayList<>();
        tasks.add("Prepare new template");
        tasks.add("Refactor your code");
        tasks.add("Create mail sending service");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://mirek0530.github.io");
        context.setVariable("button", "Manage tasks");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("goodbye_message", "Best regards");
        context.setVariable("preview", "Daily task info");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("tasks", tasks);
        return templateEngine.process("mail/daily-tasks-mail", context);
    }
}
