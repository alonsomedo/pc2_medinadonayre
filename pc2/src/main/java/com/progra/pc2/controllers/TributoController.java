package com.progra.pc2.controllers;

import java.util.List;
import java.util.Map;

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
	
	public Person LogicaNegocio(Person person)
	{
		double impuestoTotal = 0;
		double [] montoAfectado = {0,0,0,0,0};
		double [] impuestoAfectado = {0,0,0,0,0};
		String tipoTrabajador = person.getTipoTrabajador();
		double salarioMensual = person.getSalario();
		double uit = person.getUit();
		
		double salarioAnual = 0;
		
		if(tipoTrabajador.equalsIgnoreCase("Independiente"))
		{
			salarioAnual = salarioMensual * 12;
		}else
		{
			salarioAnual = salarioMensual * 14;
		}

		
		person.setTotalBruto(round(salarioAnual,2));
		
		double descuento7uit = CalcularDescuento7UIT(person.getUit());
		
		double renta_neta = salarioAnual - descuento7uit;
		
		double [] montos_maximos = {5*uit,20*uit,35*uit, 45*uit};
		
		if (renta_neta > 0)
		{
			if(renta_neta<= montos_maximos[0])
			{
				montoAfectado[0] = renta_neta;
				impuestoAfectado[0] = montoAfectado[0] * tasas[0];
			}else
			{
				montoAfectado[0] = montos_maximos[0];
				impuestoAfectado[0] = montoAfectado[0] * tasas[0];
			}
			
			if(renta_neta <= montos_maximos[1])
			{
				montoAfectado[1] = renta_neta - montoAfectado[0];
				impuestoAfectado[1] = montoAfectado[1] * tasas[1];
			}else
			{
				montoAfectado[1] = montos_maximos[1] - montoAfectado[0];
				impuestoAfectado[1] = montoAfectado[1] * tasas[1];
			}
			
			if(renta_neta <= montos_maximos[2])
			{
				montoAfectado[2] = renta_neta - (montoAfectado[0]+montoAfectado[1]);
				impuestoAfectado[2] = montoAfectado[2] * tasas[2];
			}else
			{
				montoAfectado[2] = montos_maximos[2] - (montoAfectado[0]+montoAfectado[1]);
				impuestoAfectado[2] = montoAfectado[2] * tasas[2];
			}
			
			if(renta_neta <= montos_maximos[3])
			{
				montoAfectado[3] = renta_neta - (montoAfectado[0]+montoAfectado[1]+montoAfectado[2]);
				impuestoAfectado[3] = montoAfectado[3] * tasas[3];
			}else
			{
				montoAfectado[3] = montos_maximos[3] - (montoAfectado[0]+montoAfectado[1]+montoAfectado[2]);
				impuestoAfectado[3] = montoAfectado[3] * tasas[3];
			}
			
			if(renta_neta > montos_maximos[3])
			{
				montoAfectado[4] = renta_neta - montos_maximos[3];
				impuestoAfectado[4] = montoAfectado[4] * tasas[4];
			}else
			{
				montoAfectado[4] = 0;
				impuestoAfectado[4] = montoAfectado[4] * tasas[4];
			}
		}
		
		impuestoTotal = CalcularImpuestoTotal(impuestoAfectado);
		
		person.setTotalImpuesto(round(impuestoTotal,2));

		
		return person;
	}
	
	public double CalcularImpuestoTotal(double [] impuesto)
	{
		double impuestoTotal = 0;
		for(int i=0; i < impuesto.length; i++)
		{
			impuestoTotal += impuesto[i];
		}
		return impuestoTotal;
				
	}
	public double CalcularDescuento7UIT(double uit)
	{
		return uit * 7;
	}
	
	private double round(double valor, int q_decimales) {
	    if (q_decimales < 0) return 0;

	    long factor = (long) Math.pow(10, q_decimales);
	    valor = valor * factor;
	    long tmp = Math.round(valor);
	    return (double) tmp / factor;
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
		
		Person _person = LogicaNegocio(person);
		
		tributoRepository.save(_person);
		
		return "redirect:/persona/listado";
	}
	
	@GetMapping("/persona/listado")
	private String listado(Map<String, Object> model) {
		
		List<Person> personas = tributoRepository.findAll();
		model.put("personas", personas);
		return "listPerson";
	}
}
