package me.nolanjames.booknetworkapi.feedback;

public record FeedbackResponse(
        Double note,
        String comment,
        boolean ownFeedback
) {
}
