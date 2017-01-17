package com.tutorialspoint.client.controller;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("message")
public interface MessageService extends RemoteService {
	ContentDB getMessage(String input, String databasePath, String databaseUser, String databasePW);
   
	ContentDB getMessageForCreate(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW); 
	
	ContentDB getMessageForDelete(String selectedPath, String databasePath, String databaseUser, String databasePW);
   
	ContentDB getMessageUpload(String databaseFolder, String databasePath, String databaseUser, String databasePW);
	
	ContentDB getMessageImageTiles(String databaseFolder, String databasePath, String databaseUser, String databasePW);

}