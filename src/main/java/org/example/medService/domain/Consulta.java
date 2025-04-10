package org.example.medService.domain;

import org.example.medService.dto.ConsultaAgendarRequestDTO;
import org.example.medService.dto.ConsultaCancelarRequestDTO;
import org.example.medService.enums.ConsultaMotivoCancto;
import org.example.medService.exceptions.BusinessException;

import java.sql.Timestamp;

public class Consulta {
    private Integer id;
    private Integer idPaciente;
    private Integer idMedico;
    private Timestamp dataHora;
    private ConsultaMotivoCancto cdMotivoCancto;
    private String dsMotivoCancto;

    public Consulta(Integer id, Integer idPaciente, Integer idMedico, Timestamp dataHora, ConsultaMotivoCancto cdMotivoCancto, String dsMotivoCancto) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dataHora = dataHora;
        this.cdMotivoCancto = cdMotivoCancto;
        this.dsMotivoCancto = dsMotivoCancto;
    }

    public Consulta(ConsultaCancelarRequestDTO consultaDTO) throws BusinessException {
        this.id = consultaDTO.getId();
        try {
            this.cdMotivoCancto = ConsultaMotivoCancto.valueOf(consultaDTO.getCdMotivoCancto());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Não existe motivo de cancelamento com este nome");
        }
        this.dsMotivoCancto = consultaDTO.getDsMotivoCancto();
    }

    public Consulta(ConsultaAgendarRequestDTO consultaDTO) throws BusinessException {
        this.idPaciente = consultaDTO.getIdPaciente();
        this.idMedico = consultaDTO.getIdMedico();

        if (!consultaDTO.getDataHora().matches("\\d{4}-\\d\\d-\\d\\d \\d\\d:\\d\\d")) {
            throw new BusinessException("Data/hora inválida. Utilize o formato \"YYYY-MM-DD HH:mm\"");
        }

        this.dataHora = Timestamp.valueOf(consultaDTO.getDataHora() + ":00");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public ConsultaMotivoCancto getCdMotivoCancto() {
        return cdMotivoCancto;
    }

    public void setCdMotivoCancto(ConsultaMotivoCancto cdMotivoCancto) {
        this.cdMotivoCancto = cdMotivoCancto;
    }

    public String getDsMotivoCancto() {
        return dsMotivoCancto;
    }

    public void setDsMotivoCancto(String dsMotivoCancto) {
        this.dsMotivoCancto = dsMotivoCancto;
    }
}
