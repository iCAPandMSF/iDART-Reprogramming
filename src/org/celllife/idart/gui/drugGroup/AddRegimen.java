/*
 * iDART: The Intelligent Dispensing of Antiretroviral Treatment
 * Copyright (C) 2006 Cell-Life
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License version
 * 2 for more details.
 * 
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */

package org.celllife.idart.gui.drugGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.manager.AdministrationManager;
import model.manager.DrugManager;

import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.hibernate.AtcCode;
import org.celllife.idart.database.hibernate.ChemicalCompound;
import org.celllife.idart.database.hibernate.ChemicalDrugStrength;
import org.celllife.idart.database.hibernate.Drug;
import org.celllife.idart.database.hibernate.Form;
import org.celllife.idart.database.hibernate.LinhaT;
import org.celllife.idart.database.hibernate.RegimeTerapeutico;
import org.celllife.idart.database.hibernate.RegimeTerapeuticoDrugs;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.drug.AddChemicalCompound;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.search.Search;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartColor;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.messages.Messages;
import org.celllife.idart.misc.iDARTUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.jfree.util.Log;

/**
 */
public class AddRegimen extends GenericFormGui {

	private static final String ID = "id";

	private Button btnSearch;

	private Button rdBtnAdult;

	private Button rdBtnChild;
	
	private Button rdBtnSideTreatment2;

	private Button rdBtnARV2;

	private Text txtName;

	boolean isAddnotUpdate;

	private RegimeTerapeutico localRegime;

	private Combo cmbForm;

	private Table tblChemicalCompounds;

	private Button btnAddChemical;

	private TableEditor editor;

	private Label lblTablets;

	private Label lblTake;

	private Label lblPackDescription;

	private Group grpChemicalCompounds;

	private Group grpDrugInfo;

	private Button btnEditChemical;

	/**
	 * Use true if you want to add a new drug, use false if you are updating an
	 * existing drug
	 * 
	 * @param parent
	 *            Shell
	 */
	public AddRegimen(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes newDrug
	 */
	@Override
	protected void createShell() {
		isAddnotUpdate = (Boolean) getInitialisationOption(OPTION_isAddNotUpdate);
		// The GenericFormsGui class needs
		// Header text, icon URL, shell bounds
		String shellTxt = isAddnotUpdate ? "Add a New Drug"
				: "Update an Existing Drug";
		Rectangle bounds = new Rectangle(25, 0, 800, 600);
		// Parent Generic Methods ------
		buildShell(shellTxt, bounds); // generic shell build
	}

	@Override
	protected void createContents() {
		createCompDrugInfo();
		createGrpChemicalCompounds();

		if (isAddnotUpdate) {
			enableFields(true);
			txtName.setFocus();
		} else {
			enableFields(false);
			btnSearch.setFocus();
		}
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = (isAddnotUpdate ? "Add a New Drug"
				: "Update an Existing Drug");
		iDartImage icoImage = iDartImage.PRESCRIPTIONADDDRUG;
		buildCompHeader(headerTxt, icoImage);
	}

	@Override
	protected void createCompButtons() {
		// Parent Class generic call
		buildCompButtons();
	}

	/**
	 * This method initializes compDrugInfo
	 * 
	 */
	private void createCompDrugInfo() {

		// compDrugInfo
		grpDrugInfo = new Group(getShell(), SWT.NONE);
		grpDrugInfo.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpDrugInfo.setText(Messages.getString("addRegimen.screen.title"));
		grpDrugInfo.setBounds(new Rectangle(18, 110, 483, 293));
		GridLayout layout = new GridLayout(3, false);
		layout.verticalSpacing = 10;
		grpDrugInfo.setLayout(layout);

		Label lblDrugSearch = new Label(grpDrugInfo, SWT.NONE);
		lblDrugSearch.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));

