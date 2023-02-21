package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.SlipResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
			"mvc.command.name=/CotizadorTransportes/generarSlip"
		},
		service = MVCResourceCommand.class
	)

public class GenerarSlipResourceCommand extends BaseMVCResourceCommand {

	@Reference
	CotizadorPaso3Transportes _ServicePaso3;
	
	@Reference
	CotizadorPaso3 _ServicePaso3Generico;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		String usuario = "";
		String cotizacion = ParamUtil.getString(resourceRequest, "cotizacion");
		int version = ParamUtil.getInteger(resourceRequest, "version");
		String pantalla = ParamUtil.getString(resourceRequest, "pantalla");
		int word = ParamUtil.getInteger(resourceRequest, "word");
		int ingles = ParamUtil.getInteger(resourceRequest, "ingles");
		
		try {
			User user = (User) resourceRequest.getAttribute(WebKeys.USER);
			usuario = user.getScreenName();
		} catch(Exception e){
			SessionErrors.add(resourceRequest, "errorUsuario");
			SessionMessages.add(resourceRequest, PortalUtil.getPortletId(resourceRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		
		
		
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		
		
		try {
			/*SimpleResponse responseValidaciones = _ServicePaso3Generico.validacionesPaso3(Integer
					.parseInt(cotizacion), version, idPerfilUser, usuario, pantalla);
			
			if(responseValidaciones.getCode() == 0) {
			*/
				SlipResponse response = fGetSlip(cotizacion, version, usuario, pantalla, word, ingles);
				
				Gson gson = new Gson();
				String jsonString = gson.toJson(response);
				
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(jsonString);
			/*
			}
			else {
				Gson gson = new Gson();
				String jsonString = gson.toJson(responseValidaciones);
				
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(jsonString);
			}
			*/
		}
		catch(Exception e) {
			
		}
	}
	
	private SlipResponse fGetSlip(String cotizacion, int version, String usuario, String pantalla,
			int p_word, int p_ingles) {
		try {
			return _ServicePaso3.getSlip(cotizacion, version, usuario, pantalla, p_word, p_ingles);
			/*return null;*/
		} catch (Exception e) {
			/* TODO Auto-generated catch block	*/
			return null;
		}
	}

}
