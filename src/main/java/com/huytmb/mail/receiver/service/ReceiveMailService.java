package com.huytmb.mail.receiver.service;

import javax.mail.internet.MimeMessage;


public interface ReceiveMailService {

    public void handleReceivedMail(MimeMessage message);

}