package com.ad.almenzarjimenezsergio.archivosdejamones.data.util;

import com.ad.almenzarjimenezsergio.archivosdejamones.data.Vino;

public class Csv {

    public static Vino getVino(String str){
        String[] atributos = str.split(";");
        Vino v = new Vino();

        //long id, String nombre, String bodega, String color, String origen, double gradacion, int fecha
        if (atributos.length == 6){
            try {
                v.setId(Long.parseLong(atributos[0].trim()));

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            v.setNombre(atributos[1].trim());
            v.setBodega(atributos[2].trim());
            v.setColor(atributos[3].trim());
            v.setOrigen(atributos[4].trim());

            try {
                v.setGraduacion(Double.parseDouble(atributos[5]));

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            try {
                v.setFecha(Integer.parseInt(atributos[6]));

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return v;
    }

    public static String getCsv(Vino v){
        return v.getId() + "; " +
                v.getNombre() + "; " +
                v.getBodega() + "; " +
                v.getColor() + "; " +
                v.getOrigen() + "; " +
                v.getGraduacion() + "; " +
                v.getFecha()+ "\n";
    }

    public static String getCsv(String[] campos){
        String salida = "";

        for (int i = 0; i < campos.length; i++){
            if ((i+1) < campos.length){
                salida+= campos[i] + ";";
            } else {
                salida += campos[i];
            }
        }

        return salida;
    }


}
