package app.externalapi.cityapi;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CityService {
    private final RestTemplate restTemplate;
    private final String URL = "https://en.wikipedia.org/wiki/List_of_cities_in_Azerbaijan";

    public CityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpHeaders pretendBrowser(){
        return new HttpHeaders(){{
            add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        }};
    }

    public String getPage(String address){
        return restTemplate.exchange(address, HttpMethod.GET,new HttpEntity<>(pretendBrowser()), String.class).getBody();
    }

    public String clean(String s){

      String cleaned = s.replaceAll("\\[", "")
              .replaceAll("]", "")
              .replaceAll("[0-9]", "");
      
      if (cleaned.contains(" ")) return cleaned.substring(0, cleaned.indexOf(" "));
      else return cleaned;
    }

    public List<City> getCities() {
        String html = getPage(URL);
        Document document = Jsoup.parse(html);
        Elements elements = document.select(".div-col ul li");
        return elements.stream().map((Element e) -> {
            String name = clean(e.text());
            return new City(name);
        })
                .collect(Collectors.toList());

    }
}
