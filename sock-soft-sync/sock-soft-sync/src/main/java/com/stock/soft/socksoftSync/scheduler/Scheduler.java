package com.stock.soft.socksoftSync.scheduler;

import com.stock.soft.socksoftSync.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {


   @Autowired
   private SyncService syncService;

   // every 2 second update prices
    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void companyDataSync() {
        syncService.companyDataSync();
    }

    // 1 Hour Schedule For Update Database with Trade Summary
    @Scheduled(fixedRateString = "3600000", initialDelayString = "0")
    public void companyDataSyncSheduleAuto() {
        syncService.companyDataSyncAuto();
    }

    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void schedulingTask() {
        syncService.sendMessages();
    }

}
