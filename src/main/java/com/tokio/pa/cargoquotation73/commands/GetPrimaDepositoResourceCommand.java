package com.tokio.pa.cargoquotation73.commands;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.CaratulaResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
		"mvc.command.name=/cotizadores/paso3/recalculoPrimaDeposito"
	},
	service = MVCResourceCommand.class
)

public class GetPrimaDepositoResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso3Transportes _CMSPaso3Transportes;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		Gson gson = new Gson();
		
		int p_cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int p_version = ParamUtil.getInteger(resourceRequest, "version");
		int p_primaDeposito = ParamUtil.getInteger(resourceRequest, "primaDeposito");
		String p_pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String p_usuario = user.getScreenName();
		
		double p_primaObjetivo = ParamUtil.getDouble(resourceRequest, "primaObjetivo");
		double p_gastos = ParamUtil.getDouble(resourceRequest, "gastos");
		double p_recargoPago = ParamUtil.getDouble(resourceRequest, "recargoPago");
		
		
		CaratulaResponse response = _CMSPaso3Transportes.getPrimaDeposito(p_cotizacion, p_version, p_primaDeposito, p_primaObjetivo, p_recargoPago, p_gastos,
				p_usuario, p_pantalla);
		
		String responseString = gson.toJson(response);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(responseString);
	}

}
