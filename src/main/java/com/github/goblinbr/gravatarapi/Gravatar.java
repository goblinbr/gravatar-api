package com.github.goblinbr.gravatarapi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

import javax.imageio.ImageIO;

public class Gravatar {
	
	private static final String GRAVATAR_URL = "https://www.gravatar.com/avatar/";
	
	/**
	 * Retrieve a <code>BufferedImage</code> with the gravatar image of the email, or null if the email doesn't have a gravatar
	 * @param email An email like anemail@anhost.com
     * @return a <code>BufferedImage</code> containing the image or null if the email doesn't have a gravatar
	 * @throws IOException if an error occurs during reading.
	 */
    public BufferedImage retrieveAvatar( String email ) throws IOException {
    	return retrieveAvatar(email, null, null, null);
    }
    
    /**
     * Retrieve a <code>BufferedImage</code> with the gravatar image of the email
     * @param email An email like anemail@anhost.com
     * @param rating By default, only 'G' rated images are displayed unless you indicate that you would like to see higher ratings
     * @param whenNotFound Defines what should return when the email doesn't have a gravatar
     * @param size Size in pixels. You may request images anywhere from 1px up to 2048px. By default, images are presented at 80px by 80px if no size parameter is supplied
     * @return a <code>BufferedImage</code> containing the image or null if the email doesn't have a gravatar and <b>whenNotFound</b> is null
     * @throws IOException if an error occurs during reading.
     */
    public BufferedImage retrieveAvatar( String email, Rating rating, WhenNotFound whenNotFound, Integer size ) throws IOException {
    	if( email == null ){
    		throw new IllegalArgumentException("email can't be null");
    	}
    	if( whenNotFound == null ){
    		whenNotFound = WhenNotFound.NULL;
    	}
    	if( size != null && (size < 1 || size > 2048) ){
    		size = null;
    	}
    	
        String hash = md5Hex(email);
        StringBuilder urlBuilder = new StringBuilder( GRAVATAR_URL );
        urlBuilder.append( hash );
        urlBuilder.append("?").append(whenNotFound);
        if( rating != null ){
        	urlBuilder.append("&").append(rating);
        }
        if( size != null ){
        	urlBuilder.append("&").append("s=").append(size);
        }

        String urlStr = urlBuilder.toString();
        
        System.out.println(urlStr);
        
        URL url = null;
        try{
        	url = new URL(urlStr);
        }
        catch( MalformedURLException e ){
        	e.printStackTrace();
        }
        
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        BufferedImage bi = null;
        int responseCode = urlConnection.getResponseCode();
        if( responseCode == 404 ){
        	if( whenNotFound != WhenNotFound.NULL ){
        		throw new IOException("Returned status 404");
        	}
        }
        else {
        	bi = ImageIO.read(url);
        }
        
        return bi;
    }
    
	/**
	 * Save a <code>file</code> with the gravatar image of the email
	 * @param email An email like anemail@anhost.com
	 * @param file To which file the avatar will be saved
	 * @param fileType The file type
     * @return a <code>boolean</code> indicating that the image was saved to file or not
	 * @throws IOException if an error occurs during reading.
	 */
    public boolean saveAvatarToFile( String email, File file, FileType fileType ) throws IOException {
    	BufferedImage bi = retrieveAvatar( email );
    	boolean saved = false;
    	if( bi != null ){
    		saved = ImageIO.write(bi, fileType.toString().toLowerCase(), file);
    	}
    	return saved;
    }
    
    /**
     * Save a <code>file</code> with the gravatar image of the email
     * @param email An email like anemail@anhost.com
     * @param file To which file the avatar will be saved
     * @param fileType The file type
     * @param rating By default, only 'G' rated images are displayed unless you indicate that you would like to see higher ratings
     * @param whenNotFound Defines what should return when the email doesn't have a gravatar
     * @param size Size in pixels. You may request images anywhere from 1px up to 2048px. By default, images are presented at 80px by 80px if no size parameter is supplied
     * @return a <code>boolean</code> indicating that the image was saved to file or not
     * @throws IOException if an error occurs during reading.
     */
    public boolean saveAvatarToFile( String email, File file, FileType fileType, Rating rating, WhenNotFound whenNotFound, Integer size ) throws IOException {
    	BufferedImage bi = retrieveAvatar( email, rating, whenNotFound, size );
    	boolean saved = false;
    	if( bi != null ){
    		saved = ImageIO.write(bi, fileType.toString().toLowerCase(), file);
    	}
    	return saved;
    }
    
    private String md5Hex(String message) {
    	String hex = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(message.getBytes("CP1252"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            hex = sb.toString();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return hex;
    }
}
