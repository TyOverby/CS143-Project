package graphics.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class WavefrontMtlLoader {
    public static final String MATERIAL_DIR = "assets/materials/";
    private static List<WavefrontLoaderError> errors = new java.util.ArrayList<WavefrontLoaderError>();

    public static int load(String filepath){
        return load(new File(MATERIAL_DIR + filepath));
    }

    public static int load(File file) {
        WavefrontMtlLoader mtl = new WavefrontMtlLoader(file);
        BufferedReader in;
        String line;

        try {
            in = new BufferedReader(new FileReader(file));

            // While we have lines in our file
            while((line = in.readLine()) != null)
                mtl.readLine(line);
        } catch (IOException e) {                     
            errors.add(new WavefrontLoaderError(file, e));
            return 0;
        }

        Map<String, Material> newMaterials = mtl.getMaterials();
        Material.materials.putAll(newMaterials);

        return newMaterials.size();
    }

    private Material current_material;
    private Map<String, Material> materials;
    private File file;


    private WavefrontMtlLoader(File file) {
        materials = new java.util.HashMap<String, Material>();
        this.file = file;
    }

    private void readLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        // And while we have tokens in the line
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // The first token in the line determines the data type that this line specifies
            switch(tokenType(token)) {
                case COMMENT:
                    /* NOOP - skip remainder of this line */
                    return;
                case NEW_MATERIAL:
                    current_material = Material.findOrCreateByName(tokenizer.nextToken());
                    materials.put(current_material.getName(), current_material);
                    break;
                case AMBIENT_COLOR:
                    getCurrentMaterial().setAmbientColor(readColor(tokenizer));
                    break;
                case DIFFUSE_COLOR:
                    getCurrentMaterial().setDiffuseColor(readColor(tokenizer));
                    break;
                case SPECULAR_COLOR:
                    getCurrentMaterial().setSpecularColor(readColor(tokenizer));
                    break;
                case ALPHA_TRANSPARENCY:
                    getCurrentMaterial().setTransparency(Float.parseFloat(tokenizer.nextToken()));
                    break;
                case ILLUMINATION_MODEL:
                    // ignore for now
                    return;
                case SHININESS:
                    getCurrentMaterial().setShininess(Float.parseFloat(tokenizer.nextToken()));
                    break;
                case OPTICAL_DENSITY:
                    // ignore for now, this is the index of refraction and we need a refraction shader to use this
                    return;
                case TEXTURE_MAP_FILENAME:
                    getCurrentMaterial().setTexture( Texture.findOrCreateByName(tokenizer.nextToken()));
                    break;
                default:
                    errors.add(new WavefrontLoaderError(file, "WavefrontMtlLoader: Unhandled Token: " + token + "\n" +"Line: " + line));
                    return;
            }
        }
    }

    private Material getCurrentMaterial(){
        if(current_material == null)
            throw new NullPointerException("current_material is null, potentially malformed material file missing material name declaration");
        return current_material;
    }

    private float[] readColor(StringTokenizer tokenizer) {
        float[] components = new float[5];
        float[] result;
        int i = 0;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            try {
                components[i] = Float.parseFloat(token);
                i++;
            } catch (NumberFormatException e) {
                errors.add(new WavefrontLoaderError(file, "Unexpected token " + token));
            }
        }
        switch (i) {
            case 1:
                // Java arrays are dumb - if we could create a Vector or ArrayList of floats we could just .toArray, but no we have to member by member copy to an array of the correct length
                result = new float[1];
                result[0] = components[0];
                return result;
            case 3:
                result = new float[3];
                result[0] = components[0];
                result[1] = components[1];
                result[2] = components[2];
                return result;
        }
        return null;
    }

    private Map<String, Material> getMaterials() {
        return materials;
    }

    private TokenType tokenType(String token) {
        if (token.equals("#"))
            return TokenType.COMMENT;
        if (token.equals("newmtl"))
            return TokenType.NEW_MATERIAL;
        if (token.equals("Ka"))
            return TokenType.AMBIENT_COLOR;
        if (token.equals("Kd"))
            return TokenType.DIFFUSE_COLOR;
        if (token.equals("Ks"))
            return TokenType.SPECULAR_COLOR;
        if (token.equals("d"))
            return TokenType.ALPHA_TRANSPARENCY;
        if (token.equals("Tr"))
            return TokenType.ALPHA_TRANSPARENCY;
        if (token.equals("Ns"))
            return TokenType.SHININESS;
        if (token.equals("Ni"))
            return TokenType.OPTICAL_DENSITY;
        if (token.equals("illum"))
            return TokenType.ILLUMINATION_MODEL;
        if (token.equals("map_Ka"))
            return TokenType.TEXTURE_MAP_FILENAME;
        if (token.equals("map_Kd"))
            return TokenType.TEXTURE_MAP_FILENAME;
        if (token.regionMatches(0, "#", 0, 1))
            return TokenType.COMMENT;
        return TokenType.UNKNOWN;
    }

    private enum TokenType {
        COMMENT, AMBIENT_COLOR, DIFFUSE_COLOR, SPECULAR_COLOR, ALPHA_TRANSPARENCY, UNKNOWN, SHININESS, ILLUMINATION_MODEL, TEXTURE_MAP_FILENAME, NEW_MATERIAL, OPTICAL_DENSITY,
    }

    public static List<WavefrontLoaderError> getErrors() {
        return errors;
    }
}
