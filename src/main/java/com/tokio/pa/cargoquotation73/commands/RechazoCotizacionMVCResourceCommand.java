package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
					 "mvc.command.name=/cotizadores/paso3/rechazoCotizacion" },
		service = MVCResourceCommand.class
)

public class RechazoCotizacionMVCResourceCommand extends BaseMVCResourceCommand{
	@Reference
	CotizadorPaso3 _ServicePaso3;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		String cotizacion = ParamUtil.getString(resourceRequest, "cotizacion");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		int motivoRechazo = ParamUtil.getInteger(resourceRequest, "motivoRechazo");
		int boton = ParamUtil.getInteger(resourceRequest, "boton");
		String comentario = ParamUtil.getString(resourceRequest, "motivo");
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		String pantalla = CargoQuotation73PortletKeys.PANTALLA;

		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));

		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		SimpleResponse simpleResponse = _ServicePaso3.rechazaCotizacion(cotizacion, version, motivoRechazo, comentario, usuario, pantalla, idPerfilUser, boton);
		Gson gson = new Gson();
		String stringJson = gson.toJson(simpleResponse);
		resourceResponse.getWriter().write(stringJson);
	}
}
