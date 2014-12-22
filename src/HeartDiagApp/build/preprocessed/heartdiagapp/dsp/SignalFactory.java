package heartdiagapp.dsp;

import heartdiagapp.StringHelper;
import heartdiagapp.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class SignalFactory {
    public final static int NUMBER_OF_SAMPLES = 3500;
    public static Signal fromStream(InputStream inputStream) throws IOException {
        FileReader fileReader = new FileReader(inputStream);
        SimpleSignal simpleSignal = new SimpleSignal(NUMBER_OF_SAMPLES);
        simpleSignal.setTimeBetweenValues(Double.parseDouble(fileReader.nextLine()));
        for( int i = 0; i < NUMBER_OF_SAMPLES;) {            
            String line = fileReader.nextLine();
            if(line.trim().length() > 0) {
                double value = Double.parseDouble(StringHelper.split(line, ' ').elementAt(1).toString());
                simpleSignal.set(i, value);
                i++;
            }
        }
        simpleSignal.calculateMaxAltitude();
        simpleSignal.calculateBaseLine();        
        return simpleSignal;
    }
}
