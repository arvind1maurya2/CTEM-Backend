package com.ctem.entity;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Arvind Maurya
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "UserType.getAllActiveUserTypes", query = "SELECT ut fROM UserType ut WHERE ut.archived='false' AND ut.id not in ('1') ORDER BY creationDate DESC"),
})
public class UserType extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 333669255422007179L;
	private String typeName;
	private String displayName;
	private boolean systemUserType;
	private int priority;
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the systemUserType
	 */
	public boolean isSystemUserType() {
		return systemUserType;
	}
	/**
	 * @param systemUserType the systemUserType to set
	 */
	public void setSystemUserType(boolean systemUserType) {
		this.systemUserType = systemUserType;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}
