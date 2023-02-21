<%@ include file="../init.jsp"%>
<jsp:include page="modales.jsp" />

<portlet:resourceURL id="/cotizadores/paso1/listaPersonas" var="listaPersonasURL" cacheability="FULL"/>
<portlet:resourceURL id="/CotizadorTransportistas/GuardaPaso1" var="guardaPaso1URL" cacheability="FULL"/>
<portlet:resourceURL id="/cotizadores/paso1/canalNegocio" var="canalNegocio" cacheability="FULL"/>
<portlet:resourceURL id="/cotizadores/validaAgente" var="validaAgenteURL" cacheability="FULL" />

<portlet:resourceURL id="/cotizadorTransportistas/GuardaCotizacionEndoso" var="guardaEndosoPaso1URL" cacheability="FULL"/>

<liferay-portlet:actionURL name="/CotizadorTransportistas/SavePaso1" var="savePaso1URL" />
<liferay-portlet:actionURL name="/CotizadorTransportistas/SavePaso2" var="savePaso2URL" />

<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css?v=${version}"> --%>
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css?v=${version}"> --%>

<section id="cotizadores-p1" class="upper-case-all">

	<div class="section-heading">
		<div class="container-fluid">
			<h4 class="title text-left"><liferay-ui:message key="CotizadorTransportistas.titulo" /></h4> 
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<ul class="stepper stepper-horizontal container-fluid">
					<li id="step1" class="active ">
						<a href="javascript:void(0)">
							<span class="circle">1</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoUno" />
							</span>
						</a>
					</li>
					<li id="step2">
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

	<div style="position: relative;">
		<liferay-ui:success key="consultaExitosa" message="CotizadorTransportistas.exito" />
<%-- 		<liferay-ui:error key="errorConocido" message="${errorMsg}" /> --%>
		<liferay-ui:error key="errorDesconocido" message="CotizadorTransportistas.erorDesconocido" />
	</div>

	<div class="container-fluid" id="divPaso1">
		<div class="row">
			<div class="col-md-6"></div>
			<c:if test="${numeroSolicitud}">
				<div class="col-md-3" style="text-align: right;">
					<div class="md-form form-group">
						<input id="txtSolicitud" type="text" name="txtSolicitud" class="form-control" value="${ infCot.solicitud }" disabled>
						<label class="active" for="txtSolicitud">
							<liferay-ui:message key="CotizadorTransportistas.titSolicitud" />
						</label>
					</div>
				</div>
			</c:if>
			<div class="col-md-3 divFolio" style="text-align: right;">
				<div class="md-form form-group">
<%-- 					<input id="txtFolioP1" type="text" name="txtFolioP1" class="form-control" value="${ inf.folio } - ${ inf.version }" disabled> --%>
					<input id="txtFolioP1" type="text" name="txtFolioP1" class="form-control" value="${ cotizadorData.folio} - ${cotizadorData.version}"  disabled>
					<label class="active" for="txtFolioP1">
						<liferay-ui:message key="CotizadorTransportistas.titFolio" />
					</label>
				</div>
			</div>
		</div>
	</div>

 	 
	<div class="padding70"  id="contPaso1">
		<h5>
			<liferay-ui:message key="CotizadorTransportistas.titDatContratante" />
		</h5>
		<br />

		<div class="row">
			<div class="col-md-12">
				<div class="form-inline divRdoTpClient">
					<div class="form-check">
						<input class="form-check-input form-control" name="group1" type="radio" id="radio_ce" value="0" checked="checked">
						<label class="form-check-label" for="radio_ce">
							<liferay-ui:message key="CotizadorTransportistas.rdbCliExP1" />
						</label>
					</div>
					<div class="form-check">
						<input class="form-check-input form-control" name="group1" type="radio" id="radio_cn" value="1">
						<label class="form-check-label" for="radio_cn">
							<liferay-ui:message key="CotizadorTransportistas.rdbCliNuP1" />
						</label>
					</div>
				</div>
			</div>
		</div>

		<div class="row data_cteext">
			<div class="col-md-3">
				<div class="md-form">
