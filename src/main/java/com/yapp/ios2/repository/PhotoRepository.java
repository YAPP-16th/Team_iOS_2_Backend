package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
