package org.zerock.springex.dto;

import lombok.Builder;

import java.util.List;

public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int strat;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;
    //dtoList로 받기
    private List<E> dtoList;

    //생성자 만들기
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO,List<E> dtoList,int total){
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;
    }
}
