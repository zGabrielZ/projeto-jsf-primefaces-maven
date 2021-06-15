package br.com.gabrielferreira.controller;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.entidade.enums.Turno;
import br.com.gabrielferreira.search.relatorio.AlunoRelatorio;
import br.com.gabrielferreira.search.relatorio.ProfessorRelatorio;
import br.com.gabrielferreira.search.relatorio.TurmaRelatorio;
import br.com.gabrielferreira.service.impl.AlunoServiceImpl;
import br.com.gabrielferreira.service.impl.ProfessorServiceImpl;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class GerarRelatorioController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private AlunoServiceImpl alunoServiceImpl;
	
	@Inject
	private ProfessorServiceImpl professorServiceImpl;
	
	@Getter
	@Setter
	private TurmaRelatorio turmaRelatorio;
	
	@Getter
	@Setter
	private AlunoRelatorio alunoRelatorio;
	
	@Getter
	@Setter
	private ProfessorRelatorio professorRelatorio;
	
	@PostConstruct
	public void init() {
		turmaRelatorio = new TurmaRelatorio();
		alunoRelatorio = new AlunoRelatorio();
		professorRelatorio = new ProfessorRelatorio();
	}
	
	public void gerarRelatorioTurma() {
		turmaServiceImpl.getGerarRelatorioTurma(turmaRelatorio.getNome(), turmaRelatorio.getTurno());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório da turma gerado com sucesso !",
				null);
		turmaRelatorio = new TurmaRelatorio();
	}

	public void gerarRelatorioAluno() {
		alunoServiceImpl.getGerarRelatorioAluno(alunoRelatorio.getNome(), alunoRelatorio.getSexo(), alunoRelatorio.getTurma());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório do aluno gerado com sucesso !",
				null);
		alunoRelatorio = new AlunoRelatorio();
	}
	
	public void gerarRelatorioProfessor() {
		professorServiceImpl.getGerarRelatorioProfessor(professorRelatorio.getNome(), professorRelatorio.getSexo(),
				professorRelatorio.getGraduacao());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório do professor gerado com sucesso !",
				null);
		alunoRelatorio = new AlunoRelatorio();
	}
	
	public Sexo[] getSexos() {
		return Sexo.values();
	}
	
	public Turno[] getTurnos() {
		return Turno.values();
	}
	
	
}
