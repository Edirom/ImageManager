package com.tutorialspoint.client;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class ConfigurationDialog extends DialogBox {
	
	private MessageServiceAsync messageService = 
			   GWT.create(MessageService.class);
	
	
	private static String databaseName = "";
	private static String databasePath = "";
	private static String databaseUser = "";
	private static String databasePW = "";
	
	final static SingleSelectionModel<Composer> selectionModel = new SingleSelectionModel<Composer>();
	
	private Button ok = new Button("connenct");
	
	//boolean isCreted = false;
	
	
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
	    	  MyDialog myDialog = new MyDialog(result.getMessage());

	          int left = Window.getClientWidth()/ 2;
	          int top = Window.getClientHeight()/ 2;
	          myDialog.setPopupPosition(left, top);
	          myDialog.show();
	        // Window.confirm(result.getMessage());
	        // Window.enableScrolling(true);
//	          if(isCreted){
//	        	  
//	        	  Composer node =   selectionModel.getSelectedObject();
//		        	 //Composer composer = new Composer("NewNode");
//		        	// node.addPlaylist(composer);
//		        	// node.refresh();
//	        	  
//	        	  Map<String, String> result = result_1.getMessage();
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
	          createEditorView(result.getMessage());
	   //       }
	      }	   
	   }
	
	native void jsClickUpload(Element pElement) /*-{
    pElement.click();
}-*/;
	
	
	public ConfigurationDialog(String uploadData) {
		
        // Set the dialog box's caption.
        setText("Upload Image");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);
        
        VerticalPanel panel = new VerticalPanel();
        //create a FormPanel 
        final FormPanel form = new FormPanel();
        //create a file upload widget
        
        
        
