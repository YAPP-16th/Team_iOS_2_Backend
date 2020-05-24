package com.yapp.ios2.config;

import com.yapp.ios2.repository.CoverRepository;
import com.yapp.ios2.vo.Cover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CoverInitializer implements CommandLineRunner {

    @Autowired
    CoverRepository coverRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> covers = Arrays.asList(
                "dreamy2121",
                "fellinlove",
                "sweetholiday",
                "happilyeverafter",
                "mysweetyLovesyou",
                "90svibe"
        );
        for(int i = 0; i < covers.size(); i++){
            Cover cover = coverRepository.findById(Long.valueOf(i+1)).orElse(
                    Cover.builder()
                            .name(covers.get(i))
                            .path("static/" + covers.get(i) + ".jpeg")
                            .build()
            );
            coverRepository.save(cover);
        }
    }
//0 = dreamy2121
//1 = fellinlove
//2 = sweetholiday
//3 = happilyeverafter
//4 = mysweetyLovesyou
//5 = 90svibe
}
