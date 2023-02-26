package meldexun.nothirium.util;

import meldexun.nothirium.api.renderer.chunk.IRenderChunk;

public enum Direction {

	DOWN(0, Axis.Y) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return renderChunk.getY() >= 256 || cameraY > renderChunk.getY();
		}
	},
	UP(1, Axis.Y) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return renderChunk.getY() < 0 || cameraY < renderChunk.getY() + 16;
		}
	},
	NORTH(2, Axis.Z) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return cameraZ > renderChunk.getZ();
		}
	},
	SOUTH(3, Axis.Z) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return cameraZ < renderChunk.getZ() + 16;
		}
	},
	WEST(4, Axis.X) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return cameraX > renderChunk.getX();
		}
	},
	EAST(5, Axis.X) {
		@Override
		public boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ) {
			return cameraX < renderChunk.getX() + 16;
		}
	};

	static {
		WEST.opposite = EAST;
		EAST.opposite = WEST;
		DOWN.opposite = UP;
		UP.opposite = DOWN;
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
	}

	public static final Direction[] ALL = Direction.values();
	public static final Direction[] HORIZONTAL = {
			NORTH,
			SOUTH,
			WEST,
			EAST };
	public static final Direction[] VERTICAL = {
			DOWN,
			UP };

	private final int index;
	private final Axis axis;
	private Direction opposite;

	private Direction(int index, Axis axis) {
		this.index = index;
		this.axis = axis;
	}

	public int getIndex() {
		return this.index;
	}

	public Direction opposite() {
		return this.opposite;
	}

	public Axis getAxis() {
		return axis;
	}

	public static Direction get(int index) {
		return ALL[index];
	}

	public static Direction get(float x, float y, float z) {
		float x1 = Math.abs(x);
		float y1 = Math.abs(y);
		float z1 = Math.abs(z);
		if (x1 >= y1 && x1 >= z1) {
			return x < 0.0F ? WEST : EAST;
		} else if (y1 >= z1) {
			return y < 0.0F ? DOWN : UP;
		} else {
			return z < 0.0F ? NORTH : SOUTH;
		}
	}

	public abstract boolean isFaceCulled(IRenderChunk<?> renderChunk, double cameraX, double cameraY, double cameraZ);

}
