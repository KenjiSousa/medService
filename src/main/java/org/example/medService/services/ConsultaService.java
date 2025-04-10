package org.example.medService.services;

import org.example.medService.domain.Consulta;
import org.example.medService.dto.DadosValidacaoConsultaAgendarDTO;
import org.example.medService.enums.ConsultaMotivoCancto;
import org.example.medService.exceptions.BusinessException;
import org.example.medService.repositories.ConsultaRepository;
import org.example.medService.repositories.ValidacaoRepository;

import static org.junit.platform.commons.util.StringUtils.isBlank;

public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final ValidacaoRepository validacaoRepository;

    public ConsultaService() {
        consultaRepository = new ConsultaRepository();
        validacaoRepository = new ValidacaoRepository();
    }

    public Consulta agendar(Consulta consulta) throws BusinessException {
        if (consulta.getIdPaciente() == null) {
            throw new BusinessException("Paciente é obrigatório");
        } else if (consulta.getIdMedico() == null) {
            throw new BusinessException("Médico é obrigatório");
        } else if (consulta.getDataHora() == null) {
            throw new BusinessException("Data/hora da consulta é obrigatório");
        }

        DadosValidacaoConsultaAgendarDTO dadosValidacaoConsultaAgendarDTO;
        try {
            dadosValidacaoConsultaAgendarDTO = validacaoRepository.getDadosValidacaoConsultaAgendarDTO(
                    consulta.getDataHora(),
                    consulta.getIdPaciente(),
                    consulta.getIdMedico()
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar dados de validação para agendamento de consulta");
        }
        if (!dadosValidacaoConsultaAgendarDTO.getDiaSemanaValido()) {
            throw new BusinessException("Data da consulta deve ser de segunda à sábado");
        } else if (!dadosValidacaoConsultaAgendarDTO.getHorarioValido()) {
            throw new BusinessException("Horário da consulta deve ser entre 07:00 e 19:00. Tendo duração de uma hora, consultas só podem ser marcadas até as 18 horas.");
        } else if (!dadosValidacaoConsultaAgendarDTO.getAntencedenciaValido()) {
            throw new BusinessException("Consulta só pode ser agendada com no mínimo 30 minutos de antecedência");
        } else if (!dadosValidacaoConsultaAgendarDTO.getPacienteExiste()) {
            throw new BusinessException("O paciente especificado não existe");
        } else if (!dadosValidacaoConsultaAgendarDTO.getPacienteIsAtivo()) {
            throw new BusinessException("Não é possível agendar consulta pois o paciente está inativo");
        } else if (consulta.getIdMedico() != 0 && !dadosValidacaoConsultaAgendarDTO.getMedicoExiste()) {
            throw new BusinessException("O médico especificado não existe");
        } else if (consulta.getIdMedico() != 0 && !dadosValidacaoConsultaAgendarDTO.getMedicoIsAtivo()) {
            throw new BusinessException("Não é possível agendar consulta pois o médico está inativo");
        }

        Boolean existeConsultaMesmoPaciente;
        try {
            existeConsultaMesmoPaciente = consultaRepository.validaMesmoPaciente(consulta.getIdPaciente(), consulta.getDataHora());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar consultas do dia para o mesmo paciente");
        }
        if (existeConsultaMesmoPaciente) {
            throw new BusinessException("Não é possível agendar consulta pois já existe consulta para este paciente no dia indicado");
        }

        Boolean existeConsultaMesmoMedico;
        try {
            existeConsultaMesmoMedico = consultaRepository.validaMesmoMedico(consulta.getDataHora(), consulta.getIdMedico());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar consultas do dia para o mesmo médico");
        }
        if (existeConsultaMesmoMedico) {
            throw new BusinessException("Não é possível agendar consulta pois já existe consulta para este médico no horário indicado");
        }

        if (consulta.getIdMedico() == 0) {
            Integer idMedico;
            try {
                idMedico = consultaRepository.getRandomMedicoDisponivel(consulta.getDataHora());
            } catch (Exception e) {
                e.printStackTrace(System.out);
                throw new BusinessException("Ocorreu um erro ao obter médico disponível para consulta");
            }

            if (idMedico == null) {
                throw new BusinessException("Não existe médico disponível para o horário especificado");
            }

            consulta.setIdMedico(idMedico);
        }

        if (consulta.getIdMedico() == null) {
            throw new BusinessException("Não é possível agendar consulta pois não há médico disponível para o horário selecionado");
        }

        try {
            return consultaRepository.agendar(consulta);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao inserir registro da consulta");
        }
    }

    public void cancelar(Consulta consulta) throws BusinessException {
        if (consulta.getCdMotivoCancto() == null) {
            throw new BusinessException("Código do motivo de cancelamento é obrigatório");
        }

        if (consulta.getCdMotivoCancto() == ConsultaMotivoCancto.OUTROS && isBlank(consulta.getDsMotivoCancto())) {
            throw new BusinessException("Para motivo de cancelamento \"OUTROS\" é obrigatório informar a descrição");
        }

        Consulta consultaOld;
        try {
            consultaOld = consultaRepository.getById(consulta.getId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao consultar registro da consulta a ser cancelada");
        }
        if (consultaOld == null) {
            throw new BusinessException("Não foi encontrada consulta para o id especificado");
        } else if (consultaOld.getCdMotivoCancto() != null) {
            throw new BusinessException("A consulta especificada já foi cancelada");
        }

        Boolean antecedenciaValido;
        try {
            antecedenciaValido = validacaoRepository.validaConsultaAntecedenciaCancelamento(consulta.getId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao validar antecedência da consulta a ser cancelada");
        }
        if (!antecedenciaValido) {
            throw new BusinessException("Consulta só pode ser cancelada com no mínimo 24 horas de antecedência");
        }

        try {
            consultaRepository.cancelar(consulta);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BusinessException("Ocorreu um erro ao cancelar a consulta");
        }
    }
}
