package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.dto.relatorio.AlunoRelDTO;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielferreira.repositorio.AlunoRepositorio;
import br.com.gabrielferreira.repositorio.PessoaRepositorio;
import br.com.gabrielferreira.repositorio.TelefoneRepositorio;
import br.com.gabrielferreira.repositorio.TurmaRepositorio;
import br.com.gabrielferreira.search.AlunoSearch;
import br.com.gabrielferreira.service.AlunoService;
import br.com.gabrielferreira.utils.FacesMessages;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AlunoServiceImpl implements Serializable,AlunoService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoRepositorio alunoRepositorio;
	
	@Inject
	private TurmaRepositorio turmaRepositorio;
	
	@Inject
	private PessoaRepositorio pessoaRepositorio;
	
	@Inject
	private TelefoneRepositorio telefoneRepositorio;
	
	@Override
	public void getInserirAluno(Pessoa pessoa) throws RegraDeNegocioException {
		getVerificarAtualizarOuInserir(pessoa);
		pessoaRepositorio.inserir(pessoa);
	}

	@Override
	public void getRemoverAluno(Pessoa pessoa) {
		pessoaRepositorio.remover(pessoa);
	}

	@Override
	public void getAtualizarAluno(Pessoa pessoa) throws RegraDeNegocioException {
		getVerificarNumeroAtualizado(pessoa);
		getVerificarAtualizarOuInserir(pessoa);
		pessoaRepositorio.atualizar(pessoa);
	}

	@Override
	public void getVerificarAtualizarOuInserir(Pessoa pessoa) throws RegraDeNegocioException {
		Aluno aluno = (Aluno) pessoa;
		
		if(aluno.getId() == null) {
			if(aluno.getTurma() != null) {
				
				if(aluno.getTurma().getVagas() == 0) {
					throw new RegraDeNegocioException("Não é possível inserir o aluno nesta turma, pois acabou a vaga da turma : " + aluno.getTurma().getNomeTurma());
				}
				
				novaVaga(aluno);
			}
		} else {
			if(aluno.getTurma() != null) {
				
				if(aluno.getTurma().getVagas() == 0) {
					throw new RegraDeNegocioException("Não é possível atualizar este aluno nesta turma, pois acabou a vaga da turma : " + aluno.getTurma().getNomeTurma());
				}
				
				Pessoa alunoAntigo = pessoaRepositorio.procurarPorId(aluno.getId()); 
				Aluno alunoBuscadoAntigo = (Aluno) alunoAntigo;
				
				if(!alunoBuscadoAntigo.getTurma().getId().equals(aluno.getTurma().getId())) {
					antigaVaga(alunoAntigo);
					novaVaga(aluno);
				}
			}
		}
		
	}
	
	private void antigaVaga(Pessoa alunoAntigo) {
		Aluno aluno = (Aluno) alunoAntigo;
		Integer novaVagas = aluno.getTurma().getVagas() + 1;
		
		aluno.getTurma().setVagas(novaVagas);
		turmaRepositorio.atualizar(aluno.getTurma());
	}
	
	private void novaVaga(Aluno aluno) {
		Integer novaVagas = aluno.getTurma().getVagas() - 1;
		
		aluno.getTurma().setVagas(novaVagas);
		turmaRepositorio.atualizar(aluno.getTurma());
	}

	@Override
	public Aluno getConsultarDetalhe(Integer id) {
		Pessoa pessoa = pessoaRepositorio.procurarPorId(id);
		Aluno aluno = (Aluno) pessoa;
		return aluno;
	}

	@Override
	public boolean getVerificarNumero(String numero) {
		boolean verificar = alunoRepositorio.verificarNumero(numero);
		return verificar;
	}

	@Override
	public void getVerificarNumeroAtualizado(Pessoa pessoa) throws RegraDeNegocioException {
		
		Aluno aluno = (Aluno) pessoa;
		
		if(alunoRepositorio.verificarNumeroAtualizado(aluno.getNumeroMatricula(), aluno.getId())) {
			throw new RegraDeNegocioException("Não é possível atualizar este número, pois já está cadastrado !");
		}
	}

	@Override
	public List<Aluno> getFiltar(AlunoSearch alunoSearch) {
		List<Aluno> alunos = alunoRepositorio.filtrar(alunoSearch);
		return alunos;
	}

	@Override
	public List<AlunoRelDTO> getListarAlunosRelatorio(String nome, String sexo, String turma) {
		List<AlunoRelDTO> alunoRelDTOs = alunoRepositorio.listarAlunosRelatorio(nome, sexo, turma);
		return alunoRelDTOs;
	}
	
	public ArrayList<AlunoRelDTO> getListaAlunos(String nome, String sexo, String turma){
		List<AlunoRelDTO> alunoRelDTOs = alunoRepositorio.listarAlunosRelatorio(nome, sexo, turma);
		ArrayList<AlunoRelDTO> alunos = new ArrayList<AlunoRelDTO>();
		for(AlunoRelDTO a : alunoRelDTOs) {
			List<TelefoneRelDTO> telefoneRelDTOs = telefoneRepositorio.listarTelefonesRelatorio(a.getId());
			a.setTelefones(telefoneRelDTOs);
			alunos.add(a);
		}
		
		return alunos;
	}
	
	@Override
	public void getGerarRelatorioAluno(String nome, String sexo, String turma) {
		List<AlunoRelDTO> alunos = getListaAlunos(nome, sexo, turma);
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminhoAluno = facesContext.getExternalContext().getRealPath("/resources/relatorio/aluno/Alunos.jrxml");
			JasperReport compilarRelatorioAluno = JasperCompileManager.compileReport(caminhoAluno);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criador", "Gabriel Ferreira");
			paramatros.put("nomeParam", nome);
			paramatros.put("sexoParam", sexo);
			paramatros.put("turmaParam", turma);
			String caminhoTelefoneJasper = facesContext.getExternalContext().getRealPath("/resources/relatorio/telefone/Telefones.jasper");
			paramatros.put("SUBREPORT_DIR", caminhoTelefoneJasper);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(alunos);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorioAluno,paramatros,dataSource);
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_alunos.pdf");
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
