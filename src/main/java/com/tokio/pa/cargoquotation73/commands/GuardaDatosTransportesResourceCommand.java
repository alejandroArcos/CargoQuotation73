package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.CoberturasAdicionalesTransportes;
import com.tokio.pa.cotizadorModularServices.Bean.CondicionesAseguramiento;
import com.tokio.pa.cotizadorModularServices.Bean.DatosTransportesResponse;
import com.tokio.pa.cotizadorModularServices.Bean.LimitesGenerales;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		 property = {
		 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		 "mvc.command.name=/cotizadorTransportistas/GuardaDatosPaso2"
		 },
		 service = MVCResourceCommand.class
		 )

public class GuardaDatosTransportesResourceCommand extends BaseMVCResourceCommand {

	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
		Gson gson = new Gson();
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		String condicionesAseguramiento = ParamUtil.getString(resourceRequest, "condicionesAseguramiento");
		String limitesGenerales = ParamUtil.getString(resourceRequest, "limitesGenerales");
		String coberturasAdicionales = ParamUtil.getString(resourceRequest, "coberturasAdicionales");
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		String folio = ParamUtil.getString(resourceRequest, "folio");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		String pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		CondicionesAseguramiento condicionesAseg = gson.fromJson(condicionesAseguramiento, CondicionesAseguramiento.class);
		condicionesAseg.setBienesTransportar(condicionesAseg.getBienesTransportar().toUpperCase());
		LimitesGenerales limitesGral = gson.fromJson(limitesGenerales, LimitesGenerales.class);
		CoberturasAdicionalesTransportes coberturasAdic = gson.fromJson(coberturasAdicionales, CoberturasAdicionalesTransportes.class);
		
		DatosTransportesResponse dtResponse = _CMPaso2Transportes.guardaDatosRyMTransportes(folio, idPerfilUser, cotizacion, 
				version, pantalla, user.getScreenName(), condicionesAseg, limitesGral, coberturasAdic);
		
		String jsonStringResponse = gson.toJson(dtResponse);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonStringResponse);
		
	}

}