<%-- 					<input type="text" name="ce_rfc" id="ce_rfc" class="form-control " maxlength="13" pattern="^[a-zA-Z0-9]{4,10}$" value="${ cotizadorData.datosCliente.rfc }"> --%>
					<input type="text" name="ce_rfc" id="ce_rfc" class="form-control " maxlength="13" pattern="^[a-zA-Z0-9]{4,13}$" value="${ cotizadorData.datosCliente.rfc }">
					<label for="ce_rfc">
						<liferay-ui:message key="CotizadorTransportistas.lblRfcExP1" />
					</label>
				</div>
			</div>
			<div class="col-md-6">
				<div class="md-form">
					<input type="text" name="ce_nombre" id="ce_nombre" class="form-control "  value="${ cotizadorData.datosCliente.nombre } ${ cotizadorData.datosCliente.appPaterno } ${ cotizadorData.datosCliente.appMaterno }">
					<label for="ce_nombre">
						<liferay-ui:message key="CotizadorTransportistas.lblNomComExP1" />
					</label>
				</div>
			</div>
			<div class="col-md-3">
				<div class="md-form">
					<input type="text" name="ce_codigo" id="ce_codigo" class="form-control" value="${ cotizadorData.datosCliente.codigo }"  disabled>
					<label for="ce_codigo">
						<liferay-ui:message key="CotizadorTransportistas.lblCodClieExP1" />
					</label>
				</div>
			</div>
		</div>


		<div class="row data_ctenvo d-none">
			<div class="col-sm-12">
				<div class="row data_nuevotip">
					<div class="col-md-8 cn_ncEx">
						<div class="md-form form-group">
							<input type="text" id="cn_nombrecompleto" name="cn_nombrecompleto" class="form-control" disabled>
							<label for="cn_nombrecompleto">
								<liferay-ui:message key="CotizadorTransportistas.lblNomComExP1" />
							</label>
						</div>
					</div>
					<div class="col-md-4 cn_tpEx">
						<div class="form-inline tipo_persona">
							<div class="form-check">
								<input class="form-check-input form-control" name="group2" type="radio" id="cn_personamoral" checked="checked" value="2">
								<label class="form-check-label" for="cn_personamoral">
									<liferay-ui:message key="CotizadorTransportistas.lblTipPerNvMorP1" />
								</label>
							</div>
							<div class="form-check">
								<input class="form-check-input form-control" name="group2" type="radio" id="cn_personafisica" value="1">
								<label class="form-check-label" for="cn_personafisica">
									<liferay-ui:message key="CotizadorTransportistas.lblTipPerNvFisP1" />
								</label>
							</div>
						</div>
					</div>

					<div class="col-md-3 cn_rdEx d-none">
						<div class="row row justify-content-md-center">
							<label class="pb-2"> Extranjera:</label>
						</div>
						<div class="row row justify-content-md-center">
							<div class="switch">
								<label>
									No
									<input id="chktoggle" type="checkbox">
									<span class="lever"></span>
									Si
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="md-form">
							<input type="text" id="cn_rfc" name="cn_rfc " class="form-control " maxlength="13" pattern="^[a-zA-Z0-9]{4,10}$">
							<label for="cn_rfc">
								<liferay-ui:message key="CotizadorTransportistas.lblRfcExP1" />
							</label>
						</div>
					</div>

					<div class="col-md-9 px-0 tip_moral divPerMor">
						<div class="col-md-6">
							<div class="md-form">
								<input type="text" id="cn_nombrecontratante" name="cn_nombrecontratante" class="form-control">
								<label for="cn_nombrecontratante">
									<liferay-ui:message key="CotizadorTransportistas.lblNomConNvMoP1" />
								</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="md-form form-group">
								<select name="cn_denominacion" id="cn_denominacion" class="mdb-select form-control-sel colorful-select dropdown-primary" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
									<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
									<c:forEach items="${listaCatDenominacion}" var="option">
										<option value="${option.idCatalogoDetalle}">${option.valor}</option>
									</c:forEach>
								</select>
								<label for="cn_denominacion">
									<liferay-ui:message key="CotizadorTransportistas.lblDenominaNvMoP1" />
								</label>
							</div>
						</div>
					</div>



					<div class="col-md-9 px-0 tip_fisica" style="display: none">

						<div class="col-md-4">
							<div class="md-form">
								<input type="text" id="cn_fisnombre" name="cn_fisnombre" class="form-control">
								<label for="cn_fisnombre">
									<liferay-ui:message key="CotizadorTransportistas.lblNomFisicaP1" />
								</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="md-form">
								<input type="text" id="cn_fispaterno" name="cn_fispaterno" class="form-control ">
								<label for="cn_fispaterno">
									<liferay-ui:message key="CotizadorTransportistas.lblApPatFisicaP1" />
								</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="md-form">
								<input type="text" id="cn_fismaterno" name="cn_fismaterno" class="form-control ">
								<label for="cn_fismaterno">
									<liferay-ui:message key="CotizadorTransportistas.lblApMatFisicaP1" />
								</label>
							</div>
						</div>
					</div>






				</div>


			</div>
		</div>

		<div class="data_cotizacion">
			<br />
			<h5>
				<liferay-ui:message key="CotizadorTransportistas.titDatosCotizacion" />
			</h5>
			<br />
			<div class="row">
				<div class="col-md-12">
					<div class="form-inline form-left float-left divRdoVigencia">
						<div class="form-check">
							<input class="form-check-input form-control anualCotizacion" name="group3" type="radio" id="dc_declaracion" ${ cotizadorData.vigencia == 1 ? 'checked' : '' } value="1">
							<label class="form-check-label" for="dc_declaracion">
								<liferay-ui:message key="CotizadorTransportistas.p1Declaracion" />
							</label>
						</div>
						<div class="form-check">
							<input class="form-check-input form-control anualCotizacion" name="group3" type="radio" id="dc_pronostico" ${ cotizadorData.vigencia == 2 ? 'checked' : '' } value="2">
							<label class="form-check-label" for="dc_pronostico">
								<liferay-ui:message key="CotizadorTransportistas.p1Pronostico" />
							</label>
						</div>
						<div class="form-check">
							<input class="form-check-input form-control anualCotizacion" name="group3" type="radio" id="dc_viaje" ${ cotizadorData.vigencia == 3 ? 'checked' : '' } value="3">
							<label class="form-check-label" for="dc_viaje">
								<liferay-ui:message key="CotizadorTransportistas.p1Viaje" />
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-3">
					<div class="md-form form-group">
						<select name="dc_movimientos" id="dc_movimientos" class="mdb-select form-control-sel colorful-select dropdown-primary" disabled>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaMovimiento}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ 207 ==  option.idCatalogoDetalle ? 'selected' : ''}>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="dc_movimientos">
							<liferay-ui:message key="CotizadorTransportistas.lblTipoMovimientoDtsCotizaP1" />
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="md-form form-group">
						<select name="dc_moneda" id="dc_moneda" class="mdb-select form-control-sel">
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatMoneda}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ cotizadorData.moneda ==  option.idCatalogoDetalle ? 'selected' : ''}>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="dc_moneda">
							<liferay-ui:message key="CotizadorTransportistas.lblMonedaDtsCotizaP1" />
						</label>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="md-form form-group">
						<div class="row">
							<div class="col">
								<input placeholder="Fecha Desde" type="date" id="dc_dateDesde" name="dc_dateDesde" class="form-control datepicker paso1Fecha" value="${ fechaHoy }" >
								<label for="dc_dateDesde">
									<liferay-ui:message key="CotizadorTransportistas.lblDesdeDtsCotizaP1" />
								</label>
							</div>
							<div class="col">
								<input placeholder="Fecha Hasta" type="date" id="dc_dateHasta" name="dc_dateHasta" class="form-control datepicker paso1Fecha" value="${ fechaMasAnio }"  disabled> 
								<label for="dc_dateHasta">
									<liferay-ui:message key="CotizadorTransportistas.lblHastaDtsCotizaP1" />
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-3">
					<div class="md-form form-group">
						<select name="dc_agentes" id="dc_agentes" class="mdb-select form-control-sel colorful-select dropdown-primary" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
							<c:if test="${fn:length(listaAgentes) gt 1}">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							</c:if>
							<c:forEach items="${listaAgentes}" var="option">
								<option value="${option.idPersona}" ${ cotizadorData.agente ==  option.idPersona ? 'selected' : ''}>${option.nombre}${option.appPaterno}${option.appMaterno}</option>
							</c:forEach>
						</select>
						<label for="dc_agentes">
							<liferay-ui:message key="CotizadorTransportistas.lblAgentesDtsCotizaP1" />
						</label>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="md-form form-group ${dNonePerfil}">
						<select name="dc_canalNegocio" id="dc_canalNegocio" class="mdb-select form-control-sel" disabled>
							<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
