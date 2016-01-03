package com.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
		this.materials = apiBean.apiCompanyMaterials(company);
		return navigation.goToCompanyMaterials();
	}
	
	public String consumeMaterial(Material material) {
		this.material = apiBean.apiConsumeMaterial(material);
		return navigation.goToMaterialDetails();
	}
	
	public String showMaterialEditPage() {
		return navigation.editMaterialDetails();
	}
	
	public String showMaterialDetails() {
		return navigation.goToMaterialDetails();
	}
	
	
	
	

	
	

}
