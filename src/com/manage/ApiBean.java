package com.manage;

import java.util.ArrayList;
import java.util.List;

import com.domain.Company;
import com.domain.Material;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ApiBean {
	
	private List<Company> companies = new ArrayList<Company>();
	private List<Material> materials = new ArrayList<Material>();
	private Material material = new Material();
	
	
	public List<Company> apiCompanies() {
		try {
			Client client = Client.create();
			
			WebResource webResource = client.resource("http://193.142.112.220:8337/companyList");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			
			if(response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			String jsonInString = response.getEntity(String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<Company> companies = mapper.readValue(jsonInString, new TypeReference<List<Company>>(){});
			this.companies = companies;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this.companies;
	}
	
	public List<Material> apiCompanyMaterials(Company company) {
		
		try {
			Client client = Client.create();
			
			WebResource webResource = client.resource("http://193.142.112.220:8337/materialList?companyID=" + company.getCompanyID().toString());
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			
			if(response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			System.out.println("ID: " + company.getCompanyID().toString());
			
			String jsonInString = response.getEntity(String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<Material> materials = mapper.readValue(jsonInString, new TypeReference<List<Material>>(){});
			this.materials = materials;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.materials;
	}
	
	public Material apiConsumeMaterial(Material material) {
		
		try {
			Client client = Client.create();
			
			WebResource webResource = client.resource("http://193.142.112.220:8337/materialDetails?ID=" + material.getID().toString());
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			
			if(response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			String jsonInString = response.getEntity(String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Material detailedMaterial = mapper.readValue(jsonInString, Material.class);
			this.material = detailedMaterial;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this.material;
			
		
	}
	

}
