package com.tokio.pa.cargoquotation73.commands;

/**
 * 
 */

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.PersonaResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso1;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
		immediate = true, property = { 
				"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas, 
				"mvc.command.name=/cotizadores/paso1/listaPersonas" }, service = MVCResourceCommand.class
)
public class GetListaPersonasResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso1 _CMServicesP1;


	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		// TODO Auto-generated method stub
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);

		String usuario = user.getScreenName();
		String pantalla = ParamUtil.getString(resourceRequest, "pantalla");;
		String nombreCliente = ParamUtil.getString(resourceRequest, "term");
		int tipo = ParamUtil.getInteger(resourceRequest, "tipo");

		Gson gson = new Gson();
		PrintWriter writer = resourceResponse.getWriter();
		try {
			PersonaResponse respuesta = _CMServicesP1.getListaPersonas(nombreCliente, tipo, usuario, pantalla);

			if (respuesta.getCode() == 0) {

				String jsonString = gson.toJson(respuesta.getPersonas());
				writer.write(jsonString);
			} else {
				writer.write("{\"codigo\" : \"0\", \"error\" : \"sin informacion\" }");
			}

		} catch (Exception e) {
			writer.write("{\"codigo\" : \"0\", \"error\" : \"sin informacion\" }");
		}

	}

}
