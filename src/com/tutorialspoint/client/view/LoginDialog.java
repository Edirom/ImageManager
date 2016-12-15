package com.tutorialspoint.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.Message;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;

public class LoginDialog  extends DialogBox {

	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	public LoginDialog() {
		setText("Database Login");
		setAnimationEnabled(true);
		setGlassEnabled(true);
	}

	public void createFields() {

		final Button ok = new Button("Connenct");

		final TextBox txtDatenBankUser = new TextBox(); 
		txtDatenBankUser.setWidth("300");
		txtDatenBankUser.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				ImageManager.databaseUser = txtDatenBankUser.getValue();
				if(ImageManager.databaseName.length() >0 && ImageManager.databasePath.length() >0 && ImageManager.databaseUser.length() >0) {
					ok.setEnabled(true);
				}
				else{
					ok.setEnabled(false);
				}
			}
		});
		Label lblDatenBankUser = new Label("User");

		final PasswordTextBox txtDatenBankPW = new PasswordTextBox(); 
		txtDatenBankPW.setWidth("300");
		txtDatenBankPW.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				ImageManager.databasePW = txtDatenBankPW.getValue();
				if(ImageManager.databaseName.length() >0 && ImageManager.databasePath.length() >0 && ImageManager.databaseUser.length() >0) {
					ok.setEnabled(true);
				}
				else{
					ok.setEnabled(false);
				}
			}
		});
		Label lblDatenBankPW = new Label("Password");

		final TextBox txtDatenBankName = new TextBox(); 
		txtDatenBankName.setWidth("300");
		txtDatenBankName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				ImageManager.databaseName = txtDatenBankName.getValue();
				if(ImageManager.databaseName.length() >0 && ImageManager.databasePath.length() >0 && ImageManager.databaseUser.length() >0) {
					ok.setEnabled(true);
				}
				else{
					ok.setEnabled(false);
				}
			}
		});
		Label lblDatenBankName = new Label("Database Name");
		final TextBox txtDatenBankPath = new TextBox();
		ImageManager.databasePath = "xmldb:exist://localhost:8080/exist/xmlrpc";
		txtDatenBankPath.setValue(ImageManager.databasePath);
		txtDatenBankPath.setWidth("300");
		txtDatenBankPath.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				ImageManager.databasePath = txtDatenBankPath.getValue();
				if(ImageManager.databaseName.length() >0 && ImageManager.databasePath.length() >0 && ImageManager.databaseUser.length() >0) {
					ok.setEnabled(true);
				}
				else{
					ok.setEnabled(false);
				}
			}
		});
		Label lblDatenBankPath = new Label("Datenbase Path");

		ok.setEnabled(false);
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LoginDialog.this.hide();          	   
				messageService.getMessage(ImageManager.databaseName, ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
			}
		});

		HorizontalPanel hPanelUserName = new HorizontalPanel();	
		hPanelUserName.add(lblDatenBankUser);
		hPanelUserName.add(txtDatenBankUser);
		hPanelUserName.setCellWidth(lblDatenBankUser, "130");

		HorizontalPanel hPanelPW = new HorizontalPanel();	
		hPanelPW.add(lblDatenBankPW);
		hPanelPW.add(txtDatenBankPW);
		hPanelPW.setCellWidth(lblDatenBankPW, "130");

		HorizontalPanel hPanel = new HorizontalPanel();	
		hPanel.add(lblDatenBankName);
		hPanel.add(txtDatenBankName);
		hPanel.setCellWidth(lblDatenBankName, "130");

		HorizontalPanel hPanel_1 = new HorizontalPanel();	
		hPanel_1.add(lblDatenBankPath);
		hPanel_1.add(txtDatenBankPath);
		hPanel_1.setCellWidth(lblDatenBankPath, "130");

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("00");
		panel.setWidth("00");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LOCALE_END);
		panel.add(hPanelUserName);
		panel.add(hPanelPW);
		panel.add(hPanel);
		panel.add(hPanel_1);
		panel.add(ok);

		setWidget(panel);
	}


	private class MessageCallBack implements AsyncCallback<ContentDB> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: " + caught.getMessage());	
		}
		@Override
		public void onSuccess(ContentDB result) {
			/* server returned result, show user the message */

			TempDialog myDialog = new TempDialog(result.getMessage());

			int left = Window.getClientWidth()/ 2;
			int top = Window.getClientHeight()/ 2;
			myDialog.setPopupPosition(left, top);
			myDialog.show();
			
			FolderPanel folderPanel = new FolderPanel();

			folderPanel.createEditorView(result.getMessage());
			
			

		}	   
	}


	public static class TempDialog extends DialogBox {

		public TempDialog(Map<List<String>, String> map) {

			setText("Result");
			setAnimationEnabled(true);
			setGlassEnabled(true);

			Button ok = new Button("OK");
			ok.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					TempDialog.this.hide();
				}
			});
			HTML html1 = new HTML(map.toString());

			VerticalPanel panel = new VerticalPanel();
			panel.setHeight("500");
			panel.setWidth("00");
			panel.setSpacing(10);
			panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			panel.add(html1);
			panel.add(ok);

			setWidget(panel);
		}
	}


}
