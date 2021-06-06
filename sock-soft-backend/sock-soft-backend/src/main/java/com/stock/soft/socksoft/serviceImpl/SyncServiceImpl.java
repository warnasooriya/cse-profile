package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.dao.SyncDao;
import com.stock.soft.socksoft.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    private SyncDao syncDao;
    @Override
    public String companyDataSync() {
       return syncDao.companyDataSync();
    }
}
