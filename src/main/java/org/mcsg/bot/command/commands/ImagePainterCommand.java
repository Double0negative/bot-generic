package org.mcsg.bot.command.commands;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.filter.Bloom2;
import org.mcsg.bot.drawing.filter.ChromaticAberration;
import org.mcsg.bot.drawing.filter.Distort_;
import org.mcsg.bot.drawing.filter.Edge;
import org.mcsg.bot.drawing.filter.Warp;
import org.mcsg.bot.drawing.filter.Pixelate;
import org.mcsg.bot.drawing.filter.ScanLines;
import org.mcsg.bot.drawing.filter.Smoosh;
import org.mcsg.bot.drawing.painters.AbstractShapes;
import org.mcsg.bot.drawing.painters.Circles;
import org.mcsg.bot.drawing.painters.Clusters;
import org.mcsg.bot.drawing.painters.Curves;
import org.mcsg.bot.drawing.painters.DotLines;
import org.mcsg.bot.drawing.painters.Dots;
import org.mcsg.bot.drawing.painters.Empty;
import org.mcsg.bot.drawing.painters.Fireworks;
import org.mcsg.bot.drawing.painters.Landscape;
import org.mcsg.bot.drawing.painters.Lines;
import org.mcsg.bot.drawing.painters.BasicShapes;
import org.mcsg.bot.drawing.painters.Smoke;
import org.mcsg.bot.drawing.painters.Smudge;
import org.mcsg.bot.drawing.painters.SprayPaint;
import org.mcsg.bot.drawing.painters.Sunset;
import org.mcsg.bot.util.Arguments;
import org.mcsg.bot.util.MapWrapper;

public class ImagePainterCommand implements BotCommand {

	private static final HashMap<String, Class<? extends AbstractPainter>> painters = new HashMap<>();
	private static final HashMap<String, Class<? extends Filter>> filters = new HashMap<>();

	private Random random = new Random();

	static {
		ImagePainterCommand.painters.put("shapes", BasicShapes.class);
		ImagePainterCommand.painters.put("abstract", AbstractShapes.class);
		ImagePainterCommand.painters.put("cluster", Clusters.class);
		ImagePainterCommand.painters.put("lines", Lines.class);
		ImagePainterCommand.painters.put("smoke", Smoke.class);
		ImagePainterCommand.painters.put("sunset", Sunset.class);
		ImagePainterCommand.painters.put("circles", Circles.class);
		ImagePainterCommand.painters.put("dots", Dots.class);
		ImagePainterCommand.painters.put("dotlines", DotLines.class);
		ImagePainterCommand.painters.put("spray", SprayPaint.class);
		ImagePainterCommand.painters.put("landscape", Landscape.class);
		ImagePainterCommand.painters.put("curves", Curves.class);
		ImagePainterCommand.painters.put("smudge", Smudge.class);
		ImagePainterCommand.painters.put("firework", Fireworks.class);

		ImagePainterCommand.painters.put("empty", Empty.class);

		ImagePainterCommand.filters.put("pixel", Pixelate.class);
		ImagePainterCommand.filters.put("distort", Distort_.class);
		ImagePainterCommand.filters.put("smoosh", Smoosh.class);
		ImagePainterCommand.filters.put("edge", Edge.class);
		ImagePainterCommand.filters.put("ab", ChromaticAberration.class);
		ImagePainterCommand.filters.put("warp", Warp.class);
		ImagePainterCommand.filters.put("scanlines", ScanLines.class);
		ImagePainterCommand.filters.put("bloom2", Bloom2.class);
	}

	public static void registerPainter(String name, Class<? extends AbstractPainter> painter) {
		ImagePainterCommand.painters.put(name, painter);
	}

	public static void registerFilter(String name, Class<? extends Filter> filter) {
		ImagePainterCommand.filters.put(name, filter);
	}

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		Arguments arge = new Arguments(args, "background/bg arg", "resolution/res arg", "filter arg", "import arg");

		String randGen = getRandomKey(painters);
		List<String> generators = new ArrayList<>();

		boolean set = false;
		if (arge.getArgs().length > 0) {
			generators.addAll(Arrays.asList(arge.getArgs()[0].split(",")));

		}

		int width = 1920;
		int height = 1080;

		System.out.println(arge.getSwitches());

		if (arge.hasSwitch("resolution")) {
			String res = arge.getSwitch("resolution");

			width = Integer.parseInt(res.split("x")[0]);
			height = Integer.parseInt(res.split("x")[1]);
		}

		BufferedImage img = null;

		if (arge.hasSwitch("import")) {
			img = ImageIO.read(new URL(arge.getSwitch("import")));
			if (generators.size() == 0) {
				generators.add("empty");
			}
		}

		if (img == null) {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		System.out.println(img);
		System.out.println(generators);

//		frame(img);

		final MapWrapper wrap = new MapWrapper();
		for (String arg : args) {
			String[] split = arg.split(":");
			if (split.length > 1)
				wrap.put(split[0], split[1]);
		}

		Color bg = Color.BLACK;

		if ("none".equalsIgnoreCase(arge.getSwitch("background"))) {
			bg = null;
		}
		if (arge.hasSwitch("background")) {
			bg = Color.decode(arge.getSwitch("background"));
		}

		if (!arge.hasSwitch("import"))
			setBackground(img, bg);

		for (String gen : generators) {
			Class<? extends AbstractPainter> generator = painters.get(gen);
			if (generator == null) {
				generator = painters.get(randGen);
			}
			AbstractPainter painter = generator.getConstructor(BufferedImage.class).newInstance(img);
			painter.paint(wrap);

		}

		if (arge.getSwitches().containsKey("filter")) {
			for (String fstr : arge.getSwitches().get("filter").split(",")) {

				Class<? extends Filter> fClass = filters.get(fstr);
				if (fClass != null) {
					Filter filter = fClass.newInstance();
					img = filter.filter(img, (Graphics2D) img.getGraphics(), wrap);
				}
			}
		}

		File file = new File(chat.getServer().getBot().getSettings().getDataFolder(),
				"painter_" + System.currentTimeMillis() + ".png");
		ImageIO.write(img, "png", file);
		chat.sendFile(file);
	}

	private void frame(BufferedImage img) {
		JFrame frame = new JFrame();
		frame.setSize(1280, 730);
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, 1280, 720, this); // draw background image
			}
		};
		frame.add(panel);
		panel.setBounds(0, 0, 1280, 720);

		frame.setVisible(true);

		new Thread(() -> {
			while (true) {
				sleep(5);
				panel.repaint();
			}
		}).start();
	}

	private void setBackground(BufferedImage img, Color color) {
		Graphics2D g = (Graphics2D) img.getGraphics();
		Color bcolor = g.getColor();

		g.setColor(color);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());

		g.setColor(bcolor);

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

	private String getRandomKey(Map<String, ?> map) {
		String[] s = map.keySet().toArray(new String[0]);

		return s[random.nextInt(s.length)];

	}

	@Override
	public String getPermission() {
		return "paint";
	}

}
