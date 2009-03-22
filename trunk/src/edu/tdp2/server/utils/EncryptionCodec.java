package edu.tdp2.server.utils;

import javax.crypto.BadPaddingException;

public class EncryptionCodec
{
    public byte[] decode( String str ) throws BadPaddingException
    {
        if ( str.length( ) % 3 > 0 )
            throw new BadPaddingException( "Bad padding, cannot decode with EncryptionCodec." );
        byte[] enc = new byte[str.length( ) / 3];
        for ( int i = 0; i < str.length( ); i += 3 )
        {
            String strByte = str.substring( i, i + 3 );
            int bite = new Integer( strByte );
            bite -= 128;
            enc[i / 3] = ( byte ) bite;
        }
        return enc;
    }

    public String encode( byte[] enc )
    {
        String str = "";
        for ( byte bite : enc )
        {
            int ibite = ( int ) bite;
            ibite += 128;
            String strByte = new Integer( ibite ).toString( );

            // Pads with zeroes
            while ( strByte.length( ) < 3 )
                strByte = "0" + strByte;
            str += strByte;
        }
        return str;
    }
}
