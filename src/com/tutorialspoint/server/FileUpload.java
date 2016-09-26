package com.tutorialspoint.server;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.commons.io.output.DeferredFileOutputStream;
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
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

import java.util.logging.Level;
import java.util.logging.LogRecord;


import java.util.Iterator;
import java.io.File;


public class FileUpload extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1088429096511375185L;
	
	//private static final String UPLOAD_DIRECTORY = "d:\\uploaded\\";
	 
	     @Override
	     protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	             throws ServletException, IOException {
	         super.doGet(req, resp);
	     }
	 
	     @Override
	     protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	             throws ServletException, IOException {
	         
	         // process only multipart requests
	         if (ServletFileUpload.isMultipartContent(req)) {
	 
	             // Create a factory for disk-based file items
	             FileItemFactory factory = new DiskFileItemFactory();
	 
	             // Create a new file upload handler
	             ServletFileUpload upload = new ServletFileUpload(factory);
	 
	             // Parse the request
	             try {
	                 List<FileItem> items = upload.parseRequest(req);
	                 for (FileItem item : items) {
	                     // process only file upload - discard other form item types
	                	 if (item.isFormField()) continue;
//	                     if (item.isFormField()) {
//	                    	// if(item.getFieldName().equals("textBoxFormElement")){
//	                    	//	 DeferredFileOutputStream outputPath = (DeferredFileOutputStream) item.getOutputStream();
//	                    		 dbPath = item.getFieldName();
//	                    		 String nameTest = item.getName();
//	                    	// }
//	                    	 
//	                     }
//	                     else{	 
	                    	 
	                    	
	                     
	                     String fileName = item.getName();
	                     // get only the file name not whole path
	                     if (fileName != null) {
	                         fileName = FilenameUtils.getName(fileName);
	                     }
	 
	                     File uploadedFile = new File("/Users/Shared/", fileName);
	                     if (uploadedFile.createNewFile()) {
	                         item.write(uploadedFile);
	                         resp.setStatus(HttpServletResponse.SC_CREATED);
	                         resp.getWriter().print("The file was created successfully.");
	                         resp.flushBuffer();
	                     } else
	                         throw new IOException("The file already exists in repository.");
	                        
	                     
	                 }
	             } catch (Exception e) {
	                 resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	                         "An error occurred while creating the file : " + e.getMessage());
	             }
	 
	         } else {
	             resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
	                             "Request contents type is not supported by the servlet.");
	         }
	     }


//	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
//		SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();
//		remoteLog.publish(new LogRecord(Level.INFO, "log message"));
		
		
//		Collection col = null;
//		try {
//			col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc" + "db", "admin", "");
//			col.setProperty(OutputKeys.INDENT, "no");
//		} catch (XMLDBException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		
//		FileItemFactory factory = new DiskFileItemFactory();
//		   // Create a new file upload handler
//		   ServletFileUpload upload = new ServletFileUpload(factory);
//		   try{
//		      // Parse the request
//		         List items = upload.parseRequest(request); 
//
//		         // Process the uploaded items
//		         Iterator iter = items.iterator();
//
//		         while (iter.hasNext()) {
//		            FileItem item = (FileItem) iter.next();
//		               String fieldName = item.getFieldName();
//		               String fileName = item.getName();
//		               if (fileName != null) {
//		                  fileName = FilenameUtils.getName(fileName);
//		               }
//		               String contentType = item.getContentType();
//		               boolean isInMemory = item.isInMemory();
//		               long sizeInBytes = item.getSize();
//		               System.out.print("Field Name:"+fieldName+",File Name:"+fileName);
//		               //remoteLog.publish(new LogRecord(Level.INFO, "Field Name:"+fieldName+",File Name:"+fileName));
//		               System.out.print("Content Type:"+contentType+",Is In Memory:"+isInMemory+",Size:"+sizeInBytes);
//		               //remoteLog.publish(new LogRecord(Level.INFO, "Content Type:"+contentType+",Is In Memory:"+isInMemory+",Size:"+sizeInBytes));
//		               byte[] data = item.get();
//		               fileName = RequestFactoryServlet.getThreadLocalServletContext().getRealPath("/usr/local/share/"+fileName);
//		            		   //getServletContext().getRealPath("http://localhost:8080/uploadedFiles/"+fileName);
//		               FileOutputStream fileOutSt = new FileOutputStream(fileName);
//			           fileOutSt.write(data);
//			           fileOutSt.close();
//		           // }	
//		         }
//		    } catch(Exception e){
//		    	e.printStackTrace();
//		    }
		
		
		
		
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

//	}
//}




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

}
