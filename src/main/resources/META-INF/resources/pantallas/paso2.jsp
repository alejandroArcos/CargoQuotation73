<%@ include file="../init.jsp"%>

<liferay-portlet:actionURL name="/CotizadorTransportistas/SavePaso2" var="savePaso2URL" />
<liferay-portlet:actionURL name="/CotizadorTransportistas/RegresaPaso1" var="regresaPaso1URL" />

<portlet:resourceURL id="/cotizadorTransportistas/origendestino" var="autoOrigenDestino" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/GuardaDatosPaso2" var="guardaDatosPaso2URL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/GuardaRutas" var="guardaRutasURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/getRutasPaso2" var="consultaRutasURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/enviaArchivos" var="enviaArchivosURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/validaSuscripcion" var="validaSuscripcionURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/sendMailAgenteSuscriptor" var="sendMailAgenteSuscriptorURL" cacheability="FULL" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css?v=${version}">

<fmt:setLocale value="es_MX" />

<section id="cotizadores-p2" class="upper-case-all">
	
	<div class="section-heading">
		<div class="container-fluid">
			<h4 class="title text-left"> <liferay-ui:message key="CotizadorTransportistas.titulo" /> </h4>
		</div>
	</div>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<ul class="stepper stepper-horizontal container-fluid">
					<li id="step1" class="completed">
						<a href="javascript:void(0)">
							<span class="circle">1</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoUno" />
							</span>
						</a>
					</li>
					<li id="step2" class="active">
						<a href="javascript:void(0)">
							<span class="circle">2</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoDos" />
							</span>
						</a>
					</li>
					<li id="step3">
						<a href="javascript:void(0)">
							<span class="circle">3</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoTres" />
							</span>
						</a>
					</li>
					<li id="step4">
						<a href="javascript:void(0)">
							<span class="circle">4</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoCuatro" />
							</span>
						</a>
					</li>
				</ul>

			</div>
		</div>
	</div>
	
	<div class="container-fluid" id="divPaso2">
		<div class="section-heading">
			<div class="row divFolio">
				<div class="col-md-9">
					<h5 class="title text-left padding70 mt-4">${ infP1.nombre } ${ infP1.appPaterno } ${ infP1.appMaterno }</h5>
				</div>
				<div class="col-md-3" style="text-align: right;">
					<div class="md-form form-group">
						<input id="txtFolioP2" type="text" name="txtFolioP2" class="form-control" value="${folioCoti} - ${versionCoti}"  disabled>
						<label class="active" for="txtFolioP1">
							<liferay-ui:message key="CotizadorTransportistas.titFolio" />
						</label>
					</div>
				</div>
			</div>
		</div>
		
		<form id="formPaso2" class="padding70" action="" method="POST">
			<div class="section-heading">
				<h5 class="title text-left">
					Condiciones de Aseguramiento
				</h5>
			</div>
			
			<div class="row">
			
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="tipoMercancia" id="tipoMercancia" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:if test="${infP1.vigencia == 3}">
								<c:forEach items="${listaCatTipoMercancia}" var="option">
									<c:if test="${ fn:contains(option.valor, 'MAQUINARIA')  }">
										<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.tipoMercancia == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${infP1.vigencia != 3}">
								<c:forEach items="${listaCatTipoMercancia}" var="option">
									<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.tipoMercancia == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
								</c:forEach>
							</c:if>
						</select>
						<label for="tipoMercancia">
							Tipo de Mercancía
						</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="clausulaIL" id="clausulaIL" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatClausula}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.clausula == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="clausulaIL">
							Cláusula IL
						</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="selTipoBienes" id="selTipoBienes" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatTipoBienes}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.tipoBienes == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="selTipoBienes">
							Tipo de Bienes
						</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="selTipoPronostico" id="selTipoPronostico" class="mdb-select form-control-sel colorful-select dropdown-primary ${infCot.poliza == 3 ? '' :  'requerido' }" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatTipoPronostico}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.tipoPronostico == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="selTipoPronostico">
							Tipo de Pronóstico
						</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="selTerritorialidad" id="selTerritorialidad" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatTerritorialidad}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.territorialidad == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="selTerritorialidad">
							Territorialidad
						</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="selTraOrigen" id="selTraOrigen" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatOrigenDestino}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.origen == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="selTraOrigen">Trayecto Origen</label>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="md-form form-group">
						<select name="selTraDestino" id="selTraDestino" class="mdb-select form-control-sel colorful-select dropdown-primary requerido" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatOrigenDestino}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ infoTransportes.condicionesAseguramiento.destino == option.idCatalogoDetalle ? 'selected' : '' }>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="selTraDestino">
							Trayecto Destino
						</label>
					</div>
				</div>

				<div class="col-md-3 d-none">
					<div class="md-form form-group">
						<input type="text" id="primaDeposito" name="primaDeposito" class="form-control moneda" value="${ infoTransportes.condicionesAseguramiento.primaDeposito }">
						<label for="primaDeposito">
							Prima en Depósito
						</label>
					</div>
				</div>
				
				<div class="col-md-12">
					<div class="md-form">
						<textarea id="bienesTransportar" name="bienesTransportar" class="md-textarea form-control" rows="5" maxlength="1000" style="text-transform: uppercase;">${infoTransportes.condicionesAseguramiento.bienesTransportar}</textarea>
						<label for="dr_DescripcionConstruccion">Bienes a Transportar</label>
					</div>
				</div>
			
			</div>
			
			<div class="row mt-5">
				
				<div class="col-md-12">
					<!--Accordion wrapper-->
					<div class="accordion md-accordion" id="accordionEx" role="tablist" aria-multiselectable="true">
						<div class="card d-none" id="accordionEndoso">
							<div class="card-header btn-blue modificado" role="tab" id="headingInformacionEndoso">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseInformacionEndoso" aria-expanded="false" aria-controls="collapseInformacionEndoso">
									<h5 class="mb-0">
										Información General de la Póliza
										<i class="fas fa-angle-down rotate-icon"></i>
									</h5>
								</a>
							</div>
							<div id="collapseInformacionEndoso" class="collapse" role="tabpanel" aria-labelledby="headingInformacionEndoso" data-parent="#accordionEx">
								<div class="card-body">
									<div class="row">
										<div class="col-md-6">
											<div class="md-form form-group">
												<input id="pronosticoEndoso" type="text" name="pronosticoEndoso" class="form-control moneda reqTxtEnd" value="" />
												<label for="pronosticoEndoso">
													Pronóstico Anual
												</label>
											</div>
										</div>
										<div class="col-md-6">
											<div class="md-form form-group">
												<input id="numeroDeclaracionEndoso" type="text" name="numeroDeclaracionEndoso " class="form-control moneda reqTxtEnd" value="" />
												<label for="numeroDeclaracionEndoso">
													Número de Declaración
												</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="md-form form-group">
												<input id="primaNetaEndoso" type="text" name="primaNetaEndoso" class="form-control moneda reqTxtEnd" value="" />
												<label for="primaNetaEndoso">
													Prima Neta Póliza Original
												</label>
											</div>
										</div>
										<div class="col-md-6">
											<div class="md-form form-group">
												<input id="montoDeclaracionEndoso" type="text" name="montoDeclaracionEndoso" class="form-control moneda reqTxtEnd" value="" />
												<label for="montoDeclaracionEndoso">
													Monto de Declaración
												</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="md-form form-group">
												<input id="cuotaFinalEndoso" type="text" name="cuotaFinalEndoso" class="form-control reqTxtEnd" value="" />
												<label for="cuotaFinalEndoso">
													Cuota Final de Póliza Original (%)
												</label>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Accordion card -->
						<div class="card " id="accordionLimitesGenerales">
							<!-- Card header -->
							<div class="card-header btn-blue modificado" role="tab" id="headingDatosContratante">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseDatosContratante" aria-expanded="false" aria-controls="collapseDatosContratante">
									<h5 class="mb-0">
										LÍMITES GENERALES
										<i class="fas fa-angle-down rotate-icon"></i>
									</h5>
								</a>
							</div>
							
							<div id="collapseDatosContratante" class="collapse" role="tabpanel" aria-labelledby="headingDatosContratante" data-parent="#accordionEx">
								<div class="card-body">
								
									<div class="row d-flex justify-content-center">
										
									</div>
									<div class="row">
										<div class="col-md-3">
											<div class="md-form form-group">
												<input id="intLMaxEmgarbue" type="text" name="intLMaxEmgarbue" class="form-control moneda reqTxt" value="${ infoTransportes.limitesGenerales.limiteMaxEmbarque }" />
												<label for="intLMaxEmgarbue">
													Límite Máximo por Embarque
												</label>
											</div>
										</div>
										<div class="col-md-4 ml-5">
											<div class="md-form input-group ml-5">
												<span class="mt-2 mr-5">Robo</span>
												<div class="input-group-prepend mr-4">
													<div class="input-group-text md-addon">
														<input class="form-check-input check-Input" type="checkbox" value="" id="checkRobo" name="checkRobo" fId="inRobo" ${ (infoTransportes.limitesGenerales.robo == 1) || (infCot.modo == 'NUEVA') ? 'checked': '' }>
														<label class="form-check-label" for="checkRobo">
													</div>
												</div>
												<input type="text" id="inRobo" name="inRobo" class="form-control moneda" style="width: auto;" value="${ infoTransportes.limitesGenerales.monto }" readonly>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-3">
											<div class="md-form form-group">
												<input id="intLMaxEstadia" type="text" name="intLMaxEstadia" class="form-control moneda" value="${ infoTransportes.limitesGenerales.limiteMaxEstadia }" />
												<label for="intLMaxEstadia">
													Límite Máximo por Estadía
												</label>
											</div>
										</div>
									</div>
									<div class="row mt-4">
										<div class="col-md-6">
											<div class="row">
												<div class="col-md-6">
													<p>MEDIO DE TRANSPORTE</p>
												</div>
												<div class="col-md-6">													
													<p>PRONOSTICO ANUAL</p>
												</div>
											</div>
											<div class="row">
												<div class="col-md-5">
													<p class="mt-4">Terrestre</p>
												</div>
												<div class="col-md-5">
													<div class="md-form form-group md-form-magin">
														<input id="inTerrestre" type="text" name="inTerrestre" class="form-control moneda unoDeTres" value="${ infoTransportes.limitesGenerales.pronosticoTerrestre.toString() }" />
													</div>
												</div>
												<div class="col-md-2"></div>
												
												<div class="col-md-5">
													<p class="mt-4">Marítimo - Aéreo</p>
												</div>
												<div class="col-md-5">
													<div class="md-form form-group md-form-magin">
														<input id="inMaritimo" type="text" name="inMaritimo" class="form-control moneda unoDeTres" value="${ infoTransportes.limitesGenerales.pronosticoMaritimo }" />
													</div>
												</div>
												<div class="col-md-2"></div>
													
												<div class="col-md-5">
													<p class="mt-4">Intermodal</p>
												</div>
												<div class="col-md-5">
													<div class="md-form form-group md-form-magin">
														<input id="inIntermodal" type="text" name="inIntermodal" class="form-control moneda unoDeTres" value="${ infoTransportes.limitesGenerales.pronosticoIntermodal }" />
													</div>
												</div>
												<div class="col-md-2"></div>
												
												<div class="col-md-10">
													<div class="md-form input-group mt-0 mb-3">
														<span class="mt-2 mr-5">Envíos por mensajería</span>
														<div class="input-group-prepend mr-4">
															<div class="input-group-text md-addon">
																<input class="form-check-input check-Input" type="checkbox" fId="inEnvMensajeria" value="" id="checkEnvMensajeria" name="checkEnvMensajeria" ${ infoTransportes.limitesGenerales.mensajeria == 1 ? 'checked': '' }>
																<label class="form-check-label" for="checkEnvMensajeria">
															</div>
														</div>
														<input type="text" id="inEnvMensajeria" name="inEnvMensajeria" class="form-control moneda" style="width: auto;" value="${ infoTransportes.limitesGenerales.montoMensajeria }" />
													</div>
												</div>
												<div class="col-md-2"></div>
												
											</div>	
										</div>
										<div class="col-md-6 pl-5 ${ idPerfilUser == 1 || idPerfilUser == 2 ? 'd-none' : ''}">
											<p>¿Cuenta con el siguiente servicio?</p>
											
											
												<div class="form-check mt-4">
												    <input type="checkbox" class="form-check-input" id="checkPrevencionCG" name="checkPrevencionCG" ${ infoTransportes.limitesGenerales.prevencion == 1 ? 'checked': '' }>
												    <label class="form-check-label" for="checkPrevencionCG">Prevención <span id="tooltip0"><i class="fa fa-question-circle" aria-hidden="true"></i></span></label>
												</div>
	
												<div class="form-check mt-4">
												    <input type="checkbox" class="form-check-input" id="checkMedioTransporteCG" name="checkMedioTransporteCG" ${ infoTransportes.limitesGenerales.medioCertificado == 1 ? 'checked': '' }>
												    <label class="form-check-label" for="checkMedioTransporteCG">Medio de transporte <span id="tooltip1"><i class="fa fa-question-circle" aria-hidden="true"></i></span></label>
												</div>
	
												<div class="form-check mt-4">
												    <input type="checkbox" class="form-check-input" id="checkViasCG" nomae="checkViasCG" ${ infoTransportes.limitesGenerales.viasComunicacion == 1 ? 'checked': '' }>
												    <label class="form-check-label" for="checkViasCG">Vías de Comunicación <span id="tooltip2"><i class="fa fa-question-circle" aria-hidden="true"></i></span></label>
												</div>
											
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-12 text-right d-none">
											<div class="btn btn-blue" id="paso2_rutas" onclick="consultaRutas();">Rutas</div>
