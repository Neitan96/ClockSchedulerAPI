package br.neitan96.clockschedulerapi.config;

import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Neitan96 on 01/10/15.
 */
public class YamlConfigurationUTF8 extends YamlConfiguration{

    /**
     * Charset da config
     */
    private static final Charset CHARSET = Charset.forName("UTF-8");


    /**
     * Carrega a config a parti de um arquivo
     *
     * @param file Arquivo para ser carregado
     */
    @Override
    public void load(File file) throws IOException, InvalidConfigurationException{
        load(new FileInputStream(file));
    }

    /**
     * Carrega a config a parti de um InputStream
     *
     * @param stream InputStream a ser carregado
     */
    @SuppressWarnings("deprecation")
    @Override
    public void load(InputStream stream) throws IOException, InvalidConfigurationException{
        Validate.notNull(stream, "InputStream não pode ser nulo!");

        InputStreamReader reader = new InputStreamReader(stream, CHARSET);
        StringBuilder builder = new StringBuilder();
        BufferedReader input = new BufferedReader(reader);

        //noinspection TryFinallyCanBeTryWithResources
        try{

            String line;

            while((line = input.readLine()) != null){
                builder.append(line);
                builder.append('\n');
            }

        }finally{
            input.close();
        }

        loadFromString(builder.toString());
    }

    /**
     * Salva a config em um arquivo
     *
     * @param file Arquivo para salvar
     */
    @Override
    public void save(File file) throws IOException{
        Validate.notNull(file, "Arquivo não pode ser nulo!");

        Files.createParentDirs(file);
        FileOutputStream stream = new FileOutputStream(file);

        save(stream);
    }

    /**
     * Salva a config em um OutputStream
     *
     * @param stream OutputStream para salvar
     */
    public void save(OutputStream stream) throws IOException{
        Validate.notNull(stream, "OutputStream não pode ser nulo!");

        OutputStreamWriter writer = new OutputStreamWriter(stream, CHARSET);

        //noinspection TryFinallyCanBeTryWithResources
        try{

            writer.write(saveToString());

        }finally{
            writer.close();
        }
    }

}