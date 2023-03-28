package rl01.Main;

public class WorldBuilder {

	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;

	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
	}

	public World build() {
		return new World(tiles);
	}

	private WorldBuilder randomizeTiles() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
				tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
			}
		  }
		}
		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][][] tiles2 = new Tile[width][height][depth];
		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					for (int z = 0; z < depth; z++) {
					int floors = 0;
					int rocks = 0;

					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height)
								continue;

							if (tiles[x + ox][y + oy][z] == Tile.FLOOR)
								floors++;
							else
								rocks++;
						}
					}
					tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
				}
			  }
			}
			tiles = tiles2;
		}
		return this;
	}

	public WorldBuilder makeCaves() {
		return randomizeTiles().smooth(8);
	}

}