<!-- 											<a class="btn btn-blue" id="paso2_cuotas">Cuotas</a> -->
										</div>
									</div>
									
								</div>
							</div>
						</div>
						<!-- /Accordion card -->

						<!-- Accordion card -->
						<div class="card " id="accordionCoberturas">
							<!-- Card header -->
							<div class="card-header btn-blue modificado" role="tab" id="headingCoberturasAdicionales">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseCoberturasAdicionales" aria-expanded="false" aria-controls="collapseDatosContratante">
									<h5 class="mb-0">
										COBERTURAS ADICIONALES
										<i class="fas fa-angle-down rotate-icon"></i>
									</h5>
								</a>
							</div>
							
							<div id="collapseCoberturasAdicionales" class="collapse" role="tabpanel" aria-labelledby="headingCoberturasAdicionales" data-parent="#accordionEx">
								<div class="card-body">
									<div class="row">
										<div class="col-md-12 divMaxestadia">
											<div class="col-md-4 divMaxestadia">
												<p class="mt-4">Interrupción en el Transporte (Estadía) hasta</p>
											</div>
											<div class="col-md-8 mt-4 divMaxestadia">
												<div class="row">
													<div class="col-md-3">
														<!-- Group of material radios - option 1 -->
														<div class="form-check">
															<input type="radio" class="form-check-input" id="interrupcion1" name="groupInterrupcionRadios" ${ infoTransportes.coberturasAdicionales.estadia == 30 ? 'checked': '' } value="30" checked="true">
															<label class="form-check-label" for="interrupcion1">30 días</label>
														</div>
													</div>
													<div class="col-md-3">
														<!-- Group of material radios - option 2 -->
														<div class="form-check">
														  <input type="radio" class="form-check-input" id="interrupcion2" name="groupInterrupcionRadios" ${ infoTransportes.coberturasAdicionales.estadia == 60 ? 'checked': '' } value="60">
														  <label class="form-check-label" for="interrupcion2">60 días</label>
														</div>
													</div>
													<div class="col-md-3">
														<!-- Group of material radios - option 3 -->
														<div class="form-check">
														  <input type="radio" class="form-check-input" id="interrupcion3" name="groupInterrupcionRadios" ${ infoTransportes.coberturasAdicionales.estadia == 120 ? 'checked': '' } value="120">
														  <label class="form-check-label" for="interrupcion3">120 días</label>
														</div>
													</div>
													<div class="col-md-3">
														<!-- Group of material radios - option 3 -->
														<div class="form-check">
														  <input type="radio" class="form-check-input" id="interrupcion4" name="groupInterrupcionRadios" ${ infoTransportes.coberturasAdicionales.estadia == 180 ? 'checked': '' } value="180">
														  <label class="form-check-label" for="interrupcion4">180 días</label>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-12 d-none" id="divAverias">
											<div class="col-md-4">
												<p class="mt-4">Averías en el sistema de Refrigeración</p>
											</div>
											<div class="col-md-8">
												<div class="form-check mt-4">
												    <input class="form-check-input" type="checkbox" value="" id="checkAverias" name="checkAverias" ${ infoTransportes.coberturasAdicionales.averias == 1 ? 'checked': '' }>
													<label class="form-check-label" for="checkAverias">
												</div>
											</div>
										</div>
										<div class="col-md-12 d-none" id="divHuelgasTerrestre">
											<div class="col-md-4">
												<p class="mt-4">Huelgas Terrestre</p>
											</div>
											<div class="col-md-8">
												<div class="form-check mt-4">
												    <input class="form-check-input" type="checkbox" value="" id="checkHTerrestre" name="checkHTerrestre" ${ infoTransportes.coberturasAdicionales.huelgaTerrestre == 1 ? 'checked': '' }>
													<label class="form-check-label" for="checkHTerrestre">
												</div>
											</div>
										</div>
									
									

											<div class="col-md-4"></div>
											<div class="col-md-3 divSubPronostivo text-right">
												<a>Sub Pronóstico Cuota</a>
											</div>
											<div class="col-md-5"></div>

										

											<div class="col-md-12 d-none" id="divHuelgasMaritimo">
												<div class="col-md-4">
													<p class="mt-2">Huelgas Marítimo</p>
												</div>
												<div class="col-md-3">
													<div class="md-form input-group mt-0 mb-3 ml-2">
														<div class="input-group-prepend mr-4">
															<div class="input-group-text md-addon">
																<input class="form-check-input check-Input" type="checkbox" value="" id="checkHMaritimo" name="checkHMaritimo" fId="inHMaritimo" ${ infoTransportes.coberturasAdicionales.huelgaMaritimo == 1 ? 'checked': '' }>
																<label class="form-check-label" for="checkHMaritimo">
															</div>
														</div>
														<input type="text" id="inHMaritimo" name="inHMaritimo" class="form-control moneda text-right" style="width: auto;" value="${ infoTransportes.coberturasAdicionales.montoHMaritimo }">
													</div>
												</div>
												<div class="col-md-5"></div>
											</div>
											<div class="col-md-12 d-none" id="divHuelgasAereo">
												<div class="col-md-4">
													<p class="mt-2">Huelgas Aéreo</p>
												</div>
												<div class="col-md-3">
													<div class="md-form input-group mt-0 mb-3 ml-2">
														<div class="input-group-prepend mr-4">
															<div class="input-group-text md-addon">
																<input class="form-check-input check-Input" type="checkbox" value="" id="checkHAereo" name="checkHAereo" fId="inHAereo" ${ infoTransportes.coberturasAdicionales.huelgaAereo == 1 ? 'checked': '' }>
																<label class="form-check-label" for="checkHAereo">
															</div>
														</div>
														<input type="text" id="inHAereo" name="inHAereo" class="form-control moneda text-right" style="width: auto;" value="${ infoTransportes.coberturasAdicionales.montoHAereo }">
													</div>
												</div>
												<div class="col-md-5"></div>
											</div>
											<div class="col-md-12 d-none divMaxestadia" id="divHuelgasEstadía">
												<div class="col-md-4">
													<p class="mt-2">Huelgas Estadía</p>
												</div>
												<div class="col-md-3">
													<div class="md-form input-group mt-0 mb-3 ml-2">
														<div class="input-group-prepend mr-4">
															<div class="input-group-text md-addon">
																<input class="form-check-input check-Input" type="checkbox" value="" id="checkHEstadia" name="checkHEstadia" fId="inHEstadia" ${ infoTransportes.coberturasAdicionales.huelgaEstadia == 1 ? 'checked': '' }>
																<label class="form-check-label" for="checkHEstadia">
															</div>
														</div>
														<input type="text" id="inHEstadia" name="inHEstadia" class="form-control moneda text-right" style="width: auto;" value="${ infoTransportes.coberturasAdicionales.montoHEstadia }">
													</div>
												</div>
												<div class="col-md-5"></div>
											</div>

									</div>
								</div>
							</div>
						</div>
						<!-- /Accordion card -->
					
					</div>
				</div>
				
			</div>
			
		</form>
		<div class="row">
			<div class="col-sm-12 text-right">
				<button class="btn btn-blue" id="paso2_archivos" onclick="$('#modalArchivos').modal('show');">Adjuntar Archivos</button>
				<button class="btn btn-blue" id="paso2_back" onclick="clickBtnBack();">Regresar</button>
				<button class="btn btn-blue" id="paso2_save" onclick="saveDatosTransportes();">Guardar</button>
				<button class="btn btn-pink" id="paso2_next" onclick="clickBtnNext();" disabled>Continuar</button>
				<button class="btn btn-pink d-none" id="sendSuscrip" onclick="enviaSuscripcion();">Enviar a suscripción</button>
			</div>
		</div>
	</div>
	
