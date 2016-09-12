package view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	
	public static final AssetManager manager = new AssetManager();
	public static Map<String, String> map = new HashMap<String, String>();
	public static final String St_bl = "gameStonePattern/stern_blau.png",
	St_ge = "gameStonePattern/stern_gelb.png",
	St_gr = "gameStonePattern/stern_gruen.png",
	St_li = "gameStonePattern/stern_lila.png",
	St_or = "gameStonePattern/stern_orange.png",
	St_ro = "gameStonePattern/stern_rot.png",
	
	Ra_bl = "gameStonePattern/raute_blau.png",
	Ra_ge = "gameStonePattern/raute_gelb.png",
	Ra_gr = "gameStonePattern/raute_gruen.png",
	Ra_li = "gameStonePattern/raute_lila.png",
	Ra_or = "gameStonePattern/raute_orange.png",
	Ra_ro = "gameStonePattern/raute_rot.png",
	
	Kr_bl = "gameStonePattern/kreis_blau.png",
	Kr_ge = "gameStonePattern/kreis_gelb.png",
	Kr_gr = "gameStonePattern/kreis_gruen.png",
	Kr_li = "gameStonePattern/kreis_lila.png",
	Kr_or = "gameStonePattern/kreis_orange.png",
	Kr_ro = "gameStonePattern/kreis_rot.png",
	
	Qu_bl = "gameStonePattern/quadrat_blau.png",
	Qu_ge = "gameStonePattern/quadrat_gelb.png",
	Qu_gr = "gameStonePattern/quadrat_gruen.png",
	Qu_li = "gameStonePattern/quadrat_lila.png",
	Qu_or = "gameStonePattern/quadrat_orange.png",
	Qu_ro = "gameStonePattern/quadrat_rot.png",
	
	// Blume ist jetzt Blitz und Sonne ist Dreieck
	
	Bl_bl = "gameStonePattern/blitz_blau.png",
	Bl_ge = "gameStonePattern/blitz_gelb.png",
	Bl_gr = "gameStonePattern/blitz_gruen.png",
	Bl_li = "gameStonePattern/blitz_lila.png",
	Bl_or = "gameStonePattern/blitz_orange.png",
	Bl_ro = "gameStonePattern/blitz_rot.png",
	
	So_bl = "gameStonePattern/dreieck_blau.png",
	So_ge = "gameStonePattern/dreieck_gelb.png",
	So_gr = "gameStonePattern/dreieck_gruen.png",
	So_li = "gameStonePattern/dreieck_lila.png",
	So_or = "gameStonePattern/dreieck_orange.png",
	So_ro = "gameStonePattern/dreieck_rot.png";
	
	public static void load(){
		
		manager.load(St_bl, Texture.class);
		manager.load(St_ge, Texture.class);
		manager.load(St_gr, Texture.class);
		manager.load(St_li, Texture.class);
		manager.load(St_or, Texture.class);
		manager.load(St_ro, Texture.class);
		
		manager.load(Kr_bl, Texture.class);
		manager.load(Kr_ge, Texture.class);
		manager.load(Kr_gr, Texture.class); 
		manager.load(Kr_li, Texture.class);
		manager.load(Kr_or, Texture.class);
		manager.load(Kr_ro, Texture.class);
		
		manager.load(Qu_bl, Texture.class);
		manager.load(Qu_ge, Texture.class);
		manager.load(Qu_gr, Texture.class);
		manager.load(Qu_li, Texture.class);
		manager.load(Qu_or, Texture.class);
		manager.load(Qu_ro, Texture.class);
		
		manager.load(Bl_bl, Texture.class);
		manager.load(Bl_ge, Texture.class);
		manager.load(Bl_gr, Texture.class);
		manager.load(Bl_li, Texture.class);
		manager.load(Bl_or, Texture.class);
		manager.load(Bl_ro, Texture.class);
		
		manager.load(So_bl, Texture.class);
		manager.load(So_ge, Texture.class);
		manager.load(So_gr, Texture.class);
		manager.load(So_li, Texture.class);
		manager.load(So_or, Texture.class);
		manager.load(So_ro, Texture.class);

		manager.load(Ra_bl, Texture.class);
		manager.load(Ra_ge, Texture.class);
		manager.load(Ra_gr, Texture.class);
		manager.load(Ra_li, Texture.class);
		manager.load(Ra_or, Texture.class);
		manager.load(Ra_ro, Texture.class);
		
		map.put("St_bl", St_bl);
		map.put("St_ge", St_ge);
		map.put("St_gr", St_gr);
		map.put("St_li", St_li);
		map.put("St_or", St_or);
		map.put("St_ro", St_ro);

		map.put("Ra_bl", Ra_bl);
		map.put("Ra_ge", Ra_ge);
		map.put("Ra_gr", Ra_gr);
		map.put("Ra_li", Ra_li);
		map.put("Ra_or", Ra_or);
		map.put("Ra_ro", Ra_ro);

		map.put("Kr_bl", Kr_bl);
		map.put("Kr_ge", Kr_ge);
		map.put("Kr_gr", Kr_gr);
		map.put("Kr_li", Kr_li);
		map.put("Kr_or", Kr_or);
		map.put("Kr_ro", Kr_ro);

		map.put("Qu_bl", Qu_bl);
		map.put("Qu_ge", Qu_ge);
		map.put("Qu_gr", Qu_gr);
		map.put("Qu_li", Qu_li);
		map.put("Qu_or", Qu_or);
		map.put("Qu_ro", Qu_ro);

		map.put("Bl_bl", Bl_bl);
		map.put("Bl_ge", Bl_ge);
		map.put("Bl_gr", Bl_gr);
		map.put("Bl_li", Bl_li);
		map.put("Bl_or", Bl_or);
		map.put("Bl_ro", Bl_ro);

		map.put("So_bl", So_bl);
		map.put("So_ge", So_ge);
		map.put("So_gr", So_gr);
		map.put("So_li", So_li);
		map.put("So_or", So_or);
		map.put("So_ro", So_ro);
	}
	
	public static void dispose(){
		
		manager.dispose();
	}
}
