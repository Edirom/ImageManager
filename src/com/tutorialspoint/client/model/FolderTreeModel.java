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

	private final List<Folder> folders;
	private SingleSelectionModel<Folder> selectionModel;

	public FolderTreeModel(Map<List<String>, String> map, String databaseName, SingleSelectionModel<Folder>  selectionModel) {

		this.selectionModel = selectionModel;

		folders = new ArrayList<Folder>();

		Folder rootFolder = new Folder(databaseName);
		folders.add(rootFolder);

		for(List<String> nameKey : map.keySet()){	        	 
			Folder folder = new Folder(nameKey.get(0));
			folder.setTypeFolder(nameKey.get(1));
			rootFolder.addFolderlist(folder);
			folder.setParent(rootFolder);
		}

	}


	/**
	 * Get the {@link NodeInfo} that provides the children of the 
	 * specified value.
	 */
	 public <T> NodeInfo<?> getNodeInfo(T value) {
		 if (value == null) {
			 ListDataProvider<Folder> dataProvider = new ListDataProvider<Folder>(folders);

			 Cell<Folder> cell = new AbstractCell<Folder>() {

				 @Override
				 public void render(com.google.gwt.cell.client.Cell.Context context, Folder value, SafeHtmlBuilder sb) {
					 if (value != null) {
						 sb.appendHtmlConstant("    "); 
						 sb.appendEscaped(value.getName());
					 }		
				 }
			 };

			 return new DefaultNodeInfo<Folder>(dataProvider, cell);
		 } else if (value instanceof Folder) {
			 ListDataProvider<Folder> dataProvider = new ListDataProvider<Folder>(((Folder) value).getFolderlists());
			 MyCell cell = new MyCell(dataProvider);
			 ((Folder) value).setCell(cell);
			 return new DefaultNodeInfo<Folder>(dataProvider, cell, selectionModel, null);
		 }

		 return null;
	 }

	 /**
	  * Check if the specified value represents a leaf node. 
	  * Leaf nodes cannot be opened.
	  */
	 public boolean isLeaf(Object value) {
		 if(value instanceof Folder){
			 Folder folder = (Folder) value;
			 if(folder.getTypeFolder().equals("File")){
				 return true;
			 }	    		  
			 return false;
		 }
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
