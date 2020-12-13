/**
 * 
 */
package com.ctem.service;

import java.util.Map;

import com.ctem.payload.OfficeDetails;

/**
 * @author Arvind Maurya
 *
 */
public interface OfficeService {
	
	@SuppressWarnings("rawtypes")
	public Map createOffice(OfficeDetails officeDetails);
	
	@SuppressWarnings("rawtypes")
	public Map getOfficeById(Long id);
	
	@SuppressWarnings("rawtypes")
	public Map officesList();
	
	@SuppressWarnings("rawtypes")
	public Map updateOfficeById(OfficeDetails officeDetails);

}
