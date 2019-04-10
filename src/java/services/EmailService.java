package services;

import domain.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * EmailService - Class used to send an email to a specified email address
 *                and uses a specified template to send the email with.
 * 
 * @author Harmanjit Mohaar (000758243)
 */
public class EmailService {
    
    public static void sendOrderUpdate(String email, Order order, String comments, String status, String template)
    {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", order.getAccount().getFirstname());
        tags.put("orderId", "" + order.getOrderId());
        tags.put("status", status);
        tags.put("comments", comments);
        sendMail(email, "Order Status Update: #" + order.getOrderId(), template + "/notificationtemplates/orderupdate.html", tags, "%%%", "%%%");
    }
    
    /**
     * Method used to create the email and setup tags and information specified in the email
     * 
     * @param email The email to send the message to
     * @param subject The subject of the email
     * @param template The template being used to format the email
     * @param tags The specified tags that are used in the template - %%%name%%%
     * @param replaceBegin The beginning value for the tags - %%%
     * @param replaceEnd The ending value for the tags - %%%
     */
    public static void sendMail(String email, String subject,
            String template, HashMap<String, String> tags, String replaceBegin, String replaceEnd) {
        try {
            //Read the template as a String
            BufferedReader br = new BufferedReader(new FileReader(new File(template)));
            String line = br.readLine();
            String body = "";
            while(line != null) {
                body += line;
                line = br.readLine();
            }
            
            
            //Replace all %%% signs with values
            for(String tag : tags.keySet()) {
                body = body.replace(replaceBegin + tag + replaceEnd, tags.get(tag));
            }
            
            //Send the email
            sendMail(email, subject, body, true);
            
        } catch (Exception ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Method used by the previous sendMail method, however this one sends the actual message to the recipient
     * 
     * @param to The address to send the email to
     * @param subject The subject of the email
     * @param body The body of the email to send
     * @param bodyIsHTML Does the body consist of HTML?
     * @throws MessagingException This is thrown if an error occurs while sending the email
     * @throws NamingException This is thrown if the naming of an email is incorrect
     * @throws java.io.UnsupportedEncodingException If the email being hidden is incorrect
     */
    public static void sendMail(String to, String subject, String body, boolean bodyIsHTML) throws MessagingException, NamingException, UnsupportedEncodingException {
        //This is the login information for the account to use
        //Context env = (Context)new InitialContext().lookup("java:comp/env");
        String username = "aris3dprinting@gmail.com";//(String)env.lookup("webmail-username");
        String password = "adminAri$3d";//(String)env.lookup("webmail-password");
        
        //Setup the properties for sending the email.
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        
        //Create the message and set its subject values
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }
        
        //Set the address of the message we are sending
        Address fromAddress = new InternetAddress(username, "ARIS3D");
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);
        
        //Finally send the message to the specified email address
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
