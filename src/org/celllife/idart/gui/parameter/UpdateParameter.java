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

package org.celllife.idart.gui.parameter;

import java.text.MessageFormat;

import javax.swing.JOptionPane;

import model.manager.ParameterManager;

import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.Parameter;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.misc.iDARTChangeListener;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.messages.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 */
public class UpdateParameter extends GenericFormGui {

	private Button btnSearch;

	private Text txtValue;

	private Label lblInstructions;

	private Parameter localParameter;

	private Group grpDoctorInfo;

	private iDARTChangeListener changeListener;

	/**
	 * Use true if you want to add a new doctor, use false if you are updating
	 * an existing doctor
	 * @param parent Shell
	 * @wbp.parser.entryPoint
	 */
	public UpdateParameter(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes newDoctor
	 */
	@Override
	protected void createShell() {
		String shellTxt = Messages.getString("addParameter.screen.title.update"); //$NON-NLS-1$
		Rectangle bounds = new Rectangle(100, 100, 600, 440);
		buildShell(shellTxt, bounds);
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = Messages.getString("addParameter.screen.title.update"); //$NON-NLS-1$
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
		grpDoctorInfo = new Group(getShell(), SWT.NONE);
		grpDoctorInfo.setBounds(new Rectangle(60, 90, 480, 240));

		lblInstructions = new Label(grpDoctorInfo, SWT.CENTER);
		lblInstructions.setBounds(new org.eclipse.swt.graphics.Rectangle(110,
				15, 260, 20));
		lblInstructions.setText(Messages.getString("common.label.compulsory")); //$NON-NLS-1$
		lblInstructions.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_10_ITALIC));

		// lblDoctor & txtDoctor
		Label lblDoctorSurname = new Label(grpDoctorInfo, SWT.NONE);
		lblDoctorSurname.setBounds(new org.eclipse.swt.graphics.Rectangle(30,
				50, 130, 20));
		lblDoctorSurname.setText(Messages.getString("addParameter.label.name")); //$NON-NLS-1$
		lblDoctorSurname.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		txtValue = new Text(grpDoctorInfo, SWT.BORDER);
		txtValue.setBounds(new org.eclipse.swt.graphics.Rectangle(170,
				50, 170, 20));
		txtValue.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		txtValue.setFocus();

		getShell().setDefaultButton(btnSave);
		loadParameters();

	}

	@Override
	protected void cmdSaveWidgetSelected() {

		if (fieldsOk()) {

			Transaction tx = null;
			String action = ""; //$NON-NLS-1$

			try {
				tx = getHSession().beginTransaction();
				
				Integer amcData= Integer.parseInt(txtValue.getText());
				if(amcData.intValue() <= 0 || amcData.intValue() >= 29)
				{
					JOptionPane.showMessageDialog(null, "Please select a number between 1 and 28");
					return ;
				}
				
				localParameter.setParameterValue(txtValue.getText());
				
				getHSession().flush();
				tx.commit();
				String message = MessageFormat.format(Messages.getString("adddoctor.message"), localParameter.getParameterValue(),action); //$NON-NLS-1$
				showMessage(MessageDialog.INFORMATION, Messages.getString("adddoctor.messageupdate"), message);//$NON-NLS-1$ 
				fireChangeEvent(localParameter);
				cmdCancelWidgetSelected();
			} catch (HibernateException he) {
				getLog().error(Messages.getString("adddoctor.errordb"), he); //$NON-NLS-1$
				showMessage(MessageDialog.ERROR, Messages.getString("adddoctor.errordb"), //$NON-NLS-1$ 
						Messages.getString("adddoctor.errordb.saving"));//$NON-NLS-1$
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

	}

	/**
	 * Check if the necessary field names are filled in. Returns true if there
	 * are fields missing
	 * @return boolean
	 */
	@Override
	protected boolean fieldsOk() {

		boolean fieldsOkay = true;

		if (txtValue.getText().equals(EMPTY)) { //$NON-NLS-1$
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(Messages.getString("adddoctor.surname.empty")); //$NON-NLS-1$
			b.setText(Messages.getString("adddoctor.missingfields")); //$NON-NLS-1$
			b.open();
			txtValue.setFocus();
			fieldsOkay = false;
		}
		return fieldsOkay;
	}

	/**
	 * Method loadDoctorsDetails.
	 * @return boolean
	 */
	private void loadParameters() {

		localParameter = ParameterManager.getAmc(getHSession());

		try {
			txtValue.setText(localParameter.getParameterValue() == null ? EMPTY : localParameter.getParameterValue()); //$NON-NLS-1$
		} catch (Exception e) {
		}
	}

	/**
	 * Method enableFields.
	 * @param enable boolean
	 */
	@Override
	protected void enableFields(boolean enable) {
		txtValue.setEnabled(enable);
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
