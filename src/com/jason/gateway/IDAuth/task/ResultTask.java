package com.jason.gateway.IDAuth.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.jason.gateway.IDAuth.modle.IDAuthResult;
import com.jason.gateway.IDAuth.utils.Utils;

public class ResultTask implements Runnable {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			putResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IDAuthResult[] recv() {
		return null;
	}

	private void putResult() {
		String sql = "insert into id_auth_result (image_id, is_hit, "
				+ "score, name, idcard, created_time) values (?,?,?,?,?,?)";
		IDAuthResult[] results = recv();
		for (int i = 0; i < results.length; i++) {
			java.util.Date datetime = new java.util.Date();
			Timestamp currentTime = new Timestamp(datetime.getTime());

			try {
				conn = Utils.getConnection();
				conn.setAutoCommit(true);
				ps = conn.prepareStatement(sql);
				ps.setString(1, results[i].getImage_id());
				ps.setBoolean(2, results[i].getIs_hit());
				ps.setFloat(3, results[i].getScore());
				ps.setString(4, results[i].getName());
				ps.setString(5, results[i].getIdcard());
				ps.setTimestamp(6, currentTime);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Utils.release(rs, ps, conn);
			}
		}
	}

}
