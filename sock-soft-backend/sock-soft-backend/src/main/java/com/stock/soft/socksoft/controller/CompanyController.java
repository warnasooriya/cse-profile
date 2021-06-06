package com.stock.soft.socksoft.controller;
import com.stock.soft.socksoft.model.Commission;
import com.stock.soft.socksoft.model.Companies;
import com.stock.soft.socksoft.repository.CommissionRepository;
import com.stock.soft.socksoft.repository.CompanyRepository;
import com.stock.soft.socksoft.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyController {
    private final static Logger logger = Logger.getLogger(CompanyController.class.getName());
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private SyncService syncService;

    @RequestMapping(value = "/create" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Companies createCompany(@RequestBody  final  Companies companies){
        return companyRepository.save(companies);
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public Companies updateCompany(@RequestBody  final  Companies companies){
        return companyRepository.save(companies);
    }

    @RequestMapping(value = "/delete/{companyId}" ,method = RequestMethod.DELETE)
    public Companies deleteCompany(@PathVariable(value = "companyId") String companyId){
        Companies companies = new Companies();
        try {
             companies = companyRepository.findOne(companyId);
            companyRepository.delete(companyId);
        }catch (Exception e){
            logger.warning("Problem with data deleting " +  e.getMessage());
        }
        return companies;
    }

    @RequestMapping(value = "/getAll" ,method = RequestMethod.GET)
    public List<Companies> getAllCompanies(){
        return (List<Companies>) companyRepository.findAll();
    }

    @RequestMapping(value = "/getById/{companyId}" ,method = RequestMethod.GET)
    public Companies getCompanyById(@PathVariable(value = "companyId") String companyId){
        return companyRepository.findOne(companyId);
    }

    @RequestMapping(value = "/getCommission" ,method = RequestMethod.GET)
    public List<Commission> getCommission(){
        return (List<Commission>) commissionRepository.findAll();
    }

    @RequestMapping(value = "/sync" ,method = RequestMethod.GET)
    public String sync(){
        syncService.companyDataSync();
        return "success";
    }


}
