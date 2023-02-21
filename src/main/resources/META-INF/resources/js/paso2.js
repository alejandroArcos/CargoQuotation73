var auxClone;
$(document).ready(function(){
	checkVisibleAll();
	validaMaxEstadia();
	validTerrestre();
	validMaritimoAereo();
	validPerecederos();
	validaRobo();
	showHideEtiqueta();
	validaReglasNEgocio();
	validaPronosticoAnual();
	seleccionaModo();
	
	$('body').on('click' , '.modal-body .select-wrapper.mdb-select', function(){
	    $(this).find('input.search').focus();
	});
	
	auxClone = $('#inOrigen0 option').clone();
	
	validaTipoMoneda();
});

tippy(
		'#tooltip0', {
			content: "El asegurado ya está ejecutando las recomendaciones del área de prevención",
		}
);

tippy(
	'#tooltip1', {
		content: "Medio de transporte en buenas condiciones",
	}
);

tippy(
	'#tooltip2', {
		content: "Utiliza caminos de cuota siempre que estén disponibles",
	}
);

/*
 					<div id="box-inOrigen`+countRuta+`" class="md-form">
						<input type="text" id="inOrigen`+countRuta+`" class="form-control ciudad-autocomplete txtCiudadO" codigoLugar="0">
						<label for="inOrigen`+countRuta+`">Origen</label>
					</div>
					<div id="box-inDestino`+countRuta+`" class="md-form">
						<input type="text" id="inDestino`+countRuta+`" class="form-control ciudad-autocomplete txtCiudadD" codigoLugar="0">
						<label for="inDestino`+countRuta+`">Destino</label>
					</div>
 */

function validaTipoMoneda() {
	
	$.each($(".moneda"), function(key, value) {
		
		daFormatoMoneda($(this));
	});
	
}

function seleccionaModo() {
	console.log('modo: ' + infCotString.modo);
	switch (infCotString.modo) {
		case modo.NUEVA :
			break;
		case modo.EDICION :
			break;
		case modo.COPIA :
			break;
		case modo.ALTA_ENDOSO :
			break;
		case modo.EDITAR_ALTA_ENDOSO :
			break;
		case modo.CONSULTA :
		case modo.CONSULTA_VOBO_REASEGURO :
			$("#cotizadores-p2 input, textarea, select").attr('disabled', true);
			$("#paso2_save").attr("disabled", true);
			$("#paso2_next").attr("disabled", false);
			$("#paso2_archivos").attr("disabled", true);
			
			break;
		case modo.BAJA_ENDOSO :
			break;
		case modo.EDITAR_BAJA_ENDOSO :
			break;
		case modo.FACTURA_492 :
			break;
		case modo.EDICION_JAPONES:
			break;
		case modo.DECLARACION_ENDOSO:
			$("#accordionEndoso").removeClass('d-none');
			$("#accordionLimitesGenerales").addClass('d-none');
			$("#accordionCoberturas").addClass('d-none');
			break;
		case modo.CONSULTAR_REVISION :
			$("#cotizadores-p2 input, textarea, select").attr('disabled', true);
			$("#paso2_save").attr("disabled", true);
			$("#paso2_next").attr("disabled", true);
			$("#paso2_archivos").attr("disabled", true);
			break;
		default:
			showMessageError('.navbar', msj.es.errorInformacion, 0);
			console.error(" Modo default");
			break;
	}
}

