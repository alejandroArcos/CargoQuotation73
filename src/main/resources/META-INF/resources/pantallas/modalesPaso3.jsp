<!-- Modal Comisiones Agente -->
<div class="modal" id="modalComisionesAgente" tabindex="-1" role="dialog" aria-labelledby="modalComisionesAgenteLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">Comisiones del Agente</h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>
			<!--Body-->
			<div class="modal-body">
				<div class="row">
					<div class="col-12">
						<table class="table">
							<thead>
								<tr>
									<th>Ramo</th>
									<th>Cobertura</th>
									<th>Nueva Comisi&oacute;n</th>
								</tr>
							</thead>
							<tbody id="tableComisionesBody">
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					<div class="col-md-6">
						<div class="btn btn-pink waves-effect waves-light" id="btnGuardarComisionesAgente">Guardar</div>
					</div>
					<div class="col-md-6">
						<div class="btn btn-blue waves-effect waves-light" data-dismiss="modal" id="btnCancelarComisionesAgente">Cancelar</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal Rechazar propuesta -->
<div class="modal" id="modalRechazarProp" tabindex="-1" role="dialog"
	aria-labelledby="modalRechazarPropLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalRechazarPropLabel">
					<liferay-ui:message key="Paso3Portlet.modalRechazaPropuesta" />
				</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!--Body-->
			<div class="modal-body">

				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<span id="textRechazo">
							<liferay-ui:message key="Paso3Portlet.modalTxtRechazo" />
						</span>
					</div>

					<div class="col-md-12">
						<div class="md-form form-group">
							<select name="selectMotRechazo" id="selectMotRechazo "
								class="mdb-select form-control-sel">
								<option value="-1" selected><liferay-ui:message key="Paso3Portlet.selectOpDefoult" /></option>
								<c:forEach items="${motivoRechazo}" var="option">
									<option value="${option.idCatalogoDetalle}">${option.valor}</option>
								</c:forEach>
							</select> <label for="selectMotRechazo"> <liferay-ui:message key="Paso3Portlet.modalMotivo" /> </label>
						</div>
					</div>
					<div class="col-12">
						<div class="md-form">
							<textarea id="comentariosRechazarProp"
								name="comentariosRechazarProp" class="md-textarea form-control"
								rows="3" maxlength="1000" style="text-transform: uppercase;"></textarea>
							<label for="comentariosDosSuscrip"> <liferay-ui:message key="Paso3Portlet.modalComentarios" /> </label>
						</div>
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					<div class="col-md-6">
						<button class="btn btn-blue waves-effect waves-light"
							data-dismiss="modal"> <liferay-ui:message key="Paso3Portlet.cancelar" /> </button>
					</div>
					<div class="col-md-6">
						<button class="btn btn-pink waves-effect waves-light"
							id="btnEnvRecha"> <liferay-ui:message key="Paso3Portlet.enviar" /> </button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END  Modal Rechazar propuesta -->