		if (isAddnotUpdate) {
			lblDrugSearch.setText("");
		} else {
			lblDrugSearch.setText(Messages.getString("addRegimen.search.message"));
		}
		lblDrugSearch.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// btnSearch
		btnSearch = new Button(grpDrugInfo, SWT.NONE);
		btnSearch.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 2,1));
		btnSearch.setText("Regimen Search");
		btnSearch.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnSearch.setVisible(!isAddnotUpdate);
		btnSearch
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdSearchWidgetSelected();
			}
		});
		btnSearch
		.setToolTipText(Messages.getString("addRegimen.search.message.tooltip"));
		
		// lblName & txtName
		Label lblName = new Label(grpDrugInfo, SWT.NONE);
		lblName.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblName.setText(Messages.getString("addRegimen.field.name"));
		lblName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		txtName = new Text(grpDrugInfo, SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 2,1));
		txtName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// lblFormLanguage1 & txtFormLanguage1
		Label lblFormLanguage1 = new Label(grpDrugInfo, SWT.NONE);
		lblFormLanguage1.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblFormLanguage1.setText(Messages.getString("addRegimen.field.line"));
		lblFormLanguage1.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		cmbForm = new Combo(grpDrugInfo, SWT.BORDER);
		cmbForm.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 2,1));
		CommonObjects.populateLines(getHSession(), cmbForm);
		cmbForm.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		cmbForm.setText("");
		cmbForm.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
		cmbForm.setVisibleItemCount(cmbForm.getItemCount());

		cmbForm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				String theLineStr = cmbForm.getText();
				if (!"".equalsIgnoreCase(theLineStr)) {
					LinhaT line = AdministrationManager.getLinha(getHSession(),
							theLineStr);

				}

			}
		});
		cmbForm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String theText = cmbForm.getText();
				if (theText.length() > 2) {
					String s = theText.substring(0, 1);
					String t = theText.substring(1, theText.length());
					theText = s.toUpperCase() + t;
					String[] items = cmbForm.getItems();
					for (int i = 0; i < items.length; i++) {
						if (items[i].length() > 2
								&& items[i].substring(0, 3).equalsIgnoreCase(
										theText)) {
							cmbForm.setText(items[i]);
							cmbForm.setFocus();
						}
					}
				}
			}
		});


		// Account Status
		Label lblAdultChild = new Label(grpDrugInfo, SWT.NONE);
		lblAdultChild.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblAdultChild.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblAdultChild.setText(Messages.getString("addRegimen.field.adult.or.child"));

		rdBtnAdult = new Button(grpDrugInfo, SWT.RADIO);
		rdBtnAdult.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1,1));
		rdBtnAdult.setLayoutData(new GridData(110, 20));
		rdBtnAdult.setText(Messages.getString("addRegimen.field.adult"));
		rdBtnAdult.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		rdBtnAdult.setSelection(true);

		rdBtnChild = new Button(grpDrugInfo, SWT.RADIO);
		rdBtnChild.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		rdBtnChild.setText(Messages.getString("addRegimen.field.child"));
		rdBtnChild.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		rdBtnChild.setSelection(false);
		
		grpDrugInfo.layout();
	}

	/**
	 * This method initializes compButtons
	 * 
	 */

	@Override
	protected void cmdSaveWidgetSelected() {

		if (fieldsOk()) {
			MessageBox mSave = new MessageBox(getShell(), SWT.ICON_QUESTION
					| SWT.YES | SWT.NO);
			mSave.setText(isAddnotUpdate ? Messages.getString("addRegimen.question.add") : Messages.getString("addRegimen.question.update"));
			mSave
			.setMessage(isAddnotUpdate ? Messages.getString("addRegimen.question.add.confirm")
					: Messages.getString("addRegimen.question.update.confirm"));

			switch (mSave.open()) {

			case SWT.YES:

				Transaction tx = null;
				String action = "";
				try {
					tx = getHSession().beginTransaction();
					if (isAddnotUpdate) {
						localRegime = new RegimeTerapeutico();
						setLocalRegime();
						AdministrationManager.saveRegime(getHSession(), localRegime);
						action = "added";
					} else {
						setLocalRegime();
						action = "updated";
					}

					tx.commit();
					getHSession().flush();

					// Updating the drug list after being saved.
					MessageBox m = new MessageBox(getShell(),
							SWT.ICON_INFORMATION | SWT.OK);
					m.setMessage("Regimen '".concat(localRegime.getRegimeesquema()).concat(
							"' has been " + action + "."));
					m.setText("Database Updated");
					m.open();

				} catch (HibernateException he) {
					MessageBox m = new MessageBox(getShell(), SWT.OK
							| SWT.ICON_INFORMATION);
					m.setText("Problems Saving to the Database");
					m
					.setMessage("There was a problem saving the drug's information to the database. Please try again.");
					m.open();
					if (tx != null) {
						tx.rollback();
					}
					getLog().error(he);
				}
				cmdCancelWidgetSelected(); // go back to previous screen
				break;
			case SWT.NO:
				// do nothing
			}

		}
	}

	/**
	 * Clears the form
	 */
	@Override
	public void clearForm() {

		try {

			txtName.setText("");
			cmbForm.setText("");
			lblPackDescription.setText("");
			btnSearch.setEnabled(true);

			localRegime = null;
			enableFields(isAddnotUpdate);

			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				tblChemicalCompounds.getItem(i).setText(1, "");
				tblChemicalCompounds.getItem(i).setChecked(false);
			}

			Control old = editor.getEditor();
			if (old != null) {
				old.dispose();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void cmdCancelWidgetSelected() {
		cmdCloseSelected();
	}

	private void cmdAddChemicalWidgetSelected(final ChemicalCompound cc) {

		final AddChemicalCompound ac = new AddChemicalCompound(getShell(), getHSession(), cc);
		ac.getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {

				if (AddChemicalCompound.compoundAdded != null) {
					ChemicalCompound ncc = DrugManager
					.getChemicalCompoundByName(getHSession(),
							AddChemicalCompound.compoundAdded.getName());

					if (ncc != null) {
						if (cc == null){
							// new cc
							TableItem ti = new TableItem(tblChemicalCompounds,
									SWT.NONE);
		
							populateTableItem(ncc, ti);
						} else {
							// edit cc
							TableItem[] items = tblChemicalCompounds.getItems();
							for (TableItem ti : items) {
								if (ti.getData(ID).equals(ncc.getId())){
									// populate table
									populateTableItem(ncc, ti);
								}
							}
						}
					}
				}
			}
		});
	}

	private void cmdSearchWidgetSelected() {

		Search regimenSearch = new Search(getHSession(), getShell(),
				CommonObjects.REGIME);

		if (regimenSearch.getValueSelected() != null) {

			localRegime = AdministrationManager.getRegime(getHSession(), Integer.parseInt(regimenSearch
					.getValueSelected()[0]));
			loadDrugDetails();
			btnSearch.setEnabled(false);
			// txtBarcode.setEditable(false);
			enableFields(true);
			txtName.setFocus();

		} else {
			btnSearch.setEnabled(true);
		}

	}

	@Override
	protected void cmdClearWidgetSelected() {
		clearForm();
	}

	private void loadDrugDetails() {

		txtName.setText(localRegime.getRegimeesquema());
		cmbForm.setText(localRegime.getLinhaT().getLinhanome());

		btnSearch.setEnabled(false);

		if (localRegime.isAdult()) {
			rdBtnAdult.setSelection(true);
			rdBtnChild.setSelection(false);
		}

		else {
			rdBtnChild.setSelection(true);
			rdBtnAdult.setSelection(false);
		}

		Iterator<RegimeTerapeuticoDrugs> chemicalDrugStrengthIt = localRegime.getRegimeDrugs().iterator();

		while (chemicalDrugStrengthIt.hasNext()) {
			RegimeTerapeuticoDrugs cds = chemicalDrugStrengthIt.next();
			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				if (((ChemicalCompound) tblChemicalCompounds.getItem(i)
						.getData()).getId() == cds.getChemicalCompound()
						.getId()) {
					tblChemicalCompounds.getItem(i).setChecked(true);
				}

			}

		}
		
		grpDrugInfo.layout();

	}

	/**
	 * Check if the necessary field names are filled in. Returns true if there
	 * are fields missing
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean fieldsOk() {

		// checking all simple fields

		if (txtName.getText().equals("")) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage("Regimen Name cannot be blank");
			b.setText("Missing Information");
			b.open();
			txtName.setFocus();
			return false;
		}

		if (cmbForm.indexOf(cmbForm.getText()) == -1) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b
			.setMessage("The line of the regimen must be from the list provided.");
			b.setText("Incorrect Information");
			b.open();
			cmbForm.setFocus();
			return false;
		}

		// end of checking all simple fields

		Set<RegimeTerapeuticoDrugs> chemicalDrugStrengthList = new HashSet<RegimeTerapeuticoDrugs>();

		// go through and check the chemical compounds table
		for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
			TableItem ti = tblChemicalCompounds.getItem(i);

			// there should be a strength for each checked chemical compound
			if (ti.getChecked()) {
				// create a ChemicalCompound object from the TableItem
				// information
				String currentChemComString = ti.getText(0);

				// get the acronym and name
				int endOfAcronym = currentChemComString.indexOf("]") + 1;
				String acronym = currentChemComString
				.substring(0, endOfAcronym).trim();
				String name = currentChemComString.substring(endOfAcronym + 1,
						currentChemComString.length()).trim();

				// create the chemicalCompound using name and acronym
				ChemicalCompound currentChemicalCompound = new ChemicalCompound(
						name, acronym);

				

				// get the drug
				if (localRegime == null) {
					localRegime = new RegimeTerapeutico();
				}

				// create ChemicalDrugStrength which combines
				// chemicalCompound
				// and strength information
				RegimeTerapeuticoDrugs regimeTerapeuticoDrug = new RegimeTerapeuticoDrugs(localRegime, currentChemicalCompound  );

				// add to the set of chemical compounds for this drug
				chemicalDrugStrengthList.add(regimeTerapeuticoDrug);

			}
		}// end of for loop

		if (localRegime != null) {
			setLocalRegime();
		}
		// all ARV drugs must have a chemical compound and must not have the
		// same chemical compounds and strengths as an existing drug
		if (rdBtnChild.getSelection()) {

			if (localRegime == null) {
				localRegime = new RegimeTerapeutico();
			}

			setLocalRegime();

			if (chemicalDrugStrengthList.isEmpty()) {
				MessageBox m = new MessageBox(getShell(), SWT.ICON_INFORMATION
						| SWT.OK);
				m.setText("No Chemical Compound");
				m
				.setMessage("All Regimens must have a chemical compound. \nPlease add a chemical for "
						+ localRegime.getRegimeesquema());
				m.open();

				return false;
			} else {
				/*// check that there are no other drugs already with the same
				// chemical composition
				String chemicalDrugMatch = DrugManager
				.existsChemicalComposition(getHSession(),
						chemicalDrugStrengthList, localDrug.getName());
				
				boolean flag = DrugManager
				.formChemicalComposition(getHSession(),
						chemicalDrugStrengthList, localDrug.getName(), localDrug.getForm().getForm());
				
				if (chemicalDrugMatch != null) {
					if(flag){
						MessageBox m = new MessageBox(getShell(),
								SWT.ICON_INFORMATION | SWT.OK);
						m.setText("Duplicate ARV Drug");
						m
						.setMessage("The drug you are trying to add has the same chemical composition as drug '"
								+ chemicalDrugMatch +" Form ("+localDrug.getForm().getForm()+")"
								+ "' which is already in the database.\n\nIf this is the same drug, but a different manufacturer, you will capture this information when you receive the stock using 'Stock Arrives at the Pharmacy' screen.");
						m.open();
						return false;
					}
					
				}*/
			}
		}
		return true;
	}

	private void setLocalRegime() {

		try {

			localRegime.setRegimeesquema(txtName.getText());

			localRegime.setLinhaT(AdministrationManager.getLinha(getHSession(),
					cmbForm.getText()));


			if (rdBtnAdult.getSelection()) {
				localRegime.setAdult(true);
			}

			else {
				localRegime.setAdult(false);
			}

			if (localRegime.getRegimeDrugs() == null) {
				localRegime
				.setRegimeDrugs(new TreeSet<RegimeTerapeuticoDrugs>());
			}
			localRegime.getRegimeDrugs().clear();

			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				TableItem ti = tblChemicalCompounds.getItem(i);
				if (ti.getChecked()) {

					Log.debug("found checked chem: " + ti);
					localRegime.getRegimeDrugs().add(
							new RegimeTerapeuticoDrugs(localRegime,(ChemicalCompound) ti
									.getData()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method enableFields.
	 * 
	 * @param enable
	 *            boolean
	 */
	@Override
	protected void enableFields(boolean enable) {

		txtName.setEnabled(enable);
		cmbForm.setEnabled(enable);
		rdBtnAdult.setEnabled(enable);
		rdBtnChild.setEnabled(enable);
		btnSave.setEnabled(enable);
		grpChemicalCompounds.setEnabled(enable);
		tblChemicalCompounds.setEnabled(enable);
		btnAddChemical.setEnabled(enable);
		btnEditChemical.setEnabled(enable);

		if (enable) {
			cmbForm.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
		} else {
			cmbForm.setBackground(ResourceUtils
					.getColor(iDartColor.WIDGET_BACKGROUND));
		}

	}

	/**
	 * This method initializes grpChemicalCcompounds
	 * 
	 */
	private void createGrpChemicalCompounds() {
		grpChemicalCompounds = new Group(getShell(), SWT.NONE);
		grpChemicalCompounds.setText("Chemical Composition ");
		grpChemicalCompounds.setBounds(new Rectangle(524, 110, 235, 372));
		grpChemicalCompounds.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		
		tblChemicalCompounds = new Table(grpChemicalCompounds, SWT.CHECK
				| SWT.FULL_SELECTION | SWT.BORDER);
		tblChemicalCompounds.setHeaderVisible(true);
		tblChemicalCompounds.setLinesVisible(true);
		tblChemicalCompounds.setBounds(new Rectangle(12, 20, 213, 301));
		tblChemicalCompounds.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));

//		Label lblAddChemical = new Label(grpChemicalCompounds, SWT.NONE);
//		lblAddChemical.setBounds(new Rectangle(14, 334, 30, 26));
//		lblAddChemical.setText("");
//		lblAddChemical.setImage(ResourceUtils.getImage(iDartImage.DRUG_30X26));
		
		btnAddChemical = new Button(grpChemicalCompounds, SWT.NONE);
		btnAddChemical.setBounds(new Rectangle(14, 332, 100, 30));
		btnAddChemical.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnAddChemical.setText("Add Compound");
		btnAddChemical
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdAddChemicalWidgetSelected(null);
			}
		});
		
		btnEditChemical = new Button(grpChemicalCompounds, SWT.NONE);
		btnEditChemical.setBounds(new Rectangle(120, 332, 100, 30));
		btnEditChemical.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnEditChemical.setText("Edit Compound");
		btnEditChemical.setEnabled(false);
		btnEditChemical
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				TableItem[] selection = tblChemicalCompounds.getSelection();
				if (selection.length > 0){
					ChemicalCompound cc = (ChemicalCompound) selection[0].getData();
					cmdAddChemicalWidgetSelected(cc);
				}
			}
		});

		TableColumn tblColChemicalCompounds = new TableColumn(
				tblChemicalCompounds, SWT.NONE);
		tblColChemicalCompounds.setWidth(140);
		tblColChemicalCompounds.setText("Chemical");

		TableColumn tblClmStrength = new TableColumn(tblChemicalCompounds,
				SWT.NONE);
		tblClmStrength.setWidth(72);
		tblClmStrength.setText("Strength");
		populateTblChemicalCompounds();

		editor = new TableEditor(tblChemicalCompounds);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		tblChemicalCompounds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				// Dispose any existing editor
				Control old = editor.getEditor();
				if (old != null) {
					old.dispose();
				}

				// Determine where the mouse was clicked
				Point pt = new Point(event.x, event.y);

				// Determine which row was selected
				final TableItem item = tblChemicalCompounds.getItem(pt);
				if (item != null) {
					// Determine which column was selected
					int column = -1;
					for (int i = 0, n = tblChemicalCompounds.getColumnCount(); i < n; i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							// This is the selected column
							column = i;
							break;
						}
					}

					if (column == 1) {
						// Create the Text object for our editor

						final Text text = new Text(tblChemicalCompounds,
								SWT.NONE);
						text.setForeground(item.getForeground());
						text.setBackground(ResourceUtils
								.getColor(iDartColor.GRAY));
						text.setFont(ResourceUtils
								.getFont(iDartFont.VERASANS_8));
						text.setText(item.getText(column));
						text.setForeground(item.getForeground());
						text.selectAll();
						text.setFocus();

						editor.minimumWidth = text.getBounds().width;

						// Set the control into the editor
						editor.setEditor(text, item, column);

						final int col = column;
						text.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent event1) {

								item.setText(col, text.getText());

								// if you've set a strength, check the column
								if (!text.getText().trim().equals("")) {
									item.setChecked(true);
								}

								else if (item.getChecked()) {
									item.setChecked(false);
								}
							}
						});
					}
				}
			}
		});

		// if the user unchecks (or checks) a colum, clear the current contents
		// of the strength field
		tblChemicalCompounds.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {

				if (event.detail == SWT.CHECK) {
					TableItem ti = (TableItem) event.item;
					ti.setText(1, "");

				}
			}
		});

	}

	private void populateTblChemicalCompounds() {
		List<ChemicalCompound> chemicalCompoundList = new ArrayList<ChemicalCompound>();

		chemicalCompoundList = DrugManager
		.getAllChemicalCompounds(getHSession());

		Iterator<ChemicalCompound> chemicalCompoundIt = chemicalCompoundList
		.iterator();

		while (chemicalCompoundIt.hasNext()) {
			ChemicalCompound cc = chemicalCompoundIt.next();
			TableItem ti = new TableItem(tblChemicalCompounds, SWT.NONE);

			// populate table
			populateTableItem(cc, ti);
		}
	}

	/**
	 * @param cc
	 * @param ti
	 */
	private void populateTableItem(ChemicalCompound cc, TableItem ti) {
		ti.setText(0, "[" + cc.getAcronym() + "] " + cc.getName());
		ti.setData(cc);
		ti.setData(ID, cc.getId());
	}

	/**
	 * Method submitForm.
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean submitForm() {
		return false;
	}

	@Override
	protected void setLogger() {
		Logger log = Logger.getLogger(this.getClass());
		setLog(log);
	}

}
