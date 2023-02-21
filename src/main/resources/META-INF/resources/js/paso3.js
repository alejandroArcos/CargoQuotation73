$( document ).ready( function() {
	showLoader();
	window.scrollTo( 0, 0 );
	seleccionaModo();
	hideLoader();
	setBase64();
	validaTipoMoneda();
});

function validaTipoMoneda() {
	$.each( $( ".moneda" ), function(key, registro) {
		daFormatoMoneda( registro );
	} );
}

$("#tabPaso3_2").on('blur', '.campoEditable', function(event) {
	
	$(this).attr('disabled', true);
});

$("#porcentajeAjustes").on("keyup blur",function() {
	validaCampoPorcentaje();
});

function seleccionaModo() {
	switch (infCotiJson.modo) {
		case modo.NUEVA :
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			if(!valIsNullOrEmpty($("#txtDividendo").val())) {
				$("#checkDividendo").click();
			}
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.EDICION :
			fValidaAgente();
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			if(!valIsNullOrEmpty($("#txtDividendo").val())) {
				$("#checkDividendo").click();
			}
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.COPIA :
			fValidaAgente();
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.ALTA_ENDOSO :
			fValidaAgente();
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.EDITAR_ALTA_ENDOSO :
			fValidaAgente();
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.CONSULTA :
			fValidaAgente();
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
				$("#checkSiniestralidad").parent().addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			if(!valIsNullOrEmpty($("#txtDividendo").val())) {
				$("#checkDividendo").click();
			}
			else {
				$("#checkDividendo").parent().addClass('d-none');
			}
			disabled = 'disabled';
			$("#btnRecalcularPrima").addClass('d-none');
			$('#art41').addClass('d-none');
			$('#divPrimaObj').addClass('d-none');
			$('#divPrimaDepo').addClass('d-none');
			$('#aplicaDividendo').addClass('d-none');
			$('#siniestralidad').addClass('d-none');
			$("#tabPaso3_2 a").addClass('d-none');
			$("#btnDeducibles").attr('disabled', true);
			$("#btnClausulas").attr('disabled', true);
			$("#btnComisionesAgente").attr('disabled', true);
			$("#btnReaseguro").attr('disabled', true);
			$("#btnCoaseguro").attr('disabled', true);
			$("#paso3_noAceptado").removeClass("d-none");
			$("#paso3_revire").removeClass("d-none");
			
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.CONSULTA_VOBO_REASEGURO :
			disabled = 'disabled';
			
			$("#btnRecalcularPrima").addClass("d-none");
			$("#art41").addClass("d-none");
			$("#divPrimaObj").addClass("d-none");
			$("#divPrimaDepo").addClass("d-none");
			$("#aplicaDividendo").addClass("d-none");
			$("#siniestralidad").addClass("d-none");
			$("#tabPaso3_2 a").addClass("d-none");
			$("#btnClausulas").addClass("d-none");
			$("#btnDeducibles").addClass("d-none");
			$("#paso3_noAceptado").addClass("d-none");
			$("#paso3_revire").addClass("d-none");
			$("#paso3_Slip").addClass("d-none");
			$("#paso3_next_form").addClass("d-none");
			$("#paso3_next").addClass("d-none");
			$("#btnNoAcepPropuesta").addClass("d-none");
			
			$("#btnComisionesAgente").removeClass("d-none");
			$("#btnReaseguro").removeClass("d-none");
			$("#btnCoaseguro").removeClass("d-none");
			break;
		case modo.BAJA_ENDOSO :
			fValidaAgente();
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.EDITAR_BAJA_ENDOSO :
			fValidaAgente();
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.FACTURA_492 :
			fValidaAgente();
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			if(!valIsNullOrEmpty($("#txtDividendo").val())) {
				$("#checkDividendo").click();
			}
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.EDICION_JAPONES:
			fValidaAgente();
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			if(!valIsNullOrEmpty($("#txtDividendo").val())) {
				$("#checkDividendo").click();
			}
			console.log('modo: ' + infCotiJson.modo);
			break;
		case modo.COASEGURO:
		case modo.REASEGURO:
			fValidaAgente();
			if(valIsNullOrEmpty($("#noAjustes").val()) || parseFloat($("#noAjustes").val().replace( /[$,]/g, '' )) == 0) {
				$('.siniestraValida').addClass('d-none');
			}
			else {
				$("#checkSiniestralidad").click();
			}
			break;
		case modo.DECLARACION_ENDOSO:
			fValidaAgente();
			break;
		default:
			showMessageError('.navbar', msj.es.errorInformacion, 0);
			console.error(" Modo default");
			break;
	}
}

var countDeducible = 1
function addDeducibleJsp(){
	if( $('#rowDeduciblesLibre .rowDeducible').length < 5){
		$('#rowDeduciblesLibre').append(`
				<div class="row rowDeducible">
				<div class="col-md-12">
				<div class="row">
				<div class="col-md-11"><span></span></div>
				<div class="col-md-1 mt-4">
					<a class="borraDeducible"><i class="fa fa-trash" aria-hidden="true"></i></a>
				</div>
				</div>
				<div class="row">
				<div class="col-md-3">
				<div class="md-form">
				<input type="text" id="txtDeducible`+countDeducible+`" class="form-control descripcionDed">
				</div>
				</div>
				<div class="col-md-2">
				<div class="md-form">
				<input type="text" id="mdlPorcenDedu`+countDeducible+`" class="form-control porcenDed porcentajeDeducible">
				</div>
				</div>
				<div class="col-md-3">
				<div class="md-form form-group">
					<select id="mdlCritDedu`+countDeducible+`" class="mdb-select form-control-se criterioDed">
						<option value="-1" selected>Seleccione una opción</option>
					</select>
				</div>
				</div>
				<div class="col-md-2">
				<div class="md-form">
				<input type="text" id="mdlMontoDedu`+countDeducible+`" class="form-control montoDed moneda">
				</div>
				</div>
				<div class="col-md-2">
				<div class="md-form form-group">
				<select id="mdlUnidadDedu`+countDeducible+`" class="mdb-select form-control-sel unidadDed">
				</select>
				</div>
				</div>
				</div>
				</div>
				</div>
		`);

		$.each(catalogoCriterios.lista, function(key, value) {
			$("#mdlCritDedu"+countDeducible).append("<option value=\"" + value.idCatalogoDetalle + "\">" + value.valor + "</option>");
		});
		
		selectDestroy( $('#mdlCritDedu'+countDeducible) , false);
		$('#mdlCritDedu'+countDeducible).attr('disabled', true);
		
		$.each(catalogoUnidades.lista, function(key, value) {
			$("#mdlUnidadDedu"+countDeducible).append("<option value=\"" + value.idCatalogoDetalle + "\" selected>" + value.valor + "</option>");
		});

		selectDestroy( $('#mdlUnidadDedu'+countDeducible) , false);
		countDeducible++;
	}else{
		showMessageError('#modalDeducibles .modal-header', 'Máximo 5 deducibles adicionales', 0);
	}
}

$("#rowDeduciblesLibre").on('click', '.borraDeducible', function() {
	
	$(this).closest('.rowDeducible').remove();
});

function clickBtnNext(){
	
	showLoader();

	$.post(enviarCotizacionURL, {
		/*infoCotiResponse: infoCotiResponse*/
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version
	}).done(function(data) {
		if( !valIsNullOrEmpty(data) ){
			var jsonResponse = JSON.parse(data);
			if( jsonResponse.code == 0 ){
				/*goToHome();*/
				
				/*Envio de correo*/
				
				var url = new URL(window.location.href);
				var auxUrl = url.pathname;
			    
			    $.post( sendMailSuscriptorAgenteURL , {
			        cotizacion: infCotiJson.cotizacion,
			        version: infCotiJson.version,
			        folio: infCotiJson.folio,
			        url: url.origin + auxUrl,
			        tipoCotizacion: infCotiJson.tipoCotizacion.toString(),
			        email: $('#txtEmailAgente').val()
			    }).done(function() {
			    	goToHome();
			    });
				
				/*Envio de correo*/
				
			}else{
				hideLoader();
				showMessageError('.navbar', jsonResponse.msg, 0);
			}
		}else{
			hideLoader();
			showMessageError('.navbar', "Error al enviar", 0);
		}
	});
}

function goToHome(){
	
	showLoader();
	
	var urlHome = window.location.origin + window.location.pathname;
	
	window.location.href = urlHome;
}

function clickBtnBack(){
	
	guardaSiniestralidad();
	
	$("#formPaso3Back #infoCot").val(JSON.stringify(infCotiJson));
	$('#action_back').click();
}

function selectDestroy(objeto, enabled) {
    $(objeto).prop("disabled", enabled);
    $(objeto).materialSelect('destroy');
    $(objeto).materialSelect();
}

$("#modalDeducibles").on('keyup keydown', '.porcentajeDeducible', function(){
	
	var expReg1 = /^\d{1,2}(\.)?$/;
	var expReg2 = /^\d{1,2}(\.\d{1,2})?$/;
	var validate = expReg1.test(this.value) || expReg2.test(this.value);
	
	if(!validate){
		this.value = this.value.slice(0, -1);
	}
});

