package com.animeapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.animeapp.models.Anime;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AnimeService {

    @Value("${api.url}") // URL de la API externa desde application.properties
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;
    
    public List<Anime> getAnimeList() {
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        return extractAnimeData(response.getBody());
    }

    private List<Anime> extractAnimeData(String json) {
        List<Anime> animeList = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode data = root.path("data");
            for (JsonNode node : data) {
                Long id = node.path("mal_id").asLong();
                String title = node.path("title").asText();
                Double score = node.path("score").asDouble();
                String season = node.path("season").asText();
                animeList.add(new Anime(id, title, score, season));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animeList;
    }

    public String calculateAverageScore() {
        String message = "";
        String img = "";

        String json = restTemplate.getForObject(apiUrl, String.class);

        List<Anime> animeList = extractAnimeData(json);

        Map<String, List<Double>> scoresBySeason = new HashMap<>();
        for (Anime anime : animeList) {
            String season = anime.getSeason();
            double score = anime.getScore();
            scoresBySeason.computeIfAbsent(season, k -> new ArrayList<>()).add(score);
        }

        double totalScore = 0;
        int count = 0;
        for (List<Double> scores : scoresBySeason.values()) {
            for (Double score : scores) {
                totalScore += score;
                count++;
            }
        }

        double averageScore = count == 0 ? 0 : totalScore / count;

        if (averageScore >= 1 && averageScore <= 4) {
            message = "I do not recommend it.";
            img = "src/main/resources/images/image_low_score.jpg";
        } else if (averageScore >= 5 && averageScore <= 7) {
            message = "You may have fun.";
            img = "src/main/resources/images/image_medium_score.jpg";
        } else if (averageScore > 7) {
            message = "Great, this is one of the best anime.";
            img = "src/main/resources/images/image_high_score.jpg";
        }

        return (message + img);
    }
}

