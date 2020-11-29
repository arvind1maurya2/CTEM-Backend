package com.ctem.util;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ctem.entity.RoleAllotedPermission;
import com.ctem.entity.SideBarMenus;

/**
 * @author Shashank
 *
 */
public class SidebarContentUtil {
	
	
	@SuppressWarnings("unchecked")
	public static List<SideBarMenus> checkChildMenuPermission(List<RoleAllotedPermission> userPermissions, SideBarMenus parentMenu, EntityManager entityManager) {
		List<SideBarMenus> allotedMenus = new ArrayList<SideBarMenus>();
		List<SideBarMenus> submenus = entityManager.createNamedQuery("SideBarMenus.findAllChildMenus").setParameter("parentId", parentMenu.getId()).getResultList();
		if(!CollectionUtils.isEmpty(submenus)) {
			for(SideBarMenus submenu : submenus) {
				if(!CollectionUtils.isEmpty(userPermissions)) {
					for(RoleAllotedPermission permission : userPermissions) {
						if(StringUtils.equalsIgnoreCase(StringUtils.replace(submenu.getMenuName(), "-", ""), StringUtils.replace(permission.getModuleName(),"_",""))) {
							if(!checkSubmenuAlreadyExist(allotedMenus,submenu)) {
								allotedMenus.add(submenu);
							}
						}
					}
				}
			}
		}
		return allotedMenus;
	}
	
	private static boolean checkSubmenuAlreadyExist(List<SideBarMenus> submenus, SideBarMenus smenu) {
		boolean exist = false;
		for(SideBarMenus submenu : submenus) {
			if(StringUtils.equals(submenu.getMenuName(), smenu.getMenuName())) {
				exist = true;
			}
		}
		return exist;
	}
}