$('#btnRecalcularPrima').click(function(e) {
    try {
        showLoader();
		var primaDeposito = $("#selPrimaObj").val();
        var primaNeta = parseFloat(valIsNullOrEmpty($('#primaNeta').text()) ?
            0 : $('#primaNeta').text().replace(/[$,]/g, ''));
        var primaObj = parseFloat(valIsNullOrEmpty($('#txtPrimaObj').val()) ?
            0 : $('#txtPrimaObj').val().replace(/[$,]/g, ''));
		var gastos = parseFloat(valIsNullOrEmpty($('#gastos').val()) ?
            0 : $('#gastos').val().replace(/[$,]/g, ''));
		var recargoPago = parseFloat(valIsNullOrEmpty($('#recargoPago').val()) ?
            0 : $('#recargoPago').val().replace(/[$,]/g, ''));
        var idPerfil = parseInt($('#txtIdPerfilUser').val());
        var minPrima = parseFloat($('#txtMinPrima').val());
        var cambioDoll = parseFloat($('#txtTpoCambio').val());
        /*var tipoMonSelect = parseInt($('#dc_moneda option:selected').val());   CAMBIAR POR NUEVA VARIABLE*/ 
        var tipoMonSelect = parseInt($('#dc_moneda').val());
        if (tipoMonSelect != 1) {
            minPrima = minPrima / cambioDoll;
        }

        var nuevaCaratula = true;

		/*
        if (idPerfil != 1) {
            if (primaObj < primaNeta) {
                nuevaCaratula = false;
                showMessageError('.navbar', 'La prima capturada no puede ser menor a ' + generaFormatoNumerico(primaNeta, true, true, false), 0);
            }
        }
        if (primaObj < minPrima) {
            nuevaCaratula = false;
            showMessageError('.navbar', 'La prima minima para el Cotizador '+ infCotiJson.tipoCotizacion +' es ' + generaFormatoNumerico(minPrima, true, true, false), 1);
        }
		*/
        
        guardaSiniestralidad();

        if (nuevaCaratula) {
            $.post(ligasServicios.recalculoPrimaDeposito, {
                cotizacion: infCotiJson.cotizacion,
                version: infCotiJson.version,
				pantalla: infCotiJson.pantalla,
                primaObjetivo: primaObj,
				gastos: gastos,
				recargoPago: recargoPago,
				primaDeposito: primaDeposito
            }).done(function(data) {


                var respuestaJson = JSON.parse(data);
                if (respuestaJson.code == 0) {
                    $('#txtEmailAgente').val(respuestaJson.email);
                    var band = null;
                    $('#tabPaso3').html("");
                    $.each(respuestaJson.datosCaratula, function(k, valCaratula) {
                        if (!(valCaratula.contenedor == band)) {
                            band = valCaratula.contenedor;

                            $('#tabPaso3').append("<tr><th>" + band + "</td><td></td><td></td><td></th></tr>");
                        }
                        $('#tabPaso3').append("<tr><td>" + valCaratula.titulo + "</td><td class=\"number\">" + valCaratula.sa + "</td><td>" + valCaratula.deducible + "</td></tr>");
                    });
                    $('#tabPaso3_2').html("<tr><td>Prima Neta:</td><td id='primaNeta' class=\"number\">" + setCoinFormat('' + respuestaJson.primaNeta) + "</td></tr>");
                    
					if(perfilSuscriptor == 1 || perfilJapones == 1) {
						$('#tabPaso3_2').append("<tr><td>Recargo por Pago Fraccionado:</td><td class=\"number\"><input id=\"recargoPago\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.recargo) + "\" disabled=\"true\" /></td><td><a onclick=\"editarCamposPrima('recargoPago')\" style=\"color: #0275d8; text-decoration: underline;\">Editar</a></td></tr>");
                		$('#tabPaso3_2').append("<tr><td>Gastos de Expedición:</td><td class=\"number\"><input id=\"gastos\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.gastos) + "\" disabled=\"true\" /></td><td><a onclick=\"editarCamposPrima('gastos')\" style=\"color: #0275d8; text-decoration: underline;\">Editar</a></td></tr>");	
					}
					else {
						$('#tabPaso3_2').append("<tr><td>Recargo por Pago Fraccionado:</td><td class=\"number\"><input id=\"recargoPago\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.recargo) + "\" disabled=\"true\" /></td></tr>");
                    	$('#tabPaso3_2').append("<tr><td>Gastos de Expedición:</td><td class=\"number\"><input id=\"gastos\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.gastos) + "\" disabled=\"true\" /></td></tr>");
					}
                    
					$('#tabPaso3_2').append("<tr><td>I.V.A.:</td><td class=\"number\">" + setCoinFormat('' + respuestaJson.iva) + "</td></tr>");
                    $('#tabPaso3_3').html(setCoinFormat('' + respuestaJson.total));
					$('#titPrimaObj').text("Cuota Objetivo:");
					$('#txtPrimaObj').val(respuestaJson.cuotaObjetivo);

                    hideLoader();
                    showMessageSuccess('.navbar', 'Información actualizada correctamente', 0);
                    validarReaseguro();
                } else {
	
					showMessageError('.navbar', respuestaJson.msg, 0);

                    hideLoader();
                }
            });
        }
        $('#txtPrimaObj').val('');
        hideLoader();
    } catch (err) {

        hideLoader();
        showMessageError('.navbar', 'Error al consultar la información ' + primaNeta, 0);
    }
});

function validarReaseguro(){
	 showLoader();
	 $.post(ligasServicios.validarReaseguro, {
         cotizacion: infCotiJson.cotizacion,
         version: infCotiJson.version,
		 pantalla: infCotiJson.pantalla
     }).done(function(data) {
         var respuestaJson = JSON.parse(data);
         if (respuestaJson.code == 0) {
             hideLoader();
         } else {
			 showMessageError('.navbar', respuestaJson.msg, 0);
             hideLoader();
         }
     });
}

function editarCamposPrima(campo){
	
	showMessageError('.navbar', "Se debe recalcular prima", 0);
	
	$("#"+campo).attr('disabled', false);
	$("#"+campo).focus();
	
	$("#btnEnvCotiSusAgente").attr('disabled', true);
	$("#btnContinuarJK").attr('disabled', true);
	$("#paso3_slip").attr('disabled', true);
	$("#paso3_next").attr('disabled', true);
	$("#paso3_next").addClass('d-none');
	
	banderaEditar = true;
}

$("#primaNeta").focusout(function(){
	$( '#primaNeta' ).attr("contenteditable",false);
  });
$("#recargoPago").focusout(function(){
	$( '#recargoPago' ).attr("contenteditable",false);
});

$("#btnComisionesAgente").click(function(){
	
	guardaSiniestralidad();
	
	$.post(comisionesAgenteURL,
		{
			cotizacion: infCotiJson.cotizacion,
			version: infCotiJson.version,
			pantalla: infCotiJson.pantalla
		})
		.done(function(data){
			responseComision = JSON.parse(data);
			$("#tableComisionesBody").empty();
			$.each(responseComision.lista, function(index, value){
				$("#tableComisionesBody").append('<tr><td>' + value.ramo + '</td><td>' + value.descripcion + '</td><td class="text-right comision" ramo="'+value.ramo+'" valorMin="'+ value.min_valor +'" valorMax="' + value.max_valor + '"> <input type="text" class="auxPorcen" value="' + value.comision + '" ' + disabled + '> </td></tr>')
			});
			if(disabled=='disabled'){
				$("#btnGuardarComisionesAgente").addClass("d-none");
			}
			$("#modalComisionesAgente").modal('show');
		});
});

$("#btnGuardarComisionesAgente").click(function(){
	generaComosionesAgente();
	guardaComosionesAgente();
	
	$.each($(".comision"), function(index, value){
		responseComision.comisiones[index].comisionNueva = parseFloat($(this).text());
	});
	
});

function generaComosionesAgente(){
	flagValMax = true;
	comsisionesAgArr = [];
	
	$.each($("#modalComisionesAgente .comision"), function(index, value){
			var comisionAux = new Object();
			
			comisionAux.codigo_ramo = $(this).attr('ramo');
			comisionAux.valor =  parseFloat($(this).find('input').val());
			
			if ( comisionAux.valor  > parseFloat($(this).attr('valormax')) ) {
				flagValMax = false;
				$(this).addClass('colorRed')
			}
			
			comsisionesAgArr.push(comisionAux);
	});
	console.log( JSON.stringify(comsisionesAgArr) );
	console.log('flag: ' + flagValMax);
}

function guardaComosionesAgente(){
	if( flagValMax ){
		$.post(guardaComisionesAgenteURL,{
			cotizacion: infCotiJson.cotizacion,
			version: infCotiJson.version,
			pantalla: infCotiJson.pantalla,
			comisiones: JSON.stringify(comsisionesAgArr)
		}).done(function(data){
			console.log(data);
			
			var respuestaJson = JSON.parse(data);
			
			if (respuestaJson.code == 0) {
				
				$("#modalComisionesAgente").modal('hide');
				
                $('#txtEmailAgente').val(respuestaJson.email);
                var band = null;
                $('#tabPaso3').html("");
                $.each(respuestaJson.datosCaratula, function(k, valCaratula) {
                    if (!(valCaratula.contenedor == band)) {
                        band = valCaratula.contenedor;

                        $('#tabPaso3').append("<tr><th>" + band + "</td><td></td><td></td><td></th></tr>");
                    }
                    $('#tabPaso3').append("<tr><td>" + valCaratula.titulo + "</td><td class=\"number\">" + valCaratula.sa + "</td><td>" + valCaratula.deducible + "</td></tr>");
                });
                $('#tabPaso3_2').html("<tr><td>Prima Neta:</td><td id='primaNeta' class=\"number\">" + setCoinFormat('' + respuestaJson.primaNeta) + "</td></tr>");
                
				if(perfilSuscriptor == 1 || perfilJapones == 1) {
					$('#tabPaso3_2').append("<tr><td>Recargo por Pago Fraccionado:</td><td class=\"number\"><input id=\"recargoPago\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.recargo) + "\" disabled=\"true\" /></td><td><a onclick=\"editarCamposPrima('recargoPago')\" style=\"color: #0275d8; text-decoration: underline;\">Editar</a></td></tr>");
            		$('#tabPaso3_2').append("<tr><td>Gastos de Expedición:</td><td class=\"number\"><input id=\"gastos\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.gastos) + "\" disabled=\"true\" /></td><td><a onclick=\"editarCamposPrima('gastos')\" style=\"color: #0275d8; text-decoration: underline;\">Editar</a></td></tr>");	
				}
				else {
					$('#tabPaso3_2').append("<tr><td>Recargo por Pago Fraccionado:</td><td class=\"number\"><input id=\"recargoPago\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.recargo) + "\" disabled=\"true\" /></td></tr>");
                	$('#tabPaso3_2').append("<tr><td>Gastos de Expedición:</td><td class=\"number\"><input id=\"gastos\" class=\"moneda campoEditable\" value=\"" + setCoinFormat('' + respuestaJson.gastos) + "\" disabled=\"true\" /></td></tr>");
				}
                
				$('#tabPaso3_2').append("<tr><td>I.V.A.:</td><td class=\"number\">" + setCoinFormat('' + respuestaJson.iva) + "</td></tr>");
                $('#tabPaso3_3').html(setCoinFormat('' + respuestaJson.total));
				$('#titPrimaObj').text("Cuota Objetivo:");
				$('#txtPrimaObj').val(respuestaJson.cuotaObjetivo);

                hideLoader();
                showMessageSuccess('.navbar', 'Información actualizada correctamente', 0);
                validarReaseguro();
            } else {

				showMessageError('.navbar', respuestaJson.msg, 0);

                hideLoader();
            }
		});
	}else{
		showMessageError( '#modalComisionesAgente .modal-header', "La comisión ingresada no debe ser mayor a la comisión máxima del ramo", 0 );
	}
}

