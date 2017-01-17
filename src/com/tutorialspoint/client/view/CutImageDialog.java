package com.tutorialspoint.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tutorialspoint.client.ImageManager;
import com.tutorialspoint.client.controller.ContentDB;
import com.tutorialspoint.client.controller.MessageService;
import com.tutorialspoint.client.controller.MessageServiceAsync;
import com.tutorialspoint.client.model.Folder;

public class CutImageDialog extends DialogBox {
	
	private MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	private final static int TILE_SIZE = 256;
	private Folder selectedFolder;
	
	native void jsClickUpload(Element pElement) /*-{
    pElement.click();
}-*/;
	
	public CutImageDialog() {

		// Set the dialog box's caption.
		setText("Cut Image(s)");

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

	}
	
	public void createFields() {
		
		final FormPanel form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() + "upload");

		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		Label lblDatenBankPath = new Label("Databasepath");
		final TextBox tb = new TextBox();
		tb.setName("textBoxFormElement");
		tb.setWidth("300");
		selectedFolder = FolderPanel.selectionModel.getSelectedObject();
		String selectedPath = selectedFolder.getPath();        
		tb.setValue(selectedPath);
		tb.setEnabled(false);
		
		
		final FileUpload upload = new FileUpload();
		upload.setName("imageTilesFormElement");
		upload.getElement().setPropertyBoolean("multiple", true);
		//upload.addStyleName("gwt-FileUpload");
		upload.setWidth("430");
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				if (upload.getFilename().length() == 0) {
					Window.alert("The text box must not be empty");
					event.cancel();
				}
			}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				messageService.getMessageImageTiles(tb.getValue(), ImageManager.databasePath, ImageManager.databaseUser, ImageManager.databasePW, new MessageCallBack());
				hide();
			}
		});  

		HorizontalPanel hPanel_1 = new HorizontalPanel();	
		hPanel_1.add(lblDatenBankPath);
		hPanel_1.add(tb);
		hPanel_1.setCellWidth(lblDatenBankPath, "130");

		HorizontalPanel hPanelBoxes = new HorizontalPanel();
		hPanelBoxes.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		}));

		hPanelBoxes.add(new Button("Cancel", new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		}));   	 	   
		hPanelBoxes.setSpacing(10);
		
		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("00");
		panel.setWidth("00");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		panel.add(hPanel_1);
		panel.add(upload);
		panel.add(hPanelBoxes);

		form.setWidget(panel);

		setWidget(form);
		
	}
	
	private class MessageCallBack implements AsyncCallback<ContentDB> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: " 
					+ caught.getMessage());	
		}
		@Override
		public void onSuccess(ContentDB result) {
			Map<List<String>, String> resultMap = result.getMessage();

			String listOutput = "";
			for(List<String> nameKey : resultMap.keySet()){
				listOutput =listOutput + " ,"+ nameKey.get(0);
				Folder newFolder = new Folder(nameKey.get(0));
				newFolder.setTypeFolder(nameKey.get(1));
				selectedFolder.addFolderlist(newFolder);
				newFolder.setParent(selectedFolder);

			}

			selectedFolder.refresh();
		}	   
	}
	

	
//	protected Control createDialogArea(Composite topParent) {
//		this.getShell().setText("Cut Image");
//
//		leftLabelWidt = getMaximalLabelWidth(rightLabels, topParent);
//
//		Composite parent = createComposite(topParent, 1);
//		createSourceSection(parent);
//		createTargetSection(parent);
//		return parent;
//	}


	
	/**
	 * Method for create controls in source section.
	 * 
	 * @param parent
	 */
