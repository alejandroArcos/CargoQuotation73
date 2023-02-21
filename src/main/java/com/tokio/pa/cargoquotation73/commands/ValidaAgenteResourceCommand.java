package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorGenerico;

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
		"mvc.command.name=/cotizadores/validaAgente"
	},
	service = MVCResourceCommand.class
)

public class ValidaAgenteResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorGenerico _CMGenerico;
	
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		String auxCodigo = ParamUtil.getString(resourceRequest, "codigoAgente");
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		String codigoAgente = "";
		
		if(!auxCodigo.isEmpty()) {
			String auxCodAgente[] = auxCodigo.split("-");
			codigoAgente = auxCodAgente[0].trim();
		}
		
		SimpleResponse response = _CMGenerico.validaAgentes(cotizacion, codigoAgente,
				CargoQuotation73PortletKeys.CotizadorTransportistas);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(response);
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonString);
		
	}

}
