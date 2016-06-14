package com.github.goblinbr.gravatarapi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Gravatar gravatar = new Gravatar();
        BufferedImage bi = gravatar.retrieveAvatar("rodrigo.goblin@gmail.com");
        
        File outputfile = new File("c:\\temp\\saved.png");
        ImageIO.write(bi, "png", outputfile);
    }
}