//	private void createSourceSection(Composite parent) {
//	
//		Section sourceSection = createDetailSection(parent, SWT.NONE, "Source", 1);
//		Composite sourceComposite = createDetailComposite(sourceSection, 2, false);
//		sourceSection.setClient(sourceComposite);		
//		Composite sourceDetailsComposite = createComposite(sourceComposite, 4);
//
//		// Info composite
//		Composite infoComposite = new Composite(sourceDetailsComposite, SWT.NONE);
//		infoComposite.setLayout(new GridLayout(3, false));
//		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
//		data.horizontalSpan = 4;
//		infoComposite.setLayoutData(data);
//		Label infoLabel = new Label(infoComposite, SWT.NONE);
//		infoLabel.setImage(infoComposite.getShell().getDisplay().getSystemImage(SWT.ICON_INFORMATION));
//		Text infoText = new Text(infoComposite, SWT.READ_ONLY | SWT.MULTI);
//		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
//		infoText.setLayoutData(data);
//		infoText.setText("Select an image or a derectory for cut more images");
//		infoText.setBackground(infoComposite.getBackground());
//
//		// Selection sources area
//		// Image selection
//		createLabel(sourceDetailsComposite, "Image", leftLabelWidt);
//		Composite mandatoryImage= createDecorator(sourceDetailsComposite);
//		//addDrawListener(mandatoryImage);		
//		Composite imageSelectionComposite = createComposite(sourceDetailsComposite, 1);
//		Text sourcePathControl = createText(imageSelectionComposite, "");
//		sourcePathControl.setEditable(false);
//
//		final Button sourceDialogButton = new Button(sourceDetailsComposite, SWT.PUSH);
//		data = new GridData(SWT.FILL, SWT.FILL, false, false);
//		sourceDialogButton.setLayoutData(data);
//		sourceDialogButton.setText("...");
//		
//		// Directory selection
//		createLabel(sourceDetailsComposite, "Source directory", leftLabelWidt);
//		Composite mandatorySourceDecorator= createDecorator(sourceDetailsComposite);
//		//addDrawListener(mandatorySourceDecorator);		
//		Composite directorySelectionComposite = createComposite(sourceDetailsComposite, 1);		
//		Text sourceDirectoryControl = createText(directorySelectionComposite, "");		
//		sourceDirectoryControl.setEditable(false);
//
//		final Button sourceDirectoryButton = new Button(sourceDetailsComposite, SWT.PUSH);
//		data = new GridData(SWT.FILL, SWT.FILL, false, false);
//		sourceDirectoryButton.setLayoutData(data);
//		sourceDirectoryButton.setText("...");
//		sourceDirectoryButton.addSelectionListener(new SelectionListener() {
//			public void widgetDefaultSelected(SelectionEvent e) {
//				widgetSelected(e);
//			}
//			public void widgetSelected(SelectionEvent e) {
//				DirectoryDialog fileDialog = new DirectoryDialog(getShell());
//				sourceDirectory = fileDialog.open();
//				if (sourceDirectory != null) {
//					sourceDirectoryControl.setText(sourceDirectory);
//					if (sourcePath != null) {
//						sourcePath = null;
//						sourcePathControl.setText("");
//					}	
//				}
//				handleOkButton();
//			}
//		});			
//		sourceDialogButton.addSelectionListener(new SelectionListener() {
//			public void widgetDefaultSelected(SelectionEvent e) {
//				widgetSelected(e);
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				FileDialog fileDialog = new FileDialog(getShell());
//				sourcePath = fileDialog.open();
//				if (sourcePath != null) {
//					sourcePathControl.setText(sourcePath);
//					if (sourceDirectory != null) {
//						sourceDirectory = null;
//						sourceDirectoryControl.setText("");
//					}				
//				}
//				handleOkButton();
//			}
//		});
//	}

	/**
	 * Method to create controls in target section
	 * 
	 * @param parent
	 */
