package org.example.medService.services;

import org.example.medService.domain.Medico;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.repositories.MedicoRepository;

import java.util.ArrayList;

import static org.junit.platform.commons.util.StringUtils.isBlank;

public class MedicoService {
    private final MedicoRepository medicoRepository;

    public MedicoService() {
        medicoRepository = new MedicoRepository();
    }

    private void validarMedico(Medico medico) throws BusinessException {
        if (isBlank(medico.getNome())) {
            throw new BusinessException("Nome do médico é obrigatório");
        } else if (medico.getNome().length() > 100) {
            throw new BusinessException("Nome do médico deve ter no máximo 100 caracteres");
        } else if (isBlank(medico.getEmail())) {
            throw new BusinessException("E-mail do médico é obrigatório");
        } else if (medico.getEmail().length() > 100) {
            throw new BusinessException("E-mail do médico deve ter no máximo 100 caracteres");
        } else if (!medico.getEmail().matches( // https://stackoverflow.com/a/201378
                "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])$")) {
            throw new BusinessException("E-mail do médico é inválido");
        } else if (isBlank(medico.getTelefone())) {
            throw new BusinessException("Telefone do médico é obrigatório");
        } else if (medico.getTelefone().length() > 30) {
            throw new BusinessException("Telefone do médico deve ter no máximo 30 caracteres");
        } else if (isBlank(medico.getCrm())) {
            throw new BusinessException("CRM do médico é obrigatório");
        } else if (medico.getCrm().length() > 30) {
            throw new BusinessException("CRM do médico deve ter no máximo 30 caracteres");
        } else if (medico.getEspec() == null) {
            throw new BusinessException("Especificação do médico é obrigatório");
        } else if (isBlank(medico.getLogradouro())) {
            throw new BusinessException("Logradouro do endereço do médico é obrigatório");
        } else if (medico.getLogradouro().length() > 100) {
            throw new BusinessException("Logradouro do endereço do médico deve ter no máximo 100 caracteres");
        } else if (medico.getNumero().length() > 30) {
            throw new BusinessException("Número do endereço do médico deve ter no máximo 30 caracteres");
        } else if (medico.getComplemento().length() > 100) {
            throw new BusinessException("Complemento do endereço do médico deve ter no máximo 100 caracteres");
        } else if (isBlank(medico.getBairro())) {
            throw new BusinessException("Bairro do endereço do médico é obrigatório");
        } else if (medico.getBairro().length() > 100) {
            throw new BusinessException("Bairro do endereço do médico deve ter no máximo 100 caracteres");
        } else if (isBlank(medico.getCidade())) {
            throw new BusinessException("Cidade do endereço do médico é obrigatório");
        } else if (medico.getCidade().length() > 100) {
            throw new BusinessException("Cidade do endereço do médico deve ter no máximo 100 caracteres");
        } else if (isBlank(medico.getUf())) {
            throw new BusinessException("UF do endereço do médico é obrigatório");
        } else if (medico.getUf().length() > 2) {
            throw new BusinessException("UF do endereço do médico deve ter no máximo 2 caracteres");
        } else if (isBlank(medico.getCep())) {
            throw new BusinessException("CEP do endereço do médico é obrigatório");
        } else if (medico.getCep().length() > 20) {
            throw new BusinessException("CEP do endereço do médico deve ter no máximo 20 caracteres");
        }
    }

    public Medico inserir(Medico medico) throws BusinessException {
        validarMedico(medico);

        try {
            return medicoRepository.inserir(medico);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao inserir registro do médico");
        }
    }

    public ArrayList<Medico> listar() throws BusinessException {
        try {
            return medicoRepository.listar();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao listar médicos");
        }
    }

    public Medico atualizar(Medico medico) throws BusinessException {
        validarMedico(medico);

        if (medico.getId() == null) {
            throw new BusinessException("Id do médico é obrigatório");
        }

        Medico medicoOld;

        try {
            medicoOld = medicoRepository.selectById(medico.getId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar registro do médico a ser atualizado");
        }

        if (medicoOld == null) {
            throw new BusinessException("Não foi encontrado médico para o id especificado");
        } else if (!medicoOld.getEmail().equals(medico.getEmail())) {
            throw new BusinessException("E-mail do médico não pode ser alterado");
        } else if (!medicoOld.getCrm().equals(medico.getCrm())) {
            throw new BusinessException("CRM do médico não pode ser alterado");
        } else if (!medicoOld.getEspec().equals(medico.getEspec())) {
            throw new BusinessException("Especialidade do médico não pode ser alterada");
        }

        try {
            return medicoRepository.atualizar(medico);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao atualizar registro do médico");
        }
    }

    public void excluir(Integer id) throws BusinessException {
        Medico medicoOld;

        try {
            medicoOld = medicoRepository.selectById(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar registro do médico a ser atualizado");
        }

        if (medicoOld == null) {
            throw new BusinessException("Não foi encontrado médico para o id especificado");
        } else if (!medicoOld.getAtivo()) {
            throw new BusinessException("O médico especificado já foi excluído");
        }

        try {
            medicoRepository.excluir(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao excluir registro do médico");
        }
    }
}
