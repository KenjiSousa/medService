package org.example.medService.dto;

public class DadosValidacaoConsultaAgendarDTO {
    private Boolean diaSemanaValido;
    private Boolean horarioValido;
    private Boolean antencedenciaValido;
    private Boolean pacienteExiste;
    private Boolean pacienteIsAtivo;
    private Boolean medicoExiste;
    private Boolean medicoIsAtivo;

    public DadosValidacaoConsultaAgendarDTO(Boolean diaSemanaValido, Boolean horarioValido, Boolean antencedenciaValido, Boolean pacienteExiste, Boolean pacienteIsAtivo, Boolean medicoExiste, Boolean medicoIsAtivo) {
        this.diaSemanaValido = diaSemanaValido;
        this.horarioValido = horarioValido;
        this.antencedenciaValido = antencedenciaValido;
        this.pacienteExiste = pacienteExiste;
        this.pacienteIsAtivo = pacienteIsAtivo;
        this.medicoExiste = medicoExiste;
        this.medicoIsAtivo = medicoIsAtivo;
    }

    public Boolean getDiaSemanaValido() {
        return diaSemanaValido;
    }

    public void setDiaSemanaValido(Boolean diaSemanaValido) {
        this.diaSemanaValido = diaSemanaValido;
    }

    public Boolean getHorarioValido() {
        return horarioValido;
    }

    public void setHorarioValido(Boolean horarioValido) {
        this.horarioValido = horarioValido;
    }

    public Boolean getAntencedenciaValido() {
        return antencedenciaValido;
    }

    public void setAntencedenciaValido(Boolean antencedenciaValido) {
        this.antencedenciaValido = antencedenciaValido;
    }

    public Boolean getPacienteExiste() {
        return pacienteExiste;
    }

    public void setPacienteExiste(Boolean pacienteExiste) {
        this.pacienteExiste = pacienteExiste;
    }

    public Boolean getPacienteIsAtivo() {
        return pacienteIsAtivo;
    }

    public void setPacienteIsAtivo(Boolean pacienteIsAtivo) {
        this.pacienteIsAtivo = pacienteIsAtivo;
    }

    public Boolean getMedicoExiste() {
        return medicoExiste;
    }

    public void setMedicoExiste(Boolean medicoExiste) {
        this.medicoExiste = medicoExiste;
    }

    public Boolean getMedicoIsAtivo() {
        return medicoIsAtivo;
    }

    public void setMedicoIsAtivo(Boolean medicoIsAtivo) {
        this.medicoIsAtivo = medicoIsAtivo;
    }
}
