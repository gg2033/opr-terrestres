//package com.losilegales.oprterrestres.entity;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.Data;
//@Entity
//@Table(name="pasajeros",schema="public")
//@Data
//public class Pasajero {
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id_pasajero")
//	private Integer idPasajero;
//	
//	@Column(name="nombre")
//	private String nombre;
//	
//	@OneToMany(mappedBy = "pasajero", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Carga> carga;
//	
////	@ManyToOne(fetch = FetchType.LAZY)
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_vuelo")
//	private Vuelo vuelo;
//
//	
////	@Column(name="id_dieta")
////	private Integer idDieta;
//	
//	
//
//
//}
