package com.tokio.pa.cargoquotation73.commands;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.AutoCompleteResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
					 "mvc.command.name=/cotizadorTransportistas/origendestino" },
		service = MVCResourceCommand.class
)

public class OrigDestAutocompleteResourceCommand extends BaseMVCResourceCommand{
	
	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String usuario = themeDisplay.getUser().getScreenName();
			String pantalla = CargoQuotation73PortletKeys.CotizadorTransportistas;
			String query = ParamUtil.getString(resourceRequest, "query");
			
			AutoCompleteResponse ciudades = _CMPaso2Transportes.autocompletadoLugar(query, usuario, pantalla);
			
			Gson gson = new Gson();
			resourceResponse.getWriter().write(gson.toJson(ciudades));
		}catch(Exception e){
			e.printStackTrace();
			Gson gson = new Gson();
			JsonObject response = new JsonObject();
			response.addProperty("code", 3);
			response.addProperty("msg", "Error desconocido en la aplicacion");

			resourceResponse.getWriter().write(gson.toJson(response));

		}
	}
}
