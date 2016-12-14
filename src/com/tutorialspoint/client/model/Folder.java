package com.tutorialspoint.client.model;

import java.util.ArrayList;
import java.util.List;

public class Folder {
	private final String name;
    private final List<Folder> composerList = new ArrayList<Folder>();
    private com.tutorialspoint.client.model.FolderTreeModel.MyCell cell;
    private Folder parentComp = null;
    public Folder(String name) {
       this.name = name;
    }

    /**
    * Add a playlist to the composer.
    * 
    * @param playlist the playlist to add
    */
    public Folder addPlaylist(Folder playlist) {   
  	  composerList.add(playlist);
       return playlist;
    }

    public String getName() {
       return name;
    }

    /**
    * Return the rockin' playlist for this composer.
    */
    public List<Folder> getPlaylists() {
       return composerList;
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
			this.parentComp = parentComp;
		}
		
		public Folder getParent() {
			return this.parentComp;
		}

}
