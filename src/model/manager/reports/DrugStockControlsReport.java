package model.manager.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.manager.DrugStockControlManager;
import model.manager.excel.conversion.exceptions.ReportException;
import model.nonPersistent.DrugStockControlDetail;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.celllife.idart.database.hibernate.DrugStockControl;
import org.eclipse.swt.widgets.Shell;

public class DrugStockControlsReport extends AbstractJasperReport {
	
	List<DrugStockControlDetail> drugStockControlDetails;
	

	public DrugStockControlsReport(Shell parent) {
		super(parent);
	}

	@Override
	protected void generateData() throws ReportException {
		
	}

	@Override
	protected Map<String, Object> getParameterMap() throws ReportException {

		List<DrugStockControl> drugStockControls = DrugStockControlManager.getDrugStockControls(hSession);
		drugStockControlDetails = new ArrayList <DrugStockControlDetail>();
		for(DrugStockControl dsc : drugStockControls)
		{
			drugStockControlDetails.add(new DrugStockControlDetail(dsc.getDrug().getName(), new Integer(dsc.getAmc()), new Integer(dsc.getExistingStock()), new Integer(dsc.getOrderQuantity()), dsc.getRiskStatus()));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", getReportPath());
		map.put("list", drugStockControlDetails);
		
		return map;
	}
	
	public Object getDataSource()
	{
		List<DrugStockControl> drugStockControls = DrugStockControlManager.getDrugStockControls(hSession);
		drugStockControlDetails = new ArrayList <DrugStockControlDetail>();
		for(DrugStockControl dsc : drugStockControls)
		{
			drugStockControlDetails.add(new DrugStockControlDetail(dsc.getDrug().getName(), new Integer(dsc.getAmc()), new Integer(dsc.getExistingStock()), new Integer(dsc.getOrderQuantity()), dsc.getRiskStatus()));
		}
		return new JRBeanCollectionDataSource(drugStockControlDetails);
	}

	@Override
	protected String getReportFileName() {
		return "DrugStockControl";
	}


}
