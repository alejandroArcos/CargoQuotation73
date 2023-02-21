<%@ include file="../init.jsp"%>
<%@ include file="./modalesPaso3.jsp"%>

<liferay-portlet:actionURL name="/CotizadorTransportistas/RegresaPaso2" var="regresaPaso2URL" />
<liferay-portlet:actionURL name="/CotizadorTransportistas/Paso3Next" var="continuarPaso3URL" />

<portlet:resourceURL id="/cotizadores/paso3/rechazoCotizacion" var="txtRechazaCotizacionURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/getComisionesAgente" var="getComisionesAgenteURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/guardaComisionesAgente" var="guardaComisionesAgenteURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/enviarCotizacion" var="enviarCotizacionURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/continuarJK" var="continuarJKURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/recalculoPrima" var="recalculoPrimaURL" cacheability="FULL" />
<portlet:resourceURL id="/CotizadorTransportes/Deducibles" var="saveDeducibles" cacheability="FULL"/>
<portlet:resourceURL id="/CotizadorTransportes/saveClausulasA" var="saveClausulasA" cacheability="FULL"/>
<portlet:resourceURL id="/cotizadores/paso3/getClausulas" var="getClausulas" cacheability="FULL"/>
<portlet:resourceURL id="/CotizadorTransportes/plantillaSlip" var="plantillaSlipUrl" cacheability="FULL"/>
<portlet:resourceURL id="/CotizadorTransportes/generarSlip" var="generacionSlipUrl" cacheability="FULL"/>
<portlet:resourceURL id="/CotizadorTransportes/generarSlipSemi" var="generacionSlipSemiUrl" cacheability="FULL"/>
<portlet:resourceURL id="/cotizadores/paso3/recalculoPrimaDeposito" var="recalculoPrimaDepositoURL" cacheability="FULL" />
<portlet:resourceURL id="/CotizadorTransportes/getDeducibles" var="getDeducibles" cacheability="FULL"/>
<portlet:resourceURL id="/cotizadores/paso3/generaInfoReaseguro" var="generaInfoReaseguroURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/guardaSiniestralidad" var="guardaSiniestralidadURL" cacheability="FULL" />
<portlet:resourceURL id="/emisionArt492/getemision" var="getEmisionArt492Url" cacheability="FULL" />
<portlet:resourceURL id="/getDocsEmision" var="getDocsEmision" cacheability="FULL" />

<portlet:resourceURL id="/cotizadorTransportistas/enviaArchivos" var="enviaArchivosURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadorTransportistas/sendMailAgenteSuscriptor" var="sendMailAgenteSuscriptorURL" cacheability="FULL" />
<portlet:resourceURL id="/sendMailSuscriptorAgente" var="sendMailSuscriptorAgenteURL" cacheability="FULL"/>

<portlet:resourceURL id="/CotizadorTransportes/getSeccionComisionUrl" var="getSeccionComisionUrl" cacheability="FULL" />

<portlet:resourceURL id="/emisionData" var="getEmisionData" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso1/redirigePasoX" var="redirigeURL"  cacheability="FULL" />

<portlet:resourceURL id="/cotizadores/paso3/generaInfoCoaseguro" var="generaInfoCoaseguroURL" cacheability="FULL" />

<portlet:resourceURL id="/cotizadores/paso3/getPermisoEmision" var="getPermisoEmisionURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/validaAgente" var="validaAgenteURL" cacheability="FULL" />
<portlet:resourceURL id="/cotizadores/paso3/validarReaseguro" var="validarReaseguroURL" cacheability="FULL" />

<fmt:setLocale value="es_MX" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css?v=${version}">

