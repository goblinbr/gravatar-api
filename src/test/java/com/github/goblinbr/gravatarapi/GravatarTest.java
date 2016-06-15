package com.github.goblinbr.gravatarapi;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class GravatarTest {

	private static final String INVALID_EMAIL = "x@akoakoakaokaokoakdsodasodlaosldoa.kk";;
	
	private Gravatar gravatar = new Gravatar();
	
	@Test
	public void retrieveAvatarWithInvalidEmailRatingPWhenNotFoundNullShouldReturnNull(){
		try {
			BufferedImage bi = gravatar.retrieveAvatar(INVALID_EMAIL, Rating.G, WhenNotFound.NULL, null);
			Assert.assertEquals(null, bi);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Should not throw an Exception. Verify the internet connection!");
		}
	}
	
}
