package com.tokio.pa.cargoquotation73.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;
import com.tokio.pa.cotizadorModularServices.Bean.CaratulaResponse;
import com.tokio.pa.cotizadorModularServices.Bean.CotizadorDataResponse;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Bean.ListaRegistro;
import com.tokio.pa.cotizadorModularServices.Bean.Registro;
import com.tokio.pa.cotizadorModularServices.Bean.RutasResponse;
import com.tokio.pa.cotizadorModularServices.Bean.ValidarReaseguroResponse;
import com.tokio.pa.cotizadorModularServices.Constants.CotizadorModularServiceKey;
import com.tokio.pa.cotizadorModularServices.Enum.ModoCotizacion;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorGenerico;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso1;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso2Transportes;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3;
import com.tokio.pa.cotizadorModularServices.Interface.CotizadorPaso3Transportes;
import com.tokio.pa.cotizadorModularServices.Util.CotizadorModularUtil;

import java.util.Base64;
import java.util.Comparator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		 property = {
		 "javax.portlet.name="+ CargoQuotation73PortletKeys.CotizadorTransportistas,
		 "mvc.command.name=/CotizadorTransportistas/SavePaso2"
		 },
		 service = MVCActionCommand.class
		 )

public class SavePaso2ActionCommand extends BaseMVCActionCommand {
	
	@Reference
	CotizadorPaso2Transportes _CMPaso2Transportes;
	
	@Reference
	CotizadorPaso3 _ServicePaso3;
	
	@Reference
	CotizadorPaso3Transportes _ServiceP3Transportes;
	
	@Reference
	CotizadorGenerico _ServiceGenerico;
	
	User user;
	int idPerfilUser;
	
	InfoCotizacion infCotizacion = new InfoCotizacion();
	CaratulaResponse caratulaResponse = new CaratulaResponse();
	
	@Reference
	CotizadorPaso1 _CMServicesP1;
	
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
		
		user = (User) actionRequest.getAttribute(WebKeys.USER);
		idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		
		String paso = ParamUtil.getString(actionRequest, "paso");
		String folioCoti = ParamUtil.getString(actionRequest, "folioCoti");
		String versionCoti = ParamUtil.getString(actionRequest, "versionCoti");
		String cotizacion = ParamUtil.getString(actionRequest, "cotizacion");
		
		/*
		infCotizacion.setFolio(Long.parseLong(folioCoti));
		infCotizacion.setVersion(Integer.parseInt(versionCoti));
		infCotizacion.setCotizacion(Long.parseLong(cotizacion));
		*/
		
		llenaInfoCotizacion(actionRequest);
		llenaCatalogos(actionRequest);
		validaModoCotizacion(actionRequest);
		fSetModoCotizacion( infCotizacion.getModo().toString(), actionRequest);
		
		
		ValidarReaseguroResponse response=_ServicePaso3.validarReaseguro((int)infCotizacion.getCotizacion(), infCotizacion.getVersion(), idPerfilUser, user.getScreenName(),infCotizacion.getPantalla());
		if(response.getCode()!=0) {
			SessionErrors.add(actionRequest, "reaseguroEliminado");
			actionRequest.setAttribute("reaseguroEliminadoMsg",response.getMsg());
			SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		
		String infoCotJson = CotizadorModularUtil.objtoJson(infCotizacion);
		
		actionRequest.setAttribute("paso", paso );
		actionRequest.setAttribute("folioCoti", folioCoti );
		actionRequest.setAttribute("versionCoti", versionCoti );
		actionRequest.setAttribute("cotizacion", cotizacion );
		actionRequest.setAttribute("infoCotJson", infoCotJson);
		actionRequest.setAttribute("infCotizacion", infCotizacion);
		
		actionRequest.setAttribute("mailUser", Base64.getEncoder().encodeToString(user.getEmailAddress().toString().getBytes()));
		caratulaResponse.setEmail(Base64.getEncoder().encodeToString(caratulaResponse.getEmail().toString().getBytes()));
		
		
		actionRequest.setAttribute("caratulaResponse", caratulaResponse);
		
		actionRequest.setAttribute("perfilSuscriptor", perfilSuscriptor());
		actionRequest.setAttribute("perfilJapones", perfilJapones());
		actionRequest.setAttribute("numeroRutas", getNumeroRutas());
		
//		actionResponse.setRenderParameter("jspPage", "/pantallas/paso3.jsp");
		actionResponse.getRenderParameters().setValue("jspPage", "/pantallas/paso3.jsp");
	}
	
