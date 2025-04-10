package org.example.medService.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.medService.domain.Consulta;

import java.text.SimpleDateFormat;

@XmlRootElement
@XmlType(propOrder = { "id", "idPaciente", "idMedico", "dataHora" })
public class ConsultaAgendarResponseDTO {
    private Integer id;
    private Integer idPaciente;
    private Integer idMedico;
    private String dataHora;

    // Utilizado para construção do serviço
    public ConsultaAgendarResponseDTO() {}

    public ConsultaAgendarResponseDTO(Consulta consulta) {
        this.id = consulta.getId();
        this.idPaciente = consulta.getIdPaciente();
        this.idMedico = consulta.getIdMedico();
        this.dataHora = new SimpleDateFormat("yyyy-dd-MM HH:mm").format(consulta.getDataHora());
    }

    @XmlElement(required = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
