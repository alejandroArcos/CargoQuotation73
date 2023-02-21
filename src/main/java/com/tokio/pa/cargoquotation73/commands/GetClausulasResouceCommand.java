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
import com.tokio.pa.cotizadorModularServices.Bean.ClausulaResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
			"mvc.command.name=/cotizadores/paso3/getClausulas"
		},
		service = MVCResourceCommand.class
	)

public class GetClausulasResouceCommand extends BaseMVCResourceCommand{
	@Reference
	CotizadorPaso3Transportes _CMSP3T;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
//		HttpServletRequest originalRequest = PortalUtil
//				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));

//		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		Gson gson = new Gson();
		
		int p_cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int p_version = ParamUtil.getInteger(resourceRequest, "version");
		String p_pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String p_usuario = user.getScreenName();
		
		 ClausulaResponse response = _CMSP3T.getClausulasAdicionales(p_cotizacion, p_version, p_usuario, p_pantalla);
		
		String responseString = gson.toJson(response);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(responseString);
	}
}
