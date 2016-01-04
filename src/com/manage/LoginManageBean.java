package com.manage;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Class responsible for loging and logout user
 * @author Adam
 *
 */
@ManagedBean
@SessionScoped
public class LoginManageBean implements Serializable {
	
	private static final long serialVersionUID = 1094801825228386363L;
	
	private String userName;
	private String password;
	private NavigationBean navigation = new NavigationBean();
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Login user and redirects to companies page
	 * @return link to proper page
	 */
	public String loginUser() {
		boolean result = validateUsernamePassword(this.userName, this.password);
		if(result) {
			HttpSession session = SessionBean.getSession();
            session.setAttribute("userName", this.userName);
			return navigation.goToCompanies();
		}
		else {
			addMessage("Login error", "Incorrect Username or Password.");
			return navigation.goToLogin();
		}
	}
	
	/**
	 * Logout user and redirects to login page
	 * @return link to login page
	 */
	public String logoutUser() {
		 HttpSession session = SessionBean.getSession();
	     session.invalidate();
	     addMessage("Success", "Logged out.");
	     return navigation.goToLogin();
	}
	
	/**
	 * Validates userName and password
	 * @param userName
	 * @param password
	 * @return true if password and userName are correct, false otherwise
	 */
	private boolean validateUsernamePassword(String userName, String password) {
		if(userName.equals("root") && password.equals("password"))
			return true;
		return false;
	}
	
    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	

}
