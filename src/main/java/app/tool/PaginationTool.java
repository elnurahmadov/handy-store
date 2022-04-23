package app.tool;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class PaginationTool<T> {
    public void controller(Page<T> page, Model model, int currentPage, String sortField, String sortDir) {
        List<T> content = page.getContent();
        int totalPages = page.getTotalPages();
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        if (totalPages == 0) totalPages = 1;

        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("posts", content);
    }

    public Pageable service(int currentPage, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(currentPage - 1, 10, sort);
    }

}