<%-- 							<c:forEach items="${listaCanalNegocio}" var="option"> --%>
							<c:forEach items="${listaCatCanalNegocio}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ cotizadorData.canalNegocio ==  option.idCatalogoDetalle ? 'selected' : ''}>${option.valor}</option>
							</c:forEach>
						</select>
						<label for="dc_canalNegocio">
							<liferay-ui:message key="CotizadorTransportistas.p1CanalNegocio" />
						</label>
					</div>
				</div>

				<div class="col-6 px-0 empresarial_giros">
					<div class="row">
						<div class="col-sm-6">
							<div class="md-form form-group">
								<select name="dc_formpago" id="dc_formpago" class="mdb-select form-control-sel" ${ cotizadorData.vigencia == 3 ? 'disabled' : '' }>
									<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
									<c:forEach items="${listaCatFormaPago}" var="option">
										<c:if test="${ option.codigo == 'S' }">
											<c:if test="${ perfilSuscriptorJ == 1 }">
												<option value="${option.idCatalogoDetalle}" ${ cotizadorData.formaPago ==  option.idCatalogoDetalle ? 'selected' : ''}>${option.valor}</option>
											</c:if>
										</c:if>
										<c:if test="${ option.codigo != 'S' }">
											<option value="${option.idCatalogoDetalle}" ${ cotizadorData.formaPago ==  option.idCatalogoDetalle ? 'selected' : ''}>${option.valor}</option>
										</c:if>
									</c:forEach>
								</select>
								<label for="dc_formpago">
									<liferay-ui:message key="CotizadorTransportistas.lblPagoDtsCotizaP1" />
								</label>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="md-form form-group">
								<input id="dc_giro" type="text" name="dc_giro" class="form-control" maxlength="150" value="${ cotizadorData.giroTransporte }">
								<label for="dc_giro">
									<liferay-ui:message key="CotizadorTransportistas.lblGiroDtsCotizaP1" />
									<span class="spmodal"   id="tooltip"><i class="fa fa-question-circle" aria-hidden="true"></i></span>
									
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-sm-3">
					<div class="md-form form-group ${dNonePerfil}">
						<select name="dc_coaseguro" id="dc_coaseguro" class="mdb-select form-control-sel colorful-select dropdown-primary" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
						<option value="-1"><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCoaseguro}" var="option">
								<c:if test="${option.codigo != '4'}">
									<option value="${option.idCatalogoDetalle}" ${ cotizadorData.coaseguro ==  option.idCatalogoDetalle ? 'selected' : ''}
																				${ cotizadorData==null && option.valor=='DIRECTO' ? 'selected' : ''} >${option.valor}</option>
								</c:if>
							</c:forEach>
						</select>
						<label for="dc_coaseguro">
							<liferay-ui:message key="CotizadorTransportistas.p1Coaseguros" />
						</label>
					</div>
				</div>
				<div class="col-sm-3 ${idPerfilUser == 4 || idPerfilUser == 5 || idPerfilUser == 6 ? '': 'd-none'}">
					<div class="md-form form-group">
						<select name="dc_sector" id="dc_sector" class="mdb-select form-control-sel colorful-select dropdown-primary" searchable='<liferay-ui:message key="CotizadorTransportistas.buscar" />'>
						<option value="-1"><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
							<c:forEach items="${listaCatSector}" var="option">
								<option value="${option.idCatalogoDetalle}" ${ cotizadorData.sector ==  option.idCatalogoDetalle ? 'selected' : ''} 
																								${ cotizadorData==null && option.valor=='PRIVADO' ? 'selected' : ''} >${option.valor}</option>
							</c:forEach>
						</select>
						<label for="dc_sector">
							<liferay-ui:message key="CotizadorTransportistas.p1Sector" />
						</label>
					</div>
				</div>
			</div>
			</div>
				<div class="row">
					<div class="col-sm-12 text-right">
						<button class="btn btn-pink" id="paso1_next">Continuar</button>
						<button class="btn btn-pink d-none" id="guardaPaso1Endoso">Continuar</button>
					</div>
				</div>
			</div>
	<form class="padding70" id="formPaso1"  action="<%=savePaso1URL%>" method="POST">
		<input type="hidden" id="cotizacion" name="cotizacion" class="d-none" value="" />
		<input type="hidden" id="folioCoti" name="folioCoti" class="d-none" value="" />
		<input type="hidden" id="versionCoti" name="versionCoti" class="d-none" value="" />
		<input type="hidden" id="canalNegocioInf" name="canalNegocioInf" class="d-none" value="" />
		<input type="hidden" id="paso" name="paso" class="d-none" value="paso2" />
		<input type="hidden" id="infoCot" name="infoCot" class="d-none" value="" />
		<input type="hidden" id="responseP1" name="responseP1" class="d-none" value="" />
		<input type="hidden" id="coaseguro" name="coaseguro" class="d-none" value="" />
	</form>
	
	<form action="<%=savePaso2URL%>" method="POST" hidden="true" id="form_paso3">
		<input type="hidden" id="paso" name="paso" class="d-none" value="paso3">
		<input type="hidden" id="folioCoti" name="folioCoti" class="d-none" value="${cotizadorData.folio}">
		<input type="hidden" id="versionCoti" name="versionCoti" class="d-none" value="${cotizadorData.version}">
		<input type="hidden" id="cotizacion" name="cotizacion" class="d-none" value="${cotizadorData.cotizacion}" />
		<input type="hidden" id="infoCotizacion" name="infoCotizacion" class="d-none" value="${infoCot}">
		<button id="action_next"></button>
	</form>
	
	<div id="infoCliente" class="d-none">
		<input type="hidden" id="infAppMaterno" name="infAppMaterno" class="d-none" value="${ cotizadorData.datosCliente.appMaterno }" />
		<input type="hidden" id="infAppPaterno" name="infAppPaterno" class="d-none" value="${ cotizadorData.datosCliente.appPaterno }" />
		<input type="hidden" id="infCodigo" name="infCodigo" class="d-none" value="${ cotizadorData.datosCliente.codigo }" />
		<input type="hidden" id="infExtranjero" name="infExtranjero" class="d-none" value="0" />
		<input type="hidden" id="infIdDenominacion" name="infIdDenominacion" class="d-none" value="${ cotizadorData.datosCliente.idDenominacion }" />
		<input type="hidden" id="infIdPersona" name="infIdPersona" class="d-none" value="${ cotizadorData.datosCliente.idPersona }" />
		<input type="hidden" id="infNonmbre" name="infNonmbre" class="d-none" value="${ cotizadorData.datosCliente.nombre }" />
		<input type="hidden" id="infRfc" name="infRfc" class="d-none" value="${ cotizadorData.datosCliente.rfc }" />
		<input type="hidden" id="infTipoPer" name="infTipoPer" class="d-none" value="${ cotizadorData.datosCliente.tipoPer }" />
	</div>
