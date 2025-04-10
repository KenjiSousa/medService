package org.example.medService.interfaces;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.example.medService.domain.Paciente;
import org.example.medService.dto.PacienteInsertRequestDTO;
import org.example.medService.dto.PacienteUpdateRequestDTO;
import org.example.medService.exceptions.BusinessException;

import java.util.ArrayList;

@WebService
public interface PacienteWS {
    @WebMethod(action = "inserir")
    @XmlElement(name = "paciente")
    Paciente inserir(@XmlElement(required = true) @WebParam(name = "paciente") PacienteInsertRequestDTO pacienteDTO) throws BusinessException;

    @WebMethod(action = "listar")
    @XmlElement(name = "paciente")
    ArrayList<Paciente> listar() throws BusinessException;

    @WebMethod(action = "atualizar")
    @XmlElement(name = "paciente")
    Paciente atualizar(@XmlElement(required = true) @WebParam(name = "paciente") PacienteUpdateRequestDTO pacienteDTO) throws BusinessException;

    @WebMethod(action = "excluir")
    void excluir(@XmlElement(required = true) @WebParam(name = "id") Integer id) throws BusinessException;
}
