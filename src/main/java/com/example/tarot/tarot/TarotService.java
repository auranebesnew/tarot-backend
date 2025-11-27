package com.example.tarot.tarot;

import com.example.tarot.user.CurrentUserService;
import com.example.tarot.user.SubscriptionType;
import com.example.tarot.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarotService {

    private final TarotReadingRepository repository;
    private final CurrentUserService currentUserService;
    private final AiClient aiClient;
    private final BasicTarotInterpreter basicInterpreter;
    private final ObjectMapper objectMapper;

    public TarotService(TarotReadingRepository repository,
                        CurrentUserService currentUserService,
                        AiClient aiClient,
                        BasicTarotInterpreter basicInterpreter,
                        ObjectMapper objectMapper) {
        this.repository = repository;
        this.currentUserService = currentUserService;
        this.aiClient = aiClient;
        this.basicInterpreter = basicInterpreter;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public TarotReadingResponse createReading(TarotReadingRequest request) {
        User user = currentUserService.getCurrentUserOrThrow();

        boolean canUseAi = user.getSubscriptionType() == SubscriptionType.PRO && user.hasActiveProSubscription();
        if (!canUseAi && user.getBonusAiReadings() > 0) {
            canUseAi = true;
            user.setBonusAiReadings(user.getBonusAiReadings() - 1);
        }

        String interpretation;
        boolean usedAi = false;

        if (canUseAi) {
            interpretation = aiClient.generateInterpretation(
                    request.getQuestion(),
                    request.getContext(),
                    request.getSpreadType(),
                    request.getCards()
            );
            usedAi = true;
        } else {
            interpretation = basicInterpreter.buildSimpleText(
                    request.getQuestion(),
                    request.getContext(),
                    request.getSpreadType(),
                    request.getCards()
            );
        }

        String cardsJson;
        try {
            cardsJson = objectMapper.writeValueAsString(request.getCards());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize cards", e);
        }

        TarotReading reading = new TarotReading(
                request.getQuestion(),
                request.getContext(),
                request.getSpreadType(),
                cardsJson,
                interpretation,
                usedAi,
                user
        );
        repository.save(reading);

        return mapToResponse(reading);
    }

    public List<TarotReadingResponse> listMyReadings() {
        User user = currentUserService.getCurrentUserOrThrow();
        return repository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TarotReadingResponse mapToResponse(TarotReading reading) {
        List<TarotCardDto> cards;
        try {
            cards = objectMapper.readValue(
                    reading.getCardsJson(),
                    new TypeReference<List<TarotCardDto>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize cards", e);
        }

        return new TarotReadingResponse(
                reading.getId(),
                reading.getQuestion(),
                reading.getContext(),
                reading.getSpreadType(),
                cards,
                reading.getInterpretation(),
                reading.isUsedAi(),
                reading.getCreatedAt()
        );
    }
}
