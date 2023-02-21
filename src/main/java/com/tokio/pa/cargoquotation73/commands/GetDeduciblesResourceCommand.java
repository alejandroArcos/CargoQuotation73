package com.tokio.pa.cargoquotation73.commands;

import java.io.PrintWriter;
import java.util.Comparator;

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
import com.tokio.pa.cotizadorModularServices.Bean.Deducible;
import com.tokio.pa.cotizadorModularServices.Bean.DeducibleResponse;
import com.tokio.pa.cotizadorModularServices.Bean.Registro;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;


@Component(
	 property = {
			 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
			 "mvc.command.name=/CotizadorTransportes/getDeducibles"
	 },
	 service = MVCResourceCommand.class
)

public class GetDeduciblesResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso3Transportes _CMSP3Transportes;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		Gson gson = new Gson();
		
		int p_cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int p_version = ParamUtil.getInteger(resourceRequest, "version");
		String p_pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String p_usuario = user.getScreenName();
		
		DeducibleResponse response = _CMSP3Transportes.getDeducibles("" + p_cotizacion, p_version, p_usuario, p_pantalla);
		
		if(response.getCode() == 0) {
			response.getDeduciblesAdicionales().sort(Comparator.comparing(Deducible::getIdDeducible));
		}
		
		String responseString = gson.toJson(response);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(responseString);
	}

}
