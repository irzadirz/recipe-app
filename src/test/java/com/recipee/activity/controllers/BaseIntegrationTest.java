package com.recipee.activity.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipee.jwt.controllers.dto.JwtRequest;
import com.recipee.jwt.controllers.dto.JwtResponse;
import com.recipee.jwt.controllers.dto.Response;
import com.recipee.jwt.model.AuthorisationDocumentDto;
import com.recipee.jwt.service.JwtUserDetailsService;
import com.recipee.utils.EndPointConstants;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"int"})
@TestPropertySource({"classpath:application-it.properties"})
public class BaseIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private MongoTemplate mongoTemplate;
    public static String jwt;

    @BeforeEach
    public void before() throws Exception {
        String json = fromResource("Auth/user1.json");
        ObjectMapper mapper = new ObjectMapper();
        var usr = mapper.readValue(json, AuthorisationDocumentDto.class);
        if (!userDetailsService.userExist(usr.getUsername()))
            userDetailsService.save(json,new MockMultipartFile("test",new byte[0]));

        var r = mvc.perform(MockMvcRequestBuilders.post(EndPointConstants.SLASH_AUTHENTICATE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        var res=mapper.readValue(r, Response.class);
        var out = (LinkedHashMap<String, String>) res.getData();
        this.jwt = out.get("token");

    }




    /*BaseIntegrationTest() {
        String json = fromResource("Auth/user1.json");
        ObjectMapper mapper = new ObjectMapper();
        var usr = mapper.readValue(json, AuthorisationDocumentDto.class);
        if (!userDetailsService.userExist(usr.getUsername()))
            userDetailsService.save(json,new MockMultipartFile("test",new byte[0]));

        var r = mvc.perform(MockMvcRequestBuilders.post(EndPointConstants.SLASH_AUTHENTICATE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(r);
    }*/

    public static String fromResource(String resourceName) throws IOException {
        InputStream inputStream = BaseIntegrationTest.class
                .getClassLoader().getResourceAsStream(resourceName);
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }catch (Exception e) {
            throw new IOException("resource not fond");
        }
    }
    /*String getJwtToken(){

    }*/
}
