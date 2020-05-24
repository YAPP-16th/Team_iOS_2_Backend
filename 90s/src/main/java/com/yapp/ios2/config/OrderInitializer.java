package com.yapp.ios2.config;

import com.yapp.ios2.repository.AlbumOrderPaperTypeRepository;
import com.yapp.ios2.repository.AlbumOrderStatusRepository;
import com.yapp.ios2.vo.AlbumOrderPaperType;
import com.yapp.ios2.vo.AlbumOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OrderInitializer implements CommandLineRunner {

    @Autowired
    AlbumOrderPaperTypeRepository albumOrderPaperTypeRepository;
    @Autowired
    AlbumOrderStatusRepository albumOrderStatusRepository;

    @Override
    public void run(String... args) throws Exception{

//        종이타입 초기화
        List<String> papers = Arrays.asList("gloss","matt");
        if(albumOrderPaperTypeRepository.findAll().isEmpty()){
            papers.forEach(
                    paper -> {
                        albumOrderPaperTypeRepository.save(
                                AlbumOrderPaperType.builder()
                                        .type(paper)
                                        .build());
                    }
            );
        }

//        배송 상태 초기화
        List<String> statuses = Arrays.asList("pending", "processing", "shipping", "done");
        if(albumOrderStatusRepository.findAll().isEmpty()){
            statuses.forEach(
                    status -> {
                        albumOrderStatusRepository.save(
                                AlbumOrderStatus.builder()
                                        .status(status)
                                        .build()
                        );
                    }
            );
        }

    }
}
