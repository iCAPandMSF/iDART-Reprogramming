package org.celllife.idart.gui.drug;

import java.text.MessageFormat;

import model.manager.AdministrationManager;

import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.hibernate.LinhaT;
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
import org.hibernate.Transaction;

public class AddLine extends GenericFormGui {

	private Button btnSearch;

	private Text txtLinhaId;

	private Text txtLineName;

	private LinhaT localLinhaT;

	private Group grpLinhaInfo;

	private Composite compStatus;

	private Label lblStatus;

	private Button rdBtnActive;

	private Button rdBtnInactive;

	private boolean isAddnotUpdate;

	private iDARTChangeListener changeListener;

	/**
	 * Use true if you want to add a new doctor, use false if you are updating
	 * an existing doctor
	 * @param parent Shell
	 */
	public AddLine(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
		if (!isAddnotUpdate) {
			enableFields(false);
			txtLinhaId.setEnabled(false);
			txtLineName.setEnabled(false);
		}
	}

	/**
	 * This method initializes newDoctor
	 */
	@Override
	protected void createShell() {
		isAddnotUpdate = ((Boolean) getInitialisationOption(OPTION_isAddNotUpdate))
		.booleanValue();
		String shellTxt = isAddnotUpdate ? Messages.getString("addLine.screen.title.new") //$NON-NLS-1$
				: Messages.getString("adddoctor.screen.title.update"); //$NON-NLS-1$
		Rectangle bounds = new Rectangle(100, 100, 600, 440);
		buildShell(shellTxt, bounds);
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = (isAddnotUpdate ? Messages.getString("addLine.screen.title.new") //$NON-NLS-1$
				: Messages.getString("addLine.screen.title.update")); //$NON-NLS-1$
		iDartImage icoImage = iDartImage.DOCTOR;
		// Parent class generic call
		buildCompHeader(headerTxt, icoImage);
	}

	/**
	 * This method initializes compButtons
	 * 
	 */
	@Override
	protected void createCompButtons() {
		// Parent Class generic call
		buildCompButtons();
	}

	/**
	 * This method initializes compDoctorInfo
	 * 
	 */
	@Override
	protected void createContents() {

		// grpDoctorInfo
		grpLinhaInfo = new Group(getShell(), SWT.NONE);
		grpLinhaInfo.setBounds(new Rectangle(60, 90, 480, 240));

		// lblDoctor & txtDoctor
		Label lblLinhaId = new Label(grpLinhaInfo, SWT.NONE);
		lblLinhaId.setBounds(new org.eclipse.swt.graphics.Rectangle(30,
				50, 130, 20));
		lblLinhaId.setText(Messages.getString("addLine.label.id")); //$NON-NLS-1$
		lblLinhaId.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		txtLinhaId = new Text(grpLinhaInfo, SWT.BORDER);
		txtLinhaId.setBounds(new org.eclipse.swt.graphics.Rectangle(170,
				50, 170, 20));
		txtLinhaId.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		txtLinhaId.setFocus();

		// btnSearch
		btnSearch = new Button(grpLinhaInfo, SWT.NONE);
		btnSearch.setBounds(new org.eclipse.swt.graphics.Rectangle(350, 48,
				105, 30));
		btnSearch.setText(Messages.getString("addLine.button.search.title")); //$NON-NLS-1$
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
		Label lblLineName = new Label(grpLinhaInfo, SWT.NONE);
		lblLineName.setBounds(new org.eclipse.swt.graphics.Rectangle(30,
				80, 130, 20));
		lblLineName.setText(Messages.getString("addLine.label.name.title")); //$NON-NLS-1$
		lblLineName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		txtLineName = new Text(grpLinhaInfo, SWT.BORDER);
		txtLineName.setBounds(new org.eclipse.swt.graphics.Rectangle(
				170, 80, 170, 20));
		txtLineName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		lblStatus = new Label(grpLinhaInfo, SWT.NONE);
		lblStatus.setBounds(new org.eclipse.swt.graphics.Rectangle(30, 200,
				130, 20));
		lblStatus.setText(Messages.getString("addLine.label.status.title")); //$NON-NLS-1$
		lblStatus.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// compAccStatus
		compStatus = new Composite(grpLinhaInfo, SWT.NONE);
		compStatus.setBounds(new Rectangle(170, 200, 220, 20));

		rdBtnActive = new Button(compStatus, SWT.RADIO);
		rdBtnActive.setBounds(new org.eclipse.swt.graphics.Rectangle(0, 0, 80,
				20));
		rdBtnActive.setText(Messages.getString("addLine.buttonactive.title")); //$NON-NLS-1$
		rdBtnActive.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		rdBtnActive.setSelection(true);

		rdBtnInactive = new Button(compStatus, SWT.RADIO);
		rdBtnInactive.setBounds(new org.eclipse.swt.graphics.Rectangle(90, 0,
				80, 20));
		rdBtnInactive.setText(Messages.getString("addLine.buttoninactive.title")); //$NON-NLS-1$
		rdBtnInactive.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		rdBtnInactive.setSelection(false);

		if (isAddnotUpdate) {
			rdBtnInactive.setEnabled(false);
		}

		getShell().setDefaultButton(btnSave);

	}

