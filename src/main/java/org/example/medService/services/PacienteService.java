package org.example.medService.services;

import org.example.medService.domain.Paciente;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.repositories.PacienteRepository;

import java.util.ArrayList;

import static org.junit.platform.commons.util.StringUtils.isBlank;

public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService() {
        pacienteRepository = new PacienteRepository();
    }

    private void validarPaciente(Paciente paciente) throws BusinessException {
        if (isBlank(paciente.getNome())) {
            throw new BusinessException("Nome do paciente é obrigatório");
        } else if (paciente.getNome().length() > 100) {
            throw new BusinessException("Nome do paciente deve ter no máximo 100 caracteres");
        } else if (isBlank(paciente.getEmail())) {
            throw new BusinessException("E-mail do paciente é obrigatório");
        } else if (paciente.getEmail().length() > 100) {
            throw new BusinessException("E-mail do paciente deve ter no máximo 100 caracteres");
        } else if (!paciente.getEmail().matches( // https://stackoverflow.com/a/201378
                "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])$")) {
            throw new BusinessException("E-mail do paciente é inválido");
        } else if (isBlank(paciente.getTelefone())) {
            throw new BusinessException("Telefone do paciente é obrigatório");
        } else if (paciente.getTelefone().length() > 30) {
            throw new BusinessException("Telefone do paciente deve ter no máximo 30 caracteres");
        } else if (isBlank(paciente.getCpf())) {
            throw new BusinessException("CPF do paciente é obrigatório");
        } else if (paciente.getCpf().length() != 11) {
            throw new BusinessException("CPF do paciente deve ter 11 caracteres");
        } else if (isBlank(paciente.getLogradouro())) {
            throw new BusinessException("Logradouro do endereço do paciente é obrigatório");
        } else if (paciente.getLogradouro().length() > 100) {
            throw new BusinessException("Logradouro do endereço do paciente deve ter no máximo 100 caracteres");
        } else if (paciente.getNumero().length() > 30) {
            throw new BusinessException("Número do endereço do paciente deve ter no máximo 30 caracteres");
        } else if (paciente.getComplemento().length() > 100) {
            throw new BusinessException("Complemento do endereço do paciente deve ter no máximo 100 caracteres");
        } else if (isBlank(paciente.getBairro())) {
            throw new BusinessException("Bairro do endereço do paciente é obrigatório");
        } else if (paciente.getBairro().length() > 100) {
            throw new BusinessException("Bairro do endereço do paciente deve ter no máximo 100 caracteres");
        } else if (isBlank(paciente.getCidade())) {
            throw new BusinessException("Cidade do endereço do paciente é obrigatório");
        } else if (paciente.getCidade().length() > 100) {
            throw new BusinessException("Cidade do endereço do paciente deve ter no máximo 100 caracteres");
        } else if (isBlank(paciente.getUf())) {
            throw new BusinessException("UF do endereço do paciente é obrigatório");
        } else if (paciente.getUf().length() > 2) {
            throw new BusinessException("UF do endereço do paciente deve ter no máximo 2 caracteres");
        } else if (isBlank(paciente.getCep())) {
            throw new BusinessException("CEP do endereço do paciente é obrigatório");
        } else if (paciente.getCep().length() > 20) {
            throw new BusinessException("CEP do endereço do paciente deve ter no máximo 20 caracteres");
        }
    }

    public Paciente inserir(Paciente paciente) throws BusinessException {
        validarPaciente(paciente);

        try {
            return pacienteRepository.inserir(paciente);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao inserir registro do paciente");
        }
    }

    public ArrayList<Paciente> listar() throws BusinessException {
        try {
            return pacienteRepository.listar();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao listar pacientes");
        }
    }

    public Paciente atualizar(Paciente paciente) throws BusinessException {
        validarPaciente(paciente);

        if (paciente.getId() == null) {
            throw new BusinessException("Id do paciente é obrigatório");
        }

        Paciente pacienteOld;

        try {
            pacienteOld = pacienteRepository.selectById(paciente.getId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar registro do paciente a ser atualizado");
        }

        if (pacienteOld == null) {
            throw new BusinessException("Não foi encontrado paciente para o id especificado");
        } else if (!pacienteOld.getEmail().equals(paciente.getEmail())) {
            throw new BusinessException("E-mail do paciente não pode ser alterado");
        } else if (!pacienteOld.getCpf().equals(paciente.getCpf())) {
            throw new BusinessException("CPF do paciente não pode ser alterado");
        }

        try {
            return pacienteRepository.atualizar(paciente);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao atualizar registro do paciente");
        }
    }

    public void excluir(Integer id) throws BusinessException {
        Paciente pacienteOld;

        try {
            pacienteOld = pacienteRepository.selectById(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar registro do paciente a ser atualizado");
        }

        if (pacienteOld == null) {
            throw new BusinessException("Não foi encontrado paciente para o id especificado");
        } else if (!pacienteOld.getAtivo()) {
            throw new BusinessException("O paciente especificado já foi excluído");
        }

        try {
            pacienteRepository.excluir(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao excluir registro do paciente");
        }
    }
}
