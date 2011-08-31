package pilotage.graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pilotage.Main;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Texture;

/**
 * A zone of information
 */
public class OSD {

	private static OSD instance;
	private Texture tex;
	private final int MARGIN = 10;
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	
	private OSD() {
		BufferedImage background = null;
		try {
		   background = ImageIO.read(new File ("textures" + File.separatorChar + "osd.png"));
		} catch(IOException e) {
			System.err.println(e.toString());
		}
		tex = new Texture(background);
		this.height = background.getHeight();
		this.width =background.getWidth();
		this.xPos = Main.configuration.width - width - this.MARGIN;
		this.yPos = Main.configuration.height - height - this.MARGIN;
	}
	
	public static OSD getInstance() {
		if(instance == null)
			instance = new OSD();
		return instance;
	}
		
	public void blit(FrameBuffer buffer) {
		buffer.blit(tex, 0, 0, this.xPos, this.yPos, this.width, this.height, true);
	}
}
