package com.bitc.tomcatjava.jebcex.service;

import com.bitc.tomcatjava.jebcex.dao.TodoDAO;
import com.bitc.tomcatjava.jebcex.domain.TodoVO;
import com.bitc.tomcatjava.jebcex.dto.TodoDTO;
import com.bitc.tomcatjava.jebcex.util.MapperUtil;
import org.modelmapper.ModelMapper;

public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService() {
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }

    public void register(TodoDTO todoDTO) throws Exception{
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        System.out.println("todoVO : " + todoVO);

        dao.insert(todoVO);
    }
}
