package com.ctem.entity;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.session.APISession;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Shashank
 *
 */

@MappedSuperclass
public class BaseEntity implements Serializable {

	public static ThreadLocal<Long> currentUserId = new ThreadLocal<Long>();
	public static ThreadLocal<APISession> apiSession = new ThreadLocal<APISession>();
	public static ThreadLocal<APIClient> apiClient = new ThreadLocal<APIClient>();
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "persistenceid", updatable = false, nullable = false)
	private Long id;

	@JsonIgnore
	@CreatedDate
	private Calendar creationDate;
	@JsonIgnore
	@LastModifiedDate
	private Calendar updationDate;

	@JsonIgnore
	private Long createdUserId;
	@JsonIgnore
	private Long updatedUserId;
	@JsonIgnore
	@Version
	private Long version;
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean archived;

	/**
	 * @return the creationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true, updatable = false)
	public Calendar getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the updationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdationDate() {
		return updationDate;
	}

	/**
	 * @param updationDate the updationDate to set
	 */
	public void setUpdationDate(Calendar updationDate) {
		this.updationDate = updationDate;
	}

	/**
	 * @return the createdUserId
	 */
	@Column(insertable = true, updatable = false)
	public Long getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * @param createdUserId the createdUserId to set
	 */
	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * @return the updatedUserId
	 */
	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	/**
	 * @param updatedUserId the updatedUserId to set
	 */
	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	@PrePersist
	public void createAuditInfo() {
		setCreatedUserId((Long) BaseEntity.currentUserId.get());
		setCreationDate(Calendar.getInstance());
	}

	@PreUpdate
	public void updateAuditInfo() {
		setUpdatedUserId((Long) BaseEntity.currentUserId.get());
		setUpdationDate(Calendar.getInstance());
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}