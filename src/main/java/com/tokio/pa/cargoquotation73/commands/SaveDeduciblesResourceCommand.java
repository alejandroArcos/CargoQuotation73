package com.tokio.pa.cargoquotation73.commands;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.DeducibleResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
					"mvc.command.name=/CotizadorTransportes/Deducibles" 
				},
		service = MVCResourceCommand.class)

public class SaveDeduciblesResourceCommand extends BaseMVCResourceCommand{
	
	@Reference
	CotizadorPaso3 _CMServicesP3;
	
	@Reference
	CotizadorPaso3Transportes _CMSP3Transportes;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		Gson gson = new Gson();
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		
		String p_deduciblesFijo = ParamUtil.getString(resourceRequest, "p_deduciblesFijo");
		String p_deduciblesLibre = ParamUtil.getString(resourceRequest, "p_deduciblesLibre");
		
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		
		JsonArray deduciblesFijo = gson.fromJson(p_deduciblesFijo, JsonArray.class);
		JsonArray deduciblesLibre = gson.fromJson(p_deduciblesLibre, JsonArray.class);

		
		//		_CMServicesP3.guardaDeducibles(p_cotizacion, p_version, p_usuario, p_pantalla, p_deduciblesFijo, p_deduciblesLibre);
		//DeducibleResponse response = _CMServicesP3.guardaDeducibles(cotizacion, version, user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas, deduciblesFijo, deduciblesLibre);
		
		DeducibleResponse response = _CMSP3Transportes.guardaDeducibles(cotizacion, version, deduciblesFijo, deduciblesLibre, user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
		
		PrintWriter writer = resourceResponse.getWriter();
		String responseString = gson.toJson(response);
		writer.write(responseString);
	}
	
}
