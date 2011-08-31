package pilotage.core;

import java.io.File;

import com.threed.jpct.Loader;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import pilotage.sound.SoundBox;

/* ****** IMPORTANT : METTRE A CHAQUE FOIS A JOUR COORDONNEE DE L'OBJET
 * 			ET COORDONNEES DU MODELE 3D, ici ce n'est pas encore le cas*************
 */

/**
 * The hot air balloon entity.
 */
public class Montgolfier extends Entity {
	
   static {
      /**
      * Load all the textures required by this entity. The same for every Montgolfier.
      */
      Texture spot = new Texture("textures" + File.separatorChar + "spot.jpg");
      Texture nac = new Texture("textures" + File.separatorChar + "nac.jpg");
      TextureManager.getInstance().addTexture("balloon", spot);
      TextureManager.getInstance().addTexture("nac", nac);
   }

   Object3D nacelle;
   
   public Montgolfier() {
	  /*super(Primitives.getSphere(30, 10));
	  nacelle = Primitives.getCube(1.8f);
	  this.model.addChild(nacelle);
	  this.model.setTexture("balloon");
	  nacelle.setTexture("nac");
	  nacelle.setEnvmapped(Object3D.ENVMAP_ENABLED);
	  nacelle.translate(new SimpleVector(0, 7, 0));
	  nacelle.translateMesh();
	  
      this.model.setEnvmapped(Object3D.ENVMAP_ENABLED);
      this.model.build();
      nacelle.build();*/

	   Object3D[] objs = Loader.load3DS("models" + File.separatorChar + "ballon.3ds", 1);
	   for(int i=0; i<objs.length; i++)
			this.model.addChild(objs[0]);
   }

   /**
    * Add the balloon to the world.
    */
   public void addToWorld(World world) {
      world.addObject(this.model);
      world.addObject(this.nacelle);
   }
   
   // TODO put good formulas
   private void burner() {
	   SoundBox.getInstance().play("sound/car.wav");
	   if (speed.y > -1.5)
		   this.speed.y -= 0.1;
   }
   
   // TODO put good formulas
   private void openValve() {
		if (this.speed.y < 1.5)
			this.speed.y += 0.1f;
   }
   
   /**
    * This method is called by WorldManager with a known granularity
    * @see KeyBoardManager
    */
   public void manageKeys() {
	   if(this.keyboardManager.isPressed("q")) {
		   burner();
	   }
	   if(this.keyboardManager.isPressed("w")) {
		   openValve();
	   }
	   
	   // TODO manage the slow down correctly due to the "aero brake"
	   this.speed.z = 1.1f;
	   this.speed.x = 1.1f;
   }
}
