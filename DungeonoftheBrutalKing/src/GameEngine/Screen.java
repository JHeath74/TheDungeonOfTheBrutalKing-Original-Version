
// Screen.java
package GameEngine;

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
        for (int n = 0; n < pixels.length / 2; n++) {
            if (pixels[n] != Color.DARK_GRAY.getRGB()) {
                pixels[n] = Color.DARK_GRAY.getRGB();
            }
        }
        for (int i = pixels.length / 2; i < pixels.length; i++) {
            if (pixels[i] != Color.gray.getRGB()) {
                pixels[i] = Color.gray.getRGB();
            }
        }

        for (int x = 0; x < width; x = x + 1) {
            double cameraX = 2 * x / (double) (width) - 1;
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
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            } else {
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
            }

            int lineHeight;
            if (perpWallDist > 0) {
                lineHeight = Math.abs((int) (height / perpWallDist));
            } else {
                lineHeight = height;
            }
            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0) {
                drawStart = 0;
            }
            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height) {
                drawEnd = height - 1;
            }

            if (mapX < 0 || mapX >= mapWidth || mapY < 0 || mapY >= mapHeight) continue;
            int texNum = map[mapX][mapY] - 1;
            if (texNum < 0 || texNum >= textures.size()) continue;

            double wallX;
            if (side == 1) {
                wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {
                wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            wallX -= Math.floor(wallX);

            int texX = (int) (wallX * (textures.get(texNum).SIZE));
            int halfSize = textures.get(texNum).SIZE / 2;
            if (side == 0 && rayDirX > 0 || side == 1 && rayDirY < 0) {
                texX = Math.min(texX, halfSize - 1);
            } else {
                texX = Math.max(texX, halfSize);
            }
            if (side == 0 && rayDirX > 0) {
                texX = textures.get(texNum).SIZE - texX - 1;
            }
            if (side == 1 && rayDirY < 0) {
                texX = textures.get(texNum).SIZE - texX - 1;
            }
            texX = Math.max(0, Math.min(texX, textures.get(texNum).SIZE - 1));

            for (int y = drawStart; y < drawEnd; y++) {
                if (y < 0 || y >= height) continue;
                int texY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
                texY = Math.max(0, Math.min(texY, textures.get(texNum).SIZE - 1));
                int color;
                if (side == 0) {
                    color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
                } else {
                    color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)] >> 1) & 8355711;
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
