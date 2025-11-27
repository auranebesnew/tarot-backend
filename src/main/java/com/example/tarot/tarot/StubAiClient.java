package com.example.tarot.tarot;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StubAiClient implements AiClient {

    @Override
    public String generateInterpretation(String question, String context,
                                         SpreadType spreadType,
                                         List<TarotCardDto> cards) {
        String cardsList = cards.stream()
                .map(c -> c.getPosition() + ": " + c.getName() + (c.isReversed() ? " (reversed)" : ""))
                .collect(Collectors.joining("\n"));

        StringBuilder sb = new StringBuilder();
        sb.append("Это заглушка ответа ИИ.\n\n");
        sb.append("Вопрос: ").append(question).append("\n");
        sb.append("Контекст: ").append(context == null ? "-" : context).append("\n");
        sb.append("Расклад: ").append(spreadType).append("\n\n");
        sb.append("Карты:\n").append(cardsList).append("\n\n");
        sb.append("Здесь в будущем будет детальная интерпретация расклада с учётом вопроса и контекста.");
        return sb.toString();
    }
}