<section id="cotizadores-p3" class="upper-case-all">
	<div class="section-heading">
		<div class="container-fluid">
			<h4 class="title text-left"> <liferay-ui:message key="CotizadorTransportistas.titulo" /> </h4>
		</div>
	</div>
	
	<div style="position: relative;">
		<liferay-ui:error key="reaseguroEliminado" message="${reaseguroEliminadoMsg}" />
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
					<li id="step2" class="completed">
						<a href="javascript:void(0)">
							<span class="circle">2</span>
							<span class="label">
								<liferay-ui:message key="CotizadorTransportistas.titPasoDos" />
							</span>
						</a>
					</li>
					<li id="step3" class="active">
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
	
	
	<div class="container-fluid" id="divPaso3">
		<div class="section-heading">
			<div class="row divFolio">
				<div class="col-md-9">
					<h5 class="title text-left padding70 mt-4">${caratulaResponse.cliente}</h5>
				</div>
				<div class="col-md-3" style="text-align: right;">
					<div class="md-form form-group">
						<input id="idFolio3" type="text" name="idFolio3" class="form-control" value="${infCotizacion.folio} - ${infCotizacion.version}" disabled>
						<label class="active" for="txtFolioP1">
							<liferay-ui:message key="CotizadorTransportistas.titFolio" />
						</label>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row" style="padding: 0 70px">
			<div id="divTbl" class="col-md-12">
				<table class="customTable" style="width: 100%;">
					<!-- <table class="table simple-data-table table-striped table-bordered" style="width:100%;"> -->
					<thead>
						<tr>
							<th>
								Coberturas
							</th>
							<th>
								L&iacute;mite M&aacute;ximo de Responsabilidad
							</th>
							<th>
								Deducibles
							</th>
						</tr>
					</thead>
					<tbody id="tabPaso3">
						<c:set var="bandera" value=""/>
						<c:forEach items="${caratulaResponse.datosCaratula}" var="opc">
							<%-- c:if test="${bandera != opc.contenedor}"> 
								<c:set var="bandera" value="${opc.contenedor}"/>
								<tr><th>${bandera}</th><td></td><td></td><c:if test="${perfilSuscriptorJ == 1}"><td></td></c:if></tr>
							</c:if --%>
							<tr><td>${opc.titulo}</td><td class="number">${opc.sa}</td><%-- c:if test="${perfilSuscriptorJ == 1}"><td class="number">${opc.prima}</td></c:if --%><td>${opc.deducible}</td></tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div id="divTblEndBj" class="col-md-12 d-none">
				<div class="row justify-content-center">
					<div id="titulosEndBj"></div>
					<div id="datosEndBj" class="table-wrapper-scroll-table">
						
					</div>
					<div id="totalEndBj"></div>
				</div>			
			</div>
		</div>
		
		<div class="row" style="padding: 0 70px;">
			<div class="col-md-3">
				<div class="row" id="art41">
					<div class="col-sm-12">
						<!-- Grid row -->
						<div class="form-row align-items-center">
							<!-- Grid column -->
							<div class="col-11">
								<!-- Material input -->
								<div class="md-form">
									<input type="text" id="txtArt41" class="form-control valPorcen">
									<label id="txtArt41" for="txtCederComision">Art. 41</label>
								</div>
							</div>
							<!-- Grid column -->
							<div class="col-1">
								<!-- Material input -->
								<div class="md-form input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text md-addon">%</span>
									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<button type="button" class="btn btn-pink waves-effect waves-light float-right btn-block" id="btnCederComision" name="btnCederComision">
									CEDER COMISI&Oacute;N
								</button>
							</div>
							<!-- Grid column -->
						</div>
						<!-- Grid row -->
					</div>
				</div>
				<c:if test="${ perfilSuscriptor == 1 || perfilJapones == 1}">
					<div class="row" id="aplicaDividendo">
						<div class="col-sm-12">
							<div class="form-check mt-4">
							    <input type="checkbox" class="form-check-input" id="checkDividendo" name="checkDividendo">
							    <label class="form-check-label" for="checkDividendo"> &iquest;Aplica dividendo?</label>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="md-form">
								<input type="text" id="txtDividendo" name="txtDividendo" class="form-control d-none" maxlength="100" value="${caratulaResponse.p_dividendo}">
							</div>
						</div>
					</div>
				</c:if>
			
			</div>
			<div class="col-md-5">
				<table class="table-borderless" style="width: 100%;">
					<tbody id="tabPaso3_2">
						<tr><td>Prima Neta:</td><td id="primaNeta" class="number"><fmt:formatNumber value = "${caratulaResponse.primaNeta}" type = "currency"/> </td></tr>
						<tr><td>Recargo por Pago Fraccionado:</td><td class="number"><input id="recargoPago" class="moneda campoEditable" value="${caratulaResponse.recargo}" disabled="true" /></td><c:if test="${perfilSuscriptor == 1 || perfilJapones == 1}"><td><a onclick="editarCamposPrima('recargoPago')" style="color: #0275d8; text-decoration: underline;">Editar</a></td></c:if></tr>
						<tr><td>Gastos de Expedici&oacute;n:</td><td class="number"><input id="gastos" class="moneda campoEditable" value="${caratulaResponse.gastos}" disabled="true" /></td><c:if test="${perfilSuscriptor == 1 || perfilJapones == 1}"><td><a onclick="editarCamposPrima('gastos')" style="color: #0275d8; text-decoration: underline;">Editar</a></td></c:if></tr>
						<tr><td>I.V.A.:</td><td class="number"><fmt:formatNumber value = "${caratulaResponse.iva}" type = "currency"/> </td></tr>
					</tbody>
					<tfoot>
						<tr>
							<td>
								<b><liferay-ui:message key="Paso3Portlet.primTotal" /></b>
							</td>
							<td class="number" id="valPrimTot">
								<b id="tabPaso3_3">
									<fmt:formatNumber value = "${caratulaResponse.total}" type = "currency"/>
								</b>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
			<div class="col-md-3">
				<c:if test="${ perfilSuscriptor == 1 || perfilJapones == 1}">
					<div class="row" id="divPrimaObj" >
						<div class="col-sm-12">
							<!-- Grid row -->
							<div class="form-row align-items-center">
								<!-- Grid column -->
								<div class="col-11">
									<!-- Material input -->
									<div class="md-form">
										<input type="text" id="txtPrimaObj" class="form-control auxPorcen" value="${caratulaResponse.cuotaObjetivo}" ${ numeroRutas > 1 ? 'disabled' : '' }>
										<label id="titPrimaObj" for="txtPrimaObj">Cuota Objetivo:</label>
									</div>
								</div>
								<!-- Grid column -->
								<div class="col-1">
									<!-- Material input -->
									<div class="md-form input-group mb-3">
										<div class="input-group-prepend">
											<span class="input-group-text md-addon">%</span>
										</div>
									</div>
								</div>
								<!-- Grid column -->
							</div>
							<!-- Grid row -->
						</div>
					</div>
				</c:if>
				<div class="row ${ infCotizacion.poliza == 1 ? '' : 'd-none'}" id="divPrimaDepo" >
					<div class="col-sm-12">
						<div class="md-form form-group">
							<select name="selPrimaObj" id="selPrimaObj" class="mdb-select form-control-sel colorful-select dropdown-primary" ${ (perfilSuscriptor != 1) && (perfilJapones != 1) ? 'disabled' : ''}>
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${primaDeposito}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == caratulaResponse.primaDeposito ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
							<label for="selPrimaObj">Prima en dep&oacute;sito</label>
						</div>
					</div>
				</div>				
			</div>
		</div>
		<div class="row ${ perfilSuscriptor == 1 || perfilJapones == 1 ? '' : 'd-none'}" style="padding: 0 70px;" id="siniestralidad">
			<div class="col-md-3">
				<div class="form-check mt-4">
				    <input type="checkbox" class="form-check-input" id="checkSiniestralidad" name="checkSiniestralidad">
				    <label class="form-check-label" for="checkSiniestralidad">Ajuste de Siniestralidad</label>
				</div>
			</div>
			<div class="col-md-3">
				<div class="md-form siniestraValida">
					<input type="text" id="noAjustes" class="form-control cNumnero" value="${caratulaResponse.p_sinisetralidadNumero}">
					<label for="noAjustes">N&uacute;mero de Ajustes</label>
				</div>
			</div>
			<div class="col-md-3">
				<div class="md-form siniestraValida">
					<input type="text" id="montoAjustes" class="form-control moneda" value="${caratulaResponse.p_sinisetralidadMonto}">
					<label for="montoAjustes">Monto de los ajustes:</label>
				</div>
			</div>
			<div class="col-md-3">
				<div class="md-form siniestraValida">
					<input type="text" id="porcentajeAjustes" class="form-control" value="${caratulaResponse.p_sinisetralidadPorcentaje}">
					<label for="porcentajeAjustes">Porcentaje de los ajustes:</label>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row" style="padding: 0 70px;">
		<div class="col-md-3">
			<div class="row mb-2 mt-5">
				<div class="col-md-12">
					<c:if test="${ perfilSuscriptor == 1 || perfilJapones == 1}">
						<button class="btn btn-blue float-left btn-block mb-2" id="btnComisionesAgente" onclick="">Comisiones del Agente</button>					
						<button class="btn btn-blue float-left btn-block mb-2" id="btnDeducibles" onclick="showModalDeducibles();">Deducibles</button>
						<button class="btn btn-blue float-left btn-block mb-2" id="btnClausulas">Cl&aacute;usulas Adicionales</button>
						<button class="btn btn-blue float-left btn-block mb-2" id="btnCoaseguro" name="btnCoaseguro" ${infCotizacion.tipoCoaseguro == 2575 ? 'disabled' : '' }>Coaseguro</button>
						<button class="btn btn-blue float-left btn-block mb-2" id="btnReaseguro" name="btnReaseguro">Reaseguro</button>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-md-9" ${ (Leg492 == 'factura') ? 'hidden' : '' }>
			<div class="row mb-2 mt-5">
			<input type="hidden" id="idCanalNegocio" name="<portlet:namespace/>idCanalNegocio" value="${infCotizacion.canalNegocio}" class="form-control">
			
				<div class="col-md-12 text-right">
