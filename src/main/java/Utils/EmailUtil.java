package Utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class EmailUtil {

    public static void sendResetPasswordEmail(String toEmail, String token) {
    	
        // Cấu hình thông tin kết nối tới Gmail SMTP server
        String host = "smtp.gmail.com";
        final String username = "nkiet0508@gmail.com"; // Thay bằng email của bạn
        final String appPassword = "gtku jbgi kigd bzzr"; // Thay bằng mật khẩu ứng dụng của bạn

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên gửi email
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            // Soạn email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác nhận reset mật khẩu");

            // Cập nhật nội dung email để gửi token
            String body = "Để reset mật khẩu, vui lòng sử dụng mã sau:\n\n" + token + "\n\nVui lòng không chia sẻ mã này với người khác.";
            message.setText(body);

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
