package com.example.tarot.tarot;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarot")
public class TarotController {

    private final TarotService tarotService;

    public TarotController(TarotService tarotService) {
        this.tarotService = tarotService;
    }

    @PostMapping("/readings")
    public ResponseEntity<TarotReadingResponse> createReading(
            @Valid @RequestBody TarotReadingRequest request
    ) {
        return ResponseEntity.ok(tarotService.createReading(request));
    }

    @GetMapping("/readings")
    public ResponseEntity<List<TarotReadingResponse>> myReadings() {
        return ResponseEntity.ok(tarotService.listMyReadings());
    }
}