	@Override
	protected void cmdSaveWidgetSelected() {

		if (fieldsOk()) {

			Transaction tx = null;
			String action = ""; //$NON-NLS-1$

			try {
				tx = getHSession().beginTransaction();
				// this is a new doctor
				if (localLinhaT == null && isAddnotUpdate) {

					localLinhaT = new LinhaT(Integer.parseInt(txtLinhaId.getText()),
							txtLineName.getText(), rdBtnActive.getSelection() ? true : false);
					action = Messages.getString("addLine.action"); //$NON-NLS-1$
					AdministrationManager.saveLinhaT(getHSession(), localLinhaT);
					
				

				}

				// else, we're updating an existing doctor
				else if (localLinhaT != null && !isAddnotUpdate) {
					localLinhaT.setLinhaid(Integer.parseInt(txtLinhaId.getText()));
					localLinhaT.setLinhanome(txtLineName.getText());
					localLinhaT.setActive(rdBtnActive.getSelection() ? true
							: false);
					action = Messages.getString("addLine.updated"); //$NON-NLS-1$
				}

				getHSession().flush();
				tx.commit();
				String message = MessageFormat.format(Messages.getString("addLine.message"), localLinhaT.getLinhanome(),action); //$NON-NLS-1$
				showMessage(MessageDialog.INFORMATION, Messages.getString("addLine.messageupdate"), message);//$NON-NLS-1$ 
				fireChangeEvent(localLinhaT);
				cmdCancelWidgetSelected();
			} catch (HibernateException he) {
				getLog().error(Messages.getString("addLine.errordb"), he); //$NON-NLS-1$
				showMessage(MessageDialog.ERROR, Messages.getString("addLine.errordb"), //$NON-NLS-1$ 
						Messages.getString("addLine.errordb.saving"));//$NON-NLS-1$
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
	public void clearForm() {

		txtLinhaId.setText(EMPTY); 
		txtLineName.setText(EMPTY); 
		rdBtnActive.setSelection(true);
		rdBtnInactive.setSelection(false);

		localLinhaT = null;

		enableFields(isAddnotUpdate);

	}

	@Override
	protected void cmdCancelWidgetSelected() {
		cmdCloseSelected();
		changeListener = null;
	}

	@Override
	protected void cmdClearWidgetSelected() {

		clearForm();
		btnSearch.setEnabled(true);

	}

	private void cmdSearchWidgetSelected() {

		Search LineSearch = new Search(getHSession(), getShell(),
				CommonObjects.LINE, true);

		if (LineSearch.getValueSelected() != null) {

			localLinhaT = AdministrationManager.getLinha(getHSession(),
					LineSearch.getValueSelected()[0]);

			if (loadDoctorsDetails()) {
				enableFields(true);
				btnSearch.setEnabled(false);
				txtLinhaId.setEnabled(false);
				txtLineName.setEnabled(false);
			}

			else {
				showMessage(MessageDialog.ERROR, Messages.getString("adddoctor.db.error"), //$NON-NLS-1$
						Messages.getString("adddoctor.db.doctorinfo"));//$NON-NLS-1$
			}
		}
	}

	/**
	 * Check if the necessary field names are filled in. Returns true if there
	 * are fields missing
	 * @return boolean
	 */
	@Override
	protected boolean fieldsOk() {

		boolean fieldsOkay = true;

		if (txtLinhaId.getText().equals(EMPTY)) { //$NON-NLS-1$
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(Messages.getString("adddoctor.surname.empty")); //$NON-NLS-1$
			b.setText(Messages.getString("adddoctor.missingfields")); //$NON-NLS-1$
			b.open();
			txtLinhaId.setFocus();
			fieldsOkay = false;
		}

		else if (txtLineName.getText().equals(EMPTY)) { //$NON-NLS-1$
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(Messages.getString("adddoctor.firstname.empty")); //$NON-NLS-1$
			b.setText(Messages.getString("adddoctor.missingfields")); //$NON-NLS-1$
			b.open();
			txtLineName.setFocus();
			fieldsOkay = false;

		}
		return fieldsOkay;
	}

	/**
	 * Method loadDoctorsDetails.
	 * @return boolean
	 */
	private boolean loadDoctorsDetails() {

		boolean loadSuccessful = false;

		try {
			txtLinhaId.setText(localLinhaT.getLinhaid()+"" == null ? EMPTY : localLinhaT.getLinhaid()+""); //$NON-NLS-1$
			txtLineName.setText(localLinhaT.getLinhanome() == null ? EMPTY : localLinhaT.getLinhanome()); //$NON-NLS-1$
			txtLinhaId.setEditable(false);
			txtLineName.setEditable(false);

			if (localLinhaT.isActive()) {
				rdBtnActive.setSelection(true);
				rdBtnInactive.setSelection(false);

			} else {
				rdBtnActive.setSelection(false);
				rdBtnInactive.setSelection(true);
			}
			loadSuccessful = true;
		} catch (Exception e) {
			loadSuccessful = false;
		}

		return loadSuccessful;

	}

	/**
	 * Method enableFields.
	 * @param enable boolean
	 */
	@Override
	protected void enableFields(boolean enable) {
		btnSave.setEnabled(enable);
	}

	/**
	 * Method submitForm.
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

}

