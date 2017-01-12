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

public class CreateFolderDialog extends DialogBox {

	private MessageServiceAsync messageService = GWT.create(MessageService.class);

	private String newName = "";
	private String newPath = "";
	private Button okButton = new Button("Create");

	private Folder selectedFolder;

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

		okButton.setEnabled(false);
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateFolderDialog.this.hide(); 
				messageService.getMessageForCreate(newName, newPath, ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());

			}
		});

		final TextBox txtDatenBankPath = new TextBox();	         	         
		txtDatenBankPath.setWidth("300");

		selectedFolder = FolderPanel.selectionModel.getSelectedObject();
		newPath = selectedFolder.getPath();        
		txtDatenBankPath.setValue(newPath);
		txtDatenBankPath.setEnabled(false);
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

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("00");
		panel.setWidth("00");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		panel.add(hPanel);
		panel.add(hPanel_1);
		panel.add(hPanelBoxes);

		setWidget(panel);
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