$( "#tableComisionesBody" ).on( "click", ".colorRed input", function() {
  $(this).parent().removeClass('colorRed');
});

$( "#tableComisionesBody" ).on("keyup", ".auxPorcen",function() {
	validaCampoPorcentaje();
});

function validaCampoPorcentaje(){
	$(event.target).val(function(index, value) {
		 var aux = value.replace(/[$,]/g, '');
		 aux = aux.replace(/\D/g, "")
		 .replace(/([0-9])([0-9]{2})$/, '$1.$2')
		 .replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
	        
		 aux = aux.replace(/\,/g, '');
		 
		 if (parseInt(aux) > 100) {
			 /*showMessageError('.navbar', 'El porcentaje no puede superar el 100% ', 0);*/
			 return '100.00';
		 }
		 var res = aux.split(".");
		 if( res[0].length > 3){
			 /*showMessageError('.navbar', 'El porcentaje mínimo es 00.000001% ', 0);*/
			 return '0.01'
		 }
	        
		 return aux;
	 });
}

$('#btnContinuarJK').click(function() {
    showLoader();
	
	$.post(continuarJKURL, 
		{
			cotizacion: infCotiJson.cotizacion,
			version: infCotiJson.version,
			pantalla: infCotiJson.pantalla,
		})
	.done(function(data){
		var response = JSON.parse(data);
		showMessageSuccess('.navbar', response.msg, 0);
		hideLoader();
	});
});


$('#btnEnvCotiSusAgente').click(function() {
    showLoader();
    var url = new URL(window.location.href);
	var auxUrl = url.pathname.replace("/paso3", seleccionaVentana());
	
	$.post(enviarCotizacionURL, 
		{
			cotizacion: infCotiJson.cotizacion,
			version: infCotiJson.version,
			pantalla: infCotiJson.pantalla,
		})
	.done(function(data){
		var response = JSON.parse(data);
		showMessageSuccess('.navbar', response.msg, 0);
		hideLoader();
	});
});

$('#btnNoAcepPropuesta').click(function() {
	boton=0; // Estatus -> Rechazada
    $('#modalRechazarProp').modal('show');
});
$('#paso3_noAceptado').click(function() {
	boton=1; // No aceptada
	$('#modalRechazarProp').modal('show');
});
$('#paso3_revire').click(function() {
    $('#btnSuscripEnvSus2').removeClass('d-none');
    $('#fileModal').modal('show');
});
$('#btnSuscripEnvSus2').click(function() {
    showLoader();
    $('#comentariosDosSuscrip').trigger('keyup');

    if ($('#docAgenSusc')[0].files.length == 0 && $('#comentariosDosSuscrip').val().trim().length == 0) {
        showMessageError('#fileModal .modal-header', 'Agregar documentos y/o comentarios', 0);
        hideLoader();
    } else {
        $('#txtAuxEnvDoc').val('1');
        adjuntaArchivos(1);
    }
});
$('#btnEnvRecha').click(function(e) {
    showLoader();
    e.preventDefault();
    var errores = false;
    errores = (noSelect($('#mdlRechOp')) ? true : errores);
    errores = (valIsNullOrEmpty($('#comentariosRechazarProp').val().trim()) ? true : errores);
    if (errores) {
        showMessageError('#modalRechazarProp .modal-header', 'Los campos son obligatorios', 0);
        hideLoader();
    } else {
        showLoader();
        var url = new URL(window.location.href);
        var motivo = $('#modalRechazarProp .modal-body select').val();
        $.post(rechazaCotizacionURL, {
            cotizacion: infCotiJson.cotizacion,
            version: infCotiJson.version,
            motivoRechazo: motivo,
            motivo: $('#comentariosRechazarProp').val(),
            boton: boton // Estatus -> De acuerdo al botón presionado
        }).done(function() {
			hideLoader();
			$('#modalRechazarProp').modal('hide');
			window.location.href = url.origin + url.pathname;
        });
    }
});


function setCoinFormat(num) {
	num = "" + num;
	if( num ==""){
		return num;
	}
	
	arraySplit = num.split(".");
	izq = arraySplit[0];
	der = "00";
	if ( num.includes(".") ) {
		der = arraySplit[1];
	}
	izq = izq.replace(/ /g, "");
	izq = izq.replace(/\$/g, "");
	izq = izq.replace(/,/g, "");

	var izqAux = "";
	var j = 0;
	for ( i = izq.length - 1; i >= 0; i-- ) {
		if ( j != 0 && j % 3 == 0 ) {
			izqAux += ",";
		}
		j++;
		izqAux += izq[i];
	}
	izq = "";
	for ( i = izqAux.length - 1; i >= 0; i-- ) {
		izq += izqAux[i];
	}
	der = der.substring(0, 2);
	if ( der.length < 2 ) {
		der += "0";
	}
	return "$" + izq + "." + der;
}

function agregaTipoMonedaPT(){
	var p1json = JSON.parse(infP1);	
	$("#valPrimTot").addClass("pt_mon");
	if(p1json.monedaSeleccionada == "1"){
		$("#valPrimTot #tabPaso3_3").text($("#valPrimTot #tabPaso3_3").text() + 'MXN');
	}else{
		$("#valPrimTot #tabPaso3_3").text($("#valPrimTot #tabPaso3_3").text() + 'USD')
	}

}

/*
$('#txtPrimaObj').on('keyup', function() {
    $(event.target).val(function(index, value) {
        var aux = value.replace(/[$,]/g, '');
        aux = aux.replace(/\D/g, "")
            .replace(/([0-9])([0-9]{2})$/, '$1.$2')
            .replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
        return '$' + aux;
    });
});
*/

$("#tabPaso3_2" ).on("blur", '.cNumnero', function(event){
	this.value = this.value.replace(/[^0-9\.]/g,'');
});

$("#tabPaso3_2" ).on("blur", '.moneda', function(event){
	this.value = this.value.replace(/[^0-9\.]/g,'');
	daFormatoMoneda($(this));
});

function daFormatoMoneda(campo){
	if(!valIsNullOrEmpty($( campo ).val())){
		$( campo ).val(formatter.format($( campo ).val()));
	}
}

function noSelect(campo) {
    var errores = false;
    if ($(campo).val() == "-1") {
        errores = true;
        $(campo).siblings("input").addClass('invalid');
        $(campo).parent().append(
            "<div class=\"alert alert-danger\"> <span class=\"glyphicon glyphicon-ban-circle\"></span> " + " " +
            msj.es.campoRequerido + "</div>");
    }
    
    return errores;
}

function saveDeducibles(){
	generaDeducibles();
	guardaDeducibles();
}

