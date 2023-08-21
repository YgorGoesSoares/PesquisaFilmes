package br.com.ygor.pesquisafilmes.controllers;

import br.com.ygor.pesquisafilmes.models.Movie;
import br.com.ygor.pesquisafilmes.models.MovieList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MovieController {
    private final String API_KEY = "insira aqui sua chave da API THEMOVIEDB";
    private final String BASE_URL = "https://api.themoviedb.org/3/";

    private final RestTemplate restTemplate;

    public MovieController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String paginaInicial() {
        return "index";
    }

    @GetMapping("/filmes")
    public String getInfoFilme(@RequestParam("tituloFilme") String tituloFilme, Model model) {
        String url = BASE_URL + "search/movie?api_key=" + API_KEY + "&query=" + tituloFilme + "&language=pt-BR";
        MovieList movieList = restTemplate.getForObject(url, MovieList.class); // Criação da classe MovieList para mapear a lista de filmes

        if (movieList != null && movieList.getResults() != null && !movieList.getResults().isEmpty()) {
            Movie filme = movieList.getResults().get(0); // Pega o primeiro filme da lista retornado

            model.addAttribute("filme", filme);
        }

        return "index";
    }
}