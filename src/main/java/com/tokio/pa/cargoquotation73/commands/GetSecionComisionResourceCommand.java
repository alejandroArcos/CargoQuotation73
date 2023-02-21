package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Exception.CotizadorModularException;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;

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
		"mvc.command.name=/CotizadorTransportes/getSeccionComisionUrl"
	},
	service = MVCResourceCommand.class
)

public class GetSecionComisionResourceCommand extends BaseMVCResourceCommand{
	
	@Reference
	CotizadorPaso3 _ServicePaso3;
	
	@Override
    protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		try {
			double sc = ParamUtil.getDouble(resourceRequest, "seccomi");
			int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
			String version = ParamUtil.getString(resourceRequest, "version");
			User user = (User) resourceRequest.getAttribute(WebKeys.USER);
			String p_usuario = user.getScreenName();

			SimpleResponse respuesta = _ServicePaso3.getSecionComision(cotizacion, version, sc, p_usuario, CargoQuotation73PortletKeys.CotizadorTransportistas);

			Gson gson = new Gson();
			String jsonString = gson.toJson(respuesta);
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(jsonString);

		} catch (CotizadorModularException e) {
			// TODO Auto-generated catch block
			PrintWriter writer = resourceResponse.getWriter();
			String jsonString = "{\"code\" : \"5\", \"msg\" : \"Error al consultar la información\" }";
			writer.write(jsonString);
		}
	}
}