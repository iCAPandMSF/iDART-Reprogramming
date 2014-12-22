package model.manager.reports;

import java.util.HashMap;
import java.util.Map;

import model.manager.excel.conversion.exceptions.ReportException;

import org.eclipse.swt.widgets.Shell;

public class DrugStockControlsReport extends AbstractJasperReport {

	public DrugStockControlsReport(Shell parent) {
		super(parent);
	}

	@Override
	protected void generateData() throws ReportException {
	}

	@Override
	protected Map<String, Object> getParameterMap() throws ReportException {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", getReportPath());
		
		return map;
	}

	@Override
	protected String getReportFileName() {
		return "DrugStockControl";
	}


}
