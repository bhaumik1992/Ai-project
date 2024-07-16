package com.smart.todo.service;

import com.smart.todo.entity.Todo;

import java.util.List;


public interface TodoService {

    List<Todo> getAllTodos() ;

    List<Todo> createTodo(List<Todo> todos) ;

    void deleteTodoById(Long id) ;

    Todo updateTodo(Long id, Todo updateTodo);
}
