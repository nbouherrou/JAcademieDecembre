package org.jacademie.examenDecembre.DAOs;

import java.util.Set;

public class Artiste {

	private Integer codeArtiste;
	private String nom;
	private Set<Album> albums;
	
	public Artiste(){
		super();
	}
	public Artiste(Integer codeArtist, String nom, Set<Album> albums) {
		super();
		this.codeArtiste = codeArtist;
		this.nom = nom;
		this.albums = albums;
	}
	
	
	
	@Override
	public String toString() {
		return "Artiste [codeArtist=" + codeArtiste + ", nom=" + nom
				+ ", albums=" + albums + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((albums == null) ? 0 : albums.hashCode());
		result = prime * result
				+ ((codeArtiste == null) ? 0 : codeArtiste.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artiste other = (Artiste) obj;
		if (albums == null) {
			if (other.albums != null)
				return false;
		} else if (!albums.equals(other.albums))
			return false;
		if (codeArtiste == null) {
			if (other.codeArtiste != null)
				return false;
		} else if (!codeArtiste.equals(other.codeArtiste))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
	public Integer getCodeArtiste() {
		return codeArtiste;
	}
	public void setCodeArtiste(Integer codeArtiste) {
		this.codeArtiste = codeArtiste;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Set<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(Set<Album> chansons) {
		this.albums = chansons;
	}
	
	
}
