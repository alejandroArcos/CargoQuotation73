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
import com.tokio.pa.cotizadorModularServices.Bean.ClausulaResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
					"mvc.command.name=/CotizadorTransportes/saveClausulasA" 
				},
		service = MVCResourceCommand.class)

public class SaveClausulasResourceCommand extends BaseMVCResourceCommand{
	@Reference
	CotizadorPaso3Transportes _CMServicesP3T;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		Gson gson = new Gson();
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		
		String clausulasA = ParamUtil.getString(resourceRequest, "clausulasArr");
		JsonArray p_clausulas = gson.fromJson(clausulasA, JsonArray.class);

		
//		_CMServicesP3.saveClausulasAdicionales(p_cotizacion, p_version, p_usuario, p_pantalla, p_clausulas);
		ClausulaResponse response = _CMServicesP3T.saveClausulasAdicionales(cotizacion, version, user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas, p_clausulas);
		
		PrintWriter writer = resourceResponse.getWriter();
		String responseString = gson.toJson(response);
		writer.write(responseString);
	}
}
