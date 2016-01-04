package com.domain;

import java.util.List;
import java.util.ArrayList;
import com.domain.Material;

/**
 * Company domain class
 * @author Adam
 *
 */
public class Company {
	
	private Integer companyID;
	private String companyName;
	
	private List<Material> materials = new ArrayList<Material>();

	public Integer getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
	}
	
	

}
