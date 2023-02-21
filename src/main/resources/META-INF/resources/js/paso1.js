$( document ).ready(function() {
	/*$('#dc_movimientos option[value=207]').prop('selected', true);*/

	$( "#ce_nombre" ).autocomplete( {
		minLength : 3,
		source : function(request, response) {
			$.getJSON( ligasServicios.listaPersonas, {
				term : request.term,
				tipo : 1,
				pantalla : "moduloEmpresarial"
			}, function(data, status, xhr) {
				sessionExtend();
				if (data.codigo == '0') {
					showMessageError( '.navbar', msj.es.errorInformacion, 0 );
					console.error("autocomplete nombre");
					response( null );
				} else {
					response( data );
				}
			} );
		},
		focus : function(event, ui) {
			$( "#ce_nombre" ).val( ui.item.nombrepersona );
			return false;
		},
		select : function(event, ui) {
			$( "#ce_nombre" ).val( ui.item.nombre + " " + ui.item.appPaterno + " " + ui.item.appMaterno );
			$( "#ce_rfc" ).val( ui.item.rfc );
			$( "#ce_codigo" ).val( ui.item.codigo );
			$( "#ce_idPersona" ).val( ui.item.idPersona );
			$( "#tipoPer" ).val( ui.item.tipoPer );
			$( "#idDenominacion" ).val( ui.item.idDenominacion );
			activaCampos("#ce_rfc");
			activaCampos("#ce_codigo");
			auxP1.infoClientExistenttEncontrado = ui.item;
			seleccionaTipoPer();
			return false;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			showMessageError( '.navbar', msj.es.errorInformacion, 0 );
			console.error("autocomplete nombre");
		}
	} ).autocomplete( "instance" )._renderItem = function(ul, item) {
		if (item.idDenominacion == 0) {
			return $( "<li>" ).append(
					"<div>" + item.nombre + " " + item.appPaterno + " " + item.appMaterno + "</div>" )
					.appendTo( ul );
		} else {
			return $( "<li>" ).append(
					"<div>" + item.nombre + " " + item.appPaterno + " " + item.appMaterno + "</div>" )
					.appendTo( ul );
		}
	};

	$( "#ce_rfc" ).autocomplete( {
		minLength : 3,
		source : function(request, response) {
			$.getJSON( ligasServicios.listaPersonas, {
				term : request.term,
				tipo : 3,
				pantalla : "moduloEmpresarial"
			}, function(data, status, xhr) {
				sessionExtend();
				if (data.codigo == '0') {
					showMessageError( '.navbar', msj.es.errorInformacion, 0 );
					console.error("autocomplete rfc");
					response( null );
				} else {
					response( data );
				}
			} );
		},
		focus : function(event, ui) {
			$( "#ce_rfc" ).val( ui.item.nombrepersona );
			return false;
		},
		select : function(event, ui) {
			$( "#ce_nombre" ).val( ui.item.nombre + " " + ui.item.appPaterno + " " + ui.item.appMaterno );
			$( "#ce_rfc" ).val( ui.item.rfc );
			$( "#ce_codigo" ).val( ui.item.codigo );
			$( "#ce_idPersona" ).val( ui.item.idPersona );
			$( "#tipoPer" ).val( ui.item.tipoPer );
			$( "#idDenominacion" ).val( ui.item.idDenominacion );
			activaCampos("#ce_nombre");
			activaCampos("#ce_codigo");
			auxP1.infoClientExistenttEncontrado = ui.item;
			seleccionaTipoPer();
			return false;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			showMessageError( '.navbar', msj.es.errorInformacion, 0 );
			console.error("autocomplete rfc");
		}
	} ).autocomplete( "instance" )._renderItem = function(ul, item) {
		if (item.idDenominacion == 0) {
			return $( "<li>" ).append(
					"<div>" + item.rfc + " - " + item.nombre + " " + item.appPaterno + " " + item.appMaterno
							+ "</div>" ).appendTo( ul );
		} else {
			return $( "<li>" ).append(
					"<div>" + item.rfc + " - " + item.nombre + " " + item.appPaterno + " " + item.appMaterno
							+ "</div>" ).appendTo( ul );
		}
	};
	
	/*validaVigenciaCheck();*/
	setInfoCliente();
	aplicaReglas();
	fValidaAgente();
	hideLoader();
	
	/*$('#paso1_next').removeClass("d-none");*/
});

