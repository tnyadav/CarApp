package com.carapp.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class FleetData {

@Expose
private List<Fleet> fleets = new ArrayList<Fleet>();

/**
*
* @return
* The fleets
*/
public List<Fleet> getFleets() {
return fleets;
}

@Override
public String toString() {
	return "FleetData [fleets=" + fleets + "]";
}

/**
*
* @param fleets
* The fleets
*/
public void setFleets(List<Fleet> fleets) {
this.fleets = fleets;
}


}