function generaDeducibles(){
	p_deduciblesFijo = [];
	
	var deduFijo1 = new Object();
	deduFijo1.idDeducible = 6471;
	deduFijo1.descripcion = "Riesgos Ordinarios de Transito";
	deduFijo1.porcentaje = valIsNullOrEmpty( $('#mdlPorcenROT').val() ) ? 0.0 : parseFloat( $('#mdlPorcenROT').val() );
	deduFijo1.idCriterio = valIsNullOrEmpty( $('#mdlCritROT').val() ) ? 0 : parseInt( $('#mdlCritROT').val() );
	deduFijo1.monto = valIsNullOrEmpty( $('#mdlMontoROT').val() ) ? 0 : parseInt( $('#mdlMontoROT').val().replace(/[$,]/g, '') );
	deduFijo1.unidad = valIsNullOrEmpty( $('#mdlUnidadROT').val() ) ? 0 : parseInt( $('#mdlUnidadROT').val() );
	p_deduciblesFijo.push(deduFijo1);

	if(!$(".rowRobo").hasClass('d-none')) {
		var deduFijo2 = new Object();
		deduFijo2.idDeducible = 6472;
		deduFijo2.descripcion = "Robo";
		deduFijo2.porcentaje = valIsNullOrEmpty( $('#mdlPorcenRobo').val() ) ? 0.0 : parseFloat( $('#mdlPorcenRobo').val() );
		deduFijo2.idCriterio = valIsNullOrEmpty( $('#mdlCritRobo').val() ) ? 0 : parseInt( $('#mdlCritRobo').val() );
		deduFijo2.monto = valIsNullOrEmpty( $('#mdlMontoRobo').val() ) ? 0 : parseInt( $('#mdlMontoRobo').val().replace(/[$,]/g, '') );
		deduFijo2.unidad = valIsNullOrEmpty( $('#mdlUnidadRobo').val() ) ? 0 : parseInt( $('#mdlUnidadRobo').val());
		p_deduciblesFijo.push(deduFijo2);
	}

	if(!$(".rowAveria").hasClass('d-none')) {
		var deduFijo3 = new Object();
		deduFijo3.idDeducible = 6473;
		deduFijo3.descripcion = "Averías en Sistema de Refrigeración";
		deduFijo3.porcentaje = valIsNullOrEmpty( $('#mdlPorcenASF').val() ) ? 0.0 : parseFloat( $('#mdlPorcenASF').val() );
		deduFijo3.idCriterio = valIsNullOrEmpty( $('#mdlCritASF').val() ) ? 0 : parseInt( $('#mdlCritASF').val() );
		deduFijo3.monto = valIsNullOrEmpty( $('#mdlMontoASF').val() ) ? 0 : parseInt( $('#mdlMontoASF').val().replace(/[$,]/g, '') );
		deduFijo3.unidad = valIsNullOrEmpty( $('#mdlUnidadASF').val() ) ? 0 : parseInt( $('#mdlUnidadASF').val() );
		p_deduciblesFijo.push(deduFijo3);
	}
	
	if(!$(".rowHT").hasClass('d-none')) {
		var deduFijo4 = new Object();
		deduFijo4.idDeducible = 6488;
		deduFijo4.descripcion = "Huelga Terrestre";
		deduFijo4.porcentaje = valIsNullOrEmpty( $('#mdlPorcenHT').val() ) ? 0.0 : parseFloat( $('#mdlPorcenHT').val() );
		deduFijo4.idCriterio = valIsNullOrEmpty( $('#mdlCritHT').val() ) ? 0 : parseInt( $('#mdlCritHT').val() );
		deduFijo4.monto = valIsNullOrEmpty( $('#mdlMontoHT').val() ) ? 0 : parseInt( $('#mdlMontoHT').val().replace(/[$,]/g, '') );
		deduFijo4.unidad = valIsNullOrEmpty( $('#mdlUnidadHT').val() ) ? 0 : parseInt( $('#mdlUnidadHT').val() );
		p_deduciblesFijo.push(deduFijo4);
	}

	if(!$(".rowIT").hasClass('d-none')) {
		var deduFijo5 = new Object();
		deduFijo5.idDeducible = 7652;
		deduFijo5.descripcion = "Interrupción en el Transporte (Estadía)";
		deduFijo5.porcentaje = valIsNullOrEmpty( $('#mdlPorcenIT').val() ) ? 0.0 : parseFloat( $('#mdlPorcenIT').val() );
		deduFijo5.idCriterio = valIsNullOrEmpty( $('#mdlCritIT').val() ) ? 0 : parseInt( $('#mdlCritIT').val() );
		deduFijo5.monto = valIsNullOrEmpty( $('#mdlMontoIT').val() ) ? 0 : parseInt( $('#mdlMontoIT').val().replace(/[$,]/g, '') );
		deduFijo5.unidad = valIsNullOrEmpty( $('#mdlUnidadIT').val() ) ? 0 : parseInt( $('#mdlUnidadIT').val() );
		p_deduciblesFijo.push(deduFijo5);
	}

	
	p_deduciblesLibre = [];
	
	$.each($("#rowDeduciblesLibre .rowDeducible"), function(index, value){
		var deduAux = new Object();
		deduAux.idDeducible = index;
		deduAux.deducibleAdic = $(this).find('input.descripcionDed').val();
		deduAux.porcentaje = valIsNullOrEmpty( $(this).find('input.porcenDed').val() ) ? 0.0 : parseFloat( $(this).find('input.porcenDed').val() );
		deduAux.idCriterio = valIsNullOrEmpty( $(this).find('select.criterioDed').val() ) ? 0 : parseInt( $(this).find('select.criterioDed').val() );
		deduAux.monto = valIsNullOrEmpty( $(this).find('input.montoDed').val() ) ? 0 : parseInt( $(this).find('input.montoDed').val().replace(/[$,]/g, '') );
		deduAux.unidad = valIsNullOrEmpty( $(this).find('select.unidadDed').val() ) ? 0 : parseInt( $(this).find('select.unidadDed').val() );
		
		p_deduciblesLibre.push(deduAux);
		
	});
	
}

function guardaDeducibles(){
	
	showLoader();
	$.post( ligasServicios.saveDeducibles, {
		p_deduciblesFijo: JSON.stringify(p_deduciblesFijo),
		p_deduciblesLibre: JSON.stringify(p_deduciblesLibre),
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version
	} ).done( function(data) {
		if( !valIsNullOrEmpty(data) ){
			var jsonResponse = JSON.parse(data);
			if( jsonResponse.code == 0 ){
				showMessageSuccess('.navbar', jsonResponse.msg, 0);
				
				var band = null;
                $('#tabPaso3').html("");
                $.each(jsonResponse.datosCaratula, function(k, valCaratula) {
                    if (!(valCaratula.contenedor == band)) {
                        band = valCaratula.contenedor;

                        $('#tabPaso3').append("<tr><th>" + band + "</td><td></td><td></td><td></th></tr>");
                    }
                    $('#tabPaso3').append("<tr><td>" + valCaratula.titulo + "</td><td class=\"number\">" + valCaratula.sa + "</td><td>" + valCaratula.deducible + "</td></tr>");
                });		
			}
		}else{
			showMessageError('.navbar', 'Error en servicios', 0);
		}
		$("#modalDeducibles").modal('hide');
		hideLoader();
	} );
}

function saveClausulas(){
	generaClausulas();
	guardaClausulas();
}

function generaClausulas(){
	clausulasArr = [];
	
	$.each($("#modalClausulas .rowClausulas .form-check"), function(index, value){
			var clausulaAux = new Object();
			
			clausulaAux.idClausula = $(this).find('input').attr('id');
			clausulaAux.aplica = $(this).find('input').is(':checked');
			clausulaAux.aplicaDiferido = ( $(this).find('input').attr('aplDife') == '1' ? true : false );
			clausulaAux.idDiferido = 0;
			/*clausulaAux.descripcion = $(this).find('label').text();*/
			/*clausulaAux.obligatoria = $(this).find('input').is(':disabled');*/
			
			clausulasArr.push(clausulaAux);
	});
	console.log( JSON.stringify(clausulasArr) );
}

function guardaClausulas(){
	showLoader();
	$.post( ligasServicios.saveClausulasA, {
		clausulasArr: JSON.stringify(clausulasArr),
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version
	} ).done( function(data) {
		if( !valIsNullOrEmpty(data) ){
			var jsonResponse = JSON.parse(data);
			if( jsonResponse.code == 0 ){
				showMessageSuccess('.navbar', jsonResponse.msg, 0);				
			}
		}else{
			showMessageError('.navbar', 'Error en servicios', 0);
		}
		$("#modalClausulas").modal('hide');
		hideLoader();
	} );
}

$(".moneda" ).on("focus", function(){
	var abc = $(this).text().replace(/[^0-9\.,]/g,'').replace(',', '');
	$(this).text(abc);
});

function showModalSlipSemi(){
	$('#modalSlipSemi').modal('show');
}

function showModalEspecificacion(){
	$('#modalEspecificacion').modal('show');
}

function showModalDeducibles() {
	
	$.post(ligasServicios.getDeducibles,{
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version,
		pantalla: infCotiJson.pantalla
	}).done(function(data) {
		
		var response = JSON.parse(data);
		
		$("#modalDeducibles").modal('show');
		
		if(response.code == 0) {
				$.each(response.deducibles, function(key, value) {
					
					switch(value.idDeducible) {
						case 6471: 	$("#mdlPorcenROT").val(value.porcentaje);
									$("#mdlCritROT").val(value.idCriterio);
									$("#mdlMontoROT").val(value.monto);
									$("#mdlUnidadROT").val(value.unidad);
									
									break;
						case 6472:	$("#mdlPorcenRobo").val(value.porcentaje);
									$("#mdlCritRobo").val(value.idCriterio);
									$("#mdlMontoRobo").val(value.monto);
									$("#mdlUnidadRobo").val(value.unidad);
									$(".rowRobo").removeClass('d-none');
									break;
						case 6473: 	$("#mdlPorcenASF").val(value.porcentaje);
									$("#mdlCritASF").val(value.idCriterio);
									$("#mdlMontoASF").val(value.monto);
									$("#mdlUnidadASF").val(value.unidad);
									$(".rowAveria").removeClass('d-none');
									break;
						case 6488:	$("#mdlPorcenHT").val(value.porcentaje);
									$("#mdlCritHT").val(value.idCriterio);
									$("#mdlMontoHT").val(value.monto);
									$("#mdlUnidadHT").val(value.unidad);
									$(".rowHT").removeClass('d-none');
									break;
						case 7652:	$("#mdlPorcenIT").val(value.porcentaje);
									$("#mdlCritIT").val(value.idCriterio);
									$("#mdlMontoIT").val(value.monto);
									$("#mdlUnidadIT").val(value.unidad);
									$(".rowIT").removeClass('d-none');
									break;
					}
				});
				
				/*
				$("#rowDeduciblesLibre .rowDeducible")
				*/
				
				if(response.deduciblesAdicionales.length > 0) {
				
					var deduciblesAdicAuxCont = $("#rowDeduciblesLibre .rowDeducible").length;
					
					while(deduciblesAdicAuxCont < response.deduciblesAdicionales.length) {
						addDeducibleJsp();
						deduciblesAdicAuxCont++;
					}
					
					$.each($("#rowDeduciblesLibre .rowDeducible"), function(key, value) {
						$(this).find('input.descripcionDed').val(response.deduciblesAdicionales[key].deducible);
						$(this).find('input.porcenDed').val(response.deduciblesAdicionales[key].porcentaje);
						$(this).find('input.criterioDed').val(response.deduciblesAdicionales[key].criterioAdic);
						$(this).find('input.montoDed').val(response.deduciblesAdicionales[key].monto);
						$(this).find('select.unidadDed').val(response.deduciblesAdicionales[key].unidad);
					});
				}
				
				$("#modalDeducibles select").materialSelect();
				$("#modalDeducibles .moneda").blur();
				$("#modalDeducibles select").change();
		}
		else {
			showMessageError('#modalDeducibles .modal-header', response.msg, 0);
		}
	});
}

