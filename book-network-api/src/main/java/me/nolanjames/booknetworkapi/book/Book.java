package me.nolanjames.booknetworkapi.book;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.nolanjames.booknetworkapi.feedback.Feedback;
import me.nolanjames.booknetworkapi.history.BookTransactionHistory;
import me.nolanjames.booknetworkapi.shared.BaseEntity;
import me.nolanjames.booknetworkapi.user.User;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String coverImage;
    private boolean archived;
    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> bookTransactionHistoryList;
}
