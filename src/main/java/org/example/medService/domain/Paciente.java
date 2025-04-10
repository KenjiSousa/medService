package org.example.medService.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.medService.dto.PacienteInsertRequestDTO;
import org.example.medService.dto.PacienteUpdateRequestDTO;

@XmlType(propOrder = { "id", "nome", "email", "telefone", "cpf", "logradouro", "numero", "complemento", "bairro", "cidade", "uf", "cep", "ativo" })
public class Paciente {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private Boolean ativo;

    public Paciente() {}

    public Paciente(Integer id, String nome, String email, String telefone, String cpf, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.ativo = ativo;
    }

    public Paciente(PacienteInsertRequestDTO pacienteDTO) {
        this.nome = pacienteDTO.getNome();
        this.email = pacienteDTO.getEmail();
        this.telefone = pacienteDTO.getTelefone();
        this.cpf = pacienteDTO.getCpf();
        this.logradouro = pacienteDTO.getLogradouro();
        this.numero = pacienteDTO.getNumero();
        this.complemento = pacienteDTO.getComplemento();
        this.bairro = pacienteDTO.getBairro();
        this.cidade = pacienteDTO.getCidade();
        this.uf = pacienteDTO.getUf();
        this.cep = pacienteDTO.getCep();
    }

    public Paciente(PacienteUpdateRequestDTO pacienteDTO) {
        this.id = pacienteDTO.getId();
        this.nome = pacienteDTO.getNome();
        this.email = pacienteDTO.getEmail();
        this.telefone = pacienteDTO.getTelefone();
        this.cpf = pacienteDTO.getCpf();
        this.logradouro = pacienteDTO.getLogradouro();
        this.numero = pacienteDTO.getNumero();
        this.complemento = pacienteDTO.getComplemento();
        this.bairro = pacienteDTO.getBairro();
        this.cidade = pacienteDTO.getCidade();
        this.uf = pacienteDTO.getUf();
        this.cep = pacienteDTO.getCep();
    }

    @XmlElement(required = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(required = true)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlElement(required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(required = true)
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @XmlElement(required = true)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @XmlElement(required = true)
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @XmlElement(required = true)
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @XmlElement(required = true)
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @XmlElement(required = true)
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @XmlElement(required = true)
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
