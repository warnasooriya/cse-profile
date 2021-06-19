package com.stock.soft.socksoftSync.daoImpl;

import com.stock.soft.socksoftSync.Dto.cse.*;
import com.stock.soft.socksoftSync.dao.SyncDao;
import com.stock.soft.socksoftSync.model.Companies;
import com.stock.soft.socksoftSync.model.LastUpdate;
import com.stock.soft.socksoftSync.repository.CompanyRepository;
import com.stock.soft.socksoftSync.repository.LastUpdateRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import sun.swing.BakedArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Transactional
@Repository
public class SyncDaoImpl implements SyncDao {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/greetings";
    private static final String WS_MESSAGE_TRANSFER_DESTINATION_INDEX = "/topic/indexes";
    private static final String WS_MESSAGE_TRANSFER_DESTINATION_TRADE_SUMMARY = "/topic/trade";
    @Autowired
    private Environment env;
    @Autowired
    private CompanyRepository companyRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LastUpdateRepository lastUpdateRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public SyncDaoImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @Override
    public void companyDataSync() {

        List<SocketPricesDto> socketPricesDtosList = new ArrayList<>();
        try {

            HttpEntity<String> request = new HttpEntity<>("");
            RestTemplate restTemplate = new RestTemplate();
            String url = env.getProperty("cse.base.url") + "tradeSummary";
            ResponseEntity<ResTradeSummary> response
                    = restTemplate.postForEntity(url, request, ResTradeSummary.class);
            ResTradeSummary tradeLists = response.getBody();
            tradeLists.getReqTradeSummery().forEach(reqAlphabetical -> {
                SocketPricesDto com = new SocketPricesDto();
                com.setCode(reqAlphabetical.getSymbol());
                com.setPrice(reqAlphabetical.getPrice());
                socketPricesDtosList.add(com);
            });
            simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION_TRADE_SUMMARY,
                    socketPricesDtosList);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }


    }


    private boolean updateAvailable() {
        boolean ststus = false;
        RestTemplate restTemplate = new RestTemplate();
        String url = env.getProperty("cse.base.url") + "lastUpdateTime";
        ResponseEntity<LastUpdatedRequest> response
                = restTemplate.getForEntity(url, LastUpdatedRequest.class);
        LastUpdatedRequest lastUpdatedRequest = response.getBody();
        LastUpdate lastUpdate= lastUpdateRepository.findOne(lastUpdatedRequest.getId());
        if(lastUpdate == null){
            LastUpdate update = new LastUpdate();
            update.setId(lastUpdatedRequest.getId().toString());
            update.setLastUpdate(lastUpdatedRequest.getLastUpdatedTime());
            update.setUpdatedTime(DateTime.now().toDateTime().toString());
            lastUpdateRepository.save(update);
            ststus=true;
        }
        return  ststus;
    }


    @Override
    public void sendMessages() {
        HttpEntity<String> request = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();
        String url = env.getProperty("cse.base.url") + "aspiData";
        ResponseEntity<AspiData> response
                = restTemplate.postForEntity(url, request, AspiData.class);

        ResponseEntity<AspiData> responsesnp
                = restTemplate.postForEntity(env.getProperty("cse.base.url") + "snpData", request, AspiData.class);
        AspiData aspiData = response.getBody();
        AspiData snpData = responsesnp.getBody();
        IndexUpdates indexUpdates = new IndexUpdates();
        indexUpdates.setAspiAmount(aspiData.getValue());
        indexUpdates.setAspiPersentage(aspiData.getPercentage());
        indexUpdates.setSnpAmount(snpData.getValue());
        indexUpdates.setSnpPersentage(snpData.getPercentage());
        indexUpdates.setAspichange(aspiData.getChange());
        indexUpdates.setSnpchange(snpData.getChange());
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION_INDEX,
                indexUpdates);
    }

    @Override
    public String companyDataSyncAuto() {
        String statusMsg = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        List<SocketPricesDto> socketPricesDtosList = new ArrayList<>();
        try {
            if(updateAvailable()) {

                List<Companies> companiesList = (List<Companies>) companyRepository.findAll();
                EntityManager em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();

                HttpEntity<String> request = new HttpEntity<>("");
                RestTemplate restTemplate = new RestTemplate();
                String url = env.getProperty("cse.base.url") + "tradeSummary";
                ResponseEntity<ResTradeSummary> response
                        = restTemplate.postForEntity(url, request, ResTradeSummary.class);

                ResTradeSummary tradeLists = response.getBody();

                tradeLists.getReqTradeSummery().forEach(reqAlphabetical -> {

                    Companies companies = null;
                    try {
                        companies = companiesList.stream()
                                .filter(com -> reqAlphabetical.getSymbol().equals(com.getCode())).findAny()                                      // If 'findAny' then return found
                                .orElse(null);
                        //companies = companyRepository.findByCode(reqAlphabetical.getSymbol());
                    } catch (Exception e) {
                        System.out.println("Company Not Found " + reqAlphabetical.getSymbol());
                    }

                    if (companies != null) {

                        String query = "UPDATE companies SET  " +
                                "last_traded_time=?, " +
                                "price=? , " +
                                "turnover=? , " +
                                "sharevolume=? ," +
                                "tradevolume=? ," +
                                "percentage_change=? ," +
                                "`change_value`=? ," +
                                " high=? ," +
                                " closing_price=? ," +
                                " crossing_trade_vol=? ," +
                                " crossing_volume=? ," +
                                " low=? ," +
                                " market_cap=? ," +
                                " market_cap_percentage=? ," +
                                " open=? ," +
                                " previous_close=? ," +
                                " status=? " +
                                " WHERE id=?";

                        DateTime lud = null;
                        if (reqAlphabetical.getLastTradedTime() != null) {
                            reqAlphabetical.getLastTradedTime().toLocalDateTime().toDateTime().toString();
                        }
                        em.createNativeQuery(query)
                                .setParameter(1, lud)
                                .setParameter(2, reqAlphabetical.getPrice())
                                .setParameter(3, reqAlphabetical.getTurnover())
                                .setParameter(4, reqAlphabetical.getSharevolume())
                                .setParameter(5, reqAlphabetical.getTradevolume())
                                .setParameter(6, reqAlphabetical.getPercentageChange())
                                .setParameter(7, reqAlphabetical.getChange())
                                .setParameter(8, reqAlphabetical.getHigh())
                                .setParameter(9, reqAlphabetical.getClosingPrice())
                                .setParameter(10, reqAlphabetical.getCrossingTradeVol())
                                .setParameter(11, reqAlphabetical.getCrossingVolume())
                                .setParameter(12, reqAlphabetical.getLow())
                                .setParameter(13, reqAlphabetical.getMarketCap())
                                .setParameter(14, reqAlphabetical.getMarketCapPercentage())
                                .setParameter(15, reqAlphabetical.getOpen())
                                .setParameter(16, reqAlphabetical.getPreviousClose())
                                .setParameter(17, reqAlphabetical.getStatus())
                                .setParameter(18, companies.getId())
                                .executeUpdate();
                        SocketPricesDto socketPricesDto = new SocketPricesDto();
                        socketPricesDto.setCode(reqAlphabetical.getSymbol());
                        socketPricesDto.setPrice(reqAlphabetical.getPrice());
                        socketPricesDtosList.add(socketPricesDto);

                    } else {

                        Companies com = new Companies();
                        com.setName(reqAlphabetical.getName());
                        com.setCode(reqAlphabetical.getSymbol());
                        com.setChangeValue(reqAlphabetical.getChange());
                        com.setPrice(reqAlphabetical.getPrice());
                        com.setPercentageChange(reqAlphabetical.getPercentageChange());
                        com.setTurnover(reqAlphabetical.getTurnover());
                        com.setTradevolume(reqAlphabetical.getTradevolume());
                        com.setSharevolume(reqAlphabetical.getSharevolume());
                        if (reqAlphabetical.getLastTradedTime() != null) {
                            com.setLastTradedTime(reqAlphabetical.getLastTradedTime().toDateTime().toString());
                        }
                        em.persist(com);
                    }
                });


                em.getTransaction().commit();
                simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION_TRADE_SUMMARY,
                        socketPricesDtosList);

                statusMsg=" Data Synced  ";
            }else {
                statusMsg=" Already Updated  ";

            }

        } catch (Exception e) {
            statusMsg = e.getLocalizedMessage();
            // logger.error(e.getLocalizedMessage());
        }

        System.out.println("STATUS :: -  " + sdf.format(new Date()) + statusMsg);

        return  statusMsg;
    }
}
