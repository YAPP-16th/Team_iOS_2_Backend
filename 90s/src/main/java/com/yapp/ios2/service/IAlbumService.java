package com.yapp.ios2.service;

import com.yapp.ios2.vo.Album;

import java.util.Map;

public interface IAlbumService {
    Album create(String name, Integer photoLimit);
}
