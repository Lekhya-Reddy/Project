package com.ctl.ch.driver.diagnostics.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ctl.ch.common.coreutilities.DataRow;
import com.ctl.ch.common.coreutilities.DataTable;

public abstract class ServiceUtils {
	
	public static interface RowFormater<T> {
		public Class<T> getType();
		public String[] fields();
		public Object[] format(DataRow<?, ?> row);
	}

	private static Logger log = Logger.getLogger(ServiceUtils.class);

	public static <R> R instance(RowFormater<R> formater,
			Object... values) {
		return instance(BeanUtils.instantiate(formater.getType()), formater, values);
	}

	public static <R> R instance(R instance, RowFormater<R> formater,
			Object[] values) {
		String[] mapping = formater.fields();
		if (mapping.length == values.length) {
			for (int i = 0; i < mapping.length; i++) {
				if( values[i] != null ) {
					Method mappingSetter = BeanUtils.findMethod(formater.getType(), "set"
							+ mapping[i], values[i].getClass());
					if (mappingSetter != null) {
						try {
							mappingSetter.invoke(instance, values[i]);
						} catch (ReflectiveOperationException e) {
							// Failed invoke the method
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// INFO: Should not occur
							e.printStackTrace();
						}
					} else {
						log.warn("Setter method not found for " + formater.getType().getName()
								+ "/" + mapping[i]);
					}
				}
			}
		}
		return instance;
	}
	
	public static <R> List<R> populateList(DataTable table, RowFormater<R> formater) {
		ArrayList<R> list = new ArrayList<R>();

		for (DataRow<?, ?> row : table.getRows()) {
			Object[] values = formater.format(row);
			if (values.length == formater.fields().length) {
				list.add(populate(formater, values));
			} else {
				log.warn("Failed to create an instance of "
						+ formater.getType().getName()
						+ ",as Properties count do not match the values");
			}
		}

		return list;
	}

	public static <R> R populate(DataTable table, RowFormater<R> formater) {
		ArrayList<DataRow<String, Object>> rows = table.getRows();
		if (rows.size() > 0) {
			return instance(formater, formater.format(rows.get(0)));
		}
		return null;
	}

	public static <R> R populate(RowFormater<R> formater, Object... values) {
		return instance(formater, values);
	}

	public static <R> R populate(R instance, RowFormater<R> formater, Object... values) {
		return instance(instance, formater, values);
	}
	
	public static boolean isNullOrEmpty(String value) {
		return value == null || "".equals(value);
	}
}
