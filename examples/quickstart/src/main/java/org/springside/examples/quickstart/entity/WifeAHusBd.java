package org.springside.examples.quickstart.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CZP_T_WIFEHUD")
public class WifeAHusBd extends IdEntity {
	private Task husband;
	private Task wife;

	@OneToOne()
	@JoinColumn(name = "HUSBAND_ID")
	public Task getHusband() {
		return husband;
	}

	public void setHusband(Task husband) {
		this.husband = husband;
	}

	@OneToOne()
	@JoinColumn(name = "WIFE_ID")
	public Task getWife() {
		return wife;
	}

	public void setWife(Task wife) {
		this.wife = wife;
	}

}
