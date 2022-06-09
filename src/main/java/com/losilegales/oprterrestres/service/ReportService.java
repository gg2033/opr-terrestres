package com.losilegales.oprterrestres.service;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.losilegales.oprterrestres.config.AppConfig;
import com.losilegales.oprterrestres.dto.VueloHistoricoDTO;
import com.losilegales.oprterrestres.dto.CheckIn.CargaPorTagDTO;
import com.losilegales.oprterrestres.dto.CheckIn.CargaPorTipoReporteDTO;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.repository.VueloHistoricoRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {
	@Autowired
	VueloRepository vueloRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CargaRepository cargaRepository;

	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate = new RestTemplate();

	private DozerBeanMapper mapper = AppConfig.dozerBeanMapper();

	@Autowired
	private VueloHistoricoRepository vueloHistoricoRepository;

	public ResponseEntity<byte[]> reporteCargaVueloDesdeFecha(String fechaHora) {
		try {
			Date fechaHoraDesde = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fechaHora);
			List<Object[]> cargasPorTag = cargaRepository.cantidadCargasPorTag(fechaHoraDesde);

			Integer cantidadVuelos = cargaRepository.cantidadVuelos();
			List<CargaPorTagDTO> cargaLst = new ArrayList<CargaPorTagDTO>();
			for (Object[] objects : cargasPorTag) {
				BigInteger cantidadCargaPorTag = (BigInteger) objects[1];
				int promedioCargaPorVuelo = (cantidadCargaPorTag.intValue() / cantidadVuelos);
				CargaPorTagDTO cargaTag = new CargaPorTagDTO(String.valueOf(objects[0]), promedioCargaPorVuelo);
				cargaLst.add(cargaTag);
			}

			// dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Reportes de Cargas");
			parameters.put("Author", "Cargas");
			parameters.put("FECHA_DESDE", fechaHora);

//			empParams.put("cargaData", new JRBeanCollectionDataSource(cargaLst));
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(cargaLst);

			JasperPrint empReport = JasperFillManager.fillReport(
					JasperCompileManager.compileReport(new FileInputStream("src/main/resources/cargas_por_tag.jrxml")) // path
																														// of
																														// the
																														// jasper
																														// report
					, parameters // dynamic parameters
					, beanColDataSource

			);

			HttpHeaders headers = new HttpHeaders();
			// set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "carga-reporte.pdf");
			// create the report in PDF format
			return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<byte[]> reportePorCodigoFechaVuelo(String codigoVuelo, String fechaHora) {
		try {
			Date fechaHoraDesde = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fechaHora);
			List<Object[]> cargasPorTag = cargaRepository.cantidadCargasPorTagCodigoFechaVuelo(codigoVuelo,
					fechaHoraDesde);

			Integer cantidadVuelos = cargaRepository.cantidadVuelos();
			List<CargaPorTagDTO> cargaLst = new ArrayList<CargaPorTagDTO>();
			for (Object[] objects : cargasPorTag) {
				BigInteger cantidadCargaPorTag = (BigInteger) objects[1];
				int promedioCargaPorVuelo = (cantidadCargaPorTag.intValue() / cantidadVuelos);
				CargaPorTagDTO cargaTag = new CargaPorTagDTO(String.valueOf(objects[0]), promedioCargaPorVuelo);
				cargaLst.add(cargaTag);
			}

			// dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Reportes de Cargas");
			parameters.put("Author", "Cargas");
			parameters.put("FECHA_DESDE", fechaHora);
			parameters.put("CODIGO_VUELO", codigoVuelo);
//			empParams.put("cargaData", new JRBeanCollectionDataSource(cargaLst));
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(cargaLst);

			JasperPrint empReport = JasperFillManager.fillReport(JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/cargas_por_tag_vuelo.jrxml")) // path of the
																											// jasper
																											// report
					, parameters // dynamic parameters
					, beanColDataSource

			);

			HttpHeaders headers = new HttpHeaders();
			// set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "carga-reporte.pdf");
			// create the report in PDF format
			return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<byte[]> reporteCargaPorVuelo(String codigoVuelo) {
		try {

			List<Object[]> cargaPorVueloLst = cargaRepository.cantidadTipoCargasPorVuelo(codigoVuelo);
			List<CargaPorTipoReporteDTO> cargaLst = new ArrayList<CargaPorTipoReporteDTO>();
			for (Object[] objects : cargaPorVueloLst) {
				CargaPorTipoReporteDTO cargaTag = new CargaPorTipoReporteDTO();
				BigInteger cantidadTipoCargaPorVuelo = (BigInteger) objects[1];
				cargaTag.setCantidad(cantidadTipoCargaPorVuelo.intValue());
				cargaTag.setTipo(String.valueOf(objects[0]));
				cargaLst.add(cargaTag);
			}

			// dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Reportes de Cargas");
			parameters.put("Author", "Cargas");
			parameters.put("CODIGO_VUELO", codigoVuelo);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(cargaLst);

			JasperPrint empReport = JasperFillManager.fillReport(JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/cargas_reporte_por_vuelo.jrxml")) // path of
																												// the
																												// jasper
																												// report
					, parameters // dynamic parameters
					, beanColDataSource

			);

			HttpHeaders headers = new HttpHeaders();
			// set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "carga-reporte.pdf");
			// create the report in PDF format
			return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<byte[]> getAeronavesMasUsadasTop3() {
		URI uri;
		try {
			uri = new URI(OprConstants.ENDPOINT_VUELOS_HISTORICOS_JSON);
			VueloHistoricoDTO[] vuelosHistoricos = simpleRestTemplate.getForObject(uri, VueloHistoricoDTO[].class);
			
			

			 Map<String, Long> counting = Arrays.asList(vuelosHistoricos).stream().filter(e-> e.getModeloaeronave()!=null)
				
					 .collect(
		                Collectors.groupingBy(VueloHistoricoDTO::getModeloaeronave, Collectors.counting()));

			List<CargaPorTipoReporteDTO> topNaveLst = new ArrayList<CargaPorTipoReporteDTO>();
			for (Entry<String, Long> entry : counting.entrySet()) {
				CargaPorTipoReporteDTO vueloHistorico = new CargaPorTipoReporteDTO();
				vueloHistorico.setTipo(entry.getKey());
				vueloHistorico.setCantidad(entry.getValue().intValue());
				topNaveLst.add(vueloHistorico);
			}
			
			List<CargaPorTipoReporteDTO> topNaveLstOrder =topNaveLst.stream().sorted(Comparator.comparingInt(CargaPorTipoReporteDTO::getCantidad).reversed())
			.collect(Collectors.toList());
			
			//dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Top de Naves mas usadas");
			parameters.put("Author", "Naves");
//			parameters.put("CODIGO_VUELO", codigoVuelo);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
					topNaveLstOrder);
			
			JasperPrint empReport =
					JasperFillManager.fillReport
				   (
							JasperCompileManager.compileReport(
									new FileInputStream("src/main/resources/top_naves.jrxml")
									) // path of the jasper report
							, parameters // dynamic parameters
							, beanColDataSource 
							
					);

			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "naves-top-reporte.pdf");
			//create the report in PDF format
			return new ResponseEntity<byte[]>
					(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
		}
		catch (Exception e ) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
