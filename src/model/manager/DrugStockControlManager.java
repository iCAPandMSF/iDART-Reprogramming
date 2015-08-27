package model.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.Drug;
import org.celllife.idart.database.hibernate.DrugStockControl;
import org.celllife.idart.database.hibernate.Parameter;
import org.hibernate.Query;
import org.hibernate.Session;

public class DrugStockControlManager {

	private static Logger log = Logger.getLogger(DrugStockControlManager.class);
	
	public static List<DrugStockControl> getDrugStockControls(Session session)
	{
		createDrugStockControls(session);
		updateDrugStockControls(session);
		Query query = session.createQuery("SELECT dsc FROM drugstockcontrol as dsc");
		return (List<DrugStockControl>) query.list();
	}
	
	private static void createDrugStockControls(Session session)
	{
		Query query = session
				.createQuery("select d from Drug as d");
		
		List<Drug> drugs = query.list();
		DrugStockControl dsc;
		for (Drug drug : drugs)
		{
			dsc = new DrugStockControl(drug.getId());
			dsc.setDrug(drug);
			if(drugStockControlExists(session, drug.getId()))
			{
				session.save(dsc);
			}
		}
		session.flush();
	}
	
	private static boolean drugStockControlExists(Session session,int drugId)
	{
		Query query = session.createQuery("SELECT dsc FROM drugstockcontrol as dsc where dsc.drugId = :drugId").setInteger("drugId", drugId);
		List <DrugStockControl> dscs = query.list();
		if(dscs.size() > 0)
		{
			return false;
		}
		return true;
	}
	
	private static void updateDrugStockControls(Session session)
	{
		Query query = session.createQuery("SELECT dsc FROM drugstockcontrol as dsc");
		
		List<DrugStockControl> drugStockControls = query.list();
		
		Date startFirstMonth,endFirstMonth,startSecondMonth,endSecondMonth,startThirdMonth,endThirdMonth,startCurrentMonth,endCurrentMonth;
		Long firstMonthQty,secondMonthQty,thirdMonthQty,CurrentMonthQty, existingQuantity,orderQuantity;
		String riskStatus;
		int amc;
		Parameter getAmc=ParameterManager.getAmc(session);
		int getAmcValue=Integer.parseInt(getAmc.getParameterValue());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date currentDate = cal.getTime();
		endCurrentMonth = cal.getTime();
		
		cal.add(Calendar.MONTH, cal.get(Calendar.DAY_OF_MONTH) > getAmcValue ? 0 : -1);
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue+1);
		startCurrentMonth = cal.getTime();
		
		cal.add(Calendar.MONTH, cal.get(Calendar.DAY_OF_MONTH) > getAmcValue ? 0 : -1);
		
		cal.set(Calendar.DAY_OF_MONTH,getAmcValue );
		endThirdMonth = cal.getTime();
		
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue+1);
		startThirdMonth = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue);
		endSecondMonth = cal.getTime();
		
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue+1);
		startSecondMonth = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue);
		endFirstMonth = cal.getTime();
		
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, getAmcValue+1);
		startFirstMonth = cal.getTime();
		
		
		
		
		
		
		for(DrugStockControl dsc : drugStockControls)
		{
			
				firstMonthQty = quantityDrugDispensed(session, dsc.getDrugId(), startFirstMonth, endFirstMonth);
				secondMonthQty = quantityDrugDispensed(session, dsc.getDrugId(), startSecondMonth, endSecondMonth);
				thirdMonthQty = quantityDrugDispensed(session, dsc.getDrugId(), startThirdMonth, endThirdMonth);
				CurrentMonthQty=quantityDrugDispensed(session, dsc.getDrugId(), startCurrentMonth, endCurrentMonth);
						
				amc = Math.max(firstMonthQty.intValue(), secondMonthQty.intValue());
				amc = Math.max(amc, thirdMonthQty.intValue());
				//amc =Math.max(amc, CurrentMonthQty.intValue());
				
				dsc.setAmc(amc);
	
				existingQuantity = getExistingStock(session,dsc.getDrugId());
				
				dsc.setExistingStock(existingQuantity.intValue());
				
				orderQuantity = amc*3 - existingQuantity < 0 ? 0 : amc*3 - existingQuantity;
				
				dsc.setOrderQuantity(orderQuantity.intValue());
				
				riskStatus = "Normal stock";
				if(existingQuantity <= amc/3)
				{
					riskStatus = "Pending rupture";
				}
				else
				{
					if(existingQuantity <= amc)
					{
						riskStatus = "Risk rupture";
					}
					if(existingQuantity > amc*2)
					{
						riskStatus = "OverStock";
					}
				}
				
				dsc.setRiskStatus(riskStatus);
				session.save(dsc);
			
			
		}
		session.flush();
	}
	
	private static Long quantityDrugDispensed(Session session,int drugId,Date startDate,Date endDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		Date endDateFinal = cal.getTime();
		
		Double qty = ((Double) session
				.createQuery(
						"select sum(pd.amount)/avg(pd.stock.drug.packSize) "
						+ "from PackagedDrugs pd where pd.stock.drug = :drug "
						+ "and pd.parentPackage.packDate between :startDate and :endDate "
						+ "and pd.parentPackage.prescription is not null ")
						.setInteger("drug", drugId).setDate("startDate", startDate).setDate(
										"endDate", endDateFinal).uniqueResult());
		return qty == null ? new Long(0) : qty.longValue();
	}
	
	private static Long getExistingStock(Session session,int drugId)
	{
		Long quantity = ((Long) session
				.createQuery(
						"select sum(sl.fullContainersRemaining) "
						+ "from StockLevel sl where sl.batch.drug = :drug ")
						.setInteger("drug", drugId).uniqueResult());
		return quantity == null ? new Long(0) : quantity;
	}
	
}