$("#modalDeducibles").on('blur', '.moneda', function() {
	this.value = this.value.replace(/[^0-9\.]/g,'');
	daFormatoMoneda($(this));
});

$("#modalDeducibles").on('change', 'select.unidad', function() {
	
	var auxField = $(this).closest('.row').find('.moneda');
	
	if($(this).val() == 6475) {	
		$(auxField).val($(auxField).val().replace('$', ''));
	}
	else {
		$(auxField).val($(auxField).val().replace(/[^0-9\.]/g,''));
		daFormatoMoneda($(auxField));
	}
});

function downloadPlantillaSlip(){
	console.log('descargar plantilla');
	showLoader();
	
	guardaSiniestralidad();
	
	$.post( ligasServicios.generacionSlip, {
		/*infoCotiResponse: infoCotiResponse*/
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version,
		pantalla: infCotiJson.pantalla,
		folio: infCotiJson.folio,
		word: 1,
		ingles: 0
	} ).done( function(data) {
		if( !valIsNullOrEmpty(data) ){
			var respuestaJson = JSON.parse(data);
			if( respuestaJson.code == 0 ){
				showMessageSuccess('.navbar', respuestaJson.msg, 0);
				$('#paso3_enviar').attr('disabled', false);
				
				var buffer = new Uint8Array(respuestaJson.documento);
	            var blob = new Blob([buffer], { type: "application/pdf" });
	            $("#aPdf").attr("href", window.URL.createObjectURL(blob));
	            var link = document.getElementById("aPdf");
	            link.download = respuestaJson.nombre + respuestaJson.extension;
	            link.click();
	            
	            $.post( ligasServicios.generacionSlip, {
	        		/*infoCotiResponse: infoCotiResponse*/
	        		cotizacion: infCotiJson.cotizacion,
	        		version: infCotiJson.version,
	        		pantalla: infCotiJson.pantalla,
	        		folio: infCotiJson.folio,
	        		word: 1,
	        		ingles: 1
	        	} ).done( function(dataIng) {
	        		
	        		var respuestaJsonIng = JSON.parse(dataIng);
	        		
	    			if( respuestaJsonIng.code == 0 ) {
	        		
		        		var buffer = new Uint8Array(respuestaJsonIng.documento);
			            var blob = new Blob([buffer], { type: "application/pdf" });
			            $("#aPdf").attr("href", window.URL.createObjectURL(blob));
			            var link = document.getElementById("aPdf");
			            link.download = respuestaJsonIng.nombre + respuestaJsonIng.extension;
			            link.click();
	    			}
		            else {
						showMessageError('.navbar', respuestaJson.msg, 0);
					}
	        	});
	     
			}
			else{
				showMessageError('.navbar', respuestaJson.msg, 0);
			}
		}else{
			showMessageError('.navbar', 'Error en servicios', 0);
		}
		hideLoader();
	} );
}
function generarSlip(){
	
	if(infCotiJson.canalNegocio == 2525 || infCotiJson.canalNegocio == 2524){
		idCatalogoSlip = 6408;
		showModalSlipSemi();
	} 
	else {
		
		if(infCotiJson.poliza != "3" || (infCotiJson.poliza == "3" && (perfilSuscriptor == 1 || perfilJapones == 1))){
			idCatalogoSlip = 6409;
			showModalSlipSemi();
		}
		else {
			
			showLoader();
			
			guardaSiniestralidad();
			
			$.post(ligasServicios.generacionSlip ,
			{
		        cotizacion: infCotiJson.cotizacion,
		        version: infCotiJson.version,
				pantalla: infCotiJson.pantalla,
				folio: infCotiJson.folio,
				word: 0,
				ingles: 0
		    }).done(function(data) {
		
		        var respuestaJson = JSON.parse(data);
		        if (respuestaJson.code == 0) {
		        	
		        	
				
		            var buffer = new Uint8Array(respuestaJson.documento);
		            var blob = new Blob([buffer], { type: "application/pdf" });
		            $("#aPdf").attr("href", window.URL.createObjectURL(blob));
		            var link = document.getElementById("aPdf");
		            link.download = respuestaJson.nombre + ".pdf";
		            link.click();
		            validaBotonEmision(respuestaJson.estado);
		            $("#btnEnvCotiSusAgente").prop("disabled", false);
		            $("#btnEmitEndoso").prop("disabled", false);
		            
		            $.post(ligasServicios.generacionSlip ,
		        			{
		        		        cotizacion: infCotiJson.cotizacion,
		        		        version: infCotiJson.version,
		        				pantalla: infCotiJson.pantalla,
		        				folio: infCotiJson.folio,
		        				word: 0,
		        				ingles: 1
		        		    }).done(function(dataIng) {
		        		    	
		        		    	var respuestaJsonIng = JSON.parse(dataIng);
		        		        if (respuestaJsonIng.code == 0) {
		        		    	
			        		    	var buffer = new Uint8Array(respuestaJsonIng.documento);
			    		            var blob = new Blob([buffer], { type: "application/pdf" });
			    		            $("#aPdf").attr("href", window.URL.createObjectURL(blob));
			    		            var link = document.getElementById("aPdf");
			    		            link.download = respuestaJsonIng.nombre + ".pdf";
			    		            link.click();
			    		            validaBotonEmision(respuestaJson.estado);
			    		            $("#btnEnvCotiSusAgente").prop("disabled", false);
			    		            $("#btnEmitEndoso").prop("disabled", false);
			    		            
					
			        		    	hideLoader();
		        		        }
		        		        else {
		        		        	showMessageError(".navbar", "Mensaje: " + respuestaJsonIng.msg, 1);
		        		            hideLoader();
		        		        }
		        		    });
		        } else {
		            /*agregaAlertError("Mensaje: " + respuestaJson.msg);*/
		        	showMessageError(".navbar", "Mensaje: " + respuestaJson.msg, 1);
		            hideLoader();
		        }
		    });
		}
	}
	
}

function validaBotonEmision(estado) {
    /*if (estado == 340 || estado == 350 || estado == 351) {
        $("#paso3_next_form").attr("disabled", false);
    } else {*/
	if(perfilSuscriptor == '1') {
    	$.post(getPermisoEmisionURL, {})
		.done(function(dataE){
			var response = JSON.parse(dataE);
			
			if(response.code == 0) {
				
				$.post(validaAgenteURL, {
            		cotizacion: infCotiJson.cotizacion,
        	    	codigoAgente: ''
        	    }).done(function(data) {
        	    	
        	    	var response = JSON.parse(data);
        	    	
        	    	if(response.code != 0) {
        	    		if(response.code == 3) {
        	    			$("#modalBloqueoAgente").modal('show');
        	    		}
        	    		else {
        	    			showMessageError('.navbar', response.msg, 0);
        	    		}
        	    	}
        	    	else {
        	    		
				$("#paso3_next_form").attr("disabled", false);
        	    	}
        	    });
				
				
			}
			else {
				$("#paso3_next_form").attr("disabled", true);
			}
		});
	}
	else {
		if (estado == 340 || estado == 350 || estado == 351) {
			
			$.post(validaAgenteURL, {
        		cotizacion: infCotiJson.cotizacion,
    	    	codigoAgente: ''
    	    }).done(function(data) {
    	    	
    	    	var response = JSON.parse(data);
    	    	
    	    	if(response.code != 0) {
    	    		if(response.code == 3) {
    	    			$("#modalBloqueoAgente").modal('show');
    	    		}
    	    		else {
    	    			showMessageError('.navbar', response.msg, 0);
    	    		}
    	    	}
    	    	else {
    	    		
    	    		$("#paso3_next_form").attr("disabled", false);
    	    	}
    	    });
	    } else {
	    	$("#paso3_next_form").attr("disabled", true);
	    }
	}
    /*}*/
}

$("#archivoSlip").change(function(evt){
	var listMimetypeValid = ["application/pdf"
    ];
	
	var file = evt.target.files[0];
	var tipoPermitido = (listMimetypeValid.indexOf(file.type) >= 0);
	
	if(tipoPermitido){
		$("#infDocSuc").val(file.name);
	}else{
		$("#infDocSuc").val("");
		$(this).val("");
		showMessageError( '#modalSlipSemi .modal-header', "Archivo no valido", 0 );
	}
	
});

