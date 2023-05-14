package com.uniwa.contract.notification.service;

import com.uniwa.contract.notification.model.EmailInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    public void send(EmailInfo emailInfo) {

        String htmlBody = templateEngine.process("template-email.html", createEmailContext(emailInfo));
        MimeMessage mimeMessage = createMimeMessage(emailInfo.getEmail(), emailInfo.getSubject(), htmlBody);

        emailSender.send(mimeMessage);
    }

    private MimeMessage createMimeMessage(String email, String subject, String htmlBody) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper =
                    new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
            messageHelper.setFrom("info@uniwa.gr");
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlBody, true);

        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
        return message;
    }


    private Context createEmailContext(EmailInfo emailInfo) {

        return new Context(Locale.getDefault(), emailInfo.getValues());
    }
}
