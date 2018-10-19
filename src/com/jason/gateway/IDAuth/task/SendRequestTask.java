package com.jason.gateway.IDAuth.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jason.gateway.IDAuth.IDAuthJob;
import com.jason.gateway.IDAuth.modle.IDAuthRequest;
import com.jason.gateway.IDAuth.utils.Utils;

public class SendRequestTask implements Runnable {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private void TestInserResult(Connection conn, String image_id) throws SQLException {
		String sql = "insert into id_auth_result (image_id, is_hit, "
				+ "score, name, idcard, created_time) values (?,?,?,?,?,?)";

		java.util.Date datetime = new java.util.Date();
		Timestamp currentTime = new Timestamp(datetime.getTime());


		ps = conn.prepareStatement(sql);
		ps.setString(1, image_id);
		ps.setBoolean(2, true);
		ps.setFloat(3, 0.9f);
		ps.setString(4, "aa");
		ps.setString(5, "123123");
		ps.setTimestamp(6, currentTime);
		ps.executeUpdate();
	}

	private String Send() {
		return "success";
	}

	public void run() {
		while (true) {
			while (!IDAuthJob.sendqueue.isEmpty()) {
				rs = null;
				IDAuthRequest request = IDAuthJob.sendqueue.poll();
				// get osg 图片
				String flag = Send();
				try {
					conn = Utils.getConnection();
					conn.setAutoCommit(true);
					String updatesql = "update id_auth_requst set upload_status = '" + flag + "' where id = "
							+ request.getId();
					ps = conn.prepareStatement(updatesql);
					ps.execute();
					TestInserResult(conn, request.getImage_id());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Utils.release(rs, ps, conn);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
