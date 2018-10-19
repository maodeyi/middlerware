package com.jason.gateway.IDAuth.modle;

public class IDAuthResult {
	private boolean is_hit;
	private float score;
	private String name;
	private String idcard;
	private String image_id;
	public boolean getIs_hit() {
		return is_hit;
	}
	public void setIs_hit(boolean is_hit) {
		this.is_hit = is_hit;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	
}
