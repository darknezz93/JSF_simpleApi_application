package com.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import com.domain.*;



@ManagedBean
@SessionScoped
public class CompanyManageBean {
	
	private List<Company> companies = new ArrayList<Company>();
	private ApiBean apiBean = new ApiBean();
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	@PostConstruct
	public void consumeCompanies() {
		this.companies = apiBean.apiCompanies();
	}
	
	
	

}
