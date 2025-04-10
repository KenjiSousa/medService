package org.example.medService.interfaces;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import org.example.medService.dto.ConsultaAgendarRequestDTO;
import org.example.medService.dto.ConsultaAgendarResponseDTO;
import org.example.medService.dto.ConsultaCancelarRequestDTO;
import org.example.medService.exceptions.BusinessException;

@WebService
public interface ConsultaWS {
    @WebMethod(action = "agendar")
    @XmlElement(name = "consulta")
    ConsultaAgendarResponseDTO agendar(@XmlElement(required = true) @WebParam(name = "consulta") ConsultaAgendarRequestDTO consultaDTO) throws BusinessException;

    @WebMethod(action = "cancelar")
    @XmlElement(name = "consulta")
    void cancelar(@XmlElement(required = true) @WebParam(name = "consulta") ConsultaCancelarRequestDTO consultaDTO) throws BusinessException;
}
