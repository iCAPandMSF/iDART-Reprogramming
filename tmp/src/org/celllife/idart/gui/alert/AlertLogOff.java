/*
 * 

 *
 */

package org.celllife.idart.gui.alert;
import java.sql.SQLException;
import java.util.Vector;




import org.celllife.idart.database.dao.ConexaoJDBC;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 */
public class AlertLogOff extends GenericFormGui  {


	
	private TableColumn clmSaldo;

	private TableColumn clmDrugName;

	private TableColumn clmSpace;

	private TableColumn clmAMC;
	
	private TableColumn clmRequisicao;
	
	private TableColumn clmTipoDeAlerta;

	private Group grpDrugs;
	
	private Table tblDrugs;

	private TableEditor editor;

	private Button btnCancel;

	

	public AlertLogOff(Shell theParent,
			boolean fromShortcut) {
	
		super(theParent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes getMyShell()
	 */
	@Override
	protected void createShell() {
		String shellTxt = Messages.getString("alerta.roptura.stock.title"); //$NON-NLS-1$
		Rectangle bounds = new Rectangle(75, 50, 800, 400);
		
	
			
		// Parent Generic Methods ------
		buildShell(shellTxt, bounds); // generic shell build
	}

	@Override
	protected void createContents() {
	

		createGrpDrugs();
		
		// btnCancel
		btnCancel = new Button(getShell(), SWT.NONE);
		btnCancel.setText(Messages.getString("alerta.button.fechar.text")); //$NON-NLS-1$
		btnCancel.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnCancel.setToolTipText(Messages
				.getString("alerta.button.fechar.tooltip")); //$NON-NLS-1$
		
		btnCancel.setBounds(50,325, 100, 40);
		
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdCancelWidgetSelected();
			}
		});
		

	}

	/**
	 * Method addDisposeListener.
	 * 
	 * @param dl
	 *            DisposeListener
	 */
	public void addDisposeListener(DisposeListener dl) {
		getShell().addDisposeListener(dl);
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = "Alerta de N�veis de Stock";
		iDartImage icoImage = iDartImage.PRESCRIPTIONNEW;
		buildCompHeaderAlerta(headerTxt, icoImage);
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

		ConexaoJDBC c=new ConexaoJDBC();
		 
		  Display display = Display.getDefault();
		Color red = display.getSystemColor(SWT.COLOR_RED);
		  
		  Color green =display.getSystemColor(SWT.COLOR_GREEN);
		  
		 Color orange = new Color(display, 255, 127, 0);
		 
		try {
			Vector<RiscoRoptura> riscos =c.selectRiscoDeRopturaStock();
			int k=0;
			for(int i =0; i<riscos.size();i++){
				
				System.out.println("\n medicamento: "+riscos.get(i).getNome()+" - amc: "+riscos.get(i).getAmc()+" - saldo: "+riscos.get(i).getSaldo());
				
				
 //Pendig Ropture --> stock <= AMC/3
				
				if(riscos.get(i).getAmc()/3>=riscos.get(i).getSaldo()){
					
					final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				    item.setText(new String[] {""+(++k), riscos.get(i).getNome(), ""+riscos.get(i).getAmc(),""+riscos.get(i).getSaldo(), ""+riscos.get(i).getQtyOrdem(),"Pending Rupture"});
				    
				      item.setBackground(5, red);
				}
				
				else
				
				//se tiver risco de roptura de stock --> stock =< AMC
				
				if(riscos.get(i).getAmc()>=riscos.get(i).getSaldo()){
					
					final TableItem item = new TableItem(tblDrugs, SWT.NONE);
				    item.setText(new String[] {""+(++k), riscos.get(i).getNome(), ""+riscos.get(i).getAmc(),""+riscos.get(i).getSaldo(), ""+riscos.get(i).getQtyOrdem(), "Risk of Rupture"});
				    
				    item.setBackground(5, orange);


				   
				   
			}
				else
					// Overstock --> stock>AMC*3
					if(riscos.get(i).getAmc()*3 < riscos.get(i).getSaldo()){
						
						final TableItem item = new TableItem(tblDrugs, SWT.NONE);
					    item.setText(new String[] {""+(k+1), riscos.get(i).getNome(), ""+riscos.get(i).getAmc(),""+riscos.get(i).getSaldo(), " -- ", "OverStock"});
					    	
						item.setBackground(5, green);
					}
	         


				   
				   
			
		
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		   clmSpace.pack();
			clmDrugName.pack();
			clmSaldo.pack();
			clmRequisicao.pack();
			clmTipoDeAlerta.pack();
	}






	@Override
	protected void cmdCancelWidgetSelected() {
		// TODO Auto-generated method stub
		closeShell(false);
	}


	@Override
	protected void setLogger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createCompButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearForm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean submitForm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean fieldsOk() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void cmdSaveWidgetSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void cmdClearWidgetSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void enableFields(boolean enable) {
		// TODO Auto-generated method stub
		
	}
	
	

}