function guardarSlipSemi() {
	
	var dataDoc = new FormData();
	
	var url = new URL(window.location.href);
	var totArc = 0;
	var auxiliarDoc = '{';

	/*
    $.each($('#archivoSlip')[0].files, function(i, file) {
        dataDoc.append('file-' + i, file);
        var nomAux = file.name.split('.');

		if(nomAux.length > 2) {
			
			var nomAux2 = "";
			
			for(i = 0; i < (nomAux.length - 1); i++) {
				nomAux2 += nomAux[i] + ".";
			}
			
			nomAux2 = nomAux2.slice(0,-1);
			
			auxiliarDoc += '\"plantillaSlip\" : {';
	        auxiliarDoc += '\"nom\" : \"' + nomAux2 + '\",';
	        auxiliarDoc += '\"ext\" : \"' + nomAux[nomAux.length - 1] + '\"}';
		}
		else {
	        auxiliarDoc += '\"plantillaSlip\" : {';
	        auxiliarDoc += '\"nom\" : \"' + nomAux[0] + '\",';
	        auxiliarDoc += '\"ext\" : \"' + nomAux[1] + '\"}';
		}
    });
    auxiliarDoc += '}';
    */
    
    $.each($('.inFile'), function(i, f) {
		var file = f.files[0];
    /*$.each($('#modalArchivos')[0].files, function(i, file) {*/
		if (f.files.length > 0) {	
			dataDoc.append('file-' + i, file);
	        var nomAux = file.name.split('.');
	        var nomAux = file.name.split('.');

			if(nomAux.length > 2) {
				
				var nomAux2 = "";
				
				for(i = 0; i < (nomAux.length - 1); i++) {
					nomAux2 += nomAux[i] + ".";
				}
				
				nomAux2 = nomAux2.slice(0,-1);
				
				if (i == 0) {
					auxiliarDoc += '\"plantillaSlip-' + i + '\" : {';
				}
				else{
					auxiliarDoc += ', \"plantillaSlip-' + i + '\" : {';
				}
		        auxiliarDoc += '\"nom\" : \"' + nomAux2 + '\",';
		        if(f.id=='archivoSlip'){
		        	auxiliarDoc += '\"idioma\" : \"Esp\",';
		        }else{
		        	auxiliarDoc += '\"idioma\" : \"Ing\",';
		        }
		        auxiliarDoc += '\"ext\" : \"' + nomAux[nomAux.length - 1] + '\"}';
			}
			else {
				if (i == 0) {
					auxiliarDoc += '\"plantillaSlip-' + i + '\" : {';
				}
				else{
					auxiliarDoc += ', \"plantillaSlip-' + i + '\" : {';
				}
		        auxiliarDoc += '\"nom\" : \"' + nomAux[0] + '\",';
		        if(f.id=='archivoSlip'){
		        	auxiliarDoc += '\"idioma\" : \"Esp\",';
		        }else{
		        	auxiliarDoc += '\"idioma\" : \"Ing\",';
		        }
		        auxiliarDoc += '\"ext\" : \"' + nomAux[1] + '\"}';
			}
			totArc++;
		}
    });
    auxiliarDoc += '}';

	dataDoc.append('auxiliarDoc', auxiliarDoc);
	dataDoc.append('infoCot', JSON.stringify(infCotiJson));
	dataDoc.append('idCatalogoSlip', idCatalogoSlip);
	dataDoc.append('url', url.origin + url.pathname); 
    dataDoc.append('url2', url.origin);
    dataDoc.append('totArc', totArc);
	
	$.ajax( {
		url : ligasServicios.generacionSlipSemi, 
		data : dataDoc, 
		processData : false, 
		contentType : false, 
		type : 'POST',
		async: false,
		success : function(data) {
			console.log(data);
			
			var response = JSON.parse(data);
			
			if(response.code == 0) {
				$("#modalSlipSemi").modal('hide');
				
				fValidaAgente();
				$("#paso3_next").prop("disabled", false);
				
			}
			else{
				showMessageError( '#modalSlipSemi .modal-header', response.msg, 0);
			}
			
		}
	});
}

function fValidaAgente(){
	$.post(validaAgenteURL, {
		cotizacion: infCotiJson.cotizacion,
    	codigoAgente: ''
    }).done(function(data) {
    	
    	var response = JSON.parse(data);
    	
    	if(response.code != 0) {
    		if(response.code == 3) {
    			$("#modalBloqueoAgente").modal('show');
    		}
    		else {
    			showMessageError('.navbar', response.msg, 0);
    		}
    	}
    	else {
    		
    		$("#paso3_next_form").prop('disabled', false);
    	}
    });
}

$("#paso3_next_form").click(function(e){
	
	showLoader();
    $.post($('#txtJSGetEmisionData').val(), {
        cotizacion: infCotiJson.cotizacion,
        version: infCotiJson.version
    }).done(function(data) {
        sessionExtend();
        var response = jQuery.parseJSON(data);
        if (response.code == 0) {
        	/*
            varAuxiliares.tipoPersona = isFisica_Moral(response.datosCliente.tipoPer);
            preCargaDatos(response);
            */
            showLoader();
			actualizainfoCot();
			$.post( redirigeURL, {
				infoCot : JSON.stringify( infCotiJson ),
				paso : enlace.PASO4
				/*paso : seleccionaVentana()*/
				/*paso : enlace.PASO2*/
			} ).done( function(data) {
				var response = JSON.parse( data );
				if (response.code == 0) {
					$("#infoCotizacion").val(response.msg);
					$("#paso3-form").submit();
				} else {
					showMessageError( '.navbar', response.msg, 0 );
					hideLoader();
				}
			} );
            /*hideLoader();*/
        } else {
            showMessageError('.navbar', 'Error al consultar la información', 0);
            hideLoader();
        }
        /*
        if (valIsNullOrEmpty($('#txtBtnEmiteFactu').val())) {
            validaPrimaMax492(0);
        }
        */
    }).fail(function(e) {
        showMessageError('.navbar', 'Error al consultar la información (data)', 0);
        hideLoader();
    });
	
});

function actualizainfoCot(){
	switch (infCotiJson.modo) {
		case modo.NUEVA:
			infCotiJson.modo = modo.EDICION;
			break;
		case modo.COPIA:
			infCotiJson.modo = modo.EDICION;
			break;
		case modo.ALTA_ENDOSO:
			infCotiJson.modo = modo.EDITAR_ALTA_ENDOSO;
			break;
		case modo.BAJA_ENDOSO:
			infCotiJson.modo = modo.EDITAR_BAJA_ENDOSO;
			break;
		default:
			break;
	}
}

$("#checkDividendo").change(function() {
    if ($(this).is(":checked")) {
    	$( '#txtDividendo' ).removeClass('d-none');
    	if ($( '#txtDividendo' ).val()=='null')
    		$( '#txtDividendo' ).val('');
    } else {
    	$( '#txtDividendo' ).addClass('d-none');
    	$( '#txtDividendo' ).val('');
    }
});

$("#checkSiniestralidad").change(function() {
    if ($(this).is(":checked")) {
    	$( '.siniestraValida' ).removeClass('d-none');
    } else {
    	$( '#noAjustes' ).val('');
    	$( '#montoAjustes' ).val('');
    	$( '#porcentajeAjustes' ).val('');
    	$( '.siniestraValida' ).addClass('d-none');
    }
});

$('.valPorcen').on('keyup', function() {
    $(event.target).val(function(index, value) {
        var aux = value.replace(/\D/g, "")
        if (parseInt(aux) > 100) {
            showMessageError('.navbar', 'La comisión no pude superar el 100% ', 0);
            return '100';
        }
        return aux;
    });
});

$( "#divPrimaObj" ).on("keyup", ".auxPorcen",function() {
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
		 var res = aux.split(".");
		 if (parseInt(res[0]) > 100 && parseInt(res[1]) > 000001 ) {
			 return '100.000000';
		 }			 
		 if( res.length > 1 ){
			 if( res[0].length > 2 && res[1].length == 6){
				 return '00.000001'
			 }
		 }
		 */
	        
		 return aux;
	 });
}

$(".siniestraValida .moneda" ).on("blur", function(){
	this.value = this.value.replace(/[^0-9\.]/g,'');
	daFormatoMoneda($(this));
});

$(".siniestraValida .cNumnero" ).on("keyup", function(event){
	var aux = $(event.target).val().split('.');
	$(event.target).val(aux[0]);
	$(event.target).val(function (index, value ) {
		return value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
	});
});

$(".siniestraValida .moneda" ).on("keyup", function(event){
	var aux = $(event.target).val().split('.');
	$(event.target).val(aux[0]);
	 $(event.target).val(function (index, value ) {
	        return value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d)\.?)/g, ",");
	    });
});

$(".siniestraValida .moneda" ).on("focus", function(){
	/*this.value = this.value.replace(/[^0-9\.,]/g,''); */
	var abc = $(this).val().replace(/[^0-9\.,]/g,'');
	$(this).val(abc.split('.')[0]);
});

$("#bodyModalClausulas #checkALL").change(function() {
    var curCheck = $(this).is(":checked");
    $.each( $("#bodyModalClausulas .rowClausulas .checkClausula"), function( key, value ) {
    	if( !$(this).is(":disabled") && ( perfilJapones == '1' || perfilSuscriptor == '1' ) ){
    		$(this).prop("checked", curCheck);
    	}
	});
});

$("#bodyModalClausulas").on('change', '.checkClausula', function() {
	
	if($(this).attr('apldife') == 1) {
		if(this.checked) {
			
			if($(this).parent().parent().siblings('.divSelect').length > 0) {
				
				$(this).parent().parent().siblings('.divSelect').removeClass('d-none')
            }
		}
		else {
			
			$(this).parent().parent().siblings('.divSelect').addClass('d-none');
		}
	}
});

$("#btnClausulas").click(function(){
	$.post(clausulasURL,
		{
			cotizacion: infCotiJson.cotizacion,
			version: infCotiJson.version,
			pantalla: infCotiJson.pantalla
		})
		.done(function(data){
			responseClausulas = JSON.parse(data);
			$("#bodyModalClausulas .rowClausulas").empty();
			
			$.each(responseClausulas.clausulas, function(index, value){
				var auxAplica = value.aplica ? 'checked' : '';
				var auxObligatoria = value.obligatoria ? 'disabled' : '';
				var auxDisabledPerfil = ( perfilJapones == '1' || perfilSuscriptor == '1' ) ? '' : 'disabled';
				var auxDiferido = value.aplicaDiferido ? '1' : '0';
				
				if(auxDiferido == 1) {
					$("#bodyModalClausulas  .rowClausulas").append(`
							<div class="col-sm-12">
								<div class="col-sm-6">
									<div class="form-check mt-4">
									    <input type="checkbox" class="form-check-input checkClausula" id="`+value.idClausula+`" name="`+value.idClausula+`" `+auxAplica+` `+auxObligatoria+` aplDife="`+auxDiferido+`" `+auxDisabledPerfil+`>
									    <label class="form-check-label" for="`+value.idClausula+`">`+value.descripcion+`</label>
									</div>
								</div>
								<div class="col-sm-6 divSelect">
									<div class="md-form form-group">
									    <select id="selectClausula`+value.idClausula+`" class="mdb-select form-control-sel colorful-select dropdown-primary">
									    </select>
									</div> 
								</div>
							</div>
					`);
					
					$.each(listaDias.lista, function(key, valueList) {
					    $("#selectClausula"+value.idClausula).append(
					        "<option value=" + valueList.idCatalogoDetalle + ">"
					        + valueList.valor + "</option>"
					        );
					});
					
					$("#selectClausula"+value.idClausula).materialSelect();
				}
				else {
					$("#bodyModalClausulas  .rowClausulas").append(`
							<div class="col-sm-12">
								<div class="form-check mt-4">
								    <input type="checkbox" class="form-check-input checkClausula" id="`+value.idClausula+`" name="`+value.idClausula+`" `+auxAplica+` `+auxObligatoria+` aplDife="`+auxDiferido+`" `+auxDisabledPerfil+`>
								    <label class="form-check-label" for="`+value.idClausula+`">`+value.descripcion+`</label>
								</div>
							</div>
					`);
				}
			});
			
			$('#modalClausulas').modal('show');
		});
});

