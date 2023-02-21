/**
 * Modo para el flujo de cotizaciones
 */
const modo = {
    NUEVA: "NUEVA",
    EDICION: "EDICION",
    COPIA: "COPIA",
    AUX_PASO4: "AUX_PASO4",
    ALTA_ENDOSO: "ALTA_ENDOSO",
    BAJA_ENDOSO: "BAJA_ENDOSO",
    EDITAR_ALTA_ENDOSO : "EDITAR_ALTA_ENDOSO",
    EDITAR_BAJA_ENDOSO : "EDITAR_BAJA_ENDOSO",
    CONSULTA : "CONSULTA",
    FACTURA_492 : "FACTURA_492",
    ERROR : "ERROR",
    RENOVACION_AUTOMATICA : "RENOVACION_AUTOMATICA",
    CONSULTAR_RENOVACION_AUTOMATICA : "CONSULTAR_RENOVACION_AUTOMATICA",
	EDICION_JAPONES : "EDICION_JAPONES",
	CONSULTAR_REVISION: "CONSULTAR_REVISION",
	COASEGURO: "COASEGURO",
	REASEGURO: "REASEGURO",
	DECLARACION_ENDOSO: "DECLARACION_ENDOSO",
	REASEGURO_CONSULTA: "REASEGURO_CONSULTA",
	CONSULTA_VOBO_REASEGURO: "CONSULTA_VOBO_REASEGURO",
	COASEGURO_CONSULTA: "COASEGURO_CONSULTA",
};


const tipoCotizacion = {
	ERROR : "ERROR",
	FAMILIAR : "FAMILIAR",
	EMPRESARIAL : "EMPRESARIAL"
};

const tipoPersona = {
		FISICA : "FISICA",
		MORAL : "MORAL"
};

const formatter = new Intl.NumberFormat('en-US', {
	  style: 'currency',
	  currency: 'USD',
	  prefix: '',
	  pattern: '#,###,###.##',
	  minimumFractionDigits: 2
});

const diasRetroactividad = 14;

const msj = {
		es : {
			errorInformacion : "Error al  cargar la información",
			catSinInfo: "Catalogo sin información",
	        campoRequerido: "El campo es requerido",
	        faltaInfo: "Hace falta información requerida",
	        errorGuardar: "Error al guardar su información"
		}
};

/**
 * objeto para Url de Resources Command 
 */
var ligasServicios = {
	listaPersonas : "",
	listaSubgiros : "",
	guardaInfo : "",
	redirige : "",
	saveDeducibles : "",
	saveClausulasA : "",
	plantillaSlip : "",
	generacionSlip : "",
	recalculoPrimaDeposito: "",
	getDeducibles: "",
	secionComisionUrl: "",
	guardaEndosoPaso1URL: ""
};

/**
 * rfc genericos que se descartan
 */
var rfcGenerico = ["XAXX010101000", "XEXX010101000"];

/**
 * Variables globales auxiliares j
 */
var auxP1 = {
	infoClientExistenttEncontrado : null
};

var infClienteAux = {
		appMaterno : "",
		appPaterno : "",
		codigo : "",
		extranjero : 0,
		idDenominacion : 0,
		idPersona : 0,
		nombre : "",
		rfc : "",
		tipoPer : 0
}

/**
 * informacion necesaria para guardar
 */
var DatosGenerales = {
		 tipomov :  0,
		 vigencia :  0,
		 fecinicio :  "",
		 fecfin :  "",
		 moneda :  0,
		 formapago :  0,
		 agente :  0,
		 idPersona :  0,
		 tipoPer :  0,
		 rfc : ""  ,
		 nombre :  "",
		 appPaterno :  "",
		 appMaterno :  "",
		 idDenominacion :  0,
		 codigo :  "",
		 modo :  "",
		 tipoCot : "",
		 cotizacion :  "",
		 version :  0,
		 giro :  "",
		 subGiro :  0,
		 folio :  "",
		 detalleSubGiro :  "",
		 pantalla : "",
		 p_permisoSubgiro :  0,
		 subEstado : "",
		 canalN : 0,
		 tipoCoaseguro : 0,
		 vigencia : 0
};

var CondicionesAseguramiento = {
	tipoMercancia : 0,
	clausula : 0,
	tipoBienes : 0,
	tipoPronostico : 0,
	territorialidad : 0,
	origen : 0,
	destino : 0,
	primaDeposito : 0,
	bienesTransportar : ""
};

var LimitesGenerales = {
	robo : 0,
	monto : 0,
	limiteMaxEmbarque : 0,
	limiteMaxEstadia : 0,
	pronosticoTerrestre : 0,
	pronosticoMaritimo : 0,
	pronosticoIntermodal : 0,
	mensajeria : 0,
	montoMensajeria : 0,
	prevencion : 0,
	medioCertificado : 0,
	viasComunicacion : 0
};

var CoberturasAdicionales = {
	estadia : 0,
	averias : 0,
	huelgaTerrestre : 0,
	huelgaMaritimo : 0,
	montoHMaritimo : 0,
	huelgaAereo : 0,
	montoHAereo : 0,
	huelgaEstadia : 0,
	montoHEstadia : 0
}

var rutas = [];

/**
 * rfc genericos que se descartan
 */
var rfcGenerico = ["XAXX010101000", "XEXX010101000"];

const enlace = {
		FAMILIAR : "/paquete-familiar",
		EMPRESARIAL : "/paquete-empresarial",
		PASO2 : "/paso2",
		PASO3 : "/paso3",
		PASO4 : "/paso4"
	};