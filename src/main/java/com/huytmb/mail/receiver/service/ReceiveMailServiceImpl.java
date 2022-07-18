package com.huytmb.mail.receiver.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.Status;
import com.huytmb.mail.receiver.model.attachementsModel;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.repository.MailRepo;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class ReceiveMailServiceImpl implements ReceiveMailService {
	
	@Autowired
	private MailRepo mr;
	
	
    private static final Logger log = LoggerFactory.getLogger(ReceiveMailServiceImpl.class);

    private static final String DOWNLOAD_FOLDER = "data";

    private static final String DOWNLOADED_MAIL_FOLDER = "DOWNLOADED";

    @Override
    public void handleReceivedMail(MimeMessage receivedMessage) {
        try {

            Folder folder = receivedMessage.getFolder();
            folder.open(Folder.READ_WRITE);
            System.out.println("les messagesssssssssssssssssssssssssssssssssssssss recuuuuuuuuuu"+receivedMessage.getContent());

            Message[] messages = folder.getMessages();
            fetchMessagesInFolder(folder, messages);

            Arrays.asList(messages).stream().filter(message -> {
                MimeMessage currentMessage = (MimeMessage) message;
                try {
                    return currentMessage.getMessageID().equalsIgnoreCase(receivedMessage.getMessageID());
                } catch (MessagingException e) {
                    log.error("Error occurred during process message", e);
                    return false;
                }
            }).forEach(this::extractMail);

           // copyMailToDownloadedFolder(receivedMessage, folder);

           // folder.close(true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void fetchMessagesInFolder(Folder folder, Message[] messages) throws MessagingException {
        FetchProfile contentsProfile = new FetchProfile();
        contentsProfile.add(FetchProfile.Item.ENVELOPE);
        contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
        contentsProfile.add(FetchProfile.Item.FLAGS);
        contentsProfile.add(FetchProfile.Item.SIZE);
        folder.fetch(messages, contentsProfile);
    }

    private void copyMailToDownloadedFolder(MimeMessage mimeMessage, Folder folder) throws MessagingException {
        Store store = folder.getStore();
        Folder downloadedMailFolder = store.getFolder(DOWNLOADED_MAIL_FOLDER);
        if (downloadedMailFolder.exists()) {
            downloadedMailFolder.open(Folder.READ_WRITE);
            downloadedMailFolder.appendMessages(new MimeMessage[]{mimeMessage});
            downloadedMailFolder.close();
        }
    }
    

    private void extractMail(Message message) {
        try {
            final MimeMessage messageToExtract = (MimeMessage) message;
            final MimeMessageParser mimeMessageParser = new MimeMessageParser(messageToExtract).parse();

            mailModel mail =showMailContent(mimeMessageParser);

           downloadAttachmentFiles(mimeMessageParser, mail);
          

            // To delete downloaded email
            messageToExtract.setFlag(Flags.Flag.DELETED, false);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private mailModel showMailContent(MimeMessageParser mimeMessageParser) throws Exception {
        log.debug("From: {} to: {} | Subject: {}", mimeMessageParser.getFrom(), mimeMessageParser.getTo(), mimeMessageParser.getSubject());
        log.debug("Mail content: {}", mimeMessageParser.getPlainContent());
        log.debug("attachment {}",mimeMessageParser.getAttachmentList().size());
        log.debug("attachment {}",mimeMessageParser.getAttachmentList().toString());




        
        System.out.println("mes donneess a sauvgarder"+mimeMessageParser.getFrom());
        System.out.println("mes donneess a sauvgarder"+mimeMessageParser.getTo());
        System.out.println("mes donneess a sauvgarder"+mimeMessageParser.getSubject());



        
      mailModel mail=new mailModel();
        mail.setSenderAddress(mimeMessageParser.getFrom());
        mail.setSubject(mimeMessageParser.getSubject());
        mail.setCc(mimeMessageParser.getPlainContent());
        mail.setStatus(Status.nontraiter);
        
        
       
       // mail.writeTo(new FileOutputStream(new File("c:/mail.eml")));
        return ajouterMail(mail);
        
    }
	@Transactional
	public  mailModel ajouterMail(mailModel mail){
	return mr.save(mail);
	}
    	
    private void downloadAttachmentFiles(MimeMessageParser mimeMessageParser,mailModel mail) {
       // log.debug("Email has {} attachment files", mimeMessageParser.getAttachmentList().size());
      mimeMessageParser.getAttachmentList().forEach(dataSource -> {
            if (StringUtils.isNotBlank(dataSource.getName())) {
                String rootDirectoryPath = new FileSystemResource("").getFile().getAbsolutePath();
                String dataFolderPath = rootDirectoryPath + File.separator + DOWNLOAD_FOLDER;
                createDirectoryIfNotExists(dataFolderPath);

                
				String downloadedAttachmentFilePath = rootDirectoryPath + File.separator + DOWNLOAD_FOLDER + File.separator + dataSource.getName();
                File downloadedAttachmentFile = new File(downloadedAttachmentFilePath);

                log.info("Save attachment file to: {}", downloadedAttachmentFilePath);
                mail.getAttachments().add(new attachementsModel(downloadedAttachmentFilePath,mail));
                try (
                        OutputStream out = new FileOutputStream(downloadedAttachmentFile)

                       //  InputStream in = dataSource.getInputStream()
                ) {
                    InputStream in = dataSource.getInputStream();
                    IOUtils.copy(in, out);
                } catch (IOException e) {
                    log.error("Failed to save file.", e);
                }

            }
        });
      ajouterMail(mail);
    }
    
    
    			public attachementsModel  getAttachmentsByMailID(int idMail,int idAtt){
    				Optional<mailModel> mail = mr.findById(idMail);
    				if(mail.isPresent()){
    					attachementsModel attachment=mail.get().getAttachments().stream().filter(a->a.getIdAtt()==idAtt).findAny().orElse(null);
    					return attachment;
    				}    			
    				return null;
    			}
    			
    			
    private void createDirectoryIfNotExists(String directoryPath) {
        if (!Files.exists(Paths.get(directoryPath))) {
            try {
                Files.createDirectories(Paths.get(directoryPath));
            } catch (IOException e) {
                log.error("An error occurred during create folder: {}", directoryPath, e);
            }
        }
    }
 
}