function validaVigenciaCheck(){
	if ( valIsNullOrEmpty( chkRdodtsContr( $('.divRdoVigencia .form-check-input') )  ) ) {
		$('#dc_declaracion').click();
	}
}

/*
function aplicaReglas() {
	seleccionaModo();
}
*/


function seleccionaModo() {
	switch (infCotizacion.modo) {
		case modo.NUEVA :
			console.log('selmodo1');
			ocultaCampos( ".divFolio" );
			$('.divRdoVigencia .form-check-input[value=1]').attr('checked', true);
			$("#dc_movimientos").prop("disabled", true);
			break;
		case modo.EDICION :
			if(datosCliente != ''){
				auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
				bloqueaCampoEdicion();
			}else{
				showMessageError('.navbar', msj.es.errorInformacion, 0);
				console.error(" Modo Edicion");
			} 
			break;
		case modo.COPIA :
			if(datosCliente == ''){
				showMessageError('.navbar', msj.es.errorInformacion, 0);
				console.error(" Modo copia");
			}else{
				auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
				$("#dc_subgiro").prop("disabled", false);
			}
			break;
		case modo.ALTA_ENDOSO :
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			$('#dc_dateDesde').off("change");
			bloqueaCampoAltaEndoso();
			
			break;
		case modo.EDITAR_ALTA_ENDOSO :
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			$('#dc_dateDesde').off("change");
			bloqueaCampoAltaEndoso();
			break;
		case modo.CONSULTA :
		case modo.CONSULTA_VOBO_REASEGURO :
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			bloqueaCamposConsulta();
			break;
		case modo.CONSULTAR_REVISION:
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			bloqueaCamposConsulta();
			break;
		case modo.BAJA_ENDOSO :
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			$('#dc_dateDesde').off("change");
			bloqueaCampoBajaEndoso();
			break;
		case modo.EDITAR_BAJA_ENDOSO :
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			$('#dc_dateDesde').off("change");
			bloqueaCampoBajaEndoso();
			break;
		case modo.FACTURA_492 :
			showLoader();
			ocultaCampos( "#paso1_next" );
			auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
			bloqueaCamposConsulta();
			$("#form_paso3").submit();
			break;
		case modo.EDICION_JAPONES:
			if(datosCliente != ''){
				auxP1.infoClientExistenttEncontrado = JSON.parse(datosCliente);
				bloqueaCamposEdicionJapones();
				bloqueaCampoEdicion();
			}
			else{
				showMessageError('.navbar', msj.es.errorInformacion, 0);
				console.error(" Modo Edicion");
			}
			
			break;
		case modo.COASEGURO:
		case modo.COASEGURO_CONSULTA:
		case modo.REASEGURO:
		case modo.REASEGURO_CONSULTA:
			showLoader();
			$("#formPaso3 #folioCoti").val(infCotizacion.folio);
			$("#formPaso3 #versionCoti").val(infCotizacion.version);
			$("#formPaso3 #cotizacion").val(infCotizacion.cotizacion);
			
			$("#action_next").click();
			break;
		case modo.DECLARACION_ENDOSO:
			$("#dc_movimientos").val(220);
			$("#dc_movimientos").change();
			$("#guardaPaso1Endoso").removeClass('d-none');
			$("#paso1_next").addClass('d-none');
			break;
		default:
			showMessageError('.navbar', msj.es.errorInformacion, 0);
			console.error(" Modo default");
			break;
	}
}


/*
$('#contPaso1').submit( function ( e ){
	removeClassInvalid();
	showLoader()
	
	var completos = validaRequeridos();
	var completos = true;
	if(completos){
		llenaDatos();
		guardaPaso1();
	}else{
		e.preventDefault();
		hideLoader();
		showMessageError('.navbar', msj.es.faltaInfo, 0);
		console.error("paso 1 next");
		return completos;
	}
} );
*/