<!-- MODAL DEDUCIBLES -->
<div class="modal" id="modalDeducibles" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 80%;">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">DEDUCIBLES</h3>
				
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div id="bodyModalDeducibles" class="modal-body">
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<p>Porcentaje</p>
					</div>
					<div class="col-md-3">
						<p>Criterio</p>
					</div>
					<div class="col-md-2">
						<p>Monto</p>
					</div>
					<div class="col-md-2">
						<p>Unidad</p>
					</div>
				</div>
					
				<div class="row rowROT">
					<div class="col-md-3">
						<p class="mt-3 labelROT">Riesgos Ordiniarios de Tr&aacute;nsito (ROT)</p>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlPorcenROT" class="form-control porcenROT porcentajeDeducible">
						</div>
					</div>
					<div class="col-md-3">
						<div class="md-form form-group">
							<select name="criterio_ROT" id="mdlCritROT" class="mdb-select form-control-sel criterioROT">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${listaCriterio}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlMontoROT" class="form-control moneda">
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlUnidadROT" class="mdb-select form-control-sel unidad" disabled>
 								<c:forEach items="${listaUnidades}" var="option">
									<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row rowRobo d-none">
					<div class="col-md-3">
						<p class="mt-3">Robo</p>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlPorcenRobo" class="form-control porcentajeDeducible">
						</div>
					</div>
					<div class="col-md-3">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlCritRobo" class="mdb-select form-control-sel">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${listaCriterio}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlMontoRobo" class="form-control moneda">
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlUnidadRobo" class="mdb-select form-control-sel unidad" disabled>
 								<c:forEach items="${listaUnidades}" var="option">
									<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row rowAveria d-none">
					<div class="col-md-3">
						<p class="mt-3">Aver&iacute;as en el sistema de refrigeraci&oacute;n</p>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlPorcenASF" class="form-control porcentajeDeducible">
						</div>
					</div>
					<div class="col-md-3">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlCritASF" class="mdb-select form-control-sel">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${listaCriterio}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlMontoASF" class="form-control moneda">
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlUnidadASF" class="mdb-select form-control-sel unidad" disabled>
 								<c:forEach items="${listaUnidades}" var="option">
									<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row rowHT d-none">
					<div class="col-md-3">
						<p class="mt-3">Huelgas Terrestre</p>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlPorcenHT" class="form-control porcentajeDeducible">
						</div>
					</div>
					<div class="col-md-3">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlCritHT" class="mdb-select form-control-sel">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${listaCriterio}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlMontoHT" class="form-control moneda">
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlUnidadHT" class="mdb-select form-control-sel unidad" disabled>
 								<c:forEach items="${listaUnidades}" var="option">
									<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row rowIT d-none">
					<div class="col-md-3">
						<p class="mt-3">Interrupción en el Transporte (Estadía)</p>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlPorcenIT" class="form-control porcentajeDeducible">
						</div>
					</div>
					<div class="col-md-3">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlCritIT" class="mdb-select form-control-sel">
								<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
 								<c:forEach items="${listaCriterio}" var="option">
									<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form">
							<input type="text" id="mdlMontoIT" class="form-control moneda">
						</div>
					</div>
					<div class="col-md-2">
						<div class="md-form form-group">
							<select name="dc_moneda" id="mdlUnidadIT" class="mdb-select form-control-sel unidad" disabled>
 								<c:forEach items="${listaUnidades}" var="option">
									<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
 								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row mt-4">
					<div class="col-sm-12 text-right">
						<a onclick="addDeducibleJsp();">Agregar Deducible <i class="fa fa-plus-circle" aria-hidden="true"></i></a>
					</div>
				</div>
				<div id="rowDeduciblesLibre">
					<div class="row rowDeducible">
						<div class="col-md-3">
							<div class="md-form">
								<input type="text" id="txtDeducible0" class="form-control descripcionDed">
							</div>
						</div>
						<div class="col-md-2">
							<div class="md-form">
								<input type="text" id="mdlPorcenDedu0" class="form-control porcenDed porcentajeDeducible">
							</div>
						</div>
						<div class="col-md-3">
							<div class="md-form">
								<select id="mdlCritDedu0" class="mdb-select form-control-sel criterioDed">
									<option value="-1" selected><liferay-ui:message key="CotizadorTransportistas.selectOpDefoult" /></option>
	 								<c:forEach items="${listaCriterio}" var="option">
										<option value="${option.idCatalogoDetalle}" ${option.idCatalogoDetalle == 6462 ? 'selected' : '' }>${option.valor}</option>
	 								</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="md-form">
								<input type="text" id="mdlMontoDedu0" class="form-control montoDed moneda">
							</div>
						</div>
						<div class="col-md-2">
							<div class="md-form form-group">
								<select id="mdlUnidadDedu0" class="mdb-select form-control-sel unidadDed" disabled>
	 								<c:forEach items="${listaUnidades}" var="option">
										<option value="${option.idCatalogoDetalle}" selected>${option.valor}</option>
	 								</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>

			</div><!--  -->
			<!--Footer-->
			<div class="modal-footer justify-content-center blue-gradient">
				<button type="button" class="btn btn-pink" style="display: none;">Cancelar</button>
				<button onclick="" type="button" class="btn btn-pink" data-dismiss="modal">Cancelar</button>
				<button onclick="saveDeducibles();" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>
<!-- MODAL DEDUCIBLES -->

