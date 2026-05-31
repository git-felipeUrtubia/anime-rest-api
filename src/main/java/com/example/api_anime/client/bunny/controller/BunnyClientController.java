package com.example.api_anime.client.bunny.controller;

import com.example.api_anime.client.bunny.service.BunnyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/bunny")
@RequiredArgsConstructor
public class BunnyClientController {

    private final BunnyService bunnyService;

    @PostMapping("/{libraryId}")
    public ResponseEntity<String> updateTitleVideo(
            @PathVariable String libraryId,
            @RequestBody Map<String, Object> body,
            @RequestHeader("X-Bunny-Key") String bunnyKey
    ) {
        List<String> listaGuids = (List<String>) body.get("listaGuids");

        if (listaGuids == null || listaGuids.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El parámetro 'listaGuids' es obligatorio y no puede estar vacío.");
        }

        try {
            bunnyService.renombrarVideosEnBunny(libraryId, bunnyKey, listaGuids);

            return ResponseEntity.ok("Proceso de renombrado masivo completado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error en el servidor: " + e.getMessage());
        }
    }

    @GetMapping("/{libraryId}")
    public ResponseEntity<List<String>> listVideoGuids(
            @PathVariable Long libraryId,
            @RequestParam(required = false) String collectionId, // Parámetro opcional dinámico
            @RequestHeader("X-Bunny-Key") String bunnyKey
    ) {
        List<String> guids = bunnyService.getVideosGuids(libraryId, collectionId, bunnyKey);
        return ResponseEntity.ok(guids);
    }

}
