package org.celllife.idart.gui.reportParameters;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.manager.AdministrationManager;
import model.manager.DrugStockControlManager;
import model.manager.reports.DrugStockControlsReport;
import model.manager.reports.MiaReport;
import model.manager.reports.MonthlyStockOverviewReport;

import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.dao.ConexaoJDBC;
import org.celllife.idart.database.hibernate.DrugStockControl;
import org.celllife.idart.database.hibernate.StockCenter;
import org.celllife.idart.gui.platform.GenericReportGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartColor;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
	 */
public class DrugStockControlReport extends GenericReportGui {

	private Group grpPharmacySelection;

	private CCombo cmbStockCenter;
	
	private TableColumn clmSaldo;

	private TableColumn clmDrugName;

	private TableColumn clmSpace;

	private TableColumn clmAMC;
	
	private TableColumn clmRequisicao;
	
	private TableColumn clmTipoDeAlerta;

	private Group grpDrugs;
	
	private Table tblDrugs;
	
	private TableEditor editor;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            Shell
	 * @param activate
	 *            boolean
	 */
	public DrugStockControlReport(Shell parent, boolean activate) {
		super(parent, REPORTTYPE_STOCK, activate);
	}

	/**
	 * This method initializes newMonthlyStockOverview
	 */
	@Override
	protected void createShell() {
		Rectangle bounds = new Rectangle(100, 50, 800, 510);
		buildShell(REPORT_MIA, bounds);
		// create the composites
		createMyGroups();
	}

	private void createMyGroups() {
		createGrpDrugs();
		createDrugsTable();
	}
	
	/**
	 * This method initializes grpDrugs
	 * 
	 */
	private void createGrpDrugs() {

		grpDrugs = new Group(getShell(), SWT.NONE);

		grpDrugs.setText("Medicamentos e Niveis de Stock:");
		
		grpDrugs.setBounds(new Rectangle(50, 80, 700, 239));
		
		grpDrugs.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		createDrugsTable();
	}

