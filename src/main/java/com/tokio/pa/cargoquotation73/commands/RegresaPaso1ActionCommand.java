package com.tokio.pa.cargoquotation73.commands;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.Bean.Persona;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.CotizadorDataResponse;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Bean.ListaRegistro;
import com.tokio.pa.cotizadorModularServices.Bean.Registro;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Constants.CotizadorModularServiceKey;
import com.tokio.pa.cotizadorModularServices.Enum.ModoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.TipoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.TipoPersona;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorGenerico;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso1;
import com.tokio.pa.cotizadorModularServices.Util.CotizadorModularUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.init-param.copy-request-parameters=true",
			"javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
			"mvc.command.name=/CotizadorTransportistas/RegresaPaso1"
		},
		service = MVCActionCommand.class
	)

public class RegresaPaso1ActionCommand extends BaseMVCActionCommand {
	
	@Reference
	CotizadorGenerico _CMServicesGenerico;
	
	@Reference
	CotizadorPaso1 _CMServicesP1;
	
	InfoCotizacion infCotizacion;
	User user;
	int idPerfilUser;

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		
		System.err.println("Entramos al Action de Regreso");
		
		llenaInfoCotizacion(actionRequest);
//		generaFechas(actionRequest);
		validaPerfil(actionRequest);
		seleccionaModo(actionRequest, actionResponse);
		CotizadorDataResponse respCotizadorData = getCotizadorData(actionRequest);
		cargaCatalogos(actionRequest, respCotizadorData);

		String infoCot = CotizadorModularUtil.objtoJson(infCotizacion);
		
		System.out.println("infCotizacionJson: " + infoCot);
		actionRequest.setAttribute("infCotizacionJson", infoCot);
		
