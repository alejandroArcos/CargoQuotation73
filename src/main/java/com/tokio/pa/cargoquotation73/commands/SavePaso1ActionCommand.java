package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.DatosTransportesResponse;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Bean.ListaRegistro;
import com.tokio.pa.cotizadorModularServices.Bean.Persona;
import com.tokio.pa.cotizadorModularServices.Bean.Registro;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Constants.CotizadorModularServiceKey;
import com.tokio.pa.cotizadorModularServices.Exception.CotizadorModularException;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorGenerico;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;
import com.tokio.pa.cotizadorModularServices.Util.CotizadorModularUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		 property = {
		 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		 "mvc.command.name=/CotizadorTransportistas/SavePaso1"
		 },
		 service = MVCActionCommand.class
		 )

public class SavePaso1ActionCommand extends BaseMVCActionCommand{
	
	@Reference
	CotizadorGenerico _CMServicesGenerico;
	
	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;
	
	User user;
	int idPerfilUser;
	InfoCotizacion infCot;
	int idClausula;
	
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
//		responseP1
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
		Enumeration<String> en = actionRequest.getParameterNames();
		
		while (en.hasMoreElements()) {
			Object objOri = en.nextElement();
			String param = (String) objOri;
			String value = actionRequest.getParameter(param);
			System.err.println("[ 1---> " +param + " : " + value );
		}
		System.err.println("Paso 2 consume");
		
		user = (User) actionRequest.getAttribute(WebKeys.USER);
		idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		String paso = ParamUtil.getString(actionRequest, "paso");
		String folioCoti = ParamUtil.getString(actionRequest, "folioCoti");
		String versionCoti = ParamUtil.getString(actionRequest, "versionCoti");
		String cotizacion = ParamUtil.getString(actionRequest, "cotizacion");
		int canalNegocio = ParamUtil.getInteger(actionRequest, "canalNegocioInf");
		int tipoCoaseguro = ParamUtil.getInteger(actionRequest, "coaseguro");
		String infoCotAux = ParamUtil.getString(actionRequest, "infoCot");
		String responseP1 = ParamUtil.getString(actionRequest, "responseP1");
		
		Gson gson = new Gson();
		infCot = gson.fromJson(infoCotAux, InfoCotizacion.class);
		
		infCot.setCotizacion(Long.parseLong(cotizacion));
		infCot.setFolio(Long.parseLong(folioCoti));
		infCot.setVersion(Integer.parseInt(versionCoti));
		infCot.setCanalNegocio(canalNegocio);
		infCot.setTipoCoaseguro(tipoCoaseguro);
		
//		_CMPaso2Transportes.validaSuscripcionTransportes(cotizacion, version, idPerfil, canalNegocio, usuario, pabntalla);
		
		infoPaso1toView(actionRequest, responseP1);
		
		String infoCot = CotizadorModularUtil.encodeURL(infCot);
		String infCotJson = CotizadorModularUtil.objtoJson(infCot);
		
		actionRequest.setAttribute("paso", paso );
		actionRequest.setAttribute("folioCoti", folioCoti );
		actionRequest.setAttribute("versionCoti", versionCoti );
		actionRequest.setAttribute("cotizacion", cotizacion );
		actionRequest.setAttribute("infoCot", infoCot );
		actionRequest.setAttribute("infCot", infCot );
		actionRequest.setAttribute("infCotizacion", infCotJson);
		actionRequest.setAttribute("idPerfilUser", idPerfilUser);
		
		consultaInfoTransportes(actionRequest);
		cargaCatalogos(actionRequest);
		
