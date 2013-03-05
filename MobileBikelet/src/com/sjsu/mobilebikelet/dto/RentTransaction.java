package com.sjsu.mobilebikelet.dto;

import java.util.Date;
import java.util.List;

public class RentTransaction {

	private Long id;
	
	private String fromStation;

    private String toStation;

    private String rentStartDate;

    private String rentEndDate;

    private String status;

    private String comments;

	private String bike;
	
	private String accessKey;
	
	private List<Station> stationList; 
    
    
    public List<Station> getStationlist() {
		return stationList;
	}

	public void setStationlist(List<Station> stationlist) {
		this.stationList = stationlist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getRentStartDate() {
		return rentStartDate;
	}

	public void setRentStartDate(String rentStartDate) {
		this.rentStartDate = rentStartDate;
	}

	public String getRentEndDate() {
		return rentEndDate;
	}

	public void setRentEndDate(String rentEndDate) {
		this.rentEndDate = rentEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getBike() {
		return bike;
	}

	public void setBike(String bike) {
		this.bike = bike;
	}

    public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
}
