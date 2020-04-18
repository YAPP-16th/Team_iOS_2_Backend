package com.yapp.ios2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.dto.KakaoProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoService {

    @Autowired
    private final RestTemplate restTemplate;
    private final Environment env;
    private final ObjectMapper objectMapper;
    private KakaoProfileDto kakaoProfileDto;

    public KakaoProfileDto getKakaoProfile(String accessToken) {

        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty(""), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                kakaoProfileDto = objectMapper.readValue(response.getBody(), KakaoProfileDto.class);
                return kakaoProfileDto;
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

//    public RetKakaoAuth getKakaoTokenInfo(String code) {
//        // Set header : Content-type: application/x-www-form-urlencoded
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        // Set parameter
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", kakaoClientId);
//        params.add("redirect_uri", baseUrl + kakaoRedirect);
//        params.add("code", code);
//        // Set http entity
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.kakao.url.token"), request, String.class);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return gson.fromJson(response.getBody(), RetKakaoAuth.class);
//        }
//        return null;
//    }
}