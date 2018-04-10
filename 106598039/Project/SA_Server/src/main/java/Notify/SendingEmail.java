package Notify;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendingEmail{
    private String SenderName;
    private String SenderPassword;
    private String SenderAddress;
    private String ReceiverAddress;
    private String Subject;
    private String Body;
    public SendingEmail(String SenderName, String SenderPassword, String SenderAddress, String ReceiverAddress, String Subject, String Body){
        this.SenderName = SenderName;
        this.SenderPassword = SenderPassword;
        this.SenderAddress = SenderAddress;
        this.ReceiverAddress = ReceiverAddress;
        this.Subject = Subject;
        this.Body = Body;
        Sending();
    }

    public void Sending(){
        // 寄件的smtp伺服器
        String host = "smtp.ntut.edu.tw";

        // 建立一個Properties來設定Properties
        Properties properties = System.getProperties();

        //設定傳輸協定為smtp
        properties.setProperty("mail.transport.protocol", "smtp");
        //設定mail Server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "25");
        //需要驗證帳號密碼
        properties.put("mail.smtp.auth", "true");
        //Bypass the SSL authentication
        properties.put("mail.smtp.ssl.enable", false);
        properties.put("mail.smtp.starttls.enable", false);

        //帳號，密碼
        SmtpAuthenticator authentication = new SmtpAuthenticator(SenderName, SenderPassword);

        // 建立一個Session物件，並把properties傳進去
        Session mailSession = Session.getDefaultInstance(properties, authentication);

        try {
            //建立一個 MimeMessage object.
            MimeMessage message = new MimeMessage(mailSession);
            // 設定寄件人
            message.setFrom(new InternetAddress(SenderAddress));

            // 設定收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(ReceiverAddress));

            // 設定主旨
            message.setSubject(Subject);

            //設定內文
            message.setText(Body);

            Transport transport = mailSession.getTransport();

            // 傳送信件
            transport.send(message);

            System.out.println("發送成功");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
