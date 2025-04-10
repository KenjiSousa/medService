package org.example.medService.repositories;

import org.example.medService.dto.DadosValidacaoConsultaAgendarDTO;
import org.example.medService.infrastructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;

public class ValidacaoRepository {
    private static final String SELECT_DADOS_VALIDACAO_CONSULTA_AGENDAR =
            "WITH DADOS_CONSULTA AS (SELECT ?::TIMESTAMP DATA_CONSULTA)" +
            "SELECT EXTRACT(DOW FROM DADOS_CONSULTA.DATA_CONSULTA) BETWEEN 1 AND 6 DIA_SEMANA_VALIDO," +
            "       (   EXTRACT(HOUR FROM DADOS_CONSULTA.DATA_CONSULTA) BETWEEN 7 AND 17" +
            "        OR EXTRACT(HOUR FROM DADOS_CONSULTA.DATA_CONSULTA) = 18 AND EXTRACT(MINUTE FROM DADOS_CONSULTA.DATA_CONSULTA) = 0) HORARIO_VALIDO," +
            "       DADOS_CONSULTA.DATA_CONSULTA > (CURRENT_TIMESTAMP + '30 minutes'::INTERVAL) ANTECEDENCIA_VALIDO," +
            "       EXISTS (SELECT 1 FROM PACIENTE WHERE ID = ?) PACIENTE_EXISTE," +
            "       (SELECT ATIVO FROM PACIENTE WHERE ID = ?) PACIENTE_ATIVO," +
            "       EXISTS (SELECT 1 FROM MEDICO WHERE ID = ?) MEDICO_EXISTE," +
            "       (SELECT ATIVO FROM MEDICO WHERE ID = ?) MEDICO_ATIVO" +
            "  FROM DADOS_CONSULTA";
    private static final String VALIDACAO_CONSULTA_ANTECEDENCIA_CANCELAMENTO =
            "SELECT CURRENT_TIMESTAMP < (DATAHORA - '1 day'::INTERVAL)" +
            "  FROM CONSULTA" +
            " WHERE ID = ?";

    public DadosValidacaoConsultaAgendarDTO getDadosValidacaoConsultaAgendarDTO(Timestamp dataConsulta, Integer idPaciente, Integer idMedico) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_DADOS_VALIDACAO_CONSULTA_AGENDAR)
        ) {
            stmt.setTimestamp(1, dataConsulta);
            stmt.setInt(2, idPaciente);
            stmt.setInt(3, idPaciente);
            stmt.setInt(4, idMedico);
            stmt.setInt(5, idMedico);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();

                return new DadosValidacaoConsultaAgendarDTO(
                        rs.getBoolean("DIA_SEMANA_VALIDO"),
                        rs.getBoolean("HORARIO_VALIDO"),
                        rs.getBoolean("ANTECEDENCIA_VALIDO"),
                        rs.getBoolean("PACIENTE_EXISTE"),
                        rs.getBoolean("PACIENTE_ATIVO"),
                        rs.getBoolean("MEDICO_EXISTE"),
                        rs.getBoolean("MEDICO_ATIVO")
                );
            }
        }
    }

    public Boolean validaConsultaAntecedenciaCancelamento(Integer id) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(VALIDACAO_CONSULTA_ANTECEDENCIA_CANCELAMENTO)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();

                return rs.getBoolean(1);
            }
        }
    }
}
