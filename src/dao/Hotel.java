package dao;

import java.util.ArrayList;

public class Hotel {
	private String nomHotel;
	
	private String pays;
	private String ville;
	private ArrayList<chambre> chambres;
	
	public Hotel() {}
	
	
	public Hotel(String pays, String ville, ArrayList<chambre> chambres) {
		super();
		this.pays = pays;
		this.ville = ville;
		this.chambres = chambres;
	}
	
	public String getNomHotel() {
		return nomHotel;
	}


	public void setNomHotel(String nomHotel) {
		this.nomHotel = nomHotel;
	}
	
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public ArrayList<chambre> getChambres() {
		return chambres;
	}
	public void setChambres(ArrayList<chambre> chambres) {
		this.chambres = chambres;
	}
	
	

}
