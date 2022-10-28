package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class ThumbnailatorConfig implements Serializable {

	private static final long serialVersionUID = -4833776683755042074L;
	
	private double scale;
	private boolean watermark;
	private String watermarkImage;
	
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public boolean isWatermark() {
		return watermark;
	}
	public void setWatermark(boolean watermark) {
		this.watermark = watermark;
	}
	public String getWatermarkImage() {
		return watermarkImage;
	}
	public void setWatermarkImage(String watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

}
