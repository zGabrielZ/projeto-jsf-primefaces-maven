package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.dto.relatorio.ProfessorRelDTO;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.repositorio.PessoaRepositorio;
import br.com.gabrielferreira.repositorio.ProfessorRepositorio;
import br.com.gabrielferreira.repositorio.TelefoneRepositorio;
import br.com.gabrielferreira.search.ProfessorSearch;

import br.com.gabrielferreira.service.ProfessorService;
import br.com.gabrielferreira.utils.FacesMessages;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProfessorServiceImpl implements Serializable,ProfessorService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfessorRepositorio professorRepositorio;
	
	@Inject
	private PessoaRepositorio pessoaRepositorio;
	
	@Inject
	private TelefoneRepositorio telefoneRepositorio;

	@Override
	public void getInserirProfessor(Pessoa pessoa) throws RegraDeNegocioException {
		pessoaRepositorio.inserir(pessoa);
	}

	@Override
	public void getAtualizarProfessor(Pessoa pessoa) throws RegraDeNegocioException {
		pessoaRepositorio.atualizar(pessoa);
	}
	
	@Override
	public void getRemoverProfessor(Pessoa pessoa) {
		pessoaRepositorio.remover(pessoa);
	}

	@Override
	public List<Professor> getFiltar(ProfessorSearch professorSearch) {
		List<Professor> professores = professorRepositorio.filtrar(professorSearch);
		return professores;
	}

	@Override
	public Professor getConsultarDetalhe(Integer id) {
		Pessoa pessoa = pessoaRepositorio.procurarPorId(id);
		Professor professor = (Professor) pessoa;
		return professor;
	}

	@Override
	public List<Professor> getListar() {
		List<Professor> professores = professorRepositorio.listarProfessores();
		return professores;
	}

	@Override
	public List<ProfessorRelDTO> getListarProfessoresRelatorio(String nome, String sexo, String graduacao) {
		List<ProfessorRelDTO> professorRelDTOs = professorRepositorio.listarProfessoresRelatorio(nome, sexo, graduacao);
		return professorRelDTOs;
	}
	
	public ArrayList<ProfessorRelDTO> getListaProfessores(String nome, String sexo, String graduacao){
		List<ProfessorRelDTO> professoresRelDTOs = professorRepositorio.listarProfessoresRelatorio(nome, sexo, graduacao);
		ArrayList<ProfessorRelDTO> professores = new ArrayList<ProfessorRelDTO>();
		for(ProfessorRelDTO p : professoresRelDTOs) {
			List<TelefoneRelDTO> telefoneRelDTOs = telefoneRepositorio.listarTelefonesRelatorio(p.getId());
			p.setTelefones(telefoneRelDTOs);
			professores.add(p);
		}
		
		return professores;
	}

	@Override
	public void getGerarRelatorioProfessor(String nome, String sexo, String graduacao) {
		List<ProfessorRelDTO> professores = getListaProfessores(nome, sexo, graduacao);
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminhoAluno = facesContext.getExternalContext().getRealPath("/resources/relatorio/professor/Professor.jrxml");
			JasperReport compilarRelatorioAluno = JasperCompileManager.compileReport(caminhoAluno);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criador", "Gabriel Ferreira");
			paramatros.put("nomeParam", nome);
			paramatros.put("sexoParam", sexo);
			paramatros.put("graduacaoParam", graduacao);
			String caminhoTelefoneJasper = facesContext.getExternalContext().getRealPath("/resources/relatorio/telefone/Telefones.jasper");
			paramatros.put("SUBREPORT_DIR", caminhoTelefoneJasper);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(professores);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorioAluno,paramatros,dataSource);
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_professores.pdf");
			byte [] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			response.getOutputStream().write(bytes);
			response.getCharacterEncoding();
			facesContext.responseComplete();
			
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_ERROR, "Erro ao gerar relatório !",
					null);
			e.printStackTrace();
		}
		
	}
}