$("#envio_suscripcion").click(function (){
	$('#modalArchivos').modal('show');
});

$("#enviaArchivosSuscripcion").click(function() {
	enviaArchivosModal();
});


$("#btnCoaseguro").click(function() {
	
	guardaSiniestralidad();
	
	$.post(generaInfoCoaseguroURL,{
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version,
		folio: infCotiJson.folio,
		poliza: infCotiJson.poliza,
		pantalla: infCotiJson.pantalla,
		coaseguro: infCotiJson.tipoCoaseguro,
		modo: infCotiJson.modo
	}).done(function(data){
		console.log(data);
		
		window.location = "rea-coa?infoCotizacion=" + data;
	});
});

$("#btnReaseguro").click(function() {
	
	guardaSiniestralidad();
	
	$.post(generaInfoReaseguroURL,{
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version,
		folio: infCotiJson.folio,
		poliza: infCotiJson.poliza,
		pantalla: infCotiJson.pantalla,
		coaseguro: infCotiJson.tipoCoaseguro,
		canalNeg: infCotiJson.canalNegocio,
		modo: infCotiJson.modo
	}).done(function(data){
		console.log(data);
		
		window.location = "rea-coa?infoCotizacion=" + data;
	});
});

function enviaArchivosModal() {
	if( !valIsNullOrEmpty( $('#fileReporte').val() ) ) {
		showLoader();
		var url = new URL(window.location.href);
		var data = new FormData();
		var auxiliarDoc = '{';
		var docCompletos = true;
		var totArchivos = 0;
		
		$.each($('.inFile'), function(k, f) {
			var file = f.files[0];
			if (f.files.length > 0) {
				data.append("file-" + totArchivos, file);
				var nomAux = file.name.split('.');
				if (totArchivos == 0) {
					auxiliarDoc += '\"file-' + totArchivos + '\" : {';
				} else {
					auxiliarDoc += ', \"file-' + totArchivos + '\" : {';
				}
				auxiliarDoc += '\"nom\" : \"' + $(f).attr("prefijo") + nomAux[0] + '\",';
				auxiliarDoc += '\"iddoc\" : \"' + $(f).attr("iddoc") + '\",';
				auxiliarDoc += '\"idcatdet\" : \"' + $(f).attr("idcatalogodetalle") + '\",';
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
		auxiliarDoc += '}';
		
		console.log("totArchivos : " + totArchivos);
		
		data.append('auxiliarDoc', auxiliarDoc);
		data.append('comentarios', $('#mdlFileComentarios').val());
		data.append('folio', infCotiJson.folio);
		data.append('cotizacion', infCotiJson.cotizacion);
		data.append('version', infCotiJson.version);
		data.append('modo', infCotiJson.modo);
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
				adjuntaArchivos(0);
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

async function adjuntaArchivos(isRevire) {

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

    data.append('isRevire', isRevire);
    data.append('auxiliarDoc', auxiliarDoc);
    data.append('isRevire', auxiliarDoc);
    data.append('comentarios', $('#mdlFileComentarios').val());
    data.append('infoCot', JSON.stringify(infCotiJson));
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

function guardaSiniestralidad() {
	$.post(guardaSiniestralidadURL, {
		cotizacion: infCotiJson.cotizacion,
		version: infCotiJson.version,
		p_dividendo: $("#txtDividendo").val(),
		p_sMonto: $("#montoAjustes").val().replace(/[$,]/g, ''),
		p_sNumero: $("#noAjustes").val().replace(/[$,]/g, ''),
		p_sPorcentaje: $("#porcentajeAjustes").val()
	}).done(function(data) {
		
		var response = JSON.parse(data);
		
		if(response.code == 0) {
			
		}
		else {
			showMessageError('.navbar', response.msg, 0);
		}
	});
}


$('#btnFacturaSuscrip').click(function() {
    var idFac = ($('#chkfactauto').is(':checked')) ? 1 : 0;
    $('#txtBtnEmiteFactu').val(idFac);
    showLoader();

    $.post(getEmisionArt492Url, {
        cotizacion: infCotiJson.cotizacion,
        version: infCotiJson.version,
        factura: idFac,
        cotizador: 'LiabilityQuotation'
    }).done(function(data) {
        var response = jQuery.parseJSON(data);
        console.log('aquiiii');
        console.log(response);
        if (response.code == 0) {
            llenaInfoModalPoliza(response);
            $('#modalGenerarPoliza').modal({
                show: true
            });
        } else if (response.code == 4) {
            llenaInfoModalPoliza(response);
            $('#modalGenerarPoliza').modal({
                show: true
            });
            showMessageError('#modalGenerarPoliza', (response.msg), 0);
        } else {
            showMessageError('.navbar', (response.msg), 0);
        }
    }).always(function() {
        hideLoader();
    });
});


function llenaInfoModalPoliza(json) {
    $('.listaCorreos li').remove();
    $('#txtModalPolizaNumeroPoliza').text(validaKeyJson(json, 'numeroPoliza'));
    $('#txtModalPolizaCertificado').text(validaKeyJson(json, 'certificado'));
    $('#txtModalPolizaAsegurado').text(validaKeyJson(json, 'asegurado'));
    $('#txtModalPolizaAgente').val(validaKeyJson(json, 'agente'));

    $('#txtModalPolizaVigenciaDe').text(stringToDate(validaKeyJson(json, 'vigencia.inicio')));
    $('#txtModalPolizaVigenciaAl').text(stringToDate(validaKeyJson(json, 'vigencia.fin')));
    $('#divDescargarArchivos').html();

    $('#txtModalPolizaTotalUbicaciones').text(validaKeyJson(json, 'totalUbicaciones'));
    $('#txtModalPolizaMoneda').text(validaKeyJson(json, 'moneda'));
    $('#txtModalPolizaMoneda').text(validaKeyJson(json, 'moneda'));
    $('#txtModalPolizaFormaPago').text(validaKeyJson(json, 'formaPago'));
    $('#txtModalPolizaPrimaNeta').text(validaKeyJson(json, 'primaNeta'));
    $('#txtModalPolizaRecargoPago').text(validaKeyJson(json, 'recargo'));
    $('#txtModalPolizaGastosExpedicion').text(validaKeyJson(json, 'gastos'));
    $('#txtModalPolizaIva').text(validaKeyJson(json, 'iva'));
    $('#txtModalPolizaTotal').text(validaKeyJson(json, 'total'));
    $('#tablaArchivosPoliza tbody').empty();
    if (!valIsNullOrEmpty($('#txtEmailUser').val())) {
        $('.modal .listaCorreos')
            .append(
                $('<li email="' +
                	Base64.decode( $('#txtEmailUser').val() ) +
                    '" ><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                    Base64.decode( $('#txtEmailUser').val() ) + '</li>'));
    }

    if (valIsNullOrEmpty(validaKeyJson(json, 'archivos'))) {
        $('.selectCheckImput').prop('checked', false);
        $('.selectCheckImput').prop("disabled", true);
        $('#btnDescargarArchivos').prop("disabled", true);
        $('#polizaBtnEnviar').prop("disabled", true);
    } else {
        $('.selectCheckImput').prop('checked', true);
        $('.selectCheckImput').prop("disabled", false);
        $('#btnDescargarArchivos').prop("disabled", false);
        validaBtnEnviar();
        $.each(json.archivos, function(i, a) {
            var chekbox = '<div class="form-check"> ' + '<input class="form-check-inpu chekArchivos" name="' + a.nombre +
                "-" + a.extension + '" idCarpeta="' + a.idCarpeta + '" idDocumento="' + a.idDocumento +
                '" idCatalogoDetalle="' + a.idCatalogoDetalle + '" type="checkbox" id="' + a.nombre + "-" +
                a.extension + '" checked>' + '<label for="' + a.nombre + "-" + a.extension + '"></label>' +
                '</div>';

            $('#tablaArchivosPoliza tbody').append(
                $('<tr> <td> ' + chekbox + ' </td> <td>  ' + a.nombre + "." + a.extension + ' </td> <td >  ' +
                    a.tipo + ' </td> </tr>'));

            $('#divDescargarArchivos').append($('<a id="' + a.nombre + a.extension + '" />'));

        });
    }
}

function validaKeyJson(json, cadena) {
    var infoJson = '';
    var res = cadena.split(".");
    var ant = null;
    json['name']
    $.each(res, function(key, val) {
        if (key == 0) {
            infoJson = (val in json) ? eval('json.' + val) : "";
            ant = eval('json.' + val);
        } else {
            if (valIsNullOrEmpty(ant)) {
                infoJson = "";
            } else {
                infoJson = (val in ant) ? eval('ant.' + val) : "";
                ant = eval('ant.' + val);
            }
        }
    });
    return infoJson;
}

function validaBtnEnviar() {
    if ($('.listaCorreos li').length > 0) {
        $('#polizaBtnEnviar').prop("disabled", false);
        $('.msjActivarBtnEnviar').prop('hidden', true);
    } else {
        $('#polizaBtnEnviar').prop("disabled", true);
        $('.msjActivarBtnEnviar').prop('hidden', false);
    }
}

$('#polizaBtnEnviar').click(function(e) {
    showLoader();
    var emailsList = $('#listaCorreos li');
    var emailsTot = "";
    $.each(emailsList, function(i, emlis) {
        if (i > 0) {
            emailsTot += ",";
        }
        emailsTot += $(emlis).attr('email');
    });
    recuperaDocumentosEmision(emailsTot);
});

function recuperaDocumentosEmision(emails) {
    $.post($('#txtJSGetDocsEmision').val(), {
        infoDocs: jsonDocumentosEmision(),
        listaEmails: emails,
        cliente: $('#txtModalPolizaAsegurado').text(),
        poliza: $('#txtModalPolizaNumeroPoliza').text(),
        totUbica: $('#txtModalPolizaTotalUbicaciones').text(),
        moneda: $('#txtModalPolizaMoneda').text(),
        certificado: $('#txtModalPolizaCertificado').text(),
        vigencia: $('#txtModalPolizaVigenciaDe').text() + ' al ' + $('#txtModalPolizaVigenciaAl').text(),
        formaPago: $('#txtModalPolizaFormaPago').text(),
        primaNeta: '$' + $('#txtModalPolizaPrimaNeta').text(),
        recargo: '$' + $('#txtModalPolizaRecargoPago').text(),
        gasto: '$' + $('#txtModalPolizaGastosExpedicion').text(),
        iva: '$' + $('#txtModalPolizaIva').text(),
        prima: '$' + $('#txtModalPolizaTotal').text(),
        folio: infCotiJson.folio,
        agente: $('#txtModalPolizaAgente').val()
    }).done(function(data) {
        sessionExtend();
        var respuestaJson = JSON.parse(data);
        if (respuestaJson.code >= 0) {
            if (emails == null) {
                $.each(respuestaJson.archivos, function(i, archivo) {
                	/*
                    fileAux = 'data:application/octet-stream;base64,' + archivo.documento
                    var dlnk = document.getElementById(archivo.nombre + archivo.extension);
                    dlnk.href = fileAux;
                    dlnk.download = archivo.nombre + '.' + archivo.extension;
                    dlnk.click();
                    */
                	if(detectIEEdge()){
    					fileAux = 'data:application/octet-stream;base64,'+archivo.documento
    					var dlnk = document.getElementById('dwnldLnk');
    					dlnk.href = fileAux;
    					dlnk.download = archivo.nombre+'.'+archivo.extension;
    					location.href=document.getElementById("dwnldLnk").href;
    					/*dlnk.click();*/
    				}else{
    					/*
    					 * downloadDocument('archivo base 64' , 'nombre.extension' );
    					 */
    					downloadDocument(archivo.documento, archivo.nombre+'.'+archivo.extension);
    				}
                });
            } else {
                showMessageSuccess('#modalGenerarPoliza', "Correo(s) enviado(s)", 0);
            }
        } else {
            showMessageError('#modalGenerarPoliza', respuestaJson.msg, 0);
        }
    }).fail(function() {
        showMessageError('#modalGenerarPoliza', "Error al consultar la informacion", 0);
    }).always(function() {
        hideLoader();
    });
}

$('#btnDescargarArchivos').click(function(e) {
    showLoader();
    recuperaDocumentosEmision(null);
});

$('#btnCederComision').click(function(e) {
    try {
        showLoader();
        if (valIsNullOrEmpty($('#txtArt41').val())) {
            showMessageError('.navbar', 'Sin comisión ', 0);
        } else {
            $.post( ligasServicios.secionComisionUrl, {
                seccomi: $('#txtArt41').val(),
                /*tipoCoti : infCotiJson.tipoCotizacion.toString(),*/
                cotizacion: infCotiJson.cotizacion,
                version: infCotiJson.version
            }).done(function(data) {
                sessionExtend();
                var respuestaJson = JSON.parse(data);
                if (respuestaJson.code == 0) {
                    showMessageSuccess('.navbar', 'Información actualizada correctamente', 0);
                } else {
                    showMessageError('.navbar', respuestaJson.msg, 0);
                }
            }).fail(function() {
                showMessageError('.navbar', 'Error al consultar la información', 0);
                hideLoader();
            });
        }
        hideLoader();
    } catch (err) {

        hideLoader();
        showMessageError('.navbar', 'Error al consultar la información', 0);
    }
});

function jsonDocumentosEmision() {
    var cheks = $('#modalGenerarPoliza .bodyArchivos input[type=checkbox]');
    var listaDocumentos = '';
    if ($('.selectCheckImput').is(':checked')) {
        $.each(cheks, function(i, chek) {
            var ids = $(chek).attr('id');
            if (i > 0) {
                listaDocumentos += ",";
            }
            listaDocumentos += '{"idCarpeta" : ' + $(chek).attr('idCarpeta') + ', "idDocumento" : ' +
                $(chek).attr('idDocumento') + ', "idCatalogoDetalle" : ' +
                $(chek).attr('idCatalogoDetalle') + ', "documento" : "", "nombre" : "", "extension" : "" }';
        });
    } else {
        $.each(cheks, function(i, chek) {
            if ($(chek).is(':checked')) {
                var ids = $(chek).attr('id');
                if (!valIsNullOrEmpty(listaDocumentos)) {
                    listaDocumentos += ",";
                }
                listaDocumentos += '{"idCarpeta" : ' + $(chek).attr('idCarpeta') + ', "idDocumento" : ' +
                    $(chek).attr('idDocumento') + ', "idCatalogoDetalle" : ' +
                    $(chek).attr('idCatalogoDetalle') +
                    ', "documento" : "", "nombre" : "", "extension" : "" }';
            }
        });
    }
    return listaDocumentos;
}

$("#modalPolizaEnviarCorreo").keyup(function(e) {
    if (valIsNullOrEmpty($(this).val())) {
        $('#btnAgregaCorreoPoliza').prop("disabled", true);
    } else {
        $('#btnAgregaCorreoPoliza').prop("disabled", false);
        eliminaErrorEmailEmision();
    }
    var code = (e.keyCode ? e.keyCode : e.which);
    if (code == 13) {
        $('#btnAgregaCorreoPoliza').trigger('click');
    }
});

$('#modalGenerarPoliza').on('hidden.bs.modal', function() {
    window.scrollTo(0, 0);
    goToHome();
    /*goToPage(seleccionaVentana());*/
    /*var url = new URL(window.location.href);
    window.location.href = url.origin + url.pathname;*/
});

function eliminaErrorEmailEmision() {
    $("#modalPolizaEnviarCorreo").removeClass('invalid');
    $("#modalPolizaEnviarCorreo").siblings('.alert-danger').remove();
}

function detectIEEdge() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        console.log(parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10));
        return true;
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        // IE 11 => return version number
        var rv = ua.indexOf('rv:');
        console.log(parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10));
        return true;
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
        // Edge => return version number
        console.log(parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10));
        return true;
    }

    // other browser
    return false;
}

$('#btnAgregaCorreoPoliza').click(function(e) {
    var correo = $('#modalPolizaEnviarCorreo').val();
    var error = chekEmail($('#modalPolizaEnviarCorreo'));
    if ((!error) && (!valIsNullOrEmpty(correo))) {
        $('.modal .listaCorreos').append(
            $('<li email="' + correo +
                '"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                correo + '</li>'));
        $('#modalPolizaEnviarCorreo').val('');
        $('#listaCorreos').scrollTo('li:last');
        $('#btnAgregaCorreoPoliza').prop("disabled", true);
    }
    validaBtnEnviar();
    if (!almenosUnArchivoSeleccionado()) {
        $('#polizaBtnEnviar').prop("disabled", true);
    }
});

function downloadDocument(strBase64, filename) {
    var url = "data:application/octet-stream;base64," + strBase64;
    var documento = null;
    /*.then(res => res.blob())*/
    fetch(url)
        .then(function(res) { return res.blob() })
        .then(function(blob) {
            downloadBlob(blob, filename);
        });
}

function chekEmail(campos) {
    var errores = false;
    $.each(campos, function(index, value) {
        if (!valIsNullOrEmpty($(value).val())) {
            if (!validateEmail($(value).val())) {
                errores = true;
                $(value).addClass('invalid');
                $(value).parent().append(
                    "<div class=\"alert alert-danger\" role=\"alert\"> <span class=\"glyphicon glyphicon-ban-circle\"></span>" +
                    " " + $('#txtFormatoEmail').val() + "</div>");
            }
        }
    });
    return errores;
}

function downloadBlob(blob, filename) {
    if (window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveBlob(blob, filename);
    } else {
        var elem = window.document.createElement('a');
        elem.href = window.URL.createObjectURL(blob);
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
    }
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function almenosUnArchivoSeleccionado() {
    var seleccionado = false;
    $.each($('.modal .bodyArchivos .chekArchivos'), function(i, chek) {
        if ($(chek).is(':checked')) {
            seleccionado = true;
            return false;
        }
    });
    return seleccionado;
}

$('.modal .listaCorreos').on('click', '.close', function(e) {
    $(this).parent().remove();
    validaBtnEnviar();
});

function setBase64(){
	Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};
}

function validaCampoPorcentaje(){
	$(event.target).val(function(index, value) {
		
		var expReg1 = /^\d{1,3}(\.)?$/;
		var expReg2 = /^\d{1,3}(\.\d{1,2})?$/;
		var validate = expReg1.test(value) || expReg2.test(value);
		
		if(!validate){
			value = value.slice(0, -1);
		}
		
		if(valIsNullOrEmpty(value)){
			return 0;
		}
		else {
			return value;
		}
	});
}

$('#modalArchivos').on('click' , '.cleanFile', function(){
    $(this).closest(".row").find('input').val('');
});