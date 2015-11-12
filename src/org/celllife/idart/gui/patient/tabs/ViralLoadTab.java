package org.celllife.idart.gui.patient.tabs;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.manager.PatientManager;

import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.Patient;
import org.celllife.idart.database.hibernate.PatientViralLoad;
import org.celllife.idart.gui.misc.GenericTab;
import org.celllife.idart.gui.patient.ShowPAVAS;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.widget.DateButton;
import org.celllife.idart.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.hibernate.Session;

public class ViralLoadTab extends GenericTab implements IPatientTab {

	protected DateButton btnTestResultDate;
	protected DateButton btnCounselingDate;
	private Session hSession;
	private final Logger log = Logger.getLogger(this.getClass());
	private TabFolder parent;
	private Button rdBtnNoHighViralLoad;
	private Button rdBtnHighViralLoad;
	private Button rdBtnNotRecommendCounseling;
	private Button rdBtnRecommendCounseling;
	private Button rdBtnNoBelongsGaac;
	private Button rdBtnBelongsGaac;
	private Text txtGaacNumber;
	private int style;
	private Patient localPatient;
	private PatientViralLoad lastViralLoad;

	private void addLastViralLoad(Patient patient) {
		if(changesMade(patient))
		{
			PatientViralLoad latestViralLoad = new PatientViralLoad();
			latestViralLoad.setPatient(patient);
			latestViralLoad.setHighViralLoad(rdBtnHighViralLoad.getSelection());
			latestViralLoad.setBelongsGaac(rdBtnBelongsGaac.getSelection());
			latestViralLoad.setRecommendedToCounselor(rdBtnRecommendCounseling.getSelection());
			latestViralLoad.setResultDate(btnTestResultDate.getDate()==null ? null : new java.sql.Date(btnTestResultDate.getDate().getTime()));
			latestViralLoad.setCounselingDate(btnCounselingDate.getDate()==null ? null : new java.sql.Date(btnCounselingDate.getDate().getTime()));
			latestViralLoad.setGaacNumber(Integer.parseInt((txtGaacNumber.getText().equals("") ? "0" : txtGaacNumber.getText())));
			patient.getPatientViralLoads().add(latestViralLoad);//se o patient não existe na base de dados há stress 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.patient.util.IPatientTab#changesMade(org.celllife
	 * .idart.database.hibernate.Patient)
	 */
	@Override
	public boolean changesMade(Patient patient) {
		boolean changesMade=false;
		if(lastViralLoad==null)
		{
			changesMade=rdBtnHighViralLoad.getSelection()||rdBtnBelongsGaac.getSelection()||rdBtnRecommendCounseling.getSelection();
		}
		else{
			Date date=btnCounselingDate.getDate();
			Date date2=lastViralLoad.getCounselingDate();
			changesMade=!(lastViralLoad.getHighViralLoad()==rdBtnHighViralLoad.getSelection() && 
					lastViralLoad.getBelongsGaac()==rdBtnBelongsGaac.getSelection() &&
					lastViralLoad.getRecommendedToCounselor()==rdBtnRecommendCounseling.getSelection() &&
					lastViralLoad.getGaacNumber().toString().equals(txtGaacNumber.getText()) &&
					(lastViralLoad.getCounselingDate()==btnCounselingDate.getDate() 
							|| lastViralLoad.getCounselingDate().equals(btnCounselingDate.getDate())) &&
					(lastViralLoad.getResultDate()==btnTestResultDate.getDate()  
							|| lastViralLoad.getResultDate().equals(btnTestResultDate.getDate())));
		}

		return changesMade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.celllife.idart.gui.patient.util.IPatientTab#clear()
	 */
	@Override
	public void clear() {
		rdBtnHighViralLoad.setSelection(false);
		rdBtnNoHighViralLoad.setSelection(true);
		btnTestResultDate.clearDate();
		btnTestResultDate.setText("Not applicable");
		
		rdBtnBelongsGaac.setSelection(false);
		rdBtnNoBelongsGaac.setSelection(true);
		txtGaacNumber.setText("");
		btnTestResultDate.setText("Not applicable");
		
		rdBtnRecommendCounseling.setSelection(false);
		rdBtnNotRecommendCounseling.setSelection(true);
		btnCounselingDate.clearDate();
		btnCounselingDate.setText("Not applicable");
	}

	@Override
	public void create() {
		this.tabItem = new TabItem(parent, style);
		tabItem.setText("  Viral load  ");
		createGrpClinicalInfo();
	}

	/**
	 * This method initializes grpPregnancy
	 */
	private void createGrpClinicalInfo() {

		Group grpViralLoad = new Group(tabItem.getParent(), SWT.NONE);
		grpViralLoad.setBounds(new Rectangle(3, 3, 750, 140));
		grpViralLoad.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		tabItem.setControl(grpViralLoad);

		// HighViralLoad?
		Label lblHighViralLoad = new Label(grpViralLoad, SWT.NONE);
		lblHighViralLoad.setBounds(new Rectangle(11, 25, 166, 20));
		lblHighViralLoad.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblHighViralLoad.setText(Messages
				.getString("patient.viralload.hashigh.viralload"));

		// ResultDate?
		Label lblResultDate = new Label(grpViralLoad, SWT.NONE);
		lblResultDate.setBounds(new Rectangle(11, 45, 166, 20));
		lblResultDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblResultDate.setText(Messages
				.getString("patient.viralload.has.resultdate"));

		// RecommendedToCounselor?
		Label lblRecommendedToCounselor = new Label(grpViralLoad, SWT.NONE);
		lblRecommendedToCounselor.setBounds(new Rectangle(11, 65, 166, 20));
		lblRecommendedToCounselor.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		lblRecommendedToCounselor.setText(Messages
				.getString("patient.viralload.is.recommendtocounselor"));

		// CounselingDate?
		Label lblCounselingDate = new Label(grpViralLoad, SWT.NONE);
		lblCounselingDate.setBounds(new Rectangle(11, 85, 166, 20));
		lblCounselingDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblCounselingDate.setText(Messages
				.getString("patient.viralload.has.counselingdate"));

		// BelongsGaac?
		Label lblBelongsGaac = new Label(grpViralLoad, SWT.NONE);
		lblBelongsGaac.setBounds(new Rectangle(11, 105, 166, 20));
		lblBelongsGaac.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblBelongsGaac.setText(Messages
				.getString("patient.viralload.belongs.gaac"));

		// GaacNumber?
		Label lblGaacNumber = new Label(grpViralLoad, SWT.NONE);
		lblGaacNumber.setBounds(new Rectangle(11, 125, 166, 20));
		lblGaacNumber.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblGaacNumber.setText(Messages
				.getString("patient.viralload.has.gaacnumber"));

		// compHighViralLoad
		Composite compHighViralLoad = new Composite(grpViralLoad, SWT.NONE);
		compHighViralLoad.setBounds(new Rectangle(230, 25, 200, 20));

		rdBtnHighViralLoad = new Button(compHighViralLoad, SWT.RADIO);
		rdBtnHighViralLoad.setBounds(new Rectangle(0, 0, 60, 20));
		rdBtnHighViralLoad.setText(Messages
				.getString("patient.viralload.radiobutton.yes"));
		rdBtnHighViralLoad.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		rdBtnHighViralLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				btnTestResultDate.setEnabled(true);
				if (btnTestResultDate.getDate() == null) {
					btnTestResultDate.setText("Unknown");
					btnCounselingDate.setEnabled(true);
					rdBtnRecommendCounseling.setEnabled(true);
					rdBtnNotRecommendCounseling.setEnabled(true);
				}
			}
		});
		rdBtnHighViralLoad.setSelection(false);

