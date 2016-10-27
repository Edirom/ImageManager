package com.tutorialspoint.client;

import java.io.Serializable;
import java.util.Map;

public class Message implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String message;
   private Map<String, String> collectionMap;
   public Message(){};

   public void setMessage(Map<String, String> message) {
      this.collectionMap = message;
   }

   public Map<String, String> getMessage() {
      return collectionMap;
   }

}