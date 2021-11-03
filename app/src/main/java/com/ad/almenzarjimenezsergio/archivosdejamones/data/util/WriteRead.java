package com.ad.almenzarjimenezsergio.archivosdejamones.data.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteRead {

    public String readFile(File file, String fileName){
        File f = new File(file, fileName);
        String texto = "";

        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null){
                texto += linea+"\n";
            }
            br.close();


        } catch (IOException e) {
            texto = null;
        }

        return texto;
    }

    public void /*boolean*/ writeFile(File file,String fileName ,String string){
        File f = new File(file, fileName);
        FileWriter fw = null; //FileWriter(File f,boolean append)
        //boolean ok = true;
        try {
            fw = new FileWriter(f, true);
            fw.write(string);
            fw.write("");
            fw.flush();
            fw.close();

        } catch (IOException ioe){
          //  ok = false;
        }
        //return ok;
    }

    public void deleteRow(File file, String id, String archivo) {
        File f = new File(file, archivo);
        File f2 = new File(file, "temp.tmp");
        String str[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                str = linea.split(";");
                if (!id.equals(str[0])) {
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);
        } catch (Exception e) {
            Log.v("jamaica", e.toString());
        }

    }

}
