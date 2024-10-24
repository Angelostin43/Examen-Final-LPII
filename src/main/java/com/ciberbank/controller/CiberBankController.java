package com.ciberbank.controller;

import java.io.OutputStream;
import java.sql.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ciberbank.interfaces.ISolicitudRepository;
import com.ciberbank.interfaces.ITipoRepository;
import com.ciberbank.model.Solicitud;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class CiberBankController {

	@Autowired
	private ITipoRepository repoTip;

	@Autowired
	private ISolicitudRepository repoSol;

	@GetMapping("/solicitud")
	public String cargarSoli(Model model) {
		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("lstSol", repoSol.findAll());
		model.addAttribute("lstTipos", repoTip.findAll());
		return "solicitudLlanos";
	}

	@PostMapping("/grabSoli")
	public String grabSoli(@ModelAttribute Solicitud solicitud, Model model) {
		try {
			repoSol.save(solicitud);
			model.addAttribute("mensaje", "La solicitud ah sido registrada correctamente");
			model.addAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al registrar solicitud");
			model.addAttribute("cssmensaje", "alert alert-danger");
		}

		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("lstTipos", repoTip.findAll());
		return "solicitudLlanos";
	}

	@GetMapping("/listado")
	public String cargarList(Model model) {
		model.addAttribute("lstSol", repoSol.findAll());
		return "listadoLlanos";
	}

	@PostMapping("/filtrarListado")
	public String filtrarListado(@RequestParam Date fchregistro, Model model) {
		try {
			model.addAttribute("lstSol", repoSol.findByFchregistro(fchregistro));

			model.addAttribute("mensajel",
					repoSol.findByFchregistro(fchregistro).isEmpty()
							? "No se encontraron solicitudes para la fecha seleccionada." 
							: "Solicitudes filtradas correctamente.");

			model.addAttribute("cssmensajel",
					repoSol.findByFchregistro(fchregistro).isEmpty() ? "alert alert-warning" : "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensajel", "Error al filtrar las solicitudes. Por favor, inténtalo de nuevo.");
			model.addAttribute("cssmensajel", "alert alert-danger");
		}
		return "listadoLlanos";
	}
	
	@Autowired
	private DataSource dataSource; 

	@Autowired
	private ResourceLoader resourceLoader; 

	@GetMapping("/reportes")
	public void reportes(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:static/CiberReporteBank.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
