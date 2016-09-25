package com.tutorialspoint.server;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;

import org.apache.commons.el.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator; 
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jetty.util.log.Log;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;

import com.google.gwt.core.client.GWT;
import com.tutorialspoint.client.Message;
import com.tutorialspoint.client.MessageService;
import com.tutorialspoint.client.MessageServiceAsync; 


import com.google.gwt.logging.client.SimpleRemoteLogHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


import java.util.Iterator;
import java.io.File;


public class FileUpload extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1088429096511375185L;


	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
//		SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();
//		remoteLog.publish(new LogRecord(Level.INFO, "log message"));
		
		
		Collection col = null;
		try {
			col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc" + "db", "admin", "");
			col.setProperty(OutputKeys.INDENT, "no");
		} catch (XMLDBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		FileItemFactory factory = new DiskFileItemFactory();
		   // Create a new file upload handler
		   ServletFileUpload upload = new ServletFileUpload(factory);
		   try{
		      // Parse the request
		         List items = upload.parseRequest(request); 

		         // Process the uploaded items
		         Iterator iter = items.iterator();

		         while (iter.hasNext()) {
		            FileItem item = (FileItem) iter.next();
		            //handling a normal form-field
		            if(item.isFormField()) {
		               //System.out.println("Got a form field");
		              // remoteLog.publish(new LogRecord(Level.INFO, "Got a form field"));
		               String name = item.getFieldName();
		               String value = item.getString();
		              // System.out.print("Name:"+name+",Value:"+value);
		               //remoteLog.publish(new LogRecord(Level.INFO, "Name:"+name+",Value:"+value));
		            } else {//handling file loads
		               //System.out.println("Not form field");
		               //remoteLog.publish(new LogRecord(Level.INFO, "Not form field"));
		               String fieldName = item.getFieldName();
		               String fileName = item.getName();
		               if (fileName != null) {
		                  fileName = FilenameUtils.getName(fileName);
		               }
		               String contentType = item.getContentType();
		               boolean isInMemory = item.isInMemory();
		               long sizeInBytes = item.getSize();
		               //System.out.print("Field Name:"+fieldName+",File Name:"+fileName);
		               //remoteLog.publish(new LogRecord(Level.INFO, "Field Name:"+fieldName+",File Name:"+fileName));
		               //System.out.print("Content Type:"+contentType+",Is In Memory:"+isInMemory+",Size:"+sizeInBytes);
		               //remoteLog.publish(new LogRecord(Level.INFO, "Content Type:"+contentType+",Is In Memory:"+isInMemory+",Size:"+sizeInBytes));
		               byte[] data = item.get();
		               fileName = getServletContext().getRealPath("http://localhost:8080/uploadedFiles/"+fileName);
		               FileOutputStream fileOutSt = new FileOutputStream(fileName);
			           fileOutSt.write(data);
			           fileOutSt.close();
		               
		               
		               
		              // FileInputStream file = null;
						// databaseNewName = "/Users/elena/Desktop/Incipits.png";
						// String databaseNewName = fileUpload.getFilename();
		
//						file = new FileInputStream(fileName);
//		
//						byte[] contents = null;
//		
//						contents = new byte[file.available()];
//		
//						file.read(contents);
//						file.close();
//						 String pathSegments[] = fileName.split("/");
//						 String name = pathSegments[pathSegments.length-1];
//						Resource res = col.createResource(name, BinaryResource.RESOURCE_TYPE);
//						res.setContent(contents);
//						col.storeResource(res);
		               //System.out.print("File name:" +fileName);	
		               //remoteLog.publish(new LogRecord(Level.INFO, "File name:" +fileName));
		              // FileOutputStream fileOutSt = new FileOutputStream(fileName);
		              // fileOutSt.write(data);
		              // fileOutSt.close();
		               //out.print("File Uploaded Successfully!");
		            }	
		         }
		    } catch(Exception e){
		    	e.printStackTrace();
		    }
		
		
		
		
//		ServletFileUpload upload = new ServletFileUpload();
//
//		SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();
//		remoteLog.publish(new LogRecord(Level.INFO, "log message"));
//
//		//  try{
//		FileItemIterator iter = null;
//		try {
//			iter = upload.getItemIterator(request);
//		} catch (FileUploadException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		try {
//			while (iter.hasNext()) {
//				FileItemStream item = iter.next();
//
//				String name = item.getFieldName();
//
//				FileInputStream file = null;
//				// databaseNewName = "/Users/elena/Desktop/Incipits.png";
//				// String databaseNewName = fileUpload.getFilename();
//
//				file = new FileInputStream(name);
//
//				byte[] contents = null;
//
//				contents = new byte[file.available()];
//
//				file.read(contents);
//				file.close();
//
//				Collection col = null;
//
//				col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc" + "db", "admin", "");
//
//				col.setProperty(OutputKeys.INDENT, "no");
//				Resource res = col.createResource(name, BinaryResource.RESOURCE_TYPE);
//
//				res.setContent(contents);
//
//				col.storeResource(res);
//
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (FileUploadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (XMLDBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
}




//            InputStream stream = item.openStream();

//
//                // Process the input stream
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                int len;
//                byte[] buffer = new byte[8192];
//                while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
//                    out.write(buffer, 0, len);
//                }

//                int maxFileSize = 10*(1024*1024); //10 megs max 
//                if (out.size() > maxFileSize) { 
//                    throw new RuntimeException("File is > than " + maxFileSize);
//                }
//           }
//        }
//        catch(Exception e){
//            throw new RuntimeException(e);
//        }

//   }

//}