function llenaDatos(){
	if($("#radio_ce").is(":checked")){
		llenaClienteExistente();
	}else{
		llenaClienteNuevo();
	}
	llenaDatsGene();
}

function llenaClienteExistente(){
	DatosGenerales.idPersona = auxP1.infoClientExistenttEncontrado.idPersona;
	DatosGenerales.tipoPer = auxP1.infoClientExistenttEncontrado.tipoPer;
	DatosGenerales.rfc = auxP1.infoClientExistenttEncontrado.rfc;
	DatosGenerales.nombre = auxP1.infoClientExistenttEncontrado.nombre;
	DatosGenerales.appPaterno = auxP1.infoClientExistenttEncontrado.appPaterno;
	DatosGenerales.appMaterno = auxP1.infoClientExistenttEncontrado.appMaterno;
	DatosGenerales.idDenominacion = auxP1.infoClientExistenttEncontrado.idDenominacion;
	DatosGenerales.codigo = auxP1.infoClientExistenttEncontrado.codigo;
}

function llenaClienteNuevo(){
	DatosGenerales.idPersona = 0;
	DatosGenerales.tipoPer = parseInt($(".tipo_persona .form-check-input:checked").val(), 10);
	DatosGenerales.rfc = $("#cn_rfc").val();
	llenaNombreCN();
	DatosGenerales.codigo = "";
}

function llenaDatsGene(){
	DatosGenerales.tipomov = parseInt($("#dc_movimientos").val(), 10);
	llenaTpoVigencia();
	DatosGenerales.moneda = parseInt($("#dc_moneda").val(), 10);
	
	DatosGenerales.fecinicio = $("#dc_dateDesde").val();
	DatosGenerales.fecfin = $("#dc_dateHasta").val();
	
	DatosGenerales.agente = parseInt($("#dc_agentes").val(), 10);
	
	DatosGenerales.canalN = parseInt($("#dc_canalNegocio").val(), 10);
	$('#canalNegocioInf').val( parseInt($("#dc_canalNegocio").val(), 10) );
	
	DatosGenerales.formapago = parseInt($("#dc_formpago").val(), 10);
	DatosGenerales.giro = $("#dc_giro").val();
	DatosGenerales.modo = infCotizacion.modo;
	/*DatosGenerales.tipoCot = infCotizacion.tipoCotizacion;*/
	DatosGenerales.tipoCot = 'TRANSPORTES';
	DatosGenerales.folio = infCotizacion.folio;
	DatosGenerales.cotizacion = infCotizacion.cotizacion;
	DatosGenerales.version = infCotizacion.version;
	DatosGenerales.pantalla = 'TRANSPORTES';
	/*DatosGenerales.pantalla = infCotizacion.pantalla;*/
	/*DatosGenerales.subEstado = infsubEstado;*/	
	DatosGenerales.tipoCoaseguro = parseInt($("#dc_coaseguro").val(), 10);
	DatosGenerales.sector = parseInt($("#dc_sector").val(), 10);
	
	DatosGenerales.vigencia = chkRdodtsContr( $('.divRdoVigencia .form-check-input') );
}

function llenaNombreCN(){
	if($("#cn_personamoral").is(":checked")){
		DatosGenerales.nombre = $("#cn_nombrecontratante").val();
		DatosGenerales.appPaterno = "";
		DatosGenerales.appMaterno = "";
		DatosGenerales.idDenominacion = parseInt($("#cn_denominacion").val(), 10);
		DatosGenerales.extranjero = 0;
	}else{
		DatosGenerales.nombre = $("#cn_fisnombre").val();
		DatosGenerales.appPaterno = $("#cn_fispaterno").val();
		DatosGenerales.appMaterno = $("#cn_fismaterno").val();
		DatosGenerales.idDenominacion = 0;
		DatosGenerales.extranjero = $(".cn_rdEx .switch:visible #chktoggle").is(":checked") ? 1: 0;
	}
}

