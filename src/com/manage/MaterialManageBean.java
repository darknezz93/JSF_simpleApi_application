package com.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import com.domain.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
public class MaterialManageBean {
	
	private List<Material> materials = new ArrayList<Material>();
	private Material material = new Material();
	private NavigationBean navigation = new NavigationBean();
	private ApiBean apiBean = new ApiBean();
	private Company company = new Company();

	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
	
	public NavigationBean getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}
	
	
	public String consumeCompanyMaterials(Company company) {
		FacesContext context = FacesContext.getCurrentInstance();
		this.materials = new ArrayList<Material>();
		this.materials = (List<Material>) context.getExternalContext().getSessionMap().get("materials" + company.getCompanyID().toString());
		
		if(this.materials == null)
			this.materials = apiBean.apiCompanyMaterials(company);
		this.company = company;
		return navigation.goToCompanyMaterials();
	}
	
	public String consumeMaterial(Material material) {
		FacesContext context = FacesContext.getCurrentInstance();
		List<Material> sessionMaterials = new ArrayList<Material>();
		
		if(context.getExternalContext().getSessionMap().get("materials" + material.getCompanyID().toString()) == null)
			material = apiBean.apiConsumeMaterial(material);
		else
			sessionMaterials = (List<Material>) context.getExternalContext().getSessionMap().get("materials" + material.getCompanyID().toString());
			for(int i = 0 ; i < sessionMaterials.size(); i++) {
				if(sessionMaterials.get(i).getID() == material.getID())
					if(material.isModified()) {
						material = sessionMaterials.get(i);
					} else {
						material = apiBean.apiConsumeMaterial(material);
					}
				
					
			}
		this.material = material;	
			
		return navigation.goToMaterialDetails();
	}
	
	public String showMaterialEditPage() {
		return navigation.goToEditMaterialDetails();
	}
	
	public String showMaterialDetailsPage() {
		return navigation.goToMaterialDetails();
	}
	
	public String saveAndShowMaterialDetails() {
		saveMaterialDetailsLocally();
		return navigation.goToMaterialDetails();
	}
	
	private void saveMaterialDetailsLocally() {
		for(int i = 0; i < materials.size(); i++) {
			if(materials.get(i).getID() == material.getID()) {
				this.material.setModified(true);
				this.material.setCompanyID(this.company.getCompanyID());
				materials.set(i, material);
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().put("materials" + this.company.getCompanyID().toString(), materials);
			}
		}	
	}
	
	
	
	

	
	

}
