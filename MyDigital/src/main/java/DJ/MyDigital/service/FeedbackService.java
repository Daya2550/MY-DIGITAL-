package DJ.MyDigital.service;


import DJ.MyDigital.Model.Feedback;
import DJ.MyDigital.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback existingFeedback = feedbackRepository.findById(id).orElse(null);
        if (existingFeedback != null) {
            existingFeedback.setUserName(feedback.getUserName());
            existingFeedback.setEmail(feedback.getEmail());
            existingFeedback.setMessage(feedback.getMessage());
            return feedbackRepository.save(existingFeedback);
        }
        return null;
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    public void deleteAllFeedbacks() {
        feedbackRepository.deleteAll();
    }
}
