package com.tokio.pa.cargoquotation73.commands;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.PortalUtil;

import com.tokio.pa.cargoquotation73.constants.CargoQuotation73PortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + CargoQuotation73PortletKeys.CotizadorTransportistas,
			"mvc.command.name=/CotizadorTransportes/plantillaSlip"
		},
		service = MVCResourceCommand.class
	)

public class PlantillaSlipResourceCommand extends BaseMVCResourceCommand{
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		long idGroup = PortalUtil.getScopeGroupId(resourceRequest);
		
		DLFolder auxFolder = DLFolderLocalServiceUtil.fetchFolder(idGroup, 0, "Documentos_Cotizadores");
		
		List<DLFileEntry> listFiles = DLFileEntryLocalServiceUtil.getFileEntries(auxFolder.getGroupId(), auxFolder.getFolderId());
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write( getUrlFile( listFiles ) );
	}
	
	private String getUrlFile(List<DLFileEntry> listFiles){
		String url ="";
		try {
			for (DLFileEntry curFile : listFiles) {
				if(curFile.getFileName().equals("SlipSemiAutomatico.docx")){
					url = "/documents/" + curFile.getGroupId() + "/" + curFile.getFolderId() + "/" + curFile.getName() + "/" + curFile.getUuid();
					break;
				}
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Error al recorrer DLFolder");
		}
		return url;
	}
}
