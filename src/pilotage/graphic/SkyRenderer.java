package pilotage.graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pilotage.Main;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

public class SkyRenderer {
	
	private Texture elements[];
	
	private int widthCount;
	
	private int heigthCount;
	
	private int count;
	
	// TODO : panoramic display + correct a bug (mapping bigger than necessary)
	public SkyRenderer(String fileName, boolean panoramic) {
		
		int width = Main.configuration.width;
		int heigth = Main.configuration.height;
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		
		this.widthCount = width / 256 + 1;
		this.heigthCount = width / 256;
		this.count = widthCount * heigthCount;
		
		this.elements = new Texture[count];
		
		for(int i=0; i<count; i++) {
			this.elements[i] = new Texture(image.getSubimage((i%widthCount)*256, (i/widthCount)*256, 256, 256));
			TextureManager.getInstance().addTexture("sky" + i, this.elements[i]);
		}
	}
	
	public void blit(FrameBuffer buffer) {
		for(int i=0; i<count; i++) {
			buffer.blit(TextureManager.getInstance().getTexture("sky" + i),0,0,(i%widthCount)*256,(i/widthCount)*256,256,256, false);
		}
	}
}
