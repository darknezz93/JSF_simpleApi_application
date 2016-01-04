package com.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.domain.*;


/**
 * CompanyManageBean - class responsible for company managing
 * @author Adam
 *
 */
@ManagedBean
@SessionScoped
public class CompanyManageBean {
	
	private ArrayList<Company> companies = new ArrayList<Company>();
	private ApiBean apiBean = new ApiBean();
	private NavigationBean navigation = new NavigationBean();
	private boolean restore = false;

	public boolean isRestore() {
		return restore;
	}

	public void setRestore(boolean restore) {
		this.restore = restore;
	}

	public List<Company> getCompanies() {
		return companies;
	}
	
	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}
	
	/**
	 * Load companies from apiBean
	 */
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
	
	/**
	 * Check if user can restore data 
	 */
	public void checkForRestore() {
    FacesContext context = FacesContext.getCurrentInstance();
	if(this.companies != null) {
			for(int i = 0; i < this.companies.size(); i++) {
				if(context.getExternalContext().getSessionMap().get("materials" + this.companies.get(i).getCompanyID().toString()) != null) {
					this.restore = true;
				}	
			}
		}
	}
	
	/**
	 * Removes data from session
	 * @throws IOException
	 */
	public void restoreData() throws IOException {
		for(int i = 0; i < companies.size(); i++) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("materials" + companies.get(i).getCompanyID().toString());
		}
		this.restore = false;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	
	
	

}
