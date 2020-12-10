/**
 * 
 */
package com.ctem.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<AuthorizationRule> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<AuthorizationRule> submenus) {
		this.submenus = submenus;
	}
}
