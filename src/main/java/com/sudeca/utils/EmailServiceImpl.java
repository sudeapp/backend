package com.sudeca.utils;

/**
 * * Author: Francisco Hernandez
 **/
// Importing required classes
//import com.SpringBootEmail.Entity.EmailDetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(String email, String validateCode)  {
        logger.info("sendSimpleMail");
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setSubject("Reinicio de Password");
            mailMessage.setText("Estimado Sr., este es su código de Validación de Reinicio: \n "+validateCode+" \n Link para reiniciar su password: \n http://www.google.com");
            //mailMessage.setText(details.getMsgBody());
            //mailMessage.setSubject(details.getSubject());

            logger.info("sendSimpleMail: "+mailMessage);
            // Sending the mail
            javaMailSender.send(mailMessage);
            logger.info("Mail Sent Successfully...");
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            logger.info("Exception while Sending Mail: "+e.getMessage());
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
    /*public String
    sendMailWithAttachment()   {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }*/
    
    public Date SumarDias(Date fech, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fech);
        calendar.add(Calendar.DAY_OF_YEAR,dias);
        return calendar.getTime();
    }
}