package com.example.tarot.tarot;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasicTarotInterpreter {

    public String buildSimpleText(String question, String context,
                                  SpreadType spreadType,
                                  List<TarotCardDto> cards) {
        String cardsList = cards.stream()
                .map(c -> "- " + c.getPosition() + " — " + c.getName() + (c.isReversed() ? " (перевёрнутая)" : ""))
                .collect(Collectors.joining("\n"));

        StringBuilder sb = new StringBuilder();
        sb.append("Базовая интерпретация без ИИ.\n\n");
        sb.append("Вопрос: ").append(question).append("\n");
        sb.append("Контекст: ").append(context == null ? "-" : context).append("\n");
        sb.append("Тип расклада: ").append(spreadType).append("\n\n");
        sb.append("Карты:\n").append(cardsList).append("\n\n");
        sb.append("Для полной, живой интерпретации подключите подписку PRO.");
        return sb.toString();
    }
}
