package dao;

import java.util.Date;

public class chambre {
	
  private int N_chambre;
  private Double prix;
  private String type;
  private int Nb_Per;
  private Date date_debut,date_fin;
  
  
public chambre() {} 
  

public chambre(int n_chambre, Double prix, String type, int nb_Per) {
	
	N_chambre = n_chambre;
	this.prix = prix;
	this.type = type;
	Nb_Per = nb_Per;
	
	date_debut = new Date();
	date_fin = new Date();
}


public int getN_chambre() {
	return N_chambre;
}
public void setN_chambre(int n_chambre) {
	N_chambre = n_chambre;
}
public Double getPrix() {
	return prix;
}
public void setPrix(Double prix) {
	this.prix = prix;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public int getNb_Per() {
	return Nb_Per;
}
public void setNb_Per(int nb_Per) {
	Nb_Per = nb_Per;
}

public Date getDate_debut() {
	return date_debut;
}

public void setDate_debut(Date date_debut) {
	this.date_debut = date_debut;
}

public Date getDate_fin() {
	return date_fin;
}

public void setDate_fin(Date date_fin) {
	this.date_fin = date_fin;
}
}
