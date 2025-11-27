package com.example.tarot.tarot;

import com.example.tarot.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tarot_readings")
public class TarotReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(length = 2000)
    private String context;

    @Enumerated(EnumType.STRING)
    private SpreadType spreadType;

    @Column(length = 4000)
    private String cardsJson;

    @Column(length = 8000)
    private String interpretation;

    private boolean usedAi;

    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    protected TarotReading() {
    }

    public TarotReading(String question, String context, SpreadType spreadType,
                        String cardsJson, String interpretation,
                        boolean usedAi, User user) {
        this.question = question;
        this.context = context;
        this.spreadType = spreadType;
        this.cardsJson = cardsJson;
        this.interpretation = interpretation;
        this.usedAi = usedAi;
        this.user = user;
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

    public String getCardsJson() {
        return cardsJson;
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
