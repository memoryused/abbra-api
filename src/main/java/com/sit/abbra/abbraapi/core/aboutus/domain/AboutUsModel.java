package com.sit.abbra.abbraapi.core.aboutus.domain;

import com.sit.common.CommonRequest;

public class AboutUsModel extends CommonRequest {

	private static final long serialVersionUID = 4162616802133312239L;

	private AboutUs aboutUs = new AboutUs();

	public AboutUs getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(AboutUs aboutUs) {
		this.aboutUs = aboutUs;
	}
}
