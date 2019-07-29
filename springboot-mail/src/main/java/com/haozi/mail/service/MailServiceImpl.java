package com.haozi.mail.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/7/26
 */
@Component
public class MailServiceImpl implements MailService{
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String[] to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setCc(cc);
        try {
            mailSender.send(message);
            LOG.info("邮件发送成功.");
        } catch (Exception e) {
            LOG.warn("邮件发送失败.");
        }
    }

    @Override
    public void sendHtmlMail(String[] to, String subject, String content, String... cc){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setCc(cc);
            mailSender.send(message);
            LOG.info("邮件发送成功.");
        } catch (MessagingException e) {
            LOG.warn("邮件发送失败.");
        }
    }

    @Override
    public void sendAttachmentsMail(String[] to, String subject, String content, String[] filePaths, String... cc) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setCc(cc);
            if (filePaths != null) {
                for (String filePath : filePaths) {
                    if (StringUtils.isBlank(filePath)) continue;
                    FileSystemResource file = new FileSystemResource(new File(filePath));
                    if (file != null) {
                        String fileName = file.getFilename();
                        helper.addAttachment(fileName, file);
                    }
                }
            }
            mailSender.send(message);
            LOG.info("邮件发送成功.");
        } catch (MessagingException e) {
            LOG.warn("邮件发送失败.");
        }
    }

    @Override
    public void sendInlineResourcesMail(String[] to, String subject, String content, Map<String, String> resources, String... cc) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setCc(cc);
            if (resources != null && !resources.isEmpty()) {
                for (Map.Entry<String, String> entry : resources.entrySet()) {
                    String rscId = entry.getKey();
                    String rscPath = entry.getValue();
                    if (StringUtils.isBlank(rscId) || StringUtils.isBlank(rscPath)) continue;
                    FileSystemResource res = new FileSystemResource(new File(rscPath));
                    if (res != null) {
                        helper.addInline(rscId, res);
                    }
                }
            }
            mailSender.send(message);
            LOG.info("邮件发送成功.");
        } catch (MessagingException e) {
            LOG.warn("邮件发送失败.");
        }
    }
}
