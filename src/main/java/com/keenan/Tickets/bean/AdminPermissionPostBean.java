package com.keenan.Tickets.bean;

/**
 * @author keenan on 30/03/2018
 */
public class AdminPermissionPostBean {
    public Long permissionId;

    public Boolean isApprove;

    public AdminPermissionPostBean() {
    }

    public AdminPermissionPostBean(Long permissionId, Boolean isApprove) {
        this.permissionId = permissionId;
        this.isApprove = isApprove;
    }
}
