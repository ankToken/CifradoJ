package com.example.t0ken.cifradoj;

/**
 * Created by token on 13/07/17.
 */

class Cifrado
{
    private static final char ASCII[]=new char[127];
    public Cifrado()
    {
        obtenerAscii();
    }


    public String cifrar(String mensaje, int desplazamiento)
    {
        String mensajeCifrado="";
        int contador=0;
        int posicionNueva=0;
        while(contador<mensaje.length())
        {
            if(!Character.isWhitespace(mensaje.charAt(contador)))
            {
                if(mensaje.charAt(contador)>32 && mensaje.charAt(contador)<127)
                {
                    posicionNueva=mensaje.charAt(contador)+desplazamiento;
                    while(posicionNueva>126)
                    {
                        if(posicionNueva>126)
                            posicionNueva=posicionNueva-126+33;
                    }
                    mensajeCifrado+=ASCII[posicionNueva];
                }
            }
            else
                mensajeCifrado+=" ";

            contador++;
        }
        return mensajeCifrado;
    }


    public String descifrar(String mensaje, int desplazamiento)
    {
        String mensajeDescifrado="";
        int contador=0;
        int posicionNueva=0;
        while(contador<mensaje.length())
        {
            if(!Character.isWhitespace(mensaje.charAt(contador)))
            {
                if((mensaje.charAt(contador)>32 && mensaje.charAt(contador)<127))
                {
                    posicionNueva=mensaje.charAt(contador)-desplazamiento;
                    while(posicionNueva<33)
                    {
                        if(posicionNueva<33)
                            posicionNueva=posicionNueva+126-33;
                    }
                    mensajeDescifrado+=ASCII[posicionNueva];
                }
            }
            else
                mensajeDescifrado+=" ";

            contador++;
        }
        return mensajeDescifrado;
    }


    private void obtenerAscii()
    {
        ASCII[33]='!';
        int contador=1;
        for(int i=34;i<127;i++)
        {
            ASCII[i]=(char)('!'+contador);
            contador++;
        }
    }
}