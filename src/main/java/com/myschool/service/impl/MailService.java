package com.myschool.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myschool.service.IMailService;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService implements IMailService {

	private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    ThymeleafService thymeleafService;
	
	@Override
	public void sendMail(String to, String studentName, String subjectName, Integer test) {
		
		Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Thông báo môn " + subjectName + " đã được cập nhật");
            if (test == 1) {
            	// Gửi mail khi có điểm giữa kỳ
            	message.setContent(thymeleafService.getContent(studentName, subjectName, 1), CONTENT_TYPE_TEXT_HTML);
            }else if (test == 2){
            	// Gửi mail khi có điểm cuối kỳ
            	message.setContent(thymeleafService.getContent(studentName, subjectName, 2), CONTENT_TYPE_TEXT_HTML);
            }else {
            	message.setContent(thymeleafService.getContent(studentName, subjectName, 3), CONTENT_TYPE_TEXT_HTML);
            }
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}













