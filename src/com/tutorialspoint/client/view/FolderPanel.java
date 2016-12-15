package com.tutorialspoint.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.model.Folder;
import com.tutorialspoint.client.model.FolderTreeModel;
import com.tutorialspoint.client.view.LoginDialog.TempDialog;

public class FolderPanel {

	final static SingleSelectionModel<Folder> selectionModel = new SingleSelectionModel<Folder>();

	private MessageServiceAsync messageService = GWT.create(MessageService.class);

	public void createEditorView(Map<List<String>, String> map){


		RootPanel.get("gwtContainer").clear();
		
		Button createFolderButton = new Button("Create Folder");
		createFolderButton.setWidth("100px");
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
		//CustomTreeModel model = new CustomTreeModel(result);
		FolderTreeModel model = new FolderTreeModel(map, ImageManager.databaseName, selectionModel);

		
		//Get CellTree style using its BasicResources	   
		//CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
		/*
		 * Create the tree using the model. We use <code>null</code> 
		 * as the default value of the root node. The default value will 
		 * be passed to CustomTreeModel#getNodeInfo();
		 */
		CellTree tree = new CellTree(model, null);
		tree.addStyleName("gwt-CollectionTree");
		tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

			      tree.addOpenHandler(new OpenHandler<TreeNode>(){ 
						@Override
						public void onOpen(OpenEvent<TreeNode> event) {
							//Composer comp = selectionModel.getSelectedObject();
							//event.getTarget().getValue()
			    	        //Window.alert(selectionModel.getSelectedObject().getName());
			    	        //String name = selectionModel.getSelectedObject().getName();
							Window.alert("Unable to obtain server response: ");	
							if(selectionModel.getSelectedObject() != null){
								 String vollPath = selectionModel.getSelectedObject().getPath();
//					    	        if(selectionModel.getSelectedObject().getParent().getName().equals(databaseName)){
//					    	        	vollPath = databaseName+"/"+name;
//					    	        }
//					    	        else{
//					    	        	getParentSelection(selectionModel.getSelectedObject(), vollPath);
//						    	        
//					    	        }	
					    	       
					    	        //messageService.getMessage(vollPath, databasePath, new MessageCallBack());
					    	        messageService.getMessage(ImageManager.databaseName, vollPath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());

							}
			    	       
			    	        
			         	   
						}
			    	});


		// Open the first playlist by default.
			      TreeNode rootNode = tree.getRootTreeNode();
			     
			      //TreeNode firstPlaylist = 
			    		  rootNode.setChildOpen(0, true, false);
		//	      firstPlaylist.setChildOpen(0, true);
			     

		VerticalPanel panel_1 = new VerticalPanel();
		//panel_1.setBorderWidth(1);	    
		panel_1.setWidth("330");
		panel_1.setHeight("700");
		panel_1.addStyleName("gwt-CollectionPanel");
		panel_1.add(tree);


		// Add the widgets to the root panel.
		//RootPanel.get().add(panel);

		SplitLayoutPanel p = new SplitLayoutPanel();
		p.getElement().getStyle()
		.setProperty("border", "3px solid #e7e7e7");
		//p.setTitle("Collection Browser");
		//p.setBorderWidth(1);
		p.setWidth("500");
		p.setHeight("700");
		// p.addWest(new HTML("navigation"), 330);
		p.addWest(panel_1, 330);
		// p.addNorth(new HTML("list"), 100);
		// p.add(new HTML("details"));

		p.add(panel);
		//p.addStyleName("gwt-SplitLayoutPanel");



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

			TempDialog myDialog = new TempDialog(result.getMessage());

			int left = Window.getClientWidth()/ 2;
			int top = Window.getClientHeight()/ 2;
			myDialog.setPopupPosition(left, top);
			myDialog.show();
			
			//FolderPanel folderPanel = new FolderPanel();
			
			Map<List<String>, String> resultMap = result.getMessage();
			List<Folder> composers_tmp = new ArrayList<Folder>();
			
			for(List<String> nameKey : resultMap.keySet()){	        	 
	        	 Folder composer = new Folder(nameKey.get(0));
	        	 composer.setTypeFolder(nameKey.get(1));
	        	 composers_tmp.add(composer);
	        	 
	         }
			
			for(Folder comp : composers_tmp){
	        	 for(List<String> nameKey : resultMap.keySet()){
	        		 if(nameKey.contains(comp.getName())){
	        			 String parentName = resultMap.get(nameKey);
	        			 for(Folder parentComp : composers_tmp){
	    					 if(parentName.equals(parentComp.getName())){
	    						 parentComp.addPlaylist(comp);  
	    						 comp.setParent(parentComp);
	    					 }
	    				 }
	        		 }
	        	 }
	        	
	         }
	         
	         selectionModel.getSelectedObject().refresh();
	        
			//createEditorView(result.getMessage());

		}	   
	}

}
