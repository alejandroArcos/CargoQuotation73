package com.tokio.pa.cargoquotation73.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author urielfloresvaldovinos
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.version=3.0",
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=CotizadorTransportistasPortlet Portlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/view.jsp",
			"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user",
			"com.liferay.portlet.private-session-attributes=false",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"com.liferay.portlet.private-request-attributes=false"
		},
		service = Portlet.class
	)


public class CargoQuotation73Portlet extends MVCPortlet {
	
	@Reference
	CotizadorGenerico _CMServicesGenerico;
	
	@Reference
	CotizadorPaso1 _CMServicesP1;
	
	InfoCotizacion infCotizacion;
	User user;
	int idPerfilUser;
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		
		Set<String> en = renderRequest.getRenderParameters().getNames();
		
		int params = 0;
		
		Iterator<String> it = en.iterator();
		
		while (it.hasNext()) {
			Object objOri = it.next();
			String param = (String) objOri;
			String value = renderRequest.getRenderParameters().getValue(param);
			System.out.println("[ ---> " +param + " : " + value );
			params++;
		}
//		HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		//
		String inf = renderRequest.getRenderParameters().getValue("infoCotizacion");
		if( Validator.isNull(inf) ){
			llenaInfoCotizacion(renderRequest, renderResponse);
			
			renderRequest.setAttribute("infCot", infCotizacion);
		}
		
		//viewOnlySubcriptor( renderResponse );
		
		validaPerfil(renderRequest);
		validaPantalla( renderRequest );
		
		renderRequest.setAttribute("perfilSuscriptor", perfilSuscriptor());
		renderRequest.setAttribute("perfilJapones", perfilJapones());
		renderRequest.setAttribute("perfilSuscriptorJ", perfilSuscriptorJapones());
		renderRequest.setAttribute("retroactividad", diasRetroactividad());
		renderRequest.setAttribute("idPerfilUser", idPerfilUser);
		
