package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cotizadorModularServices.Bean.ValidarReaseguroResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		property = {
			"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
			"mvc.command.name=/cotizadores/paso3/validarReaseguro"
		},
		service = MVCResourceCommand.class
)
public class ValidarReaseguroResourceCommand  extends BaseMVCResourceCommand{
	
	private static final Log _log = LogFactoryUtil.getLog(ValidarReaseguroResourceCommand.class);
	@Reference
	CotizadorPaso3 _ServicePaso3;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		int p_cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		int p_version = ParamUtil.getInteger(resourceRequest, "version");
		String p_pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String p_usuario = user.getScreenName();
		
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));

		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		ValidarReaseguroResponse response=_ServicePaso3.validarReaseguro(p_cotizacion,p_version, idPerfilUser, p_usuario,p_pantalla);
	
		Gson gson = new Gson();
		
		PrintWriter writer = resourceResponse.getWriter();
		String responseString = gson.toJson(response);
		writer.write(responseString);
	}
}