var countRuta = 1
function addRutaJsp(){
	if( $('.rowRutas').length < 10 ){
		$('#bodyModalRuta').append(`
				<hr class="rowInfoRuta`+countRuta+`">
				<div class="row rowRutas rowInfoRuta`+countRuta+`">
				<div class="col-md-6 col-select-`+countRuta+`">
					<p class="pRutaTit">Ruta</p>
					
					<div class="md-form form-group">
						<select name="inOrigen`+countRuta+`" id="inOrigen`+countRuta+`" class="mdb-select form-control-sel colorful-select dropdown-primary selCiudadO requeridoS" searchable='Buscar...'>
						</select>
						<label for="inOrigen`+countRuta+`">Origen</label>
					</div>
					<div class="md-form form-group">
						<select name="inDestino`+countRuta+`" id="inDestino`+countRuta+`" class="mdb-select form-control-sel colorful-select dropdown-primary selCiudadD requeridoS" searchable='Buscar...'>
						</select>
						<label for="inDestino`+countRuta+`">Destino</label>
					</div>
					
				</div>
				<div class="col-md-2">
				<div class="md-form mt-5">
				<input type="text" id="inPronosticoMdl`+countRuta+`" class="form-control txtPronos moneda requeridoI">
				<label for="inPronosticoMdl`+countRuta+`">Pronóstico</label>
				</div>
				</div>
				<div class="col-md-2">
				<div class="md-form mt-5">
				<input type="text" id="inLmeMdl`+countRuta+`" class="form-control txtLME moneda requeridoI">
				<label for="inLmeMdl`+countRuta+`">LME</label>
				</div>
				</div>
				<div class="col-md-2 text-right">
				<a class="btnRemove" onclick="showConfirm(`+countRuta+`);"> <i class="fa fa-trash" aria-hidden="true"></i> </a>
				<a class="btnConfirm d-none" onclick="removeRutaJsp(`+countRuta+`);"> <i class="fa fa-check" aria-hidden="true"></i> </a>
				<a class="btnCancela d-none" onclick="hideConfirm(`+countRuta+`);"> <i class="fa fa-times" aria-hidden="true"></i> </a>
				<div class="md-form">
				<input type="text" id="inCuotaMdl`+countRuta+`" class="form-control txtCuota auxPorcen requeridoI">
				<label for="inCuotaMdl`+countRuta+`">Cuota %</label>
				</div>
				</div>
				</div>
		`);
		$('#selRutasAux option').clone().appendTo('#inOrigen'+countRuta);
		$('#selRutasAux option').clone().appendTo('#inDestino'+countRuta);
		/*
		$('#inOrigen'+countRuta).find("option[value=-1]").attr('selected', true);
		$('#inDestino'+countRuta).find("option[value=-1]").attr('selected', true);
		*/
		selectDestroy( $('#inOrigen'+countRuta), false);
		selectDestroy( $('#inDestino'+countRuta), false);
		/*
		setAutocomplete('inOrigen'+countRuta);
		setAutocomplete('inDestino'+countRuta);
		*/
		setNoRutasTitulo();
		countRuta++;		
	}else{
		showMessageError( '#modalAddRutas .modal-content', 'Máximo hasta 10 rutas', 0 );
	}
	
}

function setNoRutasTitulo(){
	$('.pRutaTit').each(function (index) {
		$(this).text('Ruta ' + (index +1) );
	});
}

function showConfirm(count){
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnRemove').addClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnConfirm').removeClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnCancela').removeClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnRemove').removeClass('animated fadeInRight');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnConfirm').addClass('animated fadeInRight');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnCancela').addClass('animated fadeInRight');
}

function hideConfirm(count){
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnRemove').removeClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnConfirm').addClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnCancela').addClass('d-none');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnRemove').addClass('animated fadeInRight');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnConfirm').removeClass('animated fadeInRight');
	$('#bodyModalRuta .rowInfoRuta'+count+' .btnCancela').removeClass('animated fadeInRight');
}

function removeRutaJsp(count){
	$('#bodyModalRuta .rowInfoRuta'+count).remove();
	setNoRutasTitulo();
}


function daFormatoMoneda(campo){
	if(!valIsNullOrEmpty($( campo ).val())){
		$( campo ).val(formatter.format($( campo ).val()));
	}
}

function sumarobo(){
	/*
	var s1 = valIsNullOrEmpty( $('#inTerrestre').val() ) ? 0 : parseInt($('#inTerrestre').val().replace(/[^0-9\.]/g,'') );
	var s2 = valIsNullOrEmpty( $('#inMaritimo').val() ) ? 0 : parseInt($('#inMaritimo').val().replace(/[^0-9\.]/g,'') );
	var s3 = valIsNullOrEmpty( $('#inIntermodal').val() ) ? 0 : parseInt($('#inIntermodal').val().replace(/[^0-9\.]/g,'') );
	
	var auxSuma = +s1 + s2 + s3;
	var auxSumaStr = ""+auxSuma;
	
	$('#inRobo').val( auxSumaStr.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",") );
	
	
	
	if(rutas.length > 1) {
		var auxLMERutas = 0;
		
		$.each(rutas)
	}
	else {
		$('#inRobo').val($('#inPronosticoMdl0').val());
		
	}
	
	*/
	
	$("#inRobo").val($("#intLMaxEmgarbue").val());
	
}

function validaMaxEstadia() {
	if ( $('#intLMaxEstadia').val() == '' || parseFloat($('#intLMaxEstadia').val().replace(/[$,]/g, '')) == 0){
		$('.divMaxestadia').addClass('d-none');
	}else{
		$('.divMaxestadia').removeClass('d-none');		
	}
	
}

$("#intLMaxEstadia" ).on("blur", function(){
	validaMaxEstadia();
});

$(".accordion .moneda" ).on("blur", function(){
	sumarobo();
});

$('.site-wrapper').on("keyup", ".moneda", function() {
	var aux = $(event.target).val().split('.');
	$(event.target).val(aux[0]);
	$(event.target).val(function (index, value ) {
		console.log('valor1: ' + value.replace(/\D/g, "") );
		console.log('valor2: ' + value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",") );
		if ( aux.length > 1 ) {
			return value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",") + '.' + aux[1].slice(0,2);						
		}else{
			return value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");			
		}
	});
});

