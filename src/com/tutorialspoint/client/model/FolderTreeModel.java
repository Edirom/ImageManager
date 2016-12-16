package com.tutorialspoint.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class FolderTreeModel implements TreeViewModel{
	
	//final static SingleSelectionModel<Folder> selectionModel = new SingleSelectionModel<Folder>(); 
	
	private final List<Folder> composers;
	private SingleSelectionModel<Folder> selectionModel;
	
	/**
	   * This selection model is shared across all leaf nodes. 
	   * A selection model can also be shared across all nodes 
	   * in the tree, or each set of child nodes can have 
	   * its own instance. This gives you flexibility to 
	   * determine how nodes are selected.
	   */
	   //final SingleSelectionModel<Composer> selectionModel = new SingleSelectionModel<Composer>();

	      public FolderTreeModel(Map<List<String>, String> map, String databaseName, SingleSelectionModel<Folder>  selectionModel) {
	    	  
//	    	  Map<String, String> collectionMap = new HashMap<String, String>();
//	    	  collectionMap.put("TemporaryItems", "db");
//	    	  collectionMap.put("apps", "db");
//	    	  collectionMap.put("contents", "db");
//	    	  collectionMap.put("system", "db");
//	    	  
//	    	  collectionMap.put("config", "system");
//	    	  collectionMap.put("repo", "system");
	    	  
	    	  this.selectionModel = selectionModel;
	    	  
	         // Create a database of information.	    	  
	        composers = new ArrayList<Folder>();
	        
	        Folder composer0 = new Folder(databaseName);
	        composers.add(composer0);
	        //composer0.setParent(composer0);
	       
	       // List<Folder> composers_tmp = new ArrayList<Folder>();
	       // composers_tmp.add(composer0);
	        
	         for(List<String> nameKey : map.keySet()){	        	 
	        	 Folder composer = new Folder(nameKey.get(0));
	        	 composer.setTypeFolder(nameKey.get(1));
	        	 //composers_tmp.add(composer);
	        	 composer0.addPlaylist(composer);
	        	 composer.setParent(composer0);
	         }
	         
//	         for(Folder comp : composers_tmp){
//	        	 for(List<String> nameKey : map.keySet()){
//	        		 if(nameKey.contains(comp.getName())){
//	        			 String parentName = map.get(nameKey);
//	        			 for(Folder parentComp : composers_tmp){
//	    					 if(parentName.equals(parentComp.getName())){
//	    						 parentComp.addPlaylist(comp);  
//	    						 comp.setParent(parentComp);
//	    					 }
//	    				 }
//	        		 }
//	        	 }
//	        	
//	         }
//	         
	         
	        
	         
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
	            ListDataProvider<Folder> dataProvider 
	            = new ListDataProvider<Folder>(composers);

	            // Create a cell to display a composer.
	            Cell<Folder> cell 
	            = new AbstractCell<Folder>() {
	               
				@Override
				public void render(com.google.gwt.cell.client.Cell.Context context, Folder value, SafeHtmlBuilder sb) {
					 if (value != null) {
	                	 sb.appendHtmlConstant("    "); 
	                     sb.appendEscaped(value.getName());
	                  }		
				}
	            };

	            // Return a node info that pairs the data provider and the cell.
	            return new DefaultNodeInfo<Folder>(dataProvider, cell);
	         } else if (value instanceof Folder) {
	            // LEVEL 1.
	            // We want the children of the composer. Return the playlists.
	            ListDataProvider<Folder> dataProvider = new ListDataProvider<Folder>(((Folder) value).getPlaylists());
	            MyCell cell = new MyCell(dataProvider);
	            ((Folder) value).setCell(cell);
//	            Cell<Composer> cell = new MyCell<Composer>() {            	
//				@Override
//				public void render(com.google.gwt.cell.client.Cell.Context context, Composer value, SafeHtmlBuilder sb) {
//					if (value != null) {        
//	               	 sb.appendHtmlConstant("    "); 
//	                    sb.appendEscaped(value.getName());
//	                 }
//				}
//	            };
	            return new DefaultNodeInfo<Folder>(dataProvider, cell, selectionModel, null);
	           // return new DefaultNodeInfo<String>(dataProvider, cell, selectionModel, null);
//	        } else if (value instanceof Playlist) {
//	            // LEVEL 2 - LEAF.
//	            // We want the children of the playlist. Return the songs.
//	            ListDataProvider<String> dataProvider 
//	            = new ListDataProvider<String>(
//	            ((Playlist) value).getSongs());
	//
//	            // Use the shared selection model.
//	            return new DefaultNodeInfo<String>(dataProvider, new TextCell(),
//	            selectionModel, null);
	        }

	         return null;
	      }

	      /**
	      * Check if the specified value represents a leaf node. 
	      * Leaf nodes cannot be opened.
	      */
	      public boolean isLeaf(Object value) {
	    	  if(value instanceof Folder){
	    		  Folder comp = (Folder) value;
	    		  if(comp.getTypeFolder().equals("File")){
	    			  return true;
	    		  }	    		  
	    		  return false;
//	    		  if(comp.getPlaylists().size() == 0){
//	    			  return true;
//	    		  }
	    	  }
	      // The leaf nodes are the songs, which are Strings.
	      if (value instanceof String) {
	         return true;
	      }
	      return false;
	      }
	      
	      public class MyCell extends AbstractCell<Folder> {
	  	    ListDataProvider<Folder> dataProvider; //for refresh
	  	 
	  	    public MyCell(ListDataProvider<Folder> dataProvider) {
	  	      super();
	  	      this.dataProvider = dataProvider;
	  	    }
	  	    public void refresh() {
	  	      dataProvider.refresh();
	  	    }
	  	 
	  	    @Override
	  	    public void render(Context context, Folder value, SafeHtmlBuilder sb) {
	  	      if (value == null) {
	  	        return;
	  	      }
	  	      sb.appendEscaped(value.getName());
	  	    }
	  	  }

}
