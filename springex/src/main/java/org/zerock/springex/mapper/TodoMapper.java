package org.zerock.springex.mapper;

import org.zerock.springex.domain.TodoVO;

import java.util.List;

public interface TodoMapper {

    String getTime();

    void  insert(TodoVO todoVO);

    List<TodoVO> selectAll();

    TodoVO selectOne(int tno);

    void delete(int tno);

    void update(TodoVO todoVO);
}