$(".moneda" ).on("focus", function(){
	var abc = $(this).val().replace(/[^0-9\.,]/g,'');
	/*$(this).val(abc.split('.')[0]);*/
	$(this).val(abc);
});

$('.check-Input').on('click', function(){
	checkVisible( $(this) );
	showHideEtiqueta();
});

function showHideEtiqueta(){
	var check1 = $('#checkHMaritimo').is(':checked');
	var check2 = $('#checkHAereo').is(':checked');
	var check3 = $('#checkHEstadia').is(':checked');
	
	if( check1 || check2 || check3 ){
		$('.divSubPronostivo').removeClass('d-none');
	}else{
		$('.divSubPronostivo').addClass('d-none');
	}
}

function checkVisible(campo){
	var auxId = $(campo).attr('fId');
	if( $(campo).is(':checked') ){
		$('#'+auxId).removeClass('d-none');
	}else{
		$('#'+auxId).addClass('d-none');
		$('#'+auxId).val('');
	}
}

function checkVisibleAll(){
	$('.check-Input').each(function (index) {
		checkVisible( $(this) );
	});
}

$('.ciudad-autocomplete').each(function(index) {
	inputId = $(this).attr('id');
	setAutocomplete(inputId);
});

function setAutocomplete(inputId){
	$('#'+inputId).autocomplete({
		source: function (request, result) {
			$.ajax({
				url: getOrigenDestino,
				data: 'query=' + request.term.toUpperCase(),            
				dataType: "json",
				type: "POST",
				success: function (data) {
					console.log(data);
					result(data.lista);
					$('#'+inputId).attr("codigoLugar", '');
				}
			});
		},
		select: function( event, ui ) {
			$('#'+$(this).attr('id')).attr("codigoLugar", ui.item.id);
		},
		minLength: 3,
		appendTo: "#"+$('#'+inputId).parent().attr('id')
	}).autocomplete( "instance" )._renderMenu = function( ul, items ) {
		var that = this;
		$.each( items, function( index, item ) {
			that._renderItemData( ul, {value:item.p_descripcionLugar, label:item.p_descripcionLugar, id:item.p_codigoLugar});
		});
		$( ul ).find( "li:odd" ).addClass( "odd" );
	};
}

function generaInfoCot() {
	if (!valIsNullOrEmpty( infCotString )) {
		infoCotJson = JSON.parse( infCotString );
	}
}

function actualizainfoCot(){
	switch (infoCotJson.modo) {
		case modo.NUEVA:
			infoCotJson.modo = modo.EDICION;
			break;
		case modo.COPIA:
			infoCotJson.modo = modo.EDICION;
			break;
		case modo.ALTA_ENDOSO:
			infoCotJson.modo = modo.EDITAR_ALTA_ENDOSO;
			break;
		case modo.BAJA_ENDOSO:
			infoCotJson.modo = modo.EDITAR_BAJA_ENDOSO;
			break;

		default:
			break;
	}
	
}

function clickBtnBack() {
	document.getElementById("form_regresarPaso1").submit();
}
function clickBtnNext(){
	$('#action_next').click();
}

function saveRutas(){
	if(validaRequeridos()){
		generaInfoRutas();
		guardaRutas();
	}else{
		showMessageError(".modal-header", "Todos los campos son obligatorios", 0);
	}
	
}

function validaRequeridos(){
	removeClassInvalid();
	var completos = true;
	$.each($("#modalAddRutas #bodyModalRuta .requeridoI"), function(index, value) {
		if(vaciosInpText(value)){
			completos = false;
			$(value).siblings(".alert").text("requerido")
		}
		

    });
	$.each($("#modalAddRutas #bodyModalRuta select.requeridoS"), function(index, value) {
		completos = noSelect(value) ? false : completos;
	});
	
	return completos;
}

$("#modalAddRutas #bodyModalRuta").on("click", ":input", function(){
	removeClassInvalid();
});

$("#modalAddRutas #bodyModalRuta").on("change", ":input", function(){
	removeClassInvalid();
});

function saveDatosTransportes(){
	removeClassInvalid();
	if(validaObligatorios()){
		generaInfoTransportes();
		guardaDatosTransportes();
	}else{
		showMessageError( '.navbar', msj.es.faltaInfo, 0 );
	}
}

function validaObligatorios(){
	var completos = true;
	$('#collapseDatosContratante').collapse("show");
	$.each($("select.requerido"), function(key, campo) {
		completos = noSelect($(campo)) ? false : completos;
	});
	var vacios = true;
	$.each($(".unoDeTres"), function(key, campo) {
		vacios = !valIsNullOrEmpty($(campo).val()) ? false :  vacios;
	});	
	if(vacios){
		$.each($(".unoDeTres"), function(key, campo) {
			completos = vaciosInpText(campo) ? false : completos;
		});
	}
	completos = vaciosInpText( $('.reqTxt') ) ? false : completos;
	return completos;
}

