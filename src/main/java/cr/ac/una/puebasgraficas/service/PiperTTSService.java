package cr.ac.una.puebasgraficas.service;
import java.io.*;
import java.nio.file.*;


/**
 *
 * @author takka_sama
 */
public class PiperTTSService {
   
    
/**
 * Direccion del modelo de piper y las voces 
 */
    
    private final String vocesPath;
    private final String piperPath;
    
    private static PiperTTSService INSTANCIA;
    
    private PiperTTSService(String vocesPath, String piperPath){
        this.vocesPath = vocesPath;
        this.piperPath = piperPath;
    }
    
    public PiperTTSService getInstancia(){
        
        if(INSTANCIA == null) INSTANCIA = new PiperTTSService("cr/ac/una/pruebasgraficas/tts", detectarPiperPath());
        return INSTANCIA;
        
    }
    
    private String detectarPiperPath(){
            
        String os = System.getProperty("os.name").toLowerCase();
        
        if(os.contains("win"))
            return "./externals/Win/Pendiente";
        else
            return "./external/Linux/pipper/tts";
        
    } 
    
    private void validarArchivos() throws IllegalAccessException{
        
        File voces = new File(vocesPath);
        File piper = new File(piperPath);
        
        if(!piper.exists()) 
            throw new IllegalAccessException("Error al encontrar el directorio en : " + piperPath);
        if(!voces.exists()) 
            throw new IllegalAccessException("Error al encontrar el directorio en : " + vocesPath);
        if(!System.getProperty("os.name").toLowerCase().contains("win"))
            piper.setExecutable(true);
        
    }
    
    public void sintentizar(String msg, String outputWav) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            piperPath,
            "--model", vocesPath,
            "--output_file", outputWav
        );

        pb.directory(new File(piperPath).getParentFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (OutputStream stdin = process.getOutputStream()) {
            stdin.write(msg.getBytes("UTF-8"));
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String err = new String(process.getInputStream().readAllBytes());
            throw new RuntimeException("Piper error en "
                + System.getProperty("os.name") + ": " + err);
        }
    }
    
}
