package com.example.api_anime.client.bunny;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class BunnyClientService {

    private final WebClient webClient;

    public BunnyClientService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://video.bunnycdn.com")
                .defaultHeader("accept", "application/json")
                .build();
    }

    public List<BunnyVideoResponse> listarVideos(String libraryId, String collectionId, String apiKey) {

        // 1. Mapeamos la respuesta como un Mapa genérico para extraer los "items"
        Map<String, Object> response = this.webClient.get()
                .uri("/library/{libraryId}/videos?collection={collectionId}&itemsPerPage=150", libraryId, collectionId)
                .header("AccessKey", apiKey)
                .retrieve()
                .bodyToMono(Map.class) // Bunny nos devuelve un objeto {} con metadata
                .block();

        if (response == null || !response.containsKey("items")) {
            return List.of();
        }

        // 2. Extraemos la lista de "items" que contiene los videos reales
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        // 3. Convertimos esa lista de mapas a tu lista de BunnyVideoResponse
        return items.stream().map(item -> {
            BunnyVideoResponse video = new BunnyVideoResponse();
            video.setGuid((String) item.get("guid"));
            video.setTitle((String) item.get("title"));
            return video;
        }).toList();
    }


}