		actionResponse.getRenderParameters().setValue("jspPage", "/pantallas/paso1.jsp");
	}
	
	
	private void llenaInfoCotizacion(ActionRequest actionRequest) {

		try {
			HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));

			user = (User) actionRequest.getAttribute(WebKeys.USER);
			idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
			

			String inf = actionRequest.getParameter("infoCotizacion");
			String legal492 = originalRequest.getParameter("leg492");

			String nombreCotizador = "";
			if (Validator.isNotNull(inf)) {
				infCotizacion = CotizadorModularUtil.decodeURL(inf);
				if(infCotizacion.getModo() == ModoCotizacion.NUEVA ||
						infCotizacion.getModo() == ModoCotizacion.REASEGURO ||
								infCotizacion.getModo() == ModoCotizacion.COASEGURO){
					infCotizacion.setModo(ModoCotizacion.EDICION);
					System.out.println("SET modo edicion");
				}
			} else if (Validator.isNotNull(legal492)) {
				infCotizacion = generaCotLegal(actionRequest);
			} else {

				infCotizacion = new InfoCotizacion();

				infCotizacion.setVersion(1);
				infCotizacion.setTipoCotizacion(TipoCotizacion.TRANSPORTES);
				
			}

			infCotizacion.setPantalla(CargoQuotation73PortletKeys.PANTALLA);
			nombreCotizador = CargoQuotation73PortletKeys.PANTALLA;
			actionRequest.setAttribute("idPerfilUser", idPerfilUser);
			
			actionRequest.setAttribute("tituloCotizador", nombreCotizador);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ llenaInfoCotizacion:");
			actionRequest.setAttribute("perfilMayorEjecutivo", false);
			e.printStackTrace();
		}
	}
	
	private void cargaCatalogos(ActionRequest actionRequest, CotizadorDataResponse respCotiData) {
		// TODO Auto-generated method stub
		try {
			final PortletSession psession = actionRequest.getPortletSession();
			@SuppressWarnings("unchecked")
			List<Persona> listaAgentes = (List<Persona>) psession.getAttribute("listaAgentes", PortletSession.APPLICATION_SCOPE);
			
			
			verificaListaAgentes(actionRequest, listaAgentes);
			
			String codigoAgente = getCodeAgente (respCotiData.getDatosCotizacion().getAgente() , listaAgentes);
			
			ListaRegistro listaMovimiento = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_MOVIMIENTO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatMoneda = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_MONEDA,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatFormaPago = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_FORMA_PAGO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatDenominacion = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_DENOMINACION,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

/*
			ListaRegistro listaGiros = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_GIRO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);
*/

//			ListaRegistro listaCanalNegocio = fGetCatalogos(
//					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
//					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
//					CotizadorModularServiceKey.LIST_CAT_CAN_NEG_TRANS,
//					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
//					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);
			
			ListaRegistro listaCanalNegocio = _CMServicesP1.getCanalNegocio(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
					codigoAgente,
					user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas);
			listaCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907

//			ListaRegistro listaCanalNegocioJ = fGetCatalogos(
//					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
//					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
//					CotizadorModularServiceKey.LIST_CAT_CAN_NEG_TRANS_J,
//					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
//					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCoaseguro = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TOPO_COASEGURO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

//			ListaRegistro listaTipoPoliza = fGetCatalogos(
//					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
//					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
//					CotizadorModularServiceKey.LIST_CAT_TIP_POL_TRANS,
//					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
//					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);
			
			ListaRegistro listaCatSector = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_SECTOR,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

//			actionRequest.setAttribute("listaGiros", listaGiros.getLista());
			actionRequest.setAttribute("listaMovimiento", listaMovimiento.getLista());
			actionRequest.setAttribute("listaCatMoneda", listaCatMoneda.getLista());
			actionRequest.setAttribute("listaAgentes", listaAgentes);
			actionRequest.setAttribute("listaCatDenominacion", listaCatDenominacion.getLista());
			actionRequest.setAttribute("listaCatFormaPago", listaCatFormaPago.getLista());
			actionRequest.setAttribute("listaCoaseguro", listaCoaseguro.getLista());
//			if( idPerfilUser > 20 ){
//				actionRequest.setAttribute("listaCanalNegocio", listaCanalNegocioJ.getLista());								
//			}else{
//				actionRequest.setAttribute("listaCanalNegocio", listaCanalNegocio.getLista());				
//			}
			actionRequest.setAttribute("listaCanalNegocio", listaCanalNegocio.getLista());				
			actionRequest.setAttribute("listaCatSector", listaCatSector.getLista());

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ cargaCatalogos:");
			e.printStackTrace();
		}

	}
	
	private InfoCotizacion generaCotLegal(ActionRequest actionRequest){
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
		
		InfoCotizacion in = new InfoCotizacion();
		
		in.setTipoCotizacion(TipoCotizacion.TRANSPORTES);
		in.setFolio(Long.parseLong(originalRequest.getParameter("folioTransportes")));
		in.setCotizacion(Long.parseLong(originalRequest.getParameter("cotizacionTransportes")));
		in.setVersion(Integer.parseInt(originalRequest.getParameter("versionTransportes")));
		
		in.setModo(ModoCotizacion.FACTURA_492);
		
		System.out.println("-----------");
		System.out.println(in.toString());
		return in;
		
	}
	
	private void verificaListaAgentes(ActionRequest actionRequest, List<Persona> listaAgentes) {
		if (Validator.isNull(listaAgentes)) {
			SessionErrors.add(actionRequest, "errorConocido");
			actionRequest.setAttribute("errorMsg", "Error al cargar su información cierre sesion");
			SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
					+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
	}
	
	private void generaFechas(ActionRequest actionRequest) {
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaMasAnio = LocalDate.now().plusYears(1);

		actionRequest.setAttribute("fechaHoy", fechaHoy);
		actionRequest.setAttribute("fechaMasAnio", fechaMasAnio);
	}
	
	private void validaPerfil(ActionRequest actionRequest) {
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));

		user = (User) actionRequest.getAttribute(WebKeys.USER);
		idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		if( idPerfilUser < CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR ){
			actionRequest.setAttribute("dNonePerfil", "d-none");
		}
	}
	
	private ListaRegistro fGetCatalogos(int p_rownum, String p_tiptransaccion, String p_codigo,
			int p_activo, String p_usuario, String p_pantalla, ActionRequest actionRequest) {
		try {
			ListaRegistro lr = _CMServicesGenerico.getCatalogo(p_rownum, p_tiptransaccion, p_codigo,
					p_activo, p_usuario, p_pantalla);

			lr.getLista().sort(Comparator.comparing(Registro::getDescripcion));
			return lr;
		} catch (Exception e) {
			System.err.print("----------------- error en traer los catalogos");
			e.printStackTrace();
			SessionErrors.add(actionRequest, "errorConocido");
			actionRequest.setAttribute("errorMsg", "Error en catalogos");
			SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
					+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			return null;
		}
	}
	
	private void seleccionaModo(ActionRequest actionRequest, ActionResponse actionResponse) {
		CotizadorDataResponse respuesta = new CotizadorDataResponse();
		respuesta.setCode(5);
		respuesta.setMsg("Error al cargar su información");
		try {
			
			final PortletSession psession = actionRequest.getPortletSession();
			@SuppressWarnings("unchecked")
			List<Persona> listaAgentes = (List<Persona>) psession.getAttribute("listaAgentes",
					PortletSession.APPLICATION_SCOPE);
			verificaListaAgentes(actionRequest, listaAgentes);
			
			String codigoAgente = "";
			
			ListaRegistro listaCatCanalNegocio = null;
			
			switch (infCotizacion.getModo()) {
				case EDICION:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					System.err.println("respuesta: " + respuesta);
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				case COPIA:
					respuesta = _CMServicesP1.copyCotizadorData(infCotizacion.getFolio() + "",
							Integer.parseInt(infCotizacion.getCotizacion() + ""),
							infCotizacion.getVersion(), user.getScreenName(),
							infCotizacion.getPantalla());
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());

					infCotizacion
							.setFolio(Long.parseLong(respuesta.getDatosCotizacion().getFolio()));
					infCotizacion.setCotizacion(respuesta.getDatosCotizacion().getCotizacion());
					infCotizacion.setVersion(respuesta.getDatosCotizacion().getVersion());
					break;
				case ALTA_ENDOSO:
					SimpleResponse infEndo = _CMServicesP1.GuardarCotizacionEndoso(
							infCotizacion.getCotizacion() + "", infCotizacion.getVersion() + "",
							infCotizacion.getPantalla(), user.getScreenName());

					infCotizacion.setFolio(Long.parseLong(infEndo.getFolio()));
					infCotizacion.setCotizacion(infEndo.getCotizacion());
					infCotizacion.setVersion(infEndo.getVersion());

					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());

					actionRequest.setAttribute("perfilMayorEjecutivo", false);
					break;
				case EDITAR_ALTA_ENDOSO:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());

					actionRequest.setAttribute("perfilMayorEjecutivo", false);
					break;
				case BAJA_ENDOSO:
					
					SimpleResponse simpleRespuesta = _CMServicesGenerico.guardarCotizacionEndosoBaja(infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), null, 1, 0, 0,
							user.getScreenName() , infCotizacion.getPantalla(), 0, 0);
					
					infCotizacion.setFolio(Long.parseLong(simpleRespuesta.getFolio()));
					infCotizacion.setCotizacion(simpleRespuesta.getCotizacion());
					infCotizacion.setVersion(simpleRespuesta.getVersion());					

					respuesta = _CMServicesP1.getCotizadorData(Long.parseLong(simpleRespuesta.getFolio()),
							simpleRespuesta.getCotizacion(), simpleRespuesta.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());
					
					actionRequest.setAttribute("perfilMayorEjecutivo", false);
					break;
				case EDITAR_BAJA_ENDOSO:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());
					
					infCotizacion.setModo(ModoCotizacion.BAJA_ENDOSO);
					actionRequest.setAttribute("perfilMayorEjecutivo", false);
					break;
				case AUX_PASO4:

					break;
				case NUEVA:
					break;
				case CONSULTA:
				case CONSULTA_VOBO_REASEGURO:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
					
				case FACTURA_492 :
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), infCotizacion.getPantalla());
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				case EDICION_JAPONES:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				case CONSULTAR_REVISION:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				case REASEGURO:
				case COASEGURO:
				case VOBO_REASEGURO:
				case REASEGURO_CONSULTA:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					System.err.println("respuesta: " + respuesta);
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					actionRequest.setAttribute("infoCot", CotizadorModularUtil.encodeURL(infCotizacion));
					
					break;
				case DECLARACION_ENDOSO:
					
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					codigoAgente = getCodeAgente (respuesta.getDatosCotizacion().getAgente() , listaAgentes);
					
					listaCatCanalNegocio = _CMServicesP1.getCanalNegocio(
							CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
							CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
							CotizadorModularServiceKey.LIST_CAT_CAN_NEG,
							CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS,
							codigoAgente,
							user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas);
					listaCatCanalNegocio.getLista().removeIf(n -> (n.getDescripcion().contains("M11"))); // Mantis 3907
					actionRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				default:
					break;

			}

			if (respuesta.getDatosCotizacion().getDatosCliente().getTipoPer() == 218) {
				infCotizacion.setTipoPersona(TipoPersona.MORAL);
			} else {
				infCotizacion.setTipoPersona(TipoPersona.FISICA);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (infCotizacion.getModo() != ModoCotizacion.NUEVA) {

			if (respuesta.getCode() > 0) {
				SessionErrors.add(actionRequest, "errorConocido");
				actionRequest.setAttribute("errorMsg", respuesta.getMsg());
				SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
						+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			} else {
				String datosCliente = CotizadorModularUtil
						.objtoJson(respuesta.getDatosCotizacion().getDatosCliente());

				/*
				LocalDate fechaHoy = generaFecha(respuesta.getDatosCotizacion().getFecInicio());
				LocalDate fechaMasAnio = generaFecha(respuesta.getDatosCotizacion().getFecFin());


				fechaHoy = validaCambioFecha(fechaHoy);

				actionRequest.setAttribute("fechaHoy", fechaHoy);
				actionRequest.setAttribute("fechaMasAnio", fechaMasAnio);
				*/
				
				actionRequest.setAttribute("cotizadorData", respuesta.getDatosCotizacion());
				actionRequest.setAttribute("datosCliente", datosCliente);

			}
		}
	}
	
	private CotizadorDataResponse getCotizadorData(ActionRequest actionRequest){
		CotizadorDataResponse respuesta = new CotizadorDataResponse();
		try {
			respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
					infCotizacion.getCotizacion(), infCotizacion.getVersion(),
					user.getScreenName(), infCotizacion.getPantalla());
			
			LocalDate fechaHoy = generaFecha(respuesta.getDatosCotizacion().getFecInicio());
			LocalDate fechaMasAnio = generaFecha(respuesta.getDatosCotizacion().getFecFin());
			actionRequest.setAttribute("fechaHoy", fechaHoy);
			actionRequest.setAttribute("fechaMasAnio", fechaMasAnio);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return respuesta;
	}
	
	private LocalDate generaFecha(String fecha) {
		String aux = "";
		for (char c : fecha.toCharArray()) {
			aux += Character.isDigit(c) ? c : "";
		}
		Timestamp t = new Timestamp(Long.parseLong(aux));
		return t.toLocalDateTime().toLocalDate();
	}
	
	private String getCodeAgente (int idAgente , List<Persona> listaAgentes){
		String codeAgente = "";
		for (Persona persona : listaAgentes) {
			if(persona.getIdPersona() == idAgente){
				String[] parts = persona.getNombre().split("-");
				codeAgente = parts[0].trim();
				break;
			}
		}
		return codeAgente;
	}

}