</section>

<form action="<%=regresaPaso1URL%>" method="POST" hidden="true" id="form_regresarPaso1">
	<input type="hidden" id="paso" name="paso" class="d-none" value="paso1">
	<input type="hidden" id="folioCoti" name="folioCoti" class="d-none" value="${folioCoti}">
	<input type="hidden" id="versionCoti" name="versionCoti" class="d-none" value="${versionCoti}">
	<input type="hidden" id="infoCotizacion" name="infoCotizacion" class="d-none" value="${infoCot}">
	<input type="hidden" id="cotizacion" name="cotizacion" class="d-none" value="${cotizacion}" />
</form>

<form action="<%=savePaso2URL%>" method="POST" hidden="true">
	<input type="hidden" id="paso" name="paso" class="d-none" value="paso3">
	<input type="hidden" id="folioCoti" name="folioCoti" class="d-none" value="${folioCoti}">
	<input type="hidden" id="versionCoti" name="versionCoti" class="d-none" value="${versionCoti}">
	<input type="hidden" id="cotizacion" name="cotizacion" class="d-none" value="${cotizacion}" />
	<input type="hidden" id="infoCotizacion" name="infoCotizacion" class="d-none" value="${infoCot}">
	<button id="action_next"></button>
</form>

<!-- MODAL RUTAS -->
<div class="modal" id="modalAddRutas" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">RUTAS</h3>
				
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div id="bodyModalRuta" class="modal-body">
				<div class="row rowRutas">
					<div class="col-md-6 col-select-0">
						<p class="pRutaTit">Ruta 1</p>
						<div class="md-form form-group">
							<select name="inOrigen0" id="inOrigen0" class="mdb-select form-control-sel colorful-select dropdown-primary selCiudadO  requeridoS" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
								<c:forEach items="${listaCatOrigenDestino}" var="option">
									<option value="${option.idCatalogoDetalle}">${option.valor}</option>
								</c:forEach>
							</select>
							<label for="inOrigen0">Origen</label>
						</div>
						<div class="md-form form-group">
							<select name="inDestino0" id="inDestino0" class="mdb-select form-control-sel colorful-select dropdown-primary selCiudadD requeridoS" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
								<c:forEach items="${listaCatOrigenDestino}" var="option">
									<option value="${option.idCatalogoDetalle}">${option.valor}</option>
								</c:forEach>
							</select>
							<label for="inDestino0">Destino</label>
						</div>
