package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.dto.TurmaDTO;
import br.com.gabrielferreira.entidade.dto.relatorio.TurmaRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.repositorio.TurmaRepositorio;
import br.com.gabrielferreira.search.TurmaSearch;
import br.com.gabrielferreira.service.TurmaService;
import br.com.gabrielferreira.utils.FacesMessages;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TurmaServiceImpl implements Serializable,TurmaService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TurmaRepositorio turmaRepositorio;
	
	@Override
	public void getRemoverTurma(Turma turma) {
		turmaRepositorio.deletarPorId(Turma.class, turma.getId());
	}
	
	@Override
	public void getInserirTurma(Turma turma) throws RegraDeNegocioException {
		getVerificarNumero(turma.getNumeroTurma());
		getVerificarNomeAndTurno(turma);
		turmaRepositorio.inserir(turma);
	}

	@Override
	public List<TurmaDTO> getFiltar(TurmaSearch turmaSearch) {
		List<TurmaDTO> filtrados = turmaRepositorio.filtrar(turmaSearch);
		return filtrados;
	}
	
	@Override
	public Turma getAtualizarTurma(Turma turma) throws RegraDeNegocioException {
		getVerificarNomeAndTurnoAtualizado(turma);
		getVerificarNumeroAtualizado(turma);
		return turmaRepositorio.atualizar(turma);
	}
	
	@Override
	public void getVerificarNumero(String numero) throws RegraDeNegocioException {
		if(turmaRepositorio.verificarNumero(numero)){
			throw new RegraDeNegocioException("Não é possível cadastrar este número da turma, pois já está cadastrado !");
		} 
	}

	@Override
	public void getVerificarNomeAndTurno(Turma turma) throws RegraDeNegocioException {
		if(turmaRepositorio.verificarNomeAndTurno(turma.getNomeTurma(), turma.getTurno())) {
			throw new RegraDeNegocioException("Não é possível cadastrar este nome e o turno, pois já está cadastrado !");
		} 
	}

	@Override
	public void getVerificarNomeAndTurnoAtualizado(Turma turma) throws RegraDeNegocioException {
		if(turmaRepositorio.verificarNomeAndTurnoAtualizado(turma.getNomeTurma(), turma.getTurno(),turma.getId())) {
			throw new RegraDeNegocioException("Não é possível atualizar este nome e o turno, pois já está cadastrado !");
		}
	}

	@Override
	public void getVerificarNumeroAtualizado(Turma turma) throws RegraDeNegocioException {
		if(turmaRepositorio.verificarNumeroAtualizado(turma.getNumeroTurma(), turma.getId())){
			throw new RegraDeNegocioException("Não é possível atualizar este número da turma, pois já está cadastrado !");
		} 
	}

	@Override
	public List<Turma> getListarTurmas() {
		List<Turma> turmas = turmaRepositorio.listagem(Turma.class);
		return turmas;
	}

	@Override
	public Turma getById(Integer id) {
		Turma turma = turmaRepositorio.pesquisarPorId(id, Turma.class);
		return turma;
	}

	@Override
	public List<TurmaRelDTO> getListarTurmasRelatorio(String nome, String turno) {
		List<TurmaRelDTO> turmas = turmaRepositorio.listarTurmasRelatorio(nome, turno);
		return turmas;
	}

	@Override
	public void getGerarRelatorioTurma(String nome, String turno) {
		try {
			List<TurmaRelDTO> turmas = getListarTurmasRelatorio(nome, turno);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminho = facesContext.getExternalContext().getRealPath("/resources/relatorio/turma/Turmas.jrxml");
			JasperReport compilarRelatorio = JasperCompileManager.compileReport(caminho);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criador", "Gabriel Ferreira");
			paramatros.put("nome", nome);
			paramatros.put("turno", turno);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(turmas);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorio,paramatros,dataSource);
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_turmas.pdf");
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

	@Override
	public Turma getTurmaDetalhes(Integer id) {
		return turmaRepositorio.getTurmaDetalhes(id);
	}

}
