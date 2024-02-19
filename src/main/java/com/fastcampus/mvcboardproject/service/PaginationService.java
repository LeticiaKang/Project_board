package com.fastcampus.mvcboardproject.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;  //페이지네이션 바의 길이 [1|2|3|4|5]

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0); //음수 방지 max
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);  // 총 페이지 수 초과 방지 min

        return IntStream.range(startNumber, endNumber).boxed().toList();  // .boxed(): 기본형(int) 스트림을 참조형(Integer) 스트림으로 변환
    }

    public static void main(String[] args) {
        PaginationService paginationService = new PaginationService();
        List<Integer> paginationBar = paginationService.getPaginationBarNumbers(3, 20);
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
