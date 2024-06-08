package me.nolanjames.booknetworkapi.book;

import org.springframework.security.core.Authentication;

public interface BookService {
    Integer save(BookRequest request, Authentication connectedUser);
}