<%-- 				<c:if test="${ (perfilSuscriptor == 1 || perfilJapones == 1) && (infCotizacion.canalNegocio == 2525 || infCotizacion.canalNegocio == 2524)}"><button class="btn btn-blue" id="paso3_especif" onclick="showModalEspecificacion();">Especificaci&oacute;n</button></c:if> --%>
					<c:if test="${(perfilSuscriptor == 1 || perfilJapones == 1) && (infCotizacion.canalNegocio == 2525 ) }">
						<button class="btn btn-blue" id="paso3_especif" onclick="showModalEspecificacion();">Especificaci&oacute;n</button>
					</c:if>
					<button class="btn btn-blue d-none" id="paso3_noAceptado" onclick="">No aceptado</button>
					<c:if test="${!((perfilSuscriptor == 1 || perfilJapones == 1) && (infCotizacion.canalNegocio == 2525 ))}">
						<button class="btn btn-blue" id="paso3_Slip" onclick="generarSlip();">Generar Slip</button>
					</c:if>
					<c:if test="${ perfilSuscriptor == 1 || perfilJapones == 1}">
						<button class="btn btn-blue" id="btnRecalcularPrima" onclick="">Calcular</button>
					</c:if>
					<button class="btn btn-blue d-none" id="paso3_Slip_Endoso" onclick="generarSlipEndoso();">Generar Slip</button>
					<button class="btn btn-blue d-none" id="paso3_Carga_Slip_Endoso" onclick="cargaSlipEndoso();">Generar Slip</button>
				</div>
				<div class="col-md-12 text-right">
					<c:if test="${ perfilSuscriptor == 1 }">
						<button class="btn btn-blue" id="btnNoAcepPropuesta" name="btnNoAcepPropuesta">Rechazar</button>
					</c:if>
					<button class="btn btn-blue d-none" id="paso3_revire" onclick="">Revire</button>
					<button class="btn btn-blue" id="paso3_back" onclick="clickBtnBack();">Regresar</button>
					<button class="btn btn-pink ${ (perfilJapones == 1 && infCotizacion.canalNegocio == 2525) || (perfilSuscriptor != 1) ? 'd-none' : '' } " id="paso3_next" onclick="clickBtnNext();" disabled>Enviar Cotizaci&oacute;n</button>
					<button class="btn btn-pink ${ perfilJapones == 1 && infCotizacion.canalNegocio == 2525 ? '' : 'd-none' }" id="envio_suscripcion">A suscrpci&oacute;n</button>
					<button class="btn btn-pink" id="paso3_next_form" disabled>Solicitar Emisi&oacute;n</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-sm-12" ${ (Leg492 == 'factura') ? '' : 'hidden' }>
		<div class=" col-sm-6"></div>
		<div class="form-check form-check-inline col-sm-2">
			<input type="radio" class="form-check-input" id="chkfactauto" name="rdofactura" value="1" checked="checked">
			<label class="form-check-label" for="chkfactauto">Generar la factura autom&aacute;tica</label>
		</div>

		<!-- Material inline 2 -->
		<div class="form-check form-check-inline col-sm-2">
			<input type="radio" class="form-check-input" id="chkfactmanual" name="rdofactura" value="2">
			<label class="form-check-label" for="chkfactmanual">Generar la factura manual</label>
		</div>
		<button type="button" class="btn btn-pink waves-effect waves-light float-right" id="btnFacturaSuscrip"
			name="btnFacturaSuscrip" >Emitir</button>
	</div>
	
	<div class="row" style="display:none;">
		<input type="hidden" id="txtEmailAgente" value="${caratulaResponse.email}">
	</div>
	
