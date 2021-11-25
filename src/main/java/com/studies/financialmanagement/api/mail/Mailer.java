package com.studies.financialmanagement.api.mail;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine thymeleaf;

    @Autowired
    private EntryRepository entryRepository;

	@EventListener
	private void test(ApplicationReadyEvent event) {

        String template = "mail/warning-due-entries";

		List<Entry> list = entryRepository.findAll();

		Map<String, Object> variables = new HashMap<>();
		variables.put("entries", list);

        processEmailTemplate("rafhael.dev@gmail.com",
				Arrays.asList("rafhael.lojas@gmail.com"),
				"Testing", template, variables);
		System.out.println("Finishing email sending...");
	}

    public void processEmailTemplate(String sender,
                          List<String> recipients, String subject, String template,
                          Map<String, Object> variables) {
        Context context = new Context(new Locale("pt", "BR"));

        variables.entrySet().forEach(
                e -> context.setVariable(e.getKey(), e.getValue()));

        String message = thymeleaf.process(template, context);

        sendEmail(sender, recipients, subject, message);
    }

    public void sendEmail(String sender,
                            List<String> recipients, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(recipients.toArray(new String[recipients.size()]));
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Something is wrong!", e);
        }
    }

}
