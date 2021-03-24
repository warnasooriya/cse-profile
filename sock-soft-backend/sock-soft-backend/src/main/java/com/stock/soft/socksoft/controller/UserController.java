package com.stock.soft.socksoft.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.common.net.HttpHeaders;
import com.stock.soft.socksoft.Dto.DownloadFileDto;
import com.stock.soft.socksoft.model.Companies;
import com.stock.soft.socksoft.model.Users;
import com.stock.soft.socksoft.repository.CompanyRepository;
import com.stock.soft.socksoft.repository.UsersRepository;
import com.stock.soft.socksoft.service.S3Services;
import com.stock.soft.socksoft.service.UserService;
import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.FormParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private S3Services s3Services;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users createUser(@RequestBody final  Users users){
       // users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setCreatedAt(new Date());
        return usersRepository.save(users);
    }


    @RequestMapping(value = "/register" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users register(@RequestBody final  Users users){
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        Short  aShort = 1;
        users.setEnabled(aShort);
        users.setRole("USER");
        users.setCreatedAt(new Date());
        return usersRepository.save(users);
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users userLogin(@FormParam("username") String username , @FormParam("password") String password){
        //String pwd = bCryptPasswordEncoder.encode(password);
        return  userService.auth(username,password);
    }


    @RequestMapping(value = "/update" ,method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users updateUser(@RequestBody  final  Users users){
        return usersRepository.save(users);
    }

    @RequestMapping(value = "/delete/{userId}" ,method = RequestMethod.DELETE)
    public Users deleteUser(@PathVariable(value = "userId") String userId){
        Users users = new Users();
        try {
            users = usersRepository.findOne(userId);
            usersRepository.delete(userId);
        }catch (Exception e){
            logger.warning("Problem with data deleting " +  e.getMessage());
        }
        return users;
    }

    @RequestMapping(value = "/getAll" ,method = RequestMethod.GET)
    public List<Users> getAllUsers(){
        return (List<Users>) usersRepository.findAll();
    }

    @RequestMapping(value = "/getById/{userId}" ,method = RequestMethod.GET)
    public Users getUserById(@PathVariable(value = "userId") String userId){
        return usersRepository.findOne(userId);
    }

    @RequestMapping(value = "/getByUserName" ,method = RequestMethod.POST)
    public Users getByUserName(@RequestBody final  Users users){
        return userService.getByUserName(users.getUserName());
    }

    @PostMapping(value= "/uploadProfilePhoto")
    public String uploadProfilePhoto(@RequestPart(value= "file") final MultipartFile file){
        String fileName = UUID.randomUUID().toString();
        return  s3Services.uploadFile(fileName,file);
    }

    @GetMapping("/downloadProfilePhoto/{keyName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String keyName) {
        DownloadFileDto downloadFileDto =  s3Services.downloadFile(keyName);
        return ResponseEntity.ok()
                .contentType(downloadFileDto.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyName + "\"")
                .body(downloadFileDto.getDownloadInputStream().toByteArray());
    }


}
