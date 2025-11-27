package com.example.tarot.tarot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class TarotReadingRequest {

    @NotBlank
    private String question;

    private String context;

    @NotNull
    private SpreadType spreadType;

    @Size(min = 1, max = 10)
    private List<TarotCardDto> cards;

    public TarotReadingRequest() {
    }

    public TarotReadingRequest(String question, String context,
                               SpreadType spreadType, List<TarotCardDto> cards) {
        this.question = question;
        this.context = context;
        this.spreadType = spreadType;
        this.cards = cards;
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
}
