package com.progra.pc2.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.progra.pc2.entities.Person;
import com.progra.pc2.repositories.TributoRepository;

@Controller
public class TributoController {


	private double[] tasas = { 0.08, 0.14, 0.17, 0.2, 0.3 };
	
	
	@Autowired
	private TributoRepository tributoRepository;
	
	public Person logicaNegocio(Person person)
	{
		String tipoTrabajador = person.getTipoTrabajador();
		double salarioMensual = person.getSalario();
		
		double salarioAnual = 0;
		
		salarioAnual = (tipoTrabajador=="Independiente")?salarioMensual*12:salarioMensual*14;
		
		
		
		
		return person;
	}
	
	
	@GetMapping("/persona/calcularIR")
	private String initForm(Model model) {

		model.addAttribute(new Person());
		return "formulario";
	}
	
	@PostMapping("/persona/calcularIR")
	private String submitForm(@Valid Person person, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			return "formulario";
		}
		
		Person _person = logicaNegocio(person);
		
		return "formulario";
	}
}
