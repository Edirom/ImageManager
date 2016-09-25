package com.tutorialspoint.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.google.gwt.user.client.ui.FileUpload;

//import javax.xml.transform.OutputKeys;

//import org.exist.xmldb.EXistResource;
//import org.xmldb.api.*;
//import org.xmldb.api.base.*;
//import org.xmldb.api.modules.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tutorialspoint.client.Message;
import com.tutorialspoint.client.MessageService;

public class MessageServiceImpl extends RemoteServiceServlet 
   implements MessageService{

   private static final long serialVersionUID = 1L;


   public Message getMessage(String input, String databasePath, String databaseUser, String databasePW) {
	          
       Collection col = null;
      
       Map<String, String> collectionMap = new HashMap<String, String>();
     
       try {    
           		
           col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
           
           col.setProperty(OutputKeys.INDENT, "no");
           
//           Collection newcol = DatabaseManager.getCollection(databasePath+"db/newCollection", databaseUser, databasePW);
//           if(newcol == null){
//        	   CollectionManagementService mgt = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
//    	       newcol = mgt.createCollection("newCollection");
//           }
           
    
         
           for(int i = 0; i<col.listChildCollections().length; i++){
        	   String colName = col.listChildCollections()[i];
        	  collectionMap.put(colName, input);
        	  
        	  for(int j = 0; j<col.listResources().length; j++){
           	   String resId = col.listResources()[j];
           	   //Resource res = col.getResource(resId);
           	   collectionMap.put(resId, input);
              }
        	 
        	   Collection currCollection = col.getChildCollection(colName);
        	   if(currCollection.getChildCollectionCount()>0){
        		   getTailCollection(currCollection, collectionMap);
        	   }
           }
           
          
           
           
           
       } catch (XMLDBException e) {
			e.printStackTrace();
		} finally {
           
           if(col != null) {
               try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
           }
       }
       
//       collectionMap.put(databaseUser, databasePW);
// 	  collectionMap.put("TemporaryItems", input);
// 	  collectionMap.put("apps", input);
// 	  collectionMap.put("contents", input);
// 	  collectionMap.put("system", input);
 	  
 	  //collectionMap.put("config", "system");
 	  //collectionMap.put("repo", "system");
       
      
	  
     // String messageString = "Hello " + outputTest + "!";     
      Message message = new Message();
     // message.setMessage(messageString);
      message.setMessage(collectionMap);
      
//      ImageTiles tiles = new ImageTiles();
//		tiles.run(cutImageDialog);
      
      
      
      return message;
         
   } 
   
   public Message getMessageUpload(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW) {
       
	   HttpServletRequest request = this.getThreadLocalRequest();
	   
	   Map<String, String> collectionMap = new HashMap<String, String>();
	   
	// Create a factory for disk-based file items
	   FileItemFactory factory = new DiskFileItemFactory();
	   // Create a new file upload handler
	   ServletFileUpload upload = new ServletFileUpload(factory);
//	   try{
	      // Parse the request
	         List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 

	         // Process the uploaded items
	         Iterator iter = items.iterator();

	         while (iter.hasNext()) {
	            FileItem item = (FileItem) iter.next();
	            //handling a normal form-field
	            if(item.isFormField()) {
	              // System.out.println("Got a form field");
	               String name = item.getFieldName();
	               String value = item.getString();
	               collectionMap.put(value, name);
	              // System.out.print("Name:"+name+",Value:"+value);				
	            } else {//handling file loads
	              // System.out.println("Not form field");
	               String fieldName = item.getFieldName();
	               String fileName = item.getName();
	               collectionMap.put(fileName, fieldName);
	               if (fileName != null) {
	                  fileName = FilenameUtils.getName(fileName);
	               }
	               String contentType = item.getContentType();
	               boolean isInMemory = item.isInMemory();
	               long sizeInBytes = item.getSize();
	              // System.out.print("Field Name:"+fieldName
	              // +",File Name:"+fileName);
	              // System.out.print("Content Type:"+contentType
	              // +",Is In Memory:"+isInMemory+",Size:"+sizeInBytes);			 
	               byte[] data = item.get();
	               fileName = getServletContext().getRealPath( "/db/" + fileName);
	              // System.out.print("File name:" +fileName);			
//	               FileOutputStream fileOutSt = new FileOutputStream(fileName);
//	               fileOutSt.write(data);
//	               fileOutSt.close();
	               //out.print("File Uploaded Successfully!");
	               
	               FileInputStream file = null;
	               // databaseNewName = "/Users/elena/Desktop/Incipits.png";
	               // String databaseNewName = fileUpload.getFilename();
	     				try {
	     					file = new FileInputStream(fileName);
	     				} catch (FileNotFoundException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}
	     				
	     				byte[] contents = null;
	     				
	     				try {
	     					contents = new byte[file.available()];
	     				} catch (IOException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}
	     			
	     	          
	     				try {
	     					file.read(contents);
	     				} catch (IOException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}
	     			
	     	           try {
	     				file.close();
	     			} catch (IOException e) {
	     				// TODO Auto-generated catch block
	     				e.printStackTrace();
	     			}
	     				 Collection col = null;
	     			      
	     			       
	     			     
	     			       try {    
	     			           		
	     			           col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
	     			           
	     			           col.setProperty(OutputKeys.INDENT, "no");
	     			          Resource res = col.createResource(fileName, BinaryResource.RESOURCE_TYPE);
	     			          
	     			          
	     			         collectionMap.put(fileName, input);
	     			          
//	     			           if ( type.equals(XMLResource.RESOURCE_TYPE) ) {
//	     			              res.setContent(new String(contents));
//	     			           }
//	     			           else {
	     			              res.setContent(contents);
	     			          // }
	     			           
	     			           col.storeResource(res);
	     			          for(int i = 0; i<col.listChildCollections().length; i++){
	     			        	   String colName = col.listChildCollections()[i];

	     			        	  collectionMap.put(colName, input);
	     			        	 
	     			        	   Collection currCollection = col.getChildCollection(colName);
	     			        	   if(currCollection.getChildCollectionCount()>0){
	     			        		   getTailCollection(currCollection, collectionMap);
	     			        	   }
	     			           }
	     			       } catch (XMLDBException e) {
	     						e.printStackTrace();
	     					} finally {
	     			           
	     			           if(col != null) {
	     			               try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
	     			           }
	     			       }
	               
	            }	
	         }
//	    } catch(Exception e){
//	    	e.printStackTrace();
//	    }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
//	   Collection col = null;
//      
//       Map<String, String> collectionMap = new HashMap<String, String>();
//     
//       try {    
//           		
//           col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
//           
//           col.setProperty(OutputKeys.INDENT, "no");
//           
//
//           String pathSegments[] = databaseNewName.split("/");
//           collectionMap.put(databaseNewName, pathSegments[pathSegments.length-1]);
//         String name = pathSegments[pathSegments.length-1];
//           
//           FileInputStream file = null;
//          // databaseNewName = "/Users/elena/Desktop/Incipits.png";
//          // String databaseNewName = fileUpload.getFilename();
//				try {
//					file = new FileInputStream(name);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				byte[] contents = null;
//				
//				try {
//					contents = new byte[file.available()];
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//	          
//				try {
//					file.read(contents);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//	           try {
//				file.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    
//	           
//	           Resource res = col.createResource(name, BinaryResource.RESOURCE_TYPE);
////	           if ( type.equals(XMLResource.RESOURCE_TYPE) ) {
////	              res.setContent(new String(contents));
////	           }
////	           else {
//	              res.setContent(contents);
//	          // }
//	           
//	           col.storeResource(res);
//		
//           for(int i = 0; i<col.listChildCollections().length; i++){
//        	   String colName = col.listChildCollections()[i];
//
//        	  collectionMap.put(colName, input);
//        	 
//        	   Collection currCollection = col.getChildCollection(colName);
//        	   if(currCollection.getChildCollectionCount()>0){
//        		   getTailCollection(currCollection, collectionMap);
//        	   }
//           }
//       } catch (XMLDBException e) {
//			e.printStackTrace();
//		} finally {
//           
//           if(col != null) {
//               try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
//           }
//       }
       
//       collectionMap.put(databaseUser, databasePW);
// 	  collectionMap.put("TemporaryItems", input);
// 	  collectionMap.put("apps", input);
// 	  collectionMap.put("contents", input);
// 	  collectionMap.put("system", input);
 	  
 	  //collectionMap.put("config", "system");
 	  //collectionMap.put("repo", "system");
       
      
	  
     // String messageString = "Hello " + outputTest + "!";     
      Message message = new Message();
     // message.setMessage(messageString);
      message.setMessage(collectionMap);
      
//      ImageTiles tiles = new ImageTiles();
//		tiles.run(cutImageDialog);
      
      
      
      return message;
         
   } 
   
   private int getTailCollection2(Collection col, Map<String, String> collectionMap, int max){
	   
	   if(max > 0){
	   try {
		   
		   
		   
			   for(int i = 0; i<col.listChildCollections().length; i++){
				   String colName = col.listChildCollections()[i];
				   String parentName[] = col.getName().split("/");				   
				   collectionMap.put(colName, parentName[parentName.length-1]);
				   Collection currCollection = col.getChildCollection(colName);
				   collectionMap.put(colName, ":"+col.getName()+"!"+currCollection.getChildCollectionCount()+System.lineSeparator());
	        	   if(currCollection.getChildCollectionCount()>0){
	        		  getTailCollection2(currCollection, collectionMap, max-1);
	        	   }
			   }
		   		
	} catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	   
	   }
	   return max;
   }
   
   
   private void getTailCollection(Collection col, Map<String, String> collectionMap){
	   try {
		   
		   
		   
			   for(int i = 0; i<col.listChildCollections().length; i++){
				   String colName = col.listChildCollections()[i];
				   String parentName[] = col.getName().split("/");				   
				   collectionMap.put(colName, parentName[parentName.length-1]);
				   for(int j = 0; j<col.listResources().length; j++){
		           	   String resId = col.listResources()[j];
		           	   //Resource res = col.getResource(resId);
		           	   collectionMap.put(resId, parentName[parentName.length-1]);
		              }
				  
//					   Collection currCollection = col.getChildCollection(colName);
//		        	   if(currCollection.getChildCollectionCount()>0){
//		        		  getTailCollection(currCollection, collectionMap);
//		        	   }
				   }

			  
		   
		
	} catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   return;
	   
   }
   
   private void getTailCollection(Collection col, Map<String, String> collectionMap, int toGet, int numberLevel){
	   try {
		   
		   
		   
			   for(int i = 0; i<col.listChildCollections().length; i++){
				   String colName = col.listChildCollections()[i];
				   String parentName[] = col.getName().split("/");				   
				   collectionMap.put(colName, parentName[parentName.length-1]);
				   toGet++;
				   if(toGet <= numberLevel){
					   Collection currCollection = col.getChildCollection(colName);
		        	   if(currCollection.getChildCollectionCount()>0){
		        		  getTailCollection(currCollection, collectionMap, toGet, numberLevel);
		        	   }
				   }

			   }
		   
		
	} catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   return;
	   
   }

@Override
public Message getMessageForCreate(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW) {
	Collection col = null;
    
    Map<String, String> collectionMap = new HashMap<String, String>();
    
  
    try {    
        		
        col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
        
        col.setProperty(OutputKeys.INDENT, "no");
    
        
        String newPath = input + "/"+ databaseNewName;
        Collection newcol = DatabaseManager.getCollection(databasePath+newPath, databaseUser, databasePW);
        if(newcol == null){
     	   CollectionManagementService mgt = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
 	       newcol = mgt.createCollection(databaseNewName);
        }
      
        String pathSegments[] = newPath.split("/");
        int numberLevel = pathSegments.length;
        int toGet = 0;
        
        Collection startCollection = DatabaseManager.getCollection(databasePath + pathSegments[0], databaseUser, databasePW);
        
        for(int i = 0; i<startCollection.listChildCollections().length; i++){
     	   String colName = startCollection.listChildCollections()[i];

     	  collectionMap.put(colName, pathSegments[0]);
     	  toGet ++;
     	 if(toGet <= numberLevel){
     		 Collection currCollection = startCollection.getChildCollection(colName);
       	   if(currCollection.getChildCollectionCount()>0){
       		   getTailCollection(currCollection, collectionMap, toGet, numberLevel);
       	   } 
     	 }
     	  
        }
    }

    catch (XMLDBException e) {
			e.printStackTrace();
		} finally {
        
        if(col != null) {
            try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
        }
    }
    
//    collectionMap.put(databaseUser, databasePW);
//	  collectionMap.put("TemporaryItems", input);
//	  collectionMap.put("apps", input);
//	  collectionMap.put("contents", input);
//	  collectionMap.put("system", input);
	  
	  //collectionMap.put("config", "system");
	  //collectionMap.put("repo", "system");
   // collectionMap.put(databasePath, input);
    
	  
  // String messageString = "Hello " + outputTest + "!";     
   Message message = new Message();
  // message.setMessage(messageString);
   message.setMessage(collectionMap);
   
//   ImageTiles tiles = new ImageTiles();
//		tiles.run(cutImageDialog);
   
   
   
   return message;
}
   
  

//@Override
//public Message getMessageCreate(String input, String databasePath) {
//	 Collection col = null;
//	
//	   try {    
//	       
			// get the collection
//	       col = DatabaseManager.getCollection(databasePath + "db");
//	       col.setProperty(OutputKeys.INDENT, "no");
//	       col.getChildCollection(newName);
//		   databasePath ="xmldb:exist://localhost:8080/exist/xmlrpc";
//	       Collection parent = DatabaseManager.getCollection(databasePath + "db");
//	       CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
//	       col = mgt.createCollection(newName);
//	       col.close();
//	       parent.close(); _____________________________
	       
//	       databasePath ="xmldb:exist://localhost:8080/exist/xmlrpc";
//	       String collectionUri = "db/newtest";
//	       int pathSegmentOffset = 0;
//	       
//	       Collection parent = DatabaseManager.getCollection(databasePath + "db");
//	       CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
//           col = mgt.createCollection("newtest");
//	    ----------------------------------------   
	       
	       
//	       if(collectionUri.startsWith("/")) {
//	           collectionUri = collectionUri.substring(1);
//	       }
	       
//	       String pathSegments[] = collectionUri.split("/");
//	       if(pathSegments.length > 0) {
//	           StringBuilder path = new StringBuilder();
//	           for(int i = 0; i <= pathSegmentOffset; i++) {
//	               path.append("/" + pathSegments[i]);
//	           }
//	           Collection start = DatabaseManager.getCollection(databasePath + path);
//	           if(start == null) {
//	               //collection does not exist, so create
//	               String parentPath = path.substring(0, path.lastIndexOf("/"));
//	               Collection parent = DatabaseManager.getCollection(databasePath + parentPath);
//	               CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
//	               col = mgt.createCollection(pathSegments[pathSegmentOffset]);
//	               col.close();
//	               parent.close();
//	           } else {
//	               start.close();
//	           }
//	       }
	       
	       
	       
	       
	     
//	   } catch (XMLDBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//	       //dont forget to clean up!           
//	       
//	       if(col != null) {
//	           try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
//	       }
//	   }
//	   
//	   
//		 
//	  
//	 // String messageString = "Hello " + outputTest + "!";     
//	  Message message = new Message();
//	 // message.setMessage(messageString);
//	  message.setMessageCreate("Ja");
//	  return message;
//} 
   
//   public void getThreadLocalRequest(){
//	   HttpSession session = getThreadLocalRequest().getSession();
//	   Connection connection = (Connection)session.getAttribute("connection");
//
//	   if (connection == null) {
//	        // I'll leave it to you to implement createConnection
//	        final Connection c = createConnection();
//	        connection = c;
//
//	        session.setAttribute("connection", connection);
//	        session.setAttribute("expiryListener", new HttpSessionBindingListener() {
//	            public void valueBound(HttpSessionBindingEvent e) {}
//
//	            // This method will be called when the user's session expires
//	            public void valueUnbound(HttpSessionBindingEvent e) {
//	                c.close();
//	            }
//	        });
//	   }
//
//	   // connection is ready to use!
//	   Statement statement = connection.createStatement();
//   }
}