</section>

<!-- 	Scripts -->

 <script src="<%=request.getContextPath()%>/js/jquery-ui.min.js?v=${version}"></script> 
 <script src="<%=request.getContextPath()%>/js/main.js?v=${version}"></script> 
 <script src="<%=request.getContextPath()%>/js/objetos.js?v=${version}"></script>
 <script src="<%=request.getContextPath()%>/js/funcionesGenericas.js?v=${version}"></script>
 <script src="<%=request.getContextPath()%>/js/paso1.js?v=${version}"></script>
 <script src="<%=request.getContextPath()%>/js/tooltip/popper.min.js?v=${version}"></script>
 <script src="<%=request.getContextPath()%>/js/tooltip/tippy-bundle.umd.js?v=${version}"></script>



<script>

ligasServicios.listaPersonas = "${listaPersonasURL}";
ligasServicios.guardaInfo = "${guardaPaso1URL}";
ligasServicios.canalNegocio = "${canalNegocio}";
ligasServicios.guardaEndosoPaso1URL = "${guardaEndosoPaso1URL}";

var validaAgenteURL = '${validaAgenteURL}';

var infCotizacion = ${infCotizacionJson};
var diasRetro = ${retroactividad};
// 	var esRetroactivo = ${perfilMayorEjecutivo};	

 	var datosCliente = '${datosCliente}';
 	var cotizadorData = '${cotizadorData}';
// 	var infVigencia = '${cotizadorData.vigencia}';
// 	var infsubEstado = '${cotizadorData.subEstado}';

	var perfilSuscriptor = '${perfilSuscriptor}';
	
tippy('#tooltip', {
  content: "Especifique clara y brevemente la actividad del asegurado",
});	

	
</script>


