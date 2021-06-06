package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.Dto.cse.LastUpdatedRequest;
import com.stock.soft.socksoft.Dto.cse.ReqAlphabeticalContainer;
import com.stock.soft.socksoft.dao.SyncDao;
import com.stock.soft.socksoft.model.Companies;
import com.stock.soft.socksoft.model.LastUpdate;
import com.stock.soft.socksoft.repository.CompanyRepository;
import com.stock.soft.socksoft.repository.LastUpdateRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class SyncDaoImpl implements SyncDao {

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
    @Override
    public String companyDataSync() {
        String statusMsg = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        try {
            if(updateAvailable()) {
                String characters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
                List<String> charList = Arrays.asList(characters.split(" "));

                System.out.println("#### Company Data Sync scheduler STARTED :: " + strDate);
                List<Companies> companiesList = (List<Companies>) companyRepository.findAll();
                EntityManager em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();

                charList.forEach(s -> {
                    HttpEntity<String> request = new HttpEntity<>("");
                    RestTemplate restTemplate = new RestTemplate();
                    String url = env.getProperty("cse.base.url") + "alphabetical?alphabet=" + s;
                    ResponseEntity<ReqAlphabeticalContainer> response
                            = restTemplate.postForEntity(url, request, ReqAlphabeticalContainer.class);

                    ReqAlphabeticalContainer reqAlphabeticalContainer = response.getBody();

                    reqAlphabeticalContainer.getReqAlphabetical().forEach(reqAlphabetical -> {
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

                            String query = "UPDATE companies SET  last_traded_time=?, price=? ,  turnover=? ,sharevolume=?,tradevolume=?,percentage_change=? ,`change_value`=? WHERE id=?";

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
                                    .setParameter(8, companies.getId())
                                    .executeUpdate();

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
                });

                em.getTransaction().commit();
                System.out.println("#### Company Data Sync scheduler END ::  " + strDate);
            }else {
                statusMsg=" Already Updated  ";
            }

        } catch (Exception e) {
            statusMsg = e.getLocalizedMessage();
        }

        System.out.println("STATUS :: -  " + strDate + statusMsg);
        return  statusMsg;
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
            update.setId(lastUpdatedRequest.getId());
            update.setLastUpdate(lastUpdatedRequest.getLastUpdatedTime());
            lastUpdateRepository.save(update);
            ststus=true;
        }
        return  ststus;
    }


}
