package com.keenan.Tickets.bean;

import java.util.List;

/**
 * @author keenan on 30/03/2018
 */
public class AdminVenueApproveBean {
    public List<AdminVenueRegisterApproveBean> registers;

    public List<AdminVenueModifyApproveBean> modifications;

    public AdminVenueApproveBean() {
    }

    public AdminVenueApproveBean(List<AdminVenueRegisterApproveBean> registers, List<AdminVenueModifyApproveBean> modifications) {
        this.registers = registers;
        this.modifications = modifications;
    }
}
