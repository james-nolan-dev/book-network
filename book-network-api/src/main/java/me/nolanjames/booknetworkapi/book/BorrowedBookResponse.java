package me.nolanjames.booknetworkapi.book;

public record BorrowedBookResponse(
        Integer id,
        String title,
        String authorName,
        String isbn,
        double rate,
        boolean returned,
        boolean returnApproved
) {
}
