<%@ include file="../init.jsp"%>
<!-- Modal usuario existente -->
<div class="modal" id="modalClienteExistente" tabindex="-1" role="dialog" aria-labelledby="clienteExistenttLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="clienteExistenttLabel">
					<liferay-ui:message key="CotizadorTransportistas.titModClientExistt" />
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!--Body-->
			<div class="modal-body">

				<div class="row">
					<div class="col-12">
						<h4 class="font-weight-bold">
							<samp id="nombreClienteExistt"></samp>
						</h4>
						<liferay-ui:message key="CotizadorTransportistas.infoModClientExistt" />
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					<div class="col-md-6">
						<button class="btn btn-pink waves-effect waves-light" id="btnClienttExisttSi">Si</button>
					</div>
					<div class="col-md-6">
						<button class="btn btn-blue waves-effect waves-light" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal Bloqueo Agentes -->
<div class="modal" id="modalBloqueoAgente" tabindex="-1" role="dialog" aria-labelledby="modalBloqueoAgenteLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header orange">
				<h5 class="modal-title text-black-50" id="modalBloqueoAgenteLabel">
					<i class="fas fa-exclamation-triangle"></i> Restricción para Agente
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<!--Body-->
			<div class="modal-body">
				<div class="row">
					<div class="col-12">
						La clave de agente seleccionada debe actualizar su expediente de Agente para poder emitir pólizas.   Por el momento, solo podría generar cotizaciones.   Dudas o comentarios, favor de escribir a <a href="mailto:enterate@tokiomarine.com.mx">enterate@tokiomarine.com.mx</a>
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