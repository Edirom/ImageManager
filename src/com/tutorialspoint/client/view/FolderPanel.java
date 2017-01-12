package com.tutorialspoint.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.model.Folder;
import com.tutorialspoint.client.model.FolderTreeModel;

public class FolderPanel {

	final static SingleSelectionModel<Folder> selectionModel = new SingleSelectionModel<Folder>();

	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	Button createFolderButton;

	public void createEditorView(Map<List<String>, String> map){

		RootPanel.get("gwtContainer").clear();

		createFolderButton = new Button("Create Folder");
		createFolderButton.setWidth("100px");
		createFolderButton.setEnabled(false);
		createFolderButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Folder node =   selectionModel.getSelectedObject();

				CreateFolderDialog createFolderDialog = new CreateFolderDialog();
				createFolderDialog.createFields();

				//ConfigurationDialog myDialog = new ConfigurationDialog(node);

				int left = Window.getClientWidth()/ 3;
				int top = Window.getClientHeight()/ 3;
				createFolderDialog.setPopupPosition(left, top);
				createFolderDialog.show();

				// messageCreateService.getMessage(node.getName(), composer.getName(), new MessageCreateCallBack());

			}
		});

		Button uploadDataButton = new Button("Upload Data");
		uploadDataButton.setWidth("100px");
		//uploadDataButton.addStyleName("gwt-Green-Button");
		uploadDataButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Composer node =   selectionModel.getSelectedObject();

				UploadDataDialog uploadDataDialog = new UploadDataDialog();
				uploadDataDialog.createFields();

				//ConfigurationDialog myDialog = new ConfigurationDialog("uploadData");

				int left = Window.getClientWidth()/ 3;
				int top = Window.getClientHeight()/ 3;
				uploadDataDialog.setPopupPosition(left, top);
				uploadDataDialog.show();
				//Window.alert("Green Button clicked!");
			}
		});

		Button deleteButton = new Button("Delete");
		deleteButton.setWidth("100px");	     
		//deleteButton.addStyleName("gwt-Delete-Button");
		//	      deleteButton.addClickHandler(new ClickHandler() {
		//		         @Override
		//		         public void onClick(ClickEvent event) {
		//		            Window.alert("Blue Button clicked!");
		//		         }
		//		      });

		Button tileImagesButton = new Button("Tile Image(s)");
		tileImagesButton.setWidth("100px");	     
		//tileImagesButton.addStyleName("gwt-Blue-Button");
		tileImagesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				//Window.alert("Blue Button clicked!");
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);
		panel.addStyleName("gwt-VerticalPanel");
		// panel.add(loginButton);
		panel.add(createFolderButton);
		panel.add(uploadDataButton);	      
		panel.add(tileImagesButton);
		panel.add(deleteButton);

		// Create a model for the tree.
		FolderTreeModel model = new FolderTreeModel(map, ImageManager.databaseName, selectionModel);
		CellTree tree = new CellTree(model, null);
		tree.addStyleName("gwt-CollectionTree");
		tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

	        public void onSelectionChange(SelectionChangeEvent event) {
               if(selectionModel.getSelectedObject() != null && selectionModel.getSelectedObject().getTypeFolder().equals("Folder")){            	   
            	   createFolderButton.setEnabled(true);
               }
               else{
            	   createFolderButton.setEnabled(false);
               }
	        }
	    });
		
		tree.addOpenHandler(new OpenHandler<TreeNode>(){ 
			@Override
			public void onOpen(OpenEvent<TreeNode> event) {
				Folder selectedFolder = null;
				if(selectionModel.getSelectedObject() == null){
					selectedFolder = (Folder) event.getTarget().getValue();
					selectionModel.setSelected(selectedFolder, true);

				}
				else{
					Folder currSelectedFolder = selectionModel.getSelectedObject();
					Folder eventFolder = (Folder) event.getTarget().getValue();
					if(!currSelectedFolder.equals(eventFolder)){
						selectedFolder = eventFolder;
						selectionModel.setSelected(selectedFolder, true);
					}
					else{
						selectedFolder = selectionModel.getSelectedObject();
					}

				}
				
				String vollPath = selectedFolder.getPath();
				
				
				
				messageService.getMessage(vollPath, ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
			}
		});
		
		tree.addCloseHandler(new CloseHandler<TreeNode>(){ 
			@Override
			public void onClose(CloseEvent<TreeNode> event) {				
				Folder selectedFolder = null;
				if(selectionModel.getSelectedObject() == null){
					selectedFolder = (Folder) event.getTarget().getValue();
					selectionModel.setSelected(selectedFolder, true);

				}
				else{
					Folder currSelectedFolder = selectionModel.getSelectedObject();
					Folder eventFolder = (Folder) event.getTarget().getValue();
					if(!currSelectedFolder.equals(eventFolder)){
						selectedFolder = eventFolder;
						selectionModel.setSelected(selectedFolder, true);
					}
					else{
						selectedFolder = selectionModel.getSelectedObject();
					}

				}
				selectedFolder.getFolderlists().clear();
				}
		});

		TreeNode rootNode = tree.getRootTreeNode(); 
		rootNode.setChildOpen(0, true, false);

		ScrollPanel panel_1 = new ScrollPanel();	    
		panel_1.setWidth("330");
		panel_1.setHeight("700");
		panel_1.addStyleName("scrollPanelText");		
		panel_1.add(tree);

		SplitLayoutPanel p = new SplitLayoutPanel();
		p.getElement().getStyle().setProperty("border", "3px solid #e7e7e7");
		p.setWidth("500");
		p.setHeight("700");
		p.addWest(panel_1, 330);

		p.add(panel);

		RootPanel.get("gwtContainer").add(p);
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

			//			TempDialog myDialog = new TempDialog(result.getMessage());
			//
			//			int left = Window.getClientWidth()/ 2;
			//			int top = Window.getClientHeight()/ 2;
			//			myDialog.setPopupPosition(left, top);
			//			myDialog.show();

			Folder selectedFolder =   selectionModel.getSelectedObject();

			Map<List<String>, String> resultMap = result.getMessage();

			String listOutput = "";
			for(List<String> nameKey : resultMap.keySet()){
				listOutput =listOutput + " ,"+ nameKey.get(0);
				Folder composer = new Folder(nameKey.get(0));
				composer.setTypeFolder(nameKey.get(1));
				selectedFolder.addFolderlist(composer);
				composer.setParent(selectedFolder);

			}

			selectedFolder.refresh();

		}	   
	}

}