<!-- 						<div id="box-inOrigen" class="md-form"> -->
<!-- 							<input type="text" id="inOrigen0" class="form-control ciudad-autocomplete txtCiudadO" codigoLugar="0"> -->
<!-- 							<label for="inOrigen">Origen</label> -->
<!-- 						</div> -->
<!-- 						<div id="box-inDestino" class="md-form"> -->
<!-- 							<input type="text" id="inDestino0" class="form-control ciudad-autocomplete txtCiudadD" codigoLugar="0"> -->
<!-- 							<label for="inDestino">Destino</label> -->
<!-- 						</div> -->
					</div>
					<div class="col-md-2">
						<div class="md-form mt-5">
							<input type="text" id="inPronosticoMdl0" class="form-control txtPronos moneda requeridoI">
							<label for="inPronosticoMdl0">Pronóstico</label>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form mt-5">
							<input type="text" id="inLmeMdl0" class="form-control txtLME moneda requeridoI">
							<label for="inLmeMdl0">LME</label>
						</div>
					</div>
					<div class="col-md-2 text-right">
						<a onclick="addRutaJsp();"> <i class="fa fa-plus-circle" aria-hidden="true"></i> </a>
						<div class="md-form">
							<input type="text" id="inCuotaMdl0" class="form-control txtCuota auxPorcen requeridoI">
							<label for="inCuotaMdl0">Cuota %</label>
						</div>
					</div>
				</div>

			</div><!--  -->
			<!--Footer-->
			<div class="modal-footer justify-content-center blue-gradient">
				<button type="button" class="btn btn-pink" style="display: none;">Cancelar</button>
				<button onclick="" type="button" class="btn btn-pink" data-dismiss="modal">Cancelar</button>
				<button id="saveRutas" onclick="saveRutas()" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>