/**
 * @description tipo 0 = Cliente Existente, tipo 1 = Cliente Nuevo
 */
$( '.divRdoTpClient .form-check-input' ).click( function(e) {
	if ($( this ).val() == "1") {
		muestraCampos( ".data_ctenvo" );
		ocultaCampos( ".data_cteext" );
		$(".data_cteext input:text").val("");
	} else {
		muestraCampos( ".data_cteext" );
		ocultaCampos( ".data_ctenvo" );
	}
} );

/**
 * @description tipo 2 = persona moral, tipo 1 = persona fisica
 */
$( '.tipo_persona .form-check-input' ).click( function() {
	if ($( this ).val() == "1") {
		muestraCampos( ".tip_fisica" );
		ocultaCampos( ".tip_moral" );
		fLlenaNombreFisica();
		actualisaFisica();
	} else {
		muestraCampos( ".tip_moral" );
		ocultaCampos( ".tip_fisica" );
		fLlenaNombreMoral();
		actualizaMoral();
	}
} );


$( '.anualCotizacion' ).click( function(e) {
	$('.paso1Fecha').datepicker('destroy');
	cambiaFecha();
	if ($( this ).val() == "3") {
		$('#dc_formpago option[value=123]').prop('selected', true);
		selectDestroy($("#dc_formpago"), true);
		$( '.datepicker .paso1Fecha' ).pickadate( {
			format : 'yyyy-mm-dd', 
			formatSubmit : 'yyyy-mm-dd',
			min : 0,
			max : 90
		} );
		$('#dc_dateHasta').attr('disabled',false);
		
	} else {
		$('#dc_formpago option[value=-1]').prop('selected', true);
		selectDestroy($("#dc_formpago"), false);
		$( '.datepicker .paso1Fecha' ).pickadate( {
			format : 'yyyy-mm-dd', 
			formatSubmit : 'yyyy-mm-dd',
			min : 0,
			max : 365
		} );
		$('#dc_dateHasta').attr('disabled',true);
		
	}
} );

$('#dc_agentes').change(function() {
	if ($( this ).val() == "0000000000001"){
		$('#dc_fcomicion').hide();
		$('#dc_fcomicion').val() = 0;
	}else{
		$('#dc_fcomicion').show();
	}
	
	showLoader();
	
    $("#dc_canalNegocio option:not(:first)").remove();
    
    if ($(this).val() === '-1') {
    	 selectDestroy($("#dc_canalNegocio"), true);
         hideLoader();
    }else{
		$.post(ligasServicios.canalNegocio, {
	    	codigoAgente: $("#dc_agentes option:selected").text(),
	        pantalla : infCotizacion.pantalla
	    }).done(function(data) {
	        sessionExtend();
	        if (valIsNullOrEmpty(data)) {
	            showMessageError('.navbar', msj.es.errorInformacion, 0);
	            console.error("giro change");
	            selectDestroy($("#dc_canalNegocio"), true);
	        } else {
	            var response = jQuery.parseJSON(data);
	            if(response.totalRow > 0){
	            	$.each(response.lista, function(key, registro) {
						let seleccionado = registro.otro == "1" ? 'selected' : '';
	                    $("#dc_canalNegocio").append(
	                        "<option value=\"" + registro.idCatalogoDetalle + "\" suscripcion=\"" +
	                        registro.otro + "\" codigo=\"" + registro.codigo + "\" " + seleccionado + " >" + registro.valor + "</option>");
	                });
	                selectDestroy($("#dc_canalNegocio"), false);
	            }else{
	            	$("#dc_canalNegocio option:not(:first)").remove();
	            	selectDestroy($("#dc_canalNegocio"), true);
	            	showMessageError('.navbar', msj.es.errorInformacion, 0);
	            	console.error("Agente Change");
	            }
	        }
	        hideLoader();
	    }).fail(function() {
	    	$("#dc_canalNegocio option:not(:first)").remove();
	    	selectDestroy($("#dc_canalNegocio"), true);
	    	showMessageError('.navbar', msj.es.errorInformacion, 0);
	    	console.error("Agente Change");
	    	 hideLoader();
	    });
		
		fValidaAgente();
	
	}
} );

