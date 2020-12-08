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
public class AuthorizationRule {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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
	@Column(updatable = true, nullable = true)
	private Calendar creationDate;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int createdBy;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private Calendar lastUpdateDate;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int last_updated_by;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
	private boolean active;
	
	@Transient
	private List<AuthorizationRule> submenus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(int last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<AuthorizationRule> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<AuthorizationRule> submenus) {
		this.submenus = submenus;
	}

	
}
