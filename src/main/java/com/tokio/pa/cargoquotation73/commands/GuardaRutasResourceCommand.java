package com.tokio.pa.cargoquotation73.commands;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.CoberturasAdicionalesTransportes;
import com.tokio.pa.cotizadorModularServices.Bean.CondicionesAseguramiento;
import com.tokio.pa.cotizadorModularServices.Bean.DatosTransportesResponse;
import com.tokio.pa.cotizadorModularServices.Bean.LimitesGenerales;
import com.tokio.pa.cotizadorModularServices.Bean.RutasResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		 property = {
		 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		 "mvc.command.name=/cotizadorTransportistas/GuardaRutas"
		 },
		 service = MVCResourceCommand.class
		 )

public class GuardaRutasResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
		Gson gson = new Gson();
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		String rutasString = ParamUtil.getString(resourceRequest, "rutas");
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		String folio = ParamUtil.getString(resourceRequest, "folio");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		String pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		JsonArray rutas = gson.fromJson(rutasString, JsonArray.class);
		
		RutasResponse rutasResponse = _CMPaso2Transportes.guardaRutas(folio, cotizacion, version, rutas, user.getScreenName(),
				pantalla);
		
		String jsonStringResponse = gson.toJson(rutasResponse);
		
		System.out.println(jsonStringResponse);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonStringResponse);
		
	}

}