<!-- MODAL RUTAS -->

<!-- MODAL ARCHIVOS -->
<div class="modal" id="modalArchivos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">
					<i class="fas fa-edit" aria-hidden="true"></i> ¿Requiere incluir información adicional?
				</h3>
				
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div id="bodyModalRuta" class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<p class="text-center">Archivos permitidos: PDF, Correo, Excel, Word, JPG y JPNG. Hasta 5MB</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 mt-4">
						<span>*Reporte de siniestralidad</span>
					</div>
					<div class="col-md-6">
						<div class="md-form">
							<div class="file-field">
								<a class="btn-floating blue-gradient mt-0 float-left">
									<i class="fas fa-paperclip" aria-hidden="true"></i>
									<input type="file" class="inFile" id="mdlFileReporte" name="mdlFileReporte"
									accept="application/msword, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, image/jpeg,
										.msg, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, message/rfc822">
						    	</a>
								<div class="file-path-wrapper">
									<input class="file-path" id="fileReporte" type="text" placeholder="Adjuntar" readonly>
							    </div>
							</div>
						</div>
					</div>
					<div class="col-md-2 mt-4">
						<a class="cleanFile"><i class="fa fa-trash" aria-hidden="true"></i></a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 mt-4">
						<span>Carta de no siniestro</span>
					</div>
					<div class="col-md-6">
						<div class="md-form">
							<div class="file-field">
								<a class="btn-floating blue-gradient mt-0 float-left">
									<i class="fas fa-paperclip" aria-hidden="true"></i>
									<input type="file" class="inFile" id="mdlfileCarta" name="mdlfileCarta"
									accept="application/msword, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, image/jpeg,
										.msg, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, message/rfc822">
						    	</a>
								<div class="file-path-wrapper">
									<input class="file-path " type="text" placeholder="Adjuntar" readonly>
							    </div>
							</div>
						</div>
					</div>
					<div class="col-md-2 mt-4">
						<a class="cleanFile"><i class="fa fa-trash" aria-hidden="true"></i></a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 mt-4">
						<span>URF</span>
					</div>
					<div class="col-md-6">
						<div class="md-form">
							<div class="file-field">
								<a class="btn-floating blue-gradient mt-0 float-left">
									<i class="fas fa-paperclip" aria-hidden="true"></i>
									<input type="file" class="inFile" id="mdlFileURF" name="mdlFileURF"
									accept="application/msword, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, image/jpeg,
										.msg, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, message/rfc822">
						    	</a>
								<div class="file-path-wrapper">
									<input class="file-path " type="text" placeholder="Adjuntar" readonly>
							    </div>
							</div>
						</div>
					</div>
					<div class="col-md-2 mt-4">
						<a class="cleanFile"><i class="fa fa-trash" aria-hidden="true"></i></a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 mt-4">
						<span>Otros Documentos</span>
					</div>
					<div class="col-md-6">
						<div class="md-form">
							<div class="file-field">
								<a class="btn-floating blue-gradient mt-0 float-left">
									<i class="fas fa-paperclip" aria-hidden="true"></i>
									<input type="file" class="inFile" id="mdlFileOtros" name="mdlFileOtros"
									accept="application/msword, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, image/jpeg,
										.msg, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, message/rfc822"
										multiple>
						    	</a>
								<div class="file-path-wrapper">
									<input class="file-path " type="text" placeholder="Adjuntar" readonly>
							    </div>
							</div>
						</div>
					</div>
					<div class="col-md-2 mt-4">
						<a class="cleanFile"><i class="fa fa-trash" aria-hidden="true"></i></a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="md-form">
							<textarea id="mdlFileComentarios" name="mdlFileComentarios" class="md-textarea form-control" rows="3" maxlength="500" style="text-transform: uppercase;"></textarea>
							<label for="mdlFileComentarios">Comentarios adicionales (opcionales)</label>
						</div>
					</div>
				</div>

			</div><!--  -->
			<!--Footer-->
			<div class="modal-footer justify-content-center blue-gradient">
				<button type="button" class="btn btn-pink" style="display: none;">Cancelar</button>
				<button onclick="closeModalFiles()" type="button" class="btn btn-pink" data-dismiss="modal">Cancelar</button>
				<button onclick="saveFilesPaso2()" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>
