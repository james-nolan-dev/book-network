package me.nolanjames.booknetworkapi.book;

import me.nolanjames.booknetworkapi.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthorName(),
                book.getIsbn(),
                book.getSynopsis(),
                book.getOwner().fullName(),
                null,
                book.getRate(),
                book.isArchived(),
                book.isShareable()
        );
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory) {
        return new BorrowedBookResponse(
                bookTransactionHistory.getBook().getId(),
                bookTransactionHistory.getBook().getTitle(),
                bookTransactionHistory.getBook().getAuthorName(),
                bookTransactionHistory.getBook().getIsbn(),
                bookTransactionHistory.getBook().getRate(),
                bookTransactionHistory.isReturned(),
                bookTransactionHistory.isReturnApproved()
        );
    }
}
