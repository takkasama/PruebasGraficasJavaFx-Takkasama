package cr.ac.una.puebasgraficas.service;

import javax.naming.ConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import io.github.givimad.piperjni.PiperJNI;
import io.github.givimad.piperjni.PiperVoice;
import java.nio.file.Path;

public class TTSSService {
    
   private String pathVoiceModel;
   private String pathConfigVoiceModel;
   private PiperJNI piper;
   private PiperVoice voice;

   private static TTSSService INSTANCE;

    private TTSSService(String pathVoiceModel, String pathConfigVoiceModel) throws PiperJNI.NotInitialized {
        this.pathVoiceModel = pathVoiceModel;
        this.pathConfigVoiceModel = pathConfigVoiceModel;
        try {
            this.piper = new PiperJNI();
            verityPathToModelVoice();
            loadLocalVoiceModel();
            
        }
        catch(IOException e){ 
            throw new RuntimeException("Error To Initialize TTS"); 
        }  
        catch (ConfigurationException ex) { 
            System.getLogger(TTSSService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex); 
        }
    }
   
    public static TTSSService getInstance() throws PiperJNI.NotInitialized{
        
        if(INSTANCE == null){
            INSTANCE = new TTSSService(
             "./external/Modelos/es_ES-carlfm-x_low.onnx",
             ".external/Modelos/es_ES-carlfm-x_low.onnx.json"       
            );
        }
        return INSTANCE;
    }
    
    
    private void verityPathToModelVoice() throws ConfigurationException{
        
        if(pathConfigVoiceModel == null || pathConfigVoiceModel.isBlank())
            throw new ConfigurationException("env var CONFIG_PATH_MODEL_VOICE");
        if(pathVoiceModel == null || pathVoiceModel.isBlank())
            throw new ConfigurationException("env var CONFIG_PATH_MODEL_VOICE");
        
    }
    
    private void loadLocalVoiceModel() throws PiperJNI.NotInitialized, FileNotFoundException {
        
        Path modelVoice = Path.of(pathVoiceModel);
        Path configVoice = Path.of(pathConfigVoiceModel);
        
        voice = piper.loadVoice(modelVoice, configVoice);
        
    }
    
    public void sinthesizeText(String msg){
    }
    
}