<!-- MODAL ARCHIVOS -->

<!-- Modal subir archivos paso 2  -->
<div class="modal" id="fileModal" tabindex="-1" role="dialog"
	aria-labelledby="fileModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="fileErrorPopup"></div>
			<div class="modal-header">
				<h5 class="modal-title" id="fileModalLabel">ADJUNTAR ARCHIVOS</h5>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-4">
						<span>Archivos permitidos, PDF, Excel, Word</span>
						<div class="md-form">
							<div class="file-field">
								<div class="btn btn-blue btn-rounded btn-sm float-left">
									<span><i class="fas fa-upload mr-2" aria-hidden="true"></i>Seleccionar
										Archivo</span> <input id="docAgenSusc" type="file"
										multiple="multiple" data-file_types="pdf|doc|docx|xls|xlsx">
								</div>
								<input id="infDocSuc" class="form-control" type="text"
									placeholder="Archivos" disabled>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-blue waves-effect waves-light" id="" data-dismiss="modal">Cancelar</button>
				<button class="btn btn-pink waves-effect waves-light" id="" data-dismiss="modal">Guardar</button>
			</div>
		</div>
	</div>
</div>
<div id="infAux" class="d-none">
	<div class="md-form form-group">
		<select name="selRutasAux" id="selRutasAux" class="mdb-select form-control-sel colorful-select dropdown-primary" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
			<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
			<c:forEach items="${listaCatOrigenDestino}" var="option">
				<option value="${option.idCatalogoDetalle}">${option.valor}</option>
			</c:forEach>
		</select>
		<label for="inOrigen0">Origen</label>
	</div>
</div>
<!-- end Modal subir archivos paso 2  -->

<script>
	var getOrigenDestino = "${ autoOrigenDestino }";
	var guardaRutasURL = "${guardaRutasURL}";
	var guardaDatosPaso2 = "${guardaDatosPaso2URL}";
	var consultaRutasURL = "${consultaRutasURL}";
	var enviaArchivosURL = "${enviaArchivosURL}";
	var validaSuscripcionURL = "${validaSuscripcionURL}";
	var sendMailAgenteSuscriptorURL = '${sendMailAgenteSuscriptorURL}';
	var infCotString = ${infCotizacion};
	var infTransportes = ${infoTransportesJson};
	var listaCatTipoPron = ${listaCatTipoPron};
	var listaCatTipoBie = ${listaCatTipoBie};
</script>

<script src="<%=request.getContextPath()%>/js/objetos.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.min.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/tooltip/popper.min.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/tooltip/tippy-bundle.umd.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/funcionesGenericas.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/paso2.js?v=${version}"></script>