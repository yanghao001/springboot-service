package com.haozi.mail;

import com.haozi.mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送测试
 *
 * @auther Phantom Gui
 * @date 2018/6/14 9:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    private String[] to = new String[]{"yourusername@qq.com"};

    // 测试发送普通文本格式邮件
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail(to, "主题：springboot测试", "这是一封测试springboot发送普通邮件的测试邮件", "yourusername@gmail.com");
    }

    // 测试发送HTML页面格式的页面
    @Test
    public void sendHtmlMail() {
        String content = "<html>\n" + "<body>\n" + "<h3>这是一封测试springboot发送HTML邮件的测试邮件</h3>\n" + "</body>\n" + "</html>";
        mailService.sendHtmlMail(to, "主题：springbootHTML邮件测试", content, "yourusername@gmail.com");
    }

    // 测试发送带附件的邮件
    @Test
    public void sendAttachmentsMail() {
        String[] filePaths = new String[]{
            "D:\\tmp\\tomcat-api.jar",
             "D:\\tmp\\resources.jar"
        };
        mailService.sendAttachmentsMail(to, "主题：springboot带附件邮件测试", "这是一封测试springboot发送带附件邮件的测试邮件", filePaths, "yourusername@gmail.com");
    }

    // 测试发送嵌入图片等静态资源的邮件
    @Test
    public void sendInlineResourceMail() {
        String rscId = "10001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String rscPath = "D:\\tmp\\springboot.png";
        Map<String, String> resources = new HashMap<>();
        resources.put(rscId, rscPath);
        mailService.sendInlineResourcesMail(to, "主题：springboot静态资源邮件测试", content, resources, "yourusername@gmail.com");
    }

    // 测试发送激活邮件
    @Test
    public void activateMailTest() {
        Context context = new Context();
        context.setVariable("id", "10001");
        String content = templateEngine.process("activateMailTemplate", context);
        mailService.sendHtmlMail(to, "主题：账户激活模板邮件", content);
    }
}