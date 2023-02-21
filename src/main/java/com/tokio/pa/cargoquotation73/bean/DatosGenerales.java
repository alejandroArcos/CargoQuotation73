package com.tokio.pa.cargoquotation73.bean;

import com.tokio.pa.cotizadorModularServices.Enum.ModoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.TipoCotizacion;

public class DatosGenerales {
	int tipomov;
	int vigencia;
	String fecinicio;
	String fecfin;
	int moneda;
	int formapago;
	int agente;
	int idPersona;
	int tipoPer;
	int extranjero;
	String rfc;
	String nombre;
	String appPaterno;
	String appMaterno;
	int idDenominacion;
	String codigo;
	ModoCotizacion modo;
	TipoCotizacion tipoCot;
	int cotizacion;
	int version;
	String giro;
	int subGiro;
	int noUbicaciones;
	String folio;
	String detalleSubGiro;
	String usuario;
	String pantalla;
	int idPerfil;
	int p_permisoSubgiro;
	String subEstado;
	int canalN;
	int tipoCoaseguro;
	int sector;
	
	public int getSector() {
		return sector;
	}
	public void setSector(int sector) {
		this.sector = sector;
	}
	public int getTipomov() {
		return tipomov;
	}
	public void setTipomov(int tipomov) {
		this.tipomov = tipomov;
	}
	public int getVigencia() {
		return vigencia;
	}
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}
	public String getFecinicio() {
		return fecinicio;
	}
	public void setFecinicio(String fecinicio) {
		this.fecinicio = fecinicio;
	}
	public String getFecfin() {
		return fecfin;
	}
	public void setFecfin(String fecfin) {
		this.fecfin = fecfin;
	}
	public int getMoneda() {
		return moneda;
	}
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}
	public int getFormapago() {
		return formapago;
	}
	public void setFormapago(int formapago) {
		this.formapago = formapago;
	}
	public int getAgente() {
		return agente;
	}
	public void setAgente(int agente) {
		this.agente = agente;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getTipoPer() {
		return tipoPer;
	}
	public void setTipoPer(int tipoPer) {
		this.tipoPer = tipoPer;
	}
	public int getExtranjero() {
		return extranjero;
	}
	public void setExtranjero(int extranjero) {
		this.extranjero = extranjero;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAppPaterno() {
		return appPaterno;
	}
	public void setAppPaterno(String appPaterno) {
		this.appPaterno = appPaterno;
	}
	public String getAppMaterno() {
		return appMaterno;
	}
	public void setAppMaterno(String appMaterno) {
		this.appMaterno = appMaterno;
	}
	public int getIdDenominacion() {
		return idDenominacion;
	}
	public void setIdDenominacion(int idDenominacion) {
		this.idDenominacion = idDenominacion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ModoCotizacion getModo() {
		return modo;
	}
	public void setModo(ModoCotizacion modo) {
		this.modo = modo;
	}
	public TipoCotizacion getTipoCot() {
		return tipoCot;
	}
	public void setTipoCot(TipoCotizacion tipoCot) {
		this.tipoCot = tipoCot;
	}
	public int getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(int cotizacion) {
		this.cotizacion = cotizacion;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public int getSubGiro() {
		return subGiro;
	}
	public void setSubGiro(int subGiro) {
		this.subGiro = subGiro;
	}
	public int getNoUbicaciones() {
		return noUbicaciones;
	}
	public void setNoUbicaciones(int noUbicaciones) {
		this.noUbicaciones = noUbicaciones;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getDetalleSubGiro() {
		return detalleSubGiro;
	}
	public void setDetalleSubGiro(String detalleSubGiro) {
		this.detalleSubGiro = detalleSubGiro;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPantalla() {
		return pantalla;
	}
	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}
	public int getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	public int getP_permisoSubgiro() {
		return p_permisoSubgiro;
	}
	public void setP_permisoSubgiro(int p_permisoSubgiro) {
		this.p_permisoSubgiro = p_permisoSubgiro;
	}
	public String getSubEstado() {
		return subEstado;
	}
	public void setSubEstado(String subEstado) {
		this.subEstado = subEstado;
	}
	public int getCanalN() {
		return canalN;
	}
	public void setCanalN(int canalN) {
		this.canalN = canalN;
	}
	public int getTipoCoaseguro() {
		return tipoCoaseguro;
	}
	public void setTipoCoaseguro(int tipoCoaseguro) {
		this.tipoCoaseguro = tipoCoaseguro;
	}
	@Override
	public String toString() {
		return "DatosGenerales [tipomov=" + tipomov + ", vigencia=" + vigencia + ", fecinicio=" + fecinicio
				+ ", fecfin=" + fecfin + ", moneda=" + moneda + ", formapago=" + formapago + ", agente=" + agente
				+ ", idPersona=" + idPersona + ", tipoPer=" + tipoPer + ", extranjero=" + extranjero + ", rfc=" + rfc
				+ ", nombre=" + nombre + ", appPaterno=" + appPaterno + ", appMaterno=" + appMaterno
				+ ", idDenominacion=" + idDenominacion + ", codigo=" + codigo + ", modo=" + modo + ", tipoCot="
				+ tipoCot + ", cotizacion=" + cotizacion + ", version=" + version + ", giro=" + giro + ", subGiro="
				+ subGiro + ", noUbicaciones=" + noUbicaciones + ", folio=" + folio + ", detalleSubGiro="
				+ detalleSubGiro + ", usuario=" + usuario + ", pantalla=" + pantalla + ", idPerfil=" + idPerfil
				+ ", p_permisoSubgiro=" + p_permisoSubgiro + ", subEstado=" + subEstado + ", canalN=" + canalN
				+ ", tipoCoaseguro=" + tipoCoaseguro + "]";
	}
	
	
	
}
