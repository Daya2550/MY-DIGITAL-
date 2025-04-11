package DJ.MyDigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import DJ.MyDigital.Model.Feedback;
import DJ.MyDigital.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String getAllFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedback-list";
    }

    @GetMapping("/add")
    public String showAddFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "Contactus";
    }

    @PostMapping("/save")
    public String saveFeedback(@ModelAttribute Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditFeedbackForm(@PathVariable Long id, Model model) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        model.addAttribute("feedback", feedback);
        return "Contactus";
    }

    @PostMapping("/update/{id}")
    public String updateFeedback(@PathVariable Long id, @ModelAttribute Feedback feedback) {
        feedbackService.updateFeedback(id, feedback);
        return "redirect:/Admin/AdminHome";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/Admin/AdminHome";
    }

    @GetMapping("/deleteAll")
    public String deleteAllFeedbacks() {
        feedbackService.deleteAllFeedbacks();
        return "redirect:/Admin/AdminHome";
    }
}
