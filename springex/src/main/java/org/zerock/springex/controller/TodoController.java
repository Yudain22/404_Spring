package org.zerock.springex.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

//파일 추가 커밋 확인

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //기존, 단순 전체 갯수 출력 -> 페이징 처리해서 화면에 전달하는 코드 수정하기

    //기존 코드
//    @RequestMapping("/list")
//    public void list(Model model) {
//        log.info("todo list.........");
//
//        model.addAttribute("dtoList", todoService.getAll());
//    }
    //페이징 처리된 목록 출력 코드
    @RequestMapping("/list")
    //화면에서 전달된 파라미터를 PageRequestDTO가 자동 변환 해준다
    // Page,size가 1또는 최소, 최대 양수등이 아니면 오류가 발생하고
    // 기본 페이지로 이동, page=1&size=10 이동할 예정
    public void list(@Valid PageRequestDTO pageRequestDTO,BindingResult bindingResult, Model model) {
        log.info(pageRequestDTO);
        //에러가 존재한다면 출력후 리다이렉트
        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        //responseDTO: 키 안에는 페이징에 관련된 재료 준비물들이 다 들어있다.
//        private int page;
//        private int size;
//        private int total;
//        private int start;
//        private int end;
//        private boolean prev;
//        private boolean next;
//        private List<E> dtoList;
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
    }

    //@RequestMapping(value = "/register",method = RequestMethod.GET)
    @GetMapping("/register")
    public void registerGET() {
        log.info("todo register.......");
    }


    @PostMapping("/register")
    public String registerPost(MultipartFile file,
                              @Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws IOException {
        log.info("POST todo register.......");
        //실제 파일 이름 출력
        log.info(file.getOriginalFilename());
        //파일의 크기
        log.info(file.getSize());
        //파일의 확장자
        log.info(file.getContentType());
        //파일을 저장하는 메서드 new File("파일을 저장할 경로//파일이름.확장자")
        file.transferTo(new File("C://files//" + file.getOriginalFilename()));
        if (bindingResult.hasErrors()) {
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "/todo/register";
        }
        log.info(todoDTO);
        todoService.register(todoDTO);
        return "redirect:/todo/list";
    }


//    //@RequestMapping(value = "/register",method = RequestMethod.POST)
//    @PostMapping("/register")
//    public String registerPost(@Valid TodoDTO todoDTO,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes) {
//        log.info("POST todo register.......");
//
//        if (bindingResult.hasErrors()) {
//            log.info("has errors");
////            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            return "/todo/register";
//        }
//        log.info(todoDTO);
//        todoService.register(todoDTO);
//        return "redirect:/todo/list";
//    }

    @GetMapping({"/read", "/modify"})
    public void read(int tno, PageRequestDTO pageRequestDTO, Model model) {
        //화면에서 페이지의 정보를 전달하면
        //서버에서는 PageRequestDTO타입으로 받아두겠다
        //화면에서 PageRequestDTO를 사용하기
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);

        model.addAttribute("dto", todoDTO);
    }

    //기존에 URL 파라미터를 사용하는 메서드 방식은 get 방식이였고
    //post는 폼에 히든으로 숨겨서 전달합니다.
    @PostMapping("/remove")
    public String remove(int tno, RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO) {
        log.info("REMOVE todo register.......");
        log.info("tno : " + tno);

        todoService.remove(tno);

        //페이지, 사이즈 정보를 화면에 전달하기
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/todo/list";
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO) {
        if (bindingResult.hasErrors()) {
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }
        log.info(todoDTO);
        todoService.modify(todoDTO);
        //페이지, 사이즈 정보를 화면에 전달하기
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());

        return "redirect:/todo/list";
    }
}
