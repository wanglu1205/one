package com.wanglu.movcat.service.impl;

import com.wanglu.movcat.model.Background;
import com.wanglu.movcat.repository.BackgroundRepository;
import com.wanglu.movcat.service.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackgroundServiceImpl implements BackgroundService{

    @Autowired
    private BackgroundRepository backgroundRepository;

    @Override
    public Background randomfindBackground() {
        return backgroundRepository.randomfindBackground();
    }
}
