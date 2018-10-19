package com.jason.gateway.IDAuth.modle;

public class IDAuthRequest {
	private int id;
	private String unit_code;
	private String unit_name;
	private String addr_code;
	private String addr_detail;
	private String center_coordinate_x;
	private String center_coordinate_y;
	private String oper_name;
	private String oper_idcard;
	private String image_b64;
	private String image_id;
	private java.sql.Timestamp created_time;
	private String upload_status;

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getAddr_code() {
		return addr_code;
	}

	public void setAddr_code(String addr_code) {
		this.addr_code = addr_code;
	}

	public String getAddr_detail() {
		return addr_detail;
	}

	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}

	public String getCenter_coordinate_x() {
		return center_coordinate_x;
	}

	public void setCenter_coordinate_x(String center_coordinate_x) {
		this.center_coordinate_x = center_coordinate_x;
	}

	public String getCenter_coordinate_y() {
		return center_coordinate_y;
	}

	public void setCenter_coordinate_y(String center_coordinate_y) {
		this.center_coordinate_y = center_coordinate_y;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public String getOper_idcard() {
		return oper_idcard;
	}

	public void setOper_idcard(String oper_idcard) {
		this.oper_idcard = oper_idcard;
	}

	public String getImage_b64() {
		return image_b64;
	}

	public void setImage_b64(String image_b64) {
		this.image_b64 = image_b64;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getUpload_status() {
		return upload_status;
	}

	public void setUpload_status(String upload_status) {
		this.upload_status = upload_status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.sql.Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(java.sql.Timestamp created_time) {
		this.created_time = created_time;
	}

}
