package com.github.goblinbr.gravatarapi;

/**
 * Defines what should return when the email doesn't have a gravatar
 * @author Rodrigo de Bona Sartor
 */
public enum WhenNotFound {
	/**
	 * Will return null
	 */
	NULL("404"),
	
	/**
	 * A simple, cartoon-style silhouetted outline of a person (does not vary by email hash)
	 */
	MYSTERY_MAN("mm"),
	
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
