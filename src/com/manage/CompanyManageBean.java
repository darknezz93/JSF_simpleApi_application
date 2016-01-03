package com.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.domain.*;



@ManagedBean
@SessionScoped
public class CompanyManageBean {
	
	private List<Company> companies = new ArrayList<Company>();
	private ApiBean apiBean = new ApiBean();
	private NavigationBean navigation = new NavigationBean();
	private FacesContext context = FacesContext.getCurrentInstance();
	
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
	
	public String showCompanyMaterials() {
		return navigation.goToCompanyMaterials();
	}
	
	public String showCompanies() {
		return navigation.goToCompanies();
	}
	
	public boolean checkForRestore() {
		for(int i = 0; i < this.companies.size(); i++) {
			if(context.getExternalContext().getSessionMap().get("materials" + this.companies.get(i).getCompanyID().toString()) != null)
				return true;
		}
		return false;
	}
	
	public void restoreData() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	
	

}
