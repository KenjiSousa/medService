package org.example.medService;

import jakarta.jws.WebService;
import org.example.medService.domain.Paciente;
import org.example.medService.dto.PacienteInsertRequestDTO;
import org.example.medService.dto.PacienteUpdateRequestDTO;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.services.PacienteService;

import java.util.ArrayList;

@WebService(serviceName = "paciente", endpointInterface = "org.example.medService.interfaces.PacienteWS")
public class PacienteWSImp implements org.example.medService.interfaces.PacienteWS {
    @Override
    public Paciente inserir(PacienteInsertRequestDTO pacienteDTO) throws BusinessException {
        return new PacienteService().inserir(new Paciente(pacienteDTO));
    }

    @Override
    public ArrayList<Paciente> listar() throws BusinessException {
        return new PacienteService().listar();
    }

    @Override
    public Paciente atualizar(PacienteUpdateRequestDTO pacienteDTO) throws BusinessException {
        return new PacienteService().atualizar(new Paciente(pacienteDTO));
    }

    @Override
    public void excluir(Integer id) throws BusinessException {
        new PacienteService().excluir(id);
    }
}
