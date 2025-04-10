package org.example.medService.repositories;

import org.example.medService.domain.Paciente;
import org.example.medService.infrastructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteRepository {
    private static final String INSERT = "INSERT INTO PACIENTE (NOME, EMAIL, TELEFONE, CPF, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT ID, NOME, EMAIL, TELEFONE, CPF, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP FROM PACIENTE ORDER BY UPPER(NOME)";
    // Apenas este exibe ATIVO para validações internas
    private static final String SELECT_BY_ID = "SELECT ID, NOME, EMAIL, TELEFONE, CPF, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP, ATIVO FROM PACIENTE WHERE ID = ?";
    private static final String UPDATE = "UPDATE PACIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, CPF = ?, LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, UF = ?, CEP = ? WHERE ID = ?";
    private static final String UPDATE_INATIVA = "UPDATE PACIENTE SET ATIVO = FALSE WHERE ID = ?";

    public Paciente inserir(Paciente paciente) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getCpf());
            stmt.setString(5, paciente.getLogradouro());
            stmt.setString(6, paciente.getNumero());
            stmt.setString(7, paciente.getComplemento());
            stmt.setString(8, paciente.getBairro());
            stmt.setString(9, paciente.getCidade());
            stmt.setString(10, paciente.getUf());
            stmt.setString(11, paciente.getCep());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();

                paciente.setId(rs.getInt(1));
            }
        }

        return paciente;
    }

    public ArrayList<Paciente> listar() throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT);
                ResultSet rs = stmt.executeQuery()
        ) {
            ArrayList<Paciente> pacientes = new ArrayList<>();

            while (rs.next()) {
                Paciente paciente = new Paciente();

                paciente.setNome(rs.getString("NOME"));
                paciente.setEmail(rs.getString("EMAIL"));
                paciente.setCpf(rs.getString("CPF"));

                pacientes.add(paciente);
            }

            return pacientes;
        }
    }

    public Paciente selectById(Integer id) throws SQLException, NamingException {
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

                return new Paciente(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONE"),
                        rs.getString("CPF"),
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

    public Paciente atualizar(Paciente paciente) throws SQLException, NamingException {
        try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE)
        ) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getCpf());
            stmt.setString(5, paciente.getLogradouro());
            stmt.setString(6, paciente.getNumero());
            stmt.setString(7, paciente.getComplemento());
            stmt.setString(8, paciente.getBairro());
            stmt.setString(9, paciente.getCidade());
            stmt.setString(10, paciente.getUf());
            stmt.setString(11, paciente.getCep());
            stmt.setInt(12, paciente.getId());
            stmt.executeUpdate();
        }

        return paciente;
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
