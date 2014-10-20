package org.celllife.idart.integration.eKapa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.manager.AdherenceManager;
import model.manager.TemporaryRecordsManager;

import org.celllife.idart.commonobjects.iDartProperties;
import org.celllife.idart.database.hibernate.PillCount;
import org.celllife.idart.database.hibernate.tmp.AdherenceRecord;
import org.celllife.idart.database.hibernate.tmp.PackageDrugInfo;
import org.celllife.idart.database.hibernate.util.HibernateCallback;
import org.celllife.idart.database.hibernate.util.HibernateExecutor;
import org.celllife.idart.events.AdherenceEvent;
import org.celllife.idart.events.PackageEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;



public class EkapaEventListener {


	public void handleAdherenceEvent(final AdherenceEvent event) {
		if (iDartProperties.isEkapaVersion) {
			HibernateExecutor.execute(new HibernateCallback<Void>() {
				@Override
				public Void doInHibernate(Session session)
						throws HibernateException, SQLException {
					
					Set<PillCount> pcs = event.getPillCounts();
					List<AdherenceRecord> adhList = new ArrayList<AdherenceRecord>();
					
					for (PillCount pct : pcs) {
						AdherenceRecord ad = AdherenceManager.getAdherenceRecordForPillCount(session,pct);
						if (ad != null) {
							adhList.add(ad);
						}
					}
					TemporaryRecordsManager.saveAdherenceRecordsToDB(session, adhList);
					return null;
				}
			});
		}
	}
	

	public void handlePackageEvent(final PackageEvent event) {
		if (PackageEvent.Type.PICKUP_ONLY.equals(event.getType())){
			HibernateExecutor.execute(new HibernateCallback<Void>() {
				@Override
				public Void doInHibernate(Session session)
						throws HibernateException, SQLException {
					
					List<PackageDrugInfo> list = TemporaryRecordsManager.getPDIsForPackage(session, event.getPack());
					for (PackageDrugInfo pdi : list) {
						pdi.setPickupDate(event.getPack().getPickupDate());
						session.save(pdi);
					}
					return null;
				}
			});
		} else if (PackageEvent.Type.RETURN.equals(event.getType())){
			HibernateExecutor.execute(new HibernateCallback<Void>() {
				@Override
				public Void doInHibernate(Session session)
						throws HibernateException, SQLException {
					
					TemporaryRecordsManager.deletePackageDrugInfosForPackage(session, event.getPack());
					
					return null;
				}
			});
			
		}
	}
}
