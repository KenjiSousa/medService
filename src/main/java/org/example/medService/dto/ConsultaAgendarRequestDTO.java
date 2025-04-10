package org.example.medService.dto;

import jakarta.xml.bind.annotation.*;
import org.example.medService.domain.Consulta;

import java.text.SimpleDateFormat;

@XmlRootElement
@XmlType(propOrder = { "idPaciente", "idMedico", "dataHora" })
public class ConsultaAgendarRequestDTO {
    private Integer idPaciente;
    private Integer idMedico;
    private String dataHora;

    @XmlElement(required = true)
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    @XmlElement(required = true)
    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    @XmlElement(required = true)
    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
