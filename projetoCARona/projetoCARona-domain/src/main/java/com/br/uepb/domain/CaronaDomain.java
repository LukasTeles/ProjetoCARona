package com.br.uepb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;

/**
 * Classe de domínio que define o modelo para a Carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 19/04/2015
 */

@Entity
@Table(name="CARONAS")
public class CaronaDomain {

	final static Logger logger = Logger.getLogger(CaronaDomain.class);
	
	/** Id da sessão */
	//@NotNull(message = "O ID da Sessao não pode ser null")
	@JoinColumn(name="login")
	@Column(nullable=false)
	private String idSessao;
	
	/** Id da carona */ //TODO: Deve ser gerado automaticamente
	//@NotNull(message = "O ID da Carona não pode ser null")
	@Id
	//@GeneratedValue
	private String id;

	/** Local de origem da carona */ 
	//@NotNull(message = "O local de origem da carona não pode ser null")
	@Column(nullable=false)
	private String origem;
	
	/** Local de destino da carona */ 
	//@NotNull(message = "O local de Destino da Carona não pode ser null")
	@Column(nullable=false)
	private String destino;
	
	/** Cidade onde a carona ira acontecer (no caso de caronas municipais) */
	private String cidade;
	
	/** Horário da carona */ 
	//@NotNull(message = "O Horario da Carona não pode ser null")
	//@Pattern(regexp = "d{2}\\:\\d{2}", message="Hora: preencha no formato hh:mm")
	@Column(nullable=false)
	private String hora;
	
	/** Dia da carona */ 	
	//@NotNull(message = "O dia da Carona não pode ser null")
	//@Pattern(regexp = "d{2}\\/\\d{2}\\/\\d{4}", message="Data: preencha no formato dd/mm/yyyy")
	@Column(nullable=false)
	private String data;
	
	/** Data da volta (no caso de carona relampago)*/
	private String dataVolta;
	
	/** Quantidade de vagas disponívels para a carona */ 
	//@NotNull(message = "A quantidade de vagas na Carona não pode ser nula")
	@Column(nullable=false)
	private int vagas;
	
	/** Quantidade minima de caroneiros (para carona relampago) */
	private int minimoCaroneiros;
	
	/** Informar se a carona relampago foi expirada */
	private boolean caronaRelampagoExpirada = false;
	
	/** Diz se a carona é ou não preferencial */
	private boolean preferencial = false;
	
	/** Informa o tipo da carona. Pode ser C=Carona Comum, M=Carona Municipal, e R=Carona Relampago */
	private String tipoCarona="C";
	
	/** Lista de pontos de encontro da carona */
	@OneToMany(mappedBy="idCarona", cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PontoDeEncontroDomain> pontoDeEncontro = new ArrayList<PontoDeEncontroDomain>();
	
	/**
	 * Método construtor de CaronaDomain
	 * @param idSessao Armazena o login do usuário
	 * @param idCarona Id da carona
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param data Data que a carona irá acontecer
	 * @param hora Horário em que a carona irá sair
	 * @param vagas Quantidade de vagas disponíveis para a carona
	 * @throws Exception Lança exceção caso algum dos campos informados esteja vazio, null ou inválido
	 */
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String data, String hora, int vagas) throws Exception { 
		setTipoCarona("C");
		setId(idCarona);
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(data);
		setHora(hora);
		setVagas(vagas);
	}
	
