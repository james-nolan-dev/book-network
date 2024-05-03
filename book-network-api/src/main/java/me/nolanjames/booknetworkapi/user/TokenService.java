package me.nolanjames.booknetworkapi.user;

import java.util.Optional;

public interface TokenService {
    Optional<Token> findByToken(String token);

    void saveToken(Token token);
}
