package com.tokio.pa.cargoquotation73.commands;


import com.google.gson.Gson;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cargoquotation73.bean.DatosGenerales;
import com.tokio.pa.cargoquotation73.bean.Paso1;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Bean.SimpleResponse;
import com.tokio.pa.cotizadorModularServices.Bean.SolicitudesAutomaticasResponse;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorGenerico;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso1Transportes;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = {
		"javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		"mvc.command.name=/CotizadorTransportistas/GuardaPaso1"
	},
	service = MVCResourceCommand.class
)

public class GuardaPaso1ResourseCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorPaso1Transportes _CMPaso1Transportes;
	
	@Reference
	CotizadorGenerico _CMServicesGen;

	InfoCotizacion infCot;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		
		Gson gson = new Gson();
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
		String datos = ParamUtil.getString(resourceRequest, "datos");
		String infoCot = ParamUtil.getString(resourceRequest, "infoCot");
		
		DatosGenerales dg = gson.fromJson(datos, DatosGenerales.class);
		infCot = gson.fromJson(infoCot, InfoCotizacion.class);
		
		System.out.println("DatosGenerales:" +dg);
		System.out.println("InfoCotizacion:" + infCot);
		
		User user = (User) resourceRequest.getAttribute(WebKeys.USER);
		String usuario = user.getScreenName();
		int idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		int modo = (dg.getModo().getModoCotizacion() >= 3) ? 1
				: dg.getModo().getModoCotizacion();
		
		SimpleResponse abc = _CMPaso1Transportes.guardarCotizacionTransportes(dg.getTipomov(),
				idPerfilUser, dg.getFecinicio(), dg.getFecfin(), dg.getMoneda(), dg.getFormapago(),
				dg.getVigencia(), dg.getAgente(), dg.getIdPersona(), dg.getTipoPer(), dg.getRfc(),
				dg.getNombre(), dg.getAppPaterno(), dg.getAppMaterno(), dg.getIdDenominacion(),
				dg.getCodigo(), dg.getExtranjero(), dg.getCanalN(), modo, dg.getCotizacion(),
				dg.getVersion(), dg.getGiro(), dg.getFolio(), dg.getTipoCoaseguro(), dg.getSector(),
				usuario, dg.getPantalla());
		
		if(Validator.isNotNull(infCot.getSolicitud())) {
			
			SolicitudesAutomaticasResponse respuestaAsociacion = _CMServicesGen
					.asociaCotizacionSolicitud(infCot.getFolio() + "", infCot.getSolicitud(),
					user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
		}
		
		
		Paso1 paso1Object = new Paso1();
		paso1Object.setDac_coaseguro( ParamUtil.getString(resourceRequest, "coaseguro"));
		
		
		String jsonString = gson.toJson(abc);
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonString);

	}
	
	
}