<!-- MODAL SLIP SEMIAUTO -->
<div class="modal" id="modalSlipSemi" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">Slip Semiautom&aacute;tico</h3>
				
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div id="bodyModalSlipSemi" class="modal-body">
				<div class="row d-flex justify-content-center section-heading">
					<h5 class="title text-left mt-4">DESCARGAR SLIP ESP/ING</h5>
				</div>
				<div class="row d-flex justify-content-center">
					<div class="col-md-12 d-flex justify-content-center">
						<button type="button" class="btn blue-gradient btn-rounded" onclick="downloadPlantillaSlip();"><i class="fas fa-download pr-2" aria-hidden="true"></i>Plantilla</button>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12 d-flex justify-content-center section-heading md-form">
						<h5 class="title text-left padding70 mt-4">COMPLETA LA PLANTILLA Y SUBE TU ARCHIVO</h5>
					</div>
					<div class="col-md-6 d-flex justify-content-center md-form">
						<div class="file-field">
							<a class="btn-floating blue-gradient mt-0 float-left">
								<i class="fas fa-paperclip" aria-hidden="true"></i>
								<input type="file" id="archivoSlip" class="inFile" data-file_types="pdf" accept="application/pdf">
							</a>
							<div class="file-path-wrapper">
								<input id="infDocSuc" class="file-path" type="text" placeholder="Adjuntar Slip Español" readonly>
							</div>
						</div>
					</div>
					<div class="col-md-6 d-flex justify-content-center md-form">
						<div class="file-field">
							<a class="btn-floating blue-gradient mt-0 float-left">
								<i class="fas fa-paperclip" aria-hidden="true"></i>
								<input type="file" class="inFile" id="archivoSlipIngles" data-file_types="pdf" accept="application/pdf">
							</a>
							<div class="file-path-wrapper">
								<input id="infDocSucIngles" class="file-path" type="text" placeholder="Adjuntar Slip Ingles" readonly>
							</div>
						</div>
					</div>
				</div>

			</div><!--  -->
			<!--Footer-->
			<div class="modal-footer justify-content-center blue-gradient">
				<button type="button" class="btn btn-pink" style="display: none;">Cancelar</button>
				<button onclick="" type="button" class="btn btn-pink" data-dismiss="modal">Cancelar</button>
				<button id="guardarSlipSemi" onclick="guardarSlipSemi();" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>
<!-- MODAL SLIP SEMIAUTO -->

<!-- MODAL CLAUSULAS ADICIONALES -->
<div class="modal" id="modalClausulas" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">CLAUSULAS ADICIONALES</h3>
				
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div id="bodyModalClausulas" class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-check mt-4">
						    <input type="checkbox" class="form-check-input" id="checkALL" name="checkALL">
						    <label class="form-check-label" for="checkALL">Marcar / Desmarcar todas</label>
						</div>
					</div>
				</div>
				<hr>
				<div class="row rowClausulas">
					
				</div>

			</div><!--  -->
			<!--Footer-->
			<div class="modal-footer justify-content-center blue-gradient">
				<button type="button" class="btn btn-pink" style="display: none;">Cancelar</button>
				<button onclick="" type="button" class="btn btn-pink" data-dismiss="modal">Cancelar</button>
				<button id="saveClausulas" onclick="saveClausulas();" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>
<!-- MODAL CLAUSULAS ADICIONALES -->

<!-- MODAL ESPECIFICACION -->
<div class="modal" id="modalEspecificacion" tabindex="-1" role="dialog"
	aria-labelledby="fileModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="fileErrorPopup"></div>
			<div class="modal-header">
				<h5 class="modal-title" id="fileModalLabel">ADJUNTAR ESPECIFICACI&Oacute;N</h5>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-md-12">
						<span>Archivos permitidos, PDF, Word</span>
						<div class="md-form">
							<div class="row">
								<div class="col-md-3">
									<span>* Especificaci&oacute;n JI</span>
								</div>
								<div class="col-md-6">
									<div class="file-field">
										<div class="col-md-6">
											<div class="btn btn-blue btn-rounded btn-sm float-left">
												<span><i class="fas fa-upload mr-2" aria-hidden="true"></i>Adjuntar</span>
												<input id="docEspecificacion" type="file" data-file_types="pdf|doc|docx">
											</div>			
										</div>
										<div class="col-md-6">
											<input id="infDocEspecificacion" class="file-path form-control" type="text" placeholder="Archivo" disabled>
										</div>
									</div>
								</div>
								<div class="col-md-3">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<span>URF</span>
								</div>
								<div class="col-md-6">
									<div class="file-field">
										<div class="col-md-6">
											<div class="btn btn-blue btn-rounded btn-sm float-left">
												<span><i class="fas fa-upload mr-2" aria-hidden="true"></i>Adjuntar</span>
												<input id="docURF" type="file" data-file_types="pdf|doc|docx" />
											</div>			
										</div>
										<div class="col-md-6">
											<input id="infDocURF" class="file-path form-control" type="text" placeholder="Archivo" disabled />
										</div>
									</div>
								</div>
								<div class="col-md-3">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<span>Otro</span>
								</div>
								<div class="col-md-6">
									<div class="file-field">
										<div class="col-md-6">
											<div class="btn btn-blue btn-rounded btn-sm float-left">
												<span><i class="fas fa-upload mr-2" aria-hidden="true"></i>Adjuntar</span>
												<input id="docOtro" type="file" data-file_types="pdf|doc|docx">
											</div>			
										</div>
										<div class="col-md-6">
											<input id="infDocOtro" class="file-path form-control" type="text" placeholder="Archivo" disabled />
										</div>
									</div>
								</div>
								<div class="col-md-3">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-blue waves-effect waves-light" data-dismiss="modal">Cancelar</button>
				<button class="btn btn-pink waves-effect waves-light" id="btnGuardarEspecificacion" data-dismiss="modal">Guardar</button>
			</div>
		</div>
	</div>
