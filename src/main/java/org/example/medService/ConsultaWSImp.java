package org.example.medService;

import jakarta.jws.WebService;
import org.example.medService.domain.Consulta;
import org.example.medService.dto.ConsultaAgendarRequestDTO;
import org.example.medService.dto.ConsultaAgendarResponseDTO;
import org.example.medService.dto.ConsultaCancelarRequestDTO;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.services.ConsultaService;

@WebService(serviceName = "consulta", endpointInterface = "org.example.medService.interfaces.ConsultaWS")
public class ConsultaWSImp implements org.example.medService.interfaces.ConsultaWS {
    @Override
    public ConsultaAgendarResponseDTO agendar(ConsultaAgendarRequestDTO consultaDTO) throws BusinessException {
        return new ConsultaAgendarResponseDTO(new ConsultaService().agendar(new Consulta(consultaDTO)));
    }

    @Override
    public void cancelar(ConsultaCancelarRequestDTO consultaDTO) throws BusinessException {
        new ConsultaService().cancelar(new Consulta(consultaDTO));
    }
}
