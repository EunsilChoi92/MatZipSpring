package com.koreait.matzip.rest.model;

public class RestDMI extends RestVO {
	private double north_east;
	private double south_west;
	private String cd_category_nm;
	private String user_nm;
	private int cnt_favorite;
	private int is_favorite;
	
	public double getNorth_east() {
		return north_east;
	}
	public void setNorth_east(double north_east) {
		this.north_east = north_east;
	}
	public double getSouth_west() {
		return south_west;
	}
	public void setSouth_west(double south_west) {
		this.south_west = south_west;
	}
	public String getCd_category_nm() {
		return cd_category_nm;
	}
	public void setCd_category_nm(String cd_category_nm) {
		this.cd_category_nm = cd_category_nm;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public int getCnt_favorite() {
		return cnt_favorite;
	}
	public void setCnt_favorite(int cnt_favorite) {
		this.cnt_favorite = cnt_favorite;
	}
	public int getIs_favorite() {
		return is_favorite;
	}
	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}
	
	
	
	
}
