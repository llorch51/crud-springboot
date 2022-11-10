package com.cursojava.curso.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity//va a ser una entidad de la base de datos
@Table(name = "usuarios")//nombre de la tabla
public class Usuario {

    @Id//es la llave primaria en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)//se va a generar automaticamente el incremento de la llave primaria
    @Getter @Setter @Column(name = "id")//nombre de la columna, para que Hibernate sepa
    private Long id;
    @Getter @Setter @Column(name = "nombre")
    private String nombre;
    @Getter @Setter @Column(name = "apellido")
    private String apellido;
    @Getter @Setter @Column(name = "email")
    private String email;
    @Getter @Setter @Column(name = "telefono")
    private String telefono;
    @Getter @Setter @Column(name = "password")
    private String password;



}