function fValidaAgente(){
	$.post(validaAgenteURL, {
		cotizacion: 0,
    	codigoAgente: $("#dc_agentes option:selected").text()
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
    });
	
}

$( '.spmodal' ).click( function() {
	$('#tipModal').modal('show');
} );

$('#dc_dateDesde').on("change", function() {
	cambiaFecha();
});

function cambiaFecha(){
	var pick_ini = $( '#dc_dateDesde' ).pickadate( 'picker' );
	var pick_fin = $( '#dc_dateHasta' ).pickadate( 'picker' );
	var iniSelec = pick_ini.get("select");
	if(valIsNullOrEmpty(iniSelec)){
		pick_fin.set('clear');
	}else{
		
		if($('#dc_viaje').is(":checked")){
			var anioSig = new Date((iniSelec.year), iniSelec.month, (iniSelec.date +90 ));

		}else{
			var anioSig = new Date((iniSelec.year +1), iniSelec.month, iniSelec.date);

		}
		var auxMin = new Date(iniSelec.year, iniSelec.month, iniSelec.date);
		pick_fin.set('min', auxMin);
		pick_fin.set('max', anioSig);
		pick_fin.set('select', anioSig);
		pick_fin.set('view', anioSig);		
	}
}
$( '#cn_rfc' ).on('blur',function(e) {
	if(!valIsNullOrEmpty($(this).val())){
		showLoader();
		auxP1.infoClientExistenttEncontrado = null;
		var rfc = $( "#cn_rfc" ).val().toUpperCase();
		if (rfcGenerico.indexOf( rfc ) < 0) {
			$.post( ligasServicios.listaPersonas, {
				term : $( this ).val(),
				tipo : 3,
				pantalla :"moduloEmpresarial"
			}, function(data) {
				var response = jQuery.parseJSON( data );
				if (response.length > 0) {
					$.each( response, function(key, registro) {
						if (registro.rfc === rfc) {
							auxP1.infoClientExistenttEncontrado = registro;
							return false;
						}
					} );
					if (auxP1.infoClientExistenttEncontrado != null) {
						$( '#nombreClienteExistt' ).text(
								auxP1.infoClientExistenttEncontrado.rfc + ' - ' + auxP1.infoClientExistenttEncontrado.nombre
										+ ' ' + auxP1.infoClientExistenttEncontrado.appPaterno + ' '
										+ auxP1.infoClientExistenttEncontrado.appMaterno );
						$( '#modalClienteExistente' ).modal( 'show' );
					}
				}else{
					auxP1.infoClientExistenttEncontrado = null;
				}
				hideLoader();
			} );
		}else{
			hideLoader();
		}
	}
} );

$("#paso1_next").click(function(e){
	removeClassInvalid();
	showLoader();
	
	var completos = validaRequeridos();
	if(completos){
		llenaDatos();
		guardaPaso1();
		
		/*document.getElementById("formPaso1").submit();*/
		
	}else{
		hideLoader();
		showMessageError('.navbar', msj.es.faltaInfo, 0);
		$("#formPaso1").submit(function(e){
	        e.preventDefault();
	    });
	}
});

function validaRequeridos(){
	var campos = $("#contPaso1 input:visible:enabled:not(:radio)");
	var completos = true;
	if( $(".cn_rdEx .switch:visible #chktoggle").is(":checked")){
		campos = $("#contPaso1 input:visible:enabled:not(:radio)").not("#cn_fismaterno");
	}
	$.each(campos, function(key, campo) {
		if($(campo).hasClass("select-dropdown")){
			var select = $(campo).siblings("select");
			completos = noSelect($(select)) ? false : completos;
		}else{
			completos = vaciosInpText($(campo)) ? false : completos;	
		}	
	});
	return completos;
}

