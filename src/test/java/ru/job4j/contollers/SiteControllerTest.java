package ru.job4j.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jUrlShortcutApplication;
import ru.job4j.dtos.*;
import ru.job4j.services.site.SiteService;
import ru.job4j.services.url.UrlService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class SiteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteService siteService;

    @MockBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenPostRequestRegSite() throws Exception {
        String site = "job4j.ru";
        RequestRegistrationDto requestDto = new RequestRegistrationDto();
        requestDto.setSite(site);

        ResponseRegistrationDto responseDto = new ResponseRegistrationDto();
        responseDto.setRegistration(true);
        responseDto.setLogin(site);
        responseDto.setPassword("Df4g4g");

        String requestBody = objectMapper.writeValueAsString(requestDto);
        String responseBody = objectMapper.writeValueAsString(responseDto);

        when(siteService.save(requestDto)).thenReturn(responseDto);

        this.mockMvc.perform(post("/registration")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser("job4j.ru")
    public void whenPostRequestConvertUrl() throws Exception {
        String url = "https://job4j.ru/profile/exercise/106/task-view/532";
        UrlDto urlDto = new UrlDto(url, 0);

        ShortUrlDto shortUrlDto = new ShortUrlDto("Df4g4g");

        String requestBody = objectMapper.writeValueAsString(urlDto);
        String responseBody = objectMapper.writeValueAsString(shortUrlDto);

        when(urlService.convertUrl(urlDto, "job4j.ru")).thenReturn(shortUrlDto);

        this.mockMvc.perform(post("/convert")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @WithMockUser()
    public void whenGetRedirectOnUrl() throws Exception {
        String url = "https://job4j.ru/profile/exercise/106/task-view/532";
        FullUrlDto fullUrlDto = new FullUrlDto(url);
        String shortUrl = "Df4g4g";
        when(urlService.getFullUrl(shortUrl)).thenReturn(Optional.of(fullUrlDto));

        this.mockMvc.perform(get("/redirect/{shortUrl}", shortUrl))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("Location", url));
    }

    @Test
    @WithMockUser()
    public void whenGetRequestFindAll() throws Exception {
        String url1 = "https://job4j.ru/profile/exercise/106/task-view/532";
        String url2 = "https://job4j.ru/profile/exercise/102/task-view/521";

        UrlDto urlDto1 = new UrlDto(url1, 10);
        UrlDto urlDto2 = new UrlDto(url2, 5);

        List<UrlDto> list = List.of(urlDto1, urlDto2);
        String responseBody = objectMapper.writeValueAsString(list);

        when(urlService.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }
}