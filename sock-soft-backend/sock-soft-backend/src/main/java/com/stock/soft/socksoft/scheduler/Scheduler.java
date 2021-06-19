package com.stock.soft.socksoft.scheduler;

import com.stock.soft.socksoft.service.SyncService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Scheduler {
    Logger logger = LoggerFactory.getLogger(Scheduler.class);

   @Autowired
   private SyncService syncService;

    public void companyDataSync() {
        syncService.companyDataSync();
    }
}