</div>

<!-- MODAL ARCHIVOS -->
<div class="modal" id="modalArchivos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
	<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header blue-gradient text-white">
				<h3 class="heading lead">
					<i class="fas fa-edit" aria-hidden="true"></i> ¿Requiere incluir informaci&oacute;n adicional?
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
				<button id="enviaArchivosSuscripcion" type="button" class="btn btn-blue" >Continuar</button>
			</div>
		</div>
	<!--/.Content-->
	</div>
</div>

<div class="modal" id="modalGenerarPoliza" tabindex="-1" role="dialog" aria-labelledby="cerrarPolizaLabel"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered anchoModal" role="document">
		<div class="modal-content">
			<div class="modal-header green">
				<h5 class="modal-title text-white" id="cerrarPolizaLabel">
					<span id="titModalEmisionp3"></span>
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!--Body -->
			<div class="modal-body">

				<div class="row">
					<div class="col-12">
						<table class="table altoTbPoliza tablaPoliza">
							<tbody>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaNumeroPoliza" /></b> <span
											id="txtModalPolizaNumeroPoliza"> </span>
									</td>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaCertificado" /></b> <span
											id="txtModalPolizaCertificado"> </span>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaAsegurado" /></b> <span id="txtModalPolizaAsegurado">
										</span>
									</td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaVigencia" /></b> <span id="txtModalPolizaVigenciaDe">
										</span> <b> al </b> <span id="txtModalPolizaVigenciaAl"> </span>
									</td>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaTotalUbicaciones" /></b> <span
											id="txtModalPolizaTotalUbicaciones"> </span>
									</td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaMoneda" /></b> <span id="txtModalPolizaMoneda">
										</span>
									</td>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaFormaPago" /></b> <span id="txtModalPolizaFormaPago">
										</span>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="table altoTbPoliza table-borderless colPoliza20 tablaPoliza">
							<tbody>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaPrimaNeta" /></b>
									</td>
									<td class="moneda celdaPoliza">$</td>
									<td class="colPoliza10">
										<span id="txtModalPolizaPrimaNeta"> </span>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaRecargoPago" /></b>
									</td>
									<td class="moneda celdaPoliza">$</td>
									<td class="colPoliza10">
										<span id="txtModalPolizaRecargoPago"> </span>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaGastosExpedicion" /></b>
									</td>
									<td class="moneda celdaPoliza">$</td>
									<td class="colPoliza10">
										<span id="txtModalPolizaGastosExpedicion"> </span>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaIva" /></b>
									</td>
									<td class="moneda celdaPoliza">$</td>
									<td class="colPoliza10">
										<span id="txtModalPolizaIva"> </span>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
										<b><liferay-ui:message key="Paso3Portlet.modalPolizaTotal" /></b>
									</td>
									<td class="moneda celdaPoliza">$</td>
									<td class="colPoliza10">
										<span id="txtModalPolizaTotal"> </span>
									</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				<input type="hidden" id="txtModalPolizaAgente" class="d-none">
				</div>
				<div class="row">
					<div class="col-7">
						<div class="row">
							<div class="col-12">
								<div class="md-form">
									<input type="email" name="modalPolizaEnviarCorreo" id="modalPolizaEnviarCorreo" class="form-control">
									<label for="modalPolizaEnviarCorreo">
										<liferay-ui:message key="Paso3Portlet.modalPolizaEnviarCorreo" />
									</label>
								</div>
							</div>
							<div class="col-12">
								<button class="btn btn-pink waves-effect waves-light float-right" id="btnAgregaCorreoPoliza" disabled>
									<liferay-ui:message key="Paso3Portlet.modalPolizaBtnAgregarCorreo" />
								</button>
							</div>
							<div class="col-12 table-wrapper-scroll-y">
								<table class="altoTbArchivos table table-striped" id="tablaArchivosPoliza" style="width: 100%;">
									<thead>
										<tr>
											<th scope="col" class="th-sm">
												<div class="form-check">
													<input type="checkbox" class="form-check-input selectCheckImput" id="seleccionarTodosArchivos" checked>
													<label class="form-check-label" for="seleccionarTodosArchivos">
														<liferay-ui:message key="Paso3Portlet.modalPolizaChekTodos" />
													</label>
												</div>
											</th>
											<th scope="col" class="th-sm">
												<liferay-ui:message key="Paso3Portlet.modalPolizaArchivoTit" />
											</th>
											<th scope="col" class="th-sm">
												<liferay-ui:message key="Paso3Portlet.modalPolizaTipoTit" />
											</th>
										</tr>
									</thead>
									<tbody class="bodyArchivos">

									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-5">
						<div class="alert alert-warning msjActivarBtnEnviar" role="alert" hidden="true">
							<liferay-ui:message key="Paso3Portlet.modalPolizaMsjEnviar" />
						</div>
						<ul id="listaCorreos" class="listaCorreos scrollbarLiMod scrollbarLiMod-primary">
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-6">
						<button class="btn btn-pink waves-effect waves-light " id="btnDescargarArchivos">
							<liferay-ui:message key="Paso3Portlet.modalPolizaBtnDescargarArchivos" />
						</button>
						<button class="btn btn-pink waves-effect waves-light" id="polizaBtnEnviar">
							<liferay-ui:message key="Paso3Portlet.modalPolizaBtnEnviar" />
						</button>
					</div>
					<div class="col-6">
						<button class="btn btn-blue waves-effect waves-light float-right" id="polizaBtnAceptar" data-dismiss="modal">
							<liferay-ui:message key="Paso3Portlet.modalPolizaBtnAceptar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Poliza Generada -->

