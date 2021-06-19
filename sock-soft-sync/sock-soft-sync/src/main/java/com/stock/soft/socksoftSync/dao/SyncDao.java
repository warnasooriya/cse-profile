package com.stock.soft.socksoftSync.dao;

public interface SyncDao {
    void companyDataSync();

    void sendMessages();

    String companyDataSyncAuto();
}
