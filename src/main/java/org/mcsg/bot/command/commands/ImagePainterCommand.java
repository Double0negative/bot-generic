package org.mcsg.bot.command.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.Painter;
import org.mcsg.bot.drawing.painters.AbstractShapes;
import org.mcsg.bot.drawing.painters.Clusters;
import org.mcsg.bot.drawing.painters.Lines;
import org.mcsg.bot.drawing.painters.Shapes;
import org.mcsg.bot.drawing.painters.Smoke;
import org.mcsg.bot.util.Arguments; 
import org.mcsg.bot.util.MapWrapper;

public class ImagePainterCommand implements BotCommand{

	private HashMap<String, Class<? extends AbstractPainter>> painters = new HashMap<>();
	private Random random = new Random();

	public ImagePainterCommand() {
		this.painters.put("shapes", Shapes.class);
		this.painters.put("abstract", AbstractShapes.class);
		this.painters.put("cluster", Clusters.class);
		this.painters.put("lines", Lines.class);
		this.painters.put("smoke", Smoke.class);
	}

	@Override
	public void execute(String cmd, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		Arguments arge = new Arguments(args, "base/background arg", "resolution/res arg");

		String genName = getRandomKey(painters);
		Class<? extends AbstractPainter> generator = this.painters.get(genName);

		System.out.println(Arrays.toString(arge.getArgs()));
		if(arge.getArgs().length > 0) {
			generator = this.painters.get(arge.getArgs()[0]);
		}

		int width = 1920;
		int height = 1080;

		if(arge.getSwitches().containsKey("res")) {

		}


		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		AbstractPainter painter = generator.getConstructor(BufferedImage.class).newInstance(img);


		
		final MapWrapper wrap = new MapWrapper();
		for(String arg : args){
			String [] split = arg.split(":");
			if(split.length > 1)
				wrap.put(split[0], split[1]);
		}

		painter.setBackground(Color.BLACK);
		painter.paint(wrap);

		File file = new File(chat.getServer().getBot().getSettings().getDataFolder(), genName + "_" + System.currentTimeMillis() + ".png");
		ImageIO.write(img, "png",file);

		chat.sendFile(file);

	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("paint", "img", "genimg");
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String getRandomKey(Map<String,?> map) {
		String[] s =  map.keySet().toArray(new String[0]);
		
		return s[random.nextInt(s.length)];
	
	}

}
