package org.example.medService.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "cdMotivoCancto", "dsMotivoCancto" })
public class ConsultaCancelarRequestDTO {
    private Integer id;
    private String cdMotivoCancto;
    private String dsMotivoCancto;

    @XmlElement(required = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(required = true)
    public String getCdMotivoCancto() {
        return cdMotivoCancto;
    }

    public void setCdMotivoCancto(String cdMotivoCancto) {
        this.cdMotivoCancto = cdMotivoCancto;
    }

    @XmlElement(required = true)
    public String getDsMotivoCancto() {
        return dsMotivoCancto;
    }

    public void setDsMotivoCancto(String dsMotivoCancto) {
        this.dsMotivoCancto = dsMotivoCancto;
    }
}
