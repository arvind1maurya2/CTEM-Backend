package com.ctem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ctem.entity.BaseEntity;
import com.ctem.entity.RoleAllotedPermission;
import com.ctem.entity.SideBarMenus;
import com.ctem.entity.User;
import com.ctem.service.SideBarMenuService;
import com.ctem.util.SidebarContentUtil;

@Service
public class SideBarMenuServiceImpl implements SideBarMenuService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	private List<SideBarMenus> listAllMenuForAdmin() {
		List<SideBarMenus> parentMenus = entityManager.createNamedQuery("SideBarMenus.findAllParentMenus")
				.getResultList();
		if (parentMenus != null) {
			for (SideBarMenus parentMenu : parentMenus) {
				parentMenu.setSubmenus(entityManager.createNamedQuery("SideBarMenus.findAllChildMenus")
						.setParameter("parentId", parentMenu.getId()).getResultList());
			}
		}
		return parentMenus;
	}

	@SuppressWarnings("unchecked")
	private List<SideBarMenus> listMenuByRole() {
		List<SideBarMenus> allotedMenus = new ArrayList<SideBarMenus>();
		User user = entityManager.find(User.class, BaseEntity.currentUserId.get());
		List<RoleAllotedPermission> allPermissions = entityManager
				.createNamedQuery("RoleAllotedPermission.findAllAllotedPermissionByRole")
				.setParameter("roleId", user.getRole().getId()).getResultList();
		List<SideBarMenus> allParentMenus = entityManager.createNamedQuery("SideBarMenus.findAllParentMenus")
				.getResultList();
		if (!CollectionUtils.isEmpty(allParentMenus)) {
			for (SideBarMenus parentMenu : allParentMenus) {
				List<SideBarMenus> allChildMenus = SidebarContentUtil.checkChildMenuPermission(allPermissions,
						parentMenu, entityManager);
				if (!CollectionUtils.isEmpty(allChildMenus)) {
					parentMenu.setSubmenus(allChildMenus);
					allotedMenus.add(parentMenu);
				}
			}
		}
		return allotedMenus;
	}

	@Override
	public List<SideBarMenus> getSidebarContent() {
		User user = entityManager.find(User.class, BaseEntity.currentUserId.get());
		if (user.getRole().getId() == 1L) {
			return listAllMenuForAdmin();
		}
		return listMenuByRole();
	}

}