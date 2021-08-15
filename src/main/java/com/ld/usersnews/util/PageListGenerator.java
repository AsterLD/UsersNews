package com.ld.usersnews.util;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Класс PageListGenerator, содержит единственный метод generateAvailablePageList, который на входе получает:
 * 1. модель в которую добавляются необходимые данные,
 * 2. переменную, обозначающую номер страницы, запрашиваемой пользователем,
 * 3. Коллекцию Page, из которой берется сведения о количестве страниц с содержимым,
 * Если, число страниц содержимого в коллекции Page больше нуля,
 * List заполняется значениями (от 1, до Последняя страница с содержимым) и List добавляется в модель.
 * В модель добавляется, запрашиваемый пользователем, номер страницы, а также List с содержимым запрашиваемой страницы
 * На выходе получается Модель, содержащая:
 * List<Integer> с списком доступных страниц,
 * int с номером той страницы, которую запросил пользователь,
 * List<T> с содержимым, содержимое меняется, в зависимости от класса, переданного сервис-классом.
 */

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
