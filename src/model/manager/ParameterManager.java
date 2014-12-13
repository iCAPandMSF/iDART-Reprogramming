package model.manager;

import java.util.List;

import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.DrugStockControl;
import org.celllife.idart.database.hibernate.Parameter;
import org.hibernate.Query;
import org.hibernate.Session;

public class ParameterManager {

	private static Logger log = Logger.getLogger(ParameterManager.class);
	
	public static Parameter getAmc(Session session)
	{

		Query query = session.createQuery(" FROM parameter");
		return (Parameter) query.list().get(0);
	}
}
