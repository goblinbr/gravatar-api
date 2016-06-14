package com.github.goblinbr.gravatarapi;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
        String hash = md5Hex(email);
        String urlStr = GRAVATAR_URL + hash;

        System.out.println(urlStr);
        
        URL url = null;
        try{
        	url = new URL(urlStr);
        }
        catch( MalformedURLException e ){
        	e.printStackTrace();
        }
        
        return ImageIO.read(url);
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

/**
 * Defines what should return when the email doesn't have a gravatar
 * @author Rodrigo de Bona Sartor
 */
enum WhenNotFound {
	/**
	 * Will return null
	 */
	NULL("404"),
	
	/**
	 * A simple, cartoon-style silhouetted outline of a person (does not vary by email hash)
	 */
	MYSTERY_MAN("mystery-man"),
	
	/**
	 * A geometric pattern based on an email hash
	 */
	IDENT_ICON("identicon"),
	
	/**
	 * A generated 'monster' with different colors, faces, etc
	 */
	MONSTER_ID("monsterid"),
	
	/**
	 * Generated faces with differing features and backgrounds
	 */
	WAVATAR("wavatar"),
	
	/**
	 * Awesome generated, 8-bit arcade-style pixelated faces
	 */
	RETRO("retro"),
	
	/**
	 * A transparent image
	 */
	BLANK("blank");
	
	private String queryParam;
	
	private WhenNotFound(String queryParam) {
		this.queryParam = queryParam;
	}
	
	public String getQueryParam() {
		return queryParam;
	}
	
	@Override
	public String toString() {
		return "d=" + queryParam;
	}
	
}