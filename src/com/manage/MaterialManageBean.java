package com.manage;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.domain.*;

/**
 * Class responsible for managing materials
 * @author Adam
 *
 */
@ManagedBean
@SessionScoped
public class MaterialManageBean {
	
	private ArrayList<Material> materials = new ArrayList<Material>();
	private ArrayList<Company> companies = new ArrayList<Company>();
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

	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
	}
	
	public NavigationBean getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}
	
	
	/**
	 * Gets materials from Api or session
	 * @param company
	 * @return link to company materials page
	 */
	public String consumeCompanyMaterials(Company company) {
		FacesContext context = FacesContext.getCurrentInstance();
		this.materials = new ArrayList<Material>();
		this.materials = (ArrayList<Material>) context.getExternalContext().getSessionMap().get("materials" + company.getCompanyID().toString());
		
		if(this.materials == null)
			this.materials = apiBean.apiCompanyMaterials(company);
		this.company = company;
		return navigation.goToCompanyMaterials();
	}
	
	/**
	 * Gets material details from Api or session
	 * @param material
	 * @return link to material details page
	 */
	public String consumeMaterial(Material material) {
		FacesContext context = FacesContext.getCurrentInstance();
		List<Material> sessionMaterials = new ArrayList<Material>();
		sessionMaterials = (List<Material>) context.getExternalContext().getSessionMap().get("materials" + material.getCompanyID().toString());

		if(sessionMaterials == null)
			material = apiBean.apiConsumeMaterial(material);
		else
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
	
	/**
	 * Checks if company materials are currently stored in session
	 * @param company
	 * @return true if materials are stored, false if ther aren't
	 */
	private boolean checkSessionMaterialsForCompany(Company company) {
		List<Material> sessionMaterials = new ArrayList<Material>();
		FacesContext context = FacesContext.getCurrentInstance();
		sessionMaterials = (ArrayList<Material>) context.getExternalContext().getSessionMap().get("materials" + company.getCompanyID().toString());
		if(sessionMaterials != null)
			return true;
		return false;
	}
	
	/**
	 * Adds company materials to session
	 * @param company
	 */
	private void addCompanySessionMaterials(Company company) {
		FacesContext context = FacesContext.getCurrentInstance();
		ArrayList<Material> materials = apiBean.apiCompanyMaterials(company);
		context.getExternalContext().getSessionMap().put("materials" + company.getCompanyID().toString(), materials);
	}
	
	/**
	 * Adds company material to company materials currently stored in session
	 * @param company
	 * @param material
	 */
	private void addMaterialToCompanySessionMaterials(Company company, Material material) {
		List<Material> sessionMaterials = new ArrayList<Material>();
		FacesContext context = FacesContext.getCurrentInstance();
		sessionMaterials = (ArrayList<Material>) context.getExternalContext().getSessionMap().get("materials" + company.getCompanyID().toString());
		material.setCompanyID(company.getCompanyID());
		sessionMaterials.add(material);
		context.getExternalContext().getSessionMap().put("materials" + company.getCompanyID().toString(), sessionMaterials);
	}
	
	/**
	 * Removes material from company materials stored in session
	 * @param company
	 * @param material
	 */
	private void removeMaterialFromCompanySessionMaterials(Company company, Material material) {
		List<Material> sessionMaterials = new ArrayList<Material>();
		FacesContext context = FacesContext.getCurrentInstance();
		sessionMaterials = (ArrayList<Material>) context.getExternalContext().getSessionMap().get("materials" + company.getCompanyID().toString());
		for(int i = 0; i < sessionMaterials.size(); i++) {
			if(sessionMaterials.get(i).getID() == material.getID()) {
				sessionMaterials.remove(sessionMaterials.get(i));
			}	
		}
		for(int i = 0; i < this.materials.size(); i++) {
			if(this.materials.get(i).getID() == material.getID()) {
				this.materials.remove(this.materials.get(i));
			}
		}
		context.getExternalContext().getSessionMap().put("materials" + company.getCompanyID().toString(), sessionMaterials);
	}
	
	/**
	 * Gets company by name
	 * @param name
	 * @return company
	 */
	private Company getCompanyByName(String name) {
		this.companies = apiBean.apiCompanies();
		Company company = new Company();
		for(int i = 0; i < companies.size(); i++) {
			if(companies.get(i).getCompanyName().equals(name))
				company = companies.get(i);
		}
		return company;
	}
	
	/**
	 * Saves edited material in session storage
	 */
	private void saveMaterialDetailsLocally() {
		FacesContext context = FacesContext.getCurrentInstance();
		List<Material> sessionMaterials = new ArrayList<Material>();
		
		for(int i = 0; i < materials.size(); i++) {
			if(materials.get(i).getID() == material.getID()) {
				this.material.setModified(true);
				
				if(!this.company.getCompanyName().equals(this.material.getSupplier())) {
					Company company = getCompanyByName(this.material.getSupplier());
					
					if(checkSessionMaterialsForCompany(company)) {
						
						this.material.setCompanyID(company.getCompanyID());
						addMaterialToCompanySessionMaterials(company, this.material);
						
						if(checkSessionMaterialsForCompany(this.company)) {
							removeMaterialFromCompanySessionMaterials(this.company, material);
						}
						else {
							addCompanySessionMaterials(this.company);
							addMaterialToCompanySessionMaterials(this.company, this.material);
						}
					}
					else {
						addCompanySessionMaterials(company);
						addMaterialToCompanySessionMaterials(company, this.material);
						
						if(checkSessionMaterialsForCompany(this.company)) {
							removeMaterialFromCompanySessionMaterials(this.company, this.material);
						}
						else {
							addCompanySessionMaterials(this.company);
							removeMaterialFromCompanySessionMaterials(this.company, this.material);
						}
					}
				}
				else {
					this.material.setCompanyID(this.company.getCompanyID());
					this.materials.set(i, material);
					
					context.getExternalContext().getSessionMap().put("materials" + this.company.getCompanyID().toString(), materials);
				}
				
			}
		}	
	}
	
	

}
