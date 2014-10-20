package org.celllife.idart.database.hibernate.util;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface HibernateCallback<T> {

	T doInHibernate(Session session) throws HibernateException,
			SQLException;

}