function generaInfoRutas(){
	
	rutas = [];
	
	$.each($(".rowRutas"), function(index, value){
			var rutaAux = new Object();
			
			rutaAux.p_origen = $(this).find('select.selCiudadO').val();
			rutaAux.p_destino = $(this).find('select.selCiudadD').val();
			
		    rutaAux.p_pronosticoRutas = quitaTipoMoneda( $(this).find('input.txtPronos').val() );
		    rutaAux.p_limEmbarqueRutas = quitaTipoMoneda( $(this).find('input.txtLME').val() );
		    rutaAux.p_cuotaRutas = quitaTipoMoneda( $(this).find('input.txtCuota').val() );
			
			rutas.push(rutaAux);
	});
}

function generaInfoTransportes(){
	
	CondicionesAseguramiento.tipoMercancia = $("#tipoMercancia").val();
	CondicionesAseguramiento.clausula = $("#clausulaIL").val();
	CondicionesAseguramiento.tipoBienes = $("#selTipoBienes").val();
	CondicionesAseguramiento.tipoPronostico = $("#selTipoPronostico").val();
	CondicionesAseguramiento.territorialidad = $("#selTerritorialidad").val();
	CondicionesAseguramiento.origen = $("#selTraOrigen").val();
	CondicionesAseguramiento.destino = $("#selTraDestino").val();
	CondicionesAseguramiento.primaDeposito = quitaTipoMoneda( $("#primaDeposito").val() );
	CondicionesAseguramiento.bienesTransportar = $("#bienesTransportar").val();
	
	LimitesGenerales.robo = $("#checkRobo").is(':checked') ? 1 : 0;
	LimitesGenerales.monto = quitaTipoMoneda($("#inRobo").val());
	LimitesGenerales.limiteMaxEmbarque = quitaTipoMoneda($("#intLMaxEmgarbue").val());
	LimitesGenerales.limiteMaxEstadia = quitaTipoMoneda($("#intLMaxEstadia").val());
	LimitesGenerales.pronosticoTerrestre = quitaTipoMoneda($("#inTerrestre").val());
	LimitesGenerales.pronosticoMaritimo = quitaTipoMoneda($("#inMaritimo").val());
	LimitesGenerales.pronosticoIntermodal = quitaTipoMoneda($("#inIntermodal").val());
	LimitesGenerales.mensajeria = $("#checkEnvMensajeria").is(':checked') ? 1 : 0;
	LimitesGenerales.montoMensajeria = quitaTipoMoneda($("#inEnvMensajeria").val());
	LimitesGenerales.prevencion = $("#checkPrevencionCG").is(':checked') ? 1 : 0;
	LimitesGenerales.medioCertificado = $("#checkMedioTransporteCG").is(':checked') ? 1 : 0;
	LimitesGenerales.viasComunicacion = $("#checkViasCG").is(':checked') ? 1 : 0;
	
	CoberturasAdicionales.estadia = $("input:radio[name='groupInterrupcionRadios']:checked").val();
	CoberturasAdicionales.averias = $("#checkAverias").is(':checked') ? 1 : 0;
	CoberturasAdicionales.huelgaTerrestre = $("#checkHTerrestre").is(':checked') ? 1 : 0;
	CoberturasAdicionales.huelgaMaritimo = $("#checkHMaritimo").is(':checked') ? 1 : 0;
	CoberturasAdicionales.montoHMaritimo = quitaTipoMoneda($("#inHMaritimo").val());
	CoberturasAdicionales.huelgaAereo = $("#checkHAereo").is(':checked') ? 1 : 0;
	CoberturasAdicionales.montoHAereo = quitaTipoMoneda($("#inHAereo").val());
	CoberturasAdicionales.huelgaEstadia = $("#checkHEstadia").is(':checked') ? 1 : 0;
	CoberturasAdicionales.montoHEstadia = quitaTipoMoneda($("#inHEstadia").val());
}

