
package DungeonoftheBrutalKing.GameEngine;

import java.awt.Color;
import java.util.ArrayList;

public class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, mapLevel, width, height;
    public ArrayList<Texture> textures;

    public Screen(int[][] map2, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) {
        map = map2;
        mapWidth = mapW;
        mapHeight = mapH;
        textures = tex;
        width = w;
        height = h;
    }

    public int[] update(Camera camera, int[] pixels) {
        int half = height / 2;

        int[] floorPixels   = camera.getFloorPixels();
        int[] ceilingPixels = camera.getCeilingPixels();

        // Floor and Ceiling via raycasted world-space coordinates
        for (int y = half + 1; y < height; y++) {
            double rayDirX0 = camera.xDir - camera.xPlane;
            double rayDirY0 = camera.yDir - camera.yPlane;
            double rayDirX1 = camera.xDir + camera.xPlane;
            double rayDirY1 = camera.yDir + camera.yPlane;

            int p = y - half;
            double posZ = 0.5 * height;
            double rowDistance = posZ / p;

            double floorStepX = rowDistance * (rayDirX1 - rayDirX0) / width;
            double floorStepY = rowDistance * (rayDirY1 - rayDirY0) / width;

            double floorX = camera.xPos + rowDistance * rayDirX0;
            double floorY = camera.yPos + rowDistance * rayDirY0;

            for (int x = 0; x < width; x++) {
                int tx = (int)(floorX * Texture.SIZE) & (Texture.SIZE - 1);
                int ty = (int)(floorY * Texture.SIZE) & (Texture.SIZE - 1);
                tx = Math.max(0, Math.min(tx, Texture.SIZE - 1));
                ty = Math.max(0, Math.min(ty, Texture.SIZE - 1));

                int idx = ty * Texture.SIZE + tx;

                // Floor (bottom half)
                pixels[y * width + x] = (floorPixels != null)
                        ? floorPixels[idx]
                        : Color.GRAY.getRGB();

                // Ceiling (mirrored top half)
                int ceilingY = height - y - 1;
                pixels[ceilingY * width + x] = (ceilingPixels != null)
                        ? ceilingPixels[idx]
                        : Color.DARK_GRAY.getRGB();

                floorX += floorStepX;
                floorY += floorStepY;
            }
        }

        // Wall raycasting
        for (int x = 0; x < width; x++) {
            double cameraX = 2 * x / (double)(width) - 1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            int mapX = (int) camera.xPos;
            int mapY = (int) camera.yPos;
            double sideDistX, sideDistY;
            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
            double perpWallDist;
            int stepX, stepY;
            boolean hit = false;
            int side = 0;

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }

            while (!hit) {
                if (mapX < 0 || mapX >= mapWidth || mapY < 0 || mapY >= mapHeight) break;
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if (mapX >= 0 && mapX < mapWidth && mapY >= 0 && mapY < mapHeight && map[mapX][mapY] > 0) {
                    hit = true;
                }
            }

            if (side == 0) {
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2.0) / rayDirX);
            } else {
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2.0) / rayDirY);
            }

            int lineHeight = (perpWallDist > 0) ? Math.abs((int)(height / perpWallDist)) : height;

            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0) drawStart = 0;

            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height - 1;

            if (mapX < 0 || mapX >= mapWidth || mapY < 0 || mapY >= mapHeight) continue;
            int texNum = map[mapX][mapY] - 1;
            if (texNum < 0 || texNum >= textures.size()) continue;

            double wallX;
            if (side == 1) {
                wallX = camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2.0) / rayDirY) * rayDirX;
            } else {
                wallX = camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2.0) / rayDirX) * rayDirY;
            }
            wallX -= Math.floor(wallX);

            int texX = (int)(wallX * Texture.SIZE);
            if (side == 0 && rayDirX > 0) texX = Texture.SIZE - texX - 1;
            if (side == 1 && rayDirY < 0) texX = Texture.SIZE - texX - 1;
            texX = Math.max(0, Math.min(texX, Texture.SIZE - 1));

            for (int y = drawStart; y < drawEnd; y++) {
                if (y < 0 || y >= height) continue;
                int texY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
                texY = Math.max(0, Math.min(texY, Texture.SIZE - 1));
                int color;
                if (side == 0) {
                    color = textures.get(texNum).pixels[texX + (texY * Texture.SIZE)];
                } else {
                    color = (textures.get(texNum).pixels[texX + (texY * Texture.SIZE)] >> 1) & 8355711;
                }
                int pixelIndex = x + y * width;
                if (pixelIndex >= 0 && pixelIndex < pixels.length) {
                    pixels[pixelIndex] = color;
                }
            }
        }
        return pixels;
    }
}
