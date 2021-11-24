package com.studies.financialmanagement.api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

	@EventListener
	private void test(ApplicationReadyEvent event) {
		this.sendEmail("rafhael.dev@gmail.com",
				Arrays.asList("rafhael.lojas@gmail.com"),
				"Testing", "Hey!<br/>Test ok.");
		System.out.println("Finishing email sending...");
	}

    public void sendEmail(String remetente,
                            List<String> recipients, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(recipients.toArray(new String[recipients.size()]));
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Something is wrong!", e);
        }
    }

}