function guardaRutas(){
	showLoader();
	$.post(guardaRutasURL,{
		rutas: JSON.stringify(rutas),
		cotizacion: infCotString.cotizacion,
		folio: infCotString.folio,
		version: infCotString.version,
		pantalla: infCotString.pantalla
	}).done(function(data){
		console.log(data);
		if( !valIsNullOrEmpty(data) ){
			var jResponse = JSON.parse(data);
			if( jResponse.code == 0  ){
				if( rutas.length > 1 ){
					llenaCampoText($('#inTerrestre'), '', true);
					llenaCampoText($('#inMaritimo'), '', true);
					llenaCampoText($('#inIntermodal'), formatoNumerico(jResponse.pronosticoIntermodal), true);
					llenaCampoText($('#inRobo'), formatoNumerico(jResponse.limiteMaxEmbarque), true);
					llenaCampoText($('#intLMaxEmgarbue'), formatoNumerico(jResponse.limiteMaxEmbarque), true);
					llenaCampoText($('#intLMaxEstadia'), formatoNumerico(jResponse.limiteMaxEmbarque), true);
					
					selOpcResest($('#selTraOrigen'), $('#inOrigen0').val(), false);
					selOpcResest($('#selTraDestino'), $('#inDestino0').val(), false);
				}else{
					llenaCampoText($('#intLMaxEmgarbue'), $('#inLmeMdl0').val(), false);					
					llenaCampoText($('#intLMaxEstadia'), '', false);					
					llenaCampoText($('#inTerrestre'), '', false);					
					llenaCampoText($('#inMaritimo'), '', false);					
					llenaCampoText($('#inIntermodal'), $('#inPronosticoMdl0').val(), false);					
					llenaCampoText($('#inRobo'), $('#intLMaxEmgarbue').val(), true);
					
					selOpcResest($('#selTraOrigen'), $('#inOrigen0').val(), false);
					selOpcResest($('#selTraDestino'), $('#inDestino0').val(), false);
				}
				showMessageSuccess( '.navbar', jResponse.msg, 0 );		
				$('#modalAddRutas').modal('hide');
			}else{
				showMessageError( '.modal-header', jResponse.msg, 0 );
			}
		}
		hideLoader();
	});
}

function guardaDatosTransportes(){
	showLoader();
	$.post(guardaDatosPaso2,{
		condicionesAseguramiento: JSON.stringify(CondicionesAseguramiento),
		limitesGenerales: JSON.stringify(LimitesGenerales),
		coberturasAdicionales: JSON.stringify(CoberturasAdicionales),
		cotizacion: infCotString.cotizacion,
		folio: infCotString.folio,
		version: infCotString.version,
		pantalla: infCotString.pantalla
	}).done(function(data){
		console.log(data);
		if( !valIsNullOrEmpty(data) ) {
			var jResponse = JSON.parse(data);
			/*
			if( jResponse.code == 0  ){
				showMessageSuccess( '.navbar', jResponse.msg, 0 );
				validaSuscripcion();
			}else{
				showMessageError( '.navbar', jResponse.msg, 0 );
				$('#paso2_next').attr('disabled', true);
			}*/
			switch( jResponse.code ) {
			  case 0:
				  showMessageSuccess( '.navbar', jResponse.msg, 0 );
				  validaSuscripcion();
			    break;
			  case 2:
				  $('#paso2_next').addClass('d-none');
				  $('#sendSuscrip').addClass('d-none');
				  showMessageError( '.navbar', jResponse.msg, 0 );
				  break;
			  case 5:
				  if(infCotString.canalNegocio != 2525) {
					  $('#paso2_next').attr('disabled', true);
					  $('#paso2_next').addClass('d-none');
					  $('#sendSuscrip').removeClass('d-none');
					  showMessageError( '.navbar', jResponse.msg, 0 );
				  }
			    break;
			  case 3:
				  $('#paso2_next').attr('disabled', true);
				  $('#paso2_next').addClass('d-none');
				  $('#sendSuscrip').removeClass('d-none');
				  showMessageSuccess( '.navbar', jResponse.msg, 0 );
				  break;
			  default:
				  showMessageError( '.navbar', jResponse.msg, 0 );
			    // code block
			}
		}
		else {
			showMessageError( '.navbar', "Ocurrió un error durante el guardado de la información", 0 );
		}
		hideLoader();
	});
}

function validaSuscripcion(){
	$.post(validaSuscripcionURL,{
		cotizacion: infCotString.cotizacion,
		folio: infCotString.folio,
		version: infCotString.version,
		canalNegocio : infCotString.canalNegocio
	}).done(function(data){
		console.log(data);
		if( !valIsNullOrEmpty(data) ){
			var jResponse = JSON.parse(data);
			
			switch( jResponse.code ) {
			  case 0:
				  $('#paso2_next').attr('disabled', false);
				  $('#paso2_next').removeClass('d-none');
				  $('#sendSuscrip').addClass('d-none');
				  showMessageSuccess( '.navbar', jResponse.msg, 0 );
			    // code block
			    break;
			  case 5:
				  $('#paso2_next').attr('disabled', true);
				  $('#paso2_next').addClass('d-none');
				  $('#sendSuscrip').removeClass('d-none');
				  showMessageError( '.navbar', jResponse.msg, 0 );
			    // code block
			    break;
			  default:
				  showMessageError( '.navbar', jResponse.msg, 0 );
			    // code block
			}
			
		}
		hideLoader();
	});
}


