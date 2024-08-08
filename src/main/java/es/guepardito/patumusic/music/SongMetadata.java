package es.guepardito.patumusic.music;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Getter
public class SongMetadata {
    private String title;
    private String artist;
    private String album;
    private long duration;
    private byte[] coverArt;

    public SongMetadata(String filePath) {
        try {
            Mp3File mp3File = new Mp3File(filePath);
            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                this.title = id3v2Tag.getTitle();
                this.artist = id3v2Tag.getArtist();
                this.album = id3v2Tag.getAlbum();
                this.coverArt = id3v2Tag.getAlbumImage();
            } else if (mp3File.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3File.getId3v1Tag();
                this.title = id3v1Tag.getTitle();
                this.artist = id3v1Tag.getArtist();
                this.album = id3v1Tag.getAlbum();
                // ID3v1 no soporta imágenes
            }
            this.duration = mp3File.getLengthInMilliseconds();
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        saveCoverArtAsPng(getCoverArtAsString());
    }

    @Override
    public String toString() {
        return "SongMetadata{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                '}';
    }

    // cambiar path a la carpeta destino
    public void saveCoverArtAsPng(String name) {
        String outputPath = "F:/images/" + name + ".png";
        if (coverArt != null && coverArt.length > 0) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new java.io.ByteArrayInputStream(coverArt));

                // Redimensiona la imagen si es menor a 512x512 píxeles
                BufferedImage resizedImage = ensureMinimumSize(bufferedImage, 512, 512);

                File outputFile = new File(outputPath);

                // Asegúrate de que el directorio existe
                File parentDir = outputFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                ImageIO.write(resizedImage, "png", outputFile);
                System.out.println("Imagen de portada guardada en: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al guardar la imagen de portada.");
            }
        } else {
            System.err.println("No hay imagen de portada disponible.");
        }
    }

    private BufferedImage ensureMinimumSize(BufferedImage originalImage, int minWidth, int minHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Redimensiona la imagen si es menor a minWidth o minHeight
        if (width < minWidth || height < minHeight) {
            int newWidth = Math.max(width, minWidth);
            int newHeight = Math.max(height, minHeight);

            // Crear una nueva imagen con el tamaño mínimo
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();

            // Llenar el fondo con blanco (o el color que prefieras)
            g2d.setBackground(Color.BLACK);
            g2d.clearRect(0, 0, newWidth, newHeight);

            // Redimensionar la imagen original al centro de la nueva imagen
            g2d.drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
                    (newWidth - width) / 2, (newHeight - height) / 2, null);
            g2d.dispose();

            return resizedImage;
        }

        return originalImage;
    }

    public String getCoverArtAsString() {
        // TODO: comprobar si es una cancion especial (japones) y asignarle un numero en vez del titulo
        if (album != null && !album.isEmpty()) {
            if (album.equals("夜に駆ける")) {
                return formatCoverString("_");
            }
            return formatCoverString(album);
        } else {
            return formatCoverString(title);
        }
    }

    /**
     * Funcion para formatear los nombres de las portadas a la normalizacion que impone discord para subir las imagenes
     * @param unformattedName string antes de realizar el formateo
     * @return formated el string formateado
     */
    private String formatCoverString(String unformattedName) {
        String formatted = unformattedName.toLowerCase();
        formatted = formatted.replace(" ", "_");

        formatted = Normalizer.normalize(formatted, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        formatted = pattern.matcher(formatted).replaceAll("");

        return formatted;
    }
}