		super.render(renderRequest, renderResponse);
	}
	
	private void validaPantalla( RenderRequest renderRequest ){
		String paso = ParamUtil.getString(renderRequest, "paso");
		if( Validator.isNotNull(paso) ){
			switch (paso) {
			case "paso2":
				
				break;

			default:
				break;
			}
		}else{
			System.err.println("Entré a paso 1");
			cargaCatalogos(renderRequest);
			//generaFechas(renderRequest);
		}
	}
	
	private ListaRegistro fGetCatalogos(int p_rownum, String p_tiptransaccion, String p_codigo,
			int p_activo, String p_usuario, String p_pantalla, RenderRequest renderRequest) {
		try {
			ListaRegistro lr = _CMServicesGenerico.getCatalogo(p_rownum, p_tiptransaccion, p_codigo,
					p_activo, p_usuario, p_pantalla);

			lr.getLista().sort(Comparator.comparing(Registro::getDescripcion));
			return lr;
		} catch (Exception e) {
			System.err.print("----------------- error en traer los catalogos");
			e.printStackTrace();
			SessionErrors.add(renderRequest, "errorConocido");
			renderRequest.setAttribute("errorMsg", "Error en catalogos");
			SessionMessages.add(renderRequest, PortalUtil.getPortletId(renderRequest)
					+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			return null;
		}
	}
	
	private void llenaInfoCotizacion(RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));

			user = (User) renderRequest.getAttribute(WebKeys.USER);
			idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
			
			String inf = originalRequest.getParameter("infoCotizacion");
			String legal492 = originalRequest.getParameter("leg492");
			System.err.println("inf");
			System.err.println(inf);
			
			String nombreCotizador = "";
			if (Validator.isNotNull(inf)) {
				infCotizacion = CotizadorModularUtil.decodeURL(inf);
				System.err.println("NO ES NUEVA " + infCotizacion);
				seleccionaModo(renderRequest, renderResponse);
			} else if (Validator.isNotNull(legal492)) {
				infCotizacion = generaCotLegal(renderRequest);
			} else {
				infCotizacion = new InfoCotizacion();

				infCotizacion.setVersion(1);
				infCotizacion.setTipoCotizacion(TipoCotizacion.TRANSPORTES);
				System.err.println("ES NUEVA");
				generaFechas(renderRequest);
				
					
				String btoa = originalRequest.getParameter("btoa");
				
				if(Validator.isNotNull(btoa)) {
				
					byte[] decodedBytes = Base64.getUrlDecoder().decode(btoa);		
					String decodeb64 = new String(decodedBytes);
					String idSolicitud = decodeb64.split(";")[0];
					
					infCotizacion.setSolicitud(idSolicitud);
					
					renderRequest.setAttribute("numeroSolicitud", true);
				}
			}

			infCotizacion.setPantalla(CargoQuotation73PortletKeys.PANTALLA);
			nombreCotizador = CargoQuotation73PortletKeys.PANTALLA;
			
			String infoCot = CotizadorModularUtil.objtoJson(infCotizacion);
			System.err.println("infCotizacionJson: " + infoCot);
			renderRequest.setAttribute("infCotizacionJson", infoCot);
			
			renderRequest.setAttribute("tituloCotizador", nombreCotizador);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ llenaInfoCotizacion:");
			renderRequest.setAttribute("perfilMayorEjecutivo", false);
			e.printStackTrace();
		}
	}
	
	private InfoCotizacion generaCotLegal(RenderRequest renderRequest){
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		
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
	
	private void cargaCatalogos(RenderRequest renderRequest) {
		// TODO Auto-generated method stub
		try {

			final PortletSession psession = renderRequest.getPortletSession();
			@SuppressWarnings("unchecked")
			List<Persona> listaAgentes = (List<Persona>) psession.getAttribute("listaAgentes",
					PortletSession.APPLICATION_SCOPE);
			System.out.println("lista de agentes"+listaAgentes);
			verificaListaAgentes(renderRequest, listaAgentes);


			ListaRegistro listaMovimiento = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_MOVIMIENTO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCatMoneda = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_MONEDA,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCatFormaPago = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_FORMA_PAGO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCatDenominacion = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_DENOMINACION,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

//			ListaRegistro listaGiros = fGetCatalogos(
//					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
//					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
//					CotizadorModularServiceKey.LIST_CAT_GIRO,
//					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
//					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCanalNegocio = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_CAN_NEG_TRANS,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCanalNegocioJ = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_CAN_NEG_TRANS_J,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaCoaseguro = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TOPO_COASEGURO,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);

			ListaRegistro listaTipoPoliza = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TIP_POL_TRANS,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);
			
			ListaRegistro listaCatSector = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_SECTOR,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, renderRequest);
			
			System.out.println("listaCanalNegocio");
			System.out.println(listaCanalNegocio);
			System.out.println("listaCoaseguro");
			System.out.println(listaCoaseguro);
			System.out.println("listaTipoPoliza");
			System.out.println(listaTipoPoliza);
			System.out.println("listaCanalNegocioJ");
			System.out.println(listaCanalNegocioJ);

//			renderRequest.setAttribute("listaGiros", listaGiros.getLista());
			renderRequest.setAttribute("listaMovimiento", listaMovimiento.getLista());
			renderRequest.setAttribute("listaCatMoneda", listaCatMoneda.getLista());
			renderRequest.setAttribute("listaAgentes", listaAgentes);
			renderRequest.setAttribute("listaCatDenominacion", listaCatDenominacion.getLista());
			renderRequest.setAttribute("listaCatFormaPago", listaCatFormaPago.getLista());
			renderRequest.setAttribute("listaCoaseguro", listaCoaseguro.getLista());
			if( idPerfilUser > 20 ){
				renderRequest.setAttribute("listaCanalNegocio", listaCanalNegocioJ.getLista());								
			}else{
				renderRequest.setAttribute("listaCanalNegocio", listaCanalNegocio.getLista());				
			}
			renderRequest.setAttribute("listaCatSector", listaCatSector.getLista());

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ cargaCatalogos:");
			e.printStackTrace();
		}

	}
	
	private void verificaListaAgentes(RenderRequest renderRequest, List<Persona> listaAgentes) {
		if (Validator.isNull(listaAgentes)) {
			SessionErrors.add(renderRequest, "errorConocido");
			renderRequest.setAttribute("errorMsg", "Error al cargar su información cierre sesion");
			SessionMessages.add(renderRequest, PortalUtil.getPortletId(renderRequest)
					+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
	}
	private void generaFechas(RenderRequest renderRequest) {
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaMasAnio = LocalDate.now().plusYears(1);

		renderRequest.setAttribute("fechaHoy", fechaHoy);
		renderRequest.setAttribute("fechaMasAnio", fechaMasAnio);
	}
	
	private void validaPerfil(RenderRequest renderRequest) {
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));

		user = (User) renderRequest.getAttribute(WebKeys.USER);
		idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		if( idPerfilUser < CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR ){
			renderRequest.setAttribute("dNonePerfil", "d-none");
		}
	}
	
	private int perfilSuscriptorJapones() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_EJECUTIVOJAPONES:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	private int diasRetroactividad() {
		switch (idPerfilUser) {
		
			case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
				return CargoQuotation73PortletKeys.DIAS_RETROACTIVOS_SUSCRIPTORJR;
			case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
				return CargoQuotation73PortletKeys.DIAS_RETROACTIVOS_SUSCRIPTORSR;
			case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
				return CargoQuotation73PortletKeys.DIAS_RETROACTIVOS_SUSCRIPTORMR;
			case CargoQuotation73PortletKeys.PERFIL_JAPONES:
				return CargoQuotation73PortletKeys.DIAS_RETROACTIVOS_SUSCRIPTORJR;
			default: return 0;
		}
	}
	
	private int perfilSuscriptor() {
		try {
			switch (idPerfilUser) {
			
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
					return 1;
//				case CargoQuotation73PortletKeys.PERFIL_JAPONES:
//					return 1;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	private int perfilJapones() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_JAPONES:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void seleccionaModo(RenderRequest renderRequest, RenderResponse renderResponse) {
		CotizadorDataResponse respuesta = new CotizadorDataResponse();
		respuesta.setCode(5);
		respuesta.setMsg("Error al cargar su información");
		
		try {
			
			final PortletSession psession = renderRequest.getPortletSession();
			@SuppressWarnings("unchecked")
			List<Persona> listaAgentes = (List<Persona>) psession.getAttribute("listaAgentes",
					PortletSession.APPLICATION_SCOPE);
			verificaListaAgentes(renderRequest, listaAgentes);
			
			String codigoAgente = "";
			
			ListaRegistro listaCatCanalNegocio = null;
			
			System.err.println("Pantalla: " + CargoQuotation73PortletKeys.CotizadorTransportistas);
			System.err.println("infCotizacion.getModo(): " + infCotizacion.getModo());
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case COPIA:
					respuesta = _CMServicesP1.copyCotizadorData(infCotizacion.getFolio() + "",
							Integer.parseInt(infCotizacion.getCotizacion() + ""),
							infCotizacion.getVersion(), user.getScreenName(),
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					infCotizacion
							.setFolio(Long.parseLong(respuesta.getDatosCotizacion().getFolio()));
					infCotizacion.setCotizacion(respuesta.getDatosCotizacion().getCotizacion());
					infCotizacion.setVersion(respuesta.getDatosCotizacion().getVersion());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case ALTA_ENDOSO:
					SimpleResponse infEndo = _CMServicesP1.GuardarCotizacionEndoso(
							infCotizacion.getCotizacion() + "", infCotizacion.getVersion() + "",
							CargoQuotation73PortletKeys.CotizadorTransportistas, user.getScreenName());

					infCotizacion.setFolio(Long.parseLong(infEndo.getFolio()));
					infCotizacion.setCotizacion(infEndo.getCotizacion());
					infCotizacion.setVersion(infEndo.getVersion());

					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);

					renderRequest.setAttribute("perfilMayorEjecutivo", false);
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case EDITAR_ALTA_ENDOSO:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);

					renderRequest.setAttribute("perfilMayorEjecutivo", false);
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case BAJA_ENDOSO:
					
					SimpleResponse simpleRespuesta = _CMServicesGenerico.guardarCotizacionEndosoBaja(infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), null, 1, 0, 0,
							user.getScreenName() , CargoQuotation73PortletKeys.CotizadorTransportistas, 0, 0);
					
					infCotizacion.setFolio(Long.parseLong(simpleRespuesta.getFolio()));
					infCotizacion.setCotizacion(simpleRespuesta.getCotizacion());
					infCotizacion.setVersion(simpleRespuesta.getVersion());					

					respuesta = _CMServicesP1.getCotizadorData(Long.parseLong(simpleRespuesta.getFolio()),
							simpleRespuesta.getCotizacion(), simpleRespuesta.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					renderRequest.setAttribute("perfilMayorEjecutivo", false);
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case EDITAR_BAJA_ENDOSO:
					respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
							infCotizacion.getCotizacion(), infCotizacion.getVersion(),
							user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
					
					infCotizacion.setModo(ModoCotizacion.BAJA_ENDOSO);
					renderRequest.setAttribute("perfilMayorEjecutivo", false);
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case AUX_PASO4:

					break;
				case NUEVA:
					
					HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
				
					String btoa = originalRequest.getParameter("btoa");
					
					if(Validator.isNotNull(btoa)) {
					
						byte[] decodedBytes = Base64.getUrlDecoder().decode(btoa);		
						String decodeb64 = new String(decodedBytes);
						String idSolicitud = decodeb64.split(";")[0];
						
						infCotizacion.setSolicitud(idSolicitud);
						
						renderRequest.setAttribute("numeroSolicitud", true);
					}
					
					break;
				case CONSULTA:
				case CONSULTA_VOBO_REASEGURO:
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;	
				case FACTURA_492 :
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					renderRequest.setAttribute("infoCot",
							CotizadorModularUtil.encodeURL(infCotizacion));
					
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					break;
				case REASEGURO:
				case COASEGURO:
				case VOBO_REASEGURO:
				case REASEGURO_CONSULTA:
				case COASEGURO_CONSULTA:
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					validaFolioUsuario((int)infCotizacion.getCotizacion(),
							infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),
							CargoQuotation73PortletKeys.CotizadorTransportistas, renderResponse);
					
					renderRequest.setAttribute("infoCot",
							CotizadorModularUtil.encodeURL(infCotizacion));
					
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
					renderRequest.setAttribute("listaCatCanalNegocio", listaCatCanalNegocio.getLista());
					
					break;
				default:
					break;

			}
			
			if(infCotizacion.getModo() != ModoCotizacion.NUEVA) {
				if (respuesta.getDatosCotizacion().getDatosCliente().getTipoPer() == 218) {
					infCotizacion.setTipoPersona(TipoPersona.MORAL);
				} else {
					infCotizacion.setTipoPersona(TipoPersona.FISICA);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (infCotizacion.getModo() != ModoCotizacion.NUEVA) {

			if (respuesta.getCode() > 0) {
				SessionErrors.add(renderRequest, "errorConocido");
				renderRequest.setAttribute("errorMsg", respuesta.getMsg());
				SessionMessages.add(renderRequest, PortalUtil.getPortletId(renderRequest)
						+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			} else {
				String datosCliente = CotizadorModularUtil
						.objtoJson(respuesta.getDatosCotizacion().getDatosCliente());

				LocalDate fechaHoy = generaFecha(respuesta.getDatosCotizacion().getFecInicio());
				LocalDate fechaMasAnio = generaFecha(respuesta.getDatosCotizacion().getFecFin());

				fechaHoy = validaCambioFecha(fechaHoy);


				renderRequest.setAttribute("fechaHoy", fechaHoy);
				renderRequest.setAttribute("fechaMasAnio", fechaMasAnio);
				renderRequest.setAttribute("cotizadorData", respuesta.getDatosCotizacion());
				renderRequest.setAttribute("datosCliente", datosCliente);

			}
		}
	}
	
	private LocalDate validaCambioFecha(LocalDate fechaOriginal) {
		switch (infCotizacion.getModo()) {
			case ALTA_ENDOSO:
				return fechaMayor(fechaOriginal);
			case BAJA_ENDOSO:
				return fechaMayor(fechaOriginal);
			case EDITAR_ALTA_ENDOSO:
				return fechaMayor(fechaOriginal);
			case EDITAR_BAJA_ENDOSO:
				return fechaMayor(fechaOriginal);
			default:
				return fechaOriginal;
		}
	}
	
	private LocalDate fechaMayor(LocalDate fechaOriginal) {
		LocalDate hoy = LocalDate.now();
		if (hoy.isAfter(fechaOriginal)) {
			return hoy;
		}
		return fechaOriginal;
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
	
	private void validaFolioUsuario(int cotizacion, int version, int perfilId, String usuario, String pantalla, RenderResponse renderResponse) throws IOException{
		SimpleResponse resp = new SimpleResponse();
		try {
			resp = _CMServicesGenerico.validaFolioUsuario(cotizacion, version, perfilId, usuario, pantalla);
		} catch (Exception e) {
			// TODO: handle exception	
			System.err.println("Error al validar permisos por perfil");
			resp.setCode(1);
		}finally {
			if( resp.getCode() != 0 ){
				PortalUtil.getHttpServletResponse(renderResponse).sendRedirect("/group/portal-agentes/" );
			}						
		}
	}
	
	private int perfilSuscriptorJr() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	private int perfilSuscriptorSrMr() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	private boolean perfilSuscriptorGeneral() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
					return true;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
					return true;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
					return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	private int perfilAgenteEjecutivo() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_AGENTE:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_EJECUTIVO:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	private void viewOnlySubcriptor( RenderResponse renderResponse ){
		if( !perfilSuscriptorGeneral() ) {
			String location = "/group/portal-agentes/";
			try {
				PortalUtil.getHttpServletResponse(renderResponse).sendRedirect(location );
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error al redireccionar");
			}
		}
	}
}