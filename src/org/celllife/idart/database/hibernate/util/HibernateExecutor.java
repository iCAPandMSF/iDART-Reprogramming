package org.celllife.idart.database.hibernate.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateExecutor {
	
	private static final Logger log = Logger.getLogger(HibernateExecutor.class.getName());
	
	public static <T> T execute(HibernateCallback<T> callback) {
		return execute(callback, HibernateUtil.getNewSession(), true, true, true);
	}

	public static <T> T execute(HibernateCallback<T> callback, Session session,
			boolean transactional, boolean flushSession, boolean closeSession) {
		Transaction tx = null;
		if (transactional) {
			tx = session.beginTransaction();
		}

		T result = null;
		try {
			result = callback.doInHibernate(session);
			if (flushSession)
				session.flush();

			if (tx != null)
				tx.commit();
		} catch (Exception e) {
			log.error("Error executing hibernate callback", e);
			if (tx != null)
				tx.rollback();
		} finally {
			if (closeSession)
				session.close();
		}

		return result;
	}
}