/*function enviaSuscripcion(){
	if( !valIsNullOrEmpty( $('#fileReporte').val() ) ) {
		showLoader();
		window.location.href = window.location.origin + window.location.pathname;		
	}else{
		$('#modalArchivos').modal('show');
		showMessageError('#modalArchivos .modal-header', 'Falta archivo obligatorio', 0);
	}
}*/


function enviaSuscripcion(){
	if( !valIsNullOrEmpty( $('#fileReporte').val() ) ) {
		showLoader();
		adjuntaArchivos();
		/*window.location.href = window.location.origin + window.location.pathname;*/	
	}else{
		$('#modalArchivos').modal('show');
		showMessageError('#modalArchivos .modal-header', 'Falta archivo obligatorio', 0);
	}
};


async function adjuntaArchivos() {

    var url = new URL(window.location.href);
    var data = new FormData();
    var auxiliarDoc = '{';

    $.each($('.inFile'), function(i, f) {
		var file = f.files[0];
    /*$.each($('#modalArchivos')[0].files, function(i, file) {*/
		if (f.files.length > 0) {	
	        data.append('file-' + i, file);
	        var nomAux = file.name.split('.');
	        if (i == 0) {
	            auxiliarDoc += '\"file-' + i + '\" : {';
	        } else {
	            auxiliarDoc += ', \"file-' + i + '\" : {';
	        }
	        auxiliarDoc += '\"nom\" : \"' + nomAux[0] + '\",';
	        auxiliarDoc += '\"ext\" : \"' + nomAux[1] + '\"}';
		}
    });
    auxiliarDoc += '}';

    data.append('auxiliarDoc', auxiliarDoc);
    data.append('comentarios', $('#mdlFileComentarios').val());
    data.append('infoCot', JSON.stringify(infCotString));
    data.append('url', url.origin + url.pathname); 
    data.append('url2', url.origin);
    data.append('totArchivos', $('.inFile')[0].files.length );

    $.ajax({
        url: sendMailAgenteSuscriptorURL,
        data: data,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data) {
            if (data != "") {
                var response = jQuery.parseJSON(data);
                if (response.code > 0) {
                    $('#fileModal').modal('hide');
                    hideLoader();
                    showMessageError('.navbar', response.msg, 0);
                } else {
                    /*window.location.href = url.origin + url.pathname.replace("/paso2", seleccionaVentana());*/
                	window.location.href = window.location.origin + window.location.pathname;
                }
            } else {
            	showMessageError('.navbar', "Error al enviar la información", 0);
            }
        }
    });
}

function consultaRutas(){
	resetModalRutas();
	
	$.post(consultaRutasURL,{
		cotizacion: infCotString.cotizacion,
		folio: infCotString.folio,
		version: infCotString.version,
		pantalla: infCotString.pantalla
	}).done(function(data){
		console.log(data);
		if( !valIsNullOrEmpty(data) ){
			var jsonResponse = JSON.parse(data);
			if( jsonResponse.code==0 ){
				if( jsonResponse.lista.length == 0 ){
					addRutaPantalla();
				}else{
					addRutasModal(jsonResponse);					
				}
			}else{
				console.log("error");
				console.log(jsonResponse.msg);
			}
		}else{
			addRutaPantalla();
		}
		$('#modalAddRutas').modal('show');
	});
	
}

function addRutaPantalla(){
	var auxOrigen = $('#selTraOrigen option:selected').val();
	var auxDestino = $('#selTraDestino option:selected').val();
	
	$('#inOrigen0 option[value= '+auxOrigen+']').attr('selected', true);
	selectDestroy( $('#inOrigen0'), false);
	$('#inDestino0 option[value= '+auxDestino+']').attr('selected', true);
	selectDestroy( $('#inDestino0'), false);
	
	llenaCampoText($('#inPronosticoMdl0'), $('#inRobo').val(), false);
	llenaCampoText($('#inLmeMdl0'), $('#intLMaxEmgarbue').val(), false);
}

function addRutasModal(jsonResponse){
	$.each(jsonResponse.lista, function(key, val) {
		if( key != 0 ){
			addRutaJsp();
		}
		
		selOpcResest($('#modalAddRutas #bodyModalRuta .selCiudadO').last(), val.p_origen, false);
		selOpcResest($('#modalAddRutas #bodyModalRuta .selCiudadD').last(), val.p_destino, false);
		
		llenaCampoText($('#modalAddRutas #bodyModalRuta input.txtPronos').last() , formatoNumerico(val.p_pronosticoRutas), false);
		llenaCampoText($('#modalAddRutas #bodyModalRuta input.txtLME').last() , formatoNumerico(val.p_limEmbarqueRutas), false);
		llenaCampoText($('#modalAddRutas #bodyModalRuta input.txtCuota').last() , val.p_cuotaRutas, false);
		console.log(val);
	});
}

