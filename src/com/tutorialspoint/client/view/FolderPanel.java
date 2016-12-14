package com.tutorialspoint.client.view;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.tutorialspoint.client.model.Folder;
import com.tutorialspoint.client.model.FolderTreeModel;

public class FolderPanel {
	
	final static SingleSelectionModel<Folder> selectionModel = new SingleSelectionModel<Folder>();
	
	
public void createEditorView(Map<String, String> result, String databaseName){
		
		
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
	      FolderTreeModel model = new FolderTreeModel(result, databaseName, selectionModel);
	      
	      
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
	      
//	      tree.addOpenHandler(new OpenHandler<TreeNode>(){ 
//				@Override
//				public void onOpen(OpenEvent<TreeNode> event) {
//					//Composer comp = selectionModel.getSelectedObject();
//					//event.getTarget().getValue()
//	    	        //Window.alert(selectionModel.getSelectedObject().getName());
//	    	        String name = selectionModel.getSelectedObject().getName();
//	    	        String vollPath = "";
//	    	        		//databaseName+"/.../"+name;
//	    	        if(selectionModel.getSelectedObject().getParent().getName().equals(databaseName)){
//	    	        	vollPath = databaseName+"/"+name;
//	    	        }
//	    	        else{
//	    	        	getParentSelection(selectionModel.getSelectedObject(), vollPath);
//		    	        
//	    	        }
//	    	        
//	    	        messageService.getMessage(vollPath, databasePath, new MessageCallBack());
//	         	   
//				}
//	    	});
	     

	      // Open the first playlist by default.
//	      TreeNode rootNode = tree.getRootTreeNode();
//	      TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
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

}
