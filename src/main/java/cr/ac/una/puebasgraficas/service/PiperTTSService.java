package cr.ac.una.puebasgraficas.service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

/**
 *
 * @author takka_sama
 */
public class PiperTTSService {
   
    private final String voicesPath;
    private final String piperPath;
    
    private MediaPlayer mediaPlayerActivo;
    
    private static PiperTTSService instance;
    
    private PiperTTSService(String voicesPath, String piperPath){
        this.voicesPath = voicesPath;
        this.piperPath = piperPath;
    }
    
    public static PiperTTSService getInstance(){
        
        if(instance == null) {
            instance = new PiperTTSService("./external/Modelos", detectPiperPath());
        }
        return instance;
        
    }
    
    
    //________________ DETECCION DEL SISTEMA OPERTATIVO Y SELECCION DEL EJECUTABLE DE PIPER
    private static String detectPiperPath(){
            
        String os = System.getProperty("os.name").toLowerCase();
        
        if(os.contains("win"))
            return "./external/Windows/piper_tts/piper.exe";
        else
            return "./external/Linux/piper_tts/piper";        
    } 
    
    
    // ________________ VERIFICACION DE LAS RUTAS DE LOS MODELOS DE VOZ Y DE PIPERTTS 
    
    private void validateFiles() throws IllegalAccessException{
        
        File voces = new File(voicesPath);
        File piper = new File(piperPath);
        
        if(!piper.exists()) 
            throw new IllegalAccessException("Error al encontrar el directorio en : " + piperPath);
        if(!voces.exists()) 
            throw new IllegalAccessException("Error al encontrar el directorio en : " + voicesPath);
        if(!System.getProperty("os.name").toLowerCase().contains("win"))
            piper.setExecutable(true);
        
    }
    //________________ CREADO DE ARCHIVO DE AUIDO
    private File synthesize(String msg) throws Exception {

        validateFiles();
        
        //__ MODELO DE VOZ
        File modelo = new File(voicesPath + "/es_ES-carlfm-x_low.onnx");
        if (!modelo.exists()) {
            throw new FileNotFoundException("Modelo no encontrado: " + modelo.getAbsolutePath());
        }
        
        
        File tempFile = File.createTempFile("tts_", ".wav");
        tempFile.deleteOnExit();

        //__ COMANDOS PARA LA EJECUCION DEL AUDIO 
        ProcessBuilder processBuilder = new ProcessBuilder(
            piperPath,
            "--model", modelo.getAbsolutePath(),
            "--output_file", tempFile.getAbsolutePath()
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
     
        //__ CONVERSION DE TEXTO A AUIDO
        try (OutputStream stdin = process.getOutputStream()) {
            stdin.write(msg.getBytes(StandardCharsets.UTF_8));
        }

        //__ VERIFICACION DEL ESTADO DE LA CONVERSION DEL TEXTO-AUDIO
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            String err = new String(process.getInputStream().readAllBytes());
            throw new RuntimeException("Error ejecutando Piper: " + err);
        }
        
        System.out.println("Audio generado en: " + tempFile.getAbsolutePath());
        
        return tempFile;

    }   
    
    // ________________ REPRODUCE EL AUDIO GENERADO 
    private void reproduce(File audioArchivo){
        if(mediaPlayerActivo != null) mediaPlayerActivo.stop();
        
        Media media = new Media(audioArchivo.toURI().toString());
        mediaPlayerActivo = new MediaPlayer(media);
        mediaPlayerActivo.setOnEndOfMedia(() -> audioArchivo.delete());
        mediaPlayerActivo.play();
        
    }
    
    //________________ SINTENTIZA Y REPRODUCE EL TEXTO 
    public void speak(String msg){
        new Thread (()->{
           try{
                File audioFile = synthesize(msg);
                Platform.runLater(()-> {reproduce(audioFile);});
               System.out.println("Se reproduce el audio");
           } 
           catch(Exception e){
              System.err.println("[PiperTTS] Error: " + e.getMessage());
              e.printStackTrace();
           }
        }).start();

        
    }
}