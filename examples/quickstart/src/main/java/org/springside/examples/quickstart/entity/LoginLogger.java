package org.springside.examples.quickstart.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "czp_t_login_logger")
public class LoginLogger extends IdEntity {

	private Date createDate;
	private String ip;
	private String info;
	private String url;
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(length = 1000)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
