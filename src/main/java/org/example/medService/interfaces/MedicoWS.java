package org.example.medService.interfaces;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.example.medService.domain.Medico;
import org.example.medService.dto.MedicoInsertRequestDTO;
import org.example.medService.dto.MedicoUpdateRequestDTO;
import org.example.medService.exceptions.BusinessException;

import java.util.ArrayList;

@WebService
public interface MedicoWS {
    @WebMethod(action = "inserir")
    @XmlElement(name = "medico")
    Medico inserir(@XmlElement(required = true) @WebParam(name = "medico") MedicoInsertRequestDTO medicoDTO) throws BusinessException;

    @WebMethod(action = "listar")
    @XmlElement(name = "medico")
    ArrayList<Medico> listar() throws BusinessException;

    @WebMethod(action = "atualizar")
    @XmlElement(name = "medico")
    Medico atualizar(@XmlElement(required = true) @WebParam(name = "medico") MedicoUpdateRequestDTO medicoDTO) throws BusinessException;

    @WebMethod(action = "excluir")
    void excluir(@XmlElement(required = true) @WebParam(name = "id") Integer id) throws BusinessException;
}
