package org.celllife.idart.gui.drug;

import java.text.MessageFormat;

import model.manager.AdministrationManager;

import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.hibernate.LinhaT;
import org.celllife.idart.database.hibernate.Sector;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.misc.iDARTChangeListener;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.search.Search;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.messages.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddSector extends GenericFormGui {
	
	private Button btnSearch;

	private Text txtSectorId;

	private Text txtSectorName;

	private Sector localSector;

	private Group grpSectorInfo;





	private boolean isAddnotUpdate;

	private iDARTChangeListener changeListener;

	public AddSector(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
		if (!isAddnotUpdate) {
			enableFields(false);
			txtSectorId.setEnabled(false);
			txtSectorName.setEnabled(false);
		}
	}

	@Override
	protected void clearForm() {
		txtSectorId.setText(EMPTY); 
		txtSectorName.setText(EMPTY); 

		localSector = null;

		enableFields(isAddnotUpdate);

	}

	@Override
	protected boolean submitForm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean fieldsOk() {
		boolean fieldsOkay = true;

		if (txtSectorId.getText().equals(EMPTY)) { //$NON-NLS-1$
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(Messages.getString("addSector.id.empty")); //$NON-NLS-1$
			b.setText(Messages.getString("adddoctor.missingfields")); //$NON-NLS-1$
			b.open();
			txtSectorId.setFocus();
			fieldsOkay = false;
		}

		else if (txtSectorName.getText().equals(EMPTY)) { //$NON-NLS-1$
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(Messages.getString("addSector.name.empty")); //$NON-NLS-1$
			b.setText(Messages.getString("adddoctor.missingfields")); //$NON-NLS-1$
			b.open();
			txtSectorName.setFocus();
			fieldsOkay = false;

		}
		return fieldsOkay;
	}

	@Override
	protected void createCompHeader() {
		String headerTxt = (isAddnotUpdate ? Messages.getString("addSector.screen.title.new") //$NON-NLS-1$
				: Messages.getString("addSector.screen.title.update")); //$NON-NLS-1$
		iDartImage icoImage = iDartImage.DOCTOR;
		// Parent class generic call
		buildCompHeader(headerTxt, icoImage);

	}

	@Override
	protected void createCompButtons() {
		// Parent Class generic call
				buildCompButtons();

	}

	@Override
	protected void cmdSaveWidgetSelected() {
		if (fieldsOk()) {

			Transaction tx = null;
			String action = ""; //$NON-NLS-1$

			try {
				tx = getHSession().beginTransaction();
				// this is a new doctor
				if (localSector == null && isAddnotUpdate) {

					localSector = new Sector(Integer.parseInt(txtSectorId.getText()),txtSectorName.getText());
				//	System.out.println(Integer.parseInt(txtSectorId.getText()));
					action = Messages.getString("addLine.action"); //$NON-NLS-1$
					AdministrationManager.saveSector(getHSession(), localSector);
					
				

				}

				// else, we're updating an existing doctor
				else if (localSector != null && !isAddnotUpdate) {
					localSector.setSectorid(Integer.parseInt(txtSectorId.getText()));
					localSector.setSectorname(txtSectorName.getText());
					//localSector.setActive(rdBtnActive.getSelection() ? true: false);
					action = Messages.getString("addSector.updated"); //$NON-NLS-1$
				}

				getHSession().flush();
				tx.commit();
				String message = MessageFormat.format(Messages.getString("addSector.message"), localSector.getSectorname(),action); //$NON-NLS-1$
				showMessage(MessageDialog.INFORMATION, Messages.getString("addSector.messageupdate"), message);//$NON-NLS-1$ 
				fireChangeEvent(localSector);
				cmdCancelWidgetSelected();
			} catch (HibernateException he) {
				getLog().error(Messages.getString("addSector.errordb"), he); //$NON-NLS-1$
				showMessage(MessageDialog.ERROR, Messages.getString("addSector.errordb"), //$NON-NLS-1$ 
						Messages.getString("addSector.errordb.saving"));//$NON-NLS-1$
				if (tx != null) {
					tx.rollback();
				}
			}

		}

	}

	/**
	 * clears the current form
	 */

	

	@Override
	protected void cmdClearWidgetSelected() {
		clearForm();
		btnSearch.setEnabled(true);

	}

	@Override
	protected void cmdCancelWidgetSelected() {
		cmdCloseSelected();
		changeListener = null;

	}

	@Override
	protected void enableFields(boolean enable) {
		txtSectorName.setEnabled(enable);
		txtSectorId.setEnabled(enable);
		btnSave.setEnabled(enable);

	}

	@Override
	protected void createContents() {
		// grpDoctorInfo
				grpSectorInfo = new Group(getShell(), SWT.NONE);
				grpSectorInfo.setBounds(new Rectangle(60, 90, 480, 240));

				// lblDoctor & txtDoctor
				Label lblSectorId = new Label(grpSectorInfo, SWT.NONE);
				lblSectorId.setBounds(new org.eclipse.swt.graphics.Rectangle(30,
						50, 130, 20));
				lblSectorId.setText(Messages.getString("addLine.label.id")); //$NON-NLS-1$
				lblSectorId.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

				txtSectorId = new Text(grpSectorInfo, SWT.BORDER);
				txtSectorId.setBounds(new org.eclipse.swt.graphics.Rectangle(170,
						50, 170, 20));
				txtSectorId.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
				txtSectorId.setFocus();

				// btnSearch
				btnSearch = new Button(grpSectorInfo, SWT.NONE);
				btnSearch.setBounds(new org.eclipse.swt.graphics.Rectangle(350, 48,
						105, 30));
				btnSearch.setText(Messages.getString("addSector.button.search.title")); //$NON-NLS-1$
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

				// lblDoctorFirstname & txtDoctorFirstname
				Label lblSectorName = new Label(grpSectorInfo, SWT.NONE);
				lblSectorName.setBounds(new org.eclipse.swt.graphics.Rectangle(30,
						80, 130, 20));
				lblSectorName.setText(Messages.getString("addSector.label.name.title")); //$NON-NLS-1$
				lblSectorName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

				txtSectorName = new Text(grpSectorInfo, SWT.BORDER);
				txtSectorName.setBounds(new org.eclipse.swt.graphics.Rectangle(
						170, 80, 170, 20));
				txtSectorName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

				
			


				getShell().setDefaultButton(btnSave);


	}
	
	private void cmdSearchWidgetSelected() {

		Search SectorSearch = new Search(getHSession(), getShell(),
				CommonObjects.SECTOR, true);

		if (SectorSearch.getValueSelected() != null) {

			localSector = AdministrationManager.getSector(getHSession(),
					SectorSearch.getValueSelected()[1]);

			if (loadSectorDetails()) {
				enableFields(true);
			}

			else {
				showMessage(MessageDialog.ERROR, Messages.getString("adddoctor.db.error"), //$NON-NLS-1$
						Messages.getString("adddoctor.db.doctorinfo"));//$NON-NLS-1$
			}
		}
	}
	
	private boolean loadSectorDetails() {

		boolean loadSuccessful = false;

		try {
			txtSectorId.setText(localSector.getSectorid()+"" == null ? EMPTY : localSector.getSectorid()+""); //$NON-NLS-1$
			txtSectorName.setText(localSector.getSectorname() == null ? EMPTY : localSector.getSectorname()); //$NON-NLS-1$
			txtSectorId.setEditable(true);
			txtSectorName.setEditable(true);
			
			loadSuccessful = true;
		} catch (Exception e) {
			loadSuccessful = false;
		}

		return loadSuccessful;

	}


	@Override
	protected void setLogger() {
		Logger log = Logger.getLogger(this.getClass());
		setLog(log);

	}
	/**
	 * Method addChangeListener.
	 * 
	 * @param listener
	 *            iDARTChangeListener
	 */
	public void addChangeListener(iDARTChangeListener listener) {
		this.changeListener = listener;
	}
	
	/**
	 * Method fireChangeEvent.
	 * 
	 * @param o
	 *            Object
	 */
	private void fireChangeEvent(Object o) {
		if (changeListener != null) {
			changeListener.changed(o);
		}
	}

	@Override
	protected void createShell() {
		isAddnotUpdate = ((Boolean) getInitialisationOption(OPTION_isAddNotUpdate))
				.booleanValue();
				String shellTxt = isAddnotUpdate ? Messages.getString("addSector.screen.title.new") //$NON-NLS-1$
						: Messages.getString("addSector.screen.title.update"); //$NON-NLS-1$
				Rectangle bounds = new Rectangle(100, 100, 600, 440);
				buildShell(shellTxt, bounds);

	}

}
