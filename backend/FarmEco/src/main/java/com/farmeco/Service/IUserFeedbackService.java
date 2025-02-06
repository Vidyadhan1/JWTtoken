package com.farmeco.Service;

import com.farmeco.dto.FeedbackDto;

public interface IUserFeedbackService {
    Boolean saveFeedback(FeedbackDto feedbackDto);
}