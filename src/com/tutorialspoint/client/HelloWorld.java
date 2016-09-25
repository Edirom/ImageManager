package com.tutorialspoint.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class HelloWorld implements EntryPoint {
	
   public void onModuleLoad() {
      
      ConfigurationDialog myDialog = new ConfigurationDialog();

      int left = Window.getClientWidth()/ 3;
      int top = Window.getClientHeight()/ 3;
      myDialog.setPopupPosition(left, top);
      myDialog.show();
      
   }    
} 


//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.gwt.cell.client.AbstractCell;
//import com.google.gwt.cell.client.Cell;
//import com.google.gwt.cell.client.TextCell;
//import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
//import com.google.gwt.user.cellview.client.CellTree;
//import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
//import com.google.gwt.user.cellview.client.TreeNode;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.SplitLayoutPanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.view.client.ListDataProvider;
//import com.google.gwt.view.client.SingleSelectionModel;
//import com.google.gwt.view.client.TreeViewModel;

//public class HelloWorld implements EntryPoint {
//	
//	public void onModuleLoad() {
//		
//		
//		HTML html = new HTML("<p>Welcome to GWT application</p>");
//	      
//	      RootPanel.get("gwtContainer").add(html);
		
		
//		//create buttons
//	      Button loginButton = new Button("Login");
//	      loginButton.setEnabled(false);
//	      loginButton.setWidth("100px");
//	    //add a clickListener to the button
////	      loginButton.addClickHandler(new ClickHandler() {
////	         @Override
////	         public void onClick(ClickEvent event) {
////	            Window.alert("loginButton clicked!");
////	         }
////	      });
//
//	      Button createFolderButton = new Button("Create Folder");
//	      createFolderButton.setWidth("100px");
//	      //createFolderButton.addStyleName("gwt-Green-Button");
////	      createFolderButton.addClickHandler(new ClickHandler() {
////		         @Override
////		         public void onClick(ClickEvent event) {
////		            Window.alert("Green Button clicked!");
////		         }
////		      });
//	      
//	      
//	      Button uploadDataButton = new Button("Upload Data");
//	      uploadDataButton.setWidth("100px");
//	      //uploadDataButton.addStyleName("gwt-Green-Button");
////	      uploadDataButton.addClickHandler(new ClickHandler() {
////		         @Override
////		         public void onClick(ClickEvent event) {
////		            Window.alert("Green Button clicked!");
////		         }
////		      });
//	      
//	      Button deleteButton = new Button("Delete");
//	      deleteButton.setWidth("100px");	     
//	      //deleteButton.addStyleName("gwt-Delete-Button");
////	      deleteButton.addClickHandler(new ClickHandler() {
////		         @Override
////		         public void onClick(ClickEvent event) {
////		            Window.alert("Blue Button clicked!");
////		         }
////		      });
//	      
//	      Button tileImagesButton = new Button("Tile Image(s)");
//	      tileImagesButton.setWidth("100px");	     
//	      //tileImagesButton.addStyleName("gwt-Blue-Button");
////	      tileImagesButton.addClickHandler(new ClickHandler() {
////		         @Override
////		         public void onClick(ClickEvent event) {
////		            Window.alert("Blue Button clicked!");
////		         }
////		      });
//	      
//	      VerticalPanel panel = new VerticalPanel();
//	      panel.setSpacing(10);
//	      panel.addStyleName("gwt-VerticalPanel");
//	      panel.add(loginButton);
//	      panel.add(createFolderButton);
//	      panel.add(uploadDataButton);	      
//	      panel.add(tileImagesButton);
//	      panel.add(deleteButton);
//	      
//	      
//	      
//	   // Create a model for the tree.
//	      TreeViewModel model = new CustomTreeModel();
//	      //Get CellTree style using its BasicResources	   
//	      //CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
//	      /*
//	      * Create the tree using the model. We use <code>null</code> 
//	      * as the default value of the root node. The default value will 
//	      * be passed to CustomTreeModel#getNodeInfo();
//	      */
//	      CellTree tree = new CellTree(model, null);
//	      tree.addStyleName("gwt-CollectionTree");
//	      tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
//
//	      // Open the first playlist by default.
//	      TreeNode rootNode = tree.getRootTreeNode();
//	      TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
//	      firstPlaylist.setChildOpen(0, true);
//
//	      VerticalPanel panel_1 = new VerticalPanel();
//	      //panel_1.setBorderWidth(1);	    
//	      panel_1.setWidth("330");
//	      panel_1.setHeight("700");
//	      panel_1.addStyleName("gwt-CollectionPanel");
//	      panel_1.add(tree);
//		    
//
//		 // Add the widgets to the root panel.
//		 //RootPanel.get().add(panel);
//	   	
//		SplitLayoutPanel p = new SplitLayoutPanel();
//		p.getElement().getStyle()
//	      .setProperty("border", "3px solid #e7e7e7");
//		//p.setTitle("Collection Browser");
//		//p.setBorderWidth(1);
//		p.setWidth("500");
//		p.setHeight("700");
//		 // p.addWest(new HTML("navigation"), 330);
//		  p.addWest(panel_1, 330);
//		 // p.addNorth(new HTML("list"), 100);
//		 // p.add(new HTML("details"));
//		  
//		  p.add(panel);
//		  //p.addStyleName("gwt-SplitLayoutPanel");
//
//		  RootPanel.get("gwtContainer").add(p);
//	
//	   }
//
//  /**
//  * A list of songs.
//  */
//  private static class Playlist {
//     private final String name;
//     private final List<String> songs = new ArrayList<String>();
//
//     public Playlist(String name) {
//       this.name = name;
//     }
//
//      /**
//      * Add a song to the playlist.
//      * 
//      * @param name the name of the song
//      */
//      public void addSong(String name) {
//         songs.add(name);
//      }
//
//      public String getName() {
//         return name;
//      }
//
//      /**
//      * Return the list of songs in the playlist.
//      */
//      public List<String> getSongs() {
//         return songs;
//      }
//   }
//
//   /**
//   * A composer of classical music.
//   */
//   private static class Composer {
//      private final String name;
//      private final List<Playlist> playlists = new ArrayList<Playlist>();
//
//      public Composer(String name) {
//         this.name = name;
//      }
//
//      /**
//      * Add a playlist to the composer.
//      * 
//      * @param playlist the playlist to add
//      */
//      public Playlist addPlaylist(Playlist playlist) {   
//         playlists.add(playlist);
//         return playlist;
//      }
//
//      public String getName() {
//         return name;
//      }
//
//      /**
//      * Return the rockin' playlist for this composer.
//      */
//      public List<Playlist> getPlaylists() {
//         return playlists;
//      }
//   }
//
//   /**
//   * The model that defines the nodes in the tree.
//   */
//   private static class CustomTreeModel implements TreeViewModel {
//
//   private final List<Composer> composers;
//
//   /**
//   * This selection model is shared across all leaf nodes. 
//   * A selection model can also be shared across all nodes 
//   * in the tree, or each set of child nodes can have 
//   * its own instance. This gives you flexibility to 
//   * determine how nodes are selected.
//   */
//   private final SingleSelectionModel<String> selectionModel 
//   = new SingleSelectionModel<String>();
//
//      public CustomTreeModel() {
//         // Create a database of information.
//         composers = new ArrayList<Composer>();
//
//         // Add compositions by Beethoven.
//         {
//            Composer beethoven = new Composer("Beethoven");
//            composers.add(beethoven);
//   
//            Playlist concertos = beethoven.addPlaylist(
//            new Playlist("Concertos"));
//            concertos.addSong("No. 1 - C");
//            concertos.addSong("No. 2 - B-Flat Major");
//            concertos.addSong("No. 3 - C Minor");
//            concertos.addSong("No. 4 - G Major");
//            concertos.addSong("No. 5 - E-Flat Major");
//
//            Playlist quartets = beethoven.addPlaylist(
//            new Playlist("Quartets"));
//            quartets.addSong("Six String Quartets");
//            quartets.addSong("Three String Quartets");
//            quartets.addSong("Grosse Fugue for String Quartets");
//
//            Playlist sonatas = beethoven.addPlaylist(
//            new Playlist("Sonatas"));
//            sonatas.addSong("Sonata in A Minor");
//            sonatas.addSong("Sonata in F Major");
//
//            Playlist symphonies = beethoven.addPlaylist(
//            new Playlist("Symphonies"));
//            symphonies.addSong("No. 2 - D Major");
//            symphonies.addSong("No. 2 - D Major");
//            symphonies.addSong("No. 3 - E-Flat Major");
//            symphonies.addSong("No. 4 - B-Flat Major");
//            symphonies.addSong("No. 5 - C Minor");
//            symphonies.addSong("No. 6 - F Major");
//            symphonies.addSong("No. 7 - A Major");
//            symphonies.addSong("No. 8 - F Major");
//            symphonies.addSong("No. 9 - D Minor");
//         }
//
//         // Add compositions by Brahms.
//         {
//            Composer brahms = new Composer("Brahms");
//            composers.add(brahms);
//            Playlist concertos = brahms.addPlaylist(
//            new Playlist("Concertos"));
//            concertos.addSong("Violin Concerto");
//            concertos.addSong("Double Concerto - A Minor");
//            concertos.addSong("Piano Concerto No. 1 - D Minor");
//            concertos.addSong("Piano Concerto No. 2 - B-Flat Major");
//
//            Playlist quartets = brahms.addPlaylist(
//            new Playlist("Quartets"));
//            quartets.addSong("Piano Quartet No. 1 - G Minor");
//            quartets.addSong("Piano Quartet No. 2 - A Major");
//            quartets.addSong("Piano Quartet No. 3 - C Minor");
//            quartets.addSong("String Quartet No. 3 - B-Flat Minor");
//
//            Playlist sonatas = brahms.addPlaylist(
//            new Playlist("Sonatas"));
//            sonatas.addSong("Two Sonatas for Clarinet - F Minor");
//            sonatas.addSong("Two Sonatas for Clarinet - E-Flat Major");
//
//            Playlist symphonies = brahms.addPlaylist(
//            new Playlist("Symphonies"));
//            symphonies.addSong("No. 1 - C Minor");
//            symphonies.addSong("No. 2 - D Minor");
//            symphonies.addSong("No. 3 - F Major");
//            symphonies.addSong("No. 4 - E Minor");
//         }
//
//         // Add compositions by Mozart.
//         {
//            Composer mozart = new Composer("Mozart");
//            composers.add(mozart);
//            Playlist concertos = mozart.addPlaylist(
//            new Playlist("Concertos"));
//            concertos.addSong("Piano Concerto No. 12");
//            concertos.addSong("Piano Concerto No. 17");
//            concertos.addSong("Clarinet Concerto");
//            concertos.addSong("Violin Concerto No. 5");
//            concertos.addSong("Violin Concerto No. 4");
//         }
//      }
//
//      /**
//      * Get the {@link NodeInfo} that provides the children of the 
//      * specified value.
//      */
//      public <T> NodeInfo<?> getNodeInfo(T value) {
//         if (value == null) {
//            // LEVEL 0.
//            // We passed null as the root value. Return the composers.
//
//            // Create a data provider that contains the list of composers.
//            ListDataProvider<Composer> dataProvider 
//            = new ListDataProvider<HelloWorld.Composer>(
//            composers);
//
//            // Create a cell to display a composer.
//            Cell<HelloWorld.Composer> cell 
//            = new AbstractCell<HelloWorld.Composer>() {
//               
//			@Override
//			public void render(com.google.gwt.cell.client.Cell.Context context, Composer value, SafeHtmlBuilder sb) {
//				 if (value != null) {
//                	 sb.appendHtmlConstant("    "); 
//                     sb.appendEscaped(value.getName());
//                  }		
//			}
//            };
//
//            // Return a node info that pairs the data provider and the cell.
//            return new DefaultNodeInfo<Composer>(dataProvider, cell);
//         } else if (value instanceof Composer) {
//            // LEVEL 1.
//            // We want the children of the composer. Return the playlists.
//            ListDataProvider<HelloWorld.Playlist> dataProvider
//            = new ListDataProvider<HelloWorld.Playlist>(
//            ((Composer) value).getPlaylists());
//            Cell<HelloWorld.Playlist> cell = 
//            new AbstractCell<HelloWorld.Playlist>() {
//              
//			@Override
//			public void render(com.google.gwt.cell.client.Cell.Context context, Playlist value, SafeHtmlBuilder sb) {
//				if (value != null) {        
//               	 sb.appendHtmlConstant("    "); 
//                    sb.appendEscaped(value.getName());
//                 }
//			}
//            };
//            return new DefaultNodeInfo<Playlist>(dataProvider, cell);
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
//         }
//
//         return null;
//      }
//
//      /**
//      * Check if the specified value represents a leaf node. 
//      * Leaf nodes cannot be opened.
//      */
//      public boolean isLeaf(Object value) {
//      // The leaf nodes are the songs, which are Strings.
//      if (value instanceof String) {
//         return true;
//      }
//      return false;
//      }
//   }
//
//  
//}