package org.zerock.springex.service;

import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.PageResponseDTO;
import org.zerock.springex.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    void register(TodoDTO todoDTO);
    List<TodoDTO> getAll();
    TodoDTO getOne (int tno);
    void remove(int tno);
    void modify(TodoDTO todoDTO);
    //페이징 관련 추상 메서드 추가
    //예), 화면에서 URL 주소 : https://localhost:8080/todo/list?page=1&size=10
    // 서버에서 계산 후, 페이징 재료를 화면에 전달하고,
    // 화면에서 페이징 처리한다.
    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);
}
