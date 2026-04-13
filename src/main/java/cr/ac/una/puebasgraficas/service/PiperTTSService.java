package cr.ac.una.puebasgraficas.service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

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

    public static PiperTTSService getInstancia(){
        
        if(INSTANCIA == null) {
            INSTANCIA = new PiperTTSService("./external/Modelos_PipperTTS", detectarPiperPath());
        }
        return INSTANCIA;
        
    }
 /**
 *     Deteccion del modelo y en consecuencia devuelve la la ruta del programa de ejecucion 
 */
    private static String detectarPiperPath(){
            
        String os = System.getProperty("os.name").toLowerCase();
        
        if(os.contains("win"))
            return "./externals/Windows/piper_tts/piper.exe";
        else
            return "./external/Linux/piper_tts/piper";        
    } 
 /**
 *          Verificacion para verificar si las rutas de los modelos de voces y pipertts sea valida
 */
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
    
    public void sintetizar(String msg) throws Exception {

        validarArchivos();

        File modelo = new File(vocesPath + "/es_MX-claude-high.onnx");
        if (!modelo.exists()) {
            throw new FileNotFoundException("Modelo no encontrado: " + modelo.getAbsolutePath());
        }

        File tempFile = File.createTempFile("tts_", ".wav");
        tempFile.deleteOnExit();

        ProcessBuilder processBuilder = new ProcessBuilder(
            piperPath,
            "--model", modelo.getAbsolutePath(),
            "--output_file", tempFile.getAbsolutePath()
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (OutputStream stdin = process.getOutputStream()) {
            stdin.write(msg.getBytes(StandardCharsets.UTF_8));
        }

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            String err = new String(process.getInputStream().readAllBytes());
            throw new RuntimeException("Error ejecutando Piper: " + err);
        }

        System.out.println("Audio generado en: " + tempFile.getAbsolutePath());
        
        Media media = new Media(tempFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }    
}