//        final FileUpload upload = new FileUpload();
//
//        upload.setVisible(false);
//        upload.setName("uploadFormElement");
//       // RootPanel.get().add(upload);
//
//        Button b = new Button("Select File");
//        b.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                jsClickUpload(upload.getElement());
//            	//messageService.getMessage(databaseName, databasePath, databaseUser, databasePW, new MessageCallBack());
//            }
//        });
//
//        upload.addChangeHandler(new ChangeHandler() {
//
//            @Override
//            public void onChange(ChangeEvent event) {
//                Window.alert(upload.getFilename());
//            }});
//        panel.add(upload);
//        panel.add(b);
       
        final FileUpload fileUpload = new FileUpload();
        //fileUpload.getElement().setPropertyBoolean("multiple", true);
        //create labels
        Label selectLabel = new Label("File selection:");
        //create upload button
        Button uploadButton = new Button("Upload File");
        uploadButton.setWidth("100px");
	      //createFolderButton.setEnabled(false);
	      //createFolderButton.addStyleName("gwt-Green-Button");
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        form.setAction(GWT.getModuleBaseURL()+"fileupload");
//        uploadButton.addClickHandler(new ClickHandler() {
//		         @Override
//		         public void onClick(ClickEvent event) {
//		        	 String filename = fileUpload.getFilename();
//		        	 form.submit();
//		   
//		        	// messageService.getMessageUpload(filename, databaseName, databasePath, databaseUser, databasePW, new MessageCallBack());
//		        	 
//		 			
//		        	 // messageCreateService.getMessage(node.getName(), composer.getName(), new MessageCreateCallBack());
//		        	// Window.alert(filename);
//		         }
//		      });
       
        //add a label
        panel.add(selectLabel);
        //add fileUpload widget
        panel.add(fileUpload);
        //add a button to upload the file
        panel.add(uploadButton);
        
        uploadButton.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
              //get the filename to be uploaded
              String filename = fileUpload.getFilename();
              if (filename.length() == 0) {
                 Window.alert("No File Specified!");
              } else {
                 //submit the form
                 form.submit();	
            	 // messageService.getMessageForCreate(filename, databaseName, databasePath, databaseUser, databasePW, new MessageCallBack());
              // Window.alert(filename+databaseName+databasePath);
              }				
           }
        });
     
        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
           @Override
           public void onSubmitComplete(SubmitCompleteEvent event) {
              // When the form submission is successfully completed, this 
              //event is fired. Assuming the service returned a response 
              //of type text/html, we can get the result text here 
              Window.alert(event.getResults());				
           }
        });
        panel.setSpacing(10);
        
        // Add form to the root panel.      
        form.add(panel);      

       // RootPanel.get("gwtContainer").add(form);

        setWidget(form);
    
  }

	
	String newName = "";
	String newPath = "";
	Button okButton = new Button("create");
	
	public ConfigurationDialog(Composer parent) {
		
	         // Set the dialog box's caption.
	         setText("Create Node");

	         // Enable animation.
	         setAnimationEnabled(true);

	         // Enable glass background.
	         setGlassEnabled(true);
	         
	        // Button okButton = new Button("create");
	         okButton.setEnabled(false);
	         okButton.addClickHandler(new ClickHandler() {
	            public void onClick(ClickEvent event) {
	            	ConfigurationDialog.this.hide(); 
	            	
//	            	 Composer composer = new Composer(newName);
//	            	 parent.addPlaylist(composer);
//	            	 parent.refresh();
//	            	 
//	            	 String path = databaseName +"/"+parent.getName();
//	            	 if(parent.getParent() != null){
//	            		 path += "/"+parent.getParent().getName();
//	            	 }
	            	
	            	// messageCreateService.getMessageCreate("db", "testNew", "xmldb:exist://localhost:8080/exist/xmlrpc", new MessageCreateCallBack());
		        	   
	         	  // messageService.getMessage(databaseName, databasePath, new MessageCallBack());
	            	//String newPathName = databaseName+"/"+newName;
	            	messageService.getMessageForCreate(newName, newPath, databasePath, databaseUser, databasePW, new MessageCallBack());
	         	   
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
	 	     
	 	     
	         VerticalPanel panel = new VerticalPanel();
	         panel.setHeight("00");
	         panel.setWidth("00");
	         panel.setSpacing(10);
	         panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LOCALE_END);
	         panel.add(hPanel);
	         panel.add(hPanel_1);
	         panel.add(okButton);

	         setWidget(panel);
	     
	   }
	
	
	private static class MyDialog extends DialogBox {

	      public MyDialog(Map<String, String> result) {
	         // Set the dialog box's caption.
	         setText("Result");

	         // Enable animation.
	         setAnimationEnabled(true);

	         // Enable glass background.
	         setGlassEnabled(true);

	         // DialogBox is a SimplePanel, so you have to set its widget 
	         // property to whatever you want its contents to be.
	         Button ok = new Button("OK");
	         ok.addClickHandler(new ClickHandler() {
	            public void onClick(ClickEvent event) {
	               MyDialog.this.hide();
	            }
	         });
	         HTML html1 = 
	        	      new HTML(result.toString());
	        // Label label = new Label(result);

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
	
	public void createEditorView(Map<String, String> result){
		
		
		RootPanel.get("gwtContainer").clear();
		
		//isCreted = true;
//	      Button loginButton = new Button("Login");
//	      loginButton.setEnabled(false);
//	      loginButton.setWidth("100px");
	    //add a clickListener to the button
//	      loginButton.addClickHandler(new ClickHandler() {
//	         @Override
//	         public void onClick(ClickEvent event) {
//	            Window.alert("loginButton clicked!");
//	         }
//	      });

	      Button createFolderButton = new Button("Create Folder");
	      createFolderButton.setWidth("100px");
	      //createFolderButton.setEnabled(false);
	      //createFolderButton.addStyleName("gwt-Green-Button");
	      createFolderButton.addClickHandler(new ClickHandler() {
		         @Override
		         public void onClick(ClickEvent event) {
		        	 Composer node =   selectionModel.getSelectedObject();
		        	
		        	 
		        	 ConfigurationDialog myDialog = new ConfigurationDialog(node);
		        	 
		        	 	          int left = Window.getClientWidth()/ 2;
		        	 	          int top = Window.getClientHeight()/ 2;
		        	 	          myDialog.setPopupPosition(left, top);
		        	 	          myDialog.show();
		        	 
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
		        	
		        	 
		        	 ConfigurationDialog myDialog = new ConfigurationDialog("uploadData");
		        	 
		        	 	          int left = Window.getClientWidth()/ 2;
		        	 	          int top = Window.getClientHeight()/ 2;
		        	 	          myDialog.setPopupPosition(left, top);
		        	 	          myDialog.show();
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
	      CustomTreeModel model = new CustomTreeModel(result);
	      
	      
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
	
	 public void getParentSelection(Composer comp, String vollPath){
		 if(comp.getParent() != null){
			 Window.alert(comp.getParent().getName());
			 vollPath += comp.getParent().getName();
			 if(comp.getParent().getParent() != null){
				 vollPath += "/";
			 }
			 getParentSelection(comp.getParent(), vollPath);
		 }
		 
		 return;
	 }
	
	public ConfigurationDialog() {
        // Set the dialog box's caption.
        setText("Database Configuration");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);
        
        
        final TextBox txtDatenBankUser = new TextBox(); 
        txtDatenBankUser.setWidth("300");
        txtDatenBankUser.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
            	 databaseUser = txtDatenBankUser.getValue();
              	  if(databaseName.length() >0 && databasePath.length() >0 && databaseUser.length() >0) {
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
            	 databasePW = txtDatenBankPW.getValue();
              	  if(databaseName.length() >0 && databasePath.length() >0 && databaseUser.length() >0) {
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
            	 databaseName = txtDatenBankName.getValue();
            	 if(databaseName.length() >0 && databasePath.length() >0 && databaseUser.length() >0) {
              		  ok.setEnabled(true);
              	  }
              	  else{
              		  ok.setEnabled(false);
              	  }
            }
        });
        Label lblDatenBankName = new Label("Database Name");
        final TextBox txtDatenBankPath = new TextBox();
        databasePath = "xmldb:exist://localhost:8080/exist/xmlrpc";
        txtDatenBankPath.setValue(databasePath);
        txtDatenBankPath.setWidth("300");
        txtDatenBankPath.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
            	 databasePath = txtDatenBankPath.getValue();
            	 if(databaseName.length() >0 && databasePath.length() >0 && databaseUser.length() >0) {
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
        	   ConfigurationDialog.this.hide();          	   
        	   messageService.getMessage(databaseName, databasePath, databaseUser, databasePW, new MessageCallBack());
        	   //messageService.getMessageForCreate(databaseName, databasePath, databaseUser, databasePW, new MessageCallBack());
        	   
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
	
 
   /**
   * A composer of classical music.
   */
   private static class Composer {
      private final String name;
      private final List<Composer> composerList = new ArrayList<Composer>();
      private com.tutorialspoint.client.ConfigurationDialog.CustomTreeModel.MyCell cell;
      private Composer parentComp = null;
      public Composer(String name) {
         this.name = name;
      }

      /**
      * Add a playlist to the composer.
      * 
      * @param playlist the playlist to add
      */
      public Composer addPlaylist(Composer playlist) {   
    	  composerList.add(playlist);
         return playlist;
      }

      public String getName() {
         return name;
      }

      /**
      * Return the rockin' playlist for this composer.
      */
      public List<Composer> getPlaylists() {
         return composerList;
      }
      
      public void setCell(com.tutorialspoint.client.ConfigurationDialog.CustomTreeModel.MyCell cell) {
          this.cell = cell;
        }
     
        public void refresh() {     
          if (cell!=null) {
            cell.refresh(); //refresh tree
          }
        }

		public void setParent(Composer parentComp) {
			this.parentComp = parentComp;
		}
		
		public Composer getParent() {
			return this.parentComp;
		}
   }

   /**
   * The model that defines the nodes in the tree.
   */
   private static class CustomTreeModel implements TreeViewModel {

   private final List<Composer> composers;

   /**
   * This selection model is shared across all leaf nodes. 
   * A selection model can also be shared across all nodes 
   * in the tree, or each set of child nodes can have 
   * its own instance. This gives you flexibility to 
   * determine how nodes are selected.
   */
   //final SingleSelectionModel<Composer> selectionModel = new SingleSelectionModel<Composer>();

      public CustomTreeModel(Map<String, String> result) {
    	  
//    	  Map<String, String> collectionMap = new HashMap<String, String>();
//    	  collectionMap.put("TemporaryItems", "db");
//    	  collectionMap.put("apps", "db");
//    	  collectionMap.put("contents", "db");
//    	  collectionMap.put("system", "db");
//    	  
//    	  collectionMap.put("config", "system");
//    	  collectionMap.put("repo", "system");
    	  
         // Create a database of information.
        composers = new ArrayList<Composer>();
        
        Composer composer0 = new Composer(databaseName);
        composers.add(composer0);
        //composer0.setParent(composer0);
       
        List<Composer> composers_tmp = new ArrayList<Composer>();
        composers_tmp.add(composer0);
        
         for(String nameKey : result.keySet()){
        	 Composer composer = new Composer(nameKey);
        	 composers_tmp.add(composer);
        	 
         }
         
         for(Composer comp : composers_tmp){
        	 String parentName = result.get(comp.getName());
        	 for(Composer parentComp : composers_tmp){
				 if(parentName.equals(parentComp.getName())){
					 parentComp.addPlaylist(comp);  
					 comp.setParent(parentComp);
				 }
			 }
         }
         
         
        
         
      }
      
     
      /**
      * Get the {@link NodeInfo} that provides the children of the 
      * specified value.
      */
      public <T> NodeInfo<?> getNodeInfo(T value) {
         if (value == null) {
            // LEVEL 0.
            // We passed null as the root value. Return the composers.

            // Create a data provider that contains the list of composers.
            ListDataProvider<Composer> dataProvider 
            = new ListDataProvider<Composer>(composers);

            // Create a cell to display a composer.
            Cell<Composer> cell 
            = new AbstractCell<Composer>() {
               
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context, Composer value, SafeHtmlBuilder sb) {
				 if (value != null) {
                	 sb.appendHtmlConstant("    "); 
                     sb.appendEscaped(value.getName());
                  }		
			}
            };

            // Return a node info that pairs the data provider and the cell.
            return new DefaultNodeInfo<Composer>(dataProvider, cell);
         } else if (value instanceof Composer) {
            // LEVEL 1.
            // We want the children of the composer. Return the playlists.
            ListDataProvider<Composer> dataProvider = new ListDataProvider<Composer>(((Composer) value).getPlaylists());
            MyCell cell = new MyCell(dataProvider);
            ((Composer) value).setCell(cell);
//            Cell<Composer> cell = new MyCell<Composer>() {            	
//			@Override
//			public void render(com.google.gwt.cell.client.Cell.Context context, Composer value, SafeHtmlBuilder sb) {
//				if (value != null) {        
//               	 sb.appendHtmlConstant("    "); 
//                    sb.appendEscaped(value.getName());
//                 }
//			}
//            };
            return new DefaultNodeInfo<Composer>(dataProvider, cell, selectionModel, null);
           // return new DefaultNodeInfo<String>(dataProvider, cell, selectionModel, null);
//        } else if (value instanceof Playlist) {
//            // LEVEL 2 - LEAF.
//            // We want the children of the playlist. Return the songs.
//            ListDataProvider<String> dataProvider 
//            = new ListDataProvider<String>(
//            ((Playlist) value).getSongs());
//
//            // Use the shared selection model.
//            return new DefaultNodeInfo<String>(dataProvider, new TextCell(),
//            selectionModel, null);
        }

         return null;
      }

      /**
      * Check if the specified value represents a leaf node. 
      * Leaf nodes cannot be opened.
      */
      public boolean isLeaf(Object value) {
      // The leaf nodes are the songs, which are Strings.
      if (value instanceof String) {
         return true;
      }
      return false;
      }
      
      public class MyCell extends AbstractCell<Composer> {
  	    ListDataProvider<Composer> dataProvider; //for refresh
  	 
  	    public MyCell(ListDataProvider<Composer> dataProvider) {
  	      super();
  	      this.dataProvider = dataProvider;
  	    }
  	    public void refresh() {
  	      dataProvider.refresh();
  	    }
  	 
  	    @Override
  	    public void render(Context context, Composer value, SafeHtmlBuilder sb) {
  	      if (value == null) {
  	        return;
  	      }
  	      sb.appendEscaped(value.getName());
  	    }
  	  }
   }

  
  
        

}
