package com.tutorialspoint.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;

//import javax.xml.transform.OutputKeys;

//import org.exist.xmldb.EXistResource;
//import org.xmldb.api.*;
//import org.xmldb.api.base.*;
//import org.xmldb.api.modules.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.Message;
import com.tutorialspoint.client.controller.MessageService;

public class MessageServiceImpl extends RemoteServiceServlet 
implements MessageService{

	private static final long serialVersionUID = 1L;


	public ContentDB getMessage(String input, String databasePath, String databaseUser, String databasePW) {

		//Collection col = null;

		Map<List<String>, String> collectionMap = new HashMap<List<String>, String>();

		//		try {    
		//
		//			col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
		//
		//			col.setProperty(OutputKeys.INDENT, "no");
		//
		//			for(int i = 0; i<col.listChildCollections().length; i++){
		//				String colName = col.listChildCollections()[i];
		//
		//				List<String> collFolderData = new ArrayList<String>();
		//				collFolderData.add(colName);
		//				collFolderData.add("Folder");
		//				collectionMap.put(collFolderData, col.getName());
		//
		//				for(int j = 0; j<col.listResources().length; j++){
		//					String resId = col.listResources()[j];
		//					
		//					List<String> collFileData = new ArrayList<String>();
		//					collFileData.add(resId);
		//					collFileData.add("File");
		//					collectionMap.put(collFileData, col.getName());
		//
		//				}
		//
		//			}
		//
		//		} catch (XMLDBException e) {
		//			e.printStackTrace();
		//		} finally {
		//
		//			if(col != null) {
		//				try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
		//			}
		//		}

		String inputName[] = input.split("/");
		String parentName = inputName[inputName.length-1];

		if(parentName.equals("system")){
			List<String> testList_22 = new ArrayList<String>();
			testList_22.add("system_1");
			testList_22.add("Folder");
			collectionMap.put(testList_22, parentName);

			List<String> testList_222 = new ArrayList<String>();
			testList_222.add("system_12");
			testList_222.add("Folder");
			collectionMap.put(testList_222, parentName);

		}
		else{
			List<String> testList = new ArrayList<String>();
			testList.add("TemporaryItems");
			testList.add("File");
			collectionMap.put(testList, parentName);

			List<String> testList_1 = new ArrayList<String>();
			testList_1.add("apps");
			testList_1.add("Folder");
			collectionMap.put(testList_1, parentName);

			List<String> testList_2 = new ArrayList<String>();
			testList_2.add("contents");
			testList_2.add("Folder");
			collectionMap.put(testList_2, parentName);

			List<String> testList_3 = new ArrayList<String>();
			testList_3.add("system");
			testList_3.add("Folder");
			collectionMap.put(testList_3, parentName);
		} 

		ContentDB message = new ContentDB();		
		message.setMessage(collectionMap);

		//+++++++++++++++++++++++++++       
		//      ImageTiles tiles = new ImageTiles();
		//		tiles.run(cutImageDialog);
		//+++++++++++++++++++++++++++ 

		return message;

	} 

	private void getTailCollection(Collection col, Map<List<String>, String> collectionMap, int toGet, int numberLevel){
		try {



			for(int i = 0; i<col.listChildCollections().length; i++){
				String colName = col.listChildCollections()[i];
				String parentName[] = col.getName().split("/");
				List<String> collFolderData = new ArrayList<String>();
				collFolderData.add(colName);
				collFolderData.add("Folder");
				collectionMap.put(collFolderData, parentName[parentName.length-1]);
				//collectionMap.put(colName, parentName[parentName.length-1]);
				for(int j = 0; j<col.listResources().length; j++){
					String resId = col.listResources()[j];
					//Resource res = col.getResource(resId);
					List<String> collFileData = new ArrayList<String>();
					collFileData.add(resId);
					collFileData.add("File");
					collectionMap.put(collFileData, parentName[parentName.length-1]);
					//collectionMap.put(resId, parentName[parentName.length-1]);
				}
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
	public ContentDB getMessageForDelete(String selectedPath, String databasePath, String databaseUser,String databasePW) {
		//Collection col = null;

		Map<List<String>, String> collectionMap = new HashMap<List<String>, String>();


		//				try {    
		//		
		//					col = DatabaseManager.getCollection(databasePath + selectedPath, databaseUser, databasePW);
		//		
		//					col.setProperty(OutputKeys.INDENT, "no");
		//		
		//					String newPath = selectedPath;
		//					Collection newcol = DatabaseManager.getCollection(databasePath+newPath, databaseUser, databasePW);
		//					if(newcol != null){
		//						CollectionManagementService mgt = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
		//						mgt.removeCollection(newcol.getName());
		//					}
		//				}
		//				
		//						catch (XMLDBException e) {
		//							e.printStackTrace();
		//						} finally {
		//				
		//							if(col != null) {
		//								try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
		//							}
		//						}	

		ContentDB message = new ContentDB();
		message.setMessage(collectionMap);

		return message;


	}

	@Override
	public ContentDB getMessageForCreate(String databaseNewName, String input, String databasePath, String databaseUser, String databasePW) {
		//Collection col = null;

		Map<List<String>, String> collectionMap = new HashMap<List<String>, String>();


		//		try {    
		//
		//			col = DatabaseManager.getCollection(databasePath + input, databaseUser, databasePW);
		//
		//			col.setProperty(OutputKeys.INDENT, "no");
		//
		//			String newPath = input + "/"+ databaseNewName;
		//			Collection newcol = DatabaseManager.getCollection(databasePath+newPath, databaseUser, databasePW);
		//			if(newcol == null){
		//				CollectionManagementService mgt = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
		//				newcol = mgt.createCollection(databaseNewName);
		//				
		//				List<String> collFolderData = new ArrayList<String>();
		//				collFolderData.add(newcol.getName());
		//				collFolderData.add("Folder");
		//				collectionMap.put(collFolderData, col.getName());
		//				
		//			}


		//*******************************************************************************************
		//			String pathSegments[] = newPath.split("/");
		//			int numberLevel = pathSegments.length;
		//			int toGet = 0;
		//
		//			Collection startCollection = DatabaseManager.getCollection(databasePath + pathSegments[0], databaseUser, databasePW);
		//
		//			for(int i = 0; i<startCollection.listChildCollections().length; i++){
		//				String colName = startCollection.listChildCollections()[i];
		//				
		//				List<String> collFolderData = new ArrayList<String>();
		//				collFolderData.add(colName);
		//				collFolderData.add("Folder");
		//				collectionMap.put(collFolderData, col.getName());
		//
		//				//collectionMap.put(colName, pathSegments[0]);
		//				for(int j = 0; j<col.listResources().length; j++){
		//					String resId = col.listResources()[j];
		//					List<String> collFileData = new ArrayList<String>();
		//					collFileData.add(resId);
		//					collFileData.add("File");
		//					collectionMap.put(collFileData, col.getName());
		//					//collectionMap.put(resId, pathSegments[0]);
		//				}
		//				toGet ++;
		//				if(toGet <= numberLevel){
		//					Collection currCollection = startCollection.getChildCollection(colName);
		//					if(currCollection.getChildCollectionCount()>0){
		//						getTailCollection(currCollection, collectionMap, toGet, numberLevel);
		//					} 
		//				}
		//
		//			}
		//***************************************************


		//		}
		//
		//		catch (XMLDBException e) {
		//			e.printStackTrace();
		//		} finally {
		//
		//			if(col != null) {
		//				try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
		//			}
		//		}

		String inputName[] = input.split("/");
		String parentName = inputName[inputName.length-1];

		List<String> testList = new ArrayList<String>();
		testList.add(databaseNewName);
		testList.add("Folder");
		collectionMap.put(testList, parentName);

		ContentDB message = new ContentDB();
		message.setMessage(collectionMap);

		return message;
	}
	
	public ContentDB getMessageUpload(String databaseFolder, String databasePath, String databaseUser, String databasePW) {

		Map<List<String>, String> collectionMap = new HashMap<List<String>, String>();

		File folder = new File("/Users/Shared/");
		File[] listOfFiles = folder.listFiles();
		
		Collection col = null;
		try {
			col = DatabaseManager.getCollection(databasePath + databaseFolder, databaseUser, databasePW);
			col.setProperty(OutputKeys.INDENT, "no");
		} catch (XMLDBException e1) {
			e1.printStackTrace();
		}	

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().contains(".png") || listOfFiles[i].getName().contains(".jpg") || listOfFiles[i].getName().contains(".tif")
						|| listOfFiles[i].getName().contains(".PNG") || listOfFiles[i].getName().contains(".JPG") || listOfFiles[i].getName().contains(".TIF")){
					try {

						String databaseNewName = listOfFiles[i].getName();
						FileInputStream file = new FileInputStream("/Users/Shared/"+databaseNewName);

						byte[] contents = new byte[file.available()];

						file.read(contents);
						file.close();

						Resource res;
						try {
							res = col.createResource(databaseNewName, BinaryResource.RESOURCE_TYPE);
							res.setContent(contents);
							col.storeResource(res);

							List<String> collFileData = new ArrayList<String>();
							collFileData.add(databaseNewName);
							collFileData.add("File");
							collectionMap.put(collFileData, col.getName());

						} catch (XMLDBException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

		ContentDB message = new ContentDB();
		message.setMessage(collectionMap);

		return message;

	} 


	public ContentDB getMessageImageTiles(String databaseFolder, String databasePath, String databaseUser, String databasePW) {

		Map<List<String>, String> collectionMap = new HashMap<List<String>, String>();

		File folder = new File("/Users/Shared/");
		File[] listOfFiles = folder.listFiles();
		
		
		ImageTiles tiles = new ImageTiles();
		//tiles.run(cutImageDialog);
		

		Collection col = null;
		try {
			col = DatabaseManager.getCollection(databasePath + databaseFolder, databaseUser, databasePW);
			col.setProperty(OutputKeys.INDENT, "no");
		} catch (XMLDBException e1) {
			e1.printStackTrace();
		}	

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().contains(".png") || listOfFiles[i].getName().contains(".jpg") || listOfFiles[i].getName().contains(".tif")
						|| listOfFiles[i].getName().contains(".PNG") || listOfFiles[i].getName().contains(".JPG") || listOfFiles[i].getName().contains(".TIF")){
					try {

						String databaseNewName = listOfFiles[i].getName();
						FileInputStream file = new FileInputStream("/Users/Shared/"+databaseNewName);

						byte[] contents = new byte[file.available()];

						file.read(contents);
						file.close();

						Resource res;
						try {
							res = col.createResource(databaseNewName, BinaryResource.RESOURCE_TYPE);
							res.setContent(contents);
							col.storeResource(res);

							List<String> collFileData = new ArrayList<String>();
							collFileData.add(databaseNewName);
							collFileData.add("File");
							collectionMap.put(collFileData, col.getName());

						} catch (XMLDBException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

		ContentDB message = new ContentDB();
		message.setMessage(collectionMap);

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