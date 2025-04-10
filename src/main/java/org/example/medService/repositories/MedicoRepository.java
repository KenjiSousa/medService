package org.example.medService.repositories;

import org.example.medService.domain.Medico;
import org.example.medService.enums.Espec;
import org.example.medService.infrastructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;

public class MedicoRepository {
    private static final String INSERT = "INSERT INTO MEDICO (NOME, EMAIL, TELEFONE, CRM, ESPEC, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP) VALUES (?, ?, ?, ?, CAST(? AS ESPECIALIDADE_TYPE), ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT ID, NOME, EMAIL, TELEFONE, CRM, ESPEC, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP FROM MEDICO ORDER BY UPPER(NOME)";
    // Apenas este exibe ATIVO para validações internas
    private static final String SELECT_BY_ID = "SELECT ID, NOME, EMAIL, TELEFONE, CRM, ESPEC, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP, ATIVO FROM MEDICO WHERE ID = ?";
    private static final String UPDATE = "UPDATE MEDICO SET NOME = ?, EMAIL = ?, TELEFONE = ?, CRM = ?, ESPEC = CAST(? AS ESPECIALIDADE_TYPE), LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, UF = ?, CEP = ? WHERE ID = ?";
    private static final String UPDATE_INATIVA = "UPDATE MEDICO SET ATIVO = FALSE WHERE ID = ?";

    public Medico inserir(Medico medico) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEmail());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getCrm());
            stmt.setString(5, medico.getEspec().name());
            stmt.setString(6, medico.getLogradouro());
            stmt.setString(7, medico.getNumero());
            stmt.setString(8, medico.getComplemento());
            stmt.setString(9, medico.getBairro());
            stmt.setString(10, medico.getCidade());
            stmt.setString(11, medico.getUf());
            stmt.setString(12, medico.getCep());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();

                medico.setId(rs.getInt(1));
            }
        }

        return medico;
    }

    public ArrayList<Medico> listar() throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT);
                ResultSet rs = stmt.executeQuery()
        ) {
            ArrayList<Medico> medicos = new ArrayList<>();

            while (rs.next()) {
                Medico medico = new Medico();

                medico.setNome(rs.getString("NOME"));
                medico.setEmail(rs.getString("EMAIL"));
                medico.setCrm(rs.getString("CRM"));
                medico.setEspec(Espec.valueOf(rs.getString("ESPEC")));

                medicos.add(medico);
            }

            return medicos;
        }
    }

    public Medico selectById(Integer id) throws SQLException, NamingException {
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

                return new Medico(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONE"),
                        rs.getString("CRM"),
                        rs.getString("ESPEC") != null ? Espec.valueOf(rs.getString("ESPEC")) : null,
                        rs.getString("LOGRADOURO"),
                        rs.getString("NUMERO"),
                        rs.getString("COMPLEMENTO"),
                        rs.getString("BAIRRO"),
                        rs.getString("CIDADE"),
                        rs.getString("UF"),
                        rs.getString("CEP"),
                        rs.getBoolean("ATIVO")
                );
            }
        }
    }

    public Medico atualizar(Medico medico) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE)
        ) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEmail());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getCrm());
            stmt.setString(5, medico.getEspec().name());
            stmt.setString(6, medico.getLogradouro());
            stmt.setString(7, medico.getNumero());
            stmt.setString(8, medico.getComplemento());
            stmt.setString(9, medico.getBairro());
            stmt.setString(10, medico.getCidade());
            stmt.setString(11, medico.getUf());
            stmt.setString(12, medico.getCep());
            stmt.setInt(13, medico.getId());
            stmt.executeUpdate();
        }

        return medico;
    }

    public void excluir(Integer id) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE_INATIVA)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
