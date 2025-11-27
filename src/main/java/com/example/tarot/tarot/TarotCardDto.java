package com.example.tarot.tarot;

import jakarta.validation.constraints.NotBlank;

public class TarotCardDto {

    @NotBlank
    private String name;

    @NotBlank
    private String position; // e.g. PAST, PRESENT, FUTURE or "1","2","3"

    private boolean reversed;

    public TarotCardDto() {
    }

    public TarotCardDto(String name, String position, boolean reversed) {
        this.name = name;
        this.position = position;
        this.reversed = reversed;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public boolean isReversed() {
        return reversed;
    }
}
