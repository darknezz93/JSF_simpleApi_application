package com.manage;

public class NavigationBean {
	
	public String goToCompanyMaterials() {
		return "/companyMaterials.xhtml?faces-redirect=true";
	}
	
	public String goToMaterialDetails() {
		return "/material.xhtml?faces-redirect=true";
	}
	
	public String editMaterialDetails() {
		return "/editMaterial.xhtml?faces-redirect=true";
	}

}