</section>

<div id="divPdf" hidden="true">
	<a id="aPdf" hidden="true"></a>
	<a id="aPdfIng" hidden="true"></a>
</div>

<form action="<%=regresaPaso2URL%>" method="POST" hidden="true" id="formPaso3Back">
	<input type="hidden" id="paso" name="paso" class="d-none" value="paso2">
	<input type="hidden" id="folioCoti" name="folioCoti" class="d-none" value="${folioCoti}">
	<input type="hidden" id="versionCoti" name="versionCoti" class="d-none" value="${versionCoti}">
	<input type="hidden" id="cotizacion" name="cotizacion" class="d-none" value="${cotizacion}" />
	<input type="hidden" id="infoCot" name="infoCot" class="d-none" value="">
	<button id="action_back"></button>
</form>

<form action="${continuarPaso3URL}" method="POST" hidden="true" id="paso3-form">
	<input type="hidden" id="infoCotizacion" name="infoCotizacion" val="" />
</form>

<form id="divInfoAuxiliar" hidden="true">
	<input type="hidden" id="getSlip" value="${getSlip}">
	<input type="hidden" id="txtJSGetEmisionData" value="${getEmisionData}">
	<input type="hidden" id="txtJSGetSeccionComisionUrl" value="${getSeccionComisionUrl}">
	<input type="hidden" id="getCaratulaComision" value="${saveCaratulaComision}">
	<input id="txtIdPerfilUser" type="text" name="txtIdPerfil" class="form-control" hidden="true" value="${ idPerfilUser }">
	<input id="txtMinPrima" type="text" name="txtMinPrima" class="form-control" hidden="true" value="${ minPrima }">
	<input id="txtTpoCambio" type="text" name="txtTpoCambio" class="form-control" hidden="true" value="${ tpoCambio }">
	<input type="hidden" id="txtJSGetDocsEmision" value="${getDocsEmision}">
	<input type="hidden" id="txtEmailUser" value="${mailUser}">
	<input type="hidden" id="dc_moneda" value="${tipoMoneda}">
	<input type="hidden" id="txtEmailAgente" value="${caratulaResponse.email}">
	<input type="hidden" id="txtAuxEnvDoc" name="txtAuxEnvDoc" class="form-control">