<!-- Modal Bloqueo Agentes -->
<div class="modal" id="modalBloqueoAgente" tabindex="-1" role="dialog" aria-labelledby="modalBloqueoAgenteLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header orange">
				<h5 class="modal-title text-black-50" id="modalBloqueoAgenteLabel">
					<i class="fas fa-exclamation-triangle"></i> Restricci&oacute;n para Agente
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div class="modal-body">
				<div class="row">
					<div class="col-12">
						El agente seleccionado para esta cotizaci&oacute;n debe actualizar su expediente de Agente por lo que no es posible emitir esta cotizaci&oacute;n.   Dudas o comentarios, favor de escribir a <a href="mailto:enterate@tokiomarine.com.mx">enterate@tokiomarine.com.mx</a>.
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					<div class="col-md-6">
						<button class="btn btn-pink waves-effect waves-light" data-dismiss="modal" id="btncpsusc">Entendido</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Bloqueo Agentes -->
<!-- Modal subir archivos paso 2  -->
<div class="modal" id="fileModal" tabindex="-1" role="dialog" aria-labelledby="fileModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="fileErrorPopup"></div>
			<div class="modal-header green">
				<h5 class="modal-title text-white" id="fileModalLabel">
				<i class="far fa-edit"></i>
				¿Requiere incluir informaci&oacute;n adicional?</h5>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-12">
						<span>Archivos permitidos, PDF, Excel, Word</span>
						<div class="md-form">
							<div class="file-field">
								<div class="btn btn-blue btn-rounded btn-sm float-left">
									<span><i class="fas fa-upload mr-2" aria-hidden="true"></i>Seleccionar Archivo</span>
									<input id="docAgenSusc" type="file" multiple="multiple" data-file_types="pdf|doc|docx|xls|xlsx">
								</div>
								<input id="infDocSuc" class="form-control" type="text" placeholder="Archivos" disabled>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="md-form">
							<textarea id="comentariosDosSuscrip" name="comentariosDosSuscrip" class="md-textarea form-control" rows="3"
								maxlength="1000" style="text-transform: uppercase;"></textarea>
							<label for="comentariosDosSuscrip"> Comentarios adicionales. </label>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-pink waves-effect waves-light d-none" id="btnSuscripEnvSus2" >Enviar a suscriptor</button>
			</div>
		</div>
	</div>
</div>
<!-- end Modal subir archivos paso 2  -->