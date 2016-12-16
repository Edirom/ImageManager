package com.tutorialspoint.client.model;

import java.util.ArrayList;
import java.util.List;

public class Folder {
	private final String name;
	private final List<Folder> dbDataList = new ArrayList<Folder>();
	private com.tutorialspoint.client.model.FolderTreeModel.MyCell cell;
	private Folder parentFolder = null;
	private String typeName;

	public Folder(String name) {
		this.name = name;
	}

	public void setTypeFolder(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeFolder() {
		return this.typeName;
	}

	public Folder addFolderlist(Folder folderlist) {   
		dbDataList.add(folderlist);
		return folderlist;
	}

	public String getName() {
		return name;
	}

	public List<Folder> getFolderlists() {
		return dbDataList;
	}

	public void setCell(com.tutorialspoint.client.model.FolderTreeModel.MyCell cell) {
		this.cell = cell;
	}

	public void refresh() {     
		if (cell!=null) {
			cell.refresh(); //refresh tree
		}
	}

	public void setParent(Folder parentComp) {
		this.parentFolder = parentComp;
	}

	public Folder getParent() {
		return this.parentFolder;
	}

	public String getPath() {
		String path = this.name;
		if(parentFolder != null){
			path = computePath(parentFolder, path);
		}
		return path;
	}

	private String computePath(Folder currFolder, String path){
		path = currFolder.getName() +"/"+ path;
		if(currFolder.getParent() != null){
			path = computePath(currFolder.getParent(), path);
		}
		return path;
	}

}
