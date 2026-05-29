package com.example.api_anime.client.bunny.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BunnyService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public void renombrarVideosEnBunny(String libraryId, String apiKey, List<String> listaGuids) {
        if (listaGuids == null || listaGuids.isEmpty()) {
            throw new IllegalArgumentException("La lista de GUIDs no puede estar vacía");
        }

        System.out.println("Iniciando renombrado masivo de " + listaGuids.size() + " videos...");

        // Recorremos los GUIDs en el orden en que vienen en la lista
        for (int i = 0; i < listaGuids.size(); i++) {
            String guid = listaGuids.get(i);

            // El número del episodio será el índice + 1 (001, 002, etc.)
            // %03d transforma el 1 en "001", el 10 en "010", etc.
            String nuevoTitulo = "Episodio " + String.format("%03d", (i + 1));

            try {
                System.out.println("Renombrando posición " + (i + 1) + " [GUID: " + guid + "] -> " + nuevoTitulo);

                // Armamos el cuerpo JSON dinámico en un Map nativo para Bunny
                Map<String, String> requestBody = Map.of("title", nuevoTitulo);

                // Ejecutamos la petición POST usando el webClient de tu servicio
                this.webClient.post()
                        .uri("/library/{libraryId}/videos/{videoId}", libraryId, guid)
                        .header("AccessKey", apiKey)
                        .header("Content-Type", "application/json")
                        .bodyValue(requestBody)
                        .retrieve()
                        .toBodilessEntity() // Solo nos importa que responda un 200 OK
                        .block();

                // Un pequeño delay de 100ms para respetar el rate limit de Bunny
                Thread.sleep(100);

            } catch (Exception e) {
                // Si falla uno (por ejemplo un GUID roto), avisa pero sigue con los demás 125
                System.err.println("❌ Error al renombrar el video con GUID: " + guid + " -> " + e.getMessage());
            }
        }

        System.out.println("¡Proceso de renombrado masivo finalizado con éxito! 🎉");
    }

    public List<String> getVideosGuids(Long libraryId, String collectionId, String bunnyKey) {
        List<Map<String, Object>> todosLosItems = new java.util.ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("AccessKey", bunnyKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 1. Traemos los items de las páginas
        for (int pagina = 1; pagina <= 2; pagina++) {
            String url = "https://video.bunnycdn.com/library/" + libraryId
                    + "/videos?page=" + pagina + "&perPage=100&collection=" + collectionId;
            try {
                ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
                    if (items != null) {
                        todosLosItems.addAll(items);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error en página " + pagina + ": " + e.getMessage());
            }
        }

        // 2. ¡AQUÍ ESTÁ LA MAGIA!: Ordenamos la lista alfabéticamente por el campo "title"
        // Esto asegura que "Episodio 001" (o el nombre original viejo) vaya antes que "Episodio 002"
        return todosLosItems.stream()
                .sorted((item1, item2) -> {
                    String title1 = (String) item1.getOrDefault("title", "");
                    String title2 = (String) item2.getOrDefault("title", "");
                    return title1.compareToIgnoreCase(title2);
                })
                .map(item -> (String) item.get("guid")) // Una vez ordenados, extraemos solo el GUID
                .collect(Collectors.toList());
    }
}