</form>

<script src="<%=request.getContextPath()%>/js/objetos.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/funcionesGenericas.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/paso3.js?v=${version}"></script>

<script>

	var infCotiJson = ${infoCotJson};
	
	var rechazaCotizacionURL = '${txtRechazaCotizacionURL}';

	var guardaComisionesAgenteURL = '${guardaComisionesAgenteURL}';
	var comisionesAgenteURL = '${getComisionesAgenteURL}';
	var responseComision;
	var clausulasURL = '${getClausulas}';
	var responseClausulas;
	
	var perfilSuscriptor = '${perfilSuscriptor}'
	var perfilJapones = '${perfilJapones}'
	
	var enviarCotizacionURL = '${enviarCotizacionURL}';
	var continuarJKURL = '${continuarJKURL}';
	var recalculoPrimaURL = '${recalculoPrimaURL}';
	var idCatalogoSlip;
	var numeroRutas = ${numeroRutas};
	var catalogoUnidades = ${catalogoUnidades};
	var catalogoCriterios = ${catalogoCriterios};
	
	var enviaArchivosURL = "${enviaArchivosURL}";
	var sendMailAgenteSuscriptorURL = '${sendMailAgenteSuscriptorURL}';
	var sendMailSuscriptorAgenteURL = '${sendMailSuscriptorAgenteURL}';
	
	var getEmisionArt492Url = '${getEmisionArt492Url}';
	
	var redirigeURL = '${redirigeURL}';
	
	var generaInfoCoaseguroURL = '${generaInfoCoaseguroURL}';
	var generaInfoReaseguroURL = '${generaInfoReaseguroURL}';
	
	var guardaSiniestralidadURL = '${guardaSiniestralidadURL}';
	
	var getPermisoEmisionURL = '${getPermisoEmisionURL}';
	
	var validaAgenteURL = '${validaAgenteURL}';
	
	var disabled = '';
	
	var boton = 0;
	
	var listaDias = ${listaDias};
	
	ligasServicios.getDeducibles = "${getDeducibles}";
	ligasServicios.saveDeducibles = "${saveDeducibles}";
	ligasServicios.saveClausulasA = "${saveClausulasA}";
	ligasServicios.plantillaSlip = "${plantillaSlipUrl}";
	ligasServicios.generacionSlip = "${generacionSlipUrl}";
	ligasServicios.generacionSlipSemi = "${generacionSlipSemiUrl}";
	ligasServicios.recalculoPrimaDeposito = "${recalculoPrimaDepositoURL}";
	ligasServicios.validarReaseguro = "${validarReaseguroURL}";
	
	ligasServicios.secionComisionUrl = "${getSeccionComisionUrl}";
</script>