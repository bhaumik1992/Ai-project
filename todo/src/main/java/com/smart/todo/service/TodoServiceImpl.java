package com.smart.todo.service;


import com.smart.todo.entity.Todo;
import com.smart.todo.repository.TodoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRespository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> createTodo(List<Todo> todos) {
        return todoRepository.saveAll(todos);
    }

    @Override
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setDay(updatedTodo.getDay());
            todo.setMeals(updatedTodo.getMeals());
            todo.setCompleted(updatedTodo.isCompleted());
            return todoRepository.save(todo);
        } else {
            // Handle not found case, throw exception or return null
            return null;
        }
    }
}
