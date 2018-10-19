package com.jason.gateway.IDAuth.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jason.gateway.IDAuth.IDAuthJob;
import com.jason.gateway.IDAuth.modle.IDAuthRequest;
import com.jason.gateway.IDAuth.utils.Utils;

public class RequestTask implements Runnable {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			getResquest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getResquest() throws SQLException {
		if (Utils.getRequestSize() <= 0 || Utils.getRequestFailSize() < 0) {
			System.out.println("RequestSize is 0 os RequestFailSize < 0");
		}
		if (IDAuthJob.sendqueue.isEmpty()) {
			try {
				conn = Utils.getConnection();
				conn.setAutoCommit(false);
				if (Utils.getRequestSize() == Utils.getRequestFailSize()) {
					String sql = "select * from id_auth_requst where upload_status ='fail' order by id limit "
							+ Utils.getRequestSize();
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					List<IDAuthRequest> l = Utils.processResultSetToList(rs, IDAuthRequest.class);
					for (int i = 0; i < l.size(); i++) {
						System.out.println("get requesttask id " + l.get(i).getId());
						IDAuthJob.sendqueue.offer(l.get(i));
						System.out.println("offer auth request object to sendqueue");
					}
				} else if (Utils.getRequestSize() > Utils.getRequestFailSize()) {
					String sql = "select * from id_auth_requst where upload_status ='waiting' order by id limit "
							+ Utils.getRequestSize();
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					List<IDAuthRequest> l = Utils.processResultSetToList(rs, IDAuthRequest.class);
					for (int i = 0; i < l.size(); i++) {
						System.out.println("get requesttask id " + l.get(i).getId());
						IDAuthJob.sendqueue.offer(l.get(i));
						System.out.println("offer auth request object to sendqueue");
					}

					if (Utils.getRequestFailSize() == 0) {
						String sqlfail = "select * from id_auth_requst where upload_status ='fail' order by id limit "
								+ Utils.getRequestSize();
						ps = conn.prepareStatement(sqlfail);
						rs = ps.executeQuery();
						List<IDAuthRequest> faill = Utils.processResultSetToList(rs, IDAuthRequest.class);
						for (int i = 0; i < faill.size(); i++) {
							System.out.println("get requesttask id " + faill.get(i).getId());
							IDAuthJob.sendqueue.offer(l.get(i));
							System.out.println("offer auth request object to sendqueue");
						}
					}
				} else {
					System.out.println("Utils.getRequestFailSie() > Utils.getRequestSie()");
				}

			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					conn.commit();
					Utils.release(rs, ps, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
