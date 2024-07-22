package io.redeasy.mail.sender.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailFileDTO {

    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;



}