$( '#btnClienttExisttSi' ).click(function() {
	clickButton = true;
	$( '#radio_ce' ).trigger( 'click' );
	$( "#ce_nombre" ).val(
			auxP1.infoClientExistenttEncontrado.nombre + " " + auxP1.infoClientExistenttEncontrado.appPaterno + " "
					+ auxP1.infoClientExistenttEncontrado.appMaterno ).siblings('label').addClass("active");
	$( "#ce_rfc" ).val( auxP1.infoClientExistenttEncontrado.rfc ).siblings('label').addClass("active");
	$( "#ce_codigo" ).val( auxP1.infoClientExistenttEncontrado.codigo ).siblings('label').addClass("active");
	$(".data_ctenvo .form-control:input:text").val("");
	$( '#modalClienteExistente' ).modal( 'hide' );

} );

function seleccionaTipoPer(){
	if (auxP1.infoClientExistenttEncontrado.tipoPer == 217){
		infCotizacion.tipoPersona = tipoPersona.FISICA;
	}else if(auxP1.infoClientExistenttEncontrado.tipoPer == 218){
		infCotizacion.tipoPersona = tipoPersona.MORAL;
	}
}

function guardaPaso1(){
	
	console.log(ligasServicios.guardaInfo);
	
	$.post( ligasServicios.guardaInfo, {
		datos : JSON.stringify(DatosGenerales),
		infoCot : JSON.stringify(infCotizacion)
	}, function(data) {
		console.log(data);
		var response = JSON.parse(data);
		
		if(response.code == 0){
			
			$('#folioCoti').val( response.folio );
			$('#versionCoti').val( response.version );
			$('#cotizacion').val( response.cotizacion );
			$('#infoCot').val(JSON.stringify(infCotizacion));
			$('#responseP1').val(data);
			$('#coaseguro').val(DatosGenerales.tipoCoaseguro);
			
			document.getElementById("formPaso1").submit();
			
		}else{
			showMessageError( '.navbar', response.msg, 0 );
			hideLoader();
		}
		
		
	});
	
}

function setInfoCliente(){
	infClienteAux.appMaterno = $('#infAppMaterno').val(); 
	infClienteAux.appPaterno = $('#infAppPaterno').val(); 
	infClienteAux.codigo = $('#infCodigo').val(); 
	infClienteAux.extranjero = parseInt( $('#infExtranjero').val() ); 
	infClienteAux.idDenominacion = parseInt( $('#infIdDenominacion').val() ); 
	infClienteAux.idPersona = parseInt( $('#infIdPersona').val() ); 
	infClienteAux.nombre = $('#infNonmbre').val(); 
	infClienteAux.rfc = $('#infRfc').val(); 
	infClienteAux.tipoPer = parseInt( $('#infTipoPer').val() ); 
	
	auxP1.infoClientExistenttEncontrado = infClienteAux;
	
	seleccionaTipoPer();
}

$("#guardaPaso1Endoso").click(function(e){
	removeClassInvalid();
	showLoader()
	
	var completos = validaRequeridos();
	if(completos){
		llenaDatos();
		guardaEndosoPaso1();
		
		/*document.getElementById("formPaso1").submit();*/
		
	}else{
		hideLoader();
		showMessageError('.navbar', msj.es.faltaInfo, 0);
		$("#formPaso1").submit(function(e){
	        e.preventDefault();
	    });
	}
});

function guardaEndosoPaso1(){
	
	$.post( ligasServicios.guardaEndosoPaso1URL, {
		datos : JSON.stringify(DatosGenerales),
		infoCot : JSON.stringify(infCotizacion)
	}, function(data) {
		console.log(data);
		var response = JSON.parse(data);
		
		if(response.code == 0){
			
			$('#folioCoti').val( response.folio );
			$('#versionCoti').val( response.version );
			$('#cotizacion').val( response.cotizacion );
			$('#infoCot').val(JSON.stringify(infCotizacion));
			$('#responseP1').val(data);
			$('#coaseguro').val(DatosGenerales.tipoCoaseguro);
			
			document.getElementById("formPaso1").submit();
			
		}else{
			showMessageError( '.navbar', response.msg, 0 );
			hideLoader();
		}
		
		
	});
	
}