package org.example.medService.repositories;

import org.example.medService.domain.Consulta;
import org.example.medService.enums.ConsultaMotivoCancto;
import org.example.medService.infrastructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;

public class ConsultaRepository {
    private static final String INSERT = "INSERT INTO CONSULTA (ID_PACIENTE, ID_MEDICO, DATAHORA) VALUES (?, ?, ?)";
    private static final String SELECT_MESMO_PACIENTE =
            "SELECT EXISTS (SELECT 1 FROM CONSULTA" +
            "                WHERE ID_PACIENTE = ?" +
            "                  AND DATE_TRUNC('day', DATAHORA) = DATE_TRUNC('day', ?::TIMESTAMP)" +
            "                  AND DATAHORA IS NOT NULL" +
            "                  AND CD_MOTIVOCANCTO IS NULL)";
    private static final String SELECT_MESMO_MEDICO =
            "WITH DADOS_CONSULTA AS (SELECT ?::TIMESTAMP DATA_CONSULTA)" +
            "SELECT EXISTS (SELECT 1 FROM CONSULTA" +
            "                WHERE ID_MEDICO = ?" +
            "                  AND DATAHORA > DADOS_CONSULTA.DATA_CONSULTA - '1 hour'::INTERVAL" +
            "                  AND DATAHORA < DADOS_CONSULTA.DATA_CONSULTA + '1 hour'::INTERVAL" +
            "                  AND CD_MOTIVOCANCTO IS NULL)" +
            "  FROM DADOS_CONSULTA";
    private static final String SELECT_RANDOM_MEDICO =
            "WITH DADOS_CONSULTA AS (SELECT ?::TIMESTAMP DATA_CONSULTA)" +
            " SELECT ID FROM MEDICO, DADOS_CONSULTA" +
            "  WHERE NOT EXISTS (SELECT 1 FROM CONSULTA" +
            "                     WHERE ID_MEDICO = MEDICO.ID" +
            "                       AND DATAHORA > DADOS_CONSULTA.DATA_CONSULTA - '1 hour'::INTERVAL" +
            "                       AND DATAHORA < DADOS_CONSULTA.DATA_CONSULTA + '1 hour'::INTERVAL" +
            "                       AND CD_MOTIVOCANCTO IS NULL)" +
            "  ORDER BY RANDOM()" +
            "  LIMIT 1";
    private static final String SELECT_BY_ID = "SELECT ID, ID_PACIENTE, ID_MEDICO, DATAHORA, CD_MOTIVOCANCTO, DS_MOTIVOCANCTO FROM CONSULTA WHERE ID = ?";
    private static final String UPDATE_CANCELAR = "UPDATE CONSULTA SET CD_MOTIVOCANCTO = CAST(? AS CDMOTIVOCANCTO_TYPE), DS_MOTIVOCANCTO = ? WHERE ID = ?";

    public Consulta agendar(Consulta consulta) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setTimestamp(3, consulta.getDataHora());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();

                consulta.setId(rs.getInt(1));
            }
        }

        return consulta;
    }

    public Boolean validaMesmoPaciente(Integer idPaciente, Timestamp dataConsulta) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_MESMO_PACIENTE)
        ) {
            stmt.setInt(1, idPaciente);
            stmt.setTimestamp(2, dataConsulta);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();

                return rs.getBoolean(1);
            }
        }
    }

    public Boolean validaMesmoMedico(Timestamp dataConsulta, Integer idMedico) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_MESMO_MEDICO)
        ) {
            stmt.setTimestamp(1, dataConsulta);
            stmt.setInt(2, idMedico);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();

                return rs.getBoolean(1);
            }
        }
    }

    public Integer getRandomMedicoDisponivel(Timestamp dataConsulta) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_RANDOM_MEDICO)
        ) {
            stmt.setTimestamp(1, dataConsulta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    return null;
                }

                rs.next();

                return rs.getInt(1);
            }
        }
    }

    public Consulta getById(Integer id) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    return null;
                }

                rs.next();

                return new Consulta(
                        rs.getInt("ID"),
                        rs.getInt("ID_PACIENTE"),
                        rs.getInt("ID_MEDICO"),
                        rs.getTimestamp("DATAHORA"),
                        rs.getString("CD_MOTIVOCANCTO") != null ? ConsultaMotivoCancto.valueOf(rs.getString("CD_MOTIVOCANCTO")) : null,
                        rs.getString("DS_MOTIVOCANCTO")
                );
            }
        }
    }

    public void cancelar(Consulta consulta) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE_CANCELAR)
        ) {
            stmt.setString(1, consulta.getCdMotivoCancto().name());
            stmt.setString(2, consulta.getDsMotivoCancto());
            stmt.setInt(3, consulta.getId());
            stmt.executeUpdate();
        }
    }
}
