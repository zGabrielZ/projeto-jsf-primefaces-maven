package br.com.gabrielferreira.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class NavegacaoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void cadastrarUsuario() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/login/CadastroLoginUsuario.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void login() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/login/LoginUsuario.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void home() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/HomePrincipal.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void gerarRelatorioProfessor() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/professor/relatorio/GerarRelatorioProfessor.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void gerarRelatorioTurma() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/turma/relatorio/GerarRelatorioTurma.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void gerarRelatorioAluno() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/aluno/relatorio/GerarRelatorioAluno.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void adicionarEvento() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/evento/AdicionarEventoProfessorTurma.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaAluno() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/aluno/ConsultaAluno.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaProfessor() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/professor/ConsultaProfessor.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaTurma() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/turma/ConsultaTurma.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroAluno() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/aluno/CadastroAluno.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroProfessor() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/professor/CadastroProfessor.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroTurma() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/turma/CadastroTurma.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
}