	/**
	 * This method initializes tblDrugs
	 * 
	 * 
	 */
	private void createDrugsTable() {

		tblDrugs = new Table(grpDrugs, SWT.FULL_SELECTION);
		tblDrugs.setLinesVisible(true);
		tblDrugs.setBounds(new Rectangle(50, 25, 600, 200));
		tblDrugs.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		tblDrugs.setHeaderVisible(true);

		// 0 - clmSpace
		clmSpace = new TableColumn(tblDrugs, SWT.CENTER);
		clmSpace.setWidth(28);
		clmSpace.setText("No");

		// 1 - clmDrugName
		clmDrugName = new TableColumn(tblDrugs, SWT.NONE);
		clmDrugName.setText("Name do Medicamento");
		clmDrugName.setWidth(235);
		clmDrugName.setResizable(false);

		// 2 - clmAMC
		clmAMC = new TableColumn(tblDrugs, SWT.CENTER);
		clmAMC.setText("AMC");
		clmAMC.setWidth(50);
		clmAMC.setResizable(false);

		// 3 - clmSaldo
		clmSaldo = new TableColumn(tblDrugs, SWT.CENTER);
		clmSaldo.setText("Saldo");
		clmSaldo.setWidth(50);
		clmSaldo.setResizable(false);

		// 4 - clmRequisicao
		clmRequisicao = new TableColumn(tblDrugs, SWT.CENTER);
		clmRequisicao.setText("Qtd a Requisitar");
		clmRequisicao.setWidth(100);
		clmRequisicao.setResizable(true);

		// 4 - clmTipoDeAlerta
		clmTipoDeAlerta = new TableColumn(tblDrugs, SWT.CENTER);
		clmTipoDeAlerta.setText("Tipo de Alerta");
		clmTipoDeAlerta.setWidth(100);
		clmTipoDeAlerta.setResizable(true);

		editor = new TableEditor(tblDrugs);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		ConexaoJDBC c = new ConexaoJDBC();

		Display display = Display.getDefault();
		Color red = display.getSystemColor(SWT.COLOR_RED);

		Color green = display.getSystemColor(SWT.COLOR_GREEN);

		Color orange = new Color(display, 255, 127, 0);

		Color white = display.getSystemColor(SWT.COLOR_WHITE);

		List<DrugStockControl> risks = DrugStockControlManager
				.getDrugStockControls(getHSession());
		// Vector<RiscoRoptura> riscos =c.selectRiscoDeRopturaStock();
		int k = 0;
		for (DrugStockControl risk : risks) {

			System.out.println("\n medicamento: " + risk.getDrug().getName()
					+ " - amc: " + risk.getAmc() + " - saldo: "
					+ risk.getExistingStock());

			// Pendig Ropture --> stock <= AMC/3
			if (risk.getRiskStatus().equals("Pending rupture")) {

				final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				item.setText(new String[] { "" + (++k),
						risk.getDrug().getName(), "" + risk.getAmc(),
						"" + risk.getExistingStock(),
						"" + risk.getOrderQuantity(), risk.getRiskStatus() });
				item.setBackground(5, red);
			}

			// se tiver risco de roptura de stock --> stock =< AMC
			if (risk.getRiskStatus().equals("Risk rupture")) {

				final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				item.setText(new String[] { "" + (++k),
						risk.getDrug().getName(), "" + risk.getAmc(),
						"" + risk.getExistingStock(),
						"" + risk.getOrderQuantity(), risk.getRiskStatus() });
				item.setBackground(5, orange);
			}
			// Overstock --> stock>AMC*3
			if (risk.getRiskStatus().equals("OverStock")) {

				final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				item.setText(new String[] { "" + (++k),
						risk.getDrug().getName(), "" + risk.getAmc(),
						"" + risk.getExistingStock(),
						"" + risk.getOrderQuantity(), risk.getRiskStatus() });
				item.setBackground(5, green);
			}

			if (risk.getRiskStatus().equals("Normal Stock")) {

				final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				item.setText(new String[] { "" + (++k),
						risk.getDrug().getName(), "" + risk.getAmc(),
						"" + risk.getExistingStock(),
						"" + risk.getOrderQuantity(), risk.getRiskStatus() });
				item.setBackground(5, white);
			}
		}
		// for(int i=0;i<10;i++)
		// {
		//
		// final TableItem item = new TableItem(tblDrugs, SWT.NONE);
		// item.setText(new String[] {""+(++k), "ARV"+k, "AMC"+k,"SALDO"+k,
		// " -- ", "ALERTA"+k});
		// }

		clmSpace.pack();
		clmDrugName.pack();
		clmSaldo.pack();
		clmRequisicao.pack();
		clmTipoDeAlerta.pack();
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		iDartImage icoImage = iDartImage.REPORT_STOCKCONTROLPERCLINIC;
		buildCompdHeader(REPORT_DRUGS_STOCK_CONTROL, icoImage);
	}

	/**
	 * This method initializes grpClinicSelection
	 * 
	 */
	private void createGrpClinicSelection() {

		grpPharmacySelection = new Group(getShell(), SWT.NONE);
		grpPharmacySelection.setText("Pharmacy");
		grpPharmacySelection.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		grpPharmacySelection.setBounds(new org.eclipse.swt.graphics.Rectangle(
				140, 90, 320, 65));

		Label lblPharmacy = new Label(grpPharmacySelection, SWT.NONE);
		lblPharmacy.setBounds(new Rectangle(10, 25, 140, 20));
		lblPharmacy.setText("Select pharmacy");
		lblPharmacy.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		cmbStockCenter = new CCombo(grpPharmacySelection, SWT.BORDER);
		cmbStockCenter.setBounds(new Rectangle(156, 24, 160, 20));
		cmbStockCenter.setEditable(false);
		cmbStockCenter.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		cmbStockCenter.setBackground(ResourceUtils.getColor(iDartColor.WHITE));

		CommonObjects.populateStockCenters(getHSession(), cmbStockCenter);

	}

	/**
	 * This method initializes compButtons
	 * 
	 */
	@Override
	protected void createCompButtons() {
	}

	@Override
	protected void cmdViewReportWidgetSelected() {
		try {
			DrugStockControlsReport report = new DrugStockControlsReport(getShell());
			viewReport(report);
		} catch (Exception e) {
			getLog()
			.error(
					"Exception while running Monthly Receipts and Issues report",
					e);
		}
	}

	/**
	 * This method is called when the user presses "Close" button
	 * 
	 */
	@Override
	protected void cmdCloseWidgetSelected() {
		cmdCloseSelected();
	}

	@Override
	protected void setLogger() {
		setLog(Logger.getLogger(this.getClass()));
	}

}
