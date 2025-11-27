package com.example.tarot.tarot;

import java.util.List;

public interface AiClient {

    /**
     * Stub for external AI provider.
     * Replace implementation with real HTTP client (OpenAI, etc.).
     */
    String generateInterpretation(String question,
                                  String context,
                                  SpreadType spreadType,
                                  List<TarotCardDto> cards);
}
