package com.tutorialspoint.client.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ContentDB implements Serializable {
	 
	   private static final long serialVersionUID = 1L;
	   private Map<List<String>, String> collectionMap;
	   public ContentDB(){};

	   public void setMessage(Map<List<String>, String> message) {
	      this.collectionMap = message;
	   }

	   public Map<List<String>, String> getMessage() {
	      return collectionMap;
	   }


}
