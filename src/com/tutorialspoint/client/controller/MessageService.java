package com.tutorialspoint.client.controller;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.FileUpload;

@RemoteServiceRelativePath("message")
public interface MessageService extends RemoteService {
	ContentDB getMessage(String input, String databasePath, String databaseUser, String databasePW);
   
	ContentDB getMessageForCreate(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW);   
   
   Message getMessageUpload(String databaseFolder, String databasePath, String databaseUser, String databasePW);

}