		rdBtnNoHighViralLoad = new Button(compHighViralLoad, SWT.RADIO);
		rdBtnNoHighViralLoad.setBounds(new Rectangle(65, 0, 60, 20));
		rdBtnNoHighViralLoad.setText(Messages
				.getString("patient.viralload.radiobutton.no"));
		rdBtnNoHighViralLoad.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		rdBtnNoHighViralLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnTestResultDate.setEnabled(false);
				btnTestResultDate.setText("Not applicable");
				btnCounselingDate.setEnabled(false);
				rdBtnRecommendCounseling.setEnabled(false);
				rdBtnRecommendCounseling.setSelection(false);
				rdBtnNotRecommendCounseling.setEnabled(false);
				rdBtnNotRecommendCounseling.setSelection(true);
			}
		});
		rdBtnNoHighViralLoad.setSelection(true);

		btnTestResultDate = new DateButton(grpViralLoad,
				DateButton.ZERO_TIMESTAMP, null);
		btnTestResultDate.setBounds(new Rectangle(200, 45, 200, 18));
		btnTestResultDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnTestResultDate.setText(" Not applicable ");
		btnTestResultDate.setEnabled(false);

		// compRecommendCounseling
		Composite compCounselingDate = new Composite(grpViralLoad, SWT.NONE);
		compCounselingDate.setBounds(new Rectangle(230, 65, 200, 20));

		rdBtnRecommendCounseling = new Button(compCounselingDate, SWT.RADIO);
		rdBtnRecommendCounseling.setBounds(new Rectangle(0, 0, 60, 20));
		rdBtnRecommendCounseling.setText(Messages
				.getString("patient.viralload.radiobutton.yes"));
		rdBtnRecommendCounseling.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		rdBtnRecommendCounseling.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				btnCounselingDate.setEnabled(true);
				if (btnCounselingDate.getDate() == null) {
					btnCounselingDate.setText("Unknown");
				}
			}
		});
		rdBtnHighViralLoad.setSelection(false);

		rdBtnNotRecommendCounseling = new Button(compCounselingDate, SWT.RADIO);
		rdBtnNotRecommendCounseling.setBounds(new Rectangle(65, 0, 60, 20));
		rdBtnNotRecommendCounseling.setText(Messages
				.getString("patient.viralload.radiobutton.no"));
		rdBtnNotRecommendCounseling.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		rdBtnNotRecommendCounseling
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						btnCounselingDate.setEnabled(false);
						btnCounselingDate.setText("Not applicable");
					}
				});
		rdBtnNotRecommendCounseling.setSelection(true);

		btnCounselingDate = new DateButton(grpViralLoad,
				DateButton.ZERO_TIMESTAMP, null);
		btnCounselingDate.setBounds(new Rectangle(200, 85, 200, 18));
		btnCounselingDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnCounselingDate.setText(" Not applicable ");
		btnCounselingDate.setEnabled(false);

		// compBelongsGaac
		Composite compBelongsGaac = new Composite(grpViralLoad, SWT.NONE);
		compBelongsGaac.setBounds(new Rectangle(230, 105, 200, 20));

		rdBtnBelongsGaac = new Button(compBelongsGaac, SWT.RADIO);
		rdBtnBelongsGaac.setBounds(new Rectangle(0, 0, 60, 20));
		rdBtnBelongsGaac.setText(Messages
				.getString("patient.viralload.radiobutton.yes"));
		rdBtnBelongsGaac.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		rdBtnBelongsGaac.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				txtGaacNumber.setEnabled(true);
				if (txtGaacNumber.getText() == null) {
					txtGaacNumber.setText("Unknown");
				}
			}
		});
		rdBtnBelongsGaac.setSelection(false);

		rdBtnNoBelongsGaac = new Button(compBelongsGaac, SWT.RADIO);
		rdBtnNoBelongsGaac.setBounds(new Rectangle(65, 0, 60, 20));
		rdBtnNoBelongsGaac.setText(Messages
				.getString("patient.viralload.radiobutton.no"));
		rdBtnNoBelongsGaac.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		rdBtnNoBelongsGaac
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						txtGaacNumber.setEnabled(false);
						txtGaacNumber.setText("Not applicable");
					}
				});
		rdBtnNoBelongsGaac.setSelection(true);
		
		txtGaacNumber = new Text(grpViralLoad, SWT.BORDER);
		txtGaacNumber.setBounds(new Rectangle(200, 125, 200, 18));
		txtGaacNumber.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		txtGaacNumber.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.celllife.idart.gui.patient.util.IPatientTab#enable(boolean,
	 * org.eclipse.swt.graphics.Color)
	 */
	@Override
	public void enable(boolean enable, Color color) {
		rdBtnHighViralLoad.setEnabled(enable);
		rdBtnNoHighViralLoad.setEnabled(enable);
		rdBtnRecommendCounseling.setEnabled(enable);
		rdBtnNotRecommendCounseling.setEnabled(enable);
		rdBtnBelongsGaac.setEnabled(enable);
		rdBtnNoBelongsGaac.setEnabled(enable);
		
		if (!enable)  {
			btnTestResultDate.setEnabled(false);
			rdBtnHighViralLoad.setEnabled(false);
			rdBtnHighViralLoad.setSelection(false);
			rdBtnNoHighViralLoad.setEnabled(false);
			rdBtnNoHighViralLoad.setSelection(true);

			btnCounselingDate.setEnabled(false);
			rdBtnRecommendCounseling.setEnabled(false);
			rdBtnRecommendCounseling.setSelection(false);
			rdBtnNotRecommendCounseling.setEnabled(false);
			rdBtnNotRecommendCounseling.setSelection(true);
			
			rdBtnBelongsGaac.setEnabled(false);
			rdBtnBelongsGaac.setSelection(false);
			rdBtnNoBelongsGaac.setEnabled(false);
			rdBtnNoBelongsGaac.setSelection(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.patient.util.IPatientTab#populate(org.hibernate
	 * .Session, org.celllife.idart.database.hibernate.Patient, boolean)
	 */
	/**
	 * Method loadPatientDetails.
	 * 
	 * @param sess
	 *            Session
	 * @param patient
	 *            Patient
	 * @param isPatientActive
	 *            boolean
	 * @see org.celllife.idart.gui.patient.tabs.IPatientTab#loadPatientDetails(Session,
	 *      Patient, boolean)
	 */
	@Override
	public void loadPatientDetails(Patient patient, boolean isPatientActive) {
		if (patient.getPatientViralLoads().size()!= 0) {
			do{
				int id=0;
				lastViralLoad = PatientManager.getLastPatientViralLoad(hSession, patient);
				
				if(lastViralLoad.getHighViralLoad())
				{
					rdBtnHighViralLoad.setSelection(true);
					rdBtnNoHighViralLoad.setSelection(false);
					btnTestResultDate.setEnabled(true);
					btnTestResultDate.setDate(lastViralLoad.getResultDate());
				}
				if(lastViralLoad.getRecommendedToCounselor())
				{
					rdBtnRecommendCounseling.setSelection(true);
					rdBtnNotRecommendCounseling.setSelection(false);
					btnCounselingDate.setEnabled(true);
					btnCounselingDate.setDate(lastViralLoad.getCounselingDate());
				}
				if(lastViralLoad.getBelongsGaac())
				{
					rdBtnBelongsGaac.setSelection(true);
					rdBtnNoBelongsGaac.setSelection(false);
					txtGaacNumber.setEnabled(true);
					txtGaacNumber.setText(lastViralLoad.getGaacNumber().toString());
				}
				
			}while(false);
			
		} else {
			btnTestResultDate.setEnabled(false);
			rdBtnHighViralLoad.setEnabled(false);
			rdBtnHighViralLoad.setSelection(false);
			rdBtnNoHighViralLoad.setEnabled(false);
			rdBtnNoHighViralLoad.setSelection(true);

			btnCounselingDate.setEnabled(false);
			rdBtnRecommendCounseling.setEnabled(false);
			rdBtnRecommendCounseling.setSelection(false);
			rdBtnNotRecommendCounseling.setEnabled(false);
			rdBtnNotRecommendCounseling.setSelection(true);
			
			rdBtnBelongsGaac.setEnabled(false);
			rdBtnBelongsGaac.setSelection(false);
			rdBtnNoBelongsGaac.setEnabled(false);
			rdBtnNoBelongsGaac.setSelection(true);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.misc.IGenericTab#setParent(org.eclipse.swt.widgets
	 * .TabFolder)
	 */
	@Override
	public void setParent(TabFolder parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.misc.IGenericTab#setSession(org.hibernate.Session)
	 */
	@Override
	public void setSession(Session session) {
		this.hSession = session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.celllife.idart.gui.misc.IGenericTab#setStyle(int)
	 */
	@Override
	public void setStyle(int SWTStyle) {
		this.style = SWTStyle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.patient.util.IPatientTab#submit(org.hibernate.
	 * Session, org.celllife.idart.database.hibernate.Patient)
	 */
	/**
	 * Method submit.
	 * 
	 * @param patient
	 *            Patient
	 * @see org.celllife.idart.gui.patient.tabs.IPatientTab#submit(Patient)
	 */
	@Override
	public void submit(Patient patient) {
		if (changesMade(patient)) {
			addLastViralLoad(patient);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.celllife.idart.gui.patient.IPatientTab#validateFields()
	 */
	/**
	 * Method validateFields.
	 * 
	 * @param patient
	 *            Patient
	 * @return Map<String,String>
	 * @see org.celllife.idart.gui.patient.tabs.IPatientTab#validateFields(Patient)
	 */
	@Override
	public Map<String, String> validateFields(Patient patient) {
		String title = "";
		String message = "";
		Boolean result = true;
		// if patient is pregnant, confirm date must be set
		if ((rdBtnHighViralLoad.getSelection()) && 
				((btnTestResultDate.getDate() == null) 
						|| (lastViralLoad==null || lastViralLoad.getResultDate()==null ? false :btnTestResultDate.getDate().getDate()<lastViralLoad.getResultDate().getDate()))) {
			title = Messages.getString("patient.viralload.resultdate.error.title");
			message = Messages.getString("patient.viralload.resultdate.error.message");
			result = false;
		}
		
		if ((rdBtnRecommendCounseling.getSelection()) && 
				((btnCounselingDate.getDate() == null) 
						|| (lastViralLoad.getCounselingDate()==null ? false :
							btnCounselingDate.getDate().getDate()<lastViralLoad.getCounselingDate().getDate()))) {
			title = Messages.getString("patient.viralload.counselingdate.error.title");
			message = Messages.getString("patient.viralload.counselingdate.error.message");
			result = false;
		}
		
		if (rdBtnBelongsGaac.getSelection()) {
			try{
				Integer.parseInt(txtGaacNumber.getText());
				if(txtGaacNumber.getText().length()!=10)
				{
					title = Messages.getString("patient.viralload.gaacnumber.error.title");
					message = Messages.getString("patient.viralload.gaacnumber.error.message");
					result = false;
				}
			}catch(Exception e)
			{
				title = Messages.getString("patient.viralload.gaacnumber.error.title");
				message = Messages.getString("patient.viralload.gaacnumber.error.message");
				result = false;
			}	
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", result.toString());
		map.put("title", title);
		map.put("message", message);
		return map;
	}

	private void cmdViewPAVASWidgetSelected() {
		if (localPatient == null) {
			new ShowPAVAS(parent.getShell());
		} else {
			new ShowPAVAS(parent.getShell(), localPatient);
		}
	}

	@Override
	public void setPatientDetails(Patient patient) {
		// TODO Auto-generated method stub
		
	}

}