function formatoNumerico(val){
	var strVal = ''+val;
	var valNum = strVal.replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
	return valNum;
}

function resetModalRutas(){
	$('#modalAddRutas .rowRutas:not(:first)').remove();
	$('#modalAddRutas #bodyModalRuta hr').remove();
	$('#modalAddRutas #bodyModalRuta input').val('');
}

$('.ciudad-autocomplete').blur(function(){
	if( $(this).val().length < 3 ){
		$(this).attr("codigoLugar", '');
	}
});

$( "#modalAddRutas" ).on("keyup", ".auxPorcen",function() {
	validaPorcentajeDecimales();
});

function validaPorcentajeDecimales(){
	var aux = $(event.target).val().split('.');
	$(event.target).val(aux[0]);
	$(event.target).val(function (index, value ) {
		if ( aux.length > 1 ) {
			return value.replace(/\D/g, "") + '.' + aux[1].slice(0,6);						
		}else{
			return value.replace(/\D/g, "");			
		}
	});
}

function validaCampoPorcentaje(){
	$(event.target).val(function(index, value) {
		 var aux = value.replace(/[$,]/g, '');
		 aux = aux.replace(/\D/g, "")
		 .replace(/([0-9])([0-9]{6})$/, '$1.$2')
		 .replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
	        
		 aux = aux.replace(/\,/g, '');
		 /*
		 if (parseInt(aux) > 100) {
			 showMessageError('.navbar', 'El porcentaje no puede superar el 100% ', 0);
			 return '100.00';
		 }
		 */
	        
		 var res = aux.split(".");
		 if( res[0].length > 7){
			 /*showMessageError('.navbar', 'El porcentaje mínimo es 00.000001% ', 0);*/
			 return '000.000001'
		 }
	        
		 return aux;
	 });
}

function validaReglasNEgocio(){
	
}

$('#modalArchivos').on('click' , '.cleanFile', function(){
    $(this).closest(".row").find('input').val('');
});

function closeModalFiles(){
	$('#modalArchivos input,textarea').val('');
	$('#modalArchivos').modal('hide');
}
function saveFilesPaso2(){
	enviaArchivosModal();
	
}

function enviaArchivosModal() {
	if( !valIsNullOrEmpty( $('#fileReporte').val() ) ) {
		showLoader();
		var url = new URL(window.location.href);
		var data = new FormData();
		var auxiliarDoc = '{';
		var docCompletos = true;
		var totArchivos = 0;
		
		$.each($('.inFile'), function(k, f) {
			/*var fileAux = f.files[0]; */
			$.each(f.files, function(i, file) {
				if (f.files.length > 0) {
					data.append("file-" + totArchivos, file);
					var nomAux = file.name.split('.');
					if (totArchivos == 0) {
						auxiliarDoc += '\"file-' + totArchivos + '\" : {';
					} else {
						auxiliarDoc += ', \"file-' + totArchivos + '\" : {';
					}
					auxiliarDoc += '\"nom\" : \"' + nomAux[0] + '\",';
					auxiliarDoc += '\"iddoc\" : \"0\",';
					auxiliarDoc += '\"idcatdet\" : \"6168\",';
					auxiliarDoc += '\"ext\" : \"' + nomAux[1] + '\"}';
					$(f).parent().addClass('btn-green');
					$(f).parent().removeClass('btn-blue');
					totArchivos++;
				} else {
					$(f).parent().addClass('btn-orange');
					$(f).parent().removeClass('btn-blue');
					docCompletos = false;
				}
			});
		});
		auxiliarDoc += '}';
		
		console.log("totArchivos : " + totArchivos);
		
		data.append('auxiliarDoc', auxiliarDoc);
		data.append('comentarios', $('#mdlFileComentarios').val());
		data.append('folio', infCotString.folio);
		data.append('cotizacion', infCotString.cotizacion);
		data.append('version', infCotString.version);
		data.append('modo', infCotString.modo);
		data.append('url', url.origin + url.pathname);
		data.append('url2', url.origin);
		data.append('totArchivos', totArchivos);
		
		$.ajax({
			url: enviaArchivosURL,
			data: data,
			processData: false,
			contentType: false,
			type: 'POST',
			success: function() {
				showMessageSuccess('#modalArchivos .modal-header', 'proceso terminado con éxito', 0);
				$('#modalArchivos').modal('hide');
				hideLoader();
			},
			fail: function() {
				showMessageError('#modalArchivos .modal-header', 'Error al enviar la información ', 0);
				hideLoader();
			}
		});
		
	}else{
		showMessageError('#modalArchivos .modal-header', 'Falta archivo obligatorio', 0);
	}
}

