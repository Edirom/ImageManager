package com.tutorialspoint.client.view;

import java.util.List;
import java.util.Map;

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
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.model.Folder;

public class UploadDataDialog  extends DialogBox {

	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	private Folder selectedFolder;

	native void jsClickUpload(Element pElement) /*-{
    pElement.click();
}-*/;

	public UploadDataDialog() {
		setText("Upload Image(s)");

		setAnimationEnabled(true);

		setGlassEnabled(true);
	}

	public void createFields() {

		final FormPanel form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() + "upload");

		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		Label lblDatenBankPath = new Label("Databasepath");
		final TextBox tb = new TextBox();
		tb.setName("textBoxFormElement");
		tb.setWidth("300");
		selectedFolder = FolderPanel.selectionModel.getSelectedObject();
		String selectedPath = selectedFolder.getPath();        
		tb.setValue(selectedPath);
		tb.setEnabled(false);
	
		final FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		upload.getElement().setPropertyBoolean("multiple", true);
		upload.addStyleName("gwt-FileUpload");
		upload.setWidth("430");
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				if (upload.getFilename().length() == 0) {
					Window.alert("The text box must not be empty");
					event.cancel();
				}
			}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				messageService.getMessageUpload(tb.getValue(), ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
				hide();
			}
		});  

		HorizontalPanel hPanel_1 = new HorizontalPanel();	
		hPanel_1.add(lblDatenBankPath);
		hPanel_1.add(tb);
		hPanel_1.setCellWidth(lblDatenBankPath, "130");

		HorizontalPanel hPanelBoxes = new HorizontalPanel();
		hPanelBoxes.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		}));

		hPanelBoxes.add(new Button("Cancel", new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		}));   	 	   
		hPanelBoxes.setSpacing(10);
		
		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("00");
		panel.setWidth("00");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		panel.add(hPanel_1);
		panel.add(upload);
		panel.add(hPanelBoxes);

		form.setWidget(panel);

		setWidget(form);

	}

	private class MessageCallBack implements AsyncCallback<ContentDB> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: " 
					+ caught.getMessage());	
		}
		@Override
		public void onSuccess(ContentDB result) {
			Map<List<String>, String> resultMap = result.getMessage();

			String listOutput = "";
			for(List<String> nameKey : resultMap.keySet()){
				listOutput =listOutput + " ,"+ nameKey.get(0);
				Folder newFolder = new Folder(nameKey.get(0));
				newFolder.setTypeFolder(nameKey.get(1));
				selectedFolder.addFolderlist(newFolder);
				newFolder.setParent(selectedFolder);

			}

			selectedFolder.refresh();
		}	   
	}

}
