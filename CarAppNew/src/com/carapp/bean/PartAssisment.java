package com.carapp.bean;

public class PartAssisment {
 
 private String option;
 private String value;
 private String imagePath;
 
 public String getOption() {
	return option;
}
public void setOption(String option) {
	this.option = option;
}
public String getValue() {
	return value;
}
@Override
public String toString() {
	return  option + "-" + value;
}
public void setValue(String value) {
	this.value = value;
}
public String getImagePath() {
	return imagePath;
}
public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
}

}