var listMimetypeValid = [
	"application/msword", "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/jpeg",
	"application/vnd.ms-outlook", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "message/rfc822"
	];

$( ".inFile" ).change(function() {
	var tamano = $(this)[0].files[0].size;
	var tipo = $(this)[0].files[0].type;
	
	console.log("tamano: " + tamano)
	
	if(listMimetypeValid.indexOf(tipo) < 0) {
		
		if(($(this)[0].files[0].name.indexOf('.msg') < 0) && tipo == "") {
		
			showMessageError( '.modal-content', "Error, el tipo del archivo no es valido", 0 );
			$(this).val("");
			/*$('#fileGasto1').val("");*/
		
	    	if((tamano/1048576)>5){
	    		showMessageError( '.modal-content', "Error, el peso del archivo excede los 5 MB", 0 );
	    		
	    		$(this).val("");
	    		$('#fileGasto1').val("");
			}
		}
	}
	
});

$( "#selTipoBienes" ).change(function() {
	validPerecederos();
});

$( "#clausulaIL" ).change(function() {
	validaRobo();
});

function validaRobo(){
	if($('#clausulaIL option:selected').html()=="CLÁUSULA A"){
		$("#checkRobo").prop('checked', true);
		$("#checkRobo").attr('disabled', true);
		$("#inRobo").removeClass('d-none');
		sumarobo();
	}else{
		$("#checkRobo").attr('disabled', false);
	}
}

function validPerecederos() {
	if($('#selTipoBienes option:selected').html()=="PERECEDEROS"){
		$("#divAverias").removeClass('d-none');
	}else{
		$("#divAverias").addClass('d-none');
		$("#checkAverias").removeAttr("checked");
	}
}

$( "#inTerrestre" ).on("blur", function(){
	validTerrestre();
	validaPronosticoAnual();
});
	
$( "#inMaritimo" ).on("blur", function(){
	validMaritimoAereo();
	validaPronosticoAnual();
});

$( "#inIntermodal" ).on("blur", function(){
	validTerrestre();
	validMaritimoAereo();
	validaPronosticoAnual();
});

function validaPronosticoAnual(){
	// Mantis 3927
	if(quitaTipoMoneda($("#inTerrestre").val())>0 || quitaTipoMoneda($("#inMaritimo").val())>0){
		$("#inIntermodal").attr('disabled', true);
		$("#inIntermodal").val(0);
	}else{
		$("#inIntermodal").attr('disabled', false);
	}
	if(quitaTipoMoneda($("#inIntermodal").val())>0){
		$("#inTerrestre").attr('disabled', true);
		$("#inMaritimo").attr('disabled', true);
		$("#inTerrestre").val(0);
		$("#inMaritimo").val(0);
	}else{
		$("#inTerrestre").attr('disabled', false);
		$("#inMaritimo").attr('disabled', false);
	}
	
}

function validTerrestre() {
	if(quitaTipoMoneda($("#inTerrestre").val())>0 || quitaTipoMoneda($("#inIntermodal").val())>0){
		$("#divHuelgasTerrestre").removeClass('d-none');
	}else{
		$("#divHuelgasTerrestre").addClass('d-none');
		$("#checkHTerrestre").removeAttr("checked");
	}
}

function validMaritimoAereo() {
	if(quitaTipoMoneda($("#inMaritimo").val())>0  || quitaTipoMoneda($("#inIntermodal").val())>0){
		$("#divHuelgasMaritimo").removeClass('d-none');
		$("#divHuelgasAereo").removeClass('d-none');
	}else{
		$("#divHuelgasMaritimo").addClass('d-none');
		$("#divHuelgasAereo").addClass('d-none');
		$("#checkHMaritimo").removeAttr("checked");
		$("#checkHAereo").removeAttr("checked");
	}
}

$('#clausulaIL').change(function() {
	$("#selTipoPronostico option:not(:first)").remove();
	$.each(listaCatTipoPron, function(key, registro) {
		if($('#clausulaIL option:selected').html()=="CLÁUSULA C" || ($('#clausulaIL option:selected').html()!="CLÁUSULA C" && registro.otro!='C')){
	        $("#selTipoPronostico").append("<option value=\"" + registro.idCatalogoDetalle + "\" >" + registro.valor + "</option>");			
		}
    });
    selectDestroy($("#selTipoPronostico"), false);
    
	$("#selTipoBienes option:not(:first)").remove();
	$.each(listaCatTipoBie, function(key, registro) {
		if($('#clausulaIL option:selected').html()=="CLÁUSULA C" || ($('#clausulaIL option:selected').html()!="CLÁUSULA C" && registro.otro!='C')){
	        $("#selTipoBienes").append("<option value=\"" + registro.idCatalogoDetalle + "\" >" + registro.valor + "</option>");
		}
    });
    selectDestroy($("#selTipoBienes"), false);
});