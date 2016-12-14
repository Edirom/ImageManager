package com.tutorialspoint.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.Message;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.view.LoginDialog.TempDialog;

public class CreateFolderDialog extends DialogBox {
	
	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	private String newName = "";
	private String newPath = "";
	private Button okButton = new Button("Create");
	
	public CreateFolderDialog() {
		
		 // Set the dialog box's caption.
        setText("Create Folder");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);
        
	}
	
	public void createFields() {
        
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickHandler(new ClickHandler() {
	            public void onClick(ClickEvent event) {
	            	hide();
	            }
	         });
        
       // Button okButton = new Button("create");
        okButton.setEnabled(false);
        okButton.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   CreateFolderDialog.this.hide(); 
           	
//           	 Composer composer = new Composer(newName);
//           	 parent.addPlaylist(composer);
//           	 parent.refresh();
//           	 
//           	 String path = databaseName +"/"+parent.getName();
//           	 if(parent.getParent() != null){
//           		 path += "/"+parent.getParent().getName();
//           	 }
           	
           	// messageCreateService.getMessageCreate("db", "testNew", "xmldb:exist://localhost:8080/exist/xmlrpc", new MessageCreateCallBack());
	        	   
        	  // messageService.getMessage(databaseName, databasePath, new MessageCallBack());
           	//String newPathName = databaseName+"/"+newName;
           	messageService.getMessageForCreate(newName, newPath, ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
        	   
           }
        });

        final TextBox txtDatenBankPath = new TextBox();	         	         
        txtDatenBankPath.setWidth("300");
        txtDatenBankPath.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
           	 newPath = txtDatenBankPath.getValue();
              	  if(newPath.length() >0 && newName.length() >0){
              		okButton.setEnabled(true);
              	  }
              	  else{
              		okButton.setEnabled(false);
              	  }
            }
        });
        Label lblDatenBankPath = new Label("Path");
        
        
        final TextBox txtDatenBankNewName = new TextBox();	         	         
        txtDatenBankNewName.setWidth("300");
        txtDatenBankNewName.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
           	 newName = txtDatenBankNewName.getValue();
           	 if(newPath.length() >0 && newName.length() >0){
              		okButton.setEnabled(true);
              	  }
              	  else{
              		okButton.setEnabled(false);
              	  }
            }
        });
        Label lblDatenBankNewName = new Label("Name");
        
            
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(lblDatenBankPath);
        hPanel.add(txtDatenBankPath);
        hPanel.setCellWidth(lblDatenBankPath, "130");
	     
	      HorizontalPanel hPanel_1 = new HorizontalPanel();	
	      hPanel_1.add(lblDatenBankNewName);
	      hPanel_1.add(txtDatenBankNewName);
	     hPanel_1.setCellWidth(lblDatenBankNewName, "130");
	     
	    HorizontalPanel hPanelBoxes = new HorizontalPanel();
	   hPanelBoxes.add(cancelButton);
	  hPanelBoxes.add(okButton);
	 hPanelBoxes.setSpacing(10);
	 //hPanelBoxes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	
	     
        VerticalPanel panel = new VerticalPanel();
        panel.setHeight("00");
        panel.setWidth("00");
        panel.setSpacing(10);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        panel.add(hPanel);
        panel.add(hPanel_1);
        panel.add(hPanelBoxes);
       
        //panel.add(okButton);

        setWidget(panel);
	}
	
	private class MessageCallBack implements AsyncCallback<Message> {
	      @Override
	      public void onFailure(Throwable caught) {
	         /* server side error occured */
	         Window.alert("Unable to obtain server response: " 
	         + caught.getMessage());	
	      }
	      @Override
	      public void onSuccess(Message result) {
	          /* server returned result, show user the message */
	    	  TempDialog myDialog = new TempDialog(result.getMessage());

	          int left = Window.getClientWidth()/ 2;
	          int top = Window.getClientHeight()/ 2;
	          myDialog.setPopupPosition(left, top);
	          myDialog.show();
	        // Window.confirm(result.getMessage());
	        // Window.enableScrolling(true);
//	          if(isCreted){
//	        	  
	     //   	  Folder node =   selectionModel.getSelectedObject();
//		        	 //Composer composer = new Composer("NewNode");
//		        	// node.addPlaylist(composer);
//		        	// node.refresh();
//	        	  
	   //     	  Map<String, String> result = result_1.getMessage();
//	        	  
//	               for(String nameKey : result.keySet()){
//	              	 Composer composer = new Composer(nameKey);
//	              	 //composers_tmp.add(composer);
//	              	 //composers.add(composer);
//	              	node.addPlaylist(composer);
//	              	
//	               }
//	               node.refresh();
//	               
//	          }
//	          else{
	          
	          FolderPanel folderPanel = new FolderPanel();

				folderPanel.createEditorView(result.getMessage(), ImageManager.databaseName);
	   //       }
	      }	   
	   }

}
