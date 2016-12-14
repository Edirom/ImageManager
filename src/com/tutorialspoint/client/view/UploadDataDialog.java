package com.tutorialspoint.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.Message;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.view.LoginDialog.TempDialog;

public class UploadDataDialog  extends DialogBox {
	
	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	native void jsClickUpload(Element pElement) /*-{
    pElement.click();
}-*/;
	
	public UploadDataDialog() {
		
		// Set the dialog box's caption.
        setText("Upload Image");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);
        
	}
	
	public void createFields() {
             
     // Create a FormPanel and point it at a service.
                 final FormPanel form = new FormPanel();
                 form.setAction(GWT.getModuleBaseURL() + "upload");
         
                 // Because we're going to add a FileUpload widget, we'll need to set the
                 // form to use the POST method, and multipart MIME encoding.
                 form.setEncoding(FormPanel.ENCODING_MULTIPART);
                 form.setMethod(FormPanel.METHOD_POST);
         
                 // Create a panel to hold all of the form widgets.
                 
                 
                 Label lblDatenBankPath = new Label("Database path");
                 
                 // Create a TextBox, giving it a name so that it will be submitted.
                 final TextBox tb = new TextBox();
                 tb.setName("textBoxFormElement");
//                 tb.addKeyUpHandler(new KeyUpHandler() {
//    	             @Override
//    	             public void onKeyUp(KeyUpEvent event) {
//    	            	 tb.setName(tb.getValue());
//    	            	 
//
//    	             }
//    	         });
                 
                
         
                 // Create a ListBox, giving it a name and some values to be associated
                 // with its options.
//                 ListBox lb = new ListBox();
//                 lb.setName("listBoxFormElement");
//                 lb.addItem("foo", "fooValue");
//                 lb.addItem("bar", "barValue");
//                  lb.addItem("baz", "bazValue");
//                 panel.add(lb);
        
                 // Create a FileUpload widget.
                 final FileUpload upload = new FileUpload();
                 upload.setName("uploadFormElement");
                 upload.getElement().setPropertyBoolean("multiple", true);
         
                 // Add an event handler to the form.
                 form.addSubmitHandler(new FormPanel.SubmitHandler() {
                   public void onSubmit(SubmitEvent event) {
                         // This event is fired just before the form is submitted. We can
                         // take this opportunity to perform validation.
                         if (upload.getFilename().length() == 0) {
                             Window.alert("The text box must not be empty");
                             event.cancel();
                         }
                     }
                 });
         
                 form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
                     public void onSubmitComplete(SubmitCompleteEvent event) {
                         // When the form submission is successfully completed, this
                         // event is fired. Assuming the service returned a response of type
                       // text/html, we can get the result text here (see the FormPanel
                         // documentation for further explanation).
                        // Window.alert(event.getResults());
                    	 messageService.getMessageUpload(tb.getValue(), ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
                    	 hide();
                     }
               });                   
                 
                 HorizontalPanel hPanel_1 = new HorizontalPanel();	
   	 	      hPanel_1.add(lblDatenBankPath);
   	 	      hPanel_1.add(tb);
   	 	     hPanel_1.setCellWidth(lblDatenBankPath, "130");
   	 	     
   	 	    HorizontalPanel hPanelBoxes = new HorizontalPanel();
   	 	  // hPanelBoxes.add(cancelButton);
   	 	 // Add a 'submit' button.
   	 	hPanelBoxes.add(new Button("Submit", new ClickHandler() {
               public void onClick(ClickEvent event) {
            	   //String filename = ( (FileUpload) event.getSource() ).getFilename();
            	   //Window.alert(filename);
                   form.submit();
               }
           }));
           
           // Add a 'Cancel' button.
   	 hPanelBoxes.add(new Button("Cancel", new ClickHandler() {
               public void onClick(ClickEvent event) {
                   hide();
               }
           }));   	 	   
   	 	  //hPanelBoxes.add(okButton);
   	 	 hPanelBoxes.setSpacing(10);
   	 	// hPanelBoxes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
   	 	
   	 	VerticalPanel panel = new VerticalPanel();
        form.setWidget(panel);
        panel.add(hPanel_1);
        panel.add(upload);
        panel.add(hPanelBoxes);
    
        
   	 	     
   	        // VerticalPanel panel = new VerticalPanel();
   	         //panel.setHeight("00");
   	        // panel.setWidth("00");
   	         panel.setSpacing(10);
   	        // panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_DEFAULT);
   	         //panel.add(hPanel);
   	         //panel.add(hPanel_1);
   	        // panel.add(hPanelBoxes);
                 
                 

        //panel.setSpacing(10);
        setWidget(form);
		
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
