package com.tutorialspoint.client.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;

public interface MessageServiceAsync {
   void getMessage(String input, String databasePath, String databaseUser, String databasePW, AsyncCallback<ContentDB> callback);

void getMessageForCreate(String databaseNewName, String databaseName, String databasePath, String databaseUser,
		String databasePW, AsyncCallback<ContentDB> callback);

void getMessageUpload(String databaseFolder, String databasePath, String databaseUser, String databasePW,
		AsyncCallback<Message> messageCallBack);

}