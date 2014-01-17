package org.jacademie.examenDecembre;

import static org.jacademie.examenDecembre.utils.HibernateUtil.beginTransaction;
import static org.jacademie.examenDecembre.utils.HibernateUtil.closeSession;
import static org.jacademie.examenDecembre.utils.HibernateUtil.commitTransaction;
import static org.jacademie.examenDecembre.utils.HibernateUtil.openSession;
import static org.jacademie.examenDecembre.utils.HibernateUtil.rollbackTransaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.jacademie.examenDecembre.DAOs.Album;
import org.jacademie.examenDecembre.DAOs.AlbumHibernateDAO;
import org.jacademie.examenDecembre.DAOs.Artiste;
import org.jacademie.examenDecembre.DAOs.ArtisteHibernateDAO;
import org.jacademie.examenDecembre.DAOs.Chanson;
import org.jacademie.examenDecembre.DAOs.ChansonHibernateDAO;
import org.jacademie.examenDecembre.DAOs.IAlbumDAO;
import org.jacademie.examenDecembre.DAOs.IArtisteDAO;
import org.jacademie.examenDecembre.DAOs.IChansonDAO;
import org.jacademie.examenDecembre.utils.HibernateUtil;

import au.com.bytecode.opencsv.CSVReader;

public class ReadFileMusic {

	private static Logger logger = Logger.getLogger(ReadFileMusic.class);
	static IArtisteDAO artisteDAO = new ArtisteHibernateDAO();
	static IAlbumDAO albumDAO = new AlbumHibernateDAO();
	static IChansonDAO chansonDAO = new ChansonHibernateDAO();

	public static boolean readFile(String pathFileMusic) {
		logger.info("Read file : " + pathFileMusic);
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new FileReader(pathFileMusic));

			String[] rowAsTokens;
			
			openSession();
			beginTransaction();
			while ((rowAsTokens = csvReader.readNext()) != null) {

				if (rowAsTokens.length != 7) {
					rollbackTransaction();
					closeSession();
					return false;
				}

				Integer codeArtist = Integer.parseInt(rowAsTokens[0]);
				String nomArtiste = rowAsTokens[1];
				Integer codeAlbum = Integer.parseInt(rowAsTokens[2]);
				String nomAlbum = rowAsTokens[3];
				Integer numeroChanson = Integer.parseInt(rowAsTokens[4]);
				String titreChanson = rowAsTokens[5];
				Integer dureeChanson = Integer.parseInt(rowAsTokens[6]);

				
				Artiste artist = artisteDAO.getById(codeArtist);

				if (artist == null) {
					Chanson chanson = new Chanson(numeroChanson, titreChanson,dureeChanson);
					logger.debug("a + " + chanson);
					Album album = new Album(codeAlbum, nomAlbum, null);
					album.addChanson(chanson);
					artist = new Artiste(codeArtist, nomArtiste, null);
					artist.addAlbum(album);
					artisteDAO.save(artist);
					
				} else {
					Album album = albumDAO.getById(codeAlbum);
					if (album == null) {
						Chanson chanson = new Chanson(numeroChanson,titreChanson, dureeChanson);
						logger.debug("b + " + chanson);
						album = new Album(codeAlbum, nomAlbum, null);
						album.addChanson(chanson);
						artist.addAlbum(album);
						
					} else {
						Chanson chanson = chansonDAO.getByAlbumAndNum(album,numeroChanson);
						logger.debug("c + " + chanson);
						if (chanson == null) {
							chanson = new Chanson(numeroChanson, titreChanson,dureeChanson);
							logger.debug("c2 + " + chanson);
							album.addChanson(chanson);
							//albumDAO.update(album);
						
							
						} else {
							chanson.setTitre(titreChanson);
							chanson.setDuree(dureeChanson);
							//chansonDAO.update(chanson);
							
						}
					}
					artisteDAO.update(artist);
				}
			}

			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollbackTransaction();
			closeSession();
			return false;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollbackTransaction();
			closeSession();
			return false;
		}catch(NonUniqueObjectException e){
			e.printStackTrace();
			rollbackTransaction();
			closeSession();
			return false;
		}catch(HibernateException e){
			e.printStackTrace();
			rollbackTransaction();
			closeSession();
			return false;
		}

		commitTransaction();
		closeSession();
		return true;
	}
	private static boolean extractData() {
		return false;
	}

}