	private int getNumeroRutas() {
		
		try {
		
			RutasResponse rutas = _CMPaso2Transportes.consultaRuta("" + infCotizacion.getFolio(), (int)infCotizacion.getCotizacion(), (int) infCotizacion.getVersion(),
					infCotizacion.getPantalla(), user.getScreenName());
			
			if(rutas.getCode() == 0) {
				return rutas.getLista().size();
			}
			else {
				return 0;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void llenaInfoCotizacion(ActionRequest actionRequest) {

		try {
			/*
			HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
			*/
			String inf = ParamUtil.getString(actionRequest, "infoCotizacion");
			System.out.println(inf);

			String nombreCotizador = "";
			if (Validator.isNotNull(inf)) {
				infCotizacion = CotizadorModularUtil.decodeURL(inf);
			} else {
				infCotizacion = new InfoCotizacion();
			}
			
			
//			auxRenovacion();

			System.out.println("-----------------------------------------");
			System.err.println("inf: " + infCotizacion.toString());

			nombreCotizador = "Cotizador Paquete Empresarial";
			actionRequest.setAttribute("tituloCotizador", nombreCotizador);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("------------------ llenaInfoCotizacion:");
			SessionErrors.add(actionRequest, "errorServicios");
			e.printStackTrace();
		}

	}
	
	private void llenaCatalogos(ActionRequest actionRequest){

		ListaRegistro listaMotivoRechazo = fGetCatalogos(
				CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
				CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
				CotizadorModularServiceKey.LIST_CAT_MOTI_RECHAZO,
				CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
				infCotizacion.getPantalla());
		
		ListaRegistro listaPrimaDeposito = fGetCatalogos(
				CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
				CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
				CotizadorModularServiceKey.LIST_CAT_PRIMA,
				CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
				infCotizacion.getPantalla());
		
		ListaRegistro listaCriterio = fGetCatalogos(
				CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
				CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
				CotizadorModularServiceKey.LIST_CAT_CRIT,
				CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
				infCotizacion.getPantalla());
		
		ListaRegistro listaUnidades = fGetCatalogos(
				CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
				CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
				CotizadorModularServiceKey.LIST_CAT_UNI,
				CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
				infCotizacion.getPantalla());
		
		ListaRegistro listaDias = fGetCatalogos(
				CotizadorModularServiceKey.TMX_CTE_ROW_TODOS,
				CotizadorModularServiceKey.TMX_CTE_TRANSACCION_GET,
				CotizadorModularServiceKey.LIST_CAT_DIAS,
				CotizadorModularServiceKey.TMX_CTE_CAT_ACTIVOS, user.getScreenName(),
				infCotizacion.getPantalla());
		
		try {
			CotizadorDataResponse respuesta = _CMServicesP1.getCotizadorData(infCotizacion.getFolio(),
					infCotizacion.getCotizacion(), infCotizacion.getVersion(),
					user.getScreenName(), CargoQuotation73PortletKeys.CotizadorTransportistas);
			if(respuesta.getDatosCotizacion().getMoneda()==1) {
				listaUnidades.getLista().removeIf(n -> (n.getIdCatalogoDetalle()== 6474));
			}else {
				listaUnidades.getLista().removeIf(n -> (n.getIdCatalogoDetalle()== 6475));
			}
		}catch(Exception e) {
			
		}

		actionRequest.setAttribute("motivoRechazo", listaMotivoRechazo.getLista());
		actionRequest.setAttribute("primaDeposito", listaPrimaDeposito.getLista());
		actionRequest.setAttribute("listaCriterio", listaCriterio.getLista());
		actionRequest.setAttribute("listaUnidades", listaUnidades.getLista());
		actionRequest.setAttribute("listaDias", CotizadorModularUtil
				.objtoJson(listaDias));
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(listaUnidades, ListaRegistro.class);
		JsonObject jsonAux = gson.fromJson(jsonString, JsonObject.class);
		actionRequest.setAttribute("catalogoUnidades", jsonAux);
		
		jsonString = gson.toJson(listaCriterio, ListaRegistro.class);
		jsonAux = gson.fromJson(jsonString, JsonObject.class);
		actionRequest.setAttribute("catalogoCriterios", jsonAux);
			
	}
	
	
	private ListaRegistro fGetCatalogos(int p_rownum, String p_tiptransaccion, String p_codigo,
			int p_activo, String p_usuario, String p_pantalla) {
		try {
			ListaRegistro list = _ServiceGenerico.getCatalogo(p_rownum, p_tiptransaccion, p_codigo,
					p_activo, p_usuario, p_pantalla);
			list.getLista().sort(Comparator.comparing(Registro::getDescripcion));
			return list;
			/* return null; */
		} catch (Exception e) {
			return null;
		}
	}
	
	private void fGetCaratula(ActionRequest actionRequest) {
		try {
			String cur_version = String.valueOf(infCotizacion.getVersion());
			caratulaResponse = _ServiceP3Transportes.getCaratula((int) infCotizacion.getCotizacion(),
					cur_version, user.getScreenName(), CargoQuotation73PortletKeys.PANTALLA);
			// if caratulaResponse.getPrimaNeta()
		} catch (Exception e) {
			// TODO: handle exception
			caratulaResponse = new CaratulaResponse();
		}
	}
	
	private void fGetCalculoTransportes(ActionRequest actionRequest) {
		try {
			_ServiceP3Transportes.getCalculoTransportes(
					(int) infCotizacion.getCotizacion(), infCotizacion.getVersion(), user.getScreenName(),
					CargoQuotation73PortletKeys.PANTALLA);
			
			fGetCaratula(actionRequest);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void validaModoCotizacion(ActionRequest actionRequest) {
		switch (infCotizacion.getModo()) {
			case FACTURA_492:
				actionRequest.setAttribute("Leg492", "factura");
				fGetCaratula(actionRequest);
				break;
			case ALTA_ENDOSO:
				actionRequest.setAttribute("dBtns", "d-none");
				fGetCaratula(actionRequest);
				break;
			case EDITAR_ALTA_ENDOSO:
				actionRequest.setAttribute("dBtns", "d-none");
				fGetCaratula(actionRequest);
				break;
			case BAJA_ENDOSO:
				actionRequest.setAttribute("dBtns", "d-none");
				//caratulaBajaEnsos(actionRequest);
				break;
			case EDITAR_BAJA_ENDOSO:
				actionRequest.setAttribute("dBtns", "d-none");
				//caratulaBajaEnsos(actionRequest);
				break;
			case RENOVACION_AUTOMATICA:
				actionRequest.setAttribute("dBtns", "d-none");
				//actualizaInfoRenovacion();
				//recuperaInfoPaso1(actionRequest);
				fGetCaratula(actionRequest);
				break;
			case EDITAR_RENOVACION_AUTOMATICA:
				actionRequest.setAttribute("dBtns", "d-none");
				infCotizacion.setModo(ModoCotizacion.RENOVACION_AUTOMATICA);
				fGetCaratula(actionRequest);
			case CONSULTAR_RENOVACION_AUTOMATICA:
				actionRequest.setAttribute("dBtns", "d-none");
				fGetCaratula(actionRequest);
			case CONSULTA:
				fGetCaratula(actionRequest);
				break;
			case NUEVA:
			case EDICION:
			case EDICION_JAPONES:
				fGetCalculoTransportes(actionRequest);		
				break;
			default:
				fGetCaratula(actionRequest);
				break;
		}
			
	}
	
	private int perfilJapones() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_JAPONES:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private int perfilSuscriptor() {
		try {
			switch (idPerfilUser) {
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORJR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORSR:
					return 1;
				case CargoQuotation73PortletKeys.PERFIL_SUSCRIPTORMR:
					return 1;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void fSetModoCotizacion(String modoCotizacion, ActionRequest actionRequest) {
		
		switch (modoCotizacion) {
		case "NUEVA":
			infCotizacion.setModo(ModoCotizacion.NUEVA);
			break;
		case "EDICION":
			infCotizacion.setModo(ModoCotizacion.EDICION);
			break;
		case "COPIA":
			infCotizacion.setModo(ModoCotizacion.COPIA);
			break;
		case "AUX_PASO4":
			infCotizacion.setModo(ModoCotizacion.AUX_PASO4);
			break;
		case "ALTA_ENDOSO":
			infCotizacion.setModo(ModoCotizacion.ALTA_ENDOSO);
			break;
		case "BAJA_ENDOSO":
			infCotizacion.setModo(ModoCotizacion.BAJA_ENDOSO);
			break;
		case "EDITAR_ALTA_ENDOSO":
			infCotizacion.setModo(ModoCotizacion.EDITAR_ALTA_ENDOSO);
			break;
		case "EDITAR_BAJA_ENDOSO":
			infCotizacion.setModo(ModoCotizacion.EDITAR_BAJA_ENDOSO);
			break;
		case "CONSULTA":
			infCotizacion.setModo(ModoCotizacion.CONSULTA);
			break;
		case "FACTURA_492":
			infCotizacion.setModo(ModoCotizacion.FACTURA_492);
			actionRequest.setAttribute("Leg492", "factura");
			break;
		case "REVIRE":
			infCotizacion.setModo(ModoCotizacion.REVIRE);
			break;
		case "ERROR":
			infCotizacion.setModo(ModoCotizacion.ERROR);
			break;
		case "RENOVACION_AUTOMATICA":
			infCotizacion.setModo(ModoCotizacion.RENOVACION_AUTOMATICA);
			break;
		case "EDITAR_RENOVACION_AUTOMATICA":
			infCotizacion.setModo(ModoCotizacion.EDITAR_RENOVACION_AUTOMATICA);
			break;
		case "CONSULTAR_RENOVACION_AUTOMATICA":
			infCotizacion.setModo(ModoCotizacion.CONSULTAR_RENOVACION_AUTOMATICA);
			break;
		case "EDICION_JAPONES":
			infCotizacion.setModo(ModoCotizacion.EDICION_JAPONES);
			break;
		case "CONSULTAR_REVISION":
			infCotizacion.setModo(ModoCotizacion.CONSULTAR_REVISION);
			break;
		case "REASEGURO":
			infCotizacion.setModo(ModoCotizacion.EDICION);
			break;
		case "REASEGURO_CONSULTA":
			if(caratulaResponse.getEstado()==362) {
				infCotizacion.setModo(ModoCotizacion.CONSULTA_VOBO_REASEGURO);
			}else {
				infCotizacion.setModo(ModoCotizacion.CONSULTA);	
			}
			break;
		case "COASEGURO":
			infCotizacion.setModo(ModoCotizacion.COASEGURO);
			break;
		case "COASEGURO_CONSULTA":
			if(caratulaResponse.getEstado()==362) {
				infCotizacion.setModo(ModoCotizacion.CONSULTA_VOBO_REASEGURO);
			}else {
				infCotizacion.setModo(ModoCotizacion.CONSULTA);
			}
			break;
		case "VOBO_REASEGURO":
			infCotizacion.setModo(ModoCotizacion.VOBO_REASEGURO);
			break;
		case "CONSULTA_VOBO_REASEGURO":
			infCotizacion.setModo(ModoCotizacion.CONSULTA_VOBO_REASEGURO);
			break;
		default:
			infCotizacion.setModo(ModoCotizacion.ERROR);
			break;
		}
	}
}
