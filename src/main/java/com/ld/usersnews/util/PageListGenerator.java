package com.ld.usersnews.util;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageListGenerator {
    public static <T> void generateAvailablePageList(Model model, int pageNumber, Page<T> elementsPage) {
        int totalPages = elementsPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> availablePagesList = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("availablePagesList", availablePagesList);
        }
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("mainElementList", elementsPage.getContent());
    }
}
