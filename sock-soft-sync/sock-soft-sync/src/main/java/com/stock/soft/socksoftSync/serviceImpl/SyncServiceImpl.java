package com.stock.soft.socksoftSync.serviceImpl;

import com.stock.soft.socksoftSync.dao.SyncDao;
import com.stock.soft.socksoftSync.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    private SyncDao syncDao;
    @Override
    public void companyDataSync() {
        syncDao.companyDataSync();
    }

    @Override
    public void sendMessages() {
        syncDao.sendMessages();
    }

    @Override
    public String companyDataSyncAuto() {
        return syncDao.companyDataSyncAuto();
    }
}
