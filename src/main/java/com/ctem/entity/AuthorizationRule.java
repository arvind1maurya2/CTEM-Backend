/**
 * 
 */
package com.ctem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ARVIND MAURYA
 *
 */
@Entity
@Table(name = "authorization_rule")
@NamedQueries({
	@NamedQuery(name = "AuthorizationRule.findAllParentMenus", query = "SELECT sbm FROM AuthorizationRule sbm WHERE sbm.parent = true order by sbm.sortOrder"),
	@NamedQuery(name = "AuthorizationRule.findAllChildMenus", query = "SELECT sbm FROM AuthorizationRule sbm WHERE sbm.parentId = :parentId order by sbm.sortOrder")

})
public class AuthorizationRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(updatable = true, nullable = false)
	private String name;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private boolean menu;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private boolean parent;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private Long parentId;
	
	@Column(updatable = true, nullable = false)
	private String route;
	
	@Column(updatable = true, nullable = true)
	private String icon;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int sortOrder;
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
	private boolean enable;
	
	@Transient
	private List<AuthorizationRule> submenus;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the menu
	 */
	public boolean isMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	/**
	 * @return the parent
	 */
	public boolean isParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(boolean parent) {
		this.parent = parent;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the submenus
	 */
	public List<AuthorizationRule> getSubmenus() {
		return submenus;
	}

	/**
	 * @param submenus the submenus to set
	 */
	public void setSubmenus(List<AuthorizationRule> submenus) {
		this.submenus = submenus;
	}


}
