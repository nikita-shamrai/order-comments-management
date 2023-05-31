package ua.shamrai.ocm.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

@Controller
public class OrderCommentController {

    private final Map<String, List<String>> orderCommentsMap = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        Random random = new Random();
        for (int i = 1; i < 7; i++) {
            String orderID = String.valueOf(i * random.nextInt(1, 999));
            List<String> comments = new ArrayList<>();
            for (int j = 0; j < random.nextInt(8); j++) {
                String comment = "Comment №" + (j + 1);
                comments.add(comment);
            }
            orderCommentsMap.put(orderID, comments);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("orderCommentsMap", orderCommentsMap);
        return "index";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("orderId") String orderId,
                             @RequestParam("comment") String comment) {
        if (!comment.trim().isEmpty()) {
            orderCommentsMap.computeIfAbsent(orderId, k -> new
                    ArrayList<>()).add(comment);
        }
        return "redirect:/";
    }

    @PostMapping("/editComment")
    public String editComment(@RequestParam("orderId") String orderId,
                              @RequestParam("commentId") int commentId,
                              @RequestParam("newComment") String newComment) {
        List<String> comments = orderCommentsMap.get(orderId);
        if (comments != null && commentId >= 0 && commentId < comments.size() && !newComment.trim().isEmpty()) {
            comments.set(commentId, newComment);
        }
        return "redirect:/";
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("orderId") String orderId,
                                @RequestParam("commentId") int commentId) {
        List<String> comments = orderCommentsMap.get(orderId);
        if (comments != null && commentId >= 0 && commentId < comments.size()) {
            comments.remove(commentId);
        }
        return "redirect:/";
    }

}

