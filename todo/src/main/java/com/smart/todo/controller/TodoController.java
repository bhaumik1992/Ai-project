package com.smart.todo.controller;

import com.smart.todo.entity.Todo;
import com.smart.todo.service.TodoService;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TodoController.class);
    @Autowired
    private TodoService todoService;
    @Autowired
    private OpenAiChatModel openAiChatModel;


    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public void createTodo(@RequestBody List<Todo> todos) {
        todoService.createTodo(todos);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        return todoService.updateTodo(id, updatedTodo);
    }

    @GetMapping("/prompt")
    public String prompt( @RequestParam Map<String, String> params) {
            params.forEach((key, value) -> {
                System.out.println("key --> " + key);
                System.out.println("value --> " + value);
            });
            String age = params.getOrDefault("age", "0");
            String targetWeightType = params.getOrDefault("targetWeightType", "loss");
            String targetWeight = params.getOrDefault("targetWeight", "0");
            String foodType = params.getOrDefault("foodType", "Veg");
            String mealPlan = params.getOrDefault("mealplan", "7 days");
            String achieveIn = params.getOrDefault("achieveIn", "3 months");
            String request = String.format("I am %s years old and looking to %s %s kg within %s months. Give me a %s %s diet program including breakfast, " +
                            "mid-morning snack, lunch, afternoon snack, dinner, and evening snack. " +
                            "Provide the response only in an array of [ \"Day no: Breakfast -, Mid Morning Snack - , Lunch - , Afternoon Snack - , Evening Snack -\"]"
                    , age, targetWeightType, targetWeight, achieveIn, mealPlan, foodType);
            System.out.println("request ===> " + request);
            ChatResponse response = openAiChatModel.call(new Prompt(request,
                    OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_3_5_TURBO.getValue()).build()));
            return response.getResult().getOutput().getContent();
    }
}
