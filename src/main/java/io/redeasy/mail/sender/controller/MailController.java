package io.redeasy.mail.sender.controller;

import io.redeasy.mail.sender.domain.EmailDTO;
import io.redeasy.mail.sender.domain.EmailFileDTO;
import io.redeasy.mail.sender.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

@RestController
@RequestMapping("/v1")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/message")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){
        System.out.println("Message recieve "+emailDTO);
        emailService.sendEmail(emailDTO.getToUser(),
                               emailDTO.getSubject(),
                               emailDTO.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("status","Enviado");
        return  ResponseEntity.ok(response);
    }

    @PostMapping("/messageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute  EmailFileDTO emailFileDTO){
       try{
           String fileName =  emailFileDTO.getFile().getOriginalFilename();
           Path path= Paths.get("src/main/resources/files/"+fileName);
           Files.createDirectories(path.getParent());
           Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
           File file = path.toFile();
           emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(),
                                                              emailFileDTO.getMessage(), file);
           Map<String, String> response = new HashMap<>();
           response.put("status","Enviado");
           response.put("filename",fileName);
           return  ResponseEntity.ok(response);
       }catch (Exception e){
           throw new RuntimeException("Error no se puedo enviar el email con el archivo");
       }
    }

}
