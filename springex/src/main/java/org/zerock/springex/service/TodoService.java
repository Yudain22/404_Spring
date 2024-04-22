package org.zerock.springex.service;

import org.zerock.springex.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    void register(TodoDTO todoDTO);
    List<TodoDTO> getAll();
    TodoDTO getOne (int tno);
    void remove(int tno);
    void modify(TodoDTO todoDTO);
}
