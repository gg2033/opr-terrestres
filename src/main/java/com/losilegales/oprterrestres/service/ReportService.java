package com.losilegales.oprterrestres.service;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.dto.CheckIn.CargaPorTagDTO;
import com.losilegales.oprterrestres.dto.CheckIn.CargaPorTipoReporteDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;

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
	
	public ResponseEntity<byte[]> reporteCarga(){
		try {
			// create carga data
//			Employee emp1 = new Employee(1, "AAA", "BBB", "A city");
//			Employee emp2 = new Employee(2, "XXX", "ZZZ", "B city");
			List<Object[]> cargasPorTag = cargaRepository.cantidadCargasPorTag();
			Integer cantidadVuelos = cargaRepository.cantidadVuelos();
			List<CargaPorTagDTO> cargaLst = new ArrayList<CargaPorTagDTO>();
			for (Object[] objects : cargasPorTag) {
				BigInteger cantidadCargaPorTag = (BigInteger) objects[1];
				int promedioCargaPorVuelo = (cantidadCargaPorTag.intValue()/cantidadVuelos);
				CargaPorTagDTO cargaTag = new CargaPorTagDTO(String.valueOf(objects[0]), promedioCargaPorVuelo);
				cargaLst.add(cargaTag);
			}
			

			//dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Reportes de Cargas");
			parameters.put("Author", "Cargas");
//			empParams.put("cargaData", new JRBeanCollectionDataSource(cargaLst));
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
					cargaLst);
			
			JasperPrint empReport =
					JasperFillManager.fillReport
				   (
							JasperCompileManager.compileReport(
									new FileInputStream("src/main/resources/cargas_por_tag.jrxml")
									) // path of the jasper report
							, null // dynamic parameters
							, beanColDataSource 
							
					);

			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "carga-reporte.pdf");
			//create the report in PDF format
			return new ResponseEntity<byte[]>
					(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

		} catch(Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	public ResponseEntity<byte[]> reporteCargaPorVuelo(String codigoVuelo){
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
			
			
			//dynamic parameters required for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Reportes de Cargas");
			parameters.put("Author", "Cargas");
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
					cargaLst);
			
			JasperPrint empReport =
					JasperFillManager.fillReport
				   (
							JasperCompileManager.compileReport(
									new FileInputStream("src/main/resources/cargas_reporte_por_vuelo.jrxml")
									) // path of the jasper report
							, null // dynamic parameters
							, beanColDataSource 
							
					);

			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "carga-reporte.pdf");
			//create the report in PDF format
			return new ResponseEntity<byte[]>
					(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

		} catch(Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
