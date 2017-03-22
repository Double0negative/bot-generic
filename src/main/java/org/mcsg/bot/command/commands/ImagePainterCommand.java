package org.mcsg.bot.command.commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.Painter;
import org.mcsg.bot.drawing.filter.Distort;
import org.mcsg.bot.drawing.filter.Edge;
import org.mcsg.bot.drawing.filter.Smoosh_;
import org.mcsg.bot.drawing.filter.Pixelate;
import org.mcsg.bot.drawing.filter.Smoosh;
import org.mcsg.bot.drawing.painters.AbstractShapes;
import org.mcsg.bot.drawing.painters.Circles;
import org.mcsg.bot.drawing.painters.Clusters;
import org.mcsg.bot.drawing.painters.DotLines;
import org.mcsg.bot.drawing.painters.Dots;
import org.mcsg.bot.drawing.painters.Empty;
import org.mcsg.bot.drawing.painters.Landscape;
import org.mcsg.bot.drawing.painters.Lines;
import org.mcsg.bot.drawing.painters.BasicShapes;
import org.mcsg.bot.drawing.painters.Smoke;
import org.mcsg.bot.drawing.painters.SprayPaint;
import org.mcsg.bot.drawing.painters.Sunset;
import org.mcsg.bot.util.Arguments; 
import org.mcsg.bot.util.MapWrapper;

public class ImagePainterCommand implements BotCommand{

	private HashMap<String, Class<? extends AbstractPainter>> painters = new HashMap<>();
	private HashMap<String, Class<? extends Filter>> filters = new HashMap<>();

	private Random random = new Random();

	public ImagePainterCommand() {
		this.painters.put("shapes", BasicShapes.class);
		this.painters.put("abstract", AbstractShapes.class);
		this.painters.put("cluster", Clusters.class);
		this.painters.put("lines", Lines.class);
		this.painters.put("smoke", Smoke.class);
		this.painters.put("sunset", Sunset.class);
		this.painters.put("circles", Circles.class);
		this.painters.put("dots", Dots.class);
		this.painters.put("dotlines", DotLines.class);
		this.painters.put("spray", SprayPaint.class);
		this.painters.put("landscape", Landscape.class);

		this.painters.put("empty", Empty.class);

		this.filters.put("pixel", Pixelate.class);
		this.filters.put("distort", Distort.class);
		this.filters.put("smoosh", Smoosh.class);
		this.filters.put("edge", Edge.class);

	}

	@Override
	public void execute(String cmd, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		Arguments arge = new Arguments(args, "background/bg arg", "resolution/res arg", "filter arg", "import arg");

		String genName = getRandomKey(painters);
		Class<? extends AbstractPainter> generator = this.painters.get(genName);

		System.out.println(Arrays.toString(arge.getArgs()));
		if(arge.getArgs().length > 0) {
			generator = this.painters.get(arge.getArgs()[0]);
		}

		int width = 1920;
		int height = 1080;

		if(arge.getSwitches().containsKey("res")) {
			String res = arge.getSwitch("res");

			width = Integer.parseInt(res.split("x")[0]);
			height = Integer.parseInt(res.split("x")[1]);
		}

		BufferedImage img = null;

		if(arge.hasSwitch("import")) {
			img = ImageIO.read(new URL(arge.getSwitch("import")));
		}

		if(img == null){
			img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		}
		System.out.println(img);
		System.out.println(generator);

		AbstractPainter painter = generator.getConstructor(BufferedImage.class).newInstance(img);

		final MapWrapper wrap = new MapWrapper();
		for(String arg : args){
			String [] split = arg.split(":");
			if(split.length > 1)
				wrap.put(split[0], split[1]);
		}

		Color bg = Color.BLACK;

		if("none".equalsIgnoreCase(arge.getSwitch("background"))) {
			bg = null;
		} 
		if(arge.hasSwitch("background")) {
			bg = Color.decode(arge.getSwitch("background"));
		}

		if(!arge.hasSwitch("import"))
			painter.setBackground(bg);
		painter.paint(wrap);

		if(arge.getSwitches().containsKey("filter")) {
			for(String fstr : arge.getSwitches().get("filter").split(",")) {
				
				Class<? extends Filter> fClass = filters.get(fstr);
				if(fClass != null){
					Filter filter = fClass.newInstance();
					img = filter.filter(img, (Graphics2D) img.getGraphics(), wrap);
				}
			}
		}


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