	/**
	 * Método construtor de CaronaDomain para o caso de caronas municipais
	 * @param idSessao Armazena o login do usuário
	 * @param idCarona Id da carona
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param cidade Cidade onde a carona sera realizada (apenas para caronas municipais)
	 * @param data Data que a carona irá acontecer
	 * @param hora Horário em que a carona irá sair
	 * @param vagas Quantidade de vagas disponíveis para a carona
	 * @throws Exception Lança exceção caso algum dos campos informados esteja vazio, null ou inválido
	 */
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String cidade, String data, String hora, int vagas) throws Exception { 
		setTipoCarona("M");
		setId(idCarona);
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setCidade(cidade);
		setData(data);
		setHora(hora);
		setVagas(vagas);		
	}
	
	/**
	 * Metodo construtor de CaronaDomain para o caso de carona relampago
	 * @param idSessao Armazena o login do usuário
	 * @param idCarona Id da carona
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param cidade Cidade onde a carona sera realizada (apenas para caronas municipais)
	 * @param dataIda Data de ida da carona
	 * @param dataVolta Data de volta da carona
	 * @param hora Horário em que a carona irá sair
	 * @param minimoCaroneiros Quantidade mínima de caroneiros para a carona acontecer
	 * @throws Exception Lança exceção caso algum dos campos informados esteja vazio, null ou inválido
	 */
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String dataIda, String dataVolta, int minimoCaroneiros, String hora) throws Exception { 
		setTipoCarona("R");
		setId(idCarona);
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(dataIda);
		setDataVolta(dataVolta);
		setMinimoCaroneiros(minimoCaroneiros);
		setVagas(minimoCaroneiros);
		setHora(hora);
		
		//Funcao para verificar se a dataInicial é menor que a dataFinal
		ValidarCampos validar = new ValidarCampos();
		validar.verificarDatas(dataIda, dataVolta);
	}
	
	/** Método construtor vazio de CaronaDomain */
	public CaronaDomain(){}
	
	/**
	 * Método para retornar o id da sessão
	 * @return idSessao
	 */
	public String getIdSessao(){
		return idSessao;
	}
	
	/**
	 * Método para informar o id da sessão
	 * @param idSessao id da sessão
	 * @throws Exception Lança exceção se o idSessao for null ou vazio 
	 */
	private void setIdSessao(String idSessao) throws Exception{
		if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			logger.debug("setIdSessao() Exceção: "+MensagensErro.SESSAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		this.idSessao = idSessao;
	}
	
	/**
	 * Método para retornar o id da carona
	 * @return Id da Carona
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Método para informar o id da carona
	 * @param id Id da carona
	 * @throws Exception Lança exceção se o id da carona informado for null ou vazio
	 */
	public void setId(String id) throws Exception {
		if ( (id == null) || (id.trim().equals("")) ){
			logger.debug("setId() Exceção: "+MensagensErro.IDENTIFICADOR_NAO_INFORMADO);
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_NAO_INFORMADO);
		}
		
		this.id = id;
	}
	
	/**
	 * Método para retornar o local de origem da carona
	 * @return Origem da carona
	 */
	public String getOrigem() {
		return origem;
	}
	
	/**
	 * Método para informar o local de origem da carona
	 * @param origem Origem da carona
	 * @throws Exception Lança exceção de o local de origem informado for null ou vazio
	 */
	public void setOrigem(String origem) throws Exception {
		if ( (origem == null) || (origem.trim().equals("")) ){
			logger.debug("setOrigem() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}
		this.origem = origem;
	}
	
	/**
	 * Método para retornar o local de destino da carona
	 * @return Destino da carona
	 */
	public String getDestino() {
		return destino;
	}
	
	/**
	 * Método para informar o local de destino da carona
	 * @param destino Destino da carona
	 * @throws Exception Lança exceção se o local de destino informado for null ou vazio
	 */
	public void setDestino(String destino) throws Exception {
		if ( (destino == null) || (destino.trim().equals("")) ){
			logger.debug("setDestino() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
		this.destino = destino;
	}
	
	/**
	 * Método para retornar a cidade onde ocorrerá a carona (no caso de carona relampago)
	 * @return Cidade da carona
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Método para informar a cidade da carona (no caso de carona relampago)
	 * @param cidade Cidade onde a carona ocorrerá
	 * @throws ProjetoCaronaException Lanca excecao caso a cidade informada for for null ou vazio
	 */
	private void setCidade(String cidade) throws ProjetoCaronaException {
		if ( (cidade == null) || (cidade.trim().equals("")) ){
			logger.debug("setDestino() Exceção: "+MensagensErro.CIDADE_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.CIDADE_INEXISTENTE);
		}
		this.cidade = cidade;
	}
	
	/**
	 * Método para retornar o horário da saída da carona
	 * @return Horário da carona
	 */
	public String getHora() {
		return hora;
	}
	
	/**
	 * Método para informar o horário de saída da carona
	 * @param hora Horário da carona
	 * @throws Exception Lança exceção se o horário da carona informado for null, vazio ou estiver fora do padrão - hora:minuto(hh:mm)
	 */
	public void setHora(String hora) throws Exception {
		//validacao da Hora
		ValidarCampos validar = new ValidarCampos();
		validar.validarHora(hora);
		
		this.hora = hora;
	}
	
	/**
	 * Método para retornar a data de saída da carona
	 * @return Data da carona
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Método para informar a data de saída da carona
	 * @param data Data da carona
	 * @throws Exception Lança exceção se a data informada estiver null, vazia ou estiver fora do padrão - dia/mês/ano(dd/mm/yyyy)
	 */
	public void setData(String data) throws Exception {
		//validacao da Data
		ValidarCampos validar = new ValidarCampos();
		validar.validarData(data);		
		
		this.data = data;
	}
	
	/**
	 * Método para retornar a data de volta da carona (no caso de carona relampago)
	 * @return Data da carona
	 */
	public String getDataVolta() {
		return dataVolta;
	}

	/**
	 * Método para informar a data de volta da carona (no caso de carona relampago)
	 * @param data Data da carona
	 * @throws Exception Lança exceção se a data informada estiver null, vazia ou estiver fora do padrão - dia/mês/ano(dd/mm/yyyy)
	 */
	public void setDataVolta(String dataVolta) throws Exception {
		//validacao da Data
		ValidarCampos validar = new ValidarCampos();
		validar.validarData(dataVolta);
	
		this.dataVolta = dataVolta;
	}
	
	/**
	 * Método para retornar a quantidade de vagas disponíveis na carona
	 * @return Vagas disponíveis na carona
	 */
	public int getVagas() {
		return vagas;
	}
	
	/**
	 * Método para informar a quantidade de vagas disponíveis na carona
	 * @param vagas Vagas disponíveis na carona
	 */
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	/**
	 * Método para aumentar a quantidade de vagas disponíves na carona
	 */
	public void aumentaVagas(){
		this.vagas++;
	}
	
	/**
	 * Método para diminuir a quantidade de vagas disponíves na carona
	 */
	public void diminuiVagas(){
		this.vagas--;
	}

	/**
	 * Metodo para retornar a quantidade minima de caroneiros na viagem (no caso de carona relampago)
	 * @return Quantidade minima de caroneiros na viagem
	 */
	public int getMinimoCaroneiros() {
		return minimoCaroneiros;
	}

	/**
	 * Metodo para informar a quantidade minima de caroneiros na viagem (no caso de carona relampago)
	 * @param minimoCaroneiros Quantidade minima de caroneiros na viagem
	 * @throws ProjetoCaronaException 
	 */
	public void setMinimoCaroneiros(int minimoCaroneiros) throws ProjetoCaronaException {
		if (minimoCaroneiros == 0){
			logger.debug("setMinimoCaroneiros() Exceção: "+MensagensErro.MINIMO_CARONEIROS_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.MINIMO_CARONEIROS_INVALIDO);
		}
		this.minimoCaroneiros = minimoCaroneiros;
	}

	/**
	 * Metodo para retornar se a carona esta expirada ou nao
	 * @return boolean Informacao se a carona esta expirada
	 */
	public boolean isCaronaRelampagoExpirada() {
		return caronaRelampagoExpirada;
	}

	/**
	 * Metodo para informar se a carona esta expirada ou nao
	 * @param caronaRelampagoExpired Informacao se a carona esta expirada
	 */
	public void setCaronaRelampagoExpirada(boolean caronaRelampagoExpirada) {
		this.caronaRelampagoExpirada = caronaRelampagoExpirada;
	}

	/**
	 * Verifica se a carona é preferencial
	 * @return Retorna true se a carona for preferencial
	 */
	public boolean isPreferencial() {
		return preferencial;
	}

	/**
	 * Define se a carona será preferencial
	 * @param preferencial true para tornar a carona preferencial
	 */
	public void setPreferencial(boolean preferencial) {
		this.preferencial = preferencial;
	}

	/**
	 * Metodo para informar o tipo da carona
	 * Pode ser C=Carona Comum, M=Carona Municipal, e R=Carona Relampago
	 * @return Retorna o tipo da carona
	 */
	public String getTipoCarona() {
		return tipoCarona;
	}

	/**
	 * Define o tipo da carona
	 * Pode ser C=Carona Comum, M=Carona Municipal, e R=Carona Relampago
	 * @param tipoCarona Tipo da carona
	 * @throws Exception Lanca excecao se o tipo da carona for inválido
	 */
	public void setTipoCarona(String tipoCarona) throws Exception {
		if ((!tipoCarona.equals("C")) && (!tipoCarona.equals("M")) && !tipoCarona.equals("R")) {
			throw new Exception(MensagensErro.TIPO_CARONA_INVALIDO);
		}
		
		this.tipoCarona = tipoCarona;
	}

	
}
