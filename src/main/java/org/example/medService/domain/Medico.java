package org.example.medService.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.medService.dto.MedicoInsertRequestDTO;
import org.example.medService.dto.MedicoUpdateRequestDTO;
import org.example.medService.enums.Espec;
import org.example.medService.exceptions.BusinessException;

@XmlType(propOrder = { "id", "nome", "email", "telefone", "crm", "espec", "logradouro", "numero", "complemento", "bairro", "cidade", "uf", "cep", "ativo" })
public class Medico {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private Espec espec;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private Boolean ativo;

    public Medico() {}

    public Medico(Integer id, String nome, String email, String telefone, String crm, Espec espec, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.crm = crm;
        this.espec = espec;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.ativo = ativo;
    }

    public Medico(MedicoInsertRequestDTO medicoDTO) throws BusinessException {
        this.nome = medicoDTO.getNome();
        this.email = medicoDTO.getEmail();
        this.telefone = medicoDTO.getTelefone();
        this.crm = medicoDTO.getCrm();
        try {
            this.espec = Espec.valueOf(medicoDTO.getEspec());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Não existe especificação com este nome");
        }
        this.logradouro = medicoDTO.getLogradouro();
        this.numero = medicoDTO.getNumero();
        this.complemento = medicoDTO.getComplemento();
        this.bairro = medicoDTO.getBairro();
        this.cidade = medicoDTO.getCidade();
        this.uf = medicoDTO.getUf();
        this.cep = medicoDTO.getCep();
    }

    public Medico(MedicoUpdateRequestDTO medicoDTO) {
        this.id = medicoDTO.getId();
        this.nome = medicoDTO.getNome();
        this.email = medicoDTO.getEmail();
        this.telefone = medicoDTO.getTelefone();
        this.crm = medicoDTO.getCrm();
        this.espec = medicoDTO.getEspec();
        this.logradouro = medicoDTO.getLogradouro();
        this.numero = medicoDTO.getNumero();
        this.complemento = medicoDTO.getComplemento();
        this.bairro = medicoDTO.getBairro();
        this.cidade = medicoDTO.getCidade();
        this.uf = medicoDTO.getUf();
        this.cep = medicoDTO.getCep();
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
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @XmlElement(required = true)
    public Espec getEspec() {
        return espec;
    }

    public void setEspec(Espec espec) {
        this.espec = espec;
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
