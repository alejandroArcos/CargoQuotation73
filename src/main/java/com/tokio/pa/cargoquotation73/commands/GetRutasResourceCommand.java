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
import com.tokio.pa.cotizadorModularServices.Bean.RutasResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		 property = {
		 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		 "mvc.command.name=/cotizadorTransportistas/getRutasPaso2"
		 },
		 service = MVCResourceCommand.class
		 )

public class GetRutasResourceCommand extends BaseMVCResourceCommand {

	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		Gson gson = new Gson();
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		String folio = ParamUtil.getString(resourceRequest, "folio");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		String pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		RutasResponse rutas = _CMPaso2Transportes.consultaRuta(folio, cotizacion, version, pantalla, user.getScreenName());
		
		String jsonStringResponse = gson.toJson(rutas);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonStringResponse);
		
	}

}
