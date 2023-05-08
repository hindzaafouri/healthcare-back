package tn.esprit.healthcare.Services;


import tn.esprit.healthcare.Payload.Mail;

public interface EmailService {

    public void sendCodeByMail(Mail mail);
    public void sendMail(String to, String subject, String body);
}
