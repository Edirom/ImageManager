package com.tutorialspoint.server;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import com.tutorialspoint.client.view.CutImageDialog;

public class ImageTiles{
	
	private final static int TILE_SIZE = 256;

	private String path;
	private String sourceDirectory;
	private Map<File, String> matches = new HashMap<File, String>();
	private int count = 1;

	private String targetDirectory;

	private String prefix;

	private int tleSize;
	
	

	/**
	 * Start cut process
	 * 
	 * @param tiles
	 */
	public void run(CutImageDialog cutImageDialog){	

		initialize(cutImageDialog);

		if(path != null && !path.isEmpty()){
			setImageProperties();
		}
		else if(sourceDirectory != null && !sourceDirectory.isEmpty()){
			setPropertiesForImages();
		}
	}

	/**
	 * Get image properties from dialog fields
	 * 
	 * @param cutImageDialog
	 */
	private void initialize(CutImageDialog cutImageDialog){
//		path = cutImageDialog.getSourcePath();
//		sourceDirectory = cutImageDialog.getSourceDirectory();
//		targetDirectory = cutImageDialog.getTargetPath();
//		prefix = cutImageDialog.getPrefix();
		if(prefix != null && !prefix.isEmpty()){
			prefix = prefix+"_";
		}
//		tleSize = cutImageDialog.getSize();
		if(tleSize > 0){
			tleSize = TILE_SIZE;
		}
	}

	/**
	 * Create file and set image type. 
	 * 
	 */
	private void setImageProperties(){
		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
				monitor.beginTask("Cut Image", 60);
				monitor.subTask("get image directory");
				File fileTest = new File(path);
				String fileType = null;
				monitor.worked(20);
				monitor.subTask("get image type");
				String[] names = ImageIO.getWriterFormatNames();
				for(int i1=0; i1 < names.length; i1++) {
					if (fileTest.getName().contains(names[i1])) {
						fileType = names[i1];
					}								  

				}
				monitor.worked(20);
				monitor.subTask("cut image" + fileTest.getName()) ;
				if(fileType != null){
					cutImage(fileTest, fileType);
				}
				monitor.worked(20);
				monitor.done();
			}
		};
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
		try {
			dialog.run(true, false, runnableWithProgress);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create file and directory, set image type.
	 * 
	 */
	private void setPropertiesForImages(){
		File dir = new File(sourceDirectory);
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String[] names = ImageIO.getWriterFormatNames();
				for(int i1=0; i1 < names.length; i1++) {
					if (files[i].getName().contains(names[i1])) {
						matches.put(files[i], names[i1]);
					}								  
				}
			}
		}
		if( matches.keySet().size() > 1) {
			count = matches.keySet().size();
		}

		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
				monitor.beginTask("Cut Image", 40+count*40);
				monitor.subTask("get image directory");

				String originalTarget = targetDirectory;
				monitor.worked(20);
				monitor.subTask("cut images");
				for(File file: matches.keySet()){						
					monitor.subTask("create directory tiles for "+file.getName());
					String name = file.getName();
					name = name.substring(0, name.lastIndexOf("."));							
					File dirNew = new File(originalTarget+File.separator+name);
					dirNew.mkdir();
					targetDirectory = dirNew.getAbsolutePath();

					monitor.worked(20);
					monitor.subTask("cut image" + file.getName());
					cutImage(file, matches.get(file));
					monitor.worked(20);
				}
				monitor.worked(20);
				monitor.done();
			}
		};
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
		try {
			dialog.run(true, false, runnableWithProgress);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compute max zoom level
	 * 
	 * @param minSize
	 * @param tileSize
	 * @return newZoom
	 */
	private int getMaxZoomLevel(int minSize, int tileSize){
		int newZoom = 0;
		while(minSize > tileSize){
			minSize = minSize/2;			
			newZoom++;
		}
		return newZoom;
	}

	/**
	 * Create buffered image and start cut.
	 * 
	 * @param fileTest
	 * @param fileType
	 */
	private void cutImage(File fileTest, String fileType){
		BufferedImage originalImgage = null;
		try {
			originalImgage = ImageIO.read(fileTest);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int originalWidth = originalImgage.getWidth();
		int originalHight = originalImgage.getHeight();
		int minSize;

		if(originalWidth < originalHight ){
			minSize = originalHight;
		}
		else{
			minSize = originalWidth;
		}

		int maxZoomLevel = getMaxZoomLevel(minSize, tleSize);

		// cut original image for max zoom level
		if(maxZoomLevel > 0){
			cut(originalImgage, prefix, targetDirectory, tleSize, maxZoomLevel, fileType); 
			maxZoomLevel --;
		}

		int wight = originalImgage.getWidth()/2;
		int hight = originalImgage.getHeight()/2;

		// minimize image in 50% and cut this
		while(wight >tleSize || hight>tleSize){
			Image img = new ImageIcon(originalImgage).getImage();
			Image scaledImage = img.getScaledInstance(wight, hight, 
					Image.SCALE_FAST);
			BufferedImage outImg = new BufferedImage(wight, hight, 
					BufferedImage.TYPE_INT_RGB);
			Graphics g = outImg.getGraphics();
			g.drawImage(scaledImage, 0, 0, null);
			g.dispose();

			cut(outImg, prefix, targetDirectory, tleSize, maxZoomLevel, fileType);

			wight = wight/2;
			hight = hight/2;
			maxZoomLevel--;
		}

		// create image for min zoom level
		if(wight <= tleSize && hight <= tleSize){
			Image img = new ImageIcon(originalImgage).getImage();
			Image scaledImage = img.getScaledInstance(wight, hight, 
					Image.SCALE_FAST);
			BufferedImage outImg = new BufferedImage(wight, hight, 
					originalImgage.getType());
			Graphics g = outImg.getGraphics();
			g.drawImage(scaledImage, 0, 0, null);
			g.dispose();

			try {
				ImageIO.write(outImg, fileType, new File(targetDirectory+File.separator+0+"-"+0+"-"+0+"."+fileType));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cut image in tiles and write in directory
	 * 
	 * @param image
	 * @param prefix
	 * @param targetDirectory
	 * @param size
	 * @param zoomIndex
	 * @param fileType
	 */
	private void cut(BufferedImage image, String prefix, String targetDirectory, int size,
			int zoomIndex, String fileType){

		int originalHeight = image.getHeight();
		double originalHight_1 = (double) image.getHeight();
		double rows_1 =(double) (originalHight_1/size);  
		int rows = (int) Math.ceil(rows_1);

		int originalWidth = image.getWidth();
		double originalWidth_1 = (double) image.getWidth();
		double cols_1 =(double) (originalWidth_1/size);  	
		int cols = (int) Math.ceil(cols_1);

		int chunks = rows * cols;  
		int chunkWidth = size; 
		int chunkHeight = size; 
		int count = 0;  
		BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks  
		for (int x = 0; x < rows; x++) {  
			for (int y = 0; y < cols; y++) {  
				int newW =0;
				int newH=0;
				// new width tile size as remainder
				if(y == cols-1){
					newW = originalWidth-size*y;
				}
				// new height tile size as remainder
				if(x == rows-1){
					newH = originalHeight-size*x;
				}

				imgs[count] = new BufferedImage((newW!=0 ? newW : size), (newH!=0 ? newH : size), image.getType());  

				int test = count++;

				Graphics2D gr = imgs[test].createGraphics();  

				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, 
						chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
				gr.dispose(); 

				try {
					ImageIO.write(imgs[test], fileType, new File(targetDirectory+File.separator+zoomIndex+"-"+y+"-"+x+"."+fileType));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}  
		}   
	} 

}
