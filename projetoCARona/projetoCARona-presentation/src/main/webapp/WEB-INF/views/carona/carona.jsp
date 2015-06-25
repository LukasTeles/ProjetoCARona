<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-7">
		<div class="panel panel-default"><!--###DETALHES CARONA###-->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-4">
	  							Detalhes da Carona
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
						
						<div class="row">			  				
                				<div class="col-md-8">
			  						<span class="glyphicon glyphicon-map-marker"></span> De ${carona.origem} - para ${carona.destino}  (${carona.tipoCarona})<hr>
			  						<span class="fui-user"></span> <b>Motorista:</b> ${carona.nomeMotorista} <br>			  															
									<c:if test="${not empty carona.cidade}">
    									<span class="glyphicon glyphicon-globe"></span> <b>Cidade:</b> ${carona.cidade}<br>
									</c:if>		
			  					</div> 
			  					<div class="col-md-4">
			  						<span class="badge">${carona.vagas}</span> Vagas Dispon�veis<hr>
			  						<span class="fui-calendar"></span> <b>Sa�da:</b> ${carona.data} <br>
			  						<c:if test="${not empty carona.dataVolta}">
			  							<span class="fui-calendar"></span> <b>Volta:</b> ${carona.dataVolta} <br>
									</c:if>				
			  					</div>
			  				</div>
			  				<div class="row">
				  				<div class="col-md-12">
				  					<hr>
				  					<div class="col-md-6">
				  						<c:if test="${carona.solicitouVaga == false}">
			  								<a  href="solicitarVagaCarona.html?id=${carona.idCarona}" ><span class="fui-plus-circle"></span> <b>Solicitar Vaga</b></a>
										</c:if>
										<c:if test="${carona.solicitouVaga == true}">
			  								<a href="desistirVagaCarona.html?id=${carona.idCarona}&idSolicitacao=${carona.idSolicitacao}"><span class="glyphicon glyphicon-minus-sign"></span> <b>Desistir Vaga</b></a>
										</c:if>					  							
				  					</div>
								</div>
						</div>			
			        </div>
			    </div><!--###DETALHES CARONA###-->
			    <c:if test="${carona.vagaAceita == true }">
			    	<div class="panel panel-default"><!-- ###VAGAS### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Pessoas na carona
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numSolicitacoes == 0}">
	  						Ainda n�o h� mais pessoas nessa carona
	  					</c:if>
	  					<c:if test="${numSolicitacoes != 0}">
						<c:forEach items="${listaSolicitacoes}" var="solicitacao">
						<div class="row">
							<div class="col-md-3">
								<div class="profile-userpic">
									<img src="../images/user1.png" class="img-responsive" alt="">
								</div>
							</div>
							<div class="col-md-5">
								<b>${solicitacao.idUsuario}</b>
							</div>
						</div>
						<hr>
						</c:forEach>
						</c:if>		
			        </div>
			    </div><!-- ###VAGAS### -->
			    </c:if>
			</div>
			
			<div class="col-md-5"><!-- ###PONTOS### -->
				<div class="panel panel-default"><!--###MEUS AMIGOS###-->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Pontos Sugeridos
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numPontos == 0}">
	  						N�o h� pontos sugeridos para essa carona
	  					</c:if>
	  					<c:if test="${numPomtos != 0}">
						<c:forEach items="${listaPontos}" var="ponto">
						<div class="row">
							<div class="col-md-5"><!-- pontoDeEncontro -->
								<b>${ponto.pontoDeEncontro}</b>
							</div>
							<div class="col-md-1" style="border-left:1px solid #112;height:45px"></div>
							<div class="col-md-6">
								<c:if test="${ponto.foiAceita == true}">
									<dl class="palette">
									<span class="label label-success">Aceito</span>
									</dl>
								</c:if>
								<c:if test="${ponto.foiAceita == false}">
									<dl class="palette">
									<span class="label label-danger">Aguardando Aprova��o</span>
									</dl>
								</c:if>
							</div>
						</div>
						<hr>
						</c:forEach>
						</c:if>
						<form:form modelAttribute="ponto" class="form-inline" method="POST">
							<form:input path="idCarona" value="${carona.idCarona}" type="text" class="form-control" style="visibility:hidden" />
							<form:input path="ponto"  type="text" class="form-control" placeholder="Sugerir ponto de encontro"/>
							<form:button type="submit" class="btn btn-primary">Sugerir</form:button>
						</form:form>			
			        </div>
			    </div>
			</div>
	</div>
</div>