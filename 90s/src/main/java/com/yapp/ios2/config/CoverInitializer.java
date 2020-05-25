package com.yapp.ios2.config;

import com.yapp.ios2.repository.CoverRepository;
import com.yapp.ios2.vo.Cover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Order(2)
@Component
public class CoverInitializer implements CommandLineRunner {

    @Autowired
    CoverRepository coverRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> covers = Arrays.asList(
                "1990",
                "paradiso",
                "happilyeverafter",
                "favoritethings",
                "awsomemix",
                "lessbutbetter",
                "90sretroclub",
                "oneandonly"
        );
        if(coverRepository.findAll().isEmpty()){
            for(int i = 0; i < covers.size(); i++){
                Cover cover = coverRepository.findById(Long.valueOf(i+1)).orElse(
                        Cover.builder()
                                .uid(Long.valueOf(i+1))
                                .name(covers.get(i))
                                .path("static/" + covers.get(i) + ".jpeg")
                                .build()
                );
                coverRepository.save(cover);
            }
        }
    }
}
