/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.XmlSerializer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "SectorRelation")
@XmlAccessorType(XmlAccessType.NONE)
public class SectorTransferConfig extends XmlSerializer {

    @XmlElement(name = "Sector")
    private List<SectorTransferItem> sectorTransferList;

    public SectorTransferConfig() {
        sectorTransferList = new ArrayList<>();
    }
    
    public static void main(String [] args) {
        SectorTransferConfig sectorTransferConfig = new SectorTransferConfig();
        SectorTransferItem item = new SectorTransferItem();
        item.setName("Sector01");
        item.getSectors().add("Sector2");
        item.getSectors().add("Sector3");
        sectorTransferConfig.getSectorTransferList().add(item);
        sectorTransferConfig.save("sector-transfer.xml");
        System.out.println("Print OK");
    }

    public List<SectorTransferItem> getSectorTransferList() {
        return sectorTransferList;
    }

    public void setSectorTransferList(List<SectorTransferItem> sectorTransferList) {
        this.sectorTransferList = sectorTransferList;
    }

}