//	private void createTargetSection(Composite parent) {
//		
//		Section targetSection = createDetailSection(parent, SWT.NONE, "Target", 1);
//		Composite targetComposite = createDetailComposite(targetSection, 2, false);
//		targetSection.setClient(targetComposite);
//		Composite tergetDetailsComposite = createComposite(targetComposite, 4);
//		
//		// Info composite
//		Composite infoComposite = new Composite(tergetDetailsComposite, SWT.NONE);
//		infoComposite.setLayout(new GridLayout(2, false));
//		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
//		data.horizontalSpan = 4;
//		infoComposite.setLayoutData(data);
//		Label infoLabel = new Label(infoComposite, SWT.NONE);
//		infoLabel.setImage(infoComposite.getShell().getDisplay().getSystemImage(SWT.ICON_INFORMATION));
//		Text infoText = new Text(infoComposite, SWT.READ_ONLY | SWT.MULTI);
//		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
//		infoText.setLayoutData(data);
//		infoText.setText("If a directory for source is selected, "
//				+ "for each original image \na directory "
//				+ "in target directory with tiles will be created");
//		infoText.setBackground(infoComposite.getBackground());
//
//		// Target directory
//		createLabel(tergetDetailsComposite, "Target directory", leftLabelWidt);
//		Composite mandatoryTargetDecorator= createDecorator(tergetDetailsComposite);
//		
//		//addDrawListener(mandatoryTargetDecorator);
//		Composite targetPathComposite = createComposite(tergetDetailsComposite, 1);
//		Text targetPathControl = createText(targetPathComposite, "");
//		targetPathControl.setEditable(false);
//
//		final Button targetDialogButton = new Button(tergetDetailsComposite, SWT.PUSH);
//		data = new GridData(SWT.FILL, SWT.FILL, false, false);
//		targetDialogButton.setLayoutData(data);
//		targetDialogButton.setText("...");
//		targetDialogButton.addSelectionListener(new SelectionListener() {
//			public void widgetDefaultSelected(SelectionEvent e) {
//				widgetSelected(e);
//			}
//			public void widgetSelected(SelectionEvent e) {
//				DirectoryDialog fileDialog = new DirectoryDialog(getShell());
//				targetPath = fileDialog.open();
//				if (targetPath != null) {
//					targetPathControl.setText(targetPath);
//				}
//				handleOkButton();
//			}
//		});
//
//		// Image prefix
//		createLabel(tergetDetailsComposite, "Images prefix", leftLabelWidt);
//		createDecorator(tergetDetailsComposite);
//		Composite imsgePrefixComposite = createComposite(tergetDetailsComposite, 1);
//		((GridData) imsgePrefixComposite.getLayoutData()).horizontalSpan = 2;
//		Text prefixControl = createText(imsgePrefixComposite, "");
//		prefixControl.addModifyListener(new ModifyListener() {
//			@Override
//			public void modifyText(ModifyEvent arg0) {
//				prefix = prefixControl.getText();	
//			}
//		});
//
//		// Tile size
//		createLabel(tergetDetailsComposite, "Tile size", leftLabelWidt);
//		createDecorator(tergetDetailsComposite);		
//		Composite tileSizeComposite = createComposite(tergetDetailsComposite, 1);
//		((GridData) tileSizeComposite.getLayoutData()).horizontalSpan = 2;
//		Text sizeControl = createText(tileSizeComposite, "256");
//		((GridData) sizeControl.getLayoutData()).horizontalIndent = 2;
//		sizeControl.addModifyListener(new ModifyListener() {
//			@Override
//			public void modifyText(ModifyEvent arg0) {
//				if(sizeControl.getText() != null && !sizeControl.getText().isEmpty()){
//					String newValue = sizeControl.getText();
//					try {
//						size =Integer.parseInt(newValue);
//						if(size <= 0 || newValue.startsWith("0")){
//							errorCode = 1;
//						}
//						else{
//							errorCode = 0;
//						}
//					} catch (NumberFormatException e) {
//						errorCode = 1;
//					}
//				}
//				else {
//					size = TILE_SIZE;
//					errorCode = 0;
//				}	
//				handleOkButton();
//			}
//		});
//	}

	/**
	 * Validate input for controls and enable cut-button
	 * 
	 * @return result
	 */
//	private boolean handleOkButton() {
//		boolean result = true;
//		//sourceDirectory
//		if((sourcePath == null || sourcePath.isEmpty()) && (sourceDirectory == null || sourceDirectory.isEmpty())
//				|| (targetPath == null || targetPath.isEmpty()) || errorCode == 1){
//			result = false;
//		}
//		getButton(IDialogConstants.OK_ID).setEnabled(result);
//		return result;
//	}


}
