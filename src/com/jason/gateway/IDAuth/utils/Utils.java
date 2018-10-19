package com.jason.gateway.IDAuth.utils;

import java.sql.Connection;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Utils {
	private static final String config = "config.properties";
	private static DataSource ds;
	private static Properties pro;
	static {
		pro = new Properties();
		try {
			System.out.println(Utils.class.getClassLoader().getResource(""));
			pro.load(Utils.class.getClassLoader().getResourceAsStream(config));
			ds = BasicDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("创建数据库连接失败！");
		}

	}

	public static int getRequestSize() {
		if (pro.getProperty("requestsize") == null)
			return 100;
		return Integer.parseInt(pro.getProperty("requestsize"));
	}

	public static int getRequestFailSize() {
		if (pro.getProperty("requestfailsize") == null)
			return 0;
		return Integer.parseInt(pro.getProperty("requestfailsize"));
	}

	public static int getRequestDelay() {
		if (pro.getProperty("requestdelay") == null)
			return 180;
		return Integer.parseInt(pro.getProperty("requestdelay"));
	}

	public static int getRequestSendThread() {
		if (pro.getProperty("requestsendthread") == null)
			return 100;
		return Integer.parseInt(pro.getProperty("requestsendthread"));
	}

	public static int getResutlThread() {
		if (pro.getProperty("resultthread") == null)
			return 100;
		return Integer.parseInt(pro.getProperty("resultthread"));
	}

	public static void release(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public enum Type {
		Byte, Character, Short, Integer, Long, Float, Double, Boolean, String, Date, BYTE_ARRAY, FormFile, Object
	}

	@SuppressWarnings("deprecation")
	public static <T> List<T> processResultSetToList(ResultSet rs, Class<T> clazz) throws Exception {
		List<T> ls = new ArrayList<T>();
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {
			T tt = clazz.newInstance();
			for (Field field : fields) {
				try {
					Object value = null;
					try {
						String fieldName = field.getName();
						Type tp = getMappedType(field.getType().getSimpleName());
						switch (tp) {
						case Long:
							value = Long.valueOf(rs.getLong(fieldName));
							break;
						case Integer:
							value = Integer.valueOf(rs.getInt(fieldName));
							break;
						case Float:
							value = Double.valueOf(rs.getFloat(fieldName));
							break;
						case Date:
							java.sql.Timestamp t = rs.getTimestamp(fieldName);
							if (t != null) {
								value = new java.util.Date(t.getTime());
							}
							break;
						default:
							value = rs.getObject(fieldName);
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					field.setAccessible(true);
					if (value != null) {
						field.set(tt, value);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			ls.add(tt);
		}
		return ls;
	}

	public static Type getMappedType(String name) throws Exception {
		name = name.toLowerCase();
		if (name.contains("long")) {
			return Type.Long;
		} else if (name.contains("int")) {
			return Type.Integer;
		} else if (name.contains("date")) {
			return Type.Date;
		} else if (name.contains("string")) {
			return Type.String;
		} else if (name.contains("formfile")) {
			return Type.FormFile;
		}
		return Type.Object;
	}
}