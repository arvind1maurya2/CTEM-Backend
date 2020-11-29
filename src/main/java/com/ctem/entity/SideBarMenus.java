package com.ctem.entity;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = "SideBarMenus.findAllParentMenus", query = "SELECT sbm FROM SideBarMenus sbm WHERE sbm.parentMenu = true AND sbm.archived = 'false'"),
	@NamedQuery(name = "SideBarMenus.findAllChildMenus", query = "SELECT sbm FROM SideBarMenus sbm WHERE sbm.parentMenuId = :parentId AND sbm.archived = 'false'")

})
public class SideBarMenus extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274411679935742850L;
	
	private String menuName;
	private Long priority;
	private String menuIcon;
	@Column(columnDefinition = "boolean default false")
	private boolean parentMenu;
	private Long parentMenuId;
	private String route;
	
	@Transient
	private List<SideBarMenus> submenus;
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public boolean isParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(boolean parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	public Long getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public List<SideBarMenus> getSubmenus() {
		return submenus;
	}
	public void setSubmenus(List<SideBarMenus> submenus) {
		this.submenus = submenus;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	
	
	

}