		actionResponse.setRenderParameter("jspPage", "/pantallas/paso2.jsp");
	}
	
	private void infoPaso1toView(ActionRequest actionRequest, String responseP1){
		Gson gson = new Gson();
		if( Validator.isNotNull(responseP1) ){
			SimpleResponse infP1 = gson.fromJson(responseP1, SimpleResponse.class);
			infCot.setPoliza("" + infP1.getVigencia());
			actionRequest.setAttribute("infP1", infP1);
		}
	}
	
	private void cargaCatalogos(ActionRequest actionRequest) {
		// TODO Auto-generated method stub
		try {

			final PortletSession psession = actionRequest.getPortletSession();
			@SuppressWarnings("unchecked")
			List<Persona> listaAgentes = (List<Persona>) psession.getAttribute("listaAgentes",
					PortletSession.APPLICATION_SCOPE);
			
			verificaListaAgentes(actionRequest, listaAgentes);


			ListaRegistro listaCatTipoMercancia = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TIPO_MERC,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);
			
			ListaRegistro listaCatClausula = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_CLAUSLA,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatTipoBienes = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TIP_BIENES,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatTipoPronostico = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TIP_PRON,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatTerritorialidad = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_TERRITORIALIDAD,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			ListaRegistro listaCatOrigenDestino = fGetCatalogos(
					CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
					CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
					CotizadorModularServiceKey.LIST_CAT_ORI_DES,
					CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
					CargoQuotation73PortletKeys.CotizadorTransportistas, actionRequest);

			actionRequest.setAttribute("listaCatTipoMercancia", listaCatTipoMercancia.getLista());
			actionRequest.setAttribute("listaCatClausula", listaCatClausula.getLista());
			String clausula ="";
			for(Registro r:listaCatClausula.getLista()) {
				if(r.getIdCatalogoDetalle()==this.idClausula) {
					System.out.println("r.getValor(): " + r.getValor());
					if(r.getValor().contains(" C")) {
						clausula="C";
					}else {
						clausula="A/B";
					}
				}
			}
			String listaCatTipoBie = CotizadorModularUtil.objtoJson(listaCatTipoBienes.getLista());
			actionRequest.setAttribute("listaCatTipoBie", listaCatTipoBie);
			List addItems = new ArrayList<>();
			for(Registro r:listaCatTipoBienes.getLista()) {
				if(!(clausula.equals("") || (!clausula.equals("C") && r.getOtro().equals("C")))) {
					addItems.add(r);
				}
			}
			actionRequest.setAttribute("listaCatTipoBienes", addItems);
			String listaCatTipoPron = CotizadorModularUtil.objtoJson(listaCatTipoPronostico.getLista());
			actionRequest.setAttribute("listaCatTipoPron", listaCatTipoPron);
			addItems = new ArrayList<>();
			for(Registro r:listaCatTipoPronostico.getLista()) {
				if(!(clausula.equals("") || (!clausula.equals("C") && r.getOtro().equals("C")))) {
					addItems.add(r);
				}
			}
			actionRequest.setAttribute("listaCatTipoPronostico", addItems);
			actionRequest.setAttribute("listaCatTerritorialidad", listaCatTerritorialidad.getLista());
			actionRequest.setAttribute("listaCatOrigenDestino", listaCatOrigenDestino.getLista());

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ cargaCatalogos:");
			e.printStackTrace();
		}

	}
	
	private void verificaListaAgentes(ActionRequest actionRequest, List<Persona> listaAgentes) {
		if (Validator.isNull(listaAgentes)) {
			SessionErrors.add(actionRequest, "errorConocido");
			actionRequest.setAttribute("errorMsg", "Error al cargar su información cierre sesion");
			SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
					+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
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
	
	private void consultaInfoTransportes(ActionRequest actionRequest) {
		
		DatosTransportesResponse dtResponse = null;
		
		try {
		dtResponse = _CMPaso2Transportes.consultaDatosRyMTransportes(infCot.getFolio(),
				infCot.getCotizacion(), infCot.getVersion(), user.getScreenName(), infCot.getPantalla(), idPerfilUser);
		}
		catch(CotizadorModularException cme) {
			cme.printStackTrace();
		}
		
		Gson gson = new Gson();
		
		String infoTransportesJson = CotizadorModularUtil.objtoJson(dtResponse);
		//String infoTransportesJson = gson.toJson(dtResponse, DatosTransportesResponse.class); 
		
		actionRequest.setAttribute("infoTransportes", dtResponse);
		actionRequest.setAttribute("infoTransportesJson", infoTransportesJson);
		
		try {
			idClausula=dtResponse.getCondicionesAseguramiento().getClausula();
		}catch(Exception e) {
			idClausula=0;
		}
	}

}
