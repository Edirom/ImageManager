package com.tutorialspoint.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.FileUpload;

@RemoteServiceRelativePath("message")
public interface MessageService extends RemoteService {
   Message getMessage(String input, String databasePath, String databaseUser, String databasePW);
   
   Message getMessageForCreate(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW);   
   
   Message getMessageUpload(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW);

}