package org.example.medService;

import jakarta.jws.WebService;
import org.example.medService.domain.Medico;
import org.example.medService.dto.MedicoInsertRequestDTO;
import org.example.medService.dto.MedicoUpdateRequestDTO;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.services.MedicoService;

import java.util.ArrayList;

@WebService(serviceName = "medico", endpointInterface = "org.example.medService.interfaces.MedicoWS")
public class MedicoWSImp implements org.example.medService.interfaces.MedicoWS {
    @Override
    public Medico inserir(MedicoInsertRequestDTO medicoDTO) throws BusinessException {
        return new MedicoService().inserir(new Medico(medicoDTO));
    }

    @Override
    public ArrayList<Medico> listar() throws BusinessException {
        return new MedicoService().listar();
    }

    @Override
    public Medico atualizar(MedicoUpdateRequestDTO medicoDTO) throws BusinessException {
        return new MedicoService().atualizar(new Medico(medicoDTO));
    }

    @Override
    public void excluir(Integer id) throws BusinessException {
        new MedicoService().excluir(id);
    }
}
