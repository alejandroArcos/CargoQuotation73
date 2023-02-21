package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
		"mvc.command.name=/cotizadores/paso3/guardaSiniestralidad"
	},
	service = MVCResourceCommand.class
)

public class GuardaSiniestralidadResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso3Transportes _CMSPaso3Transportes;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		Gson gson = new Gson();
		
		int p_cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int p_version = ParamUtil.getInteger(resourceRequest, "version");
		String p_dividendo = ParamUtil.getString(resourceRequest, "p_dividendo");
		double p_sMonto = ParamUtil.getDouble(resourceRequest, "p_sMonto");
		double p_sNumero = ParamUtil.getDouble(resourceRequest, "p_sNumero");
		double p_sPorcentaje = ParamUtil.getDouble(resourceRequest, "p_sPorcentaje");
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String p_usuario = user.getScreenName();
		
		SimpleResponse response = _CMSPaso3Transportes.getSiniestralidadDividendo(p_cotizacion,
				p_version, p_dividendo, p_sMonto, p_sNumero, p_sPorcentaje, p_usuario, 
				CargoQuotation73PortletKeys.CotizadorTransportistas);
		
		String responseString = gson.toJson(response);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(responseString);
	}

}
