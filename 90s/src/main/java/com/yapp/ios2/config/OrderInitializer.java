package com.yapp.ios2.config;

import com.yapp.ios2.repository.AlbumOrderPaperType1Repository;
import com.yapp.ios2.repository.AlbumOrderPaperType2Repository;
import com.yapp.ios2.repository.AlbumOrderPostTypeRepository;
import com.yapp.ios2.repository.AlbumOrderStatusRepository;
import com.yapp.ios2.vo.AlbumOrderPaperType1;
import com.yapp.ios2.vo.AlbumOrderPaperType2;
import com.yapp.ios2.vo.AlbumOrderPostType;
import com.yapp.ios2.vo.AlbumOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Order(1)
@Component
public class OrderInitializer implements CommandLineRunner {

    @Autowired
    AlbumOrderPaperType1Repository albumOrderPaperType1Repository;
    @Autowired
    AlbumOrderPaperType2Repository albumOrderPaperType2Repository;
    @Autowired
    AlbumOrderStatusRepository albumOrderStatusRepository;
    @Autowired
    AlbumOrderPostTypeRepository albumOrderPostTypeRepository;

    @Override
    public void run(String... args) throws Exception{


//        종이타입 초기화
        List<String> papers1 = Arrays.asList("gloss","matt");
        if(albumOrderPaperType1Repository.findAll().isEmpty()){
            for(int i = 0; i < papers1.size(); i++){
                albumOrderPaperType1Repository.save(
                                AlbumOrderPaperType1.builder()
                                        .uid(Long.valueOf(i+1))
                                        .type(papers1.get(i))
                                        .build());
            }
//            papers1.forEach(
//                    paper -> {
//                        albumOrderPaperType1Repository.save(
//                                AlbumOrderPaperType1.builder()
//                                        .uid(Long.valueOf(index))
//                                        .type(paper)
//                                        .build());
//                    }
//            );
        }

        List<String> papers2 = Arrays.asList("normal","good", "better");
        if(albumOrderPaperType2Repository.findAll().isEmpty()){
            for(int i = 0; i < papers2.size(); i++){
                albumOrderPaperType2Repository.save(
                        AlbumOrderPaperType2.builder()
                                .uid(Long.valueOf(i+1))
                                .type(papers2.get(i))
                                .build());
            }
//            papers2.forEach(
//                    paper -> {
//                        albumOrderPaperType2Repository.save(
//                                AlbumOrderPaperType2.builder()
//                                        .type(paper)
//                                        .build());
//                    }
//            );
        }

//        배송 상태 초기화
        List<String> statuses = Arrays.asList("pending", "ready", "processing", "shipping", "done");
        if(albumOrderStatusRepository.findAll().isEmpty()){
            for(int i = 0; i < statuses.size(); i++){
                albumOrderStatusRepository.save(
                        AlbumOrderStatus.builder()
                                .uid(Long.valueOf(i+1))
                                .status(statuses.get(i))
                                .build());
            }
//            statuses.forEach(
//                    status -> {
//                        albumOrderStatusRepository.save(
//                                AlbumOrderStatus.builder()
//                                        .status(status)
//                                        .build()
//                        );
//                    }
//            );
        }

        //        배송 종류 초기
        List<String> postTypes = Arrays.asList("normal", "fast", "faster");
        if(albumOrderPostTypeRepository.findAll().isEmpty()){
            for(int i = 0; i < postTypes.size(); i++){
                albumOrderPostTypeRepository.save(
                        AlbumOrderPostType.builder()
                                .uid(Long.valueOf(i+1))
                                .type(postTypes.get(i))
                                .build());
            }
//            statuses.forEach(
//                    status -> {
//                        albumOrderStatusRepository.save(
//                                AlbumOrderStatus.builder()
//                                        .status(status)
//                                        .build()
//                        );
//                    }
//            );
        }

    }
}
