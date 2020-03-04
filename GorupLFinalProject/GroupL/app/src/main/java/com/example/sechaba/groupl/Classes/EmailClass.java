package com.example.sechaba.groupl.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import javax.mail.Message;

import javax.mail.Authenticator;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Motor-pie on 10/11/2017.
 */

public class EmailClass extends AsyncTask<Object, Void, Boolean> {
    private Properties prop;
    private Session session;
    private Message message;
    private MimeBodyPart mimeBodyPart;
    private Multipart multipart;
    private String[] recipientList;
    private Context context;

    public EmailClass(Context con) {
        context = con;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        try {
            prop = smtpCofig();

            session = Session.getDefaultInstance(prop, authenticator);

            message = new MimeMessage(session);

            message.setFrom(new InternetAddress("pontshomodisenyane1@gmail.com"));

            recipientList = (String[]) objects[0];

            InternetAddress[] recipients = new InternetAddress[recipientList.length];

            for (int i = 0; i < recipientList.length; i++) {
                recipients[i] = new InternetAddress(recipientList[i]);
            }

            message.setRecipients(Message.RecipientType.TO, recipients);

            message.setSubject(objects[1].toString());

            message.setSentDate(new Date());

            mimeBodyPart = new MimeBodyPart();

            mimeBodyPart.setContent(objects[2].toString(), "text/html");

            multipart = new MimeMultipart();

            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            Log.i(EmailClass.class.getName(), "Email Has Been Sent");

            return true;
        } catch (MessagingException e) {
            Log.e(EmailClass.class.getName(), e.getMessage());
        }


        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        String message = aBoolean ? "Email Has been Sent!!" : "Error Sending Email";

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    private Properties smtpCofig() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", "pontshomodisenyane1@gmail.com");
        properties.put("mail.password", "ebenezerwam");

        properties.put("mail.smtp.timeout", "20000");

        properties.put("mail.smtp.connectiontimeout", "20000");

        return properties;
    }

    private Authenticator authenticator = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("pontshomodisenyane1@gmail.com", "ebenezerwam");
        }
    };

}