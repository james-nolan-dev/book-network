package me.nolanjames.booknetworkapi.feedback;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.nolanjames.booknetworkapi.book.Book;
import me.nolanjames.booknetworkapi.book.BookRepository;
import me.nolanjames.booknetworkapi.exception.OperationNotPermittedException;
import me.nolanjames.booknetworkapi.shared.PageResponse;
import me.nolanjames.booknetworkapi.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: " + request.bookId()));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give feedback for an archived or not shareable book");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give feedback for your own book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);

        return feedbackRepository.save(feedback).getId();
    }

    @Override
    public PageResponse<FeedbackResponse> findAllFeedbackByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> allFeedback = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponseList = allFeedback.stream()
                .map(feedback -> feedbackMapper.toFeedbackResponse(feedback, user.getId()))
                .toList();

        return new PageResponse<>(
                feedbackResponseList,
                allFeedback.getNumber(),
                allFeedback.getSize(),
                allFeedback.getTotalElements(),
                allFeedback.getTotalPages(),
                allFeedback.isFirst(),
                allFeedback.isLast()
        );
    }
}
