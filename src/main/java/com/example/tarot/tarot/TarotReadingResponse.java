package com.example.tarot.tarot;

import java.time.Instant;
import java.util.List;

public class TarotReadingResponse {

    private Long id;
    private String question;
    private String context;
    private SpreadType spreadType;
    private List<TarotCardDto> cards;
    private String interpretation;
    private boolean usedAi;
    private Instant createdAt;

    public TarotReadingResponse(Long id, String question, String context,
                                SpreadType spreadType, List<TarotCardDto> cards,
                                String interpretation, boolean usedAi,
                                Instant createdAt) {
        this.id = id;
        this.question = question;
        this.context = context;
        this.spreadType = spreadType;
        this.cards = cards;
        this.interpretation = interpretation;
        this.usedAi = usedAi;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getContext() {
        return context;
    }

    public SpreadType getSpreadType() {
        return spreadType;
    }

    public List<TarotCardDto> getCards() {
        return cards;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public boolean isUsedAi() {
        return usedAi;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
